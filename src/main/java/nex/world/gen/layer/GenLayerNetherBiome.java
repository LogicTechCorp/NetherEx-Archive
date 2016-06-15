package nex.world.gen.layer;

import net.minecraft.init.Biomes;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeManager;
import nex.api.NetherExAPI;

import java.util.List;

public class GenLayerNetherBiome extends GenLayerNether
{
    public GenLayerNetherBiome(long seed)
    {
        super(seed);
        NetherExAPI.addNetherBiome(Biomes.HELL, 10);
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
    {
        int[] biomeGen = IntCache.getIntCache(areaWidth * areaHeight);

        for(int i = 0; i < areaHeight; ++i)
        {
            for(int j = 0; j < areaWidth; ++j)
            {
                this.initChunkSeed((long) (j + areaX), (long) (i + areaY));
                biomeGen[j + i * areaWidth] = Biome.getIdForBiome(this.getWeightedBiomeFromList(NetherExAPI.getNetherBiomes()).biome);
            }
        }
        return biomeGen;
    }

    private BiomeManager.BiomeEntry getWeightedBiomeFromList(List<BiomeManager.BiomeEntry> biomes)
    {
        return WeightedRandom.getRandomItem(biomes, (int)this.nextLong(WeightedRandom.getTotalWeight(biomes) / 10) * 10);
    }
}

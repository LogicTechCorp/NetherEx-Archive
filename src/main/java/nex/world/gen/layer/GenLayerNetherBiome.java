package nex.world.gen.layer;

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
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
    {
        int[] biomeIDs = IntCache.getIntCache(areaWidth * areaHeight);

        for(int i = 0; i < areaHeight; ++i)
        {
            for(int j = 0; j < areaWidth; ++j)
            {
                initChunkSeed((long) (j + areaX), (long) (i + areaY));
                biomeIDs[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry(NetherExAPI.getBiomeEntries()).biome);
            }
        }

        return biomeIDs;
    }

    private BiomeManager.BiomeEntry getWeightedBiomeEntry(List<BiomeManager.BiomeEntry> biomeEntries)
    {
        return WeightedRandom.getRandomItem(biomeEntries, nextInt(WeightedRandom.getTotalWeight(biomeEntries) / 10) * 10);
    }
}

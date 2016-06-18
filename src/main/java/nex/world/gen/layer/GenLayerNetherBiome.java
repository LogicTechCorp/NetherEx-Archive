package nex.world.gen.layer;

import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeManager;
import nex.api.NetherExAPI;

import java.util.List;

public class GenLayerNetherBiome extends GenLayerNether
{
    public GenLayerNetherBiome(long seed, GenLayer parent)
    {
        super(seed);
        this.parent = parent;
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
    {
        int[] ints = IntCache.getIntCache(areaWidth * areaHeight);

        for(int i = 0; i < areaHeight; ++i)
        {
            for(int j = 0; j < areaWidth; ++j)
            {
                this.initChunkSeed((long) (j + areaX), (long) (i + areaY));
                ints[j + i * areaWidth] = Biome.getIdForBiome(this.getRandomBiomeEntry(NetherExAPI.getBiomeEntries()).biome);
            }
        }
        return ints;
    }

    private BiomeManager.BiomeEntry getRandomBiomeEntry(List<BiomeManager.BiomeEntry> biomeEntries)
    {
        if(biomeEntries.size() == 0)
        {
            biomeEntries.add(NetherExAPI.defaultBiomeEntry);
        }

        return WeightedRandom.getRandomItem(biomeEntries, this.nextInt(WeightedRandom.getTotalWeight(biomeEntries) / 10) * 10);
    }
}

package nex.world.gen.layer;

import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.IntCache;
import nex.api.NetherExAPI;
import nex.api.biome.NetherBiomeEntry;

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
        int[] ints = IntCache.getIntCache(areaWidth * areaHeight);

        for(int i = 0; i < areaHeight; ++i)
        {
            for(int j = 0; j < areaWidth; ++j)
            {
                initChunkSeed((long) (j + areaX), (long) (i + areaY));
                ints[j + i * areaWidth] = Biome.getIdForBiome(getRandomBiomeEntry(NetherExAPI.getBiomeEntries()).biome);
            }
        }
        return ints;
    }

    private NetherBiomeEntry getRandomBiomeEntry(List<NetherBiomeEntry> biomeEntries)
    {
        if(biomeEntries.size() == 0)
        {
            biomeEntries.add(NetherExAPI.defaultBiomeEntry);
        }

        return WeightedRandom.getRandomItem(biomeEntries, nextInt(WeightedRandom.getTotalWeight(biomeEntries) / 10) * 10);
    }
}

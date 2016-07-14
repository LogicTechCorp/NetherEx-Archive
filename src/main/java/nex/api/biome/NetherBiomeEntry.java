package nex.api.biome;

import net.minecraft.util.WeightedRandom;

public class NetherBiomeEntry extends WeightedRandom.Item
{
    public final NetherBiome biome;
    public final int weight;

    public NetherBiomeEntry(NetherBiome biomeIn, int weightIn)
    {
        super(weightIn);

        biome = biomeIn;
        weight = weightIn;
    }

    @Override
    public  boolean equals(Object obj)
    {
        if(obj == null || !(obj instanceof NetherBiomeEntry))
        {
            return false;
        }
        else
        {
            NetherBiomeEntry entry = (NetherBiomeEntry) obj;

            if(entry.biome.equals(biome) && entry.itemWeight == weight)
            {
                return true;
            }
        }

        return false;
    }
}

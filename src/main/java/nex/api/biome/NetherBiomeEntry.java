package nex.api.biome;

import net.minecraft.util.WeightedRandom;

public class NetherBiomeEntry extends WeightedRandom.Item
{
    public final NetherBiome biome;

    public NetherBiomeEntry(NetherBiome biomeIn, int weightIn)
    {
        super(weightIn);

        biome = biomeIn;
    }

    @Override
    public int hashCode()
    {
        int hash = biome.hashCode();

        hash = 31 * hash + itemWeight;
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj == null || obj.getClass() != getClass())
        {
            return false;
        }

        NetherBiomeEntry entry = (NetherBiomeEntry) obj;

        return itemWeight == entry.itemWeight && (biome != null ? biome.equals(entry.biome) : entry.biome == null);
    }
}

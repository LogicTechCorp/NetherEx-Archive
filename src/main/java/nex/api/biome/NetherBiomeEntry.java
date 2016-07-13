package nex.api.biome;

import net.minecraft.util.WeightedRandom;

public class NetherBiomeEntry extends WeightedRandom.Item
{
    public final NetherBiome biome;

    public NetherBiomeEntry(NetherBiome biomeIn, int weight)
    {
        super(weight);

        biome = biomeIn;
    }
}

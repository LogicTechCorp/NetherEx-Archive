package logictechcorp.netherex.world.biome.info;

import logictechcorp.libraryex.world.biome.BiomeInfo;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;

public class NetherBiomeInfo extends BiomeInfo
{
    public NetherBiomeInfo(ResourceLocation biomeRegistryName, int weight, boolean enabled, boolean genDefaultFeatures)
    {
        super(biomeRegistryName, weight, enabled, genDefaultFeatures);
    }

    public NetherBiomeInfo()
    {
        super();
        this.biome = Biomes.HELL;
    }
}

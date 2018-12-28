package logictechcorp.netherex.world.biome.wrapper;

import logictechcorp.libraryex.world.biome.wrapper.BiomeWrapper;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;

public class NetherBiomeWrapper extends BiomeWrapper implements INetherBiomeWrapper
{
    public NetherBiomeWrapper(ResourceLocation biomeRegistryName, int weight, boolean enabled, boolean genDefaultFeatures)
    {
        super(biomeRegistryName, weight, enabled, genDefaultFeatures);
    }

    public NetherBiomeWrapper()
    {
        super();
        this.biome = Biomes.HELL;
    }
}

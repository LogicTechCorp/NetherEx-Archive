package logictechcorp.netherex.world.biome.wrapper;

import logictechcorp.libraryex.LibraryEx;
import logictechcorp.libraryex.world.biome.wrapper.BiomeWrapper;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;

import java.io.File;

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

    @Override
    public File getSaveFile()
    {
        return new File(LibraryEx.CONFIG_DIRECTORY, "NetherEx/Biomes/" + this.getBiome().getRegistryName().toString().replace(":", "/") + ".json");
    }
}

package nex.world.biome;

import lex.LibEx;
import lex.world.biome.BiomeWrapper;
import net.minecraft.util.ResourceLocation;

import java.io.File;

class NetherBiomeWrapper extends BiomeWrapper implements INetherBiomeWrapper
{
    public NetherBiomeWrapper(ResourceLocation biomeRegistryName, int weight, boolean enabled, boolean genDefaultFeatures)
    {
        super(biomeRegistryName, weight, enabled, genDefaultFeatures);
    }

    public NetherBiomeWrapper()
    {
        super();
    }

    @Override
    public File getSaveFile()
    {
        return new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Biomes/" + this.getBiome().getRegistryName().toString().replace(":", "/") + ".json");
    }
}

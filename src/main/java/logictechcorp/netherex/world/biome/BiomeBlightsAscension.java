package logictechcorp.netherex.world.biome;

import logictechcorp.libraryex.world.biome.BiomeInfo;
import logictechcorp.netherex.NetherEx;

public class BiomeBlightsAscension extends BiomeNetherEx
{
    public BiomeBlightsAscension()
    {
        super(NetherEx.instance, new BiomeProperties("Blight's Ascension").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled(), "blights_ascension");
    }

    @Override
    public BiomeInfo getInfo()
    {
        return null;
    }
}

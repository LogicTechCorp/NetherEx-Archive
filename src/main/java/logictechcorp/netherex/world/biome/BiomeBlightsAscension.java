package logictechcorp.netherex.world.biome;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.world.biome.wrapper.INetherBiomeWrapper;

public class BiomeBlightsAscension extends BiomeNetherEx
{
    public BiomeBlightsAscension()
    {
        super(NetherEx.instance, new BiomeProperties("Blight's Ascension").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled(), "blights_ascension");
    }

    @Override
    public INetherBiomeWrapper getWrapper()
    {
        return null;
    }
}

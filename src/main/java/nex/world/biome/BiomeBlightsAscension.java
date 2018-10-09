package nex.world.biome;

import lex.world.biome.BiomeConfigurations;
import nex.NetherEx;

public class BiomeBlightsAscension extends BiomeNetherEx
{
    public BiomeBlightsAscension()
    {
        super(NetherEx.instance, new BiomeProperties("Blight's Ascension").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled(), "blights_ascension");
    }

    @Override
    public BiomeConfigurations getConfigurations()
    {
        return null;
    }
}

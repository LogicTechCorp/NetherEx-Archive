package nex.world.biome;

import lex.world.biome.BiomeConfigurations;
import nex.NetherEx;

public class BiomeRegrowthsCollapse extends BiomeNetherEx
{
    public BiomeRegrowthsCollapse()
    {
        super(NetherEx.instance, new BiomeProperties("Regrowth's Collapse").setTemperature(1.1F).setRainfall(0.0F).setRainDisabled(), "regrowths_collapse");
    }

    @Override
    public BiomeConfigurations getConfigurations()
    {
        return null;
    }
}

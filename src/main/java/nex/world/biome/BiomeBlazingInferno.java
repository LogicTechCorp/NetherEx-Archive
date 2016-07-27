package nex.world.biome;

import nex.api.biome.feature.BiomeFeatureGlowStone;
import nex.api.biome.feature.BiomeFeatureMinable;
import nex.registry.ModBlocks;

public class BiomeBlazingInferno extends NetherBiomeBase
{
    public BiomeBlazingInferno(int id, String name, int weight, BiomeProperties properties)
    {
        super(id, name, weight, properties);

        topBlock = ModBlocks.NETHERRACK.getStateFromMeta(1);
        fillerBlock = ModBlocks.NETHERRACK.getStateFromMeta(1);

        biomeFeatures.add(new BiomeFeatureGlowStone(10, 4, 120));
        biomeFeatures.add(new BiomeFeatureMinable(16, 10, 108, ModBlocks.QUARTZ_ORE.getStateFromMeta(1), 14, ModBlocks.NETHERRACK.getStateFromMeta(1)));
    }
}

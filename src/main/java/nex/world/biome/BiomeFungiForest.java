package nex.world.biome;

import nex.api.biome.feature.BiomeFeatureGlowStone;
import nex.api.biome.feature.BiomeFeatureMinable;
import nex.registry.ModBlocks;

public class BiomeFungiForest extends NetherBiomeBase
{
    public BiomeFungiForest(int id, String name, int weight, BiomeProperties properties)
    {
        super(id, name, weight, properties);

        topBlock = ModBlocks.MYCELIUM.getDefaultState();
        fillerBlock = ModBlocks.NETHERRACK.getDefaultState();

        biomeFeatures.add(new BiomeFeatureGlowStone(10, 4, 120));
        biomeFeatures.add(new BiomeFeatureMinable(16, 10, 108, ModBlocks.QUARTZ_ORE.getDefaultState(), 14, ModBlocks.NETHERRACK.getDefaultState()));
    }
}

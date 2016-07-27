package nex.world.biome;

import net.minecraft.init.Blocks;
import nex.api.biome.feature.BiomeFeatureGlowStone;
import nex.api.biome.feature.BiomeFeatureLava;

public class BiomeForgottenSands extends NetherBiomeBase
{
    public BiomeForgottenSands(int id, String name, int weight, BiomeProperties properties)
    {
        super(id, name, weight, properties);

        topBlock = Blocks.SOUL_SAND.getDefaultState();
        fillerBlock = Blocks.SOUL_SAND.getDefaultState();

        biomeFeatures.add(new BiomeFeatureGlowStone(10, 4, 120));
        biomeFeatures.add(new BiomeFeatureLava(4, 4, 120, Blocks.NETHERRACK.getDefaultState(), false));
    }
}

package nex.world.biome;

import net.minecraft.init.Blocks;
import nex.api.biome.feature.BiomeFeatureGlowStone;
import nex.api.biome.feature.BiomeFeatureLava;
import nex.world.biome.feature.BiomeFeatureDungeon;

public class BiomeForgottenSands extends NetherBiomeBase
{
    public BiomeForgottenSands(int id, String name, int weight, BiomeProperties properties)
    {
        super(id, name, weight, properties);

        topBlock = Blocks.SOUL_SAND.getDefaultState();

        biomeFeatures.add(new BiomeFeatureGlowStone(10, 4, 120));
        biomeFeatures.add(new BiomeFeatureLava(4, 4, 120, Blocks.NETHERRACK.getDefaultState(), false));
        biomeFeatures.add(new BiomeFeatureDungeon(1, 28, 68, Blocks.SOUL_SAND.getDefaultState()));
    }
}

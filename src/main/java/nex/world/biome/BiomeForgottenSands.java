package nex.world.biome;

import net.minecraft.init.Blocks;
import nex.api.biome.feature.BiomeFeatureGlowStone;
import nex.api.biome.feature.BiomeFeatureLava;
import nex.entity.hostile.EntityPordenfer;

public class BiomeForgottenSands extends NetherBiomeBase
{
    public BiomeForgottenSands(int id, String name, int weight, BiomeProperties properties)
    {
        super(id, name, weight, properties);

        topBlock = Blocks.SOUL_SAND.getDefaultState();

        spawnableMonsterList.add(new SpawnListEntry(EntityPordenfer.class, 100, 4, 4));

        biomeFeatures.add(new BiomeFeatureGlowStone(10, 4, 120));
        biomeFeatures.add(new BiomeFeatureLava(4, 4, 120, Blocks.NETHERRACK.getDefaultState(), false));
    }
}

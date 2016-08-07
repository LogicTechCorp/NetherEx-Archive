package nex.world.biome;

import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.init.Blocks;
import nex.api.biome.feature.BiomeFeatureFire;
import nex.api.biome.feature.BiomeFeatureGlowStone;
import nex.api.biome.feature.BiomeFeatureLava;
import nex.api.biome.feature.BiomeFeatureMinable;
import nex.entity.passive.EntityTortoise;
import nex.registry.ModBlocks;
import nex.world.biome.feature.BiomeFeaturePool;

public class BiomeBlazingInferno extends NetherBiomeBase
{
    public BiomeBlazingInferno(int id, String name, int weight, BiomeProperties properties)
    {
        super(id, name, weight, properties);

        topBlock = ModBlocks.NETHERRACK.getStateFromMeta(1);
        fillerBlock = ModBlocks.NETHERRACK.getStateFromMeta(1);

        spawnableMonsterList.add(new SpawnListEntry(EntityTortoise.class, 100, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityBlaze.class, 20, 4, 4));

        biomeFeatures.add(new BiomeFeatureGlowStone(10, 4, 120));
        biomeFeatures.add(new BiomeFeatureLava(4, 32, 120, Blocks.NETHERRACK.getDefaultState(), false));
        biomeFeatures.add(new BiomeFeatureLava(8, 10, 108, Blocks.NETHERRACK.getDefaultState(), true));
        biomeFeatures.add(new BiomeFeatureMinable(16, 10, 108, ModBlocks.QUARTZ_ORE.getStateFromMeta(1), 14, ModBlocks.NETHERRACK.getStateFromMeta(1)));
        biomeFeatures.add(new BiomeFeaturePool(4, 32, 120, Blocks.LAVA, ModBlocks.NETHERRACK.getStateFromMeta(1)));
        biomeFeatures.add(new BiomeFeatureFire(4, 4, 120));
    }
}

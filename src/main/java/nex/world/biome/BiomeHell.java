package nex.world.biome;

import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import nex.api.biome.feature.*;
import nex.world.biome.feature.BiomeFeatureDungeon;

public class BiomeHell extends NetherBiomeBase
{
    public BiomeHell(int id, String name, int weight, BiomeProperties properties)
    {
        super(id, name, weight, properties);

        spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 50, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityMagmaCube.class, 1, 4, 4));

        biomeFeatures.add(new BiomeFeatureGlowStone(10, 4, 120));
        biomeFeatures.add(new BiomeFeatureLava(4, 32, 120, Blocks.NETHERRACK.getDefaultState(), false));
        biomeFeatures.add(new BiomeFeatureLava(8, 10, 108, Blocks.NETHERRACK.getDefaultState(), true));
        biomeFeatures.add(new BiomeFeatureMinable(16, 10, 108, Blocks.QUARTZ_ORE.getDefaultState(), 14, Blocks.NETHERRACK.getDefaultState()));
        biomeFeatures.add(new BiomeFeatureMinable(4, 28, 36, Blocks.MAGMA.getDefaultState(), 33, Blocks.NETHERRACK.getDefaultState()));
        biomeFeatures.add(new BiomeFeatureDungeon(1, 28, 68, Blocks.NETHERRACK.getDefaultState()));
        biomeFeatures.add(new BiomeFeatureBush(2, 32, 120, Blocks.RED_MUSHROOM.getDefaultState(), Blocks.NETHERRACK.getDefaultState()));
        biomeFeatures.add(new BiomeFeatureBush(2, 32, 120, Blocks.BROWN_MUSHROOM.getDefaultState(), Blocks.NETHERRACK.getDefaultState()));
        biomeFeatures.add(new BiomeFeatureFire(2, 32, 120));
    }
}

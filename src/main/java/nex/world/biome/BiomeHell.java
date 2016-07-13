package nex.world.biome;

import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import nex.api.biome.NetherBiome;
import nex.api.biome.feature.*;

public class BiomeHell extends NetherBiome
{
    public BiomeHell(BiomeProperties properties)
    {
        super(properties);

        spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 50, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityMagmaCube.class, 1, 4, 4));

        biomeFeatures.add(new BiomeFeatureFire(1, 3, 4, 120));
        biomeFeatures.add(new BiomeFeatureGlowStone(1, 8, 4, 120));
        biomeFeatures.add(new BiomeFeatureMinable(Blocks.QUARTZ_ORE.getDefaultState(), 16, Blocks.NETHERRACK.getDefaultState(), 2, 8, 10, 108));
        biomeFeatures.add(new BiomeFeatureMinable(Blocks.MAGMA.getDefaultState(), 24, Blocks.NETHERRACK.getDefaultState(), 2, 4, 28, 36));
        biomeFeatures.add(new BiomeFeatureLava(Blocks.NETHERRACK, true, 1, 8, 10, 108));
        biomeFeatures.add(new BiomeFeatureLava(Blocks.NETHERRACK, false, 1, 4, 4, 120));
        biomeFeatures.add(new BiomeFeatureBush(Blocks.RED_MUSHROOM, 2, 1, 4, 120));
        biomeFeatures.add(new BiomeFeatureBush(Blocks.BROWN_MUSHROOM, 2, 1, 4, 120));
    }
}

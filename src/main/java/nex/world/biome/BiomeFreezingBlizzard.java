package nex.world.biome;

import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.init.Blocks;
import nex.api.biome.feature.BiomeFeatureGlowStone;
import nex.api.biome.feature.BiomeFeatureMinable;
import nex.registry.ModBlocks;
import nex.world.biome.feature.BiomeFeatureDungeon;

public class BiomeFreezingBlizzard extends NetherBiomeBase
{
    public BiomeFreezingBlizzard(int id, String name, int weight, BiomeProperties properties)
    {
        super(id, name, weight, properties);

        topBlock = Blocks.PACKED_ICE.getDefaultState();
        fillerBlock = ModBlocks.NETHERRACK.getStateFromMeta(2);
        oceanBlock = Blocks.MAGMA.getDefaultState();

        spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 100, 1, 4));

        biomeFeatures.add(new BiomeFeatureGlowStone(10, 4, 120));
        biomeFeatures.add(new BiomeFeatureMinable(16, 10, 108, ModBlocks.QUARTZ_ORE.getStateFromMeta(2), 14, ModBlocks.NETHERRACK.getStateFromMeta(2)));
        biomeFeatures.add(new BiomeFeatureDungeon(1, 33, 60, ModBlocks.NETHERRACK.getStateFromMeta(2)));
    }
}

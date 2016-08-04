package nex.world.biome;

import net.minecraft.init.Blocks;
import nex.api.biome.feature.BiomeFeatureBush;
import nex.api.biome.feature.BiomeFeatureGlowStone;
import nex.api.biome.feature.BiomeFeatureMinable;
import nex.entity.passive.EntityBrownMogus;
import nex.entity.passive.EntityRedMogus;
import nex.registry.ModBlocks;
import nex.world.biome.feature.BiomeFeatureBigMushroom;
import nex.world.biome.feature.BiomeFeatureDungeon;
import nex.world.biome.feature.BiomeFeatureEnoki;

public class BiomeFungiForest extends NetherBiomeBase
{
    public BiomeFungiForest(int id, String name, int weight, BiomeProperties properties)
    {
        super(id, name, weight, properties);

        topBlock = ModBlocks.MYCELIUM.getDefaultState();
        fillerBlock = ModBlocks.NETHERRACK.getDefaultState();

        spawnableMonsterList.add(new SpawnListEntry(EntityRedMogus.class, 100, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityBrownMogus.class, 100, 4, 4));

        biomeFeatures.add(new BiomeFeatureGlowStone(10, 4, 120));
        biomeFeatures.add(new BiomeFeatureEnoki(6, 64, 120));
        biomeFeatures.add(new BiomeFeatureMinable(16, 10, 108, ModBlocks.QUARTZ_ORE.getDefaultState(), 14, ModBlocks.NETHERRACK.getDefaultState()));
        biomeFeatures.add(new BiomeFeatureDungeon(1, 33, 60, ModBlocks.NETHERRACK.getDefaultState()));
        biomeFeatures.add(new BiomeFeatureBigMushroom(256, 32, 120, Blocks.RED_MUSHROOM_BLOCK, ModBlocks.MYCELIUM.getDefaultState()));
        biomeFeatures.add(new BiomeFeatureBigMushroom(256, 32, 120, Blocks.BROWN_MUSHROOM_BLOCK, ModBlocks.MYCELIUM.getDefaultState()));
        biomeFeatures.add(new BiomeFeatureBush(8, 32, 120, Blocks.RED_MUSHROOM.getDefaultState(), ModBlocks.MYCELIUM.getDefaultState()));
        biomeFeatures.add(new BiomeFeatureBush(8, 32, 120, Blocks.BROWN_MUSHROOM.getDefaultState(), ModBlocks.MYCELIUM.getDefaultState()));
    }
}

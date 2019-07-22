/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package logictechcorp.netherex.world.biome;

import logictechcorp.libraryex.api.world.biome.data.IBiomeData;
import logictechcorp.libraryex.world.biome.BiomeBlock;
import logictechcorp.libraryex.world.biome.data.BiomeData;
import logictechcorp.libraryex.world.generation.GenerationStage;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitBigMushroom;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitCluster;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitOre;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitStructure;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.monster.EntitySpore;
import logictechcorp.netherex.entity.monster.EntitySporeCreeper;
import logictechcorp.netherex.entity.neutral.EntityMogus;
import logictechcorp.netherex.init.NetherExBiomes;
import logictechcorp.netherex.init.NetherExBlocks;
import logictechcorp.netherex.world.generation.trait.BiomeTraitEnoki;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.Biome;

import java.util.Arrays;
import java.util.Collections;

public class BiomeFungiForest extends BiomeNetherEx
{
    private static final IBlockState HYPHAE = NetherExBlocks.HYPHAE.getDefaultState();
    private static final IBlockState LIVELY_NETHERRACK = NetherExBlocks.LIVELY_NETHERRACK.getDefaultState();

    public BiomeFungiForest()
    {
        super(NetherEx.instance, new BiomeProperties("Fungi Forest").setTemperature(1.1F).setRainfall(0.0F).setRainDisabled(), "fungi_forest");
        this.topBlock = HYPHAE;
        this.fillerBlock = LIVELY_NETHERRACK;
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityMogus.class, 100, 4, 6));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySpore.class, 25, 1, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySporeCreeper.class, 50, 1, 4));
    }

    @Override
    public IBiomeData getBiomeData()
    {
        IBiomeData biomeData = new BiomeData(NetherExBiomes.FUNGI_FOREST, 4, false, true, true);
        biomeData.getBiomeBlock(BiomeBlock.FLOOR_TOP_BLOCK, NetherExBlocks.HYPHAE.getDefaultState());
        biomeData.getBiomeBlock(BiomeBlock.FLOOR_FILLER_BLOCK, LIVELY_NETHERRACK);
        biomeData.getBiomeBlock(BiomeBlock.WALL_BLOCK, LIVELY_NETHERRACK);
        biomeData.getBiomeBlock(BiomeBlock.CEILING_FILLER_BLOCK, LIVELY_NETHERRACK);
        biomeData.getBiomeBlock(BiomeBlock.CEILING_BOTTOM_BLOCK, LIVELY_NETHERRACK);
        biomeData.getBiomeBlock(BiomeBlock.OCEAN_BLOCK, LAVA);
        biomeData.getBiomeTraits(GenerationStage.DECORATION).addAll(Arrays.asList(
                BiomeTraitCluster.create(trait ->
                {
                    trait.generationAttempts(10);
                    trait.randomizeGenerationAttempts(true);
                    trait.minimumGenerationHeight(4);
                    trait.maximumGenerationHeight(124);
                    trait.blockToAttachTo(Blocks.NETHERRACK.getDefaultState());
                    trait.direction(EnumFacing.DOWN);
                }),
                BiomeTraitCluster.create(trait ->
                {
                    trait.generationAttempts(10);
                    trait.minimumGenerationHeight(1);
                    trait.maximumGenerationHeight(128);
                    trait.blockToAttachTo(Blocks.NETHERRACK.getDefaultState());
                    trait.direction(EnumFacing.DOWN);
                }),
                BiomeTraitBigMushroom.create(trait ->
                {
                    trait.generationAttempts(256);
                    trait.minimumGenerationHeight(32);
                    trait.maximumGenerationHeight(108);
                    trait.mushroomCap(NetherExBlocks.BROWN_ELDER_MUSHROOM_CAP.getDefaultState());
                    trait.mushroomStem(NetherExBlocks.ELDER_MUSHROOM_STEM.getDefaultState());
                    trait.blockToPlaceOn(HYPHAE);
                    trait.shape(BiomeTraitBigMushroom.Shape.FLAT);
                }),
                BiomeTraitBigMushroom.create(trait ->
                {
                    trait.generationAttempts(256);
                    trait.minimumGenerationHeight(32);
                    trait.maximumGenerationHeight(108);
                    trait.mushroomCap(NetherExBlocks.RED_ELDER_MUSHROOM_CAP.getDefaultState());
                    trait.mushroomStem(NetherExBlocks.ELDER_MUSHROOM_STEM.getDefaultState());
                    trait.blockToPlaceOn(HYPHAE);
                    trait.shape(BiomeTraitBigMushroom.Shape.BULB);
                }),
                BiomeTraitEnoki.create(trait ->
                {
                    trait.generationAttempts(32);
                    trait.minimumGenerationHeight(48);
                    trait.maximumGenerationHeight(118);
                })
        ));
        biomeData.getBiomeTraits(GenerationStage.ORE).add(
                BiomeTraitOre.create(trait ->
                {
                    trait.generationAttempts(16);
                    trait.minimumGenerationHeight(10);
                    trait.maximumGenerationHeight(108);
                    trait.blockToSpawn(NetherExBlocks.QUARTZ_ORE.getDefaultState());
                    trait.blockToReplace(Blocks.NETHERRACK.getDefaultState());
                    trait.veinSize(14);
                })
        );
        biomeData.getBiomeTraits(GenerationStage.STRUCTURE).add(
                BiomeTraitStructure.create(trait ->
                {
                    trait.generationAttempts(1);
                    trait.generationProbability(0.0125D);
                    trait.minimumGenerationHeight(32);
                    trait.maximumGenerationHeight(118);
                    trait.structures(Collections.singletonList(NetherEx.getResource("ghast_queen_shrine")));
                    trait.structureType(BiomeTraitStructure.StructureType.AIR);
                    trait.clearancePercentage(1.0D);
                })
        );
        return biomeData;
    }
}

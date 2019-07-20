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
import logictechcorp.libraryex.world.generation.trait.*;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.monster.EntityEmber;
import logictechcorp.netherex.entity.neutral.EntitySalamander;
import logictechcorp.netherex.init.NetherExBiomes;
import logictechcorp.netherex.init.NetherExBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.Biome;

import java.util.Arrays;

public class BiomeTorridWasteland extends BiomeNetherEx
{
    private static final IBlockState FIERY_NETHERRACK = NetherExBlocks.FIERY_NETHERRACK.getDefaultState();

    public BiomeTorridWasteland()
    {
        super(NetherEx.instance, new BiomeProperties("Torrid Wasteland").setTemperature(4.0F).setRainfall(0.0F).setRainDisabled(), "torrid_wasteland");
        this.topBlock = FIERY_NETHERRACK;
        this.fillerBlock = FIERY_NETHERRACK;
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityMagmaCube.class, 25, 1, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEmber.class, 50, 4, 6));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySalamander.class, 100, 1, 4));
    }

    @Override
    public IBiomeData getBiomeData()
    {
        IBiomeData biomeData = new BiomeData(NetherExBiomes.TORRID_WASTELAND, 6, false, true, true);
        biomeData.getBiomeBlock(BiomeBlock.FLOOR_TOP_BLOCK, FIERY_NETHERRACK);
        biomeData.getBiomeBlock(BiomeBlock.FLOOR_FILLER_BLOCK, FIERY_NETHERRACK);
        biomeData.getBiomeBlock(BiomeBlock.WALL_BLOCK, FIERY_NETHERRACK);
        biomeData.getBiomeBlock(BiomeBlock.CEILING_FILLER_BLOCK, FIERY_NETHERRACK);
        biomeData.getBiomeBlock(BiomeBlock.CEILING_BOTTOM_BLOCK, FIERY_NETHERRACK);
        biomeData.getBiomeBlock(BiomeBlock.OCEAN_BLOCK, LAVA);
        biomeData.getBiomeTraits(GenerationStage.TERRAIN_ALTERATION).add(
                BiomeTraitPool.create(trait ->
                {
                    trait.generationAttempts(8);
                    trait.minimumGenerationHeight(10);
                    trait.maximumGenerationHeight(108);
                    trait.blockToSpawn(Blocks.FLOWING_LAVA.getDefaultState());
                    trait.blockToSurround(FIERY_NETHERRACK);
                })
        );
        biomeData.getBiomeTraits(GenerationStage.DECORATION).addAll(Arrays.asList(
                BiomeTraitFluid.create(trait ->
                {
                    trait.generationAttempts(16);
                    trait.minimumGenerationHeight(4);
                    trait.maximumGenerationHeight(124);
                    trait.blockToSpawn(Blocks.FLOWING_LAVA.getDefaultState());
                    trait.blockToTarget(FIERY_NETHERRACK);
                    trait.generateFalling(false);
                }),
                BiomeTraitScatter.create(trait ->
                {
                    trait.generationAttempts(20);
                    trait.randomizeGenerationAttempts(true);
                    trait.minimumGenerationHeight(4);
                    trait.maximumGenerationHeight(124);
                    trait.blockToSpawn(Blocks.FIRE.getDefaultState());
                    trait.blockToTarget(FIERY_NETHERRACK);
                    trait.placement(BiomeTraitScatter.Placement.ON_GROUND);
                }),
                BiomeTraitCluster.create(trait ->
                {
                    trait.generationAttempts(10);
                    trait.randomizeGenerationAttempts(true);
                    trait.minimumGenerationHeight(4);
                    trait.maximumGenerationHeight(124);
                    trait.blockToAttachTo(FIERY_NETHERRACK);
                    trait.direction(EnumFacing.DOWN);
                }),
                BiomeTraitCluster.create(trait ->
                {
                    trait.generationAttempts(10);
                    trait.minimumGenerationHeight(1);
                    trait.maximumGenerationHeight(128);
                    trait.blockToAttachTo(FIERY_NETHERRACK);
                    trait.direction(EnumFacing.DOWN);
                }),
                BiomeTraitFluid.create(trait ->
                {
                    trait.generationAttempts(32);
                    trait.minimumGenerationHeight(10);
                    trait.maximumGenerationHeight(118);
                    trait.blockToSpawn(Blocks.FLOWING_LAVA.getDefaultState());
                    trait.blockToTarget(FIERY_NETHERRACK);
                    trait.generateFalling(true);
                })
        ));
        biomeData.getBiomeTraits(GenerationStage.ORE).addAll(Arrays.asList(
                BiomeTraitOre.create(trait ->
                {
                    trait.generationAttempts(16);
                    trait.minimumGenerationHeight(10);
                    trait.maximumGenerationHeight(108);
                    trait.blockToSpawn(NetherExBlocks.QUARTZ_ORE.getDefaultState());
                    trait.blockToReplace(FIERY_NETHERRACK);
                    trait.veinSize(14);
                }),
                BiomeTraitOre.create(trait ->
                {
                    trait.generationAttempts(16);
                    trait.minimumGenerationHeight(10);
                    trait.maximumGenerationHeight(108);
                    trait.blockToSpawn(NetherExBlocks.BASALT.getDefaultState());
                    trait.blockToReplace(FIERY_NETHERRACK);
                    trait.veinSize(24);
                }),
                BiomeTraitOre.create(trait ->
                {
                    trait.generationAttempts(8);
                    trait.minimumGenerationHeight(10);
                    trait.maximumGenerationHeight(108);
                    trait.blockToSpawn(Blocks.MAGMA.getDefaultState());
                    trait.blockToReplace(FIERY_NETHERRACK);
                    trait.veinSize(32);
                })
        ));
        return biomeData;
    }
}

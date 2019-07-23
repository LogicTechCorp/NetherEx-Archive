/*
 * LibraryEx
 * Copyright (c) 2017-2019 by LogicTechCorp
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

package logictechcorp.netherex.world.biome.data;

import logictechcorp.libraryex.api.world.biome.data.IBiomeData;
import logictechcorp.libraryex.world.biome.BiomeBlock;
import logictechcorp.libraryex.world.biome.data.BiomeData;
import logictechcorp.libraryex.world.generation.GenerationStage;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitCluster;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitFluid;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitOre;
import logictechcorp.libraryex.world.generation.trait.BiomeTraitScatter;
import logictechcorp.netherex.init.NetherExBlocks;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

import java.util.Arrays;

public final class BiomeDataHell extends BiomeData
{
    public static final IBiomeData INSTANCE = new BiomeDataHell();

    private BiomeDataHell()
    {
        super(Biomes.HELL, 10, false, true, true);
        this.getBiomeBlock(BiomeBlock.FLOOR_TOP_BLOCK, Blocks.NETHERRACK.getDefaultState());
        this.getBiomeBlock(BiomeBlock.FLOOR_FILLER_BLOCK, Blocks.NETHERRACK.getDefaultState());
        this.getBiomeBlock(BiomeBlock.WALL_BLOCK, Blocks.NETHERRACK.getDefaultState());
        this.getBiomeBlock(BiomeBlock.CEILING_FILLER_BLOCK, Blocks.NETHERRACK.getDefaultState());
        this.getBiomeBlock(BiomeBlock.CEILING_BOTTOM_BLOCK, Blocks.NETHERRACK.getDefaultState());
        this.getBiomeBlock(BiomeBlock.OCEAN_BLOCK, Blocks.LAVA.getDefaultState());
        this.getBiomeTraits(GenerationStage.DECORATION).addAll(Arrays.asList(
                BiomeTraitFluid.create(trait ->
                {
                    trait.generationAttempts(8);
                    trait.minimumGenerationHeight(4);
                    trait.maximumGenerationHeight(124);
                    trait.blockToSpawn(Blocks.FLOWING_LAVA.getDefaultState());
                    trait.blockToTarget(Blocks.NETHERRACK.getDefaultState());
                    trait.generateFalling(false);
                }),
                BiomeTraitScatter.create(trait ->
                {
                    trait.generationAttempts(10);
                    trait.randomizeGenerationAttempts(true);
                    trait.minimumGenerationHeight(4);
                    trait.maximumGenerationHeight(124);
                    trait.blockToSpawn(Blocks.FIRE.getDefaultState());
                    trait.blockToTarget(Blocks.NETHERRACK.getDefaultState());
                    trait.placement(BiomeTraitScatter.Placement.ON_GROUND);
                }),
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
                BiomeTraitFluid.create(trait ->
                {
                    trait.generationAttempts(16);
                    trait.minimumGenerationHeight(10);
                    trait.maximumGenerationHeight(118);
                    trait.blockToSpawn(Blocks.FLOWING_LAVA.getDefaultState());
                    trait.blockToTarget(Blocks.NETHERRACK.getDefaultState());
                    trait.generateFalling(true);
                })
        ));
        this.getBiomeTraits(GenerationStage.PLANT_DECORATION).addAll(Arrays.asList(
                BiomeTraitScatter.create(trait ->
                {
                    trait.generationAttempts(1);
                    trait.generationProbability(0.25D);
                    trait.minimumGenerationHeight(1);
                    trait.maximumGenerationHeight(128);
                    trait.blockToSpawn(Blocks.BROWN_MUSHROOM.getDefaultState());
                    trait.blockToTarget(Blocks.NETHERRACK.getDefaultState());
                    trait.placement(BiomeTraitScatter.Placement.ON_GROUND);
                }),
                BiomeTraitScatter.create(trait ->
                {
                    trait.generationAttempts(1);
                    trait.generationProbability(0.25D);
                    trait.minimumGenerationHeight(1);
                    trait.maximumGenerationHeight(128);
                    trait.blockToSpawn(Blocks.RED_MUSHROOM.getDefaultState());
                    trait.blockToTarget(Blocks.NETHERRACK.getDefaultState());
                    trait.placement(BiomeTraitScatter.Placement.ON_GROUND);
                })
        ));
        this.getBiomeTraits(GenerationStage.ORE).addAll(Arrays.asList(
                BiomeTraitOre.create(trait ->
                {
                    trait.generationAttempts(16);
                    trait.minimumGenerationHeight(10);
                    trait.maximumGenerationHeight(108);
                    trait.blockToSpawn(Blocks.QUARTZ_ORE.getDefaultState());
                    trait.blockToReplace(Blocks.NETHERRACK.getDefaultState());
                    trait.veinSize(14);
                }),
                BiomeTraitOre.create(trait ->
                {
                    trait.generationAttempts(8);
                    trait.minimumGenerationHeight(10);
                    trait.maximumGenerationHeight(108);
                    trait.blockToSpawn(NetherExBlocks.AMETHYST_ORE.getDefaultState());
                    trait.blockToReplace(Blocks.NETHERRACK.getDefaultState());
                    trait.veinSize(7);
                }),
                BiomeTraitOre.create(trait ->
                {
                    trait.generationAttempts(4);
                    trait.minimumGenerationHeight(28);
                    trait.maximumGenerationHeight(38);
                    trait.blockToSpawn(Blocks.MAGMA.getDefaultState());
                    trait.blockToReplace(Blocks.NETHERRACK.getDefaultState());
                    trait.veinSize(32);
                })
        ));

        this.updateDefaults();
    }
}

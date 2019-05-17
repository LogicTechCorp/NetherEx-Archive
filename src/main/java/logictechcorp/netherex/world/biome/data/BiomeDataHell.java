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

import logictechcorp.libraryex.world.biome.data.iface.IBiomeData;
import logictechcorp.libraryex.world.biome.data.impl.BiomeBlock;
import logictechcorp.libraryex.world.biome.data.impl.BiomeData;
import logictechcorp.libraryex.world.generation.impl.GenerationStage;
import logictechcorp.libraryex.world.generation.trait.impl.BiomeTraitCluster;
import logictechcorp.libraryex.world.generation.trait.impl.BiomeTraitFluid;
import logictechcorp.libraryex.world.generation.trait.impl.BiomeTraitOre;
import logictechcorp.libraryex.world.generation.trait.impl.BiomeTraitScatter;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.Biome;

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
        this.getBiomeEntities(EnumCreatureType.MONSTER).addAll(Arrays.asList(
                new Biome.SpawnListEntry(EntityGhast.class, 50, 1, 4),
                new Biome.SpawnListEntry(EntityPigZombie.class, 100, 1, 4),
                new Biome.SpawnListEntry(EntityMagmaCube.class, 3, 1, 4),
                new Biome.SpawnListEntry(EntityEnderman.class, 1, 1, 4)
        ));
        this.getBiomeTraits(GenerationStage.PRE_DECORATE).addAll(Arrays.asList(
                BiomeTraitFluid.create(trait -> {
                    trait.generationAttempts(8);
                    trait.minimumGenerationHeight(4);
                    trait.maximumGenerationHeight(124);
                    trait.blockToSpawn(Blocks.FLOWING_LAVA.getDefaultState());
                    trait.blockToSpawn(Blocks.NETHERRACK.getDefaultState());
                    trait.generateFalling(false);
                }),
                BiomeTraitScatter.create(trait -> {
                    trait.generationAttempts(10);
                    trait.randomizeGenerationAttempts(true);
                    trait.minimumGenerationHeight(4);
                    trait.maximumGenerationHeight(124);
                    trait.blockToSpawn(Blocks.FIRE.getDefaultState());
                    trait.blockToSpawn(Blocks.NETHERRACK.getDefaultState());
                    trait.placement(BiomeTraitScatter.Placement.ON_GROUND);
                }),
                BiomeTraitCluster.create(trait -> {
                    trait.generationAttempts(10);
                    trait.randomizeGenerationAttempts(true);
                    trait.minimumGenerationHeight(4);
                    trait.maximumGenerationHeight(124);
                    trait.blockToSpawn(Blocks.GLOWSTONE.getDefaultState());
                    trait.blockToSpawn(Blocks.NETHERRACK.getDefaultState());
                    trait.direction(EnumFacing.DOWN);
                }),
                BiomeTraitCluster.create(trait -> {
                    trait.generationAttempts(10);
                    trait.minimumGenerationHeight(1);
                    trait.maximumGenerationHeight(128);
                    trait.blockToSpawn(Blocks.GLOWSTONE.getDefaultState());
                    trait.blockToSpawn(Blocks.NETHERRACK.getDefaultState());
                    trait.direction(EnumFacing.DOWN);
                }),
                BiomeTraitFluid.create(trait -> {
                    trait.generationAttempts(16);
                    trait.minimumGenerationHeight(10);
                    trait.maximumGenerationHeight(118);
                    trait.blockToSpawn(Blocks.FLOWING_LAVA.getDefaultState());
                    trait.blockToSpawn(Blocks.NETHERRACK.getDefaultState());
                    trait.generateFalling(true);
                })
        ));
        this.getBiomeTraits(GenerationStage.DECORATE).addAll(Arrays.asList(
                BiomeTraitScatter.create(trait -> {
                    trait.generationAttempts(1);
                    trait.generationProbability(0.25D);
                    trait.minimumGenerationHeight(1);
                    trait.maximumGenerationHeight(128);
                    trait.blockToSpawn(Blocks.BROWN_MUSHROOM.getDefaultState());
                    trait.blockToSpawn(Blocks.NETHERRACK.getDefaultState());
                    trait.placement(BiomeTraitScatter.Placement.ON_GROUND);
                }),
                BiomeTraitScatter.create(trait -> {
                    trait.generationAttempts(1);
                    trait.generationProbability(0.25D);
                    trait.minimumGenerationHeight(1);
                    trait.maximumGenerationHeight(128);
                    trait.blockToSpawn(Blocks.RED_MUSHROOM.getDefaultState());
                    trait.blockToSpawn(Blocks.NETHERRACK.getDefaultState());
                    trait.placement(BiomeTraitScatter.Placement.ON_GROUND);
                })
        ));
        this.getBiomeTraits(GenerationStage.ORE).addAll(Arrays.asList(
                BiomeTraitOre.create(trait -> {
                    trait.generationAttempts(16);
                    trait.minimumGenerationHeight(10);
                    trait.maximumGenerationHeight(108);
                    trait.blockToSpawn(Blocks.QUARTZ_ORE.getDefaultState());
                    trait.blockToSpawn(Blocks.NETHERRACK.getDefaultState());
                    trait.veinSize(14);
                }),
                BiomeTraitOre.create(trait -> {
                    trait.generationAttempts(4);
                    trait.minimumGenerationHeight(10);
                    trait.maximumGenerationHeight(108);
                    trait.blockToSpawn(Blocks.MAGMA.getDefaultState());
                    trait.blockToSpawn(Blocks.NETHERRACK.getDefaultState());
                    trait.veinSize(32);
                })
        ));
    }
}

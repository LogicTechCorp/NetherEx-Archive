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

import logictechcorp.libraryex.world.biome.data.impl.BiomeBlock;
import logictechcorp.libraryex.world.generation.GenerationStage;
import logictechcorp.libraryex.world.generation.trait.impl.*;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.monster.EntityEmber;
import logictechcorp.netherex.entity.neutral.EntitySalamander;
import logictechcorp.netherex.init.NetherExBiomes;
import logictechcorp.netherex.init.NetherExBlocks;
import logictechcorp.netherex.world.biome.data.BiomeDataNetherEx;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
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
    public BiomeData getBiomeData()
    {
        return new BiomeData();
    }

    private class BiomeData extends BiomeDataNetherEx
    {
        BiomeData()
        {
            super(NetherExBiomes.TORRID_WASTELAND, 6, true, true);
            this.getBiomeBlock(BiomeBlock.FLOOR_TOP_BLOCK, FIERY_NETHERRACK);
            this.getBiomeBlock(BiomeBlock.FLOOR_FILLER_BLOCK, FIERY_NETHERRACK);
            this.getBiomeBlock(BiomeBlock.WALL_BLOCK, FIERY_NETHERRACK);
            this.getBiomeBlock(BiomeBlock.CEILING_FILLER_BLOCK, FIERY_NETHERRACK);
            this.getBiomeBlock(BiomeBlock.CEILING_BOTTOM_BLOCK, FIERY_NETHERRACK);
            this.getBiomeBlock(BiomeBlock.OCEAN_BLOCK, LAVA);
            this.getBiomeEntities(EnumCreatureType.MONSTER).addAll(new ArrayList<>(Arrays.asList(
                    new Biome.SpawnListEntry(EntitySalamander.class, 100, 1, 4),
                    new Biome.SpawnListEntry(EntityEmber.class, 50, 4, 6),
                    new Biome.SpawnListEntry(EntityMagmaCube.class, 25, 1, 4),
                    new Biome.SpawnListEntry(EntityBlaze.class, 3, 1, 4)
            )));
            this.getBiomeTraits(GenerationStage.PRE_DECORATE).addAll(new ArrayList<>(Arrays.asList(
                    new BiomeTraitFluid(16, false, 1.0D, 4, 124, FLOWING_LAVA, FIERY_NETHERRACK, false),
                    new BiomeTraitScatter(20, true, 1.0D, 4, 124, FIRE, FIERY_NETHERRACK, BiomeTraitScatter.Placement.ON_GROUND),
                    new BiomeTraitCluster(10, true, 1.0D, 4, 124, GLOWSTONE, FIERY_NETHERRACK, EnumFacing.DOWN),
                    new BiomeTraitCluster(10, false, 1.0D, 1, 128, GLOWSTONE, FIERY_NETHERRACK, EnumFacing.DOWN),
                    new BiomeTraitFluid(32, false, 1.0D, 10, 118, FLOWING_LAVA, FIERY_NETHERRACK, true),
                    new BiomeTraitPool(8, false, 1.0, 10, 108, LAVA, FIERY_NETHERRACK)
            )));
            this.getBiomeTraits(GenerationStage.ORE).addAll(new ArrayList<>(Arrays.asList(
                    new BiomeTraitOre(16, false, 1.0D, 10, 108, NetherExBlocks.QUARTZ_ORE.getDefaultState(), FIERY_NETHERRACK, 14),
                    new BiomeTraitOre(14, false, 1.0D, 10, 108, NetherExBlocks.BASALT.getDefaultState(), FIERY_NETHERRACK, 24),
                    new BiomeTraitOre(8, false, 1.0D, 28, 38, Blocks.MAGMA.getDefaultState(), FIERY_NETHERRACK, 32)
            )));
        }
    }
}

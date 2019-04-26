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
import logictechcorp.netherex.init.NetherExBiomes;
import logictechcorp.netherex.init.NetherExBlocks;
import logictechcorp.netherex.world.biome.data.BiomeDataNetherEx;
import logictechcorp.netherex.world.generation.trait.BiomeTraitThornstalk;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.Arrays;

public class BiomeRuthlessSands extends BiomeNetherEx
{
    private static final IBlockState SOUL_SANDSTONE = NetherExBlocks.SOUL_SANDSTONE.getDefaultState();
    private static final IBlockState GLOOMY_NETHERRACK = NetherExBlocks.GLOOMY_NETHERRACK.getDefaultState();
    private static final IBlockState HAUNTED_SOUL_SAND = NetherExBlocks.HAUNTED_SOUL_SAND.getDefaultState();

    public BiomeRuthlessSands()
    {
        super(NetherEx.instance, new BiomeProperties("Ruthless Sands").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled(), "ruthless_sands");
        this.topBlock = SOUL_SAND;
        this.fillerBlock = SOUL_SAND;
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
            super(NetherExBiomes.RUTHLESS_SANDS, 8, true, true);
            this.getBiomeBlock(BiomeBlock.FLOOR_TOP_BLOCK, SOUL_SAND);
            this.getBiomeBlock(BiomeBlock.FLOOR_FILLER_BLOCK, SOUL_SAND);
            this.getBiomeBlock(BiomeBlock.WALL_BLOCK, GLOOMY_NETHERRACK);
            this.getBiomeBlock(BiomeBlock.CEILING_FILLER_BLOCK, GLOOMY_NETHERRACK);
            this.getBiomeBlock(BiomeBlock.CEILING_BOTTOM_BLOCK, GLOOMY_NETHERRACK);
            this.getBiomeBlock(BiomeBlock.OCEAN_BLOCK, LAVA);
            this.getBiomeTraits(GenerationStage.PRE_DECORATE).addAll(new ArrayList<>(Arrays.asList(
                    new BiomeTraitPatch(4, false, 0.85D, 32, 124, SOUL_SANDSTONE, SOUL_SAND, 6),
                    new BiomeTraitPatch(2, false, 0.85D, 32, 124, HAUNTED_SOUL_SAND, SOUL_SAND, 3)
            )));
            this.getBiomeTraits(GenerationStage.DECORATE).addAll(new ArrayList<>(Arrays.asList(
                    new BiomeTraitCluster(10, true, 1.0D, 4, 124, GLOWSTONE, GLOOMY_NETHERRACK, EnumFacing.DOWN),
                    new BiomeTraitCluster(10, false, 1.0D, 1, 128, GLOWSTONE, GLOOMY_NETHERRACK, EnumFacing.DOWN),
                    new BiomeTraitThornstalk(12, false, 0.85D, 32, 120),
                    new BiomeTraitBoulder(4, false, 0.75D, 32, 124, SOUL_SANDSTONE, SOUL_SAND, 0),
                    new BiomeTraitBoulder(2, false, 0.75D, 32, 124, SOUL_SANDSTONE, SOUL_SAND, 1),
                    new BiomeTraitDenseTree(68, false, 1.0F, 32, 108, NetherExBlocks.SPOUL_SHROOM_STEM.getDefaultState(), NetherExBlocks.SPOUL_SHROOM_CAP.getDefaultState(), SOUL_SAND, 3, 8),
                    new BiomeTraitSparseTree(68, false, 1.0F, 32, 108, NetherExBlocks.SPOUL_SHROOM_STEM.getDefaultState(), NetherExBlocks.SPOUL_SHROOM_CAP.getDefaultState(), SOUL_SAND, 4, 12),
                    new BiomeTraitScatter(36, false, 1.0D, 32, 120, NetherExBlocks.SPOUL_SHROOM.getDefaultState(), SOUL_SAND, BiomeTraitScatter.Placement.ON_GROUND),
                    new BiomeTraitScatter(128, false, 1.0D, 32, 120, NetherExBlocks.SPOUL_VINES.getDefaultState(), NetherExBlocks.SPOUL_SHROOM_CAP.getDefaultState(), BiomeTraitScatter.Placement.ON_ROOF)
            )));
        }
    }
}

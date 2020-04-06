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

package logictechcorp.netherex.block;

import logictechcorp.netherex.world.generation.feature.NetherExFeatureConfigs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.PlantType;

import java.util.Random;

public class ElderMushroomBlock extends MushroomBlock
{
    public ElderMushroomBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean func_226940_a_(ServerWorld world, BlockPos pos, BlockState state, Random random)
    {
        world.removeBlock(pos, false);

        ConfiguredFeature<BigMushroomFeatureConfig, ?> feature;

        if(this == NetherExBlocks.BROWN_ELDER_MUSHROOM.get())
        {
            feature = Feature.HUGE_BROWN_MUSHROOM.withConfiguration(NetherExFeatureConfigs.HUGE_BROWN_ELDER_MUSHROOM);
        }
        else
        {
            if(this != NetherExBlocks.RED_ELDER_MUSHROOM.get())
            {
                world.setBlockState(pos, state, 3);
                return false;
            }

            feature = Feature.HUGE_RED_MUSHROOM.withConfiguration(NetherExFeatureConfigs.HUGE_RED_ELDER_MUSHROOM);
        }

        if(feature.place(world, world.getChunkProvider().getChunkGenerator(), random, pos))
        {
            return true;
        }
        else
        {
            world.setBlockState(pos, state, 3);
            return false;
        }
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos)
    {
        BlockPos posDown = pos.down();
        BlockState stateDown = world.getBlockState(posDown);
        Block blockDown = stateDown.getBlock();

        if(blockDown != Blocks.MYCELIUM && blockDown != Blocks.PODZOL)
        {
            return stateDown.canSustainPlant(world, posDown, Direction.UP, this);
        }
        else
        {
            return true;
        }
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos)
    {
        return PlantType.Cave;
    }
}

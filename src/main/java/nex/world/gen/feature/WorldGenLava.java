/*
 * Copyright (C) 2016.  LogicTechCorp
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

package nex.world.gen.feature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenLava extends WorldGenerator
{
    private final IBlockState blockToPLaceOn;
    private final boolean insideRock;

    public WorldGenLava(IBlockState blockToPLaceOnIn, boolean insideRockIn)
    {
        blockToPLaceOn = blockToPLaceOnIn;
        insideRock = insideRockIn;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        if(world.getBlockState(pos.up()) != blockToPLaceOn)
        {
            return false;
        }
        else if(!world.isAirBlock(pos) && world.getBlockState(pos) != blockToPLaceOn)
        {
            return false;
        }
        else
        {
            int i = 0;

            if(world.getBlockState(pos.west()) == blockToPLaceOn)
            {
                ++i;
            }

            if(world.getBlockState(pos.east()) == blockToPLaceOn)
            {
                ++i;
            }

            if(world.getBlockState(pos.north()) == blockToPLaceOn)
            {
                ++i;
            }

            if(world.getBlockState(pos.south()) == blockToPLaceOn)
            {
                ++i;
            }

            if(world.getBlockState(pos.down()) == blockToPLaceOn)
            {
                ++i;
            }

            int j = 0;

            if(world.isAirBlock(pos.west()))
            {
                ++j;
            }

            if(world.isAirBlock(pos.east()))
            {
                ++j;
            }

            if(world.isAirBlock(pos.north()))
            {
                ++j;
            }

            if(world.isAirBlock(pos.south()))
            {
                ++j;
            }

            if(world.isAirBlock(pos.down()))
            {
                ++j;
            }

            if(!insideRock && i == 4 && j == 1 || i == 5)
            {
                IBlockState state = Blocks.FLOWING_LAVA.getDefaultState();
                world.setBlockState(pos, state, 2);
                world.immediateBlockTick(pos, state, rand);
            }

            return true;
        }
    }
}
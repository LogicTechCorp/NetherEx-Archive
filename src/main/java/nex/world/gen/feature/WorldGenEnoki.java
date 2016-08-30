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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import nex.block.BlockEnokiCap;
import nex.init.ModBlocks;

import java.util.Random;

public class WorldGenEnoki extends WorldGenerator
{
    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);

        if(world.isAirBlock(pos.down()) && state == ModBlocks.NETHERRACK.getDefaultState())
        {
            BlockEnokiCap.generatePlant(world, pos, rand, 8);
            return true;
        }

        return false;
    }
}

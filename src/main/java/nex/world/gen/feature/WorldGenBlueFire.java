/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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
import nex.init.NetherExBlocks;

import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class WorldGenBlueFire extends WorldGenerator
{
    private final IBlockState targetBlock;

    public WorldGenBlueFire(IBlockState targetBlockIn)
    {
        targetBlock = targetBlockIn;
    }

    public boolean generate(World world, Random rand, BlockPos pos)
    {
        for(int i = 0; i < 64; ++i)
        {
            BlockPos newPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if(world.isAirBlock(newPos) && world.getBlockState(newPos.down()) == targetBlock)
            {
                world.setBlockState(newPos, NetherExBlocks.BLOCK_FIRE_BLUE.getDefaultState(), 2);
            }
        }

        return true;
    }
}

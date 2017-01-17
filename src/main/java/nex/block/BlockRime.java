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

package nex.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRime extends BlockNetherEx
{
    public BlockRime()
    {
        super("block_rime", Material.ROCK);

        setHardness(5.0F);
        setResistance(10.0F);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        for(int x = -1; x < 2; x++)
        {
            for(int z = -1; z < 2; z++)
            {
                for(int y = -1; y < 2; y++)
                {
                    solidifySurroundings(world, pos.add(x, y, z));
                }
            }
        }

        world.scheduleUpdate(pos, this, MathHelper.getInt(rand, 20, 40));
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        world.scheduleUpdate(pos, this, MathHelper.getInt(RANDOM, 20, 40));
    }

    private void solidifySurroundings(World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);

        if(state == Blocks.WATER.getDefaultState())
        {
            world.setBlockState(pos, Blocks.ICE.getDefaultState(), 3);
        }
        else if(state == Blocks.LAVA.getDefaultState())
        {
            world.setBlockState(pos, Blocks.MAGMA.getDefaultState(), 3);
        }
    }
}

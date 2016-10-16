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

package nex.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nex.Settings;

import java.util.Random;

public class BlockRime extends BlockNetherEx
{
    public BlockRime()
    {
        super("rime_block", Material.PACKED_ICE, SoundType.STONE, "");

        setHardness(5.0F);
        setResistance(10.0F);

        if(Settings.doesRimeFreezeWater || Settings.doesRimeFreezeLava)
        {
            setTickRandomly(true);
        }
    }

    @Override
    public int tickRate(World world)
    {
        return 20;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if(!world.isRemote)
        {
            for(int x = -1; x <= 1; x++)
            {
                for(int z = -1; z <= 1; z++)
                {
                    for(int y = -1; y <= 1; y++)
                    {
                        BlockPos newPos = pos.add(x, y, z);
                        IBlockState testState = world.getBlockState(newPos);

                        if(testState == Blocks.WATER.getDefaultState() && Settings.doesRimeFreezeWater || testState == Blocks.LAVA.getDefaultState() && Settings.doesRimeFreezeLava)
                        {
                            freeze(world, newPos, testState);
                        }
                    }
                }
            }
        }

        world.scheduleUpdate(pos, this, tickRate(world));
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        world.scheduleUpdate(pos, this, tickRate(world));
    }

    private void freeze(World world, BlockPos pos, IBlockState state)
    {
        if(state.getBlock() == Blocks.WATER)
        {
            world.setBlockState(pos, Blocks.ICE.getDefaultState());
        }
        else if(state.getBlock() == Blocks.LAVA)
        {
            world.setBlockState(pos, Blocks.MAGMA.getDefaultState());
        }
    }
}

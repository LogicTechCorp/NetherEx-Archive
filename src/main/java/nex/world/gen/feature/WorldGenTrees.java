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

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import nex.block.BlockLeaves;
import nex.block.BlockLog;
import nex.init.NetherExBlocks;

import java.util.Random;

public class WorldGenTrees extends WorldGenAbstractTree
{
    private static final IBlockState DEFAULT_TRUNK = NetherExBlocks.LOG.getDefaultState().withProperty(BlockLog.TYPE, BlockLog.EnumType.CORRUPTED).withProperty(BlockLog.AXIS, BlockLog.EnumAxis.Y);
    private static final IBlockState DEFAULT_LEAF = NetherExBlocks.LEAVES.getDefaultState().withProperty(BlockLeaves.TYPE, BlockLog.EnumType.CORRUPTED).withProperty(BlockLeaves.CHECK_DECAY, false);

    private final int minTreeHeight;

    private final IBlockState woodMeta;
    private final IBlockState leavesMeta;

    public WorldGenTrees(boolean notify)
    {
        this(notify, 4, DEFAULT_TRUNK, DEFAULT_LEAF);
    }

    public WorldGenTrees(boolean notify, int minTreeHeightIn, IBlockState woodMetaIn, IBlockState leavesMetaIn)
    {
        super(notify);
        minTreeHeight = minTreeHeightIn;
        woodMeta = woodMetaIn;
        leavesMeta = leavesMetaIn;
    }

    public boolean generate(World worldIn, Random rand, BlockPos pos)
    {
        int i = rand.nextInt(3) + minTreeHeight;
        boolean flag = true;

        if(pos.getY() >= 1 && pos.getY() + i + 1 <= worldIn.getHeight())
        {
            for(int j = pos.getY(); j <= pos.getY() + 1 + i; ++j)
            {
                int k = 1;

                if(j == pos.getY())
                {
                    k = 0;
                }

                if(j >= pos.getY() + 1 + i - 2)
                {
                    k = 2;
                }

                BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

                for(int l = pos.getX() - k; l <= pos.getX() + k && flag; ++l)
                {
                    for(int i1 = pos.getZ() - k; i1 <= pos.getZ() + k && flag; ++i1)
                    {
                        if(j >= 0 && j < worldIn.getHeight())
                        {
                            if(!isReplaceable(worldIn, mutablePos.setPos(l, j, i1)))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if(!flag)
            {
                return false;
            }
            else
            {
                IBlockState state = worldIn.getBlockState(pos.down());

                if(state.getBlock().canSustainPlant(state, worldIn, pos.down(), EnumFacing.UP, NetherExBlocks.SAPLING) && pos.getY() < worldIn.getHeight() - i - 1)
                {
                    for(int i3 = pos.getY() - 3 + i; i3 <= pos.getY() + i; ++i3)
                    {
                        int i4 = i3 - (pos.getY() + i);
                        int j1 = 1 - i4 / 2;

                        for(int k1 = pos.getX() - j1; k1 <= pos.getX() + j1; ++k1)
                        {
                            int l1 = k1 - pos.getX();

                            for(int i2 = pos.getZ() - j1; i2 <= pos.getZ() + j1; ++i2)
                            {
                                int j2 = i2 - pos.getZ();

                                if(Math.abs(l1) != j1 || Math.abs(j2) != j1 || rand.nextInt(2) != 0 && i4 != 0)
                                {
                                    BlockPos blockpos = new BlockPos(k1, i3, i2);
                                    state = worldIn.getBlockState(blockpos);

                                    if(state.getBlock().isAir(state, worldIn, blockpos) || state.getBlock().isLeaves(state, worldIn, blockpos) || state.getMaterial() == Material.VINE)
                                    {
                                        setBlockAndNotifyAdequately(worldIn, blockpos, leavesMeta);
                                    }
                                }
                            }
                        }
                    }

                    for(int j3 = 0; j3 < i; ++j3)
                    {
                        BlockPos upN = pos.up(j3);
                        state = worldIn.getBlockState(upN);

                        if(state.getBlock().isAir(state, worldIn, upN) || state.getBlock().isLeaves(state, worldIn, upN) || state.getMaterial() == Material.VINE)
                        {
                            setBlockAndNotifyAdequately(worldIn, pos.up(j3), woodMeta);
                        }
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }
}
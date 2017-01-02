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

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenPit extends WorldGenerator
{
    private final Block block;
    private final IBlockState surroundingBlock;

    public WorldGenPit(Block blockIn, IBlockState surroundingBlockIn)
    {
        block = blockIn;
        surroundingBlock = surroundingBlockIn;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        for(pos = pos.add(-8, 0, -8); pos.getY() > 5 && world.isAirBlock(pos); pos = pos.down())
        {

        }

        if(pos.getY() <= 4)
        {
            return false;
        }
        else
        {
            boolean[] hasSpace = new boolean[2048];
            int i = rand.nextInt(4) + 4;
            pos = pos.down(4);

            for(int j = 0; j < i; ++j)
            {
                double d0 = rand.nextDouble() * 6.0D + 3.0D;
                double d1 = rand.nextDouble() * 4.0D + 2.0D;
                double d2 = rand.nextDouble() * 6.0D + 3.0D;
                double d3 = rand.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
                double d4 = rand.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
                double d5 = rand.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

                for(int l = 1; l < 15; ++l)
                {
                    for(int i1 = 1; i1 < 15; ++i1)
                    {
                        for(int j1 = 1; j1 < 7; ++j1)
                        {
                            double d6 = ((double) l - d3) / (d0 / 2.0D);
                            double d7 = ((double) j1 - d4) / (d1 / 2.0D);
                            double d8 = ((double) i1 - d5) / (d2 / 2.0D);
                            double d9 = d6 * d6 + d7 * d7 + d8 * d8;

                            if(d9 < 1.0D)
                            {
                                hasSpace[(l * 16 + i1) * 8 + j1] = true;
                            }
                        }
                    }
                }
            }

            for(int k1 = 0; k1 < 16; ++k1)
            {
                for(int l2 = 0; l2 < 16; ++l2)
                {
                    for(int k = 0; k < 8; ++k)
                    {
                        boolean flag = !hasSpace[(k1 * 16 + l2) * 8 + k] && (k1 < 15 && hasSpace[((k1 + 1) * 16 + l2) * 8 + k] || k1 > 0 && hasSpace[((k1 - 1) * 16 + l2) * 8 + k] || l2 < 15 && hasSpace[(k1 * 16 + l2 + 1) * 8 + k] || l2 > 0 && hasSpace[(k1 * 16 + (l2 - 1)) * 8 + k] || k < 7 && hasSpace[(k1 * 16 + l2) * 8 + k + 1] || k > 0 && hasSpace[(k1 * 16 + l2) * 8 + (k - 1)]);

                        if(flag)
                        {
                            Material material = world.getBlockState(pos.add(k1, k, l2)).getMaterial();

                            if(k >= 4 && material.isLiquid())
                            {
                                return false;
                            }

                            if(k < 4 && !material.isSolid() && world.getBlockState(pos.add(k1, k, l2)).getBlock() != block)
                            {
                                return false;
                            }
                        }
                    }
                }
            }

            for(int l1 = 0; l1 < 16; ++l1)
            {
                for(int i3 = 0; i3 < 16; ++i3)
                {
                    for(int i4 = 0; i4 < 8; ++i4)
                    {
                        if(hasSpace[(l1 * 16 + i3) * 8 + i4])
                        {
                            world.setBlockState(pos.add(l1, i4, i3), i4 >= 4 ? Blocks.AIR.getDefaultState() : block.getDefaultState(), 2);
                        }
                    }
                }
            }

            for(int j2 = 0; j2 < 16; ++j2)
            {
                for(int k3 = 0; k3 < 16; ++k3)
                {
                    for(int k4 = 0; k4 < 8; ++k4)
                    {
                        boolean flag1 = !hasSpace[(j2 * 16 + k3) * 8 + k4] && (j2 < 15 && hasSpace[((j2 + 1) * 16 + k3) * 8 + k4] || j2 > 0 && hasSpace[((j2 - 1) * 16 + k3) * 8 + k4] || k3 < 15 && hasSpace[(j2 * 16 + k3 + 1) * 8 + k4] || k3 > 0 && hasSpace[(j2 * 16 + (k3 - 1)) * 8 + k4] || k4 < 7 && hasSpace[(j2 * 16 + k3) * 8 + k4 + 1] || k4 > 0 && hasSpace[(j2 * 16 + k3) * 8 + (k4 - 1)]);

                        if(flag1 && (k4 < 4 || rand.nextInt(2) != 0) && world.getBlockState(pos.add(j2, k4, k3)).getMaterial().isSolid())
                        {
                            world.setBlockState(pos.add(j2, k4, k3), surroundingBlock, 2);
                        }
                    }
                }
            }

            return true;
        }
    }
}

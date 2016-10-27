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

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import nex.init.NetherExBlocks;

import java.util.Random;

public class WorldGenBoneSpire extends WorldGenerator
{
    private final boolean upsideDown;

    public WorldGenBoneSpire(boolean upsideDownIn)
    {
        upsideDown = upsideDownIn;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        if(world.getBlockState(pos).getBlock() == Blocks.NETHERRACK)
        {
            pos = !upsideDown ? pos.up() : pos.down();

            if(world.isAirBlock(pos))
            {
                int height = 2 + rand.nextInt(7);

                for(int i = 0; i < height; i++)
                {
                    Block bone = NetherExBlocks.BLOCK_BONE;

                    BlockPos newPos = !upsideDown ? pos.up(i == 0 ? i : i * 2) : pos.down(i == 0 ? i : i * 2);

                    world.setBlockState(newPos, bone.getStateFromMeta(12), 2);
                    world.setBlockState(!upsideDown ? newPos.up() : newPos.down(), bone.getStateFromMeta(13), 2);

                    for(EnumFacing facing : EnumFacing.Plane.HORIZONTAL)
                    {
                        world.setBlockState(newPos.offset(facing), bone.getStateFromMeta(facing.getIndex() <= 3 ? 9 : 5), 2);
                        world.setBlockState(newPos.offset(facing, 2), bone.getStateFromMeta(facing.getIndex() >= 4 ? 6 : 10), 2);
                    }

                    if(i == height - 1)
                    {
                        world.setBlockState(!upsideDown ? newPos.up(2) : newPos.down(2), NetherExBlocks.BLOCK_BONE.getStateFromMeta(14), 2);
                    }
                }
            }
        }

        return false;
    }
}

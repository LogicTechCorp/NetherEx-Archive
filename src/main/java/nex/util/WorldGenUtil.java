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

package nex.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenUtil
{
    public static BlockPos getSuitableGroundPos(World world, BlockPos pos, float xSize, float zSize, float percentage)
    {
        while(pos.getY() > 32)
        {
            int topBlocks = 0;

            for(int x = 0; x <= MathHelper.abs(xSize); x++)
            {
                for(int z = 0; z <= MathHelper.abs(zSize); z++)
                {
                    int xPos = (int) (xSize > 0 ? xSize - x : xSize + x);
                    int zPos = (int) (zSize > 0 ? zSize - z : zSize + z);

                    BlockPos newPos = pos.add(xPos, 0, zPos);

                    Block block = world.getBlockState(newPos).getBlock();

                    if(block != Blocks.NETHER_BRICK && block.isBlockSolid(world, newPos, EnumFacing.DOWN))
                    {
                        if(world.isAirBlock(newPos.up()))
                        {
                            topBlocks++;
                        }
                    }
                }
            }

            if(topBlocks >= MathHelper.abs(xSize * zSize) * percentage)
            {
                return pos.up();
            }

            pos = pos.down();
        }

        return BlockPos.ORIGIN;
    }

    public static void setChestContents(World world, Random rand, BlockPos pos, BlockPos structureSize, ResourceLocation lootTableList)
    {
        Iterable<BlockPos> blockPositions = BlockPos.getAllInBox(pos, new BlockPos(pos.getX() + structureSize.getX(), pos.getY() + structureSize.getY(), pos.getZ() + structureSize.getZ()));

        for(BlockPos newPos : blockPositions)
        {
            Block block = world.getBlockState(newPos).getBlock();

            if(block instanceof BlockChest)
            {
                TileEntityChest chest = (TileEntityChest) world.getTileEntity(newPos);
                chest.setLootTable(lootTableList, rand.nextLong());
            }
        }
    }
}

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
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;
import java.util.Set;

public class WorldGenUtil
{
    public static BlockPos getSuitableGroundPos(World world, BlockPos pos, Set<IBlockState> foundations, float xSize, float zSize, float percentage)
    {
        while(pos.getY() > 32)
        {
            int topBlocks = 0;

            for(int x = 0; x <= MathHelper.abs(xSize); x++)
            {
                for(int z = 0; z <= MathHelper.abs(zSize); z++)
                {
                    int posX = (int) (xSize > 0 ? xSize - x : xSize + x);
                    int posZ = (int) (zSize > 0 ? zSize - z : zSize + z);

                    BlockPos newPos = pos.add(posX, 0, posZ);
                    IBlockState state = world.getBlockState(newPos);

                    if(foundations.contains(state))
                    {
                        if(world.getBlockState(newPos.up()).getMaterial().isReplaceable() && !world.getBlockState(newPos.down()).getMaterial().isReplaceable())
                        {
                            topBlocks++;
                        }
                    }
                    else if(state != Blocks.AIR.getDefaultState())
                    {
                        return BlockPos.ORIGIN;
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

    public static void setSpawnerMob(World world, BlockPos pos, BlockPos structureSize, ResourceLocation mob)
    {
        for(int x = 0; x <= MathHelper.abs(structureSize.getX()); x++)
        {
            for(int z = 0; z <= MathHelper.abs(structureSize.getZ()); z++)
            {
                for(int y = 0; y <= structureSize.getY(); y++)
                {

                    int posX = structureSize.getX() > 0 ? structureSize.getX() - x : structureSize.getX() + x;
                    int posZ = structureSize.getZ() > 0 ? structureSize.getZ() - z : structureSize.getZ() + z;

                    BlockPos newPos = pos.add(posX, y, posZ);
                    Block block = world.getBlockState(newPos).getBlock();

                    if(block instanceof BlockMobSpawner)
                    {
                        TileEntityMobSpawner spawner = (TileEntityMobSpawner) world.getTileEntity(newPos);
                        spawner.getSpawnerBaseLogic().setEntityId(mob);
                    }
                }
            }
        }
    }

    public static void setChestContents(World world, Random rand, BlockPos pos, BlockPos structureSize, ResourceLocation lootTableList)
    {
        for(int x = 0; x <= MathHelper.abs(structureSize.getX()); x++)
        {
            for(int z = 0; z <= MathHelper.abs(structureSize.getZ()); z++)
            {
                for(int y = 0; y <= structureSize.getY(); y++)
                {

                    int posX = structureSize.getX() > 0 ? structureSize.getX() - x : structureSize.getX() + x;
                    int posZ = structureSize.getZ() > 0 ? structureSize.getZ() - z : structureSize.getZ() + z;

                    BlockPos newPos = pos.add(posX, y, posZ);
                    Block block = world.getBlockState(newPos).getBlock();

                    if(block instanceof BlockChest)
                    {
                        TileEntityChest chest = (TileEntityChest) world.getTileEntity(newPos);
                        chest.setLootTable(lootTableList, rand.nextLong());
                    }
                }
            }
        }
    }
}

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

package nex.world.gen.structure.nethervillage;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import nex.init.NetherExBlocks;

import java.util.Random;

public abstract class StructureNetherVillageHut extends StructureNetherVillage
{
    public StructureNetherVillageHut()
    {

    }

    protected StructureNetherVillageHut(StructureNetherVillageWell.Controller controller, int componentType, Random rand, StructureBoundingBox boundingBoxIn, EnumFacing facing)
    {
        super(controller, componentType);
        setCoordBaseMode(facing);
        boundingBox = boundingBoxIn;
    }

    @Override
    public boolean addComponentParts(World world, Random rand, StructureBoundingBox boundingBoxIn)
    {
        if(averageGroundLvl < 0)
        {
            averageGroundLvl = getAverageGroundLevel(world, boundingBoxIn);

            if(averageGroundLvl < 0)
            {
                return true;
            }

            for(int x = 0; x < 9; x++)
            {
                for(int z = 0; z < 9; z++)
                {
                    StructureBoundingBox fakeBoundingBox = new StructureBoundingBox(boundingBox);
                    fakeBoundingBox.offset(0, averageGroundLvl - boundingBox.maxY + 5 - 1, 0);

                    BlockPos pos = new BlockPos(getXWithOffset(x, z), fakeBoundingBox.minY - 1, getZWithOffset(x, z));

                    if(world.isAirBlock(pos))
                    {
                        return false;
                    }
                }
            }

            int solidBlocks = 0;

            for(int x = 0; x < 9; x++)
            {
                for(int z = 0; z < 9; z++)
                {
                    StructureBoundingBox fakeBoundingBox = new StructureBoundingBox(boundingBox);
                    fakeBoundingBox.offset(0, averageGroundLvl - boundingBox.maxY + 5 - 1, 0);

                    BlockPos pos = new BlockPos(getXWithOffset(x, z), fakeBoundingBox.minY - 1, getZWithOffset(x, z));

                    if(world.isAirBlock(pos))
                    {
                        solidBlocks++;

                        if(solidBlocks > 63)
                        {
                            return false;
                        }
                    }
                }
            }

            boundingBox.offset(0, averageGroundLvl - boundingBox.maxY + 5 - 1, 0);
        }

        IBlockState redNetherBrick = Blocks.RED_NETHER_BRICK.getDefaultState();
        IBlockState redNetherBrickStairs = NetherExBlocks.STAIRS_RED_BRICK_NETHER.getDefaultState();
        IBlockState netherBrickFence = Blocks.NETHER_BRICK_FENCE.getDefaultState();
        IBlockState netherBrickFenceGate = NetherExBlocks.FENCE_GATE_BRICK_NETHER.getDefaultState();
        IBlockState netherBrick = Blocks.NETHER_BRICK.getDefaultState();
        IBlockState netherBrickSlab = Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.NETHERBRICK);

        fillWithAir(world, boundingBoxIn, -1, 0, -1, 7, 7, 7);
        fillWithBlocks(world, boundingBoxIn, 2, 0, 0, 4, 2, 0, redNetherBrick, redNetherBrick, false);
        setFenceGate(world, netherBrickFenceGate, 3, 0, 0, EnumFacing.NORTH, boundingBox);
        setBlockState(world, Blocks.AIR.getDefaultState(), 3, 1, 0, boundingBoxIn);
        setBlockState(world, redNetherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP).withProperty(BlockStairs.FACING, EnumFacing.NORTH), 3, 2, 0, boundingBoxIn);
        fillWithBlocks(world, boundingBoxIn, 2, 0, 6, 4, 2, 6, redNetherBrick, redNetherBrick, false);
        setBlockState(world, redNetherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.FACING, EnumFacing.SOUTH), 3, 0, 6, boundingBoxIn);
        setBlockState(world, netherBrickFence, 3, 1, 6, boundingBoxIn);
        setBlockState(world, redNetherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP).withProperty(BlockStairs.FACING, EnumFacing.SOUTH), 3, 2, 6, boundingBoxIn);
        fillWithBlocks(world, boundingBoxIn, 0, 0, 2, 0, 2, 4, redNetherBrick, redNetherBrick, false);
        setBlockState(world, redNetherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.FACING, EnumFacing.EAST), 0, 0, 3, boundingBoxIn);
        setBlockState(world, netherBrickFence, 0, 1, 3, boundingBoxIn);
        setBlockState(world, redNetherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP).withProperty(BlockStairs.FACING, EnumFacing.EAST), 0, 2, 3, boundingBoxIn);
        fillWithBlocks(world, boundingBoxIn, 6, 0, 2, 6, 2, 4, redNetherBrick, redNetherBrick, false);
        setBlockState(world, redNetherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.FACING, EnumFacing.WEST), 6, 0, 3, boundingBoxIn);
        setBlockState(world, netherBrickFence, 6, 1, 3, boundingBoxIn);
        setBlockState(world, redNetherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP).withProperty(BlockStairs.FACING, EnumFacing.WEST), 6, 2, 3, boundingBoxIn);
        fillWithBlocks(world, boundingBoxIn, 1, 0, 1, 1, 2, 1, redNetherBrick, redNetherBrick, false);
        fillWithBlocks(world, boundingBoxIn, 5, 0, 1, 5, 2, 1, redNetherBrick, redNetherBrick, false);
        fillWithBlocks(world, boundingBoxIn, 5, 0, 5, 5, 2, 5, redNetherBrick, redNetherBrick, false);
        fillWithBlocks(world, boundingBoxIn, 1, 0, 5, 1, 2, 5, redNetherBrick, redNetherBrick, false);
        fillWithBlocks(world, boundingBoxIn, 0, 3, 0, 6, 3, 6, netherBrick, netherBrick, false);
        setBlockState(world, Blocks.AIR.getDefaultState(), 0, 3, 0, boundingBoxIn);
        setBlockState(world, netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), 1, 3, 0, boundingBoxIn);
        setBlockState(world, netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), 0, 3, 1, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 6, 3, 0, boundingBoxIn);
        setBlockState(world, netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), 5, 3, 0, boundingBoxIn);
        setBlockState(world, netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), 6, 3, 1, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 6, 3, 6, boundingBoxIn);
        setBlockState(world, netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), 5, 3, 6, boundingBoxIn);
        setBlockState(world, netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), 6, 3, 5, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 0, 3, 6, boundingBoxIn);
        setBlockState(world, netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), 0, 3, 5, boundingBoxIn);
        setBlockState(world, netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), 1, 3, 6, boundingBoxIn);
        fillWithBlocks(world, boundingBoxIn, 2, 3, -1, 4, 3, -1, netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), false);
        fillWithBlocks(world, boundingBoxIn, 7, 3, 2, 7, 3, 4, netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), false);
        fillWithBlocks(world, boundingBoxIn, 2, 3, 7, 4, 3, 7, netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), false);
        fillWithBlocks(world, boundingBoxIn, -1, 3, 2, -1, 3, 4, netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), false);
        fillWithBlocks(world, boundingBoxIn, 1, 4, 1, 5, 4, 5, netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM), false);
        setBlockState(world, Blocks.AIR.getDefaultState(), 1, 4, 1, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 5, 4, 1, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 5, 4, 5, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 1, 4, 5, boundingBoxIn);
        fillWithAir(world, boundingBoxIn, 2, 3, 1, 4, 3, 5);
        fillWithAir(world, boundingBoxIn, 1, 3, 2, 5, 3, 4);
        setBlockState(world, netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.TOP), 2, 3, 1, boundingBoxIn);
        setBlockState(world, netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.TOP), 4, 3, 1, boundingBoxIn);
        setBlockState(world, netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.TOP), 4, 3, 5, boundingBoxIn);
        setBlockState(world, netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.TOP), 2, 3, 5, boundingBoxIn);
        setBlockState(world, netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.TOP), 1, 3, 2, boundingBoxIn);
        setBlockState(world, netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.TOP), 1, 3, 4, boundingBoxIn);
        setBlockState(world, netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.TOP), 5, 3, 4, boundingBoxIn);
        setBlockState(world, netherBrickSlab.withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.TOP), 5, 3, 2, boundingBoxIn);
        setBlockState(world, Blocks.GLOWSTONE.getDefaultState(), 3, 3, 3, boundingBoxIn);

        for(int x = 1; x < 6; x++)
        {
            for(int z = 1; z < 6; z++)
            {
                replaceAirAndLiquidDownwards(world, Blocks.NETHERRACK.getDefaultState(), x, -1, z, boundingBoxIn);
            }
        }

        for(int x = 2; x < 5; x++)
        {
            for(int z = 0; z < 7; z++)
            {
                replaceAirAndLiquidDownwards(world, Blocks.NETHERRACK.getDefaultState(), x, -1, z, boundingBoxIn);
            }
        }

        for(int x = 0; x < 7; x++)
        {
            for(int z = 2; z < 5; z++)
            {
                replaceAirAndLiquidDownwards(world, Blocks.NETHERRACK.getDefaultState(), x, -1, z, boundingBoxIn);
            }
        }

        return true;
    }
}

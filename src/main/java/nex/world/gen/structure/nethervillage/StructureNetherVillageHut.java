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

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import nex.init.NetherExBlocks;

import java.util.List;
import java.util.Random;

public class StructureNetherVillageHut extends StructureNetherVillage
{
    private StructureNetherVillageHut(StructureNetherVillageWell.Controller controller, int componentType, Random rand, StructureBoundingBox boundingBoxIn, EnumFacing facing)
    {
        super(controller, componentType);
        setCoordBaseMode(facing);
        boundingBox = boundingBoxIn;
    }

    public static StructureNetherVillageHut createPiece(StructureNetherVillageWell.Controller controller, List<StructureComponent> components, Random rand, int minX, int minY, int minZ, EnumFacing facing, int componentType)
    {
        StructureBoundingBox boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, 9, 5, 9, facing);
        return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(components, boundingBox) == null ? new StructureNetherVillageHut(controller, componentType, rand, boundingBox, facing) : null;
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

            boundingBox.offset(0, averageGroundLvl - boundingBox.maxY + 5 - 1, 0);
        }

        IBlockState redNetherBrick = getBiomeSpecificBlock(Blocks.RED_NETHER_BRICK.getDefaultState());
        IBlockState redNetherBrickStairs = getBiomeSpecificBlock(NetherExBlocks.STAIRS_RED_BRICK_NETHER.getDefaultState());
        IBlockState netherBrickFence = getBiomeSpecificBlock(Blocks.NETHER_BRICK_FENCE.getDefaultState());
        IBlockState netherBrickFenceGate = getBiomeSpecificBlock(NetherExBlocks.FENCE_GATE_BRICK_NETHER.getDefaultState());
        IBlockState netherBrick = getBiomeSpecificBlock(Blocks.NETHER_BRICK.getDefaultState());
        IBlockState netherBrickSlab = getBiomeSpecificBlock(Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.NETHERBRICK));

        fillWithAir(world, boundingBoxIn, 0, 0, 0, 8, 4, 8);
        fillWithBlocks(world, boundingBoxIn, 3, 0, 1, 5, 2, 1, redNetherBrick, redNetherBrick, false);
        setBlockState(world, netherBrickFenceGate.withProperty(BlockFenceGate.FACING, EnumFacing.NORTH), 4, 0, 1, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 4, 1, 1, boundingBoxIn);
        setBlockState(world, redNetherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP).withProperty(BlockStairs.FACING, EnumFacing.NORTH), 4, 2, 1, boundingBoxIn);
        fillWithBlocks(world, boundingBoxIn, 3, 0, 7, 5, 2, 7, redNetherBrick, redNetherBrick, false);
        setBlockState(world, redNetherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.FACING, EnumFacing.SOUTH), 4, 0, 7, boundingBoxIn);
        setBlockState(world, netherBrickFence, 4, 1, 7, boundingBoxIn);
        setBlockState(world, redNetherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP).withProperty(BlockStairs.FACING, EnumFacing.SOUTH), 4, 2, 7, boundingBoxIn);
        fillWithBlocks(world, boundingBoxIn, 1, 0, 3, 1, 2, 5, redNetherBrick, redNetherBrick, false);
        setBlockState(world, redNetherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.FACING, EnumFacing.EAST), 1, 0, 4, boundingBoxIn);
        setBlockState(world, netherBrickFence, 1, 1, 4, boundingBoxIn);
        setBlockState(world, redNetherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP).withProperty(BlockStairs.FACING, EnumFacing.EAST), 1, 2, 4, boundingBoxIn);
        fillWithBlocks(world, boundingBoxIn, 7, 0, 3, 7, 2, 5, redNetherBrick, redNetherBrick, false);
        setBlockState(world, redNetherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.FACING, EnumFacing.WEST), 7, 0, 4, boundingBoxIn);
        setBlockState(world, netherBrickFence, 7, 1, 4, boundingBoxIn);
        setBlockState(world, redNetherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP).withProperty(BlockStairs.FACING, EnumFacing.WEST), 7, 2, 4, boundingBoxIn);
        fillWithBlocks(world, boundingBoxIn, 2, 0, 2, 2, 2, 2, redNetherBrick, redNetherBrick, false);
        fillWithBlocks(world, boundingBoxIn, 6, 0, 2, 6, 2, 2, redNetherBrick, redNetherBrick, false);
        fillWithBlocks(world, boundingBoxIn, 6, 0, 6, 6, 2, 6, redNetherBrick, redNetherBrick, false);
        fillWithBlocks(world, boundingBoxIn, 2, 0, 6, 2, 2, 6, redNetherBrick, redNetherBrick, false);
        fillWithBlocks(world, boundingBoxIn, 1, 3, 1, 7, 3, 7, netherBrick, netherBrick, false);
        setBlockState(world, Blocks.AIR.getDefaultState(), 1, 3, 1, boundingBoxIn);
        setBlockState(world, netherBrickSlab, 2, 3, 1, boundingBoxIn);
        setBlockState(world, netherBrickSlab, 1, 3, 2, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 7, 3, 1, boundingBoxIn);
        setBlockState(world, netherBrickSlab, 6, 3, 1, boundingBoxIn);
        setBlockState(world, netherBrickSlab, 7, 3, 2, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 7, 3, 7, boundingBoxIn);
        setBlockState(world, netherBrickSlab, 6, 3, 7, boundingBoxIn);
        setBlockState(world, netherBrickSlab, 7, 3, 6, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 1, 3, 7, boundingBoxIn);
        setBlockState(world, netherBrickSlab, 1, 3, 6, boundingBoxIn);
        setBlockState(world, netherBrickSlab, 2, 3, 7, boundingBoxIn);
        fillWithBlocks(world, boundingBoxIn, 3, 3, 0, 5, 3, 0, netherBrickSlab, netherBrickSlab, false);
        fillWithBlocks(world, boundingBoxIn, 8, 3, 3, 8, 3, 5, netherBrickSlab, netherBrickSlab, false);
        fillWithBlocks(world, boundingBoxIn, 3, 3, 8, 5, 3, 8, netherBrickSlab, netherBrickSlab, false);
        fillWithBlocks(world, boundingBoxIn, 0, 3, 3, 0, 3, 5, netherBrickSlab, netherBrickSlab, false);
        fillWithBlocks(world, boundingBoxIn, 2, 4, 2, 6, 4, 6, netherBrickSlab, netherBrickSlab, false);
        setBlockState(world, Blocks.AIR.getDefaultState(), 2, 4, 2, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 6, 4, 2, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 6, 4, 6, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 2, 4, 6, boundingBoxIn);

        for(int x = 0; x < 9; x++)
        {
            for(int z = 0; z < 9; z++)
            {
                replaceAirAndLiquidDownwards(world, Blocks.NETHERRACK.getDefaultState(), x, -1, z, boundingBoxIn);
            }
        }

        return true;
    }
}

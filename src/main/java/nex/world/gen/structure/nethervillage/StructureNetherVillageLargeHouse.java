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

import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import nex.init.NetherExBlocks;

import java.util.List;
import java.util.Random;

public class StructureNetherVillageLargeHouse extends StructureNetherVillage
{
    public StructureNetherVillageLargeHouse()
    {
    }

    private StructureNetherVillageLargeHouse(StructureNetherVillageWell.Controller controller, int componentType, Random rand, StructureBoundingBox boundingBoxIn, EnumFacing facing)
    {
        super(controller, componentType);
        setCoordBaseMode(facing);
        boundingBox = boundingBoxIn;
    }

    public static StructureNetherVillageLargeHouse createPiece(StructureNetherVillageWell.Controller controller, List<StructureComponent> components, Random rand, int minX, int minY, int minZ, EnumFacing facing, int componentType)
    {
        StructureBoundingBox boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, 9, 7, 12, facing);
        return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(components, boundingBox) == null ? new StructureNetherVillageLargeHouse(controller, componentType, rand, boundingBox, facing) : null;
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

            boundingBox.offset(0, averageGroundLvl - boundingBox.maxY + 7 - 1, 0);
        }

        IBlockState iblockstate = getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
        IBlockState iblockstate1 = getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
        IBlockState iblockstate2 = getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH));
        IBlockState iblockstate3 = getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST));
        IBlockState iblockstate4 = getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST));
        IBlockState iblockstate5 = getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState());
        IBlockState iblockstate6 = getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
        fillWithBlocks(world, boundingBoxIn, 1, 1, 1, 7, 4, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
        fillWithBlocks(world, boundingBoxIn, 2, 1, 6, 8, 4, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
        fillWithBlocks(world, boundingBoxIn, 2, 0, 5, 8, 0, 10, iblockstate5, iblockstate5, false);
        fillWithBlocks(world, boundingBoxIn, 1, 0, 1, 7, 0, 4, iblockstate5, iblockstate5, false);
        fillWithBlocks(world, boundingBoxIn, 0, 0, 0, 0, 3, 5, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 8, 0, 0, 8, 3, 10, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 1, 0, 0, 7, 2, 0, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 1, 0, 5, 2, 1, 5, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 2, 0, 6, 2, 3, 10, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 3, 0, 10, 7, 3, 10, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 1, 2, 0, 7, 3, 0, iblockstate5, iblockstate5, false);
        fillWithBlocks(world, boundingBoxIn, 1, 2, 5, 2, 3, 5, iblockstate5, iblockstate5, false);
        fillWithBlocks(world, boundingBoxIn, 0, 4, 1, 8, 4, 1, iblockstate5, iblockstate5, false);
        fillWithBlocks(world, boundingBoxIn, 0, 4, 4, 3, 4, 4, iblockstate5, iblockstate5, false);
        fillWithBlocks(world, boundingBoxIn, 0, 5, 2, 8, 5, 3, iblockstate5, iblockstate5, false);
        setBlockState(world, iblockstate5, 0, 4, 2, boundingBoxIn);
        setBlockState(world, iblockstate5, 0, 4, 3, boundingBoxIn);
        setBlockState(world, iblockstate5, 8, 4, 2, boundingBoxIn);
        setBlockState(world, iblockstate5, 8, 4, 3, boundingBoxIn);
        setBlockState(world, iblockstate5, 8, 4, 4, boundingBoxIn);
        IBlockState iblockstate7 = iblockstate1;
        IBlockState iblockstate8 = iblockstate2;
        IBlockState iblockstate9 = iblockstate4;
        IBlockState iblockstate10 = iblockstate3;

        for(int i = -1; i <= 2; ++i)
        {
            for(int j = 0; j <= 8; ++j)
            {
                setBlockState(world, iblockstate7, j, 4 + i, i, boundingBoxIn);

                if((i > -1 || j <= 1) && (i > 0 || j <= 3) && (i > 1 || j <= 4 || j >= 6))
                {
                    setBlockState(world, iblockstate8, j, 4 + i, 5 - i, boundingBoxIn);
                }
            }
        }

        fillWithBlocks(world, boundingBoxIn, 3, 4, 5, 3, 4, 10, iblockstate5, iblockstate5, false);
        fillWithBlocks(world, boundingBoxIn, 7, 4, 2, 7, 4, 10, iblockstate5, iblockstate5, false);
        fillWithBlocks(world, boundingBoxIn, 4, 5, 4, 4, 5, 10, iblockstate5, iblockstate5, false);
        fillWithBlocks(world, boundingBoxIn, 6, 5, 4, 6, 5, 10, iblockstate5, iblockstate5, false);
        fillWithBlocks(world, boundingBoxIn, 5, 6, 3, 5, 6, 10, iblockstate5, iblockstate5, false);

        for(int k = 4; k >= 1; --k)
        {
            setBlockState(world, iblockstate5, k, 2 + k, 7 - k, boundingBoxIn);

            for(int k1 = 8 - k; k1 <= 10; ++k1)
            {
                setBlockState(world, iblockstate10, k, 2 + k, k1, boundingBoxIn);
            }
        }

        setBlockState(world, iblockstate5, 6, 6, 3, boundingBoxIn);
        setBlockState(world, iblockstate5, 7, 5, 4, boundingBoxIn);
        setBlockState(world, iblockstate4, 6, 6, 4, boundingBoxIn);

        for(int l = 6; l <= 8; ++l)
        {
            for(int l1 = 5; l1 <= 10; ++l1)
            {
                setBlockState(world, iblockstate9, l, 12 - l, l1, boundingBoxIn);
            }
        }

        setBlockState(world, iblockstate6, 0, 2, 1, boundingBoxIn);
        setBlockState(world, iblockstate6, 0, 2, 4, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 3, boundingBoxIn);
        setBlockState(world, iblockstate6, 4, 2, 0, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 5, 2, 0, boundingBoxIn);
        setBlockState(world, iblockstate6, 6, 2, 0, boundingBoxIn);
        setBlockState(world, iblockstate6, 8, 2, 1, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 2, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 3, boundingBoxIn);
        setBlockState(world, iblockstate6, 8, 2, 4, boundingBoxIn);
        setBlockState(world, iblockstate5, 8, 2, 5, boundingBoxIn);
        setBlockState(world, iblockstate6, 8, 2, 6, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 7, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 8, boundingBoxIn);
        setBlockState(world, iblockstate6, 8, 2, 9, boundingBoxIn);
        setBlockState(world, iblockstate6, 2, 2, 6, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 7, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 8, boundingBoxIn);
        setBlockState(world, iblockstate6, 2, 2, 9, boundingBoxIn);
        setBlockState(world, iblockstate6, 4, 4, 10, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 5, 4, 10, boundingBoxIn);
        setBlockState(world, iblockstate6, 6, 4, 10, boundingBoxIn);
        setBlockState(world, iblockstate5, 5, 5, 10, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 2, 1, 0, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 2, 2, 0, boundingBoxIn);
        placeTorch(world, EnumFacing.NORTH, 2, 3, 1, boundingBoxIn);
        createVillageDoor(world, boundingBoxIn, rand, 2, 1, 0, EnumFacing.NORTH);
        fillWithBlocks(world, boundingBoxIn, 1, 0, -1, 3, 2, -1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);

        if(getBlockStateFromPos(world, 2, 0, -1, boundingBoxIn).getMaterial() == Material.AIR && getBlockStateFromPos(world, 2, -1, -1, boundingBoxIn).getMaterial() != Material.AIR)
        {
            setBlockState(world, iblockstate7, 2, 0, -1, boundingBoxIn);

            if(getBlockStateFromPos(world, 2, -1, -1, boundingBoxIn).getBlock() == NetherExBlocks.BLOCK_NETHERRACK_PATH)
            {
                setBlockState(world, Blocks.NETHERRACK.getDefaultState(), 2, -1, -1, boundingBoxIn);
            }
        }

        for(int i1 = 0; i1 < 5; ++i1)
        {
            for(int i2 = 0; i2 < 9; ++i2)
            {
                clearCurrentPositionBlocksUpwards(world, i2, 7, i1, boundingBoxIn);
                replaceAirAndLiquidDownwards(world, iblockstate, i2, -1, i1, boundingBoxIn);
            }
        }

        for(int j1 = 5; j1 < 11; ++j1)
        {
            for(int j2 = 2; j2 < 9; ++j2)
            {
                clearCurrentPositionBlocksUpwards(world, j2, 7, j1, boundingBoxIn);
                replaceAirAndLiquidDownwards(world, iblockstate, j2, -1, j1, boundingBoxIn);
            }
        }

        spawnPigtificates(world, boundingBoxIn, 4, 1, 2, 2);
        return true;
    }
}

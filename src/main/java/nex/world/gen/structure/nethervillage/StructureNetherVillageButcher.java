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

public class StructureNetherVillageButcher extends StructureNetherVillage
{
    public StructureNetherVillageButcher()
    {
    }

    private StructureNetherVillageButcher(StructureNetherVillageWell.Controller controller, int componentType, Random rand, StructureBoundingBox boundingBoxIn, EnumFacing facing)
    {
        super(controller, componentType);
        setCoordBaseMode(facing);
        boundingBox = boundingBoxIn;
    }

    public static StructureNetherVillageButcher createPiece(StructureNetherVillageWell.Controller controller, List<StructureComponent> components, Random rand, int minX, int minY, int minZ, EnumFacing facing, int componentType)
    {
        StructureBoundingBox boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, 9, 7, 11, facing);
        return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(components, boundingBox) == null ? new StructureNetherVillageButcher(controller, componentType, rand, boundingBox, facing) : null;
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
        IBlockState iblockstate3 = getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST));
        IBlockState iblockstate4 = getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState());
        IBlockState iblockstate5 = getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
        IBlockState iblockstate6 = getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
        fillWithBlocks(world, boundingBoxIn, 1, 1, 1, 7, 4, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
        fillWithBlocks(world, boundingBoxIn, 2, 1, 6, 8, 4, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
        fillWithBlocks(world, boundingBoxIn, 2, 0, 6, 8, 0, 10, Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState(), false);
        setBlockState(world, iblockstate, 6, 0, 6, boundingBoxIn);
        fillWithBlocks(world, boundingBoxIn, 2, 1, 6, 2, 1, 10, iblockstate6, iblockstate6, false);
        fillWithBlocks(world, boundingBoxIn, 8, 1, 6, 8, 1, 10, iblockstate6, iblockstate6, false);
        fillWithBlocks(world, boundingBoxIn, 3, 1, 10, 7, 1, 10, iblockstate6, iblockstate6, false);
        fillWithBlocks(world, boundingBoxIn, 1, 0, 1, 7, 0, 4, iblockstate4, iblockstate4, false);
        fillWithBlocks(world, boundingBoxIn, 0, 0, 0, 0, 3, 5, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 8, 0, 0, 8, 3, 5, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 1, 0, 0, 7, 1, 0, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 1, 0, 5, 7, 1, 5, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 1, 2, 0, 7, 3, 0, iblockstate4, iblockstate4, false);
        fillWithBlocks(world, boundingBoxIn, 1, 2, 5, 7, 3, 5, iblockstate4, iblockstate4, false);
        fillWithBlocks(world, boundingBoxIn, 0, 4, 1, 8, 4, 1, iblockstate4, iblockstate4, false);
        fillWithBlocks(world, boundingBoxIn, 0, 4, 4, 8, 4, 4, iblockstate4, iblockstate4, false);
        fillWithBlocks(world, boundingBoxIn, 0, 5, 2, 8, 5, 3, iblockstate4, iblockstate4, false);
        setBlockState(world, iblockstate4, 0, 4, 2, boundingBoxIn);
        setBlockState(world, iblockstate4, 0, 4, 3, boundingBoxIn);
        setBlockState(world, iblockstate4, 8, 4, 2, boundingBoxIn);
        setBlockState(world, iblockstate4, 8, 4, 3, boundingBoxIn);
        IBlockState iblockstate7 = iblockstate1;
        IBlockState iblockstate8 = iblockstate2;

        for(int i = -1; i <= 2; ++i)
        {
            for(int j = 0; j <= 8; ++j)
            {
                setBlockState(world, iblockstate7, j, 4 + i, i, boundingBoxIn);
                setBlockState(world, iblockstate8, j, 4 + i, 5 - i, boundingBoxIn);
            }
        }

        setBlockState(world, iblockstate5, 0, 2, 1, boundingBoxIn);
        setBlockState(world, iblockstate5, 0, 2, 4, boundingBoxIn);
        setBlockState(world, iblockstate5, 8, 2, 1, boundingBoxIn);
        setBlockState(world, iblockstate5, 8, 2, 4, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 3, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 2, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 3, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 5, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 3, 2, 5, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 5, 2, 0, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 6, 2, 5, boundingBoxIn);
        setBlockState(world, iblockstate6, 2, 1, 3, boundingBoxIn);
        setBlockState(world, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), 2, 2, 3, boundingBoxIn);
        setBlockState(world, iblockstate4, 1, 1, 4, boundingBoxIn);
        setBlockState(world, iblockstate7, 2, 1, 4, boundingBoxIn);
        setBlockState(world, iblockstate3, 1, 1, 3, boundingBoxIn);
        fillWithBlocks(world, boundingBoxIn, 5, 0, 1, 7, 0, 3, Blocks.DOUBLE_STONE_SLAB.getDefaultState(), Blocks.DOUBLE_STONE_SLAB.getDefaultState(), false);
        setBlockState(world, Blocks.DOUBLE_STONE_SLAB.getDefaultState(), 6, 1, 1, boundingBoxIn);
        setBlockState(world, Blocks.DOUBLE_STONE_SLAB.getDefaultState(), 6, 1, 2, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 2, 1, 0, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 2, 2, 0, boundingBoxIn);
        placeTorch(world, EnumFacing.NORTH, 2, 3, 1, boundingBoxIn);
        createVillageDoor(world, boundingBoxIn, rand, 2, 1, 0, EnumFacing.NORTH);

        if(getBlockStateFromPos(world, 2, 0, -1, boundingBoxIn).getMaterial() == Material.AIR && getBlockStateFromPos(world, 2, -1, -1, boundingBoxIn).getMaterial() != Material.AIR)
        {
            setBlockState(world, iblockstate7, 2, 0, -1, boundingBoxIn);

            if(getBlockStateFromPos(world, 2, -1, -1, boundingBoxIn).getBlock() == NetherExBlocks.BLOCK_NETHERRACK_PATH)
            {
                setBlockState(world, Blocks.NETHERRACK.getDefaultState(), 2, -1, -1, boundingBoxIn);
            }
        }

        setBlockState(world, Blocks.AIR.getDefaultState(), 6, 1, 5, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 6, 2, 5, boundingBoxIn);
        placeTorch(world, EnumFacing.SOUTH, 6, 3, 4, boundingBoxIn);
        createVillageDoor(world, boundingBoxIn, rand, 6, 1, 5, EnumFacing.SOUTH);

        for(int k = 0; k < 5; ++k)
        {
            for(int l = 0; l < 9; ++l)
            {
                clearCurrentPositionBlocksUpwards(world, l, 7, k, boundingBoxIn);
                replaceAirAndLiquidDownwards(world, iblockstate, l, -1, k, boundingBoxIn);
            }
        }

        spawnPigtificates(world, boundingBoxIn, 4, 1, 2, 2);
        return true;
    }

    protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession)
    {
        return villagersSpawnedIn == 0 ? 4 : super.chooseProfession(villagersSpawnedIn, currentVillagerProfession);
    }
}

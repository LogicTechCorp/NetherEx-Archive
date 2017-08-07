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

import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

import java.util.List;
import java.util.Random;

public class StructureNetherVillageChurch extends StructureNetherVillage
{
    public StructureNetherVillageChurch()
    {
    }

    private StructureNetherVillageChurch(StructureNetherVillageWell.Controller controller, int componentType, Random rand, StructureBoundingBox boundingBoxIn, EnumFacing facing)
    {
        super(controller, componentType);
        setCoordBaseMode(facing);
        boundingBox = boundingBoxIn;
    }

    public static StructureNetherVillageChurch createPiece(StructureNetherVillageWell.Controller controller, List<StructureComponent> components, Random rand, int minX, int minY, int minZ, EnumFacing facing, int componentType)
    {
        StructureBoundingBox boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, 5, 12, 9, facing);
        return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(components, boundingBox) == null ? new StructureNetherVillageChurch(controller, componentType, rand, boundingBox, facing) : null;
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

            boundingBox.offset(0, averageGroundLvl - boundingBox.maxY + 12 - 1, 0);
        }

        IBlockState iblockstate = Blocks.COBBLESTONE.getDefaultState();
        IBlockState iblockstate1 = getBiomeSpecificBlockState(Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
        IBlockState iblockstate2 = getBiomeSpecificBlockState(Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST));
        IBlockState iblockstate3 = getBiomeSpecificBlockState(Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST));
        fillWithBlocks(world, boundingBoxIn, 1, 1, 1, 3, 3, 7, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
        fillWithBlocks(world, boundingBoxIn, 1, 5, 1, 3, 9, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
        fillWithBlocks(world, boundingBoxIn, 1, 0, 0, 3, 0, 8, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 1, 1, 0, 3, 10, 0, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 0, 1, 1, 0, 10, 3, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 4, 1, 1, 4, 10, 3, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 0, 0, 4, 0, 4, 7, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 4, 0, 4, 4, 4, 7, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 1, 1, 8, 3, 4, 8, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 1, 5, 4, 3, 10, 4, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 1, 5, 5, 3, 5, 7, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 0, 9, 0, 4, 9, 4, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 0, 4, 0, 4, 4, 4, iblockstate, iblockstate, false);
        setBlockState(world, iblockstate, 0, 11, 2, boundingBoxIn);
        setBlockState(world, iblockstate, 4, 11, 2, boundingBoxIn);
        setBlockState(world, iblockstate, 2, 11, 0, boundingBoxIn);
        setBlockState(world, iblockstate, 2, 11, 4, boundingBoxIn);
        setBlockState(world, iblockstate, 1, 1, 6, boundingBoxIn);
        setBlockState(world, iblockstate, 1, 1, 7, boundingBoxIn);
        setBlockState(world, iblockstate, 2, 1, 7, boundingBoxIn);
        setBlockState(world, iblockstate, 3, 1, 6, boundingBoxIn);
        setBlockState(world, iblockstate, 3, 1, 7, boundingBoxIn);
        setBlockState(world, iblockstate1, 1, 1, 5, boundingBoxIn);
        setBlockState(world, iblockstate1, 2, 1, 6, boundingBoxIn);
        setBlockState(world, iblockstate1, 3, 1, 5, boundingBoxIn);
        setBlockState(world, iblockstate2, 1, 2, 7, boundingBoxIn);
        setBlockState(world, iblockstate3, 3, 2, 7, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 0, 3, 2, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 4, 2, 2, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 4, 3, 2, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 0, 6, 2, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 0, 7, 2, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 4, 6, 2, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 4, 7, 2, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 2, 6, 0, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 2, 7, 0, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 2, 6, 4, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 2, 7, 4, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 0, 3, 6, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 4, 3, 6, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 2, 3, 8, boundingBoxIn);
        placeTorch(world, EnumFacing.SOUTH, 2, 4, 7, boundingBoxIn);
        placeTorch(world, EnumFacing.EAST, 1, 4, 6, boundingBoxIn);
        placeTorch(world, EnumFacing.WEST, 3, 4, 6, boundingBoxIn);
        placeTorch(world, EnumFacing.NORTH, 2, 4, 5, boundingBoxIn);
        IBlockState iblockstate4 = Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.WEST);

        for(int i = 1; i <= 9; ++i)
        {
            setBlockState(world, iblockstate4, 3, i, 3, boundingBoxIn);
        }

        setBlockState(world, Blocks.AIR.getDefaultState(), 2, 1, 0, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 2, 2, 0, boundingBoxIn);
        createVillageDoor(world, boundingBoxIn, rand, 2, 1, 0, EnumFacing.NORTH);

        if(getBlockStateFromPos(world, 2, 0, -1, boundingBoxIn).getMaterial() == Material.AIR && getBlockStateFromPos(world, 2, -1, -1, boundingBoxIn).getMaterial() != Material.AIR)
        {
            setBlockState(world, iblockstate1, 2, 0, -1, boundingBoxIn);

            if(getBlockStateFromPos(world, 2, -1, -1, boundingBoxIn).getBlock() == Blocks.GRASS_PATH)
            {
                setBlockState(world, Blocks.GRASS.getDefaultState(), 2, -1, -1, boundingBoxIn);
            }
        }

        for(int k = 0; k < 9; ++k)
        {
            for(int j = 0; j < 5; ++j)
            {
                clearCurrentPositionBlocksUpwards(world, j, 12, k, boundingBoxIn);
                replaceAirAndLiquidDownwards(world, iblockstate, j, -1, k, boundingBoxIn);
            }
        }

        spawnPigtificates(world, boundingBoxIn, 2, 1, 2, 1);
        return true;
    }

    protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession)
    {
        return 2;
    }
}

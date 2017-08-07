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

import java.util.List;
import java.util.Random;

public class StructureNetherVillageLibrary extends StructureNetherVillage
{
    public StructureNetherVillageLibrary()
    {
    }

    private StructureNetherVillageLibrary(StructureNetherVillageWell.Controller controller, int componentType, Random rand, StructureBoundingBox boundingBoxIn, EnumFacing facing)
    {
        super(controller, componentType);
        setCoordBaseMode(facing);
        boundingBox = boundingBoxIn;
    }

    public static StructureNetherVillageLibrary createPiece(StructureNetherVillageWell.Controller controller, List<StructureComponent> components, Random rand, int minX, int minY, int minZ, EnumFacing facing, int componentType)
    {
        StructureBoundingBox boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, 9, 9, 6, facing);
        return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(components, boundingBox) == null ? new StructureNetherVillageLibrary(controller, componentType, rand, boundingBox, facing) : null;
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

            boundingBox.offset(0, averageGroundLvl - boundingBox.maxY + 9 - 1, 0);
        }

        IBlockState iblockstate = getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
        IBlockState iblockstate1 = getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
        IBlockState iblockstate2 = getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH));
        IBlockState iblockstate3 = getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST));
        IBlockState iblockstate4 = getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState());
        IBlockState iblockstate5 = getBiomeSpecificBlockState(Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
        IBlockState iblockstate6 = getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
        fillWithBlocks(world, boundingBoxIn, 1, 1, 1, 7, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
        fillWithBlocks(world, boundingBoxIn, 0, 0, 0, 8, 0, 5, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 0, 5, 0, 8, 5, 5, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 0, 6, 1, 8, 6, 4, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 0, 7, 2, 8, 7, 3, iblockstate, iblockstate, false);

        for(int i = -1; i <= 2; ++i)
        {
            for(int j = 0; j <= 8; ++j)
            {
                setBlockState(world, iblockstate1, j, 6 + i, i, boundingBoxIn);
                setBlockState(world, iblockstate2, j, 6 + i, 5 - i, boundingBoxIn);
            }
        }

        fillWithBlocks(world, boundingBoxIn, 0, 1, 0, 0, 1, 5, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 1, 1, 5, 8, 1, 5, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 8, 1, 0, 8, 1, 4, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 2, 1, 0, 7, 1, 0, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 0, 2, 0, 0, 4, 0, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 0, 2, 5, 0, 4, 5, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 8, 2, 5, 8, 4, 5, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 8, 2, 0, 8, 4, 0, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 0, 2, 1, 0, 4, 4, iblockstate4, iblockstate4, false);
        fillWithBlocks(world, boundingBoxIn, 1, 2, 5, 7, 4, 5, iblockstate4, iblockstate4, false);
        fillWithBlocks(world, boundingBoxIn, 8, 2, 1, 8, 4, 4, iblockstate4, iblockstate4, false);
        fillWithBlocks(world, boundingBoxIn, 1, 2, 0, 7, 4, 0, iblockstate4, iblockstate4, false);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 4, 2, 0, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 5, 2, 0, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 6, 2, 0, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 4, 3, 0, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 5, 3, 0, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 6, 3, 0, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 3, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 0, 3, 2, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 0, 3, 3, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 2, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 3, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 8, 3, 2, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 8, 3, 3, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 5, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 3, 2, 5, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 5, 2, 5, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 6, 2, 5, boundingBoxIn);
        fillWithBlocks(world, boundingBoxIn, 1, 4, 1, 7, 4, 1, iblockstate4, iblockstate4, false);
        fillWithBlocks(world, boundingBoxIn, 1, 4, 4, 7, 4, 4, iblockstate4, iblockstate4, false);
        fillWithBlocks(world, boundingBoxIn, 1, 3, 4, 7, 3, 4, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
        setBlockState(world, iblockstate4, 7, 1, 4, boundingBoxIn);
        setBlockState(world, iblockstate3, 7, 1, 3, boundingBoxIn);
        setBlockState(world, iblockstate1, 6, 1, 4, boundingBoxIn);
        setBlockState(world, iblockstate1, 5, 1, 4, boundingBoxIn);
        setBlockState(world, iblockstate1, 4, 1, 4, boundingBoxIn);
        setBlockState(world, iblockstate1, 3, 1, 4, boundingBoxIn);
        setBlockState(world, iblockstate6, 6, 1, 3, boundingBoxIn);
        setBlockState(world, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), 6, 2, 3, boundingBoxIn);
        setBlockState(world, iblockstate6, 4, 1, 3, boundingBoxIn);
        setBlockState(world, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), 4, 2, 3, boundingBoxIn);
        setBlockState(world, Blocks.CRAFTING_TABLE.getDefaultState(), 7, 1, 1, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 1, 1, 0, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 1, 2, 0, boundingBoxIn);
        createVillageDoor(world, boundingBoxIn, rand, 1, 1, 0, EnumFacing.NORTH);

        if(getBlockStateFromPos(world, 1, 0, -1, boundingBoxIn).getMaterial() == Material.AIR && getBlockStateFromPos(world, 1, -1, -1, boundingBoxIn).getMaterial() != Material.AIR)
        {
            setBlockState(world, iblockstate5, 1, 0, -1, boundingBoxIn);

            if(getBlockStateFromPos(world, 1, -1, -1, boundingBoxIn).getBlock() == Blocks.GRASS_PATH)
            {
                setBlockState(world, Blocks.GRASS.getDefaultState(), 1, -1, -1, boundingBoxIn);
            }
        }

        for(int l = 0; l < 6; ++l)
        {
            for(int k = 0; k < 9; ++k)
            {
                clearCurrentPositionBlocksUpwards(world, k, 9, l, boundingBoxIn);
                replaceAirAndLiquidDownwards(world, iblockstate, k, -1, l, boundingBoxIn);
            }
        }

        spawnPigtificates(world, boundingBoxIn, 2, 1, 2, 1);
        return true;
    }

    protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession)
    {
        return 1;
    }
}

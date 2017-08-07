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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.List;
import java.util.Random;

public class StructureNetherVillageWoodHut extends StructureNetherVillage
{
    private boolean isRoofConvex;
    private int tablePosition;

    public StructureNetherVillageWoodHut()
    {
    }

    private StructureNetherVillageWoodHut(StructureNetherVillageWell.Controller controller, int componentType, Random rand, StructureBoundingBox boundingBoxIn, EnumFacing facing)
    {
        super(controller, componentType);
        setCoordBaseMode(facing);
        boundingBox = boundingBoxIn;
        isRoofConvex = rand.nextBoolean();
        tablePosition = rand.nextInt(3);
    }

    @Override
    protected void writeStructureToNBT(NBTTagCompound tagCompound)
    {
        super.writeStructureToNBT(tagCompound);
        tagCompound.setInteger("TablePos", tablePosition);
        tagCompound.setBoolean("Convex", isRoofConvex);
    }

    @Override
    protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
    {
        super.readStructureFromNBT(tagCompound, p_143011_2_);
        tablePosition = tagCompound.getInteger("TablePos");
        isRoofConvex = tagCompound.getBoolean("Convex");
    }

    public static StructureNetherVillageWoodHut createPiece(StructureNetherVillageWell.Controller controller, List<StructureComponent> components, Random rand, int minX, int minY, int minZ, EnumFacing facing, int componentType)
    {
        StructureBoundingBox boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, 4, 6, 5, facing);
        return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(components, boundingBox) == null ? new StructureNetherVillageWoodHut(controller, componentType, rand, boundingBox, facing) : null;
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

            boundingBox.offset(0, averageGroundLvl - boundingBox.maxY + 6 - 1, 0);
        }

        IBlockState iblockstate = getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
        IBlockState iblockstate1 = getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState());
        IBlockState iblockstate2 = getBiomeSpecificBlockState(Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
        IBlockState iblockstate3 = getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
        IBlockState iblockstate4 = getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
        fillWithBlocks(world, boundingBoxIn, 1, 1, 1, 3, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
        fillWithBlocks(world, boundingBoxIn, 0, 0, 0, 3, 0, 4, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 1, 0, 1, 2, 0, 3, Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState(), false);

        if(isRoofConvex)
        {
            fillWithBlocks(world, boundingBoxIn, 1, 4, 1, 2, 4, 3, iblockstate3, iblockstate3, false);
        }
        else
        {
            fillWithBlocks(world, boundingBoxIn, 1, 5, 1, 2, 5, 3, iblockstate3, iblockstate3, false);
        }

        setBlockState(world, iblockstate3, 1, 4, 0, boundingBoxIn);
        setBlockState(world, iblockstate3, 2, 4, 0, boundingBoxIn);
        setBlockState(world, iblockstate3, 1, 4, 4, boundingBoxIn);
        setBlockState(world, iblockstate3, 2, 4, 4, boundingBoxIn);
        setBlockState(world, iblockstate3, 0, 4, 1, boundingBoxIn);
        setBlockState(world, iblockstate3, 0, 4, 2, boundingBoxIn);
        setBlockState(world, iblockstate3, 0, 4, 3, boundingBoxIn);
        setBlockState(world, iblockstate3, 3, 4, 1, boundingBoxIn);
        setBlockState(world, iblockstate3, 3, 4, 2, boundingBoxIn);
        setBlockState(world, iblockstate3, 3, 4, 3, boundingBoxIn);
        fillWithBlocks(world, boundingBoxIn, 0, 1, 0, 0, 3, 0, iblockstate3, iblockstate3, false);
        fillWithBlocks(world, boundingBoxIn, 3, 1, 0, 3, 3, 0, iblockstate3, iblockstate3, false);
        fillWithBlocks(world, boundingBoxIn, 0, 1, 4, 0, 3, 4, iblockstate3, iblockstate3, false);
        fillWithBlocks(world, boundingBoxIn, 3, 1, 4, 3, 3, 4, iblockstate3, iblockstate3, false);
        fillWithBlocks(world, boundingBoxIn, 0, 1, 1, 0, 3, 3, iblockstate1, iblockstate1, false);
        fillWithBlocks(world, boundingBoxIn, 3, 1, 1, 3, 3, 3, iblockstate1, iblockstate1, false);
        fillWithBlocks(world, boundingBoxIn, 1, 1, 0, 2, 3, 0, iblockstate1, iblockstate1, false);
        fillWithBlocks(world, boundingBoxIn, 1, 1, 4, 2, 3, 4, iblockstate1, iblockstate1, false);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 3, 2, 2, boundingBoxIn);

        if(tablePosition > 0)
        {
            setBlockState(world, iblockstate4, tablePosition, 1, 3, boundingBoxIn);
            setBlockState(world, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), tablePosition, 2, 3, boundingBoxIn);
        }

        setBlockState(world, Blocks.AIR.getDefaultState(), 1, 1, 0, boundingBoxIn);
        setBlockState(world, Blocks.AIR.getDefaultState(), 1, 2, 0, boundingBoxIn);
        createVillageDoor(world, boundingBoxIn, rand, 1, 1, 0, EnumFacing.NORTH);

        if(getBlockStateFromPos(world, 1, 0, -1, boundingBoxIn).getMaterial() == Material.AIR && getBlockStateFromPos(world, 1, -1, -1, boundingBoxIn).getMaterial() != Material.AIR)
        {
            setBlockState(world, iblockstate2, 1, 0, -1, boundingBoxIn);

            if(getBlockStateFromPos(world, 1, -1, -1, boundingBoxIn).getBlock() == Blocks.GRASS_PATH)
            {
                setBlockState(world, Blocks.GRASS.getDefaultState(), 1, -1, -1, boundingBoxIn);
            }
        }

        for(int i = 0; i < 5; ++i)
        {
            for(int j = 0; j < 4; ++j)
            {
                clearCurrentPositionBlocksUpwards(world, j, 6, i, boundingBoxIn);
                replaceAirAndLiquidDownwards(world, iblockstate, j, -1, i, boundingBoxIn);
            }
        }

        spawnPigtificates(world, boundingBoxIn, 1, 1, 2, 1);
        return true;
    }
}

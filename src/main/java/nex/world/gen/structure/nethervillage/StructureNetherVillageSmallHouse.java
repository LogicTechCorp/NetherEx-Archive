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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;
import nex.init.NetherExBlocks;

import java.util.List;
import java.util.Random;

public class StructureNetherVillageSmallHouse extends StructureNetherVillage
{
    private boolean isRoofAccessible;

    public StructureNetherVillageSmallHouse()
    {
    }

    private StructureNetherVillageSmallHouse(StructureNetherVillageWell.Controller controller, int componentType, Random rand, StructureBoundingBox boundingBoxIn, EnumFacing facing)
    {
        super(controller, componentType);
        setCoordBaseMode(facing);
        boundingBox = boundingBoxIn;
        isRoofAccessible = rand.nextBoolean();
    }

    @Override
    protected void writeStructureToNBT(NBTTagCompound tagCompound)
    {
        super.writeStructureToNBT(tagCompound);
        tagCompound.setBoolean("Terrace", isRoofAccessible);
    }

    @Override
    protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager templateManager)
    {
        super.readStructureFromNBT(tagCompound, templateManager);
        isRoofAccessible = tagCompound.getBoolean("Terrace");
    }

    public static StructureNetherVillageSmallHouse createPiece(StructureNetherVillageWell.Controller controller, List<StructureComponent> components, Random rand, int minX, int minY, int minZ, EnumFacing facing, int componentType)
    {
        StructureBoundingBox boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, 5, 6, 5, facing);
        return StructureComponent.findIntersecting(components, boundingBox) != null ? null : new StructureNetherVillageSmallHouse(controller, componentType, rand, boundingBox, facing);
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
        fillWithBlocks(world, boundingBoxIn, 0, 0, 0, 4, 0, 4, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 0, 4, 0, 4, 4, 4, iblockstate3, iblockstate3, false);
        fillWithBlocks(world, boundingBoxIn, 1, 4, 1, 3, 4, 3, iblockstate1, iblockstate1, false);
        setBlockState(world, iblockstate, 0, 1, 0, boundingBoxIn);
        setBlockState(world, iblockstate, 0, 2, 0, boundingBoxIn);
        setBlockState(world, iblockstate, 0, 3, 0, boundingBoxIn);
        setBlockState(world, iblockstate, 4, 1, 0, boundingBoxIn);
        setBlockState(world, iblockstate, 4, 2, 0, boundingBoxIn);
        setBlockState(world, iblockstate, 4, 3, 0, boundingBoxIn);
        setBlockState(world, iblockstate, 0, 1, 4, boundingBoxIn);
        setBlockState(world, iblockstate, 0, 2, 4, boundingBoxIn);
        setBlockState(world, iblockstate, 0, 3, 4, boundingBoxIn);
        setBlockState(world, iblockstate, 4, 1, 4, boundingBoxIn);
        setBlockState(world, iblockstate, 4, 2, 4, boundingBoxIn);
        setBlockState(world, iblockstate, 4, 3, 4, boundingBoxIn);
        fillWithBlocks(world, boundingBoxIn, 0, 1, 1, 0, 3, 3, iblockstate1, iblockstate1, false);
        fillWithBlocks(world, boundingBoxIn, 4, 1, 1, 4, 3, 3, iblockstate1, iblockstate1, false);
        fillWithBlocks(world, boundingBoxIn, 1, 1, 4, 3, 3, 4, iblockstate1, iblockstate1, false);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 4, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 4, 2, 2, boundingBoxIn);
        setBlockState(world, iblockstate1, 1, 1, 0, boundingBoxIn);
        setBlockState(world, iblockstate1, 1, 2, 0, boundingBoxIn);
        setBlockState(world, iblockstate1, 1, 3, 0, boundingBoxIn);
        setBlockState(world, iblockstate1, 2, 3, 0, boundingBoxIn);
        setBlockState(world, iblockstate1, 3, 3, 0, boundingBoxIn);
        setBlockState(world, iblockstate1, 3, 2, 0, boundingBoxIn);
        setBlockState(world, iblockstate1, 3, 1, 0, boundingBoxIn);

        if(getBlockStateFromPos(world, 2, 0, -1, boundingBoxIn).getMaterial() == Material.AIR && getBlockStateFromPos(world, 2, -1, -1, boundingBoxIn).getMaterial() != Material.AIR)
        {
            setBlockState(world, iblockstate2, 2, 0, -1, boundingBoxIn);

            if(getBlockStateFromPos(world, 2, -1, -1, boundingBoxIn).getBlock() == NetherExBlocks.BLOCK_NETHERRACK_PATH)
            {
                setBlockState(world, Blocks.NETHERRACK.getDefaultState(), 2, -1, -1, boundingBoxIn);
            }
        }

        fillWithBlocks(world, boundingBoxIn, 1, 1, 1, 3, 3, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);

        if(isRoofAccessible)
        {
            setBlockState(world, iblockstate4, 0, 5, 0, boundingBoxIn);
            setBlockState(world, iblockstate4, 1, 5, 0, boundingBoxIn);
            setBlockState(world, iblockstate4, 2, 5, 0, boundingBoxIn);
            setBlockState(world, iblockstate4, 3, 5, 0, boundingBoxIn);
            setBlockState(world, iblockstate4, 4, 5, 0, boundingBoxIn);
            setBlockState(world, iblockstate4, 0, 5, 4, boundingBoxIn);
            setBlockState(world, iblockstate4, 1, 5, 4, boundingBoxIn);
            setBlockState(world, iblockstate4, 2, 5, 4, boundingBoxIn);
            setBlockState(world, iblockstate4, 3, 5, 4, boundingBoxIn);
            setBlockState(world, iblockstate4, 4, 5, 4, boundingBoxIn);
            setBlockState(world, iblockstate4, 4, 5, 1, boundingBoxIn);
            setBlockState(world, iblockstate4, 4, 5, 2, boundingBoxIn);
            setBlockState(world, iblockstate4, 4, 5, 3, boundingBoxIn);
            setBlockState(world, iblockstate4, 0, 5, 1, boundingBoxIn);
            setBlockState(world, iblockstate4, 0, 5, 2, boundingBoxIn);
            setBlockState(world, iblockstate4, 0, 5, 3, boundingBoxIn);
        }

        if(isRoofAccessible)
        {
            IBlockState iblockstate5 = Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.SOUTH);
            setBlockState(world, iblockstate5, 3, 1, 3, boundingBoxIn);
            setBlockState(world, iblockstate5, 3, 2, 3, boundingBoxIn);
            setBlockState(world, iblockstate5, 3, 3, 3, boundingBoxIn);
            setBlockState(world, iblockstate5, 3, 4, 3, boundingBoxIn);
        }

        placeTorch(world, EnumFacing.NORTH, 2, 3, 1, boundingBoxIn);

        for(int j = 0; j < 5; ++j)
        {
            for(int i = 0; i < 5; ++i)
            {
                clearCurrentPositionBlocksUpwards(world, i, 6, j, boundingBoxIn);
                replaceAirAndLiquidDownwards(world, iblockstate, i, -1, j, boundingBoxIn);
            }
        }

        spawnPigtificates(world, boundingBoxIn, 1, 1, 2, 1);
        return true;
    }
}

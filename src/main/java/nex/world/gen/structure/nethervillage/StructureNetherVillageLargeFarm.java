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

import net.minecraft.block.Block;
import net.minecraft.block.BlockNetherWart;
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

public class StructureNetherVillageLargeFarm extends StructureNetherVillage
{
    private Block cropA;
    private Block cropB;
    private Block cropC;
    private Block cropD;

    public StructureNetherVillageLargeFarm()
    {
    }

    private StructureNetherVillageLargeFarm(StructureNetherVillageWell.Controller controller, int componentType, Random rand, StructureBoundingBox boundingBoxIn, EnumFacing facing)
    {
        super(controller, componentType);
        setCoordBaseMode(facing);
        boundingBox = boundingBoxIn;
        cropA = getRandomCropType(rand);
        cropB = getRandomCropType(rand);
        cropC = getRandomCropType(rand);
        cropD = getRandomCropType(rand);
    }

    @Override
    protected void writeStructureToNBT(NBTTagCompound tagCompound)
    {
        super.writeStructureToNBT(tagCompound);
        tagCompound.setInteger("CropA", Block.REGISTRY.getIDForObject(cropA));
        tagCompound.setInteger("CropB", Block.REGISTRY.getIDForObject(cropB));
        tagCompound.setInteger("CropC", Block.REGISTRY.getIDForObject(cropC));
        tagCompound.setInteger("CropD", Block.REGISTRY.getIDForObject(cropD));
    }

    @Override
    protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager templateManager)
    {
        super.readStructureFromNBT(tagCompound, templateManager);
        cropA = Block.getBlockById(tagCompound.getInteger("CropA"));
        cropB = Block.getBlockById(tagCompound.getInteger("CropB"));
        cropC = Block.getBlockById(tagCompound.getInteger("CropC"));
        cropD = Block.getBlockById(tagCompound.getInteger("CropD"));
    }

    private Block getRandomCropType(Random rand)
    {
        switch(rand.nextInt(10))
        {
            default:
                return Blocks.NETHER_WART;
        }
    }

    public static StructureNetherVillageLargeFarm createPiece(StructureNetherVillageWell.Controller controller, List<StructureComponent> components, Random rand, int minX, int minY, int minZ, EnumFacing facing, int componentType)
    {
        StructureBoundingBox boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, 13, 4, 9, facing);
        return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(components, boundingBox) == null ? new StructureNetherVillageLargeFarm(controller, componentType, rand, boundingBox, facing) : null;
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

            boundingBox.offset(0, averageGroundLvl - boundingBox.maxY + 4 - 1, 0);
        }

        IBlockState iblockstate = getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
        fillWithBlocks(world, boundingBoxIn, 0, 1, 0, 12, 4, 8, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
        fillWithBlocks(world, boundingBoxIn, 1, 0, 1, 2, 0, 7, NetherExBlocks.BLOCK_SAND_SOUL_TILLED.getDefaultState(), NetherExBlocks.BLOCK_SAND_SOUL_TILLED.getDefaultState(), false);
        fillWithBlocks(world, boundingBoxIn, 4, 0, 1, 5, 0, 7, NetherExBlocks.BLOCK_SAND_SOUL_TILLED.getDefaultState(), NetherExBlocks.BLOCK_SAND_SOUL_TILLED.getDefaultState(), false);
        fillWithBlocks(world, boundingBoxIn, 7, 0, 1, 8, 0, 7, NetherExBlocks.BLOCK_SAND_SOUL_TILLED.getDefaultState(), NetherExBlocks.BLOCK_SAND_SOUL_TILLED.getDefaultState(), false);
        fillWithBlocks(world, boundingBoxIn, 10, 0, 1, 11, 0, 7, NetherExBlocks.BLOCK_SAND_SOUL_TILLED.getDefaultState(), NetherExBlocks.BLOCK_SAND_SOUL_TILLED.getDefaultState(), false);
        fillWithBlocks(world, boundingBoxIn, 0, 0, 0, 0, 0, 8, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 6, 0, 0, 6, 0, 8, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 12, 0, 0, 12, 0, 8, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 1, 0, 0, 11, 0, 0, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 1, 0, 8, 11, 0, 8, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 3, 0, 1, 3, 0, 7, NetherExBlocks.FLUID_ICHOR.getDefaultState(), NetherExBlocks.FLUID_ICHOR.getDefaultState(), false);
        fillWithBlocks(world, boundingBoxIn, 9, 0, 1, 9, 0, 7, NetherExBlocks.FLUID_ICHOR.getDefaultState(), NetherExBlocks.FLUID_ICHOR.getDefaultState(), false);

        for(int i = 1; i <= 7; ++i)
        {
            if(cropA instanceof BlockNetherWart)
            {
                setBlockState(world, cropA.getDefaultState().withProperty(BlockNetherWart.AGE, rand.nextInt(3)), 1, 1, i, boundingBoxIn);
                setBlockState(world, cropA.getDefaultState().withProperty(BlockNetherWart.AGE, rand.nextInt(3)), 2, 1, i, boundingBoxIn);
            }
            if(cropB instanceof BlockNetherWart)
            {
                setBlockState(world, cropB.getDefaultState().withProperty(BlockNetherWart.AGE, rand.nextInt(3)), 4, 1, i, boundingBoxIn);
                setBlockState(world, cropB.getDefaultState().withProperty(BlockNetherWart.AGE, rand.nextInt(3)), 5, 1, i, boundingBoxIn);
            }
            if(cropC instanceof BlockNetherWart)
            {
                setBlockState(world, cropC.getDefaultState().withProperty(BlockNetherWart.AGE, rand.nextInt(3)), 7, 1, i, boundingBoxIn);
                setBlockState(world, cropC.getDefaultState().withProperty(BlockNetherWart.AGE, rand.nextInt(3)), 8, 1, i, boundingBoxIn);
            }
            if(cropD instanceof BlockNetherWart)
            {
                setBlockState(world, cropD.getDefaultState().withProperty(BlockNetherWart.AGE, rand.nextInt(3)), 10, 1, i, boundingBoxIn);
                setBlockState(world, cropD.getDefaultState().withProperty(BlockNetherWart.AGE, rand.nextInt(3)), 11, 1, i, boundingBoxIn);
            }
        }

        for(int j2 = 0; j2 < 9; ++j2)
        {
            for(int k2 = 0; k2 < 13; ++k2)
            {
                clearCurrentPositionBlocksUpwards(world, k2, 4, j2, boundingBoxIn);
                replaceAirAndLiquidDownwards(world, Blocks.NETHERRACK.getDefaultState(), k2, -1, j2, boundingBoxIn);
            }
        }

        return true;
    }
}

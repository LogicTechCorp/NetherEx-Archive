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
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.List;
import java.util.Random;

public class StructureNetherVillageField1 extends StructureNetherVillage
{
    private Block cropTypeA;
    private Block cropTypeB;
    private Block cropTypeC;
    private Block cropTypeD;

    public StructureNetherVillageField1()
    {
    }

    public StructureNetherVillageField1(StructureNetherVillageWell.Start start, int type, Random rand, StructureBoundingBox p_i45570_4_, EnumFacing facing)
    {
        super(start, type);
        this.setCoordBaseMode(facing);
        this.boundingBox = p_i45570_4_;
        this.cropTypeA = this.getRandomCropType(rand);
        this.cropTypeB = this.getRandomCropType(rand);
        this.cropTypeC = this.getRandomCropType(rand);
        this.cropTypeD = this.getRandomCropType(rand);
    }

    protected void writeStructureToNBT(NBTTagCompound tagCompound)
    {
        super.writeStructureToNBT(tagCompound);
        tagCompound.setInteger("CA", Block.REGISTRY.getIDForObject(this.cropTypeA));
        tagCompound.setInteger("CB", Block.REGISTRY.getIDForObject(this.cropTypeB));
        tagCompound.setInteger("CC", Block.REGISTRY.getIDForObject(this.cropTypeC));
        tagCompound.setInteger("CD", Block.REGISTRY.getIDForObject(this.cropTypeD));
    }

    protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
    {
        super.readStructureFromNBT(tagCompound, p_143011_2_);
        this.cropTypeA = Block.getBlockById(tagCompound.getInteger("CA"));
        this.cropTypeB = Block.getBlockById(tagCompound.getInteger("CB"));
        this.cropTypeC = Block.getBlockById(tagCompound.getInteger("CC"));
        this.cropTypeD = Block.getBlockById(tagCompound.getInteger("CD"));

        if(!(this.cropTypeA instanceof BlockCrops))
        {
            this.cropTypeA = Blocks.WHEAT;
        }

        if(!(this.cropTypeB instanceof BlockCrops))
        {
            this.cropTypeB = Blocks.CARROTS;
        }

        if(!(this.cropTypeC instanceof BlockCrops))
        {
            this.cropTypeC = Blocks.POTATOES;
        }

        if(!(this.cropTypeD instanceof BlockCrops))
        {
            this.cropTypeD = Blocks.BEETROOTS;
        }
    }

    private Block getRandomCropType(Random rand)
    {
        switch(rand.nextInt(10))
        {
            case 0:
            case 1:
                return Blocks.CARROTS;
            case 2:
            case 3:
                return Blocks.POTATOES;
            case 4:
                return Blocks.BEETROOTS;
            default:
                return Blocks.WHEAT;
        }
    }

    public static StructureNetherVillageField1 createPiece(StructureNetherVillageWell.Start start, List<StructureComponent> p_175851_1_, Random rand, int p_175851_3_, int p_175851_4_, int p_175851_5_, EnumFacing facing, int p_175851_7_)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175851_3_, p_175851_4_, p_175851_5_, 0, 0, 0, 13, 4, 9, facing);
        return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175851_1_, structureboundingbox) == null ? new StructureNetherVillageField1(start, p_175851_7_, rand, structureboundingbox, facing) : null;
    }

    public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
    {
        if(this.averageGroundLvl < 0)
        {
            this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

            if(this.averageGroundLvl < 0)
            {
                return true;
            }

            this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 4 - 1, 0);
        }

        IBlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 12, 4, 8, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 1, 5, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 0, 1, 8, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 0, 1, 11, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 8, iblockstate, iblockstate, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 0, 6, 0, 8, iblockstate, iblockstate, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 0, 0, 12, 0, 8, iblockstate, iblockstate, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 11, 0, 0, iblockstate, iblockstate, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 8, 11, 0, 8, iblockstate, iblockstate, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 1, 3, 0, 7, Blocks.WATER.getDefaultState(), Blocks.WATER.getDefaultState(), false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 0, 1, 9, 0, 7, Blocks.WATER.getDefaultState(), Blocks.WATER.getDefaultState(), false);

        for(int i = 1; i <= 7; ++i)
        {
            int j = ((BlockCrops) this.cropTypeA).getMaxAge();
            int k = j / 3;
            this.setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getInt(randomIn, k, j)), 1, 1, i, structureBoundingBoxIn);
            this.setBlockState(worldIn, this.cropTypeA.getStateFromMeta(MathHelper.getInt(randomIn, k, j)), 2, 1, i, structureBoundingBoxIn);
            int l = ((BlockCrops) this.cropTypeB).getMaxAge();
            int i1 = l / 3;
            this.setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getInt(randomIn, i1, l)), 4, 1, i, structureBoundingBoxIn);
            this.setBlockState(worldIn, this.cropTypeB.getStateFromMeta(MathHelper.getInt(randomIn, i1, l)), 5, 1, i, structureBoundingBoxIn);
            int j1 = ((BlockCrops) this.cropTypeC).getMaxAge();
            int k1 = j1 / 3;
            this.setBlockState(worldIn, this.cropTypeC.getStateFromMeta(MathHelper.getInt(randomIn, k1, j1)), 7, 1, i, structureBoundingBoxIn);
            this.setBlockState(worldIn, this.cropTypeC.getStateFromMeta(MathHelper.getInt(randomIn, k1, j1)), 8, 1, i, structureBoundingBoxIn);
            int l1 = ((BlockCrops) this.cropTypeD).getMaxAge();
            int i2 = l1 / 3;
            this.setBlockState(worldIn, this.cropTypeD.getStateFromMeta(MathHelper.getInt(randomIn, i2, l1)), 10, 1, i, structureBoundingBoxIn);
            this.setBlockState(worldIn, this.cropTypeD.getStateFromMeta(MathHelper.getInt(randomIn, i2, l1)), 11, 1, i, structureBoundingBoxIn);
        }

        for(int j2 = 0; j2 < 9; ++j2)
        {
            for(int k2 = 0; k2 < 13; ++k2)
            {
                this.clearCurrentPositionBlocksUpwards(worldIn, k2, 4, j2, structureBoundingBoxIn);
                this.replaceAirAndLiquidDownwards(worldIn, Blocks.DIRT.getDefaultState(), k2, -1, j2, structureBoundingBoxIn);
            }
        }

        return true;
    }
}

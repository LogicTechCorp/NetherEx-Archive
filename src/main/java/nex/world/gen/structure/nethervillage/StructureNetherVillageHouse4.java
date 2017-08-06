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

import java.util.List;
import java.util.Random;

public class StructureNetherVillageHouse4 extends StructureNetherVillage
{
    private boolean isRoofAccessible;

    public StructureNetherVillageHouse4()
    {
    }

    public StructureNetherVillageHouse4(StructureNetherVillageWell.Start start, int p_i45566_2_, Random rand, StructureBoundingBox p_i45566_4_, EnumFacing facing)
    {
        super(start, p_i45566_2_);
        this.setCoordBaseMode(facing);
        this.boundingBox = p_i45566_4_;
        this.isRoofAccessible = rand.nextBoolean();
    }

    /**
     * (abstract) Helper method to write subclass data to NBT
     */
    protected void writeStructureToNBT(NBTTagCompound tagCompound)
    {
        super.writeStructureToNBT(tagCompound);
        tagCompound.setBoolean("Terrace", this.isRoofAccessible);
    }

    /**
     * (abstract) Helper method to read subclass data from NBT
     */
    protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
    {
        super.readStructureFromNBT(tagCompound, p_143011_2_);
        this.isRoofAccessible = tagCompound.getBoolean("Terrace");
    }

    public static StructureNetherVillageHouse4 createPiece(StructureNetherVillageWell.Start start, List<StructureComponent> p_175858_1_, Random rand, int p_175858_3_, int p_175858_4_, int p_175858_5_, EnumFacing facing, int p_175858_7_)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175858_3_, p_175858_4_, p_175858_5_, 0, 0, 0, 5, 6, 5, facing);
        return StructureComponent.findIntersecting(p_175858_1_, structureboundingbox) != null ? null : new StructureNetherVillageHouse4(start, p_175858_7_, rand, structureboundingbox, facing);
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
     * Mineshafts at the end, it adds Fences...
     */
    public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
    {
        if(this.averageGroundLvl < 0)
        {
            this.averageGroundLvl = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);

            if(this.averageGroundLvl < 0)
            {
                return true;
            }

            this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 6 - 1, 0);
        }

        IBlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
        IBlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState());
        IBlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
        IBlockState iblockstate3 = this.getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
        IBlockState iblockstate4 = this.getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 0, 4, iblockstate, iblockstate, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 4, 4, 4, iblockstate3, iblockstate3, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 3, 4, 3, iblockstate1, iblockstate1, false);
        this.setBlockState(worldIn, iblockstate, 0, 1, 0, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate, 0, 2, 0, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate, 0, 3, 0, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate, 4, 1, 0, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate, 4, 2, 0, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate, 4, 3, 0, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate, 0, 1, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate, 0, 2, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate, 0, 3, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate, 4, 1, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate, 4, 2, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate, 4, 3, 4, structureBoundingBoxIn);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, iblockstate1, iblockstate1, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, iblockstate1, iblockstate1, false);
        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 3, 3, 4, iblockstate1, iblockstate1, false);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 4, structureBoundingBoxIn);
        this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 2, 2, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate1, 1, 1, 0, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate1, 1, 2, 0, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate1, 1, 3, 0, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate1, 2, 3, 0, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate1, 3, 3, 0, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate1, 3, 2, 0, structureBoundingBoxIn);
        this.setBlockState(worldIn, iblockstate1, 3, 1, 0, structureBoundingBoxIn);

        if(this.getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR)
        {
            this.setBlockState(worldIn, iblockstate2, 2, 0, -1, structureBoundingBoxIn);

            if(this.getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getBlock() == Blocks.GRASS_PATH)
            {
                this.setBlockState(worldIn, Blocks.GRASS.getDefaultState(), 2, -1, -1, structureBoundingBoxIn);
            }
        }

        this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 3, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);

        if(this.isRoofAccessible)
        {
            this.setBlockState(worldIn, iblockstate4, 0, 5, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate4, 1, 5, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate4, 2, 5, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate4, 3, 5, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate4, 4, 5, 0, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate4, 0, 5, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate4, 1, 5, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate4, 2, 5, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate4, 3, 5, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate4, 4, 5, 4, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate4, 4, 5, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate4, 4, 5, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate4, 4, 5, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate4, 0, 5, 1, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate4, 0, 5, 2, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate4, 0, 5, 3, structureBoundingBoxIn);
        }

        if(this.isRoofAccessible)
        {
            IBlockState iblockstate5 = Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.SOUTH);
            this.setBlockState(worldIn, iblockstate5, 3, 1, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate5, 3, 2, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate5, 3, 3, 3, structureBoundingBoxIn);
            this.setBlockState(worldIn, iblockstate5, 3, 4, 3, structureBoundingBoxIn);
        }

        this.placeTorch(worldIn, EnumFacing.NORTH, 2, 3, 1, structureBoundingBoxIn);

        for(int j = 0; j < 5; ++j)
        {
            for(int i = 0; i < 5; ++i)
            {
                this.clearCurrentPositionBlocksUpwards(worldIn, i, 6, j, structureBoundingBoxIn);
                this.replaceAirAndLiquidDownwards(worldIn, iblockstate, i, -1, j, structureBoundingBoxIn);
            }
        }

        this.spawnVillagers(worldIn, structureBoundingBoxIn, 1, 1, 2, 1);
        return true;
    }
}

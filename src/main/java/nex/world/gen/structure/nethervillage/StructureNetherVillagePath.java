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

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;
import nex.util.WorldGenUtil;

import java.util.List;
import java.util.Random;

public class StructureNetherVillagePath extends StructureNetherVillage.Road
{
    private int length;

    public StructureNetherVillagePath()
    {
    }

    public StructureNetherVillagePath(StructureNetherVillageWell.Start start, int type, Random rand, StructureBoundingBox boundingBox, EnumFacing facing)
    {
        super(start, type);
        this.setCoordBaseMode(facing);
        this.boundingBox = boundingBox;
        this.length = Math.max(boundingBox.getXSize(), boundingBox.getZSize());
    }

    @Override
    protected void writeStructureToNBT(NBTTagCompound tagCompound)
    {
        super.writeStructureToNBT(tagCompound);
        tagCompound.setInteger("Length", this.length);
    }

    @Override
    protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager templateManager)
    {
        super.readStructureFromNBT(tagCompound, templateManager);
        this.length = tagCompound.getInteger("Length");
    }

    @Override
    public void buildComponent(StructureComponent component, List<StructureComponent> components, Random rand)
    {
        boolean flag = false;

        for(int i = rand.nextInt(5); i < this.length - 8; i += 2 + rand.nextInt(5))
        {
            StructureComponent structurecomponent = this.getNextComponentNN((StructureNetherVillageWell.Start) component, components, rand, 0, i);

            if(structurecomponent != null)
            {
                i += Math.max(structurecomponent.getBoundingBox().getXSize(), structurecomponent.getBoundingBox().getZSize());
                flag = true;
            }
        }

        for(int j = rand.nextInt(5); j < this.length - 8; j += 2 + rand.nextInt(5))
        {
            StructureComponent structurecomponent1 = this.getNextComponentPP((StructureNetherVillageWell.Start) component, components, rand, 0, j);

            if(structurecomponent1 != null)
            {
                j += Math.max(structurecomponent1.getBoundingBox().getXSize(), structurecomponent1.getBoundingBox().getZSize());
                flag = true;
            }
        }

        EnumFacing enumfacing = this.getCoordBaseMode();

        if(flag && rand.nextInt(3) > 0 && enumfacing != null)
        {
            switch(enumfacing)
            {
                case NORTH:
                default:
                    generateAndAddRoadPiece((StructureNetherVillageWell.Start) component, components, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, this.getComponentType());
                    break;
                case SOUTH:
                    generateAndAddRoadPiece((StructureNetherVillageWell.Start) component, components, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.WEST, this.getComponentType());
                    break;
                case WEST:
                    generateAndAddRoadPiece((StructureNetherVillageWell.Start) component, components, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                    break;
                case EAST:
                    generateAndAddRoadPiece((StructureNetherVillageWell.Start) component, components, rand, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
            }
        }

        if(flag && rand.nextInt(3) > 0 && enumfacing != null)
        {
            switch(enumfacing)
            {
                case NORTH:
                default:
                    generateAndAddRoadPiece((StructureNetherVillageWell.Start) component, components, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, this.getComponentType());
                    break;
                case SOUTH:
                    generateAndAddRoadPiece((StructureNetherVillageWell.Start) component, components, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.EAST, this.getComponentType());
                    break;
                case WEST:
                    generateAndAddRoadPiece((StructureNetherVillageWell.Start) component, components, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                    break;
                case EAST:
                    generateAndAddRoadPiece((StructureNetherVillageWell.Start) component, components, rand, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
            }
        }
    }

    public static StructureBoundingBox findPieceBox(StructureNetherVillageWell.Start start, List<StructureComponent> p_175848_1_, Random rand, int p_175848_3_, int p_175848_4_, int p_175848_5_, EnumFacing facing)
    {
        for(int i = 7 * MathHelper.getInt(rand, 3, 5); i >= 7; i -= 7)
        {
            StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175848_3_, p_175848_4_, p_175848_5_, 0, 0, 0, 3, 3, i, facing);

            if(StructureComponent.findIntersecting(p_175848_1_, structureboundingbox) == null)
            {
                return structureboundingbox;
            }
        }

        return null;
    }

    @Override
    public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
    {
        IBlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.GRASS_PATH.getDefaultState());
        IBlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState());
        IBlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.GRAVEL.getDefaultState());
        IBlockState iblockstate3 = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());

        for(int i = this.boundingBox.minX; i <= this.boundingBox.maxX; ++i)
        {
            for(int j = this.boundingBox.minZ; j <= this.boundingBox.maxZ; ++j)
            {
                BlockPos blockpos = new BlockPos(i, 32, j);

                if(structureBoundingBoxIn.isVecInside(blockpos))
                {
                    blockpos = WorldGenUtil.getSolidBlockBelow(worldIn, blockpos, 80);

                    if(blockpos.getY() < worldIn.getSeaLevel())
                    {
                        blockpos = new BlockPos(blockpos.getX(), worldIn.getSeaLevel(), blockpos.getZ());
                    }

                    while(blockpos.getY() >= worldIn.getSeaLevel())
                    {
                        IBlockState iblockstate4 = worldIn.getBlockState(blockpos);

                        if(iblockstate4.getBlock() == Blocks.GRASS && worldIn.isAirBlock(blockpos.up()))
                        {
                            worldIn.setBlockState(blockpos, iblockstate, 2);
                            break;
                        }

                        if(iblockstate4.getMaterial().isLiquid())
                        {
                            worldIn.setBlockState(blockpos, iblockstate1, 2);
                            break;
                        }

                        if(iblockstate4.getBlock() == Blocks.SAND || iblockstate4.getBlock() == Blocks.SANDSTONE || iblockstate4.getBlock() == Blocks.RED_SANDSTONE)
                        {
                            worldIn.setBlockState(blockpos, iblockstate2, 2);
                            worldIn.setBlockState(blockpos.down(), iblockstate3, 2);
                            break;
                        }

                        blockpos = blockpos.down();
                    }
                }
            }
        }

        return true;
    }
}

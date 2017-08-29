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
import nex.init.NetherExBlocks;
import nex.util.WorldGenUtil;

import java.util.List;
import java.util.Random;

public class ComponentPath extends ComponentNetherVillage.Road
{
    private int length;

    public ComponentPath()
    {
    }

    public ComponentPath(ComponentWell.Controller controller, int componentType, Random rand, StructureBoundingBox boundingBoxIn, EnumFacing facing)
    {
        super(controller, componentType, facing);
        boundingBox = boundingBoxIn;
        length = Math.max(boundingBoxIn.getXSize(), boundingBoxIn.getZSize());
    }

    @Override
    protected void writeStructureToNBT(NBTTagCompound tagCompound)
    {
        super.writeStructureToNBT(tagCompound);
        tagCompound.setInteger("Length", length);
    }

    @Override
    protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager templateManager)
    {
        super.readStructureFromNBT(tagCompound, templateManager);
        length = tagCompound.getInteger("Length");
    }

    @Override
    public void buildComponent(StructureComponent component, List<StructureComponent> components, Random rand)
    {
        boolean flag = false;

        for(int i = rand.nextInt(5); i < length - 8; i += 2 + rand.nextInt(5))
        {
            StructureComponent structureComponent = getNextComponentNN((ComponentWell.Controller) component, components, rand, 0, i);

            if(structureComponent != null)
            {
                i += Math.max(structureComponent.getBoundingBox().getXSize(), structureComponent.getBoundingBox().getZSize());
                flag = true;
            }
        }

        for(int j = rand.nextInt(5); j < length - 8; j += 2 + rand.nextInt(5))
        {
            StructureComponent structureComponent = getNextComponentPP((ComponentWell.Controller) component, components, rand, 0, j);

            if(structureComponent != null)
            {
                j += Math.max(structureComponent.getBoundingBox().getXSize(), structureComponent.getBoundingBox().getZSize());
                flag = true;
            }
        }

        EnumFacing facing = this.getCoordBaseMode();

        if(flag && rand.nextInt(3) > 0 && facing != null)
        {
            switch(facing)
            {
                case NORTH:
                default:
                    generateAndAddRoadPiece((ComponentWell.Controller) component, components, rand, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ, EnumFacing.WEST, getComponentType());
                    break;
                case SOUTH:
                    generateAndAddRoadPiece((ComponentWell.Controller) component, components, rand, boundingBox.minX - 1, boundingBox.minY, boundingBox.maxZ - 2, EnumFacing.WEST, getComponentType());
                    break;
                case WEST:
                    generateAndAddRoadPiece((ComponentWell.Controller) component, components, rand, boundingBox.minX, boundingBox.minY, boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
                    break;
                case EAST:
                    generateAndAddRoadPiece((ComponentWell.Controller) component, components, rand, boundingBox.maxX - 2, boundingBox.minY, boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
            }
        }

        if(flag && rand.nextInt(3) > 0 && facing != null)
        {
            switch(facing)
            {
                case NORTH:
                default:
                    generateAndAddRoadPiece((ComponentWell.Controller) component, components, rand, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ, EnumFacing.EAST, getComponentType());
                    break;
                case SOUTH:
                    generateAndAddRoadPiece((ComponentWell.Controller) component, components, rand, boundingBox.maxX + 1, boundingBox.minY, boundingBox.maxZ - 2, EnumFacing.EAST, getComponentType());
                    break;
                case WEST:
                    generateAndAddRoadPiece((ComponentWell.Controller) component, components, rand, boundingBox.minX, boundingBox.minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
                    break;
                case EAST:
                    generateAndAddRoadPiece((ComponentWell.Controller) component, components, rand, boundingBox.maxX - 2, boundingBox.minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
            }
        }
    }

    public static StructureBoundingBox findPieceBox(ComponentWell.Controller controller, List<StructureComponent> components, Random rand, int minX, int minY, int minZ, EnumFacing facing)
    {
        for(int i = 7 * MathHelper.getInt(rand, 3, 5); i >= 7; i -= 7)
        {
            StructureBoundingBox boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, 3, 3, i, facing);

            if(StructureComponent.findIntersecting(components, boundingBox) == null)
            {
                return boundingBox;
            }
        }

        return null;
    }

    @Override
    public boolean addComponentParts(World world, Random rand, StructureBoundingBox boundingBoxIn)
    {
        IBlockState netherrackPath = NetherExBlocks.BLOCK_NETHERRACK_PATH.getDefaultState();
        IBlockState netherBrick = Blocks.NETHER_BRICK.getDefaultState();

        for(int i = boundingBox.minX; i <= boundingBox.maxX; ++i)
        {
            for(int j = boundingBox.minZ; j <= boundingBox.maxZ; ++j)
            {
                BlockPos pos = new BlockPos(i, world.getSeaLevel() + 1, j);

                if(boundingBoxIn.isVecInside(pos))
                {
                    pos = WorldGenUtil.getSolidBlockBelow(world, pos, 125);

                    if(pos.getY() < world.getSeaLevel())
                    {
                        pos = new BlockPos(pos.getX(), world.getSeaLevel(), pos.getZ());
                    }
                    while(pos.getY() >= world.getSeaLevel())
                    {
                        IBlockState checkState = world.getBlockState(pos);

                        if(checkState.getBlock() == Blocks.NETHERRACK && world.isAirBlock(pos.up()))
                        {
                            world.setBlockState(pos, netherrackPath, 2);
                            break;
                        }
                        if(checkState.getMaterial().isLiquid())
                        {
                            world.setBlockState(pos, netherBrick, 2);
                            break;
                        }

                        pos = pos.down();
                    }
                }
            }
        }

        return true;
    }
}

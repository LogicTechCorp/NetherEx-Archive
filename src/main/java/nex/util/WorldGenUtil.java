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

package nex.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.BlockRotationProcessor;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import nex.block.BlockUrnOfSorrow;
import nex.tileentity.TileEntityUrnOfSorrow;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;
import java.util.Set;

@SuppressWarnings("ConstantConditions")
public class WorldGenUtil
{
    private static final Field FIELD_BLOCKS = ReflectionHelper.findField(Template.class, "field_186270_a", "blocks");
    private static final Field FIELD_ENTITIES = ReflectionHelper.findField(Template.class, "field_186271_b", "entities");
    private static final Method METHOD_ADD_ENTITIES_TO_WORLD = ReflectionHelper.findMethod(Template.class, "addEntitiesToWorld", "func_186263_a", World.class, BlockPos.class, Mirror.class, Rotation.class, StructureBoundingBox.class);

    public static BlockPos getSuitableGroundPos(World world, BlockPos pos, Set<IBlockState> allowedBlocks, BlockPos structureSize, float percentage)
    {
        label_while:
        while(pos.getY() > 32)
        {
            float sizeX = structureSize.getX();
            float sizeZ = structureSize.getZ();

            int topBlocks = 0;

            for(int x = 0; x <= MathHelper.abs(sizeX); x++)
            {
                for(int z = 0; z <= MathHelper.abs(sizeZ); z++)
                {
                    int posX = (int) (sizeX > 0 ? sizeX - x : sizeX + x);
                    int posZ = (int) (sizeZ > 0 ? sizeZ - z : sizeZ + z);

                    BlockPos newPos = pos.add(posX, 0, posZ);
                    IBlockState state = world.getBlockState(newPos);

                    if(allowedBlocks.contains(state))
                    {
                        if(world.getBlockState(newPos.up()).getMaterial().isReplaceable() && !world.getBlockState(newPos.down()).getMaterial().isReplaceable())
                        {
                            topBlocks++;
                        }
                    }
                    else if(state != Blocks.AIR.getDefaultState())
                    {
                        pos = pos.down();
                        continue label_while;
                    }
                }
            }

            if(topBlocks >= MathHelper.abs(sizeX * sizeZ) * percentage)
            {
                return pos;
            }

            pos = pos.down();
        }

        return BlockPos.ORIGIN;
    }

    public static BlockPos getSuitableWallPos(World world, BlockPos pos, BlockPos structureSize, float percentage)
    {
        while(pos.getY() > 32)
        {
            float sizeX = structureSize.getX();
            float sizeZ = structureSize.getZ();
            float sizeY = structureSize.getY();

            int wallBlocks = 0;

            for(int x = 0; x <= MathHelper.abs(sizeX); x++)
            {
                for(int z = 0; z <= MathHelper.abs(sizeZ); z++)
                {
                    for(int y = 0; y <= sizeY; y++)
                    {
                        int posX = (int) (sizeX > 0 ? sizeX - x : sizeX + x);
                        int posZ = (int) (sizeZ > 0 ? sizeZ - z : sizeZ + z);

                        BlockPos newPos = pos.add(posX, y, posZ);

                        if(world.getBlockState(newPos).getBlock().isBlockSolid(world, newPos, EnumFacing.UP))
                        {
                            wallBlocks++;
                        }
                    }
                }
            }

            if(wallBlocks >= MathHelper.abs(sizeX * sizeY * sizeZ) * percentage)
            {
                return pos;
            }

            pos = pos.down();
        }

        return BlockPos.ORIGIN;
    }

    public static BlockPos getSuitableCeilingPos(World world, BlockPos pos, BlockPos structureSize)
    {
        label_while:
        while(pos.getY() < 112)
        {
            float sizeX = structureSize.getX();
            float sizeZ = structureSize.getZ();
            float sizeY = structureSize.getY();

            int ceilingBlocks = 0;
            int airBlocks = 0;

            for(int x = 0; x <= MathHelper.abs(sizeX); x++)
            {
                for(int z = 0; z <= MathHelper.abs(sizeZ); z++)
                {
                    for(int y = 0; y <= sizeY; y++)
                    {
                        int posX = (int) (sizeX > 0 ? sizeX - x : sizeX + x);
                        int posZ = (int) (sizeZ > 0 ? sizeZ - z : sizeZ + z);

                        BlockPos newPos = pos.add(posX, -y, posZ);

                        if(y == 0)
                        {
                            if(world.getBlockState(newPos).getBlock().isBlockSolid(world, newPos, EnumFacing.UP))
                            {
                                ceilingBlocks++;
                            }
                            else
                            {
                                pos = pos.up();
                                continue label_while;
                            }
                        }
                        else
                        {
                            if(world.getBlockState(newPos).getBlock().isReplaceable(world, newPos))
                            {
                                airBlocks++;
                            }
                            else
                            {
                                pos = pos.up();
                                continue label_while;
                            }

                        }
                    }
                }
            }

            if(airBlocks >= MathHelper.abs(sizeX * (sizeY - 1) * sizeZ) && ceilingBlocks >= MathHelper.abs(sizeX * sizeZ))
            {
                return pos.add(0, -sizeY, 0);
            }

            pos = pos.up();
        }

        return BlockPos.ORIGIN;
    }

    public static BlockPos getSuitableAirPos(World world, BlockPos pos, BlockPos structureSize)
    {
        label_while:
        while(pos.getY() > 32)
        {
            float sizeX = structureSize.getX();
            float sizeZ = structureSize.getZ();
            float sizeY = structureSize.getY();

            int airBlocks = 0;

            for(int x = 0; x <= MathHelper.abs(sizeX); x++)
            {
                for(int z = 0; z <= MathHelper.abs(sizeZ); z++)
                {
                    for(int y = 0; y <= sizeY; y++)
                    {
                        int posX = (int) (sizeX > 0 ? sizeX - x : sizeX + x);
                        int posZ = (int) (sizeZ > 0 ? sizeZ - z : sizeZ + z);

                        BlockPos newPos = pos.add(posX, y, posZ);

                        if(world.getBlockState(newPos).getBlock().isReplaceable(world, newPos))
                        {
                            airBlocks++;
                        }
                        else
                        {
                            pos = pos.down();
                            continue label_while;
                        }
                    }
                }
            }

            if(airBlocks >= MathHelper.abs(sizeX * sizeY * sizeZ))
            {
                return pos;
            }

            pos = pos.down();
        }

        return BlockPos.ORIGIN;
    }

    public static void generateStructure(World world, BlockPos pos, Random rand, Template template, PlacementSettings placementSettings, ResourceLocation[] lootTables, ResourceLocation[] spawnerMobs)
    {
        try
        {
            List<Template.BlockInfo> blocks = (List<Template.BlockInfo>) FIELD_BLOCKS.get(template);
            List<Template.EntityInfo> entities = (List<Template.EntityInfo>) FIELD_ENTITIES.get(template);

            if((!blocks.isEmpty() || !placementSettings.getIgnoreEntities() && !entities.isEmpty()) && template.getSize().getX() >= 1 && template.getSize().getY() >= 1 && template.getSize().getZ() >= 1)
            {
                BlockRotationProcessor processor = new BlockRotationProcessor(pos, placementSettings);
                Block block = placementSettings.getReplacedBlock();
                StructureBoundingBox boundingBox = placementSettings.getBoundingBox();

                for(Template.BlockInfo blockInfo : blocks)
                {
                    BlockPos blockpos = Template.transformedBlockPos(placementSettings, blockInfo.pos).add(pos);
                    Template.BlockInfo blockInfo1 = processor != null ? processor.processBlock(world, blockpos, blockInfo) : blockInfo;

                    if(blockInfo1 != null)
                    {
                        Block block1 = blockInfo1.blockState.getBlock();

                        if((block == null || block != block1) && (!placementSettings.getIgnoreStructureBlock() || block1 != Blocks.STRUCTURE_BLOCK) && (boundingBox == null || boundingBox.isVecInside(blockpos)))
                        {
                            IBlockState state = blockInfo1.blockState.withMirror(placementSettings.getMirror());
                            IBlockState state1 = state.withRotation(placementSettings.getRotation());

                            if(blockInfo1.tileentityData != null)
                            {
                                TileEntity tileEntity = world.getTileEntity(blockpos);

                                if(tileEntity != null)
                                {
                                    if(tileEntity instanceof IInventory)
                                    {
                                        ((IInventory) tileEntity).clear();
                                    }

                                    world.setBlockState(blockpos, Blocks.BARRIER.getDefaultState(), 4);
                                }
                            }

                            if(world.setBlockState(blockpos, state1, 3) && blockInfo1.tileentityData != null)
                            {
                                TileEntity tileEntity = world.getTileEntity(blockpos);

                                if(tileEntity != null)
                                {
                                    blockInfo1.tileentityData.setInteger("x", blockpos.getX());
                                    blockInfo1.tileentityData.setInteger("y", blockpos.getY());
                                    blockInfo1.tileentityData.setInteger("z", blockpos.getZ());
                                    tileEntity.readFromNBT(blockInfo1.tileentityData);
                                    tileEntity.mirror(placementSettings.getMirror());
                                    tileEntity.rotate(placementSettings.getRotation());

                                    if(state1.getBlock() instanceof BlockChest)
                                    {
                                        ((TileEntityChest) tileEntity).setLootTable(lootTables[rand.nextInt(lootTables.length)], rand.nextLong());

                                    }
                                    if(state1.getBlock() instanceof BlockMobSpawner)
                                    {
                                        ((TileEntityMobSpawner) tileEntity).getSpawnerBaseLogic().setEntityId(spawnerMobs[rand.nextInt(spawnerMobs.length)]);
                                    }
                                    if(state.getBlock() instanceof BlockUrnOfSorrow)
                                    {
                                        ((TileEntityUrnOfSorrow) tileEntity).setCanBreak(false);
                                    }
                                }
                            }
                        }
                    }
                }

                for(Template.BlockInfo blockInfo2 : blocks)
                {
                    if(block == null || block != blockInfo2.blockState.getBlock())
                    {
                        BlockPos blockpos1 = Template.transformedBlockPos(placementSettings, blockInfo2.pos).add(pos);

                        if(boundingBox == null || boundingBox.isVecInside(blockpos1))
                        {
                            world.notifyNeighborsRespectDebug(blockpos1, blockInfo2.blockState.getBlock(), false);

                            if(blockInfo2.tileentityData != null)
                            {
                                TileEntity tileEntity = world.getTileEntity(blockpos1);

                                if(tileEntity != null)
                                {
                                    tileEntity.markDirty();
                                }
                            }
                        }
                    }
                }

                if(!placementSettings.getIgnoreEntities())
                {
                    try
                    {
                        METHOD_ADD_ENTITIES_TO_WORLD.invoke(template, world, pos, placementSettings.getMirror(), placementSettings.getRotation(), boundingBox);
                    }
                    catch(IllegalAccessException | InvocationTargetException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        catch(IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }
}

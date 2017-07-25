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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.BlockRotationProcessor;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import nex.block.BlockUrnOfSorrow;
import nex.entity.passive.EntityPigtificate;
import nex.entity.passive.EntityPigtificateLeader;
import nex.tileentity.TileEntityUrnOfSorrow;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TemplateUtil
{
    private static final Field FIELD_BLOCKS = ReflectionHelper.findField(Template.class, "field_186270_a", "blocks");
    private static final Field FIELD_ENTITIES = ReflectionHelper.findField(Template.class, "field_186271_b", "entities");

    public static void addBlocksToWorld(World world, BlockPos pos, Random rand, Template template, PlacementSettings placementSettings, List<ResourceLocation> lootTables, List<ResourceLocation> spawnerMobs)
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
                    BlockPos blockPos = Template.transformedBlockPos(placementSettings, blockInfo.pos).add(pos);
                    Template.BlockInfo blockInfo1 = processor != null ? processor.processBlock(world, blockPos, blockInfo) : blockInfo;

                    if(blockInfo1 != null)
                    {
                        Block block1 = blockInfo1.blockState.getBlock();

                        if((block == null || block != block1) && (!placementSettings.getIgnoreStructureBlock() || block1 != Blocks.STRUCTURE_BLOCK) && (boundingBox == null || boundingBox.isVecInside(blockPos)))
                        {
                            IBlockState state = blockInfo1.blockState.withMirror(placementSettings.getMirror()).withRotation(placementSettings.getRotation());

                            if(blockInfo1.tileentityData != null)
                            {
                                TileEntity tileEntity = world.getTileEntity(blockPos);

                                if(tileEntity != null)
                                {
                                    if(tileEntity instanceof IInventory)
                                    {
                                        ((IInventory) tileEntity).clear();
                                    }

                                    world.setBlockState(blockPos, Blocks.BARRIER.getDefaultState(), 4);
                                }
                            }

                            if(world.setBlockState(blockPos, state, 3) && blockInfo1.tileentityData != null)
                            {
                                TileEntity tileEntity = world.getTileEntity(blockPos);

                                if(tileEntity != null)
                                {
                                    blockInfo1.tileentityData.setInteger("x", blockPos.getX());
                                    blockInfo1.tileentityData.setInteger("y", blockPos.getY());
                                    blockInfo1.tileentityData.setInteger("z", blockPos.getZ());
                                    tileEntity.readFromNBT(blockInfo1.tileentityData);
                                    tileEntity.mirror(placementSettings.getMirror());
                                    tileEntity.rotate(placementSettings.getRotation());

                                    if(state.getBlock() instanceof BlockChest)
                                    {
                                        ((TileEntityChest) tileEntity).setLootTable(lootTables.get(rand.nextInt(lootTables.size())), rand.nextLong());

                                    }
                                    else if(state.getBlock() instanceof BlockMobSpawner)
                                    {
                                        ((TileEntityMobSpawner) tileEntity).getSpawnerBaseLogic().setEntityId(spawnerMobs.get(rand.nextInt(spawnerMobs.size())));
                                    }
                                    else if(state.getBlock() instanceof BlockUrnOfSorrow)
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
                        BlockPos blockPos1 = Template.transformedBlockPos(placementSettings, blockInfo2.pos).add(pos);

                        if(boundingBox == null || boundingBox.isVecInside(blockPos1))
                        {
                            world.notifyNeighborsRespectDebug(blockPos1, blockInfo2.blockState.getBlock(), false);

                            if(blockInfo2.tileentityData != null)
                            {
                                TileEntity tileEntity = world.getTileEntity(blockPos1);

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
                    addEntitiesToWorld(world, pos, placementSettings, entities, boundingBox);
                }
            }
        }
        catch(IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    private static void addEntitiesToWorld(World world, BlockPos pos, PlacementSettings placementSettings, List<Template.EntityInfo> entities, StructureBoundingBox boundingBox)
    {
        for(Template.EntityInfo entityInfo : entities)
        {
            BlockPos blockPos = Template.transformedBlockPos(placementSettings, entityInfo.blockPos).add(pos);

            if(boundingBox == null || boundingBox.isVecInside(blockPos))
            {
                NBTTagCompound compound = entityInfo.entityData;
                Vec3d vec3d = transformedVec3d(entityInfo.pos, placementSettings.getMirror(), placementSettings.getRotation());
                Vec3d vec3d1 = vec3d.addVector((double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
                NBTTagList tagList = new NBTTagList();
                tagList.appendTag(new NBTTagDouble(vec3d1.x));
                tagList.appendTag(new NBTTagDouble(vec3d1.y));
                tagList.appendTag(new NBTTagDouble(vec3d1.z));
                compound.setTag("Pos", tagList);
                compound.setUniqueId("UUID", UUID.randomUUID());
                Entity entity;

                try
                {
                    entity = EntityList.createEntityFromNBT(compound, world);
                }
                catch(Exception var15)
                {
                    entity = null;
                }

                if(entity != null)
                {
                    if(entity instanceof EntityPigtificateLeader)
                    {
                        entity = new EntityPigtificateLeader(world);
                    }
                    else if(entity instanceof EntityPigtificate)
                    {
                        entity = new EntityPigtificate(world);
                    }

                    float f = entity.getMirroredYaw(placementSettings.getMirror());
                    f = f + (entity.rotationYaw - entity.getRotatedYaw(placementSettings.getRotation()));
                    entity.setLocationAndAngles(vec3d1.x, vec3d1.y, vec3d1.z, f, entity.rotationPitch);
                    world.spawnEntity(entity);
                }
            }
        }
    }

    private static Vec3d transformedVec3d(Vec3d vec, Mirror mirror, Rotation rotation)
    {
        double xCoord = vec.x;
        double yCoord = vec.y;
        double zCoord = vec.z;
        boolean flag = true;

        switch(mirror)
        {
            case LEFT_RIGHT:
                zCoord = 1.0D - zCoord;
                break;
            case FRONT_BACK:
                xCoord = 1.0D - xCoord;
                break;
            default:
                flag = false;
        }

        switch(rotation)
        {
            case COUNTERCLOCKWISE_90:
                return new Vec3d(zCoord, yCoord, 1.0D - xCoord);
            case CLOCKWISE_90:
                return new Vec3d(1.0D - zCoord, yCoord, xCoord);
            case CLOCKWISE_180:
                return new Vec3d(1.0D - xCoord, yCoord, 1.0D - zCoord);
            default:
                return flag ? new Vec3d(xCoord, yCoord, zCoord) : vec;
        }
    }
}

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
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.BlockRotationProcessor;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import nex.block.BlockUrnOfSorrow;
import nex.entity.passive.EntityPigtificate;
import nex.entity.passive.EntityPigtificateLeader;
import nex.tileentity.TileEntityUrnOfSorrow;
import nex.world.biome.NetherBiomeManager;
import nex.world.gen.GenerationStage;
import nex.world.gen.feature.EnhancedGenerator;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@SuppressWarnings("ConstantConditions")
public class WorldGenUtil
{
    private static final Field FIELD_BLOCKS = ReflectionHelper.findField(Template.class, "field_186270_a", "blocks");
    private static final Field FIELD_ENTITIES = ReflectionHelper.findField(Template.class, "field_186271_b", "entities");

    public static BlockPos getSuitableGroundPos(World world, BlockPos pos, BlockPos structureSize, float percentage)
    {
        label_while:
        while(pos.getY() > 32)
        {
            float sizeX = structureSize.getX();
            float sizeY = structureSize.getY();
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

                    if(!world.getBlockState(newPos).getMaterial().isReplaceable() && !world.getBlockState(newPos.down()).getMaterial().isReplaceable() && world.getBlockState(newPos.up()).getMaterial().isReplaceable())
                    {
                        topBlocks++;
                    }
                    else if(state != Blocks.AIR.getDefaultState())
                    {
                        pos = pos.down();
                        continue label_while;
                    }
                }
            }

            int replaceableBlocks = 0;

            if(topBlocks >= MathHelper.abs(sizeX * sizeZ) * percentage)
            {
                for(int y = 1; y < sizeY; y++)
                {
                    for(int x = 0; x <= MathHelper.abs(sizeX); x++)
                    {
                        for(int z = 0; z <= MathHelper.abs(sizeZ); z++)
                        {
                            int posX = (int) (sizeX > 0 ? sizeX - x : sizeX + x);
                            int posZ = (int) (sizeZ > 0 ? sizeZ - z : sizeZ + z);

                            BlockPos newPos = pos.add(posX, y, posZ);
                            IBlockState state = world.getBlockState(newPos);

                            if(world.getBlockState(newPos).getMaterial().isReplaceable())
                            {
                                replaceableBlocks++;
                            }
                            else if(state != Blocks.AIR.getDefaultState())
                            {
                                pos = pos.down();
                                continue label_while;
                            }
                        }
                    }
                }
            }

            if(replaceableBlocks > MathHelper.abs(sizeX * sizeY * sizeZ) * 0.875F)
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

                        if(!world.getBlockState(newPos).getMaterial().isReplaceable())
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
        while(pos.getY() < 128)
        {
            float sizeX = structureSize.getX();
            float sizeZ = structureSize.getZ();
            float sizeY = structureSize.getY();

            int ceilingBlocks = 0;
            int replaceableBlocks = 0;

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
                            if(world.getBlockState(newPos).isSideSolid(world, newPos, EnumFacing.DOWN))
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
                                replaceableBlocks++;
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

            if(replaceableBlocks >= MathHelper.abs(sizeX * (sizeY - 1) * sizeZ) && ceilingBlocks >= MathHelper.abs(sizeX * sizeZ))
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

            int replaceableBlocks = 0;

            for(int x = 0; x <= MathHelper.abs(sizeX); x++)
            {
                for(int z = 0; z <= MathHelper.abs(sizeZ); z++)
                {
                    for(int y = 0; y <= sizeY; y++)
                    {
                        int posX = (int) (sizeX > 0 ? sizeX - x : sizeX + x);
                        int posZ = (int) (sizeZ > 0 ? sizeZ - z : sizeZ + z);

                        BlockPos newPos = pos.add(posX, y, posZ);

                        if(world.getBlockState(newPos).getMaterial().isReplaceable())
                        {
                            replaceableBlocks++;
                        }
                        else
                        {
                            pos = pos.down();
                            continue label_while;
                        }
                    }
                }
            }

            if(replaceableBlocks >= MathHelper.abs(sizeX * sizeY * sizeZ))
            {
                return pos;
            }

            pos = pos.down();
        }

        return BlockPos.ORIGIN;
    }

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
                                        MobSpawnerBaseLogic logic = ((TileEntityMobSpawner) tileEntity).getSpawnerBaseLogic();
                                        NBTTagCompound compound = new NBTTagCompound();

                                        logic.writeToNBT(compound);
                                        compound.removeTag("SpawnPotentials");
                                        logic.readFromNBT(compound);
                                        logic.setEntityId(spawnerMobs.get(rand.nextInt(spawnerMobs.size())));
                                        tileEntity.markDirty();

                                        world.notifyBlockUpdate(pos, state, state, 3);
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

    public static void generateFeature(World world, int chunkX, int chunkZ, Random rand, GenerationStage generationStage)
    {
        BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
        generateFeature(world, pos, rand, generationStage);
    }

    public static void generateFeature(World world, BlockPos pos, Random rand, GenerationStage generationStage)
    {
        if(world.provider.getDimension() == DimensionType.NETHER.getId())
        {
            Biome biome = world.getBiome(pos.add(16, 0, 16));

            for(EnhancedGenerator generator : NetherBiomeManager.getBiomeGenerators(biome, generationStage))
            {
                for(int genAttempts = 0; genAttempts < generator.getGenAttempts(rand); genAttempts++)
                {
                    generator.generate(world, rand, pos.add(rand.nextInt(16) + 8, RandomUtil.getNumberInRange(generator.getMinHeight(), generator.getMaxHeight(), rand), rand.nextInt(16) + 8));
                }
            }
        }
    }
}

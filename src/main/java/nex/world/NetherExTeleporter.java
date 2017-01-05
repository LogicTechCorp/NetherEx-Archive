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

package nex.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.Constants;
import nex.init.NetherExBlocks;

import java.util.Random;

/**
 * Makes Nether Portal teleportation more reliable
 * <p>
 * Written by blayO9:
 * https://github.com/blay09/NetherPortalFix/blob/1.11/src/main/java/net/blay09/mods/netherportalfix/BetterTeleporter.java
 *
 * @author blayO9
 */
@SuppressWarnings("ConstantConditions")
public class NetherExTeleporter extends Teleporter
{
    private final WorldServer worldServer;
    private final Random rand;

    public NetherExTeleporter(WorldServer worldServerIn)
    {
        super(worldServerIn);

        worldServer = worldServerIn;
        rand = new Random(worldServerIn.getSeed());
    }

    @Override
    public void placeInPortal(Entity entity, float rotationYaw)
    {
        if(entity instanceof EntityPlayer)
        {
            PortalPositionAndDimension from = new PortalPositionAndDimension(entity.lastPortalPos);
            PortalPositionAndDimension to = new PortalPositionAndDimension(entity.getPosition());

            super.placeInPortal(entity, rotationYaw);

            NBTTagCompound compound = entity.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
            NBTTagList list = compound.getTagList("ReturnPortals", Constants.NBT.TAG_COMPOUND);

            for(int i = list.tagCount() - 1; i >= 0; i--)
            {
                NBTTagCompound portalCompound = list.getCompoundTagAt(i);
                PortalPositionAndDimension testTo = new PortalPositionAndDimension(BlockPos.fromLong(portalCompound.getLong("To")), portalCompound.getInteger("ToDim"));

                if(testTo.dimensionId == entity.world.provider.getDimension() && testTo.distanceSq(to) <= 9)
                {
                    list.removeTag(i);
                }
            }

            NBTTagCompound portalCompound = new NBTTagCompound();
            portalCompound.setLong("From", from.toLong());
            portalCompound.setInteger("FromDim", from.dimensionId);
            portalCompound.setLong("To", to.toLong());
            portalCompound.setInteger("ToDim", to.dimensionId);
            list.appendTag(portalCompound);
            compound.setTag("ReturnPortals", list);
            entity.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, compound);
        }
        else
        {
            super.placeInPortal(entity, rotationYaw);
        }
    }

    @Override
    public boolean placeInExistingPortal(Entity entity, float rotationYaw)
    {
        if(entity instanceof EntityPlayer)
        {
            NBTTagCompound compound = entity.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
            NBTTagList list = compound.getTagList("ReturnPortals", Constants.NBT.TAG_COMPOUND);

            for(int i = list.tagCount() - 1; i >= 0; i--)
            {
                NBTTagCompound portalCompound = list.getCompoundTagAt(i);
                PortalPositionAndDimension to = new PortalPositionAndDimension(BlockPos.fromLong(portalCompound.getLong("To")), portalCompound.getInteger("ToDim"));

                if(to.dimensionId == entity.getEntityWorld().provider.getDimension() && to.distanceSq(entity.lastPortalPos) <= 9)
                {
                    int x = MathHelper.floor(entity.posX);
                    int z = MathHelper.floor(entity.posZ);
                    long chunkPos = ChunkPos.asLong(x, z);

                    PortalPosition oldChunkPos = destinationCoordinateCache.get(chunkPos);
                    PortalPositionAndDimension from = new PortalPositionAndDimension(BlockPos.fromLong(portalCompound.getLong("From")), portalCompound.getInteger("FromDim"));
                    destinationCoordinateCache.put(chunkPos, from);

                    boolean result = super.placeInExistingPortal(entity, rotationYaw);

                    if(oldChunkPos != null)
                    {
                        destinationCoordinateCache.put(chunkPos, oldChunkPos);
                    }

                    list.removeTag(i);
                    compound.setTag("ReturnPortals", list);
                    entity.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, compound);
                    return result;
                }
            }
        }

        return super.placeInExistingPortal(entity, rotationYaw);
    }

    @Override
    public boolean makePortal(Entity entityIn)
    {
        double d0 = -1.0D;
        int j = MathHelper.floor(entityIn.posX);
        int k = MathHelper.floor(entityIn.posY);
        int l = MathHelper.floor(entityIn.posZ);
        int i1 = j;
        int j1 = k;
        int k1 = l;
        int l1 = 0;
        int i2 = rand.nextInt(4);
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for(int j2 = j - 16; j2 <= j + 16; ++j2)
        {
            double d1 = (double) j2 + 0.5D - entityIn.posX;

            for(int l2 = l - 16; l2 <= l + 16; ++l2)
            {
                double d2 = (double) l2 + 0.5D - entityIn.posZ;
                label146:

                for(int j3 = worldServer.getActualHeight() - 1; j3 >= 0; --j3)
                {
                    if(worldServer.isAirBlock(mutablePos.setPos(j2, j3, l2)))
                    {
                        while(j3 > 0 && worldServer.isAirBlock(mutablePos.setPos(j2, j3 - 1, l2)))
                        {
                            --j3;
                        }

                        for(int k3 = i2; k3 < i2 + 4; ++k3)
                        {
                            int l3 = k3 % 2;
                            int i4 = 1 - l3;

                            if(k3 % 4 >= 2)
                            {
                                l3 = -l3;
                                i4 = -i4;
                            }

                            for(int j4 = 0; j4 < 3; ++j4)
                            {
                                for(int k4 = 0; k4 < 4; ++k4)
                                {
                                    for(int l4 = -1; l4 < 4; ++l4)
                                    {
                                        int i5 = j2 + (k4 - 1) * l3 + j4 * i4;
                                        int j5 = j3 + l4;
                                        int k5 = l2 + (k4 - 1) * i4 - j4 * l3;
                                        mutablePos.setPos(i5, j5, k5);

                                        if(l4 < 0 && !worldServer.getBlockState(mutablePos).getMaterial().isSolid() || l4 >= 0 && !worldServer.isAirBlock(mutablePos))
                                        {
                                            continue label146;
                                        }
                                    }
                                }
                            }

                            double d5 = (double) j3 + 0.5D - entityIn.posY;
                            double d7 = d1 * d1 + d5 * d5 + d2 * d2;

                            if(d0 < 0.0D || d7 < d0)
                            {
                                d0 = d7;
                                i1 = j2;
                                j1 = j3;
                                k1 = l2;
                                l1 = k3 % 4;
                            }
                        }
                    }
                }
            }
        }

        if(d0 < 0.0D)
        {
            for(int l5 = j - 16; l5 <= j + 16; ++l5)
            {
                double d3 = (double) l5 + 0.5D - entityIn.posX;

                for(int j6 = l - 16; j6 <= l + 16; ++j6)
                {
                    double d4 = (double) j6 + 0.5D - entityIn.posZ;
                    label567:

                    for(int i7 = worldServer.getActualHeight() - 1; i7 >= 0; --i7)
                    {
                        if(worldServer.isAirBlock(mutablePos.setPos(l5, i7, j6)))
                        {
                            while(i7 > 0 && worldServer.isAirBlock(mutablePos.setPos(l5, i7 - 1, j6)))
                            {
                                --i7;
                            }

                            for(int k7 = i2; k7 < i2 + 2; ++k7)
                            {
                                int j8 = k7 % 2;
                                int j9 = 1 - j8;

                                for(int j10 = 0; j10 < 4; ++j10)
                                {
                                    for(int j11 = -1; j11 < 4; ++j11)
                                    {
                                        int j12 = l5 + (j10 - 1) * j8;
                                        int i13 = i7 + j11;
                                        int j13 = j6 + (j10 - 1) * j9;
                                        mutablePos.setPos(j12, i13, j13);

                                        if(j11 < 0 && !worldServer.getBlockState(mutablePos).getMaterial().isSolid() || j11 >= 0 && !worldServer.isAirBlock(mutablePos))
                                        {
                                            continue label567;
                                        }
                                    }
                                }

                                double d6 = (double) i7 + 0.5D - entityIn.posY;
                                double d8 = d3 * d3 + d6 * d6 + d4 * d4;

                                if(d0 < 0.0D || d8 < d0)
                                {
                                    d0 = d8;
                                    i1 = l5;
                                    j1 = i7;
                                    k1 = j6;
                                    l1 = k7 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }

        int i6 = i1;
        int k2 = j1;
        int k6 = k1;
        int l6 = l1 % 2;
        int i3 = 1 - l6;

        if(l1 % 4 >= 2)
        {
            l6 = -l6;
            i3 = -i3;
        }

        if(d0 < 0.0D)
        {
            j1 = MathHelper.clamp(j1, 70, worldServer.getActualHeight() - 10);
            k2 = j1;

            for(int j7 = -1; j7 <= 1; ++j7)
            {
                for(int l7 = 1; l7 < 3; ++l7)
                {
                    for(int k8 = -1; k8 < 3; ++k8)
                    {
                        int k9 = i6 + (l7 - 1) * l6 + j7 * i3;
                        int k10 = k2 + k8;
                        int k11 = k6 + (l7 - 1) * i3 - j7 * l6;
                        worldServer.setBlockState(new BlockPos(k9, k10, k11), Blocks.COBBLESTONE.getDefaultState());
                    }
                }
            }
        }

        int posX = i6 * l6;
        int posY = k2;
        int posZ = k6 * i3;

        EnumFacing facing = entityIn.getTeleportDirection() == EnumFacing.NORTH ? EnumFacing.UP : EnumFacing.SOUTH;
        NetherExBlocks.BLOCK_PORTAL_NETHER.trySpawnPortal(worldServer, new BlockPos(posX, posY, posZ), entityIn, facing, true);
        return true;
    }

    public class PortalPositionAndDimension extends PortalPosition
    {
        public final int dimensionId;

        public PortalPositionAndDimension(BlockPos pos)
        {
            this(pos, worldServer.provider.getDimension());
        }

        public PortalPositionAndDimension(BlockPos pos, int dimensionIdIn)
        {
            super(pos, worldServer.getWorldTime());

            dimensionId = dimensionIdIn;
        }
    }
}

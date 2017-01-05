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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.Constants;

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

        return false;
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

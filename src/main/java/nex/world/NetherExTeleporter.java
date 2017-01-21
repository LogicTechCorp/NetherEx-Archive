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

/**
 * A fix for Nether Portal teleportation
 * <p>
 * Written by blay09 here:
 * https://github.com/blay09/NetherPortalFix/blob/1.11/src/main/java/net/blay09/mods/netherportalfix/BetterTeleporter.java
 *
 * @author blay09
 */
public class NetherExTeleporter extends Teleporter
{
    private final WorldServer world;

    public NetherExTeleporter(WorldServer worldIn)
    {
        super(worldIn);
        world = worldIn;
    }

    @Override
    public void placeInPortal(Entity entity, float rotationYaw)
    {
        if(entity instanceof EntityPlayer)
        {
            PortalPositionAndDimension from = new PortalPositionAndDimension(entity.lastPortalPos);
            super.placeInPortal(entity, rotationYaw);
            PortalPositionAndDimension to = new PortalPositionAndDimension(entity.getPosition());

            NBTTagCompound tagCompound = entity.getEntityData().getCompoundTag("TeleportInfo");
            NBTTagList tagList = tagCompound.getTagList("ReturnPortals", Constants.NBT.TAG_COMPOUND);

            for(int i = tagList.tagCount() - 1; i >= 0; i--)
            {
                NBTTagCompound portalCompound = tagList.getCompoundTagAt(i);
                PortalPositionAndDimension testTo = new PortalPositionAndDimension(BlockPos.fromLong(portalCompound.getLong("To")), portalCompound.getInteger("ToDim"));

                if(testTo.dimensionId == entity.world.provider.getDimension() && testTo.distanceSq(to) <= 9)
                {
                    tagList.removeTag(i);
                }
            }

            NBTTagCompound portalCompound = new NBTTagCompound();
            portalCompound.setLong("From", from.toLong());
            portalCompound.setInteger("FromDim", from.dimensionId);
            portalCompound.setLong("To", to.toLong());
            portalCompound.setInteger("ToDim", to.dimensionId);
            tagList.appendTag(portalCompound);
            tagCompound.setTag("ReturnPortals", tagList);
            entity.getEntityData().setTag("TeleportInfo", tagCompound);
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
            NBTTagCompound tagCompound = entity.getEntityData().getCompoundTag("TeleportInfo");
            NBTTagList tagList = tagCompound.getTagList("ReturnPortals", Constants.NBT.TAG_COMPOUND);

            for(int i = tagList.tagCount() - 1; i >= 0; i--)
            {
                NBTTagCompound portalCompound = tagList.getCompoundTagAt(i);
                PortalPositionAndDimension to = new PortalPositionAndDimension(BlockPos.fromLong(portalCompound.getLong("To")), portalCompound.getInteger("ToDim"));

                if(to.dimensionId == entity.getEntityWorld().provider.getDimension() && to.distanceSq(entity.lastPortalPos) <= 9)
                {
                    int x = MathHelper.floor(entity.posX);
                    int y = MathHelper.floor(entity.posZ);
                    long key = ChunkPos.asLong(x, y);

                    PortalPositionAndDimension from = new PortalPositionAndDimension(BlockPos.fromLong(portalCompound.getLong("From")), portalCompound.getInteger("FromDim"));
                    destinationCoordinateCache.put(key, from);

                    PortalPosition oldValue = destinationCoordinateCache.get(key);

                    if(oldValue != null)
                    {
                        destinationCoordinateCache.put(key, oldValue);
                    }

                    tagList.removeTag(i);
                    tagCompound.setTag("ReturnPortals", tagList);
                    entity.getEntityData().setTag("TeleportInfo", tagCompound);
                    return super.placeInExistingPortal(entity, rotationYaw);
                }
            }
        }

        return super.placeInExistingPortal(entity, rotationYaw);
    }

    public class PortalPositionAndDimension extends PortalPosition
    {
        public final int dimensionId;

        public PortalPositionAndDimension(BlockPos pos)
        {
            this(pos, world.provider.getDimension());
        }

        public PortalPositionAndDimension(BlockPos pos, int dimensionIdIn)
        {
            super(pos, world.getWorldTime());
            dimensionId = dimensionIdIn;
        }
    }
}

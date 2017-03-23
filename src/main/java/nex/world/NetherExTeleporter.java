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

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

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

    private static final Field FIELD_LAST_PORTAL_POS = ReflectionHelper.findField(Entity.class, "field_181016_an", "lastPortalPos");
    private static final Field FIELD_DESTINATION_COORDINATE_CACHE = ReflectionHelper.findField(Teleporter.class, "field_85191_c", "destinationCoordinateCache");

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
            PortalPositionAndDimension from = null;
            try
            {
                from = new PortalPositionAndDimension((BlockPos) FIELD_LAST_PORTAL_POS.get(entity));
            }
            catch(IllegalAccessException e)
            {
                e.printStackTrace();
            }
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

                try
                {
                    if(to.dimensionId == entity.getEntityWorld().provider.getDimension() && to.distanceSq((Vec3i) FIELD_LAST_PORTAL_POS.get(entity)) <= 9)
                    {
                        int x = MathHelper.floor(entity.posX);
                        int y = MathHelper.floor(entity.posZ);
                        long key = ChunkPos.asLong(x, y);

                        PortalPositionAndDimension from = new PortalPositionAndDimension(BlockPos.fromLong(portalCompound.getLong("From")), portalCompound.getInteger("FromDim"));

                        Long2ObjectMap<PortalPosition> destinationCoordinateCache = (Long2ObjectMap<PortalPosition>) FIELD_DESTINATION_COORDINATE_CACHE.get(this);
                        destinationCoordinateCache.put(key, from);
                        FIELD_DESTINATION_COORDINATE_CACHE.set(this, destinationCoordinateCache);

                        PortalPosition oldValue = destinationCoordinateCache.get(key);

                        if(oldValue != null)
                        {
                            destinationCoordinateCache.put(key, oldValue);
                            FIELD_DESTINATION_COORDINATE_CACHE.set(this, destinationCoordinateCache);
                        }

                        tagList.removeTag(i);
                        tagCompound.setTag("ReturnPortals", tagList);
                        entity.getEntityData().setTag("TeleportInfo", tagCompound);
                        return super.placeInExistingPortal(entity, rotationYaw);
                    }
                }
                catch(IllegalAccessException e)
                {
                    e.printStackTrace();
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

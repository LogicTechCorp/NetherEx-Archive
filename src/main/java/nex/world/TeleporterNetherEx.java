/*
 * NetherEx
 * Copyright (c) 2016-2018 by MineEx
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

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import nex.block.BlockNetherPortal;
import nex.init.NetherExBlocks;

public class TeleporterNetherEx extends Teleporter
{
    public TeleporterNetherEx(WorldServer world)
    {
        super(world);
    }

    @Override
    public void placeInPortal(Entity entity, float rotationYaw)
    {
        if(this.world.provider.getDimensionType().getId() != DimensionType.THE_END.getId())
        {
            if(!this.placeInExistingPortal(entity, rotationYaw))
            {
                this.makePortal(entity);
                this.placeInExistingPortal(entity, rotationYaw);
            }
        }
        else
        {
            int entityPosX = MathHelper.floor(entity.posX);
            int entityPosY = MathHelper.floor(entity.posY) - 1;
            int entityPosZ = MathHelper.floor(entity.posZ);

            for(int z = -2; z <= 2; z++)
            {
                for(int x = -2; x <= 2; x++)
                {
                    for(int y = -1; y < 3; y++)
                    {
                        int posX = entityPosX + x;
                        int posY = entityPosY + y;
                        int posZ = entityPosZ - z;
                        boolean beneathWorld = y < 0;
                        this.world.setBlockState(new BlockPos(posX, posY, posZ), beneathWorld ? Blocks.OBSIDIAN.getDefaultState() : Blocks.AIR.getDefaultState());
                    }
                }
            }

            entity.setLocationAndAngles((double) entityPosX, (double) entityPosY, (double) entityPosZ, entity.rotationYaw, 0.0F);
            entity.motionX = 0.0D;
            entity.motionY = 0.0D;
            entity.motionZ = 0.0D;
        }
    }

    @Override
    public boolean placeInExistingPortal(Entity entity, float rotationYaw)
    {
        double portalDistance = -1.0D;
        int entityPosX = MathHelper.floor(entity.posX);
        int entityPosZ = MathHelper.floor(entity.posZ);
        boolean newPortal = true;
        BlockPos portalPos = BlockPos.ORIGIN;
        long portalChunkPos = ChunkPos.asLong(entityPosX, entityPosZ);

        if(this.destinationCoordinateCache.containsKey(portalChunkPos))
        {
            Teleporter.PortalPosition portalPosition = this.destinationCoordinateCache.get(portalChunkPos);
            portalDistance = 0.0D;
            portalPos = portalPosition;
            portalPosition.lastUpdateTime = this.world.getTotalWorldTime();
            newPortal = false;
        }
        else
        {
            BlockPos entityPos = new BlockPos(entity);

            for(int x = -128; x <= 128; x++)
            {
                BlockPos newPos;

                for(int z = -128; z <= 128; z++)
                {
                    for(BlockPos checkPos = entityPos.add(x, this.world.getActualHeight() - 1 - entityPos.getY(), z); checkPos.getY() >= 0; checkPos = newPos)
                    {
                        newPos = checkPos.down();

                        if(this.world.getBlockState(checkPos).getBlock() == NetherExBlocks.NETHER_PORTAL)
                        {
                            for(newPos = checkPos.down(); this.world.getBlockState(newPos).getBlock() == NetherExBlocks.NETHER_PORTAL; newPos = newPos.down())
                            {
                                checkPos = newPos;
                            }

                            double distanceSqBetweenPosAndEntity = checkPos.distanceSq(entityPos);

                            if(portalDistance < 0.0D || distanceSqBetweenPosAndEntity < portalDistance)
                            {
                                portalDistance = distanceSqBetweenPosAndEntity;
                                portalPos = checkPos;
                            }
                        }
                    }
                }
            }
        }

        if(portalDistance >= 0.0D)
        {
            if(newPortal)
            {
                this.destinationCoordinateCache.put(portalChunkPos, new Teleporter.PortalPosition(portalPos, this.world.getTotalWorldTime()));
            }

            double adjustedPosX = (double) portalPos.getX() + 0.5D;
            double adjustedPosZ = (double) portalPos.getZ() + 0.5D;
            BlockPattern.PatternHelper patternHelper = Blocks.PORTAL.createPatternHelper(this.world, portalPos);
            boolean facingNegative = patternHelper.getForwards().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE;
            double offset = patternHelper.getForwards().getAxis() == EnumFacing.Axis.X ? (double) patternHelper.getFrontTopLeft().getZ() : (double) patternHelper.getFrontTopLeft().getX();
            double adjustedPosY = (double) (patternHelper.getFrontTopLeft().getY() + 1) - entity.getLastPortalVec().y * (double) patternHelper.getHeight();

            if(facingNegative)
            {
                offset++;
            }

            if(patternHelper.getForwards().getAxis() == EnumFacing.Axis.X)
            {
                adjustedPosZ = offset + (1.0D - entity.getLastPortalVec().x) * (double) patternHelper.getWidth() * (double) patternHelper.getForwards().rotateY().getAxisDirection().getOffset();
            }
            else
            {
                adjustedPosX = offset + (1.0D - entity.getLastPortalVec().x) * (double) patternHelper.getWidth() * (double) patternHelper.getForwards().rotateY().getAxisDirection().getOffset();
            }

            float f = 0.0F;
            float f1 = 0.0F;
            float f2 = 0.0F;
            float f3 = 0.0F;

            if(patternHelper.getForwards().getOpposite() == entity.getTeleportDirection())
            {
                f = 1.0F;
                f1 = 1.0F;
            }
            else if(patternHelper.getForwards().getOpposite() == entity.getTeleportDirection().getOpposite())
            {
                f = -1.0F;
                f1 = -1.0F;
            }
            else if(patternHelper.getForwards().getOpposite() == entity.getTeleportDirection().rotateY())
            {
                f2 = 1.0F;
                f3 = -1.0F;
            }
            else
            {
                f2 = -1.0F;
                f3 = 1.0F;
            }

            double motionX = entity.motionX;
            double motionZ = entity.motionZ;
            entity.motionX = motionX * (double) f + motionZ * (double) f3;
            entity.motionZ = motionX * (double) f2 + motionZ * (double) f1;
            entity.rotationYaw = rotationYaw - (float) (entity.getTeleportDirection().getOpposite().getHorizontalIndex() * 90) + (float) (patternHelper.getForwards().getHorizontalIndex() * 90);

            if(entity instanceof EntityPlayerMP)
            {
                ((EntityPlayerMP) entity).connection.setPlayerLocation(adjustedPosX, adjustedPosY, adjustedPosZ, entity.rotationYaw, entity.rotationPitch);
            }
            else
            {
                entity.setLocationAndAngles(adjustedPosX, adjustedPosY, adjustedPosZ, entity.rotationYaw, entity.rotationPitch);
            }

            return true;

        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean makePortal(Entity entity)
    {
        double portalDistance = -1.0D;
        int entityPosX = MathHelper.floor(entity.posX);
        int entityPosY = MathHelper.floor(entity.posY);
        int entityPosZ = MathHelper.floor(entity.posZ);
        int adjustedEntityPosX = entityPosX;
        int adjustedEntityPosY = entityPosY;
        int adjustedEntityPosZ = entityPosZ;
        int offset = 0;
        int randomOffset = this.random.nextInt(4);
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for(int x = entityPosX - 16; x <= entityPosX + 16; x++)
        {
            double adjustedPosX = (double) x + 0.5D - entity.posX;

            for(int z = entityPosZ - 16; z <= entityPosZ + 16; z++)
            {
                double adjustedPosZ = (double) z + 0.5D - entity.posZ;
                label293:

                for(int y = this.world.getActualHeight() - 1; y >= 0; y--)
                {
                    if(this.world.isAirBlock(mutableBlockPos.setPos(x, y, z)))
                    {
                        while(y > 0 && this.world.isAirBlock(mutableBlockPos.setPos(x, y - 1, z)))
                        {
                            y--;
                        }

                        for(int k3 = randomOffset; k3 < randomOffset + 4; k3++)
                        {
                            int l3 = k3 % 2;
                            int i4 = 1 - l3;

                            if(k3 % 4 >= 2)
                            {
                                l3 = -l3;
                                i4 = -i4;
                            }

                            for(int offsetZ = 0; offsetZ < 3; offsetZ++)
                            {
                                for(int offsetX = 0; offsetX < 4; offsetX++)
                                {
                                    for(int offsetY = -1; offsetY < 4; offsetY++)
                                    {
                                        int posX = x + (offsetX - 1) * l3 + offsetZ * i4;
                                        int posY = y + offsetY;
                                        int posZ = z + (offsetX - 1) * i4 - offsetZ * l3;
                                        mutableBlockPos.setPos(posX, posY, posZ);

                                        if(offsetY < 0 && !this.world.getBlockState(mutableBlockPos).getMaterial().isSolid() || offsetY >= 0 && !this.world.isAirBlock(mutableBlockPos))
                                        {
                                            continue label293;
                                        }
                                    }
                                }
                            }

                            double adjustedPosY = (double) y + 0.5D - entity.posY;
                            double adjustedPos = adjustedPosX * adjustedPosX + adjustedPosY * adjustedPosY + adjustedPosZ * adjustedPosZ;

                            if(portalDistance < 0.0D || adjustedPos < portalDistance)
                            {
                                portalDistance = adjustedPos;
                                adjustedEntityPosX = x;
                                adjustedEntityPosY = y;
                                adjustedEntityPosZ = z;
                                offset = k3 % 4;
                            }
                        }
                    }
                }
            }
        }

        if(portalDistance < 0.0D)
        {
            for(int x = entityPosX - 16; x <= entityPosX + 16; x++)
            {
                double adjustedPosX = (double) x + 0.5D - entity.posX;

                for(int z = entityPosZ - 16; z <= entityPosZ + 16; z++)
                {
                    double adjustedPosZ = (double) z + 0.5D - entity.posZ;
                    label231:

                    for(int y = this.world.getActualHeight() - 1; y >= 0; y--)
                    {
                        if(this.world.isAirBlock(mutableBlockPos.setPos(x, y, z)))
                        {
                            while(y > 0 && this.world.isAirBlock(mutableBlockPos.setPos(x, y - 1, z)))
                            {
                                y--;
                            }

                            for(int offsetZ = randomOffset; offsetZ < randomOffset + 2; offsetZ++)
                            {
                                int j8 = offsetZ % 2;
                                int j9 = 1 - j8;

                                for(int offsetX = 0; offsetX < 4; offsetX++)
                                {
                                    for(int offsetY = -1; offsetY < 4; offsetY++)
                                    {
                                        int posX = x + (offsetX - 1) * j8;
                                        int posY = y + offsetY;
                                        int posZ = z + (offsetX - 1) * j9;
                                        mutableBlockPos.setPos(posX, posY, posZ);

                                        if(offsetY < 0 && !this.world.getBlockState(mutableBlockPos).getMaterial().isSolid() || offsetY >= 0 && !this.world.isAirBlock(mutableBlockPos))
                                        {
                                            continue label231;
                                        }
                                    }
                                }

                                double adjustedPosY = (double) y + 0.5D - entity.posY;
                                double adjustedPos = adjustedPosX * adjustedPosX + adjustedPosY * adjustedPosY + adjustedPosZ * adjustedPosZ;

                                if(portalDistance < 0.0D || adjustedPos < portalDistance)
                                {
                                    portalDistance = adjustedPos;
                                    adjustedEntityPosX = x;
                                    adjustedEntityPosY = y;
                                    adjustedEntityPosZ = z;
                                    offset = offsetZ % 2;
                                }
                            }
                        }
                    }
                }
            }
        }

        int i6 = adjustedEntityPosX;
        int k2 = adjustedEntityPosY;
        int k6 = adjustedEntityPosZ;
        int l6 = offset % 2;
        int i3 = 1 - l6;

        if(offset % 4 >= 2)
        {
            l6 = -l6;
            i3 = -i3;
        }

        if(portalDistance < 0.0D)
        {
            adjustedEntityPosY = MathHelper.clamp(adjustedEntityPosY, 70, this.world.getActualHeight() - 10);
            k2 = adjustedEntityPosY;

            for(int z = -1; z <= 1; ++z)
            {
                for(int x = 1; x < 3; ++x)
                {
                    for(int y = -1; y < 3; ++y)
                    {
                        int posX = i6 + (x - 1) * l6 + z * i3;
                        int posY = k2 + y;
                        int posZ = k6 + (x - 1) * i3 - z * l6;
                        boolean beneathWorld = y < 0;
                        this.world.setBlockState(new BlockPos(posX, posY, posZ), beneathWorld ? Blocks.OBSIDIAN.getDefaultState() : Blocks.AIR.getDefaultState());
                    }
                }
            }
        }

        IBlockState iblockstate = NetherExBlocks.NETHER_PORTAL.getDefaultState().withProperty(BlockNetherPortal.AXIS, l6 == 0 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);

        for(int x = 0; x < 4; x++)
        {
            for(int z = 0; z < 4; z++)
            {
                for(int y = -1; y < 4; y++)
                {
                    int posX = i6 + (z - 1) * l6;
                    int posY = k2 + y;
                    int posZ = k6 + (z - 1) * i3;
                    boolean beneathWorld = z == 0 || z == 3 || y == -1 || y == 3;
                    this.world.setBlockState(new BlockPos(posX, posY, posZ), beneathWorld ? Blocks.OBSIDIAN.getDefaultState() : iblockstate, 2);
                }
            }

            for(int zx = 0; zx < 4; zx++)
            {
                for(int y = -1; y < 4; y++)
                {
                    int posX = i6 + (zx - 1) * l6;
                    int posY = k2 + y;
                    int posZ = k6 + (zx - 1) * i3;
                    BlockPos blockpos = new BlockPos(posX, posY, posZ);
                    this.world.notifyNeighborsOfStateChange(blockpos, this.world.getBlockState(blockpos).getBlock(), false);
                }
            }
        }

        return true;
    }

    @Override
    public void removeStalePortalLocations(long worldTime)
    {
        if(worldTime % 100L == 0L)
        {
            this.destinationCoordinateCache.values().removeIf(portalPosition -> portalPosition == null || portalPosition.lastUpdateTime < worldTime - 300L);
        }
    }

    public static TeleporterNetherEx getTeleporterForWorld(MinecraftServer minecraftServer, int dimension)
    {
        WorldServer worldServer = minecraftServer.getWorld(dimension);

        for(Teleporter teleporter : worldServer.customTeleporters)
        {
            if(teleporter instanceof TeleporterNetherEx)
            {
                return (TeleporterNetherEx) teleporter;
            }
        }

        TeleporterNetherEx teleporterNetherEx = new TeleporterNetherEx(worldServer);
        worldServer.customTeleporters.add(teleporterNetherEx);
        return teleporterNetherEx;
    }
}

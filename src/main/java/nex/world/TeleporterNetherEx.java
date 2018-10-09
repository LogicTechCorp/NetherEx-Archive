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
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import nex.block.BlockNetherPortal;
import nex.init.NetherExBlocks;

/**
 * A teleporter that teleports an entity between any dimension and the nether
 * <p>
 * Based on code written by darkhax and Shinoow here:
 * https://github.com/Darkhax-Minecraft/Hunting-Dimension/blob/3c3046e1a1c8d0c0676cf291e5069f02833eeb09/src/main/java/net/darkhax/huntingdim/dimension/TeleporterHunting.java
 */
public class TeleporterNetherEx extends Teleporter
{
    public TeleporterNetherEx(WorldServer world)
    {
        super(world);
    }

    @Override
    public void placeInPortal(Entity entity, float rotationYaw)
    {
        if(!this.placeInExistingPortal(entity, rotationYaw))
        {
            this.makePortal(entity);
            this.placeInExistingPortal(entity, rotationYaw);
        }
    }

    @Override
    public boolean placeInExistingPortal(Entity entity, float rotationYaw)
    {
        double portalDistanceSq = -1.0D;
        boolean portalExists = true;
        BlockPos portalPos = BlockPos.ORIGIN;
        long portalChunk = ChunkPos.asLong(MathHelper.floor(entity.posX), MathHelper.floor(entity.posZ));

        if(this.destinationCoordinateCache.containsKey(portalChunk))
        {
            Teleporter.PortalPosition portalPosition = this.destinationCoordinateCache.get(portalChunk);
            portalDistanceSq = 0.0D;
            portalPos = portalPosition;
            portalPosition.lastUpdateTime = this.world.getTotalWorldTime();
            portalExists = false;
        }
        else
        {
            BlockPos entityPos = new BlockPos(entity);

            for(int x = -128; x <= 128; x++)
            {
                BlockPos newPos;

                for(int z = -128; z <= 128; z++)
                {
                    for(BlockPos currentPos = entityPos.add(x, this.world.getActualHeight() - 1 - entityPos.getY(), z); currentPos.getY() >= 0; currentPos = newPos)
                    {
                        newPos = currentPos.down();

                        if(this.world.getBlockState(currentPos).getBlock() == NetherExBlocks.NETHER_PORTAL)
                        {
                            for(newPos = currentPos.down(); this.world.getBlockState(newPos).getBlock() == NetherExBlocks.NETHER_PORTAL; newPos = newPos.down())
                            {
                                currentPos = newPos;
                            }

                            double distanceSqToEntity = currentPos.distanceSq(entityPos);

                            if(portalDistanceSq < 0.0D || distanceSqToEntity < portalDistanceSq)
                            {
                                portalDistanceSq = distanceSqToEntity;
                                portalPos = currentPos;
                            }
                        }
                    }
                }
            }
        }

        if(portalDistanceSq >= 0.0D)
        {
            if(portalExists)
            {
                this.destinationCoordinateCache.put(portalChunk, new Teleporter.PortalPosition(portalPos, this.world.getTotalWorldTime()));
            }

            double teleportPosX = (double) portalPos.getX() + 0.5D;
            double teleportPosY = (double) portalPos.getY();
            double teleportPosZ = (double) portalPos.getZ() + 0.5D;
            EnumFacing teleportFacing;

            if(this.world.getBlockState(portalPos.north()).getBlock() == NetherExBlocks.NETHER_PORTAL && this.world.getBlockState(portalPos.east()).getBlock() == NetherExBlocks.NETHER_PORTAL)
            {
                teleportFacing = EnumFacing.SOUTH;
            }
            else if(this.world.getBlockState(portalPos.east()).getBlock() == NetherExBlocks.NETHER_PORTAL && this.world.getBlockState(portalPos.south()).getBlock() == NetherExBlocks.NETHER_PORTAL)
            {
                teleportFacing = EnumFacing.WEST;
            }
            else if(this.world.getBlockState(portalPos.south()).getBlock() == NetherExBlocks.NETHER_PORTAL && this.world.getBlockState(portalPos.west()).getBlock() == NetherExBlocks.NETHER_PORTAL)
            {
                teleportFacing = EnumFacing.NORTH;
            }
            else if(this.world.getBlockState(portalPos.west()).getBlock() == NetherExBlocks.NETHER_PORTAL && this.world.getBlockState(portalPos.north()).getBlock() == NetherExBlocks.NETHER_PORTAL)
            {
                teleportFacing = EnumFacing.EAST;
            }
            else if(this.world.getBlockState(portalPos.north()).getBlock() == NetherExBlocks.NETHER_PORTAL)
            {
                teleportFacing = EnumFacing.EAST;
            }
            else if(this.world.getBlockState(portalPos.east()).getBlock() == NetherExBlocks.NETHER_PORTAL)
            {
                teleportFacing = EnumFacing.SOUTH;
            }
            else if(this.world.getBlockState(portalPos.south()).getBlock() == NetherExBlocks.NETHER_PORTAL)
            {
                teleportFacing = EnumFacing.WEST;
            }
            else if(this.world.getBlockState(portalPos.west()).getBlock() == NetherExBlocks.NETHER_PORTAL)
            {
                teleportFacing = EnumFacing.NORTH;
            }
            else
            {
                teleportFacing = entity.getAdjustedHorizontalFacing();
            }

            EnumFacing horizontalFacing = EnumFacing.byHorizontalIndex(MathHelper.floor(((rotationYaw * 4.0F) / 360.0F) + 0.5F) & 3);
            EnumFacing teleportFacingCCW = teleportFacing.rotateYCCW();
            BlockPos spawnPos = portalPos.offset(teleportFacing);
            BlockPos spawnPosCCW = spawnPos.offset(teleportFacingCCW);

            boolean spawnPosBlocked = !this.world.isAirBlock(spawnPos) || !this.world.isAirBlock(spawnPos.up());
            boolean spawnPosCCWBlocked = !this.world.isAirBlock(spawnPosCCW) || !this.world.isAirBlock(spawnPosCCW.up());

            if(spawnPosCCWBlocked && spawnPosBlocked)
            {
                portalPos = portalPos.offset(teleportFacingCCW);
                teleportFacing = teleportFacing.getOpposite();
                teleportFacingCCW = teleportFacingCCW.getOpposite();
                BlockPos spawnPosTest = portalPos.offset(teleportFacing);
                BlockPos spawnPosCCWTest = spawnPosTest.offset(teleportFacingCCW);
                spawnPosBlocked = !this.world.isAirBlock(spawnPosTest) || !this.world.isAirBlock(spawnPosTest.up());
                spawnPosCCWBlocked = !this.world.isAirBlock(spawnPosCCWTest) || !this.world.isAirBlock(spawnPosCCWTest.up());
            }

            float offsetX = 0.5F;
            float offsetZ = 0.5F;

            if(!spawnPosCCWBlocked && spawnPosBlocked)
            {
                offsetX = 1.0F;
            }
            else if(spawnPosCCWBlocked && !spawnPosBlocked)
            {
                offsetX = 0.0F;
            }
            else if(spawnPosCCWBlocked)
            {
                offsetZ = 0.0F;
            }

            teleportPosX += (teleportFacingCCW.getXOffset() * offsetX) + (teleportFacing.getXOffset() * offsetZ);
            teleportPosZ += (teleportFacingCCW.getZOffset() * offsetX) + (teleportFacing.getZOffset() * offsetZ);
            float motionXPositive = 0.0F;
            float motionXNegative = 0.0F;
            float motionZPositive = 0.0F;
            float motionZNegative = 0.0F;

            if(teleportFacing == horizontalFacing)
            {
                motionXPositive = 1.0F;
                motionXNegative = 1.0F;
            }
            else if(teleportFacing == horizontalFacing.getOpposite())
            {

                motionXPositive = -1.0F;
                motionXNegative = -1.0F;
            }
            else if(teleportFacing == horizontalFacing.rotateY())
            {
                motionZPositive = 1.0F;
                motionZNegative = -1.0F;
            }
            else
            {
                motionZPositive = -1.0F;
                motionZNegative = 1.0F;
            }

            double motionX = entity.motionX;
            double motionZ = entity.motionZ;
            entity.motionX = motionX * motionXPositive + motionZ * motionZNegative;
            entity.motionZ = motionX * motionZPositive + motionZ * motionXNegative;
            entity.rotationYaw = rotationYaw - (horizontalFacing.getHorizontalIndex() * 90) + (teleportFacing.getHorizontalIndex() * 90);
            entity.setLocationAndAngles(teleportPosX, teleportPosY, teleportPosZ, entity.rotationYaw, entity.rotationPitch);
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

        IBlockState state = NetherExBlocks.NETHER_PORTAL.getDefaultState().withProperty(BlockNetherPortal.AXIS, l6 == 0 ? EnumFacing.Axis.X : EnumFacing.Axis.Z);

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
                    this.world.setBlockState(new BlockPos(posX, posY, posZ), beneathWorld ? Blocks.OBSIDIAN.getDefaultState() : state, 2);
                }
            }

            for(int zx = 0; zx < 4; zx++)
            {
                for(int y = -1; y < 4; y++)
                {
                    int posX = i6 + (zx - 1) * l6;
                    int posY = k2 + y;
                    int posZ = k6 + (zx - 1) * i3;
                    BlockPos pos = new BlockPos(posX, posY, posZ);
                    this.world.notifyNeighborsOfStateChange(pos, this.world.getBlockState(pos).getBlock(), false);
                }
            }
        }

        return true;
    }

    public static TeleporterNetherEx getTeleporterForWorld(MinecraftServer minecraftServer, int dimension)
    {
        WorldServer server = minecraftServer.getWorld(dimension);

        for(Teleporter teleporter : server.customTeleporters)
        {
            if(teleporter instanceof TeleporterNetherEx)
            {
                return (TeleporterNetherEx) teleporter;
            }
        }

        TeleporterNetherEx teleporter = new TeleporterNetherEx(server);
        server.customTeleporters.add(teleporter);
        return teleporter;
    }
}

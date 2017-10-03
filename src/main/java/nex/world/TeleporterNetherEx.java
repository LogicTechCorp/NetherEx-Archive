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

import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import nex.init.NetherExBlocks;

public class TeleporterNetherEx extends Teleporter
{
    public TeleporterNetherEx(WorldServer worldIn)
    {
        super(worldIn);
    }

    @Override
    public void placeInPortal(Entity entity, float rotationYaw)
    {
        if(world.provider.getDimensionType().getId() != 1)
        {
            if(!placeInExistingPortal(entity, rotationYaw))
            {
                makePortal(entity);
                placeInExistingPortal(entity, rotationYaw);
            }
        }
        else
        {
            int i = MathHelper.floor(entity.posX);
            int j = MathHelper.floor(entity.posY) - 1;
            int k = MathHelper.floor(entity.posZ);

            for(int j1 = -2; j1 <= 2; ++j1)
            {
                for(int k1 = -2; k1 <= 2; ++k1)
                {
                    for(int l1 = -1; l1 < 3; ++l1)
                    {
                        int i2 = i + k1 * 1 + j1 * 0;
                        int j2 = j + l1;
                        int k2 = k + k1 * 0 - j1 * 1;
                        boolean flag = l1 < 0;
                        world.setBlockState(new BlockPos(i2, j2, k2), flag ? Blocks.OBSIDIAN.getDefaultState() : Blocks.AIR.getDefaultState());
                    }
                }
            }

            entity.setLocationAndAngles((double) i, (double) j, (double) k, entity.rotationYaw, 0.0F);
            entity.motionX = 0.0D;
            entity.motionY = 0.0D;
            entity.motionZ = 0.0D;
        }
    }

    @Override
    public boolean placeInExistingPortal(Entity entity, float rotationYaw)
    {
        double d0 = -1.0D;
        int j = MathHelper.floor(entity.posX);
        int k = MathHelper.floor(entity.posZ);
        boolean flag = true;
        BlockPos originPos = BlockPos.ORIGIN;
        long l = ChunkPos.asLong(j, k);

        if(destinationCoordinateCache.containsKey(l))
        {
            Teleporter.PortalPosition portalPosition = destinationCoordinateCache.get(l);
            d0 = 0.0D;
            originPos = portalPosition;
            portalPosition.lastUpdateTime = world.getTotalWorldTime();
            flag = false;
        }
        else
        {
            BlockPos entityPos = new BlockPos(entity);

            for(int i1 = -128; i1 <= 128; ++i1)
            {
                BlockPos spawnPos;

                for(int j1 = -128; j1 <= 128; ++j1)
                {
                    for(BlockPos testPos = entityPos.add(i1, world.getActualHeight() - 1 - entityPos.getY(), j1); testPos.getY() >= 0; testPos = spawnPos)
                    {
                        spawnPos = testPos.down();

                        if(isValidPortalBlock(world.getBlockState(testPos).getBlock()))
                        {
                            for(spawnPos = testPos.down(); isValidPortalBlock(world.getBlockState(spawnPos).getBlock()); spawnPos = spawnPos.down())
                            {
                                testPos = spawnPos;
                            }

                            double d1 = testPos.distanceSq(entityPos);

                            if(d0 < 0.0D || d1 < d0)
                            {
                                d0 = d1;
                                originPos = testPos;
                            }
                        }
                    }
                }
            }
        }

        if(d0 >= 0.0D)
        {
            if(flag)
            {
                destinationCoordinateCache.put(l, new Teleporter.PortalPosition(originPos, world.getTotalWorldTime()));
            }

            double d5 = (double) originPos.getX() + 0.5D;
            double d7 = (double) originPos.getZ() + 0.5D;
            BlockPattern.PatternHelper patternHelper = Blocks.PORTAL.createPatternHelper(world, originPos);
            boolean flag1 = patternHelper.getForwards().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE;
            double d2 = patternHelper.getForwards().getAxis() == EnumFacing.Axis.X ? (double) patternHelper.getFrontTopLeft().getZ() : (double) patternHelper.getFrontTopLeft().getX();
            double d6 = (double) (patternHelper.getFrontTopLeft().getY() + 1) - entity.getLastPortalVec().y * (double) patternHelper.getHeight();

            if(flag1)
            {
                ++d2;
            }

            if(patternHelper.getForwards().getAxis() == EnumFacing.Axis.X)
            {
                d7 = d2 + (1.0D - entity.getLastPortalVec().x) * (double) patternHelper.getWidth() * (double) patternHelper.getForwards().rotateY().getAxisDirection().getOffset();
            }
            else
            {
                d5 = d2 + (1.0D - entity.getLastPortalVec().x) * (double) patternHelper.getWidth() * (double) patternHelper.getForwards().rotateY().getAxisDirection().getOffset();
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

            double d3 = entity.motionX;
            double d4 = entity.motionZ;
            entity.motionX = d3 * (double) f + d4 * (double) f3;
            entity.motionZ = d3 * (double) f2 + d4 * (double) f1;
            entity.rotationYaw = rotationYaw - (float) (entity.getTeleportDirection().getOpposite().getHorizontalIndex() * 90) + (float) (patternHelper.getForwards().getHorizontalIndex() * 90);

            if(entity instanceof EntityPlayerMP)
            {
                ((EntityPlayerMP) entity).connection.setPlayerLocation(d5, d6, d7, entity.rotationYaw, entity.rotationPitch);
            }
            else
            {
                entity.setLocationAndAngles(d5, d6, d7, entity.rotationYaw, entity.rotationPitch);
            }

            return true;
        }
        else
        {
            return false;
        }
    }

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
        int i2 = random.nextInt(4);
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for(int j2 = j - 16; j2 <= j + 16; ++j2)
        {
            double d1 = (double) j2 + 0.5D - entityIn.posX;

            for(int l2 = l - 16; l2 <= l + 16; ++l2)
            {
                double d2 = (double) l2 + 0.5D - entityIn.posZ;
                label293:

                for(int j3 = world.getActualHeight() - 1; j3 >= 0; --j3)
                {
                    if(world.isAirBlock(mutableBlockPos.setPos(j2, j3, l2)))
                    {
                        while(j3 > 0 && world.isAirBlock(mutableBlockPos.setPos(j2, j3 - 1, l2)))
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
                                        mutableBlockPos.setPos(i5, j5, k5);

                                        if(l4 < 0 && !world.getBlockState(mutableBlockPos).getMaterial().isSolid() || l4 >= 0 && !world.isAirBlock(mutableBlockPos))
                                        {
                                            continue label293;
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
                    label231:

                    for(int i7 = world.getActualHeight() - 1; i7 >= 0; --i7)
                    {
                        if(world.isAirBlock(mutableBlockPos.setPos(l5, i7, j6)))
                        {
                            while(i7 > 0 && world.isAirBlock(mutableBlockPos.setPos(l5, i7 - 1, j6)))
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
                                        mutableBlockPos.setPos(j12, i13, j13);

                                        if(j11 < 0 && !world.getBlockState(mutableBlockPos).getMaterial().isSolid() || j11 >= 0 && !world.isAirBlock(mutableBlockPos))
                                        {
                                            continue label231;
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
            j1 = MathHelper.clamp(j1, 70, world.getActualHeight() - 10);
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
                        boolean flag = k8 < 0;
                        world.setBlockState(new BlockPos(k9, k10, k11), flag ? Blocks.OBSIDIAN.getDefaultState() : Blocks.AIR.getDefaultState());
                    }
                }
            }
        }

        IBlockState iblockstate = Blocks.PORTAL.getDefaultState().withProperty(BlockPortal.AXIS, l6 == 0 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);

        for(int i8 = 0; i8 < 4; ++i8)
        {
            for(int l8 = 0; l8 < 4; ++l8)
            {
                for(int l9 = -1; l9 < 4; ++l9)
                {
                    int l10 = i6 + (l8 - 1) * l6;
                    int l11 = k2 + l9;
                    int k12 = k6 + (l8 - 1) * i3;
                    boolean flag1 = l8 == 0 || l8 == 3 || l9 == -1 || l9 == 3;
                    world.setBlockState(new BlockPos(l10, l11, k12), flag1 ? Blocks.OBSIDIAN.getDefaultState() : iblockstate, 2);
                }
            }

            for(int i9 = 0; i9 < 4; ++i9)
            {
                for(int i10 = -1; i10 < 4; ++i10)
                {
                    int i11 = i6 + (i9 - 1) * l6;
                    int i12 = k2 + i10;
                    int l12 = k6 + (i9 - 1) * i3;
                    BlockPos blockpos = new BlockPos(i11, i12, l12);
                    world.notifyNeighborsOfStateChange(blockpos, world.getBlockState(blockpos).getBlock(), false);
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
            long i = worldTime - 300L;

            destinationCoordinateCache.values().removeIf(portalPosition -> portalPosition == null || portalPosition.lastUpdateTime < i);
        }
    }

    public boolean isValidPortalBlock(Block block)
    {
        return block == Blocks.PORTAL || block == NetherExBlocks.BLOCK_PORTAL_NETHER;
    }
}

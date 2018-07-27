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

package nex.village;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import nex.entity.neutral.EntityGoldGolem;
import nex.entity.passive.EntityPigtificate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PigtificateVillage
{
    private World world;
    private final List<PigtificateVillageFenceGateInfo> villageFenceGates = Lists.newArrayList();
    private BlockPos centerHelper = BlockPos.ORIGIN;
    private BlockPos center = BlockPos.ORIGIN;
    private int villageRadius;
    private int lastAddedFenceGateTimestamp;
    private int tickCounter;
    private int pigtificates;
    private int noBreedTicks;
    private final Map<UUID, Integer> playerReputation = Maps.newHashMap();
    private final List<VillageAggressor> villageAggressors = Lists.newArrayList();
    private int goldGolems;

    public PigtificateVillage()
    {
    }

    public PigtificateVillage(World worldIn)
    {
        world = worldIn;
    }

    public World getWorld()
    {
        return world;
    }

    public void setWorld(World worldIn)
    {
        world = worldIn;
    }

    public void tick(int tickCounterIn)
    {
        tickCounter = tickCounterIn;
        removeDeadAndOutOfRangeFenceGates();
        removeDeadAndOldAggressors();

        if(tickCounterIn % 20 == 0)
        {
            updateNumPigtificates();
        }

        if(tickCounterIn % 30 == 0)
        {
            updateNumGoldGolems();
        }

        int i = pigtificates / 10;

        if(goldGolems < i && villageFenceGates.size() > 20 && world.rand.nextInt(7000) == 0)
        {
            Vec3d vec3d = findRandomSpawnPos(center, 2, 4, 2);

            if(vec3d != null)
            {
                EntityGoldGolem golem = new EntityGoldGolem(world);
                golem.setPosition(vec3d.x, vec3d.y, vec3d.z);
                world.spawnEntity(golem);
                ++goldGolems;
            }
        }
    }

    private Vec3d findRandomSpawnPos(BlockPos pos, int x, int y, int z)
    {
        for(int i = 0; i < 10; ++i)
        {
            BlockPos blockpos = pos.add(world.rand.nextInt(16) - 8, world.rand.nextInt(6) - 3, world.rand.nextInt(16) - 8);

            if(isBlockPosWithinSqVillageRadius(blockpos) && isAreaClearAround(new BlockPos(x, y, z), blockpos))
            {
                return new Vec3d((double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ());
            }
        }

        return null;
    }

    private boolean isAreaClearAround(BlockPos blockSize, BlockPos blockLocation)
    {
        if(!world.getBlockState(blockLocation.down()).isSideSolid(world, blockLocation, EnumFacing.UP))
        {
            return false;
        }
        else
        {
            int i = blockLocation.getX() - blockSize.getX() / 2;
            int j = blockLocation.getZ() - blockSize.getZ() / 2;

            for(int k = i; k < i + blockSize.getX(); ++k)
            {
                for(int l = blockLocation.getY(); l < blockLocation.getY() + blockSize.getY(); ++l)
                {
                    for(int i1 = j; i1 < j + blockSize.getZ(); ++i1)
                    {
                        if(world.getBlockState(new BlockPos(k, l, i1)).isNormalCube())
                        {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
    }

    private void updateNumGoldGolems()
    {
        List<EntityGoldGolem> list = world.getEntitiesWithinAABB(EntityGoldGolem.class, new AxisAlignedBB((double) (center.getX() - villageRadius), (double) (center.getY() - 4), (double) (center.getZ() - villageRadius), (double) (center.getX() + villageRadius), (double) (center.getY() + 4), (double) (center.getZ() + villageRadius)));
        goldGolems = list.size();
    }

    private void updateNumPigtificates()
    {
        List<EntityPigtificate> list = world.getEntitiesWithinAABB(EntityPigtificate.class, new AxisAlignedBB((double) (center.getX() - villageRadius), (double) (center.getY() - 4), (double) (center.getZ() - villageRadius), (double) (center.getX() + villageRadius), (double) (center.getY() + 4), (double) (center.getZ() + villageRadius)));
        pigtificates = list.size();

        if(pigtificates == 0)
        {
            playerReputation.clear();
        }
    }

    public BlockPos getCenter()
    {
        return center;
    }

    public int getVillageRadius()
    {
        return villageRadius;
    }

    public int getNumVillageFenceGates()
    {
        return villageFenceGates.size();
    }

    public int getTicksSinceLastFenceGateAdding()
    {
        return tickCounter - lastAddedFenceGateTimestamp;
    }

    public int getPigtificates()
    {
        return pigtificates;
    }

    public boolean isBlockPosWithinSqVillageRadius(BlockPos pos)
    {
        return center.distanceSq(pos) < (double) (villageRadius * villageRadius);
    }

    public List<PigtificateVillageFenceGateInfo> getVillageFenceGates()
    {
        return villageFenceGates;
    }

    public PigtificateVillageFenceGateInfo getNearestFenceGate(BlockPos pos)
    {
        PigtificateVillageFenceGateInfo fenceGateInfo = null;
        int i = Integer.MAX_VALUE;

        for(PigtificateVillageFenceGateInfo fenceGateInfo1 : villageFenceGates)
        {
            int j = fenceGateInfo1.getDistanceToFenceGateBlockSq(pos);

            if(j < i)
            {
                fenceGateInfo = fenceGateInfo1;
                i = j;
            }
        }

        return fenceGateInfo;
    }

    public PigtificateVillageFenceGateInfo getFenceGateInfo(BlockPos pos)
    {
        PigtificateVillageFenceGateInfo fenceGateInfo = null;
        int i = Integer.MAX_VALUE;

        for(PigtificateVillageFenceGateInfo fenceGateInfo1 : villageFenceGates)
        {
            int j = fenceGateInfo1.getDistanceToFenceGateBlockSq(pos);

            if(j > 256)
            {
                j = j * 1000;
            }
            else
            {
                j = fenceGateInfo1.getFenceGateOpeningRestrictionCounter();
            }

            if(j < i)
            {
                BlockPos blockpos = fenceGateInfo1.getFenceGateBlockPos();
                EnumFacing enumfacing = fenceGateInfo1.getInsideDirection();

                if(world.getBlockState(blockpos.offset(enumfacing, 1)).getBlock().isPassable(world, blockpos.offset(enumfacing, 1)) && world.getBlockState(blockpos.offset(enumfacing, -1)).getBlock().isPassable(world, blockpos.offset(enumfacing, -1)) && world.getBlockState(blockpos.up().offset(enumfacing, 1)).getBlock().isPassable(world, blockpos.up().offset(enumfacing, 1)) && world.getBlockState(blockpos.up().offset(enumfacing, -1)).getBlock().isPassable(world, blockpos.up().offset(enumfacing, -1)))
                {
                    fenceGateInfo = fenceGateInfo1;
                    i = j;
                }
            }
        }

        return fenceGateInfo;
    }

    public PigtificateVillageFenceGateInfo getExistedFenceGate(BlockPos fenceGateBlock)
    {
        if(center.distanceSq(fenceGateBlock) > (double) (villageRadius * villageRadius))
        {
            return null;
        }
        else
        {
            for(PigtificateVillageFenceGateInfo fenceGateInfo : villageFenceGates)
            {
                if(fenceGateInfo.getFenceGateBlockPos().getX() == fenceGateBlock.getX() && fenceGateInfo.getFenceGateBlockPos().getZ() == fenceGateBlock.getZ() && Math.abs(fenceGateInfo.getFenceGateBlockPos().getY() - fenceGateBlock.getY()) <= 1)
                {
                    return fenceGateInfo;
                }
            }

            return null;
        }
    }

    public void addVillageFenceGateInfo(PigtificateVillageFenceGateInfo fenceGateInfo)
    {
        villageFenceGates.add(fenceGateInfo);
        centerHelper = centerHelper.add(fenceGateInfo.getFenceGateBlockPos());
        updateVillageRadiusAndCenter();
        lastAddedFenceGateTimestamp = fenceGateInfo.getLastActivityTimestamp();
    }

    public boolean isAnnihilated()
    {
        return villageFenceGates.isEmpty();
    }

    public void addOrRenewAggressor(EntityLivingBase entitylivingbaseIn)
    {
        for(VillageAggressor aggressor : villageAggressors)
        {
            if(aggressor.aggressor == entitylivingbaseIn)
            {
                aggressor.aggressionTime = tickCounter;
                return;
            }
        }

        villageAggressors.add(new VillageAggressor(entitylivingbaseIn, tickCounter));
    }

    public EntityLivingBase findNearestVillageAggressor(EntityLivingBase entitylivingbaseIn)
    {
        double d0 = Double.MAX_VALUE;
        VillageAggressor aggressor = null;

        for(VillageAggressor villageAggressor : villageAggressors)
        {
            VillageAggressor aggressor1 = villageAggressor;
            double d1 = aggressor1.aggressor.getDistanceSq(entitylivingbaseIn);

            if(d1 <= d0)
            {
                aggressor = aggressor1;
                d0 = d1;
            }
        }

        return aggressor == null ? null : aggressor.aggressor;
    }

    public EntityPlayer getNearestTargetPlayer(EntityLivingBase villageDefender)
    {
        double d0 = Double.MAX_VALUE;
        EntityPlayer entityplayer = null;

        for(UUID s : playerReputation.keySet())
        {
            if(isPlayerReputationTooLow(s))
            {
                EntityPlayer entityplayer1 = world.getPlayerEntityByUUID(s);

                if(entityplayer1 != null)
                {
                    double d1 = entityplayer1.getDistanceSq(villageDefender);

                    if(d1 <= d0)
                    {
                        entityplayer = entityplayer1;
                        d0 = d1;
                    }
                }
            }
        }

        return entityplayer;
    }

    private void removeDeadAndOldAggressors()
    {
        villageAggressors.removeIf(aggressor -> !aggressor.aggressor.isEntityAlive() || Math.abs(tickCounter - aggressor.aggressionTime) > 300);
    }

    private void removeDeadAndOutOfRangeFenceGates()
    {
        boolean flag = false;
        boolean flag1 = world.rand.nextInt(50) == 0;
        Iterator<PigtificateVillageFenceGateInfo> iterator = villageFenceGates.iterator();

        while(iterator.hasNext())
        {
            PigtificateVillageFenceGateInfo fenceGateInfo = iterator.next();

            if(flag1)
            {
                fenceGateInfo.resetFenceGateOpeningRestrictionCounter();
            }

            if(Math.abs(tickCounter - fenceGateInfo.getLastActivityTimestamp()) > 1200)
            {
                centerHelper = centerHelper.subtract(fenceGateInfo.getFenceGateBlockPos());
                flag = true;
                fenceGateInfo.setIsDetachedFromVillageFlag(true);
                iterator.remove();
            }
        }

        if(flag)
        {
            updateVillageRadiusAndCenter();
        }
    }

    private void updateVillageRadiusAndCenter()
    {
        int i = villageFenceGates.size();

        if(i == 0)
        {
            center = BlockPos.ORIGIN;
            villageRadius = 0;
        }
        else
        {
            center = new BlockPos(centerHelper.getX() / i, centerHelper.getY() / i, centerHelper.getZ() / i);
            int j = 0;

            for(PigtificateVillageFenceGateInfo fenceGateInfo : villageFenceGates)
            {
                j = Math.max(fenceGateInfo.getDistanceToFenceGateBlockSq(center), j);
            }

            villageRadius = Math.max(32, (int) Math.sqrt((double) j) + 1);
        }
    }

    public int getPlayerReputation(UUID playerName)
    {
        Integer integer = playerReputation.get(playerName);
        return integer == null ? 0 : integer.intValue();
    }

    private UUID findUUID(String name)
    {
        if(world == null || world.getMinecraftServer() == null)
        {
            return EntityPlayer.getOfflineUUID(name);
        }
        GameProfile profile = world.getMinecraftServer().getPlayerProfileCache().getGameProfileForUsername(name);
        return profile == null ? EntityPlayer.getOfflineUUID(name) : profile.getId();
    }

    public int modifyPlayerReputation(UUID playerName, int reputation)
    {
        int i = getPlayerReputation(playerName);
        int j = MathHelper.clamp(i + reputation, -30, 10);
        playerReputation.put(playerName, Integer.valueOf(j));
        return j;
    }

    public boolean isPlayerReputationTooLow(UUID uuid)
    {
        return getPlayerReputation(uuid) <= -15;
    }

    public void readVillageDataFromNBT(NBTTagCompound compound)
    {
        pigtificates = compound.getInteger("PopSize");
        villageRadius = compound.getInteger("Radius");
        goldGolems = compound.getInteger("Golems");
        lastAddedFenceGateTimestamp = compound.getInteger("Stable");
        tickCounter = compound.getInteger("Tick");
        noBreedTicks = compound.getInteger("MTick");
        center = new BlockPos(compound.getInteger("CX"), compound.getInteger("CY"), compound.getInteger("CZ"));
        centerHelper = new BlockPos(compound.getInteger("ACX"), compound.getInteger("ACY"), compound.getInteger("ACZ"));
        NBTTagList nbttaglist = compound.getTagList("FenceGates", 10);

        for(int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound tagCompound = nbttaglist.getCompoundTagAt(i);
            PigtificateVillageFenceGateInfo fenceGateInfo = new PigtificateVillageFenceGateInfo(new BlockPos(tagCompound.getInteger("X"), tagCompound.getInteger("Y"), tagCompound.getInteger("Z")), tagCompound.getInteger("IDX"), tagCompound.getInteger("IDZ"), tagCompound.getInteger("TS"));
            villageFenceGates.add(fenceGateInfo);
        }

        NBTTagList tagList = compound.getTagList("Players", 10);

        for(int j = 0; j < tagList.tagCount(); ++j)
        {
            NBTTagCompound tagCompound1 = tagList.getCompoundTagAt(j);

            if(tagCompound1.hasKey("UUID"))
            {
                playerReputation.put(UUID.fromString(tagCompound1.getString("UUID")), Integer.valueOf(tagCompound1.getInteger("S")));
            }
            else
            {
                playerReputation.put(findUUID(tagCompound1.getString("Name")), Integer.valueOf(tagCompound1.getInteger("S")));
            }
        }
    }

    public void writeVillageDataToNBT(NBTTagCompound compound)
    {
        compound.setInteger("PopSize", pigtificates);
        compound.setInteger("Radius", villageRadius);
        compound.setInteger("Golems", goldGolems);
        compound.setInteger("Stable", lastAddedFenceGateTimestamp);
        compound.setInteger("Tick", tickCounter);
        compound.setInteger("MTick", noBreedTicks);
        compound.setInteger("CX", center.getX());
        compound.setInteger("CY", center.getY());
        compound.setInteger("CZ", center.getZ());
        compound.setInteger("ACX", centerHelper.getX());
        compound.setInteger("ACY", centerHelper.getY());
        compound.setInteger("ACZ", centerHelper.getZ());
        NBTTagList nbttaglist = new NBTTagList();

        for(PigtificateVillageFenceGateInfo fenceGateInfo : villageFenceGates)
        {
            NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setInteger("X", fenceGateInfo.getFenceGateBlockPos().getX());
            tagCompound.setInteger("Y", fenceGateInfo.getFenceGateBlockPos().getY());
            tagCompound.setInteger("Z", fenceGateInfo.getFenceGateBlockPos().getZ());
            tagCompound.setInteger("IDX", fenceGateInfo.getInsideOffsetX());
            tagCompound.setInteger("IDZ", fenceGateInfo.getInsideOffsetZ());
            tagCompound.setInteger("TS", fenceGateInfo.getLastActivityTimestamp());
            nbttaglist.appendTag(tagCompound);
        }

        compound.setTag("FenceGates", nbttaglist);
        NBTTagList tagList = new NBTTagList();

        for(UUID s : playerReputation.keySet())
        {
            NBTTagCompound tagCompound1 = new NBTTagCompound();

            try
            {
                {
                    tagCompound1.setString("UUID", s.toString());
                    tagCompound1.setInteger("S", playerReputation.get(s));
                    tagList.appendTag(tagCompound1);
                }
            }
            catch(RuntimeException var9)
            {

            }
        }

        compound.setTag("Players", tagList);
    }

    public void endMatingSeason()
    {
        noBreedTicks = tickCounter;
    }

    public boolean isMatingSeason()
    {
        return noBreedTicks == 0 || tickCounter - noBreedTicks >= 3600;
    }

    public void setDefaultPlayerReputation(int defaultReputation)
    {
        for(UUID s : playerReputation.keySet())
        {
            modifyPlayerReputation(s, defaultReputation);
        }
    }

    class VillageAggressor
    {
        public EntityLivingBase aggressor;
        public int aggressionTime;

        VillageAggressor(EntityLivingBase aggressorIn, int aggressionTimeIn)
        {
            aggressor = aggressorIn;
            aggressionTime = aggressionTimeIn;
        }
    }

}

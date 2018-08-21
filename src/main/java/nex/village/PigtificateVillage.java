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

import java.lang.ref.WeakReference;
import java.util.*;

public class PigtificateVillage
{
    private WeakReference<World> world;
    private final List<PigtificateVillageFenceGateInfo> villageFenceGates = new ArrayList<>();
    private BlockPos adjustedCenter = BlockPos.ORIGIN;
    private BlockPos center = BlockPos.ORIGIN;
    private int villageRadius;
    private int lastAddedFenceGateTimestamp;
    private int tickCounter;
    private int pigtificates;
    private int matingTicks;
    private final Map<UUID, Integer> playerReputations = new HashMap<>();
    private final List<VillageAggressor> aggressors = new ArrayList<>();
    private int golems;

    public PigtificateVillage()
    {
    }

    public PigtificateVillage(World world)
    {
        this.world = new WeakReference<>(world);
    }

    public World getWorld()
    {
        return this.world.get();
    }

    public void setWorld(World world)
    {
        this.world = new WeakReference<>(world);
    }

    public void tick(int tickCounter)
    {
        World world = this.world.get();

        if(world != null)
        {
            this.tickCounter = tickCounter;
            updateFenceGates();
            updateAggressors();

            if(tickCounter % 20 == 0)
            {
                updatePigtificates();
            }

            if(tickCounter % 30 == 0)
            {
                updateGolems();
            }

            int i = pigtificates / 10;

            if(golems < i && villageFenceGates.size() > 20 && world.rand.nextInt(7000) == 0)
            {
                Vec3d randomPos = findRandomSpawnPos(center, 2, 4, 2);

                if(randomPos != null)
                {
                    EntityGoldGolem golem = new EntityGoldGolem(world);
                    golem.setPosition(randomPos.x, randomPos.y, randomPos.z);
                    world.spawnEntity(golem);
                    golems++;
                }
            }
        }
    }

    private Vec3d findRandomSpawnPos(BlockPos pos, int x, int y, int z)
    {
        World world = this.world.get();

        if(world != null)
        {
            for(int i = 0; i < 10; i++)
            {
                BlockPos blockPos = pos.add(world.rand.nextInt(16) - 8, world.rand.nextInt(6) - 3, world.rand.nextInt(16) - 8);

                if(isBlockPosWithinSqVillageRadius(blockPos) && isAreaClearAround(new BlockPos(x, y, z), blockPos))
                {
                    return new Vec3d((double) blockPos.getX(), (double) blockPos.getY(), (double) blockPos.getZ());
                }
            }
    }

        return null;
    }

    private boolean isAreaClearAround(BlockPos size, BlockPos pos)
    {
        World world = this.world.get();

        if(world != null)
        {

            if(!world.getBlockState(pos.down()).isSideSolid(world, pos, EnumFacing.UP))
            {
                return false;
            }
            else
            {
                BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
                int posX = pos.getX() - size.getX() / 2;
                int posZ = pos.getZ() - size.getZ() / 2;
                for(int x = posX; x < posX + size.getX(); x++)
                {
                    for(int y = pos.getY(); y < pos.getY() + size.getY(); y++)

                    {

                        for(int z = posZ; z < posZ + size.getZ(); z++)

                        {

                            if(world.getBlockState(mutableBlockPos.setPos(x, y, z)).isNormalCube())

                            {

                                return false;

                            }

                        }

                    }
                }

                return true;
            }
        }

        return false;
    }

    private void updateGolems()
    {
        World world = this.world.get();

        if(world != null)
        {
            List<EntityGoldGolem> golems = world.getEntitiesWithinAABB(EntityGoldGolem.class, new AxisAlignedBB((double) (center.getX() - villageRadius), (double) (center.getY() - 4), (double) (center.getZ() - villageRadius), (double) (center.getX() + villageRadius), (double) (center.getY() + 4), (double) (center.getZ() + villageRadius)));
            this.golems = golems.size();
        }
    }

    private void updatePigtificates()
    {
        World world = this.world.get();

        if(world != null)
        {

            List<EntityPigtificate> pigtificates = world.getEntitiesWithinAABB(EntityPigtificate.class, new AxisAlignedBB((double) (center.getX() - villageRadius), (double) (center.getY() - 4), (double) (center.getZ() - villageRadius), (double) (center.getX() + villageRadius), (double) (center.getY() + 4), (double) (center.getZ() + villageRadius)));
            this.pigtificates = pigtificates.size();

            if(this.pigtificates == 0)
            {
                playerReputations.clear();
            }
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

    public int getVillageFenceGateAmount()
    {
        return villageFenceGates.size();
    }

    public int getTicksSinceLastFenceGateAdded()
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
        int maxDistance = Integer.MAX_VALUE;

        for(PigtificateVillageFenceGateInfo testFenceGate : villageFenceGates)
        {
            int distanceToFenceGate = testFenceGate.getDistanceToFenceGateSq(pos);

            if(distanceToFenceGate < maxDistance)
            {
                fenceGateInfo = testFenceGate;
                maxDistance = distanceToFenceGate;
            }
        }

        return fenceGateInfo;
    }

    public PigtificateVillageFenceGateInfo getFenceGateInfo(BlockPos pos)
    {
        World world = this.world.get();

        if(world != null)
        {

            PigtificateVillageFenceGateInfo fenceGateInfo = null;
            int maxDistance = Integer.MAX_VALUE;

            for(PigtificateVillageFenceGateInfo testFenceGate : villageFenceGates)
            {
                int distanceToFenceGate = testFenceGate.getDistanceToFenceGateSq(pos);

                if(distanceToFenceGate > 256)
                {
                    distanceToFenceGate = distanceToFenceGate * 1000;
                }
                else
                {
                    distanceToFenceGate = testFenceGate.getFenceGateOpeningRestrictionCounter();
                }

                if(distanceToFenceGate < maxDistance)
                {
                    BlockPos blockPos = testFenceGate.getFenceGatePos();
                    EnumFacing facing = testFenceGate.getInsideDirection();

                    if(world.getBlockState(blockPos.offset(facing, 1)).getBlock().isPassable(world, blockPos.offset(facing, 1)) && world.getBlockState(blockPos.offset(facing, -1)).getBlock().isPassable(world, blockPos.offset(facing, -1)) && world.getBlockState(blockPos.up().offset(facing, 1)).getBlock().isPassable(world, blockPos.up().offset(facing, 1)) && world.getBlockState(blockPos.up().offset(facing, -1)).getBlock().isPassable(world, blockPos.up().offset(facing, -1)))
                    {
                        fenceGateInfo = testFenceGate;
                        maxDistance = distanceToFenceGate;
                    }
                }
            }

            return fenceGateInfo;
        }

        return null;
    }

    public PigtificateVillageFenceGateInfo getExistingFenceGate(BlockPos fenceGateBlock)
    {
        if(center.distanceSq(fenceGateBlock) > (double) (villageRadius * villageRadius))
        {
            return null;
        }
        else
        {
            for(PigtificateVillageFenceGateInfo fenceGateInfo : villageFenceGates)
            {
                if(fenceGateInfo.getFenceGatePos().getX() == fenceGateBlock.getX() && fenceGateInfo.getFenceGatePos().getZ() == fenceGateBlock.getZ() && Math.abs(fenceGateInfo.getFenceGatePos().getY() - fenceGateBlock.getY()) <= 1)
                {
                    return fenceGateInfo;
                }
            }

            return null;
        }
    }

    public void addFenceGateInfo(PigtificateVillageFenceGateInfo fenceGateInfo)
    {
        villageFenceGates.add(fenceGateInfo);
        adjustedCenter = adjustedCenter.add(fenceGateInfo.getFenceGatePos());
        lastAddedFenceGateTimestamp = fenceGateInfo.getLastActivityTimestamp();
        updateVillage();
    }

    public boolean isAnnihilated()
    {
        return villageFenceGates.isEmpty();
    }

    public void setAggressor(EntityLivingBase entity)
    {
        for(VillageAggressor aggressor : aggressors)
        {
            if(aggressor.aggressor == entity)
            {
                aggressor.aggressionTime = tickCounter;
                return;
            }
        }

        aggressors.add(new VillageAggressor(entity, tickCounter));
    }

    public EntityLivingBase findNearestVillageAggressor(EntityLivingBase livingEntity)
    {
        double maxDistance = Double.MAX_VALUE;
        VillageAggressor aggressor = null;

        for(VillageAggressor villageAggressor : aggressors)
        {
            double distanceToAggressor = villageAggressor.aggressor.getDistanceSq(livingEntity);

            if(distanceToAggressor <= maxDistance)
            {
                aggressor = villageAggressor;
                maxDistance = distanceToAggressor;
            }
        }

        return aggressor == null ? null : aggressor.aggressor;
    }

    public EntityPlayer getNearestTargetPlayer(EntityLivingBase villageDefender)
    {
        World world = this.world.get();

        if(world != null)
        {

            double maxDistance = Double.MAX_VALUE;
            EntityPlayer player = null;

            for(UUID s : playerReputations.keySet())
            {
                if(isPlayerReputationTooLow(s))
                {
                    EntityPlayer entityPlayer = world.getPlayerEntityByUUID(s);

                    if(entityPlayer != null)
                    {
                        double distanceToPlayer = entityPlayer.getDistanceSq(villageDefender);

                        if(distanceToPlayer <= maxDistance)
                        {
                            player = entityPlayer;
                            maxDistance = distanceToPlayer;
                        }
                    }
                }
            }

            return player;
        }

        return null;
    }

    private void updateAggressors()
    {
        aggressors.removeIf(aggressor -> !aggressor.aggressor.isEntityAlive() || Math.abs(tickCounter - aggressor.aggressionTime) > 300);
    }

    private void updateFenceGates()
    {
        World world = this.world.get();

        if(world != null)
        {

            boolean update = false;
            boolean resetFenceGateTimer = world.rand.nextInt(50) == 0;
            Iterator<PigtificateVillageFenceGateInfo> iter = villageFenceGates.iterator();

            while(iter.hasNext())
            {
                PigtificateVillageFenceGateInfo fenceGateInfo = iter.next();

                if(resetFenceGateTimer)
                {
                    fenceGateInfo.resetFenceGateOpeningRestrictionCounter();
                }

                if(Math.abs(tickCounter - fenceGateInfo.getLastActivityTimestamp()) > 1200)
                {
                    adjustedCenter = adjustedCenter.subtract(fenceGateInfo.getFenceGatePos());
                    update = true;
                    fenceGateInfo.setIsDetachedFromVillageFlag(true);
                    iter.remove();
                }
            }

            if(update)
            {
                updateVillage();
            }
        }
    }

    private void updateVillage()
    {
        int fenceGates = villageFenceGates.size();

        if(fenceGates == 0)
        {
            center = BlockPos.ORIGIN;
            villageRadius = 0;
        }
        else
        {
            center = new BlockPos(adjustedCenter.getX() / fenceGates, adjustedCenter.getY() / fenceGates, adjustedCenter.getZ() / fenceGates);
            int distanceToFenceGate = 0;

            for(PigtificateVillageFenceGateInfo fenceGateInfo : villageFenceGates)
            {
                distanceToFenceGate = Math.max(fenceGateInfo.getDistanceToFenceGateSq(center), distanceToFenceGate);
            }

            villageRadius = Math.max(32, (int) Math.sqrt((double) distanceToFenceGate) + 1);
        }
    }

    public int getPlayerReputation(UUID playerName)
    {
        Integer reputation = playerReputations.get(playerName);
        return reputation == null ? 0 : reputation;
    }

    private UUID findUUID(String name)
    {
        World world = this.world.get();

        if(world != null)
        {
            if(world.getMinecraftServer() == null)
            {
                return EntityPlayer.getOfflineUUID(name);
            }

            GameProfile profile = world.getMinecraftServer().getPlayerProfileCache().getGameProfileForUsername(name);
            return profile == null ? EntityPlayer.getOfflineUUID(name) : profile.getId();
        }

        return EntityPlayer.getOfflineUUID(name);
    }

    public int modifyPlayerReputation(UUID playerName, int amount)
    {
        int reputation = getPlayerReputation(playerName);
        int modifiedReputation = MathHelper.clamp(reputation + amount, -30, 10);
        playerReputations.put(playerName, modifiedReputation);
        return modifiedReputation;
    }

    public boolean isPlayerReputationTooLow(UUID uuid)
    {
        return getPlayerReputation(uuid) <= -15;
    }

    public void readVillageDataFromNBT(NBTTagCompound compound)
    {
        pigtificates = compound.getInteger("PopSize");
        villageRadius = compound.getInteger("Radius");
        golems = compound.getInteger("Golems");
        lastAddedFenceGateTimestamp = compound.getInteger("Stable");
        tickCounter = compound.getInteger("Tick");
        matingTicks = compound.getInteger("MTick");
        center = new BlockPos(compound.getInteger("CX"), compound.getInteger("CY"), compound.getInteger("CZ"));
        adjustedCenter = new BlockPos(compound.getInteger("ACX"), compound.getInteger("ACY"), compound.getInteger("ACZ"));
        NBTTagList fenceGates = compound.getTagList("FenceGates", 10);

        for(int i = 0; i < fenceGates.tagCount(); i++)
        {
            NBTTagCompound fenceGate = fenceGates.getCompoundTagAt(i);
            PigtificateVillageFenceGateInfo fenceGateInfo = new PigtificateVillageFenceGateInfo(new BlockPos(fenceGate.getInteger("X"), fenceGate.getInteger("Y"), fenceGate.getInteger("Z")), fenceGate.getInteger("IDX"), fenceGate.getInteger("IDZ"), fenceGate.getInteger("TS"));
            villageFenceGates.add(fenceGateInfo);
        }

        NBTTagList players = compound.getTagList("Players", 10);

        for(int j = 0; j < players.tagCount(); ++j)
        {
            NBTTagCompound player = players.getCompoundTagAt(j);

            if(player.hasKey("UUID"))
            {
                playerReputations.put(UUID.fromString(player.getString("UUID")), player.getInteger("S"));
            }
            else
            {
                playerReputations.put(findUUID(player.getString("Name")), player.getInteger("S"));
            }
        }
    }

    public void writeVillageDataToNBT(NBTTagCompound compound)
    {
        compound.setInteger("PopSize", pigtificates);
        compound.setInteger("Radius", villageRadius);
        compound.setInteger("Golems", golems);
        compound.setInteger("Stable", lastAddedFenceGateTimestamp);
        compound.setInteger("Tick", tickCounter);
        compound.setInteger("MTick", matingTicks);
        compound.setInteger("CX", center.getX());
        compound.setInteger("CY", center.getY());
        compound.setInteger("CZ", center.getZ());
        compound.setInteger("ACX", adjustedCenter.getX());
        compound.setInteger("ACY", adjustedCenter.getY());
        compound.setInteger("ACZ", adjustedCenter.getZ());
        NBTTagList fenceGates = new NBTTagList();

        for(PigtificateVillageFenceGateInfo fenceGateInfo : villageFenceGates)
        {
            NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setInteger("X", fenceGateInfo.getFenceGatePos().getX());
            tagCompound.setInteger("Y", fenceGateInfo.getFenceGatePos().getY());
            tagCompound.setInteger("Z", fenceGateInfo.getFenceGatePos().getZ());
            tagCompound.setInteger("IDX", fenceGateInfo.getInsideOffsetX());
            tagCompound.setInteger("IDZ", fenceGateInfo.getInsideOffsetZ());
            tagCompound.setInteger("TS", fenceGateInfo.getLastActivityTimestamp());
            fenceGates.appendTag(tagCompound);
        }

        compound.setTag("FenceGates", fenceGates);
        NBTTagList players = new NBTTagList();

        for(UUID s : playerReputations.keySet())
        {
            NBTTagCompound player = new NBTTagCompound();

            try
            {
                player.setString("UUID", s.toString());
                player.setInteger("S", playerReputations.get(s));
                players.appendTag(player);
            }
            catch(RuntimeException ignored)
            {

            }
        }

        compound.setTag("Players", players);
    }

    public void endMatingSeason()
    {
        matingTicks = tickCounter;
    }

    public boolean isMatingSeason()
    {
        return matingTicks == 0 || tickCounter - matingTicks >= 3600;
    }

    public void setDefaultPlayerReputation(int defaultReputation)
    {
        for(UUID uuid : playerReputations.keySet())
        {
            modifyPlayerReputation(uuid, defaultReputation);
        }
    }

    class VillageAggressor
    {
        public EntityLivingBase aggressor;
        public int aggressionTime;

        VillageAggressor(EntityLivingBase aggressor, int aggressionTime)
        {
            this.aggressor = aggressor;
            this.aggressionTime = aggressionTime;
        }
    }

}

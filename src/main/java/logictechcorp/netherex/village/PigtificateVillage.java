/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
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

package logictechcorp.netherex.village;

import com.mojang.authlib.GameProfile;
import logictechcorp.netherex.entity.neutral.EntityGoldGolem;
import logictechcorp.netherex.entity.passive.EntityPigtificate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.*;

public class PigtificateVillage
{
    private final List<PigtificateVillageFenceGateInfo> fenceGates = new ArrayList<>();
    private BlockPos adjustedCenter = BlockPos.ORIGIN;
    private BlockPos center = BlockPos.ORIGIN;
    private int radius;
    private int lastAddedFenceGateTime;
    private int tickCounter;
    private int pigtificateAmount;
    private int matingCounter;
    private final Map<UUID, Integer> playerReputations = new HashMap<>();
    private final List<VillageAggressor> aggressors = new ArrayList<>();
    private int golemAmount;

    public PigtificateVillage()
    {
    }

    public void tick(World world, int tickCounter)
    {
        this.tickCounter = tickCounter;
        this.updateFenceGates(world);
        this.updateAggressors();

        if(tickCounter % 20 == 0)
        {
            this.updatePigtificates(world);
        }

        if(tickCounter % 30 == 0)
        {
            this.updateGolems(world);
        }

        int i = this.pigtificateAmount / 10;

        if(this.golemAmount < i && this.fenceGates.size() > 20 && world.rand.nextInt(7000) == 0)
        {
            Vec3d randomPos = this.findRandomSpawnPos(world, this.center, 2, 4, 2);

            if(randomPos != null)
            {
                EntityGoldGolem golem = new EntityGoldGolem(world);
                golem.setPosition(randomPos.x, randomPos.y, randomPos.z);
                world.spawnEntity(golem);
                this.golemAmount++;
            }
        }
    }

    private Vec3d findRandomSpawnPos(World world, BlockPos pos, int xPos, int yPos, int zPos)
    {
        for(int i = 0; i < 10; i++)
        {
            BlockPos blockPos = pos.add(world.rand.nextInt(16) - 8, world.rand.nextInt(6) - 3, world.rand.nextInt(16) - 8);

            if(this.isBlockPosWithinSqVillageRadius(blockPos) && this.isAreaClearAround(world, new BlockPos(xPos, yPos, zPos), blockPos))
            {
                return new Vec3d(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            }
        }

        return null;
    }

    private boolean isAreaClearAround(World world, BlockPos size, BlockPos pos)
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

    private void updateGolems(World world)
    {
        List<EntityGoldGolem> golems = world.getEntitiesWithinAABB(EntityGoldGolem.class, new AxisAlignedBB((this.center.getX() - this.radius), (this.center.getY() - 4), (this.center.getZ() - this.radius), (this.center.getX() + this.radius), (this.center.getY() + 4), (this.center.getZ() + this.radius)));
        this.golemAmount = golems.size();
    }

    private void updatePigtificates(World world)
    {
        List<EntityPigtificate> pigtificates = world.getEntitiesWithinAABB(EntityPigtificate.class, new AxisAlignedBB((this.center.getX() - this.radius), (this.center.getY() - 4), (this.center.getZ() - this.radius), (this.center.getX() + this.radius), (this.center.getY() + 4), (this.center.getZ() + this.radius)));
        this.pigtificateAmount = pigtificates.size();

        if(this.pigtificateAmount == 0)
        {
            this.playerReputations.clear();
        }
    }

    public BlockPos getCenter()
    {
        return this.center;
    }

    public int getRadius()
    {
        return this.radius;
    }

    public int getVillageFenceGateAmount()
    {
        return this.fenceGates.size();
    }

    public int getTicksSinceLastFenceGateAdded()
    {
        return this.tickCounter - this.lastAddedFenceGateTime;
    }

    public int getPigtificateAmount()
    {
        return this.pigtificateAmount;
    }

    public boolean isBlockPosWithinSqVillageRadius(BlockPos pos)
    {
        return this.center.distanceSq(pos) < (double) (this.radius * this.radius);
    }

    public List<PigtificateVillageFenceGateInfo> getFenceGates()
    {
        return this.fenceGates;
    }

    public PigtificateVillageFenceGateInfo getNearestFenceGate(BlockPos pos)
    {
        PigtificateVillageFenceGateInfo fenceGateInfo = null;
        int maxDistance = Integer.MAX_VALUE;

        for(PigtificateVillageFenceGateInfo testFenceGate : this.fenceGates)
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

    public PigtificateVillageFenceGateInfo getFenceGateInfo(World world, BlockPos pos)
    {
        PigtificateVillageFenceGateInfo fenceGateInfo = null;
        int maxDistance = Integer.MAX_VALUE;

        for(PigtificateVillageFenceGateInfo testFenceGate : this.fenceGates)
        {
            int distanceToFenceGate = testFenceGate.getDistanceToFenceGateSq(pos);

            if(distanceToFenceGate > 256)
            {
                distanceToFenceGate = distanceToFenceGate * 1000;
            }
            else
            {
                distanceToFenceGate = testFenceGate.getOpenRestrictionCounter();
            }

            if(distanceToFenceGate < maxDistance)
            {
                BlockPos blockPos = testFenceGate.getPos();
                EnumFacing facing = testFenceGate.getInside();

                if(world.getBlockState(blockPos.offset(facing, 1)).getBlock().isPassable(world, blockPos.offset(facing, 1)) && world.getBlockState(blockPos.offset(facing, -1)).getBlock().isPassable(world, blockPos.offset(facing, -1)) && world.getBlockState(blockPos.up().offset(facing, 1)).getBlock().isPassable(world, blockPos.up().offset(facing, 1)) && world.getBlockState(blockPos.up().offset(facing, -1)).getBlock().isPassable(world, blockPos.up().offset(facing, -1)))
                {
                    fenceGateInfo = testFenceGate;
                    maxDistance = distanceToFenceGate;
                }
            }
        }

        return fenceGateInfo;
    }

    public PigtificateVillageFenceGateInfo getExistingFenceGate(BlockPos fenceGateBlock)
    {
        if(this.center.distanceSq(fenceGateBlock) > (double) (this.radius * this.radius))
        {
            return null;
        }
        else
        {
            for(PigtificateVillageFenceGateInfo fenceGateInfo : this.fenceGates)
            {
                if(fenceGateInfo.getPos().getX() == fenceGateBlock.getX() && fenceGateInfo.getPos().getZ() == fenceGateBlock.getZ() && Math.abs(fenceGateInfo.getPos().getY() - fenceGateBlock.getY()) <= 1)
                {
                    return fenceGateInfo;
                }
            }

            return null;
        }
    }

    public void addFenceGateInfo(PigtificateVillageFenceGateInfo fenceGateInfo)
    {
        this.fenceGates.add(fenceGateInfo);
        this.adjustedCenter = this.adjustedCenter.add(fenceGateInfo.getPos());
        this.lastAddedFenceGateTime = fenceGateInfo.getLastActivityTime();
        this.updateVillage();
    }

    public boolean isAnnihilated()
    {
        return this.fenceGates.isEmpty();
    }

    public void setAggressor(EntityLivingBase entity)
    {
        for(VillageAggressor aggressor : this.aggressors)
        {
            if(aggressor.aggressor == entity)
            {
                aggressor.aggressionTime = this.tickCounter;
                return;
            }
        }

        this.aggressors.add(new VillageAggressor(entity, this.tickCounter));
    }

    public EntityLivingBase findNearestVillageAggressor(EntityLivingBase livingEntity)
    {
        double maxDistance = Double.MAX_VALUE;
        VillageAggressor aggressor = null;

        for(VillageAggressor villageAggressor : this.aggressors)
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

    public EntityPlayer getNearestTargetPlayer(World world, EntityLivingBase villageDefender)
    {
        double maxDistance = Double.MAX_VALUE;
        EntityPlayer player = null;

        for(UUID s : this.playerReputations.keySet())
        {
            if(this.isPlayerReputationTooLow(s))
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

    private void updateAggressors()
    {
        this.aggressors.removeIf(aggressor -> !aggressor.aggressor.isEntityAlive() || Math.abs(this.tickCounter - aggressor.aggressionTime) > 300);
    }

    private void updateFenceGates(World world)
    {
        boolean update = false;
        boolean resetFenceGateTimer = world.rand.nextInt(50) == 0;
        Iterator<PigtificateVillageFenceGateInfo> iter = this.fenceGates.iterator();

        while(iter.hasNext())
        {
            PigtificateVillageFenceGateInfo fenceGateInfo = iter.next();

            if(resetFenceGateTimer)
            {
                fenceGateInfo.resetOpenRestrictionCounter();
            }

            if(Math.abs(this.tickCounter - fenceGateInfo.getLastActivityTime()) > 1200)
            {
                this.adjustedCenter = this.adjustedCenter.subtract(fenceGateInfo.getPos());
                update = true;
                fenceGateInfo.setDetachedFromVillageFlag(true);
                iter.remove();
            }
        }

        if(update)
        {
            this.updateVillage();
        }
    }

    private void updateVillage()
    {
        int fenceGates = this.fenceGates.size();

        if(fenceGates == 0)
        {
            this.center = BlockPos.ORIGIN;
            this.radius = 0;
        }
        else
        {
            this.center = new BlockPos(this.adjustedCenter.getX() / fenceGates, this.adjustedCenter.getY() / fenceGates, this.adjustedCenter.getZ() / fenceGates);
            int distanceToFenceGate = 0;

            for(PigtificateVillageFenceGateInfo fenceGateInfo : this.fenceGates)
            {
                distanceToFenceGate = Math.max(fenceGateInfo.getDistanceToFenceGateSq(this.center), distanceToFenceGate);
            }

            this.radius = Math.max(32, (int) Math.sqrt(distanceToFenceGate) + 1);
        }
    }

    public int getPlayerReputation(UUID playerName)
    {
        Integer reputation = this.playerReputations.get(playerName);
        return reputation == null ? 0 : reputation;
    }

    private UUID findUUID(String name)
    {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

        if(server == null)
        {
            return EntityPlayer.getOfflineUUID(name);
        }

        GameProfile profile = server.getPlayerProfileCache().getGameProfileForUsername(name);
        return profile == null ? EntityPlayer.getOfflineUUID(name) : profile.getId();
    }

    public int modifyPlayerReputation(UUID playerName, int amount)
    {
        int reputation = this.getPlayerReputation(playerName);
        int modifiedReputation = MathHelper.clamp(reputation + amount, -30, 10);
        this.playerReputations.put(playerName, modifiedReputation);
        return modifiedReputation;
    }

    public boolean isPlayerReputationTooLow(UUID uuid)
    {
        return this.getPlayerReputation(uuid) <= -15;
    }

    public void readVillageDataFromNBT(NBTTagCompound compound)
    {
        this.pigtificateAmount = compound.getInteger("PopSize");
        this.radius = compound.getInteger("Radius");
        this.golemAmount = compound.getInteger("Golems");
        this.lastAddedFenceGateTime = compound.getInteger("Stable");
        this.tickCounter = compound.getInteger("Tick");
        this.matingCounter = compound.getInteger("MTick");
        this.center = new BlockPos(compound.getInteger("CX"), compound.getInteger("CY"), compound.getInteger("CZ"));
        this.adjustedCenter = new BlockPos(compound.getInteger("ACX"), compound.getInteger("ACY"), compound.getInteger("ACZ"));
        NBTTagList fenceGates = compound.getTagList("FenceGates", 10);

        for(int i = 0; i < fenceGates.tagCount(); i++)
        {
            NBTTagCompound fenceGate = fenceGates.getCompoundTagAt(i);
            PigtificateVillageFenceGateInfo fenceGateInfo = new PigtificateVillageFenceGateInfo(new BlockPos(fenceGate.getInteger("X"), fenceGate.getInteger("Y"), fenceGate.getInteger("Z")), fenceGate.getInteger("IDX"), fenceGate.getInteger("IDZ"), fenceGate.getInteger("TS"));
            this.fenceGates.add(fenceGateInfo);
        }

        NBTTagList players = compound.getTagList("Players", 10);

        for(int j = 0; j < players.tagCount(); ++j)
        {
            NBTTagCompound player = players.getCompoundTagAt(j);

            if(player.hasKey("UUID"))
            {
                this.playerReputations.put(UUID.fromString(player.getString("UUID")), player.getInteger("S"));
            }
            else
            {
                this.playerReputations.put(this.findUUID(player.getString("Name")), player.getInteger("S"));
            }
        }
    }

    public void writeVillageDataToNBT(NBTTagCompound compound)
    {
        compound.setInteger("PopSize", this.pigtificateAmount);
        compound.setInteger("Radius", this.radius);
        compound.setInteger("Golems", this.golemAmount);
        compound.setInteger("Stable", this.lastAddedFenceGateTime);
        compound.setInteger("Tick", this.tickCounter);
        compound.setInteger("MTick", this.matingCounter);
        compound.setInteger("CX", this.center.getX());
        compound.setInteger("CY", this.center.getY());
        compound.setInteger("CZ", this.center.getZ());
        compound.setInteger("ACX", this.adjustedCenter.getX());
        compound.setInteger("ACY", this.adjustedCenter.getY());
        compound.setInteger("ACZ", this.adjustedCenter.getZ());
        NBTTagList fenceGates = new NBTTagList();

        for(PigtificateVillageFenceGateInfo fenceGateInfo : this.fenceGates)
        {
            NBTTagCompound tagCompound = new NBTTagCompound();
            tagCompound.setInteger("X", fenceGateInfo.getPos().getX());
            tagCompound.setInteger("Y", fenceGateInfo.getPos().getY());
            tagCompound.setInteger("Z", fenceGateInfo.getPos().getZ());
            tagCompound.setInteger("IDX", fenceGateInfo.getInsideOffsetX());
            tagCompound.setInteger("IDZ", fenceGateInfo.getInsideOffsetZ());
            tagCompound.setInteger("TS", fenceGateInfo.getLastActivityTime());
            fenceGates.appendTag(tagCompound);
        }

        compound.setTag("FenceGates", fenceGates);
        NBTTagList players = new NBTTagList();

        for(UUID s : this.playerReputations.keySet())
        {
            NBTTagCompound player = new NBTTagCompound();

            try
            {
                player.setString("UUID", s.toString());
                player.setInteger("S", this.playerReputations.get(s));
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
        this.matingCounter = this.tickCounter;
    }

    public boolean isMatingSeason()
    {
        return this.matingCounter == 0 || this.tickCounter - this.matingCounter >= 3600;
    }

    public void setDefaultPlayerReputation(int defaultReputation)
    {
        for(UUID uuid : this.playerReputations.keySet())
        {
            this.modifyPlayerReputation(uuid, defaultReputation);
        }
    }

    static class VillageAggressor
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

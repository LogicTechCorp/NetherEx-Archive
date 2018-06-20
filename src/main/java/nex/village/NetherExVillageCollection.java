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
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.storage.WorldSavedData;

import java.util.Iterator;
import java.util.List;

public class NetherExVillageCollection extends WorldSavedData
{
    private World world;
    private final List<BlockPos> villagerPositions = Lists.newArrayList();
    private final List<NetherExVillageFenceGateInfo> newFenceGates = Lists.newArrayList();
    private final List<NetherExVillage> villageList = Lists.newArrayList();
    private int tickCounter;

    public NetherExVillageCollection(String name)
    {
        super(name);
    }

    public NetherExVillageCollection(World worldIn)
    {
        super(fileNameForProvider(worldIn.provider));
        world = worldIn;
        markDirty();
    }

    public void setWorldsForAll(World worldIn)
    {
        if(world == worldIn)
        {
            return;
        }

        world = worldIn;

        for(NetherExVillage village : villageList)
        {
            village.setWorld(worldIn);
        }
    }

    public void addToVillagerPositionList(BlockPos pos)
    {
        if(villagerPositions.size() <= 64)
        {
            if(!positionInList(pos))
            {
                villagerPositions.add(pos);
            }
        }
    }

    public void tick()
    {
        tickCounter++;

        for(NetherExVillage village : villageList)
        {
            village.tick(tickCounter);
        }

        removeAnnihilatedVillages();
        dropOldestVillagerPosition();
        addNewFenceGatesToVillageOrCreateVillage();

        if(tickCounter % 400 == 0)
        {
            markDirty();
        }
    }

    private void removeAnnihilatedVillages()
    {
        Iterator<NetherExVillage> iterator = villageList.iterator();

        while(iterator.hasNext())
        {
            NetherExVillage village = iterator.next();

            if(village.isAnnihilated())
            {
                iterator.remove();
                markDirty();
            }
        }
    }

    public List<NetherExVillage> getVillageList()
    {
        return villageList;
    }

    public NetherExVillage getNearestVillage(BlockPos fenceGateBlock, int radius)
    {
        NetherExVillage village = null;
        double d0 = 3.4028234663852886E38D;

        for(NetherExVillage village1 : villageList)
        {
            double d1 = village1.getCenter().distanceSq(fenceGateBlock);

            if(d1 < d0)
            {
                float f = (float) (radius + village1.getVillageRadius());

                if(d1 <= (double) (f * f))
                {
                    village = village1;
                    d0 = d1;
                }
            }
        }

        return village;
    }

    private void dropOldestVillagerPosition()
    {
        if(!villagerPositions.isEmpty())
        {
            addFenceGatesAround(villagerPositions.remove(0));
        }
    }

    private void addNewFenceGatesToVillageOrCreateVillage()
    {
        for(NetherExVillageFenceGateInfo villagefenceGateinfo : newFenceGates)
        {
            NetherExVillage village = getNearestVillage(villagefenceGateinfo.getFenceGateBlockPos(), 32);

            if(village == null)
            {
                village = new NetherExVillage(world);
                villageList.add(village);
                markDirty();
            }

            village.addVillageFenceGateInfo(villagefenceGateinfo);
        }

        newFenceGates.clear();
    }

    private void addFenceGatesAround(BlockPos central)
    {
        for(int l = -16; l < 16; ++l)
        {
            for(int i1 = -4; i1 < 4; ++i1)
            {
                for(int j1 = -16; j1 < 16; ++j1)
                {
                    BlockPos blockpos = central.add(l, i1, j1);

                    EnumFacing outside = getOutside(blockpos);

                    if(outside != null)
                    {
                        NetherExVillageFenceGateInfo villagefenceGateinfo = checkFenceGateExistence(blockpos);

                        if(villagefenceGateinfo == null)
                        {
                            addToNewFenceGatesList(blockpos, outside);
                        }
                        else
                        {
                            villagefenceGateinfo.setLastActivityTimestamp(tickCounter);
                        }
                    }
                }
            }
        }
    }

    private NetherExVillageFenceGateInfo checkFenceGateExistence(BlockPos fenceGateBlock)
    {
        for(NetherExVillageFenceGateInfo villagefenceGateinfo : newFenceGates)
        {
            if(villagefenceGateinfo.getFenceGateBlockPos().getX() == fenceGateBlock.getX() && villagefenceGateinfo.getFenceGateBlockPos().getZ() == fenceGateBlock.getZ() && Math.abs(villagefenceGateinfo.getFenceGateBlockPos().getY() - fenceGateBlock.getY()) <= 1)
            {
                return villagefenceGateinfo;
            }
        }

        for(NetherExVillage village : villageList)
        {
            NetherExVillageFenceGateInfo villagefenceGateinfo1 = village.getExistedFenceGate(fenceGateBlock);

            if(villagefenceGateinfo1 != null)
            {
                return villagefenceGateinfo1;
            }
        }

        return null;
    }

    private void addToNewFenceGatesList(BlockPos fenceGateBlock, EnumFacing facing)
    {
        newFenceGates.add(new NetherExVillageFenceGateInfo(fenceGateBlock, facing, tickCounter));
    }

    private boolean positionInList(BlockPos pos)
    {
        for(BlockPos blockpos : villagerPositions)
        {
            if(blockpos.equals(pos))
            {
                return true;
            }
        }

        return false;
    }

    private EnumFacing getOutside(BlockPos fenceGatePos)
    {
        IBlockState fenceGateState = world.getBlockState(fenceGatePos);
        IBlockState stairState = world.getBlockState(fenceGatePos.up(2));

        if(fenceGateState.getBlock() instanceof BlockFenceGate && stairState.getBlock() instanceof BlockStairs)
        {
            EnumFacing fenceGateFacing = fenceGateState.getValue(BlockFenceGate.FACING);
            EnumFacing stairFacing = stairState.getValue(BlockStairs.FACING);

            if(stairFacing == fenceGateFacing || stairFacing == fenceGateFacing.getOpposite())
            {
                return stairFacing;
            }

            return null;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        tickCounter = nbt.getInteger("Tick");
        NBTTagList nbttaglist = nbt.getTagList("NetherVillages", 10);

        for(int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            NetherExVillage village = new NetherExVillage();
            village.readVillageDataFromNBT(nbttagcompound);
            villageList.add(village);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("Tick", tickCounter);
        NBTTagList nbttaglist = new NBTTagList();

        for(NetherExVillage village : villageList)
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            village.writeVillageDataToNBT(nbttagcompound);
            nbttaglist.appendTag(nbttagcompound);
        }

        compound.setTag("NetherVillages", nbttaglist);
        return compound;
    }

    public static String fileNameForProvider(WorldProvider provider)
    {
        return "nex_villages" + provider.getDimensionType().getSuffix();
    }
}

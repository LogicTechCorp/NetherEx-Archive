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

public class PigtificateVillageCollection extends WorldSavedData
{
    private World world;
    private final List<BlockPos> pigtificatePositions = Lists.newArrayList();
    private final List<PigtificateVillageFenceGateInfo> newFenceGates = Lists.newArrayList();
    private final List<PigtificateVillage> villageList = Lists.newArrayList();
    private int tickCounter;

    public PigtificateVillageCollection(String name)
    {
        super(name);
    }

    public PigtificateVillageCollection(World worldIn)
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

        for(PigtificateVillage village : villageList)
        {
            village.setWorld(worldIn);
        }
    }

    public void addToVillagerPositionList(BlockPos pos)
    {
        if(pigtificatePositions.size() <= 64)
        {
            if(!positionInList(pos))
            {
                pigtificatePositions.add(pos);
            }
        }
    }

    public void tick()
    {
        tickCounter++;

        for(PigtificateVillage village : villageList)
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
        Iterator<PigtificateVillage> iterator = villageList.iterator();

        while(iterator.hasNext())
        {
            PigtificateVillage village = iterator.next();

            if(village.isAnnihilated())
            {
                iterator.remove();
                markDirty();
            }
        }
    }

    public List<PigtificateVillage> getVillageList()
    {
        return villageList;
    }

    public PigtificateVillage getNearestVillage(BlockPos fenceGateBlock, int radius)
    {
        PigtificateVillage village = null;
        double d0 = 3.4028234663852886E38D;

        for(PigtificateVillage pigtificateVillage : villageList)
        {
            double d1 = pigtificateVillage.getCenter().distanceSq(fenceGateBlock);

            if(d1 < d0)
            {
                float f = (float) (radius + pigtificateVillage.getVillageRadius());

                if(d1 <= (double) (f * f))
                {
                    village = pigtificateVillage;
                    d0 = d1;
                }
            }
        }

        return village;
    }

    private void dropOldestVillagerPosition()
    {
        if(!pigtificatePositions.isEmpty())
        {
            addFenceGatesAround(pigtificatePositions.remove(0));
        }
    }

    private void addNewFenceGatesToVillageOrCreateVillage()
    {
        for(PigtificateVillageFenceGateInfo fenceGateInfo : newFenceGates)
        {
            PigtificateVillage village = getNearestVillage(fenceGateInfo.getFenceGateBlockPos(), 32);

            if(village == null)
            {
                village = new PigtificateVillage(world);
                villageList.add(village);
                markDirty();
            }

            village.addVillageFenceGateInfo(fenceGateInfo);
        }

        newFenceGates.clear();
    }

    private void addFenceGatesAround(BlockPos central)
    {
        for(int x = -16; x < 16; ++x)
        {
            for(int y = -4; y < 4; ++y)
            {
                for(int z = -16; z < 16; ++z)
                {
                    BlockPos blockpos = central.add(x, y, z);
                    EnumFacing outside = getOutside(blockpos);

                    if(outside != null)
                    {
                        PigtificateVillageFenceGateInfo fenceGateInfo = checkFenceGateExistence(blockpos);

                        if(fenceGateInfo == null)
                        {
                            addToNewFenceGatesList(blockpos, outside);
                        }
                        else
                        {
                            fenceGateInfo.setLastActivityTimestamp(tickCounter);
                        }
                    }
                }
            }
        }
    }

    private PigtificateVillageFenceGateInfo checkFenceGateExistence(BlockPos fenceGateBlock)
    {
        for(PigtificateVillageFenceGateInfo fenceGateInfo : newFenceGates)
        {
            if(fenceGateInfo.getFenceGateBlockPos().getX() == fenceGateBlock.getX() && fenceGateInfo.getFenceGateBlockPos().getZ() == fenceGateBlock.getZ() && Math.abs(fenceGateInfo.getFenceGateBlockPos().getY() - fenceGateBlock.getY()) <= 1)
            {
                return fenceGateInfo;
            }
        }

        for(PigtificateVillage village : villageList)
        {
            PigtificateVillageFenceGateInfo fenceGateInfo = village.getExistedFenceGate(fenceGateBlock);

            if(fenceGateInfo != null)
            {
                return fenceGateInfo;
            }
        }

        return null;
    }

    private void addToNewFenceGatesList(BlockPos fenceGateBlock, EnumFacing facing)
    {
        newFenceGates.add(new PigtificateVillageFenceGateInfo(fenceGateBlock, facing, tickCounter));
    }

    private boolean positionInList(BlockPos pos)
    {
        for(BlockPos blockPos : pigtificatePositions)
        {
            if(blockPos.equals(pos))
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
        NBTTagList list = nbt.getTagList("PigtificateVillages", 10);

        for(int i = 0; i < list.tagCount(); ++i)
        {
            NBTTagCompound villageData = list.getCompoundTagAt(i);
            PigtificateVillage village = new PigtificateVillage();
            village.readVillageDataFromNBT(villageData);
            villageList.add(village);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("Tick", tickCounter);
        NBTTagList list = new NBTTagList();

        for(PigtificateVillage village : villageList)
        {
            NBTTagCompound villageData = new NBTTagCompound();
            village.writeVillageDataToNBT(villageData);
            list.appendTag(villageData);
        }

        compound.setTag("PigtificateVillages", list);
        return compound;
    }

    public static String fileNameForProvider(WorldProvider provider)
    {
        return "pigtificate_villages" + provider.getDimensionType().getSuffix();
    }
}

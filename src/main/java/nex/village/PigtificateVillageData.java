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

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PigtificateVillageData extends WorldSavedData
{
    private World world;
    private final List<BlockPos> pigtificatePositions = new ArrayList<>();
    private final List<PigtificateVillageFenceGateInfo> newFenceGates = new ArrayList<>();
    private final List<PigtificateVillage> villages = new ArrayList<>();
    private int tickCounter;

    public PigtificateVillageData(String name)
    {
        super(name);
    }

    public PigtificateVillageData(World world)
    {
        super(getFileName(world));
        this.world = world;
        markDirty();
    }

    public void setWorld(World world)
    {
        if(this.world == world)
        {
            return;
        }

        this.world = world;

        for(PigtificateVillage village : villages)
        {
            village.setWorld(world);
        }
    }

    public void addPigtificate(BlockPos pos)
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

        for(PigtificateVillage village : villages)
        {
            village.tick(tickCounter);
        }

        removeAnnihilatedVillages();
        dropOldestVillagerPosition();
        updateVillages();

        if(tickCounter % 400 == 0)
        {
            markDirty();
        }
    }

    private void removeAnnihilatedVillages()
    {
        Iterator<PigtificateVillage> iter = villages.iterator();

        while(iter.hasNext())
        {
            PigtificateVillage village = iter.next();

            if(village.isAnnihilated())
            {
                iter.remove();
                markDirty();
            }
        }
    }

    public List<PigtificateVillage> getVillages()
    {
        return villages;
    }

    public PigtificateVillage getNearestVillage(BlockPos fenceGateBlock, int radius)
    {
        PigtificateVillage village = null;
        double maxDistance = 3.4028234663852886E38D;

        for(PigtificateVillage pigtificateVillage : villages)
        {
            double fenceGateDistance = pigtificateVillage.getCenter().distanceSq(fenceGateBlock);

            if(fenceGateDistance < maxDistance)
            {
                float f = (float) (radius + pigtificateVillage.getVillageRadius());

                if(fenceGateDistance <= (double) (f * f))
                {
                    village = pigtificateVillage;
                    maxDistance = fenceGateDistance;
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

    private void updateVillages()
    {
        for(PigtificateVillageFenceGateInfo fenceGateInfo : newFenceGates)
        {
            PigtificateVillage village = getNearestVillage(fenceGateInfo.getFenceGatePos(), 32);

            if(village == null)
            {
                village = new PigtificateVillage(world);
                villages.add(village);
                markDirty();
            }

            village.addFenceGateInfo(fenceGateInfo);
        }

        newFenceGates.clear();
    }

    private void addFenceGatesAround(BlockPos central)
    {
        for(int x = -16; x < 16; x++)
        {
            for(int y = -4; y < 4; y++)
            {
                for(int z = -16; z < 16; z++)
                {
                    BlockPos blockpos = central.add(x, y, z);
                    EnumFacing outside = getOutside(blockpos);

                    if(outside != null)
                    {
                        PigtificateVillageFenceGateInfo fenceGateInfo = checkFenceGateExistence(blockpos);

                        if(fenceGateInfo == null)
                        {
                            addNewFenceGateInfo(blockpos, outside);
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
            if(fenceGateInfo.getFenceGatePos().getX() == fenceGateBlock.getX() && fenceGateInfo.getFenceGatePos().getZ() == fenceGateBlock.getZ() && Math.abs(fenceGateInfo.getFenceGatePos().getY() - fenceGateBlock.getY()) <= 1)
            {
                return fenceGateInfo;
            }
        }

        for(PigtificateVillage village : villages)
        {
            PigtificateVillageFenceGateInfo fenceGateInfo = village.getExistingFenceGate(fenceGateBlock);

            if(fenceGateInfo != null)
            {
                return fenceGateInfo;
            }
        }

        return null;
    }

    private void addNewFenceGateInfo(BlockPos fenceGateBlock, EnumFacing facing)
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

        if(fenceGateState.getBlock() instanceof BlockFenceGate)
        {
            return fenceGateState.getValue(BlockFenceGate.FACING).getOpposite();
        }

        return null;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        tickCounter = compound.getInteger("Tick");
        NBTTagList list = compound.getTagList("PigtificateVillages", 10);

        for(int i = 0; i < list.tagCount(); i++)
        {
            NBTTagCompound data = list.getCompoundTagAt(i);
            PigtificateVillage village = new PigtificateVillage();
            village.readVillageDataFromNBT(data);
            villages.add(village);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("Tick", tickCounter);
        NBTTagList list = new NBTTagList();

        for(PigtificateVillage village : villages)
        {
            NBTTagCompound villageData = new NBTTagCompound();
            village.writeVillageDataToNBT(villageData);
            list.appendTag(villageData);
        }

        compound.setTag("PigtificateVillages", list);
        return compound;
    }

    public static String getFileName(World world)
    {
        return "pigtificate_villages" + world.provider.getDimensionType().getSuffix();
    }
}

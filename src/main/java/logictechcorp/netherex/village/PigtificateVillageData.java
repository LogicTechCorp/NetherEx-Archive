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
        super(getDataId(world));
        this.markDirty();
    }

    public void addPigtificate(BlockPos pos)
    {
        if(this.pigtificatePositions.size() <= 64)
        {
            if(!this.positionInList(pos))
            {
                this.pigtificatePositions.add(pos);
            }
        }
    }

    public void tick(World world)
    {
        this.tickCounter++;

        for(PigtificateVillage village : this.villages)
        {
            village.tick(world, this.tickCounter);
        }

        this.removeAnnihilatedVillages();
        this.dropOldestVillagerPosition(world);
        this.updateVillages();

        if(this.tickCounter % 400 == 0)
        {
            this.markDirty();
        }
    }

    private void removeAnnihilatedVillages()
    {
        Iterator<PigtificateVillage> iter = this.villages.iterator();

        while(iter.hasNext())
        {
            PigtificateVillage village = iter.next();

            if(village.isAnnihilated())
            {
                iter.remove();
                this.markDirty();
            }
        }
    }

    public List<PigtificateVillage> getVillages()
    {
        return this.villages;
    }

    public PigtificateVillage getNearestVillage(BlockPos fenceGateBlock, int radius)
    {
        PigtificateVillage village = null;
        double maxDistance = 3.4028234663852886E38D;

        for(PigtificateVillage pigtificateVillage : this.villages)
        {
            double fenceGateDistance = pigtificateVillage.getCenter().distanceSq(fenceGateBlock);

            if(fenceGateDistance < maxDistance)
            {
                float f = (float) (radius + pigtificateVillage.getRadius());

                if(fenceGateDistance <= (double) (f * f))
                {
                    village = pigtificateVillage;
                    maxDistance = fenceGateDistance;
                }
            }
        }

        return village;
    }

    private void dropOldestVillagerPosition(World world)
    {
        if(!this.pigtificatePositions.isEmpty())
        {
            this.addFenceGatesAround(world, this.pigtificatePositions.remove(0));
        }
    }

    private void updateVillages()
    {
        for(PigtificateVillageFenceGateInfo fenceGateInfo : this.newFenceGates)
        {
            PigtificateVillage village = this.getNearestVillage(fenceGateInfo.getPos(), 32);

            if(village == null)
            {
                village = new PigtificateVillage();
                this.villages.add(village);
                this.markDirty();
            }

            village.addFenceGateInfo(fenceGateInfo);
        }

        this.newFenceGates.clear();
    }

    private void addFenceGatesAround(World world, BlockPos central)
    {
        for(int x = -16; x < 16; x++)
        {
            for(int y = -4; y < 4; y++)
            {
                for(int z = -16; z < 16; z++)
                {
                    BlockPos blockpos = central.add(x, y, z);
                    EnumFacing inside = this.getInside(world, blockpos);

                    if(inside != null)
                    {
                        PigtificateVillageFenceGateInfo fenceGateInfo = this.checkFenceGateExistence(blockpos);

                        if(fenceGateInfo == null)
                        {
                            this.addNewFenceGateInfo(blockpos, inside);
                        }
                        else
                        {
                            fenceGateInfo.setLastActivityTime(this.tickCounter);
                        }
                    }
                }
            }
        }
    }

    private PigtificateVillageFenceGateInfo checkFenceGateExistence(BlockPos fenceGateBlock)
    {
        for(PigtificateVillageFenceGateInfo fenceGateInfo : this.newFenceGates)
        {
            if(fenceGateInfo.getPos().getX() == fenceGateBlock.getX() && fenceGateInfo.getPos().getZ() == fenceGateBlock.getZ() && Math.abs(fenceGateInfo.getPos().getY() - fenceGateBlock.getY()) <= 1)
            {
                return fenceGateInfo;
            }
        }

        for(PigtificateVillage village : this.villages)
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
        this.newFenceGates.add(new PigtificateVillageFenceGateInfo(fenceGateBlock, facing, this.tickCounter));
    }

    private boolean positionInList(BlockPos pos)
    {
        for(BlockPos blockPos : this.pigtificatePositions)
        {
            if(blockPos.equals(pos))
            {
                return true;
            }
        }

        return false;
    }

    private EnumFacing getInside(World world, BlockPos fenceGatePos)
    {
        IBlockState fenceGateState = world.getBlockState(fenceGatePos);

        if(fenceGateState.getBlock() instanceof BlockFenceGate)
        {
            return fenceGateState.getValue(BlockFenceGate.FACING);
        }

        return null;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        this.tickCounter = compound.getInteger("Tick");
        NBTTagList list = compound.getTagList("PigtificateVillages", 10);

        for(int i = 0; i < list.tagCount(); i++)
        {
            NBTTagCompound data = list.getCompoundTagAt(i);
            PigtificateVillage village = new PigtificateVillage();
            village.readVillageDataFromNBT(data);
            this.villages.add(village);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("Tick", this.tickCounter);
        NBTTagList list = new NBTTagList();

        for(PigtificateVillage village : this.villages)
        {
            NBTTagCompound villageData = new NBTTagCompound();
            village.writeVillageDataToNBT(villageData);
            list.appendTag(villageData);
        }

        compound.setTag("PigtificateVillages", list);
        return compound;
    }

    public static String getDataId(World world)
    {
        return "nex_pigtificate_villages" + world.provider.getDimensionType().getSuffix();
    }
}

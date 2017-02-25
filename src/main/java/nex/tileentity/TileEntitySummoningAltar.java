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

package nex.tileentity;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import nex.entity.boss.EntityGhastQueen;
import nex.init.NetherExSoundEvents;

@SuppressWarnings("ConstantConditions")
public class TileEntitySummoningAltar extends TileEntityInventory implements ITickable
{
    private int summoningTime = 0;
    private boolean canBreak = true;

    public TileEntitySummoningAltar()
    {
        super(1);
    }

    @Override
    public void update()
    {
        if(getBlockMetadata() == 1)
        {
            ItemStack stack = getInventory().getStackInSlot(0);

            if(stack.getItem() == Items.GHAST_TEAR)
            {
                if(getSummoningTime() == 0)
                {
                    world.playSound(null, pos, NetherExSoundEvents.ENTITY_SUMMON_GHAST_QUEEN, SoundCategory.AMBIENT, 0.5F, 1.0F);
                }
                setSummoningTime(getSummoningTime() + 1);
            }
            else
            {
                setSummoningTime(0);
            }
            if(getSummoningTime() / 20.0F >= 6.8F)
            {
                getInventory().setStackInSlot(0, ItemStack.EMPTY);
                
                EntityGhastQueen ghastQueen = new EntityGhastQueen(getWorld());
                ghastQueen.setPosition(pos.getX(), pos.getY() + 10, pos.getZ());

                if(!getWorld().isRemote)
                {
                    getWorld().spawnEntity(ghastQueen);
                }
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("SummoningTime", getSummoningTime());
        compound.setBoolean("CanBreak", canBreak);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        setSummoningTime(compound.getInteger("SummoningTime"));
        setCanBreak(compound.getBoolean("CanBreak"));
    }

    public int getSummoningTime()
    {
        return summoningTime;
    }

    public boolean canBreak()
    {
        return canBreak;
    }

    private void setSummoningTime(int summoningTimeIn)
    {
        summoningTime = summoningTimeIn;
    }

    private void setCanBreak(boolean canBreakIn)
    {
        canBreak = canBreakIn;
    }
}

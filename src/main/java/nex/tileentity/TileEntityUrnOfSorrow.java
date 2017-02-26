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
import nex.block.BlockUrnOfSorrow;
import nex.entity.boss.EntityGhastQueen;
import nex.init.NetherExBlocks;
import nex.init.NetherExSoundEvents;

@SuppressWarnings("ConstantConditions")
public class TileEntityUrnOfSorrow extends TileEntityInventory implements ITickable
{
    private int summoningTime = 0;
    private boolean canBreak = true;

    public TileEntityUrnOfSorrow()
    {
        super(1);
    }

    @Override
    public void update()
    {
        if(BlockUrnOfSorrow.EnumType.fromMeta(getBlockMetadata()) == BlockUrnOfSorrow.EnumType.FULL)
        {
            ItemStack stack = getInventory().getStackInSlot(0);

            if(stack.getItem() == Items.GHAST_TEAR)
            {
                if(getSummoningTime() == 0)
                {
                    getWorld().playSound(null, getPos(), NetherExSoundEvents.ENTITY_SUMMON_GHAST_QUEEN, SoundCategory.AMBIENT, 0.5F, 1.0F);
                }
                setSummoningTime(getSummoningTime() + 1);
                setCanBreak(false);
            }
            if(getSummoningTime() / 20.0F >= 6.8F)
            {
                EntityGhastQueen ghastQueen = new EntityGhastQueen(getWorld());
                ghastQueen.setPosition(getPos().getX(), getPos().getY() + 7, getPos().getZ());
                ghastQueen.setUrnPos(getPos());

                if(!getWorld().isRemote)
                {
                    getWorld().spawnEntity(ghastQueen);
                }

                getInventory().setStackInSlot(0, ItemStack.EMPTY);
                getWorld().setBlockState(pos, NetherExBlocks.TILE_URN_SORROW.getDefaultState().withProperty(BlockUrnOfSorrow.TYPE, BlockUrnOfSorrow.EnumType.EMPTY));
                setSummoningTime(0);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("SummoningTime", getSummoningTime());
        compound.setBoolean("CanBreak", canBreak());
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

    public void setCanBreak(boolean canBreakIn)
    {
        canBreak = canBreakIn;
    }
}

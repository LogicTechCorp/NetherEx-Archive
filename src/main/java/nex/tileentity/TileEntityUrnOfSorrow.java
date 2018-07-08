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

package nex.tileentity;

import lex.tileentity.TileEntityInventory;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
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
                if(summoningTime == 0)
                {
                    world.playSound(null, pos, NetherExSoundEvents.GHAST_QUEEN_SUMMON, SoundCategory.AMBIENT, 0.5F, 1.0F);
                }

                summoningTime += 1;
                canBreak = false;
            }
            if(summoningTime / 20.0F >= 6.8F)
            {
                EntityGhastQueen ghastQueen = new EntityGhastQueen(getWorld());
                ghastQueen.setPosition(pos.getX(), pos.getY() + 7, pos.getZ());
                ghastQueen.setUrnPos(pos);

                world.playSound(null, pos.getX(), pos.getY() + 7, pos.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 4.0F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);

                if(!world.isRemote)
                {
                    world.spawnEntity(ghastQueen);
                }

                getInventory().setStackInSlot(0, ItemStack.EMPTY);
                world.setBlockState(pos, NetherExBlocks.URN_OF_SORROW.getDefaultState().withProperty(BlockUrnOfSorrow.TYPE, BlockUrnOfSorrow.EnumType.EMPTY));
                summoningTime = 0;
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("SummoningTime", summoningTime);
        compound.setBoolean("CanBreak", canBreak);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        summoningTime = compound.getInteger("SummoningTime");
        canBreak = compound.getBoolean("CanBreak");
    }

    public int getSummoningTime()
    {
        return summoningTime;
    }

    public boolean canBreak()
    {
        return canBreak;
    }
}

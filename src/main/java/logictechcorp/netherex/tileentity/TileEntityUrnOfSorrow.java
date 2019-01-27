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

package logictechcorp.netherex.tileentity;

import logictechcorp.libraryex.tileentity.TileEntityInventory;
import logictechcorp.netherex.block.BlockUrnOfSorrow;
import logictechcorp.netherex.entity.boss.EntityGhastQueen;
import logictechcorp.netherex.init.NetherExBlocks;
import logictechcorp.netherex.init.NetherExSoundEvents;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;

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
        if(BlockUrnOfSorrow.EnumType.fromMeta(this.getBlockMetadata()) == BlockUrnOfSorrow.EnumType.FULL)
        {
            ItemStack stack = this.getInventory().getStackInSlot(0);

            if(stack.getItem() == Items.GHAST_TEAR)
            {
                if(this.summoningTime == 0)
                {
                    this.world.playSound(null, this.pos, NetherExSoundEvents.GHAST_QUEEN_SUMMON, SoundCategory.AMBIENT, 0.5F, 1.0F);
                }

                this.summoningTime += 1;
                this.canBreak = false;
            }
            if(this.summoningTime / 20.0F >= 6.8F)
            {
                EntityGhastQueen ghastQueen = new EntityGhastQueen(this.getWorld());
                ghastQueen.setPosition(this.pos.getX(), this.pos.getY() + 7, this.pos.getZ());
                ghastQueen.setUrnPos(this.pos);

                this.world.playSound(null, this.pos.getX(), this.pos.getY() + 7, this.pos.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);

                if(!this.world.isRemote)
                {
                    this.world.spawnEntity(ghastQueen);
                }

                this.getInventory().setStackInSlot(0, ItemStack.EMPTY);
                this.world.setBlockState(this.pos, NetherExBlocks.URN_OF_SORROW.getDefaultState().withProperty(BlockUrnOfSorrow.TYPE, BlockUrnOfSorrow.EnumType.EMPTY));
                this.summoningTime = 0;
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("SummoningTime", this.summoningTime);
        compound.setBoolean("CanBreak", this.canBreak);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.summoningTime = compound.getInteger("SummoningTime");
        this.canBreak = compound.getBoolean("CanBreak");
    }

    @Override
    public boolean acceptsItemStack(ItemStack stack)
    {
        return stack.getItem() == Items.GHAST_TEAR;
    }

    public int getSummoningTime()
    {
        return this.summoningTime;
    }

    public boolean canBreak()
    {
        return this.canBreak;
    }
}

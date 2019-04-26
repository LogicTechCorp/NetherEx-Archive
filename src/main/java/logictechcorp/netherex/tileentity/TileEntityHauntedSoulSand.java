/*
 * LibraryEx
 * Copyright (c) 2017-2019 by LogicTechCorp
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

import logictechcorp.libraryex.utility.ExperienceHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityHauntedSoulSand extends TileEntity
{
    private final int maxExperience = ExperienceHelper.getExperienceForLevel(30);
    private int experience = 0;

    public void drainExperience(EntityPlayer player)
    {
        if(player.capabilities.isCreativeMode)
        {
            return;
        }

        if(this.experience < this.maxExperience)
        {
            int playerExperience = ExperienceHelper.getPlayerExperience(player);

            if(playerExperience <= 0)
            {
                return;
            }

            int drainAmount = Math.min(5, playerExperience);

            if(this.experience + drainAmount > this.maxExperience)
            {
                drainAmount = this.maxExperience - this.experience;
            }

            ExperienceHelper.adjustPlayerExperience(player, -drainAmount);
            this.experience += drainAmount;
        }
    }

    public void expelExperience()
    {
        if(!this.world.isRemote)
        {
            while(this.experience > 0)
            {
                int experienceFragment = EntityXPOrb.getXPSplit(this.experience);
                this.experience -= experienceFragment;
                this.world.spawnEntity(new EntityXPOrb(this.world, (double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D, experienceFragment));
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.experience = compound.getInteger("Experience");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("Experience", this.experience);
        return compound;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
        return oldState.getBlock() != newSate.getBlock();
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
    {
        this.readFromNBT(packet.getNbtCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 0, this.writeToNBT(new NBTTagCompound()));
    }

    public int getExperience()
    {
        return this.experience;
    }
}

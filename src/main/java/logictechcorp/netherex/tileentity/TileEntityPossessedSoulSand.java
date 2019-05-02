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
import logictechcorp.netherex.block.BlockPossessedSoulSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityPossessedSoulSand extends TileEntity
{
    private final int maxExperience;
    private int experience = 0;

    public TileEntityPossessedSoulSand()
    {
        this.maxExperience = ExperienceHelper.getExperienceForLevel(30);
    }

    public void consumeExperience(EntityPlayer player)
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

        if(this.experience <= this.maxExperience)
        {
            this.increaseEyeGlow();
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

    private void increaseEyeGlow()
    {
        IBlockState state = this.world.getBlockState(this.pos);
        int experienceLevel = ExperienceHelper.getLevelForExperience(this.experience) / 2;

        if(state.getBlock() == this.getBlockType())
        {
            if((experienceLevel == 2 || experienceLevel == 3) && state.getValue(BlockPossessedSoulSand.STAGE) < 1)
            {
                this.world.setBlockState(this.pos, state.withProperty(BlockPossessedSoulSand.STAGE, 1));
            }
            else if((experienceLevel == 4 || experienceLevel == 5) && state.getValue(BlockPossessedSoulSand.STAGE) < 2)
            {
                this.world.setBlockState(this.pos, state.withProperty(BlockPossessedSoulSand.STAGE, 2));
            }
            else if((experienceLevel == 6 || experienceLevel == 7) && state.getValue(BlockPossessedSoulSand.STAGE) < 3)
            {
                this.world.setBlockState(this.pos, state.withProperty(BlockPossessedSoulSand.STAGE, 3));
            }
            else if((experienceLevel == 8 || experienceLevel == 9) && state.getValue(BlockPossessedSoulSand.STAGE) < 4)
            {
                this.world.setBlockState(this.pos, state.withProperty(BlockPossessedSoulSand.STAGE, 4));
            }
            else if((experienceLevel == 10 || experienceLevel == 11) && state.getValue(BlockPossessedSoulSand.STAGE) < 5)
            {
                this.world.setBlockState(this.pos, state.withProperty(BlockPossessedSoulSand.STAGE, 5));
            }
            else if((experienceLevel == 12 || experienceLevel == 13) && state.getValue(BlockPossessedSoulSand.STAGE) < 6)
            {
                this.world.setBlockState(this.pos, state.withProperty(BlockPossessedSoulSand.STAGE, 6));
            }
            else if((experienceLevel == 14 || experienceLevel == 15) && state.getValue(BlockPossessedSoulSand.STAGE) < 7)
            {
                this.world.setBlockState(this.pos, state.withProperty(BlockPossessedSoulSand.STAGE, 7));
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

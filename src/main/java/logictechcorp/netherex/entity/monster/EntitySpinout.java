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

package logictechcorp.netherex.entity.monster;

import logictechcorp.netherex.NetherExConfig;
import logictechcorp.netherex.init.NetherExLootTables;
import logictechcorp.netherex.init.NetherExSoundEvents;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntitySpinout extends EntityMob
{
    private int spinCounter;
    private int spinCooldown;

    private boolean spinning;

    private final EntityAIBase attackMelee = new EntityAIAttackMelee(this, 1.0D, true);
    private final EntityAIBase wander = new EntityAIWander(this, 1.0D, 1);

    public EntitySpinout(World world)
    {
        super(world);
        this.isImmuneToFire = true;
        this.setSize(0.55F, 1.95F);
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return NetherExSoundEvents.SPINOUT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return NetherExSoundEvents.SPINOUT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return NetherExSoundEvents.SPINOUT_DEATH;
    }

    @Override
    public void playSound(SoundEvent sound, float volume, float pitch)
    {
        if(!this.isSilent() || this.isSilent() && sound != NetherExSoundEvents.SPINOUT_AMBIENT)
        {
            this.world.playSound(null, this.posX, this.posY, this.posZ, sound, this.getSoundCategory(), volume, pitch);
        }
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(this.spinCooldown == 0)
        {
            this.spinCounter++;

            if(!this.spinning)
            {
                this.spinning = true;
            }
            if(this.tasks.taskEntries.size() == 1)
            {
                this.tasks.addTask(1, this.attackMelee);
                this.tasks.addTask(2, this.wander);
            }
            if(this.isSilent())
            {
                this.setSilent(false);
            }
        }
        if(this.spinCounter >= NetherExConfig.entity.spinout.spinTime * 20)
        {
            this.spinCounter = 0;
            this.spinning = false;
            this.spinCooldown = NetherExConfig.entity.spinout.spinCooldown * 20;
        }
        if(this.spinCooldown > 0)
        {
            this.spinCooldown--;

            if(this.tasks.taskEntries.size() == 3)
            {
                this.tasks.removeTask(this.attackMelee);
                this.tasks.removeTask(this.wander);
            }
            if(!this.isSilent())
            {
                this.setSilent(true);
            }

            this.getNavigator().clearPath();
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("SpinCounter", this.spinCounter);
        compound.setInteger("SpinCooldown", this.spinCooldown);
        compound.setBoolean("Spinning", this.spinning);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.spinCounter = compound.getInteger("SpinCounter");
        this.spinCooldown = compound.getInteger("SpinCooldown");
        this.spinning = compound.getBoolean("Spinning");
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return !(source.isProjectile() && this.isSpinning()) && super.attackEntityFrom(source, amount);
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return NetherExLootTables.SPINOUT;
    }

    public boolean isSpinning()
    {
        return this.spinning;
    }
}

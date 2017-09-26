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

package nex.entity.monster;

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
import nex.handler.ConfigHandler;
import nex.init.NetherExLootTables;
import nex.init.NetherExSoundEvents;

@SuppressWarnings("ConstantConditions")
public class EntitySpinout extends EntityMob
{
    private int counter;
    private int cooldown;

    private boolean spinning;

    private final EntityAIBase attackMelee = new EntityAIAttackMelee(this, 1.0D, true);
    private final EntityAIBase wander = new EntityAIWander(this, 1.0D, 1);

    public EntitySpinout(World world)
    {
        super(world);

        setSize(0.55F, 1.95F);
        isImmuneToFire = true;
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return NetherExSoundEvents.ENTITY_AMBIENT_SPINOUT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return NetherExSoundEvents.ENTITY_HURT_SPINOUT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return NetherExSoundEvents.ENTITY_DEATH_SPINOUT;
    }

    @Override
    public void playSound(SoundEvent sound, float volume, float pitch)
    {
        if(!isSilent() || isSilent() && sound != NetherExSoundEvents.ENTITY_AMBIENT_SPINOUT)
        {
            world.playSound(null, posX, posY, posZ, sound, getSoundCategory(), volume, pitch);
        }
    }

    @Override
    protected void initEntityAI()
    {
        tasks.addTask(0, new EntityAISwimming(this));
        targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(getCooldown() == 0)
        {
            setCounter(getCounter() + 1);

            if(!isSpinning())
            {
                setSpinning(true);
            }
            if(tasks.taskEntries.size() == 1)
            {
                tasks.addTask(1, attackMelee);
                tasks.addTask(2, wander);
            }
            if(isSilent())
            {
                setSilent(false);
            }
        }
        if(getCounter() >= ConfigHandler.entity.spinout.spinTime * 20)
        {
            setCounter(0);
            setSpinning(false);
            setCooldown(ConfigHandler.entity.spinout.spinCooldown * 20);
        }
        if(getCooldown() > 0)
        {
            setCooldown(getCooldown() - 1);

            if(tasks.taskEntries.size() == 3)
            {
                tasks.removeTask(attackMelee);
                tasks.removeTask(wander);
            }
            if(!isSilent())
            {
                setSilent(true);
            }

            getNavigator().clearPath();
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("SpinCounter", getCounter());
        compound.setInteger("SpinCooldown", getCooldown());
        compound.setBoolean("Spinning", isSpinning());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        setCounter(compound.getInteger("SpinCounter"));
        setCooldown(compound.getInteger("SpinCooldown"));
        setSpinning(compound.getBoolean("Spinning"));
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return !(source.isProjectile() && isSpinning()) && super.attackEntityFrom(source, amount);
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return NetherExLootTables.ENTITY_SPINOUT;
    }

    public int getCounter()
    {
        return counter;
    }

    private int getCooldown()
    {
        return cooldown;
    }

    public boolean isSpinning()
    {
        return spinning;
    }

    private void setCounter(int i)
    {
        counter = i;
    }

    private void setCooldown(int i)
    {
        cooldown = i;
    }

    private void setSpinning(boolean bool)
    {
        spinning = bool;
    }
}

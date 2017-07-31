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

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nex.handler.ConfigHandler;
import nex.init.NetherExLootTables;

public class EntityBrute extends EntityMob
{
    private int cooldown;

    private boolean charging;
    private boolean addedOffset;

    private BlockPos destination;

    public EntityBrute(World world)
    {
        super(world);

        setSize(1.25F, 2.25F);
        isImmuneToFire = true;
    }

    @Override
    protected void initEntityAI()
    {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 64.0F));
        tasks.addTask(2, new EntityAIWander(this, 1.0D, 360));
        targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(32.0D);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(16.0D);
        getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.5D);
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(isCharging() && (getAttackTarget() == null || (prevPosX == posX && prevPosY == posY && prevPosZ == posZ)))
        {
            setCharging(false);
        }

        if(getCooldown() == 0)
        {
            if(getAttackTarget() != null && !isCharging())
            {
                setDestination(getAttackTarget().getPosition());
                setCharging(true);
            }
            if(isCharging())
            {
                getNavigator().tryMoveToXYZ(getDestination().getX(), getDestination().getY(), getDestination().getZ(), 2.5F);

                if(!addedOffset() && getDistanceSq(getDestination()) <= 4.0D)
                {
                    setDestination(getDestination().offset(getHorizontalFacing(), 4));
                    setAddedOffset(true);
                }
                if(getPosition().equals(getDestination()))
                {
                    setAddedOffset(false);
                    setCharging(false);
                    setCooldown(ConfigHandler.entity.brute.chargeCooldown * 20);
                }
            }
        }
        if(getCooldown() > 0)
        {
            setCooldown(getCooldown() - 1);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("ChargeCooldown", getCooldown());
        compound.setBoolean("Charging", isCharging());
        compound.setBoolean("AddedOffset", addedOffset());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        setCooldown(compound.getInteger("ChargeCooldown"));
        setCharging(compound.getBoolean("Charging"));
        setAddedOffset(compound.getBoolean("AddedOffset"));
    }

    @Override
    protected void collideWithEntity(Entity entity)
    {
        super.collideWithEntity(entity);

        if(isCharging() && !(entity instanceof EntityBrute) && entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue() + rand.nextInt((int) getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue() / 2)))
        {
            entity.motionY += 0.45D;
        }
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return NetherExLootTables.ENTITY_BRUTE;
    }

    private int getCooldown()
    {
        return cooldown;
    }

    private boolean isCharging()
    {
        return charging;
    }

    private boolean addedOffset()
    {
        return addedOffset;
    }

    private BlockPos getDestination()
    {
        return destination;
    }

    private void setCooldown(int i)
    {
        cooldown = i;
    }

    private void setCharging(boolean bool)
    {
        charging = bool;
    }

    private void setAddedOffset(boolean bool)
    {
        addedOffset = bool;
    }

    private void setDestination(BlockPos pos)
    {
        destination = pos;
    }
}

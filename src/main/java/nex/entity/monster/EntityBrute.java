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
    private int chargeCooldown;

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

        if(charging && (getAttackTarget() == null || (prevPosX == posX && prevPosY == posY && prevPosZ == posZ)))
        {
            charging = false;
        }

        if(chargeCooldown == 0)
        {
            if(getAttackTarget() != null && !charging)
            {
                destination = getAttackTarget().getPosition();
                charging = true;
            }
            if(charging)
            {
                getNavigator().tryMoveToXYZ(destination.getX(), destination.getY(), destination.getZ(), 2.5F);

                if(!addedOffset && getDistanceSq(destination) <= 4.0D)
                {
                    destination = destination.offset(getHorizontalFacing(), 4);
                    addedOffset = true;
                }
                if(getPosition().equals(destination))
                {
                    addedOffset = false;
                    charging = false;
                    chargeCooldown = ConfigHandler.entityConfig.brute.chargeCooldown * 20;
                }
            }
        }
        if(chargeCooldown > 0)
        {
            chargeCooldown--;
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("ChargeCooldown", chargeCooldown);
        compound.setBoolean("Charging", charging);
        compound.setBoolean("AddedOffset", addedOffset);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        chargeCooldown = compound.getInteger("ChargeCooldown");
        charging = compound.getBoolean("Charging");
        addedOffset = compound.getBoolean("AddedOffset");
    }

    @Override
    protected void collideWithEntity(Entity entity)
    {
        super.collideWithEntity(entity);

        if(charging && !(entity instanceof EntityBrute) && entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue() + rand.nextInt((int) getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue() / 2)))
        {
            entity.motionY += 0.45D;
        }
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return NetherExLootTables.BRUTE;
    }
}

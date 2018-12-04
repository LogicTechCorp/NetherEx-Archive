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

package logictechcorp.netherex.entity.monster;

import logictechcorp.netherex.handler.ConfigHandler;
import logictechcorp.netherex.init.NetherExLootTables;
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

public class EntityBrute extends EntityMob
{
    private int chargeCooldown;

    private boolean charging;
    private boolean addedOffset;

    private BlockPos destination;

    public EntityBrute(World world)
    {
        super(world);
        this.isImmuneToFire = true;
        this.setSize(1.25F, 2.25F);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 64.0F));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0D, 360));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.5D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(this.charging && (this.getAttackTarget() == null || (this.prevPosX == this.posX && this.prevPosY == this.posY && this.prevPosZ == this.posZ)))
        {
            this.charging = false;
        }

        if(this.chargeCooldown == 0)
        {
            if(this.getAttackTarget() != null && !this.charging)
            {
                this.destination = this.getAttackTarget().getPosition();
                this.charging = true;
            }
            if(this.charging)
            {
                this.getNavigator().tryMoveToXYZ(this.destination.getX(), this.destination.getY(), this.destination.getZ(), 2.5F);

                if(!this.addedOffset && this.getDistanceSq(this.destination) <= 4.0D)
                {
                    this.destination = this.destination.offset(this.getHorizontalFacing(), 4);
                    this.addedOffset = true;
                }
                if(this.getPosition().equals(this.destination))
                {
                    this.addedOffset = false;
                    this.charging = false;
                    this.chargeCooldown = ConfigHandler.entityConfig.brute.chargeCooldown * 20;
                }
            }
        }
        if(this.chargeCooldown > 0)
        {
            this.chargeCooldown--;
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("ChargeCooldown", this.chargeCooldown);
        compound.setBoolean("Charging", this.charging);
        compound.setBoolean("AddedOffset", this.addedOffset);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.chargeCooldown = compound.getInteger("ChargeCooldown");
        this.charging = compound.getBoolean("Charging");
        this.addedOffset = compound.getBoolean("AddedOffset");
    }

    @Override
    protected void collideWithEntity(Entity entity)
    {
        super.collideWithEntity(entity);

        if(this.charging && !(entity instanceof EntityBrute) && entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue() + this.rand.nextInt((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue() / 2)))
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

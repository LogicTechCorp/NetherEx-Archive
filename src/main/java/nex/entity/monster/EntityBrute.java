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
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nex.handler.ConfigHandler;
import nex.init.NetherExLootTables;

public class EntityBrute extends EntityMob
{
    private static final DataParameter<Integer> COOLDOWN = EntityDataManager.createKey(EntityBrute.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> CHARGING = EntityDataManager.createKey(EntityBrute.class, DataSerializers.BOOLEAN);
    private static final DataParameter<BlockPos> POS_TO_CHARGE = EntityDataManager.createKey(EntityBrute.class, DataSerializers.BLOCK_POS);

    public EntityBrute(World world)
    {
        super(world);

        setSize(1.25F, 2.25F);
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
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(32.0D);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(42.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.5D);
        getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.5D);
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataManager.register(COOLDOWN, 0);
        dataManager.register(CHARGING, false);
        dataManager.register(POS_TO_CHARGE, getPosition());
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if(getAttackTarget() == null)
        {
            setCharging(false);
        }

        if(getCooldown() == 0)
        {
            if(getAttackTarget() != null && !isCharging())
            {
                setPosToCharge(getAttackTarget().getPosition());
                setCharging(true);
            }
            if(isCharging())
            {
                getNavigator().tryMoveToXYZ(getPosToCharge().getX(), getPosToCharge().getY(), getPosToCharge().getZ(), 2.5F);
            }
            if(getDistanceSq(getPosToCharge()) <= 0.75F)
            {
                setCharging(false);
                setCooldown(ConfigHandler.entity.brute.chargeCooldown * 20 / 4);
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
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        setCooldown(compound.getInteger("ChargeCooldown"));
        setCharging(compound.getBoolean("Charging"));
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
        return dataManager.get(COOLDOWN);
    }

    public boolean isCharging()
    {
        return dataManager.get(CHARGING);
    }

    private BlockPos getPosToCharge()
    {
        return dataManager.get(POS_TO_CHARGE);
    }

    private void setCooldown(int amount)
    {
        dataManager.set(COOLDOWN, amount);
    }

    private void setCharging(boolean charging)
    {
        dataManager.set(CHARGING, charging);
    }

    private void setPosToCharge(BlockPos posToCharge)
    {
        dataManager.set(POS_TO_CHARGE, posToCharge);
    }
}

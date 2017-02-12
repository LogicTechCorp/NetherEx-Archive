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

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import nex.handler.ConfigHandler;

public class EntitySpinout extends EntityMob
{
    public int spinCounter;
    public int spinCooldown;

    public EntitySpinout(World world)
    {
        super(world);

        setSize(0.55F, 1.95F);
        stepHeight = 0.5F;
        isImmuneToFire = true;
    }

    @Override
    protected void initEntityAI()
    {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, false));
        tasks.addTask(2, new EntityAIWander(this, 1.0D));
        tasks.addTask(2, new EntityAILookIdle(this));
        targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
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
    public void onEntityUpdate()
    {
        super.onEntityUpdate();

        if(spinCooldown == 0)
        {
            spinCounter++;
        }
        if(spinCounter >= ConfigHandler.Entity.Spin.spinTime * 20)
        {
            spinCounter = 0;
            spinCooldown = ConfigHandler.Entity.Spin.spinCooldown * 20;
        }
        if(spinCooldown > 0)
        {
            spinCooldown--;
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("SpinCounter", spinCounter);
        compound.setInteger("SpinCooldown", spinCooldown);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        spinCounter = compound.getInteger("SpinCounter");
        spinCooldown = compound.getInteger("SpinCooldown");
        super.readFromNBT(compound);
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
    protected Item getDropItem()
    {
        return Items.QUARTZ;
    }

    @Override
    public String getName()
    {
        if(hasCustomName())
        {
            return getCustomNameTag();
        }
        else
        {
            String entityName = EntityList.getEntityString(this);

            if(entityName == null)
            {
                entityName = "generic";
            }

            return I18n.format("entity." + entityName + ".name");
        }
    }

    public boolean isSpinning()
    {
        return spinCounter > 0;
    }
}

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

package nex.entity.neutral;

import com.google.common.collect.Lists;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import nex.init.NetherExItems;

public class EntitySalamander extends EntityMob
{
    private static final DataParameter<Integer> SALAMANDER_TYPE = EntityDataManager.createKey(EntitySalamander.class, DataSerializers.VARINT);

    public EntitySalamander(World world)
    {
        super(world);

        setSize(0.95F, 0.45F);
        stepHeight = 0.5F;
        isImmuneToFire = true;

        setRandomType();
    }

    @Override
    protected void initEntityAI()
    {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAILeapAtTarget(this, 0.3F));
        tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        tasks.addTask(3, new EntityAIWander(this, 1.0D));
        tasks.addTask(4, new EntityAILookIdle(this));
        targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataManager.register(SALAMANDER_TYPE, 0);
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

            String type = getType() == 0 ? "orange" : "black";

            return I18n.format("entity." + entityName + "." + type + ".name");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("Type", getType());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        setType(compound.getInteger("Type"));
        super.readFromNBT(compound);
    }

    @Override
    protected Item getDropItem()
    {
        return NetherExItems.ITEM_HIDE_SALAMANDER;
    }

    @Override
    public EntityItem dropItemWithOffset(Item item, int size, float offsetY)
    {
        return entityDropItem(new ItemStack(item, size, getType()), offsetY);
    }

    public int getType()
    {
        return dataManager.get(SALAMANDER_TYPE);
    }

    private void setRandomType()
    {
        WeightedRandom.Item orange = new WeightedRandom.Item(10);
        WeightedRandom.Item black = new WeightedRandom.Item(1);
        WeightedRandom.Item item = WeightedRandom.getRandomItem(rand, Lists.newArrayList(orange, black));
        dataManager.set(SALAMANDER_TYPE, item == orange ? 0 : 1);
    }

    public void setType(int id)
    {
        if(id < 0)
        {
            id = 0;
        }
        else if(id > 1)
        {
            id = 1;
        }

        dataManager.set(SALAMANDER_TYPE, id);
    }
}

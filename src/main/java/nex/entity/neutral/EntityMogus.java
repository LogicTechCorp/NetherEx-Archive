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
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import nex.init.NetherExItems;

public class EntityMogus extends EntityMob
{
    private static final DataParameter<Integer> MOGUS_TYPE = EntityDataManager.createKey(EntityMogus.class, DataSerializers.VARINT);

    public EntityMogus(World world)
    {
        super(world);

        setSize(0.35F, 0.45F);
        stepHeight = 0.5F;
        isImmuneToFire = true;

        setRandomType();
    }

    @Override
    protected void initEntityAI()
    {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIAttackMelee(this, 1.45D, true));
        tasks.addTask(1, new EntityAIPanic(this, 1.45D));
        tasks.addTask(2, new EntityAIWander(this, 1.0D));
        tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(4, new EntityAILookIdle(this));
        targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0D);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataManager.register(MOGUS_TYPE, 0);
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return world.getDifficulty() != EnumDifficulty.PEACEFUL;
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
        return getType() == 0 ? Item.getItemFromBlock(Blocks.BROWN_MUSHROOM) : getType() == 1 ? Item.getItemFromBlock(Blocks.RED_MUSHROOM) : NetherExItems.FOOD_MUSHROOM_ENOKI;
    }

    public int getType()
    {
        return dataManager.get(MOGUS_TYPE);
    }

    private void setRandomType()
    {
        WeightedRandom.Item brown = new WeightedRandom.Item(10);
        WeightedRandom.Item red = new WeightedRandom.Item(10);
        WeightedRandom.Item white = new WeightedRandom.Item(5);
        WeightedRandom.Item item = WeightedRandom.getRandomItem(rand, Lists.newArrayList(brown, red, white));
        dataManager.set(MOGUS_TYPE, item == brown ? 0 : item == red ? 1 : 2);
    }

    public void setType(int id)
    {
        if(id < 0)
        {
            id = 0;
        }
        else if(id > 2)
        {
            id = 2;
        }

        dataManager.set(MOGUS_TYPE, id);
    }
}

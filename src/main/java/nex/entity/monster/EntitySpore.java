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
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import nex.handler.ConfigHandler;

public class EntitySpore extends EntityMob
{
    private static final DataParameter<Integer> STAGE = EntityDataManager.createKey(EntitySpore.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> COUNTER = EntityDataManager.createKey(EntitySpore.class, DataSerializers.VARINT);

    public EntitySpore(World world)
    {
        super(world);

        setSize(0.65F, 0.75F);
        isImmuneToFire = true;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(32.0D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataManager.register(STAGE, 0);
        dataManager.register(COUNTER, 0);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        posX = prevPosX;
        posY = prevPosY;
        posZ = prevPosZ;

        if(getStage() == 2)
        {
            setSize(0.78F, 0.88F);
        }
        else if(getStage() == 3)
        {
            setSize(0.8F, 1.48F);
        }

        if(getStage() < 4)
        {
            setCounter(getCounter() + 1);

            if(getCounter() >= (ConfigHandler.Entity.Spore.growthTime / (getStage() + 1)) * 20)
            {
                setCounter(0);
                setStage(getStage() + 1);
            }
        }
        else
        {
            if(world.getEntitiesWithinAABB(EntityPlayer.class, getEntityBoundingBox().expand(-2, -2, -2)).size() > 0)
            {
                setDead();

                if(!world.isRemote)
                {
                    EntitySporeCreeper creeper = new EntitySporeCreeper(world);
                    creeper.setPosition(posX, posY, posZ);
                    world.spawnEntity(creeper);
                }
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Stage", getStage());
        compound.setInteger("Counter", getCounter());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        setStage(compound.getInteger("Stage"));
        setCounter(compound.getInteger("Counter"));
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

            String stage = getStage() == 0 ? "one" : getStage() == 1 ? "two" : getStage() == 2 ? "three" : getStage() == 1 ? "four" : "five";

            return I18n.format("entity." + entityName + "." + stage + ".name");
        }
    }

    public int getStage()
    {
        return dataManager.get(STAGE);
    }

    private int getCounter()
    {
        return dataManager.get(COUNTER);
    }

    private void setStage(int stage)
    {
        if(stage < 0)
        {
            stage = 0;
        }
        else if(stage > 4)
        {
            stage = 4;
        }

        dataManager.set(STAGE, stage);
    }

    private void setCounter(int amount)
    {
        dataManager.set(COUNTER, amount);
    }
}

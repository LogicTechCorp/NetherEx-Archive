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

import com.google.common.collect.Lists;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import nex.handler.ConfigHandler;
import nex.init.NetherExItems;

@SuppressWarnings("ConstantConditions")
public class EntitySpore extends EntityMob
{
    private static final DataParameter<Integer> STAGE = EntityDataManager.createKey(EntitySpore.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> COUNTER = EntityDataManager.createKey(EntitySpore.class, DataSerializers.VARINT);

    public EntitySpore(World world)
    {
        super(world);

        setRandomStage();
    }

    public EntitySpore(World world, int stage)
    {
        this(world);

        setStage(stage);
    }


    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0D);
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
            if(world.getEntitiesWithinAABB(EntityPlayer.class, getEntityBoundingBox().expand(2, 2, 2)).size() > 0)
            {
                setDead();

                if(!world.isRemote)
                {

                    int creeperSpawns = rand.nextInt(ConfigHandler.Entity.Spore.creeperSpawns) + 1;

                    for(int i = 0; i < creeperSpawns; i++)
                    {
                        EntitySporeCreeper creeper = new EntitySporeCreeper(world);
                        creeper.setPosition(posX, posY, posZ);
                        world.spawnEntity(creeper);
                    }
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
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if(source.getEntity() != null && source.getEntity() instanceof EntityLivingBase)
        {
            if(((EntityLivingBase) source.getEntity()).getHeldItemMainhand().getItem() == NetherExItems.TOOL_SWORD_BONE)
            {
                amount *= 2.0F;
            }
        }
        return super.attackEntityFrom(source, amount);
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

    private void setRandomStage()
    {
        WeightedRandom.Item one = new WeightedRandom.Item(10);
        WeightedRandom.Item two = new WeightedRandom.Item(8);
        WeightedRandom.Item three = new WeightedRandom.Item(6);
        WeightedRandom.Item four = new WeightedRandom.Item(4);
        WeightedRandom.Item five = new WeightedRandom.Item(2);
        WeightedRandom.Item stage = WeightedRandom.getRandomItem(rand, Lists.newArrayList(one, two, three, four, five));
        setStage(stage == one ? 0 : stage == two ? 1 : stage == three ? 2 : stage == four ? 3 : 4);
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
        updateSize();
        updateHealth();
    }

    private void setCounter(int amount)
    {
        dataManager.set(COUNTER, amount);
    }

    private void updateSize()
    {
        if(getStage() == 0 || getStage() == 1 && width != 0.65F)
        {
            setSize(0.65F, 0.75F);
        }
        else if(getStage() == 2 && width != 0785F)
        {
            setSize(0.78F, 0.88F);
        }
        else if(getStage() == 3 || getStage() == 4 && width != 0.8F)
        {
            setSize(0.8F, 1.48F);
        }
    }

    private void updateHealth()
    {
        if(getStage() == 0 && getMaxHealth() != 2.0F)
        {
            getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0D);
        }
        else if(getStage() == 1 && getMaxHealth() != 4.0F)
        {
            getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
            setHealth(getHealth() * 2.0F);
        }
        else if(getStage() == 2 && getMaxHealth() != 8.0F)
        {
            getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
            setHealth(getHealth() * 2.0F);
        }
        else if(getStage() == 3 && getMaxHealth() != 16.0F)
        {
            getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
            setHealth(getHealth() * 2.0F);
        }
        else if(getStage() == 4 && getMaxHealth() != 32.0F)
        {
            getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(32.0D);
            setHealth(getHealth() * 2.0F);
        }
    }
}

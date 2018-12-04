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

import com.google.common.collect.Lists;
import logictechcorp.netherex.handler.ConfigHandler;
import logictechcorp.netherex.init.NetherExItems;
import logictechcorp.netherex.init.NetherExLootTables;
import logictechcorp.netherex.init.NetherExSoundEvents;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

public class EntitySpore extends EntityMob
{
    private static final DataParameter<Integer> STAGE = EntityDataManager.createKey(EntitySpore.class, DataSerializers.VARINT);

    private int counter;

    public EntitySpore(World world)
    {
        super(world);
        this.isImmuneToFire = true;
        this.setRandomStage();
    }

    public EntitySpore(World world, int stage)
    {
        this(world);
        this.setStage(stage);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return NetherExSoundEvents.SPORE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return NetherExSoundEvents.SPORE_DEATH;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(STAGE, 0);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        this.updateSize();

        if(this.getStage() < 4)
        {
            this.setCounter(this.getCounter() + 1);

            if(this.getCounter() >= (ConfigHandler.entityConfig.spore.growthTime / (this.getStage() + 1)) * 20)
            {
                this.setCounter(0);
                this.setStage(this.getStage() + 1);
            }
        }
        else
        {
            if(this.world.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().expand(2, 2, 2)).size() > 0)
            {
                this.setDead();

                if(!this.world.isRemote)
                {

                    int creeperSpawns = this.rand.nextInt(ConfigHandler.entityConfig.spore.creeperSpawnAmount) + 1;

                    for(int i = 0; i < creeperSpawns; i++)
                    {
                        EntitySporeCreeper creeper = new EntitySporeCreeper(this.world);
                        creeper.setPosition(this.posX, this.posY, this.posZ);
                        this.world.spawnEntity(creeper);
                    }
                }
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("Counter", this.getCounter());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.setCounter(compound.getInteger("Counter"));
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if(source.getTrueSource() != null && source.getTrueSource() instanceof EntityLivingBase)
        {
            if(((EntityLivingBase) source.getTrueSource()).getHeldItemMainhand().getItem() == NetherExItems.AMEDIAN_SWORD)
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
    protected ResourceLocation getLootTable()
    {
        return NetherExLootTables.SPORE;
    }

    public int getStage()
    {
        return this.dataManager.get(STAGE);
    }

    private int getCounter()
    {
        return this.counter;
    }

    private void setRandomStage()
    {
        WeightedRandom.Item one = new WeightedRandom.Item(10);
        WeightedRandom.Item two = new WeightedRandom.Item(8);
        WeightedRandom.Item three = new WeightedRandom.Item(6);
        WeightedRandom.Item four = new WeightedRandom.Item(4);
        WeightedRandom.Item five = new WeightedRandom.Item(2);
        WeightedRandom.Item stage = WeightedRandom.getRandomItem(this.rand, Lists.newArrayList(one, two, three, four, five));
        this.setStage(stage == one ? 0 : stage == two ? 1 : stage == three ? 2 : stage == four ? 3 : 4);
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

        this.dataManager.set(STAGE, stage);
    }

    private void setCounter(int i)
    {
        this.counter = i;
    }

    private void updateSize()
    {
        if(this.getStage() == 0 || this.getStage() == 1 && (this.width != 0.65F || this.height != 0.75F))
        {
            this.setSize(0.65F, 0.75F);
        }
        else if(this.getStage() == 2 && (this.width != 0.78F || this.height != 0.88F))
        {
            this.setSize(0.78F, 0.88F);
        }
        else if(this.getStage() == 3 || this.getStage() == 4 && (this.width != 0.8F || this.height != 1.48F))
        {
            this.setSize(0.8F, 1.48F);
        }
    }
}

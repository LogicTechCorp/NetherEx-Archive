/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
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

package logictechcorp.netherex.entity.hostile;

import com.google.common.collect.Lists;
import logictechcorp.netherex.NetherExConfig;
import logictechcorp.netherex.entity.NetherExEntities;
import logictechcorp.netherex.utility.NetherExSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class SporeEntity extends MonsterEntity
{
    private static final DataParameter<Integer> STAGE = EntityDataManager.createKey(SporeEntity.class, DataSerializers.VARINT);

    private int counter;

    public SporeEntity(EntityType<? extends MonsterEntity> entityType, World world)
    {
        super(entityType, world);
    }

    public static boolean canSpawn(EntityType<SporeEntity> entityType, IWorld world, SpawnReason spawnReason, BlockPos pos, Random random)
    {
        return world.getDifficulty() != Difficulty.PEACEFUL;
    }

    @Override
    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
    }

    @Override
    protected void registerData()
    {
        super.registerData();
        this.dataManager.register(STAGE, 0);
    }

    @Override
    public void tick()
    {
        super.tick();

        if(this.getStage() < 4)
        {
            this.counter++;

            if(this.counter >= (NetherExConfig.ENTITY.sporeGrowthTime.get() / (this.getStage() + 1)) * 20)
            {
                int stage = this.getStage() + 1;
                this.counter = 0;
                this.setStage(stage);
            }
        }
        else
        {
            if(this.world.getEntitiesWithinAABB(PlayerEntity.class, this.getBoundingBox().expand(2, 2, 2)).size() > 0)
            {
                this.remove();

                if(!this.world.isRemote)
                {

                    int creeperSpawns = this.rand.nextInt(NetherExConfig.ENTITY.sporeHatchAmount.get()) + 1;

                    for(int i = 0; i < creeperSpawns; i++)
                    {
                        SporeCreeperEntity creeper = new SporeCreeperEntity(NetherExEntities.SPORE_CREEPER, this.world);
                        creeper.setPosition(this.posX, this.posY, this.posZ);
                        this.world.addEntity(creeper);
                    }
                }
            }
        }
    }

    @Override
    public ILivingEntityData onInitialSpawn(IWorld world, DifficultyInstance difficulty, SpawnReason reason, ILivingEntityData spawnData, CompoundNBT compound)
    {
        this.setRandomStage();
        return super.onInitialSpawn(world, difficulty, reason, spawnData, compound);
    }

    @Override
    public void writeAdditional(CompoundNBT compound)
    {
        super.writeAdditional(compound);
        compound.putInt("Counter", this.counter);
    }

    @Override
    public void readAdditional(CompoundNBT compound)
    {
        super.readAdditional(compound);
        this.counter = compound.getInt("Counter");
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return NetherExSounds.ENTITY_SPORE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return NetherExSounds.ENTITY_SPORE_DEATH;
    }

    @Override
    public EntitySize getSize(Pose pose)
    {
        int stage = this.getStage();

        if(stage <= 1)
        {
            return this.getType().getSize();
        }
        else if(stage == 2)
        {
            return this.getType().getSize().scale(1.2F, 1.173F);
        }
        else
        {
            return this.getType().getSize().scale(1.23F, 1.1973F);
        }
    }

    public int getStage()
    {
        return this.dataManager.get(STAGE);
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
}

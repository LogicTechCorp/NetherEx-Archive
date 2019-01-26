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

package logictechcorp.netherex.entity.monster;

import logictechcorp.libraryex.utility.EntityHelper;
import logictechcorp.netherex.entity.projectile.EntityBlueFireball;
import logictechcorp.netherex.init.NetherExLootTables;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityFrost extends EntityMob
{
    private static final DataParameter<Byte> ON_FIRE = EntityDataManager.createKey(EntityBlaze.class, DataSerializers.BYTE);
    private float heightOffset = 0.5F;
    private int heightOffsetUpdateTime;

    public EntityFrost(World worldIn)
    {
        super(worldIn);
        this.setPathPriority(PathNodeType.WATER, 8.0F);
        this.setPathPriority(PathNodeType.LAVA, -1.0F);
        this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
        this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0F);
        this.experienceValue = 10;
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(4, new EntityAIBlueFireballAttack(this));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D, 0.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(ON_FIRE, (byte) 0);
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_BLAZE_AMBIENT;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return NetherExLootTables.FROST;
    }

    @Override
    protected void updateAITasks()
    {
        if(EntityHelper.isInBlock(this, Blocks.FIRE.getDefaultState(), Blocks.LAVA.getDefaultState(), Blocks.FLOWING_LAVA.getDefaultState()))
        {
            this.attackEntityFrom(DamageSource.ON_FIRE, 1.0F);
        }

        this.heightOffsetUpdateTime--;

        if(this.heightOffsetUpdateTime <= 0)
        {
            this.heightOffsetUpdateTime = 100;
            this.heightOffset = 0.5F + (float) this.rand.nextGaussian() * 3.0F;
        }

        EntityLivingBase attackTarget = this.getAttackTarget();

        if(attackTarget != null && attackTarget.posY + (double) attackTarget.getEyeHeight() > this.posY + (double) this.getEyeHeight() + (double) this.heightOffset)
        {
            this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
            this.isAirBorne = true;
        }

        super.updateAITasks();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    @Override
    public float getBrightness()
    {
        return 1.0F;
    }

    @Override
    public boolean isBurning()
    {
        return this.isCharged();
    }

    public boolean isCharged()
    {
        return (this.dataManager.get(ON_FIRE) & 1) != 0;
    }

    @Override
    public void onLivingUpdate()
    {
        if(!this.onGround && this.motionY < 0.0D)
        {
            this.motionY *= 0.6D;
        }

        if(this.world.isRemote)
        {
            if(this.rand.nextInt(24) == 0 && !this.isSilent())
            {
                this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.ENTITY_BLAZE_BURN, this.getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
            }

            for(int i = 0; i < 2; i++)
            {
                this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 0.0D, 0.0D, 0.0D);
            }
        }

        super.onLivingUpdate();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_BLAZE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_BLAZE_DEATH;
    }

    @Override
    protected boolean isValidLightLevel()
    {
        return true;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48.0D);
    }

    @Override
    public void fall(float distance, float damageMultiplier)
    {
    }

    public void setOnFire(boolean onFire)
    {
        byte fire = this.dataManager.get(ON_FIRE);

        if(onFire)
        {
            fire = (byte) (fire | 1);
        }
        else
        {
            fire = (byte) (fire & -2);
        }

        this.dataManager.set(ON_FIRE, fire);
    }

    static class EntityAIBlueFireballAttack extends EntityAIBase
    {
        private final EntityFrost frost;
        private int attackStep;
        private int attackTime;

        public EntityAIBlueFireballAttack(EntityFrost frost)
        {
            this.frost = frost;
            this.setMutexBits(3);
        }

        @Override
        public boolean shouldExecute()
        {
            EntityLivingBase attackTarget = this.frost.getAttackTarget();
            return attackTarget != null && attackTarget.isEntityAlive();
        }

        @Override
        public void startExecuting()
        {
            this.attackStep = 0;
        }

        @Override
        public void resetTask()
        {
            this.frost.setOnFire(false);
        }

        @Override
        public void updateTask()
        {
            World world = this.frost.getEntityWorld();
            EntityLivingBase attackTarget = this.frost.getAttackTarget();
            double distanceSq = this.frost.getDistanceSq(attackTarget);
            this.attackTime--;

            if(distanceSq < 4.0D)
            {
                if(this.attackTime <= 0)
                {
                    this.attackTime = 20;
                    this.frost.attackEntityAsMob(attackTarget);
                }

                this.frost.getMoveHelper().setMoveTo(attackTarget.posX, attackTarget.posY, attackTarget.posZ, 1.0D);
            }
            else if(distanceSq < this.getFollowDistance() * this.getFollowDistance())
            {
                double posX = attackTarget.posX - this.frost.posX;
                double posY = attackTarget.getEntityBoundingBox().minY + (double) (attackTarget.height / 2.0F) - (this.frost.posY + (double) (this.frost.height / 2.0F));
                double posZ = attackTarget.posZ - this.frost.posZ;

                if(this.attackTime <= 0)
                {
                    this.attackStep++;

                    if(this.attackStep == 1)
                    {
                        this.attackTime = 60;
                        this.frost.setOnFire(true);
                    }
                    else if(this.attackStep <= 4)
                    {
                        this.attackTime = 6;
                    }
                    else
                    {
                        this.attackTime = 100;
                        this.attackStep = 0;
                        this.frost.setOnFire(false);
                    }

                    if(this.attackStep > 1)
                    {
                        float distanceSq2 = MathHelper.sqrt(MathHelper.sqrt(distanceSq)) * 0.5F;
                        this.frost.world.playEvent(null, 1018, new BlockPos((int) this.frost.posX, (int) this.frost.posY, (int) this.frost.posZ), 0);

                        for(int i = 0; i < 1; i++)
                        {
                            EntityBlueFireball blueFireball = new EntityBlueFireball(world, this.frost, posX + this.frost.getRNG().nextGaussian() * (double) distanceSq2, posY, posZ + this.frost.getRNG().nextGaussian() * (double) distanceSq2);
                            blueFireball.posY = this.frost.posY + (double) (this.frost.height / 2.0F) + 0.5D;
                            world.spawnEntity(blueFireball);
                        }
                    }
                }

                this.frost.getLookHelper().setLookPositionWithEntity(attackTarget, 10.0F, 10.0F);
            }
            else
            {
                this.frost.getNavigator().clearPath();
                this.frost.getMoveHelper().setMoveTo(attackTarget.posX, attackTarget.posY, attackTarget.posZ, 1.0D);
            }

            super.updateTask();
        }

        private double getFollowDistance()
        {
            IAttributeInstance attribute = this.frost.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
            return attribute == null ? 16.0D : attribute.getAttributeValue();
        }
    }
}

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

import logictechcorp.netherex.NetherExConfig;
import logictechcorp.netherex.init.NetherExLootTables;
import logictechcorp.netherex.init.NetherExSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityEmber extends EntityMob
{
    private int jumpCounter;
    private int jumpDuration;
    private int moveDuration;
    private boolean wasOnGround;

    public EntityEmber(World world)
    {
        super(world);
        this.isImmuneToFire = true;
        this.jumpHelper = new JumpHelper(this);
        this.moveHelper = new MoveHelper(this);
        this.setSize(0.35F, 0.65F);
        this.setMovementSpeed(0.0D);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.LAVA, 1.0F);
        this.setPathPriority(PathNodeType.DANGER_FIRE, 1.0F);
        this.setPathPriority(PathNodeType.DAMAGE_FIRE, 1.0F);
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
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return NetherExSoundEvents.EMBER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return NetherExSoundEvents.EMBER_DEATH;
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAILeapAtTarget(this, 0.45F));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.5D);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 0.0D, 0.0D, 0.0D);

        if(this.jumpCounter != this.jumpDuration)
        {
            this.jumpCounter++;
        }
        else if(this.jumpDuration != 0)
        {
            this.jumpCounter = 0;
            this.jumpDuration = 0;
            this.setJumping(false);
        }
    }

    @Override
    public void updateAITasks()
    {
        if(this.isWet())
        {
            this.attackEntityFrom(DamageSource.DROWN, 1.0F);
        }

        if(this.moveDuration > 0)
        {
            this.moveDuration--;
        }

        if(this.onGround)
        {
            if(!this.wasOnGround)
            {
                this.setJumping(false);
                this.checkLandingDelay();
            }

            JumpHelper helper = (JumpHelper) this.jumpHelper;

            if(!helper.isJumping())
            {
                if(this.moveHelper.isUpdating() && this.moveDuration == 0)
                {
                    Path path = this.navigator.getPath();
                    Vec3d movePos = new Vec3d(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ());

                    if(path != null && path.getCurrentPathIndex() < path.getCurrentPathLength())
                    {
                        movePos = path.getPosition(this);
                    }

                    this.calculateRotationYaw(movePos.x, movePos.z);
                    this.startJumping();
                }
            }
            else if(!helper.canJump())
            {
                this.enableJumpControl();
            }
        }

        this.wasOnGround = this.onGround;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
        if(NetherExConfig.entity.ember.setPlayerOnFireRarity > 0 && this.rand.nextInt(NetherExConfig.entity.ember.setPlayerOnFireRarity) == 0)
        {
            entity.setFire(4);
        }

        return super.attackEntityAsMob(entity);
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    protected float getJumpUpwardsMotion()
    {
        if(!this.collidedHorizontally && (!this.moveHelper.isUpdating() || this.moveHelper.getY() <= this.posY + 0.5D))
        {
            Path path = this.navigator.getPath();

            if(path != null && path.getCurrentPathIndex() < path.getCurrentPathLength())
            {
                Vec3d pos = path.getPosition(this);

                if(pos.y > this.posY + 0.5D)
                {
                    return 0.5F;
                }
            }

            return this.moveHelper.getSpeed() <= 0.6D ? 0.2F : 0.3F;
        }
        else
        {
            return 0.5F;
        }
    }

    @Override
    protected void jump()
    {
        super.jump();
        double speed = this.moveHelper.getSpeed();

        if(speed > 0.0D)
        {
            double motionSq = this.motionX * this.motionX + this.motionZ * this.motionZ;

            if(motionSq < 0.010000000000000002D)
            {
                this.moveRelative(0.0F, 0.0F, 1.0F, 0.1F);
            }
        }

        if(!this.world.isRemote)
        {
            this.world.setEntityState(this, (byte) 1);
        }
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    @Override
    public boolean isNotColliding()
    {
        return this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty();
    }

    @Override
    protected ResourceLocation getLootTable()
    {
        return NetherExLootTables.EMBER;
    }

    public void setMovementSpeed(double newSpeed)
    {
        this.getNavigator().setSpeed(newSpeed);
        this.moveHelper.setMoveTo(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ(), newSpeed);
    }

    private void startJumping()
    {
        this.setJumping(true);
        this.jumpDuration = 10;
        this.jumpCounter = 0;
    }

    private void calculateRotationYaw(double x, double z)
    {
        this.rotationYaw = (float) (MathHelper.atan2(z - this.posZ, x - this.posX) * (180D / Math.PI)) - 90.0F;
    }

    private void enableJumpControl()
    {
        ((JumpHelper) this.jumpHelper).setCanJump(true);
    }

    private void disableJumpControl()
    {
        ((JumpHelper) this.jumpHelper).setCanJump(false);
    }

    private void updateMoveTypeDuration()
    {
        if(this.moveHelper.getSpeed() < 2.2D)
        {
            this.moveDuration = 10;
        }
        else
        {
            this.moveDuration = 1;
        }
    }

    private void checkLandingDelay()
    {
        this.updateMoveTypeDuration();
        this.disableJumpControl();
    }

    public class JumpHelper extends EntityJumpHelper
    {
        private final EntityEmber ember;
        private boolean canJump;

        public JumpHelper(EntityEmber ember)
        {
            super(ember);
            this.ember = ember;
        }

        public boolean isJumping()
        {
            return this.isJumping;
        }

        public boolean canJump()
        {
            return this.canJump;
        }

        public void setCanJump(boolean canJumpIn)
        {
            this.canJump = canJumpIn;
        }

        @Override
        public void doJump()
        {
            if(this.isJumping)
            {
                this.ember.startJumping();
                this.isJumping = false;
            }
        }
    }

    static class MoveHelper extends EntityMoveHelper
    {
        private final EntityEmber ember;
        private double jumpSpeed;

        public MoveHelper(EntityEmber ember)
        {
            super(ember);
            this.ember = ember;
        }

        @Override
        public void onUpdateMoveHelper()
        {
            if(this.ember.onGround && !this.ember.isJumping && !((JumpHelper) this.ember.jumpHelper).isJumping())
            {
                this.ember.setMovementSpeed(0.0D);
            }
            else if(this.isUpdating())
            {
                this.ember.setMovementSpeed(this.jumpSpeed);
            }

            super.onUpdateMoveHelper();
        }

        @Override
        public void setMoveTo(double x, double y, double z, double speedIn)
        {
            if(this.ember.isInWater())
            {
                speedIn = 1.5D;
            }

            super.setMoveTo(x, y, z, speedIn);

            if(speedIn > 0.0D)
            {
                this.jumpSpeed = speedIn;
            }
        }
    }
}

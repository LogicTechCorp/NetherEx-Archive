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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.handler.ConfigHandler;

public class EntityEmber extends EntityMob
{
    private int jumpTicks;
    private int jumpDuration;
    private int currentMoveTypeDuration;
    private boolean wasOnGround;

    public EntityEmber(World world)
    {
        super(world);

        setSize(0.35F, 0.65F);
        stepHeight = 0.5F;
        isImmuneToFire = true;
        jumpHelper = new JumpHelper(this);
        moveHelper = new MoveHelper(this);
        setMovementSpeed(0.0D);
        setPathPriority(PathNodeType.WATER, -1.0F);
        setPathPriority(PathNodeType.LAVA, 1.0F);
        setPathPriority(PathNodeType.DANGER_FIRE, 1.0F);
        setPathPriority(PathNodeType.DAMAGE_FIRE, 1.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float partialTicks)
    {
        return 15728880;
    }

    @Override
    public float getBrightness(float partialTicks)
    {
        return 1.0F;
    }

    @Override
    protected void initEntityAI()
    {
        tasks.addTask(0, new EntityAILeapAtTarget(this, 0.45F));
        tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));
        tasks.addTask(2, new EntityAIWander(this, 1.0D));
        tasks.addTask(3, new EntityAILookIdle(this));
        targetTasks.addTask(0, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.5D);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        world.spawnParticle(EnumParticleTypes.FLAME, posX + (rand.nextDouble() - 0.5D) * (double) width, posY + rand.nextDouble() * (double) height, posZ + (rand.nextDouble() - 0.5D) * (double) width, 0.0D, 0.0D, 0.0D);

        if(jumpTicks != jumpDuration)
        {
            jumpTicks++;
        }
        else if(jumpDuration != 0)
        {
            jumpTicks = 0;
            jumpDuration = 0;
            setJumping(false);
        }
    }

    @Override
    public void updateAITasks()
    {
        if(isWet())
        {
            attackEntityFrom(DamageSource.DROWN, 1.0F);
        }

        if(currentMoveTypeDuration > 0)
        {
            --currentMoveTypeDuration;
        }

        if(onGround)
        {
            if(!wasOnGround)
            {
                setJumping(false);
                checkLandingDelay();
            }

            JumpHelper helper = (JumpHelper) jumpHelper;

            if(!helper.getIsJumping())
            {
                if(moveHelper.isUpdating() && currentMoveTypeDuration == 0)
                {
                    Path path = navigator.getPath();
                    Vec3d vec3d = new Vec3d(moveHelper.getX(), moveHelper.getY(), moveHelper.getZ());

                    if(path != null && path.getCurrentPathIndex() < path.getCurrentPathLength())
                    {
                        vec3d = path.getPosition(this);
                    }

                    calculateRotationYaw(vec3d.xCoord, vec3d.zCoord);
                    startJumping();
                }
            }
            else if(!helper.canJump())
            {
                enableJumpControl();
            }
        }

        wasOnGround = onGround;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
        if(rand.nextInt(ConfigHandler.Entity.Ember.chanceOfSettingPlayerOnFire) == 0)
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
        if(!isCollidedHorizontally && (!moveHelper.isUpdating() || moveHelper.getY() <= posY + 0.5D))
        {
            Path path = navigator.getPath();

            if(path != null && path.getCurrentPathIndex() < path.getCurrentPathLength())
            {
                Vec3d vec3d = path.getPosition(this);

                if(vec3d.yCoord > posY + 0.5D)
                {
                    return 0.5F;
                }
            }

            return moveHelper.getSpeed() <= 0.6D ? 0.2F : 0.3F;
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
        double d0 = moveHelper.getSpeed();

        if(d0 > 0.0D)
        {
            double d1 = motionX * motionX + motionZ * motionZ;

            if(d1 < 0.010000000000000002D)
            {
                moveRelative(0.0F, 1.0F, 0.1F);
            }
        }

        if(!world.isRemote)
        {
            world.setEntityState(this, (byte) 1);
        }
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    @Override
    public boolean isNotColliding()
    {
        return world.checkNoEntityCollision(getEntityBoundingBox(), this) && world.getCollisionBoxes(this, getEntityBoundingBox()).isEmpty();
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

    public void setMovementSpeed(double newSpeed)
    {
        getNavigator().setSpeed(newSpeed);
        moveHelper.setMoveTo(moveHelper.getX(), moveHelper.getY(), moveHelper.getZ(), newSpeed);
    }

    private void startJumping()
    {
        setJumping(true);
        jumpDuration = 10;
        jumpTicks = 0;
    }

    private void calculateRotationYaw(double x, double z)
    {
        rotationYaw = (float) (MathHelper.atan2(z - posZ, x - posX) * (180D / Math.PI)) - 90.0F;
    }

    private void enableJumpControl()
    {
        ((JumpHelper) jumpHelper).setCanJump(true);
    }

    private void disableJumpControl()
    {
        ((JumpHelper) jumpHelper).setCanJump(false);
    }

    private void updateMoveTypeDuration()
    {
        if(moveHelper.getSpeed() < 2.2D)
        {
            currentMoveTypeDuration = 10;
        }
        else
        {
            currentMoveTypeDuration = 1;
        }
    }

    private void checkLandingDelay()
    {
        updateMoveTypeDuration();
        disableJumpControl();
    }

    public class JumpHelper extends EntityJumpHelper
    {
        private final EntityEmber theEntity;
        private boolean canJump;

        public JumpHelper(EntityEmber ember)
        {
            super(ember);
            theEntity = ember;
        }

        public boolean getIsJumping()
        {
            return isJumping;
        }

        public boolean canJump()
        {
            return canJump;
        }

        public void setCanJump(boolean canJumpIn)
        {
            canJump = canJumpIn;
        }

        public void doJump()
        {
            if(isJumping)
            {
                theEntity.startJumping();
                isJumping = false;
            }
        }
    }

    static class MoveHelper extends EntityMoveHelper
    {
        private final EntityEmber theEntity;
        private double nextJumpSpeed;

        public MoveHelper(EntityEmber ember)
        {
            super(ember);
            theEntity = ember;
        }

        public void onUpdateMoveHelper()
        {
            if(theEntity.onGround && !theEntity.isJumping && !((JumpHelper) theEntity.jumpHelper).getIsJumping())
            {
                theEntity.setMovementSpeed(0.0D);
            }
            else if(isUpdating())
            {
                theEntity.setMovementSpeed(nextJumpSpeed);
            }

            super.onUpdateMoveHelper();
        }

        public void setMoveTo(double x, double y, double z, double speedIn)
        {
            if(theEntity.isInWater())
            {
                speedIn = 1.5D;
            }

            super.setMoveTo(x, y, z, speedIn);

            if(speedIn > 0.0D)
            {
                nextJumpSpeed = speedIn;
            }
        }
    }
}

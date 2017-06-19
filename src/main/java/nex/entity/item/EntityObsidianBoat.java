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

package nex.entity.item;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.client.CPacketSteerBoat;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.init.NetherExItems;

import java.util.List;

@SuppressWarnings("ConstantConditions,unchecked")
public class EntityObsidianBoat extends EntityBoat
{
    private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(EntityObsidianBoat.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> FORWARD_DIRECTION = EntityDataManager.createKey(EntityObsidianBoat.class, DataSerializers.VARINT);
    private static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.createKey(EntityObsidianBoat.class, DataSerializers.FLOAT);
    private static final DataParameter<Boolean>[] DATA_ID_PADDLE = new DataParameter[]{EntityDataManager.createKey(EntityObsidianBoat.class, DataSerializers.BOOLEAN), EntityDataManager.createKey(EntityObsidianBoat.class, DataSerializers.BOOLEAN)};
    private final float[] paddlePositions;

    private float outOfControlTicks;
    private float deltaRotation;
    private int lerpSteps;
    private double boatPitch;
    private double lerpY;
    private double lerpZ;
    private double boatYaw;
    private double lerpXRot;
    private boolean leftInputDown;
    private boolean rightInputDown;
    private boolean forwardInputDown;
    private boolean backInputDown;
    private double lavaLevel;

    private float boatGlide;
    private Status status;
    private Status previousStatus;
    private double lastYd;

    public EntityObsidianBoat(World world)
    {
        super(world);

        setSize(1.375F, 0.5625F);
        isImmuneToFire = true;
        paddlePositions = new float[2];
        preventEntitySpawning = true;

    }

    public EntityObsidianBoat(World world, double x, double y, double z)
    {
        this(world);

        setPosition(x, y, z);
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
        prevPosX = x;
        prevPosY = y;
        prevPosZ = z;
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    protected void entityInit()
    {
        dataManager.register(TIME_SINCE_HIT, 0);
        dataManager.register(FORWARD_DIRECTION, 1);
        dataManager.register(DAMAGE_TAKEN, 0.0F);

        for(DataParameter<Boolean> booleanDataParameter : DATA_ID_PADDLE)
        {
            dataManager.register(booleanDataParameter, false);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity entity)
    {
        return entity.canBePushed() ? entity.getEntityBoundingBox() : null;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox()
    {
        return getEntityBoundingBox();
    }

    @Override
    public boolean canBePushed()
    {
        return true;
    }

    @Override
    public double getMountedYOffset()
    {
        return -0.1D;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if(isEntityInvulnerable(source))
        {
            return false;
        }
        else if(!world.isRemote && !isDead)
        {
            if(source instanceof EntityDamageSourceIndirect && source.getTrueSource() != null && isPassenger(source.getTrueSource()))
            {
                return false;
            }
            else
            {
                setForwardDirection(-getForwardDirection());
                setTimeSinceHit(10);
                setDamageTaken(getDamageTaken() + amount * 10.0F);
                setBeenAttacked();
                boolean flag = source.getTrueSource() instanceof EntityPlayer && ((EntityPlayer) source.getTrueSource()).capabilities.isCreativeMode;

                if(flag || getDamageTaken() > 40.0F)
                {
                    if(!flag && world.getGameRules().getBoolean("doEntityDrops"))
                    {
                        dropItemWithOffset(NetherExItems.ITEM_BOAT_OBSIDIAN, 1, 0.0F);
                    }

                    setDead();
                }

                return true;
            }
        }
        else
        {
            return true;
        }
    }

    @Override
    public void applyEntityCollision(Entity entity)
    {
        if(entity instanceof EntityObsidianBoat)
        {
            if(entity.getEntityBoundingBox().minY < getEntityBoundingBox().maxY)
            {
                super.applyEntityCollision(entity);
            }
        }
        else if(entity.getEntityBoundingBox().minY <= getEntityBoundingBox().minY)
        {
            super.applyEntityCollision(entity);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void performHurtAnimation()
    {
        setForwardDirection(-getForwardDirection());
        setTimeSinceHit(10);
        setDamageTaken(getDamageTaken() * 11.0F);
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return !isDead;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport)
    {
        boatPitch = x;
        lerpY = y;
        lerpZ = z;
        boatYaw = (double) yaw;
        lerpXRot = (double) pitch;
        lerpSteps = 10;
    }

    @Override
    public EnumFacing getAdjustedHorizontalFacing()
    {
        return getHorizontalFacing().rotateY();
    }

    @Override
    public void onUpdate()
    {
        previousStatus = status;
        status = getBoatStatus();

        if(status != Status.UNDER_LAVA && status != Status.UNDER_FLOWING_LAVA)
        {
            outOfControlTicks = 0.0F;
        }
        else
        {
            ++outOfControlTicks;
        }

        if(!world.isRemote && outOfControlTicks >= 60.0F)
        {
            removePassengers();
        }

        if(getTimeSinceHit() > 0)
        {
            setTimeSinceHit(getTimeSinceHit() - 1);
        }

        if(getDamageTaken() > 0.0F)
        {
            setDamageTaken(getDamageTaken() - 1.0F);
        }

        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        if(!world.isRemote)
        {
            setFlag(6, isGlowing());
        }

        onEntityUpdate();

        tickLerp();

        if(canPassengerSteer())
        {
            if(getPassengers().size() == 0 || !(getPassengers().get(0) instanceof EntityPlayer))
            {
                setPaddleState(false, false);
            }

            updateMotion();

            if(world.isRemote)
            {
                controlBoat();
                world.sendPacketToServer(new CPacketSteerBoat(getPaddleState(0), getPaddleState(1)));
            }

            move(MoverType.SELF, motionX, motionY, motionZ);
        }
        else
        {
            motionX = 0.0D;
            motionY = 0.0D;
            motionZ = 0.0D;
        }

        for(int i = 0; i <= 1; ++i)
        {
            if(getPaddleState(i))
            {
                paddlePositions[i] = (float) ((double) paddlePositions[i] + 0.01D);
            }
            else
            {
                paddlePositions[i] = 0.0F;
            }
        }

        doBlockCollisions();

        List<Entity> list = world.getEntitiesInAABBexcluding(this, getEntityBoundingBox().expand(0.20000000298023224D, -0.009999999776482582D, 0.20000000298023224D), EntitySelectors.getTeamCollisionPredicate(this));

        if(!list.isEmpty())
        {
            boolean flag = !world.isRemote && !(getControllingPassenger() instanceof EntityPlayer);

            for(Entity entity : list)
            {
                if(!entity.isPassenger(this))
                {
                    if(flag && getPassengers().size() < 2 && !entity.isRiding() && entity.width < width && entity instanceof EntityLivingBase && !(entity instanceof EntityWaterMob) && !(entity instanceof EntityPlayer))
                    {
                        entity.startRiding(this);
                    }
                    else
                    {
                        applyEntityCollision(entity);
                    }
                }
            }
        }
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target)
    {
        return new ItemStack(NetherExItems.ITEM_BOAT_OBSIDIAN, 1, 0);
    }

    private void tickLerp()
    {
        if(lerpSteps > 0 && !canPassengerSteer())
        {
            double d0 = posX + (boatPitch - posX) / (double) lerpSteps;
            double d1 = posY + (lerpY - posY) / (double) lerpSteps;
            double d2 = posZ + (lerpZ - posZ) / (double) lerpSteps;
            double d3 = MathHelper.wrapDegrees(boatYaw - (double) rotationYaw);

            rotationYaw = (float) ((double) rotationYaw + d3 / (double) lerpSteps);
            rotationPitch = (float) ((double) rotationPitch + (lerpXRot - (double) rotationPitch) / (double) lerpSteps);
            --lerpSteps;

            setPosition(d0, d1, d2);
            setRotation(rotationYaw, rotationPitch);
        }
    }

    @Override
    public void setPaddleState(boolean leftPaddle, boolean rightPaddle)
    {
        dataManager.set(DATA_ID_PADDLE[0], leftPaddle);
        dataManager.set(DATA_ID_PADDLE[1], rightPaddle);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getRowingTime(int state, float limbSwing)
    {
        return getPaddleState(state) ? (float) MathHelper.clampedLerp((double) paddlePositions[state] - 0.01D, (double) paddlePositions[state], (double) limbSwing) : 0.0F;
    }

    private Status getBoatStatus()
    {
        Status status = getUnderlavaStatus();

        if(status != null)
        {
            lavaLevel = getEntityBoundingBox().maxY;
            return status;
        }
        else if(checkInLava())
        {
            return Status.IN_LAVA;
        }
        else
        {
            float f = getBoatGlide();

            if(f > 0.0F)
            {
                boatGlide = f;
                return Status.ON_LAND;
            }
            else
            {
                return Status.IN_AIR;
            }
        }
    }

    private float getLavaLevelAbove()
    {
        AxisAlignedBB axisalignedbb = getEntityBoundingBox();
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.ceil(axisalignedbb.maxX);
        int k = MathHelper.floor(axisalignedbb.maxY);
        int l = MathHelper.ceil(axisalignedbb.maxY - lastYd);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.ceil(axisalignedbb.maxZ);
        BlockPos.PooledMutableBlockPos mutableBlockPos = BlockPos.PooledMutableBlockPos.retain();

        try
        {
            label78:

            for(int k1 = k; k1 < l; ++k1)
            {
                float f = 0.0F;
                int l1 = i;

                while(true)
                {
                    if(l1 >= j)
                    {
                        if(f < 1.0F)
                        {
                            return (float) mutableBlockPos.getY() + f;
                        }

                        break;
                    }

                    for(int i2 = i1; i2 < j1; ++i2)
                    {
                        mutableBlockPos.setPos(l1, k1, i2);
                        IBlockState state = world.getBlockState(mutableBlockPos);

                        if(state.getMaterial() == Material.LAVA)
                        {
                            f = Math.max(f, BlockLiquid.getBlockLiquidHeight(state, world, mutableBlockPos));
                        }

                        if(f >= 1.0F)
                        {
                            continue label78;
                        }
                    }

                    ++l1;
                }
            }

            return (float) (l + 1);
        }
        finally
        {
            mutableBlockPos.release();
        }
    }

    @Override
    public float getBoatGlide()
    {
        AxisAlignedBB entityBoundingBox = getEntityBoundingBox();
        AxisAlignedBB boundingBox = new AxisAlignedBB(entityBoundingBox.minX, entityBoundingBox.minY - 0.001D, entityBoundingBox.minZ, entityBoundingBox.maxX, entityBoundingBox.minY, entityBoundingBox.maxZ);
        int i = MathHelper.floor(boundingBox.minX) - 1;
        int j = MathHelper.ceil(boundingBox.maxX) + 1;
        int k = MathHelper.floor(boundingBox.minY) - 1;
        int l = MathHelper.ceil(boundingBox.maxY) + 1;
        int i1 = MathHelper.floor(boundingBox.minZ) - 1;
        int j1 = MathHelper.ceil(boundingBox.maxZ) + 1;

        List<AxisAlignedBB> list = Lists.newArrayList();

        float f = 0.0F;
        int k1 = 0;
        BlockPos.PooledMutableBlockPos mutableBlockPos = BlockPos.PooledMutableBlockPos.retain();

        try
        {
            for(int l1 = i; l1 < j; ++l1)
            {
                for(int i2 = i1; i2 < j1; ++i2)
                {
                    int j2 = (l1 != i && l1 != j - 1 ? 0 : 1) + (i2 != i1 && i2 != j1 - 1 ? 0 : 1);

                    if(j2 != 2)
                    {
                        for(int k2 = k; k2 < l; ++k2)
                        {
                            if(j2 <= 0 || k2 != k && k2 != l - 1)
                            {
                                mutableBlockPos.setPos(l1, k2, i2);
                                IBlockState state = world.getBlockState(mutableBlockPos);
                                state.addCollisionBoxToList(world, mutableBlockPos, boundingBox, list, this, false);

                                if(!list.isEmpty())
                                {
                                    f += state.getBlock().slipperiness;
                                    ++k1;
                                }

                                list.clear();
                            }
                        }
                    }
                }
            }
        }
        finally
        {
            mutableBlockPos.release();
        }

        return f / (float) k1;
    }

    private boolean checkInLava()
    {
        AxisAlignedBB entityBoundingBox = getEntityBoundingBox();
        int i = MathHelper.floor(entityBoundingBox.minX);
        int j = MathHelper.ceil(entityBoundingBox.maxX);
        int k = MathHelper.floor(entityBoundingBox.minY);
        int l = MathHelper.ceil(entityBoundingBox.minY + 0.001D);
        int i1 = MathHelper.floor(entityBoundingBox.minZ);
        int j1 = MathHelper.ceil(entityBoundingBox.maxZ);
        boolean flag = false;
        lavaLevel = Double.MIN_VALUE;
        BlockPos.PooledMutableBlockPos mutableBlockPos = BlockPos.PooledMutableBlockPos.retain();

        try
        {
            for(int k1 = i; k1 < j; ++k1)
            {
                for(int l1 = k; l1 < l; ++l1)
                {
                    for(int i2 = i1; i2 < j1; ++i2)
                    {
                        mutableBlockPos.setPos(k1, l1, i2);
                        IBlockState state = world.getBlockState(mutableBlockPos);

                        if(state.getMaterial() == Material.LAVA)
                        {
                            float f = BlockLiquid.getLiquidHeight(state, world, mutableBlockPos);
                            lavaLevel = Math.max((double) f, lavaLevel);
                            flag |= entityBoundingBox.minY < (double) f;
                        }
                    }
                }
            }
        }
        finally
        {
            mutableBlockPos.release();
        }

        return flag;
    }

    private Status getUnderlavaStatus()
    {
        AxisAlignedBB entityBoundingBox = getEntityBoundingBox();
        double d0 = entityBoundingBox.maxY + 0.001D;
        int i = MathHelper.floor(entityBoundingBox.minX);
        int j = MathHelper.ceil(entityBoundingBox.maxX);
        int k = MathHelper.floor(entityBoundingBox.maxY);
        int l = MathHelper.ceil(d0);
        int i1 = MathHelper.floor(entityBoundingBox.minZ);
        int j1 = MathHelper.ceil(entityBoundingBox.maxZ);
        boolean flag = false;
        BlockPos.PooledMutableBlockPos mutableBlockPos = BlockPos.PooledMutableBlockPos.retain();

        try
        {
            for(int k1 = i; k1 < j; ++k1)
            {
                for(int l1 = k; l1 < l; ++l1)
                {
                    for(int i2 = i1; i2 < j1; ++i2)
                    {
                        mutableBlockPos.setPos(k1, l1, i2);
                        IBlockState state = world.getBlockState(mutableBlockPos);

                        if(state.getMaterial() == Material.LAVA && d0 < (double) BlockLiquid.getLiquidHeight(state, world, mutableBlockPos))
                        {
                            if(state.getValue(BlockLiquid.LEVEL) != 0)
                            {
                                return Status.UNDER_FLOWING_LAVA;
                            }

                            flag = true;
                        }
                    }
                }
            }
        }
        finally
        {
            mutableBlockPos.release();
        }

        return flag ? Status.UNDER_LAVA : null;
    }

    private void updateMotion()
    {
        double d1 = hasNoGravity() ? 0.0D : -0.03999999910593033D;
        double d2 = 0.0D;
        float momentum = 0.05F;

        if(previousStatus == Status.IN_AIR && status != Status.IN_AIR && status != Status.ON_LAND)
        {
            lavaLevel = getEntityBoundingBox().minY + (double) height;
            setPosition(posX, (double) (getLavaLevelAbove() - height) + 0.101D, posZ);
            motionY = 0.0D;
            lastYd = 0.0D;
            status = Status.IN_LAVA;
        }
        else
        {
            if(status == Status.IN_LAVA)
            {
                d2 = (lavaLevel - getEntityBoundingBox().minY) / (double) height;
                momentum = 0.9F;
            }
            else if(status == Status.UNDER_FLOWING_LAVA)
            {
                d1 = -7.0E-4D;
                momentum = 0.9F;
            }
            else if(status == Status.UNDER_LAVA)
            {
                d2 = 0.009999999776482582D;
                momentum = 0.45F;
            }
            else if(status == Status.IN_AIR)
            {
                momentum = 0.9F;
            }
            else if(status == Status.ON_LAND)
            {
                momentum = boatGlide;

                if(getControllingPassenger() instanceof EntityPlayer)
                {
                    boatGlide /= 2.0F;
                }
            }

            motionX *= (double) momentum;
            motionZ *= (double) momentum;
            deltaRotation *= momentum;
            motionY += d1;

            if(d2 > 0.0D)
            {
                motionY += d2 * 0.06153846016296973D;
                motionY *= 0.75D;
            }
        }
    }

    private void controlBoat()
    {
        if(isBeingRidden())
        {
            float f = 0.0F;

            if(leftInputDown)
            {
                deltaRotation += -1.0F;
            }

            if(rightInputDown)
            {
                ++deltaRotation;
            }

            if(rightInputDown != leftInputDown && !forwardInputDown && !backInputDown)
            {
                f += 0.005F;
            }

            rotationYaw += deltaRotation;

            if(forwardInputDown)
            {
                f += 0.04F;
            }

            if(backInputDown)
            {
                f -= 0.005F;
            }

            motionX += (double) (MathHelper.sin(-rotationYaw * 0.017453292F) * f);
            motionZ += (double) (MathHelper.cos(rotationYaw * 0.017453292F) * f);
            setPaddleState(rightInputDown && !leftInputDown || forwardInputDown, leftInputDown && !rightInputDown || forwardInputDown);
        }
    }

    @Override
    public void updatePassenger(Entity passenger)
    {
        if(isPassenger(passenger))
        {
            float f = 0.0F;
            float f1 = (float) ((isDead ? 0.009999999776482582D : getMountedYOffset()) + passenger.getYOffset());

            if(getPassengers().size() > 1)
            {
                int i = getPassengers().indexOf(passenger);

                if(i == 0)
                {
                    f = 0.2F;
                }
                else
                {
                    f = -0.6F;
                }

                if(passenger instanceof EntityAnimal)
                {
                    f = (float) ((double) f + 0.2D);
                }
            }

            Vec3d vec3d = (new Vec3d((double) f, 0.0D, 0.0D)).rotateYaw(-rotationYaw * 0.017453292F - ((float) Math.PI / 2F));
            passenger.setPosition(posX + vec3d.x, posY + (double) f1, posZ + vec3d.z);
            passenger.rotationYaw += deltaRotation;
            passenger.setRotationYawHead(passenger.getRotationYawHead() + deltaRotation);
            applyYawToEntity(passenger);

            if(passenger instanceof EntityAnimal && getPassengers().size() > 1)
            {
                int j = passenger.getEntityId() % 2 == 0 ? 90 : 270;
                passenger.setRenderYawOffset(((EntityAnimal) passenger).renderYawOffset + (float) j);
                passenger.setRotationYawHead(passenger.getRotationYawHead() + (float) j);
            }
        }
    }

    @Override
    protected void applyYawToEntity(Entity entityToUpdate)
    {
        entityToUpdate.setRenderYawOffset(rotationYaw);
        float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - rotationYaw);
        float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
        entityToUpdate.prevRotationYaw += f1 - f;
        entityToUpdate.rotationYaw += f1 - f;
        entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void applyOrientationToEntity(Entity entityToUpdate)
    {
        applyYawToEntity(entityToUpdate);
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound)
    {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound)
    {

    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand)
    {
        if(player.isSneaking())
        {
            return false;
        }
        else
        {
            if(!world.isRemote && outOfControlTicks < 60.0F)
            {
                player.startRiding(this);
            }

            return true;
        }
    }

    @Override
    protected void updateFallState(double y, boolean onGround, IBlockState state, BlockPos pos)
    {
        lastYd = motionY;

        if(!isRiding())
        {
            if(onGround)
            {
                if(fallDistance > 3.0F)
                {
                    if(status != Status.ON_LAND)
                    {
                        fallDistance = 0.0F;
                        return;
                    }

                    fall(fallDistance, 1.0F);

                    if(!world.isRemote && !isDead)
                    {
                        setDead();

                        if(world.getGameRules().getBoolean("doEntityDrops"))
                        {
                            for(int i = 0; i < 3; ++i)
                            {
                                entityDropItem(new ItemStack(Blocks.OBSIDIAN, 1, 0), 0.0F);
                            }
                        }
                    }
                }

                fallDistance = 0.0F;
            }
            else if(world.getBlockState((new BlockPos(this)).down()).getMaterial() != Material.LAVA && y < 0.0D)
            {
                fallDistance = (float) ((double) fallDistance - y);
            }
        }
    }

    @Override
    public boolean getPaddleState(int state)
    {
        return dataManager.get(DATA_ID_PADDLE[state]) && getControllingPassenger() != null;
    }

    @Override
    public void setDamageTaken(float damageTaken)
    {
        dataManager.set(DAMAGE_TAKEN, damageTaken);
    }

    @Override
    public float getDamageTaken()
    {
        return dataManager.get(DAMAGE_TAKEN);
    }

    @Override
    public void setTimeSinceHit(int timeSinceHit)
    {
        dataManager.set(TIME_SINCE_HIT, timeSinceHit);
    }

    @Override
    public int getTimeSinceHit()
    {
        return dataManager.get(TIME_SINCE_HIT);
    }

    @Override
    public void setForwardDirection(int forwardDirection)
    {
        dataManager.set(FORWARD_DIRECTION, forwardDirection);
    }

    @Override
    public int getForwardDirection()
    {
        return dataManager.get(FORWARD_DIRECTION);
    }

    @Override
    protected boolean canFitPassenger(Entity passenger)
    {
        return getPassengers().size() < 2;
    }

    @Override
    public Entity getControllingPassenger()
    {
        List<Entity> passengers = getPassengers();
        return passengers.isEmpty() ? null : passengers.get(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateInputs(boolean left, boolean right, boolean forward, boolean back)
    {
        leftInputDown = left;
        rightInputDown = right;
        forwardInputDown = forward;
        backInputDown = back;
    }

    public static enum Status
    {
        IN_LAVA,
        UNDER_LAVA,
        UNDER_FLOWING_LAVA,
        ON_LAND,
        IN_AIR
    }
}

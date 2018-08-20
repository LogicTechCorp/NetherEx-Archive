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

package nex.entity.item;

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

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions,unchecked")
public class EntityObsidianBoat extends EntityBoat
{
    private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(EntityObsidianBoat.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> FORWARD_DIRECTION = EntityDataManager.createKey(EntityObsidianBoat.class, DataSerializers.VARINT);
    private static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.createKey(EntityObsidianBoat.class, DataSerializers.FLOAT);
    private static final DataParameter<Boolean>[] DATA_ID_PADDLE = new DataParameter[]{EntityDataManager.createKey(EntityObsidianBoat.class, DataSerializers.BOOLEAN), EntityDataManager.createKey(EntityObsidianBoat.class, DataSerializers.BOOLEAN)};
    private final float[] paddlePositions;

    private float outOfControlCounter;
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
        isImmuneToFire = true;
        paddlePositions = new float[2];
        preventEntitySpawning = true;
        setSize(1.375F, 0.5625F);
    }

    public EntityObsidianBoat(World world, double x, double y, double z)
    {
        this(world);
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
        prevPosX = x;
        prevPosY = y;
        prevPosZ = z;
        setPosition(x, y, z);
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

        for(DataParameter<Boolean> parameter : DATA_ID_PADDLE)
        {
            dataManager.register(parameter, false);
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
                markVelocityChanged();
                boolean takeDamage = source.getTrueSource() instanceof EntityPlayer && ((EntityPlayer) source.getTrueSource()).capabilities.isCreativeMode;

                if(takeDamage || getDamageTaken() > 40.0F)
                {
                    if(!takeDamage && world.getGameRules().getBoolean("doEntityDrops"))
                    {
                        dropItemWithOffset(NetherExItems.OBSIDIAN_BOAT, 1, 0.0F);
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
            outOfControlCounter = 0.0F;
        }
        else
        {
            outOfControlCounter++;
        }

        if(!world.isRemote && outOfControlCounter >= 60.0F)
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

        for(int i = 0; i <= 1; i++)
        {
            if(getPaddleState(i))
            {
                if(!isSilent() && (double) (paddlePositions[i] % ((float) Math.PI * 2F)) <= (Math.PI / 4D) && ((double) paddlePositions[i] + 0.39269909262657166D) % (Math.PI * 2D) >= (Math.PI / 4D))
                {
                    SoundEvent event = getPaddleSound();

                    if(event != null)
                    {
                        Vec3d look = getLook(1.0F);
                        double lookX = i == 1 ? -look.z : look.z;
                        double lookZ = i == 1 ? look.x : -look.x;
                        world.playSound(null, posX + lookX, posY, posZ + lookZ, event, getSoundCategory(), 1.0F, 0.8F + 0.4F * rand.nextFloat());
                    }
                }

                paddlePositions[i] = (float) ((double) paddlePositions[i] + 0.39269909262657166D);
            }
            else
            {
                paddlePositions[i] = 0.0F;
            }
        }

        doBlockCollisions();

        List<Entity> entities = world.getEntitiesInAABBexcluding(this, getEntityBoundingBox().expand(0.20000000298023224D, -0.009999999776482582D, 0.20000000298023224D), EntitySelectors.getTeamCollisionPredicate(this));

        if(!entities.isEmpty())
        {
            boolean flag = !world.isRemote && !(getControllingPassenger() instanceof EntityPlayer);

            for(Entity entity : entities)
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
        return new ItemStack(NetherExItems.OBSIDIAN_BOAT, 1, 0);
    }

    private void tickLerp()
    {
        if(lerpSteps > 0 && !canPassengerSteer())
        {
            double posX = this.posX + (boatPitch - this.posX) / (double) lerpSteps;
            double posY = this.posY + (lerpY - this.posY) / (double) lerpSteps;
            double posZ = this.posZ + (lerpZ - this.posZ) / (double) lerpSteps;
            double yaw = MathHelper.wrapDegrees(boatYaw - (double) rotationYaw);

            rotationYaw = (float) ((double) rotationYaw + yaw / (double) lerpSteps);
            rotationPitch = (float) ((double) rotationPitch + (lerpXRot - (double) rotationPitch) / (double) lerpSteps);
            lerpSteps--;
            setPosition(posX, posY, posZ);
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
    public float getRowingTime(int side, float limbSwing)
    {
        return getPaddleState(side) ? (float) MathHelper.clampedLerp((double) paddlePositions[side] - 0.39269909262657166D, (double) paddlePositions[side], (double) limbSwing) : 0.0F;
    }

    private Status getBoatStatus()
    {
        Status status = getUnderLavaStatus();

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
        AxisAlignedBB boundingBox = getEntityBoundingBox();
        int minX = MathHelper.floor(boundingBox.minX);
        int maxX = MathHelper.ceil(boundingBox.maxX);
        int minY = MathHelper.floor(boundingBox.maxY);
        int maxY = MathHelper.ceil(boundingBox.maxY - lastYd);
        int minZ = MathHelper.floor(boundingBox.minZ);
        int maxZ = MathHelper.ceil(boundingBox.maxZ);
        BlockPos.PooledMutableBlockPos mutableBlockPos = BlockPos.PooledMutableBlockPos.retain();

        try
        {
            label78:

            for(int y = minY; y < maxY; y++)
            {
                float liquidHeight = 0.0F;
                int x = minX;

                while(true)
                {
                    if(x >= maxX)
                    {
                        if(liquidHeight < 1.0F)
                        {
                            return (float) mutableBlockPos.getY() + liquidHeight;
                        }

                        break;
                    }

                    for(int z = minZ; z < maxZ; z++)
                    {
                        mutableBlockPos.setPos(x, y, z);
                        IBlockState state = world.getBlockState(mutableBlockPos);

                        if(state.getMaterial() == Material.LAVA)
                        {
                            liquidHeight = Math.max(liquidHeight, BlockLiquid.getBlockLiquidHeight(state, world, mutableBlockPos));
                        }

                        if(liquidHeight >= 1.0F)
                        {
                            continue label78;
                        }
                    }

                    x++;
                }
            }

            return (float) (maxY + 1);
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
        int minX = MathHelper.floor(boundingBox.minX) - 1;
        int maxX = MathHelper.ceil(boundingBox.maxX) + 1;
        int minY = MathHelper.floor(boundingBox.minY) - 1;
        int maxY = MathHelper.ceil(boundingBox.maxY) + 1;
        int minZ = MathHelper.floor(boundingBox.minZ) - 1;
        int maxZ = MathHelper.ceil(boundingBox.maxZ) + 1;

        List<AxisAlignedBB> list = new ArrayList<>();

        float slipperiness = 0.0F;
        int friction = 0;
        BlockPos.PooledMutableBlockPos mutableBlockPos = BlockPos.PooledMutableBlockPos.retain();

        try
        {
            for(int x = minX; x < maxX; x++)
            {
                for(int z = minZ; z < maxZ; z++)
                {
                    int offset = (x != minX && x != maxX - 1 ? 0 : 1) + (z != minZ && z != maxZ - 1 ? 0 : 1);

                    if(offset != 2)
                    {
                        for(int y = minY; y < maxY; y++)
                        {
                            if(offset <= 0 || y != minY && y != maxY - 1)
                            {
                                mutableBlockPos.setPos(x, y, z);
                                IBlockState state = world.getBlockState(mutableBlockPos);
                                state.addCollisionBoxToList(world, mutableBlockPos, boundingBox, list, this, false);

                                if(!list.isEmpty())
                                {
                                    slipperiness += state.getBlock().getSlipperiness(state, world, mutableBlockPos, this);
                                    friction++;
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

        return slipperiness / (float) friction;
    }

    private boolean checkInLava()
    {
        AxisAlignedBB entityBoundingBox = getEntityBoundingBox();
        int minX = MathHelper.floor(entityBoundingBox.minX);
        int maxX = MathHelper.ceil(entityBoundingBox.maxX);
        int minY = MathHelper.floor(entityBoundingBox.minY);
        int maxY = MathHelper.ceil(entityBoundingBox.minY + 0.001D);
        int minZ = MathHelper.floor(entityBoundingBox.minZ);
        int maxZ = MathHelper.ceil(entityBoundingBox.maxZ);
        boolean flag = false;
        lavaLevel = Double.MIN_VALUE;
        BlockPos.PooledMutableBlockPos mutableBlockPos = BlockPos.PooledMutableBlockPos.retain();

        try
        {
            for(int x = minX; x < maxX; x++)
            {
                for(int y = minY; y < maxY; y++)
                {
                    for(int z = minZ; z < maxZ; z++)
                    {
                        mutableBlockPos.setPos(x, y, z);
                        IBlockState state = world.getBlockState(mutableBlockPos);

                        if(state.getMaterial() == Material.LAVA)
                        {
                            float liquidHeight = BlockLiquid.getLiquidHeight(state, world, mutableBlockPos);
                            lavaLevel = Math.max((double) liquidHeight, lavaLevel);
                            flag |= entityBoundingBox.minY < (double) liquidHeight;
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

    private Status getUnderLavaStatus()
    {
        AxisAlignedBB entityBoundingBox = getEntityBoundingBox();
        double entityHeight = entityBoundingBox.maxY + 0.001D;
        int minX = MathHelper.floor(entityBoundingBox.minX);
        int maxX = MathHelper.ceil(entityBoundingBox.maxX);
        int minY = MathHelper.floor(entityBoundingBox.maxY);
        int maxY = MathHelper.ceil(entityHeight);
        int minZ = MathHelper.floor(entityBoundingBox.minZ);
        int maxZ = MathHelper.ceil(entityBoundingBox.maxZ);
        boolean flag = false;
        BlockPos.PooledMutableBlockPos mutableBlockPos = BlockPos.PooledMutableBlockPos.retain();

        try
        {
            for(int x = minX; x < maxX; x++)
            {
                for(int y = minY; y < maxY; y++)
                {
                    for(int z = minZ; z < maxZ; z++)
                    {
                        mutableBlockPos.setPos(x, y, z);
                        IBlockState state = world.getBlockState(mutableBlockPos);

                        if(state.getMaterial() == Material.LAVA && entityHeight < (double) BlockLiquid.getLiquidHeight(state, world, mutableBlockPos))
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
        double gravity = hasNoGravity() ? 0.0D : -0.03999999910593033D;
        double position = 0.0D;
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
                position = (lavaLevel - getEntityBoundingBox().minY) / (double) height;
                momentum = 0.9F;
            }
            else if(status == Status.UNDER_FLOWING_LAVA)
            {
                gravity = -7.0E-4D;
                momentum = 0.9F;
            }
            else if(status == Status.UNDER_LAVA)
            {
                position = 0.009999999776482582D;
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
            motionY += gravity;

            if(position > 0.0D)
            {
                motionY += position * 0.06153846016296973D;
                motionY *= 0.75D;
            }
        }
    }

    private void controlBoat()
    {
        if(isBeingRidden())
        {
            float speed = 0.0F;

            if(leftInputDown)
            {
                deltaRotation += -1.0F;
            }

            if(rightInputDown)
            {
                deltaRotation++;
            }

            if(rightInputDown != leftInputDown && !forwardInputDown && !backInputDown)
            {
                speed += 0.005F;
            }

            rotationYaw += deltaRotation;

            if(forwardInputDown)
            {
                speed += 0.04F;
            }

            if(backInputDown)
            {
                speed -= 0.005F;
            }

            motionX += (double) (MathHelper.sin(-rotationYaw * 0.017453292F) * speed);
            motionZ += (double) (MathHelper.cos(rotationYaw * 0.017453292F) * speed);
            setPaddleState(rightInputDown && !leftInputDown || forwardInputDown, leftInputDown && !rightInputDown || forwardInputDown);
        }
    }

    @Override
    public void updatePassenger(Entity passenger)
    {
        if(isPassenger(passenger))
        {
            float x = 0.0F;
            float height = (float) ((isDead ? 0.009999999776482582D : getMountedYOffset()) + passenger.getYOffset());

            if(getPassengers().size() > 1)
            {
                int index = getPassengers().indexOf(passenger);

                if(index == 0)
                {
                    x = 0.2F;
                }
                else
                {
                    x = -0.6F;
                }

                if(passenger instanceof EntityAnimal)
                {
                    x = (float) ((double) x + 0.2D);
                }
            }

            Vec3d pos = (new Vec3d((double) x, 0.0D, 0.0D)).rotateYaw(-rotationYaw * 0.017453292F - ((float) Math.PI / 2F));
            passenger.setPosition(posX + pos.x, posY + (double) height, posZ + pos.z);
            passenger.rotationYaw += deltaRotation;
            passenger.setRotationYawHead(passenger.getRotationYawHead() + deltaRotation);
            applyYawToEntity(passenger);

            if(passenger instanceof EntityAnimal && getPassengers().size() > 1)
            {
                int rotation = passenger.getEntityId() % 2 == 0 ? 90 : 270;
                passenger.setRenderYawOffset(((EntityAnimal) passenger).renderYawOffset + (float) rotation);
                passenger.setRotationYawHead(passenger.getRotationYawHead() + (float) rotation);
            }
        }
    }

    @Override
    protected void applyYawToEntity(Entity entityToUpdate)
    {
        entityToUpdate.setRenderYawOffset(rotationYaw);
        float yaw = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - rotationYaw);
        float clampedYaw = MathHelper.clamp(yaw, -105.0F, 105.0F);
        entityToUpdate.prevRotationYaw += clampedYaw - yaw;
        entityToUpdate.rotationYaw += clampedYaw - yaw;
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
            if(!world.isRemote && outOfControlCounter < 60.0F)
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
                            for(int i = 0; i < 3; i++)
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

    public enum Status
    {
        IN_LAVA,
        UNDER_LAVA,
        UNDER_FLOWING_LAVA,
        ON_LAND,
        IN_AIR
    }
}

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
        this.isImmuneToFire = true;
        this.paddlePositions = new float[2];
        this.preventEntitySpawning = true;
        this.setSize(1.375F, 0.5625F);
    }

    public EntityObsidianBoat(World world, double x, double y, double z)
    {
        this(world);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.setPosition(x, y, z);
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    protected void entityInit()
    {
        this.dataManager.register(TIME_SINCE_HIT, 0);
        this.dataManager.register(FORWARD_DIRECTION, 1);
        this.dataManager.register(DAMAGE_TAKEN, 0.0F);

        for(DataParameter<Boolean> parameter : DATA_ID_PADDLE)
        {
            this.dataManager.register(parameter, false);
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
        return this.getEntityBoundingBox();
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
        if(this.isEntityInvulnerable(source))
        {
            return false;
        }
        else if(!this.world.isRemote && !this.isDead)
        {
            if(source instanceof EntityDamageSourceIndirect && source.getTrueSource() != null && this.isPassenger(source.getTrueSource()))
            {
                return false;
            }
            else
            {
                this.setForwardDirection(-this.getForwardDirection());
                this.setTimeSinceHit(10);
                this.setDamageTaken(this.getDamageTaken() + amount * 10.0F);
                this.markVelocityChanged();
                boolean takeDamage = source.getTrueSource() instanceof EntityPlayer && ((EntityPlayer) source.getTrueSource()).capabilities.isCreativeMode;

                if(takeDamage || this.getDamageTaken() > 40.0F)
                {
                    if(!takeDamage && this.world.getGameRules().getBoolean("doEntityDrops"))
                    {
                        this.dropItemWithOffset(NetherExItems.OBSIDIAN_BOAT, 1, 0.0F);
                    }

                    this.setDead();
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
            if(entity.getEntityBoundingBox().minY < this.getEntityBoundingBox().maxY)
            {
                super.applyEntityCollision(entity);
            }
        }
        else if(entity.getEntityBoundingBox().minY <= this.getEntityBoundingBox().minY)
        {
            super.applyEntityCollision(entity);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void performHurtAnimation()
    {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11.0F);
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport)
    {
        this.boatPitch = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.boatYaw = (double) yaw;
        this.lerpXRot = (double) pitch;
        this.lerpSteps = 10;
    }

    @Override
    public EnumFacing getAdjustedHorizontalFacing()
    {
        return this.getHorizontalFacing().rotateY();
    }

    @Override
    public void onUpdate()
    {
        this.previousStatus = this.status;
        this.status = this.getBoatStatus();

        if(this.status != Status.UNDER_LAVA && this.status != Status.UNDER_FLOWING_LAVA)
        {
            this.outOfControlCounter = 0.0F;
        }
        else
        {
            this.outOfControlCounter++;
        }

        if(!this.world.isRemote && this.outOfControlCounter >= 60.0F)
        {
            this.removePassengers();
        }

        if(this.getTimeSinceHit() > 0)
        {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }

        if(this.getDamageTaken() > 0.0F)
        {
            this.setDamageTaken(this.getDamageTaken() - 1.0F);
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if(!this.world.isRemote)
        {
            this.setFlag(6, this.isGlowing());
        }

        this.onEntityUpdate();
        this.tickLerp();

        if(this.canPassengerSteer())
        {
            if(this.getPassengers().size() == 0 || !(this.getPassengers().get(0) instanceof EntityPlayer))
            {
                this.setPaddleState(false, false);
            }

            this.updateMotion();

            if(this.world.isRemote)
            {
                this.controlBoat();
                this.world.sendPacketToServer(new CPacketSteerBoat(this.getPaddleState(0), this.getPaddleState(1)));
            }

            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        }
        else
        {
            this.motionX = 0.0D;
            this.motionY = 0.0D;
            this.motionZ = 0.0D;
        }

        for(int i = 0; i <= 1; i++)
        {
            if(this.getPaddleState(i))
            {
                if(!this.isSilent() && (double) (this.paddlePositions[i] % ((float) Math.PI * 2F)) <= (Math.PI / 4D) && ((double) this.paddlePositions[i] + 0.39269909262657166D) % (Math.PI * 2D) >= (Math.PI / 4D))
                {
                    SoundEvent event = this.getPaddleSound();

                    if(event != null)
                    {
                        Vec3d look = this.getLook(1.0F);
                        double lookX = i == 1 ? -look.z : look.z;
                        double lookZ = i == 1 ? look.x : -look.x;
                        this.world.playSound(null, this.posX + lookX, this.posY, this.posZ + lookZ, event, this.getSoundCategory(), 1.0F, 0.8F + 0.4F * this.rand.nextFloat());
                    }
                }

                this.paddlePositions[i] = (float) ((double) this.paddlePositions[i] + 0.39269909262657166D);
            }
            else
            {
                this.paddlePositions[i] = 0.0F;
            }
        }

        this.doBlockCollisions();

        List<Entity> entities = this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().expand(0.20000000298023224D, -0.009999999776482582D, 0.20000000298023224D), EntitySelectors.getTeamCollisionPredicate(this));

        if(!entities.isEmpty())
        {
            boolean flag = !this.world.isRemote && !(this.getControllingPassenger() instanceof EntityPlayer);

            for(Entity entity : entities)
            {
                if(!entity.isPassenger(this))
                {
                    if(flag && this.getPassengers().size() < 2 && !entity.isRiding() && entity.width < this.width && entity instanceof EntityLivingBase && !(entity instanceof EntityWaterMob) && !(entity instanceof EntityPlayer))
                    {
                        entity.startRiding(this);
                    }
                    else
                    {
                        this.applyEntityCollision(entity);
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
        if(this.lerpSteps > 0 && !this.canPassengerSteer())
        {
            double posX = this.posX + (this.boatPitch - this.posX) / (double) this.lerpSteps;
            double posY = this.posY + (this.lerpY - this.posY) / (double) this.lerpSteps;
            double posZ = this.posZ + (this.lerpZ - this.posZ) / (double) this.lerpSteps;
            double yaw = MathHelper.wrapDegrees(this.boatYaw - (double) this.rotationYaw);

            this.rotationYaw = (float) ((double) this.rotationYaw + yaw / (double) this.lerpSteps);
            this.rotationPitch = (float) ((double) this.rotationPitch + (this.lerpXRot - (double) this.rotationPitch) / (double) this.lerpSteps);
            this.lerpSteps--;
            this.setPosition(posX, posY, posZ);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
    }

    @Override
    public void setPaddleState(boolean leftPaddle, boolean rightPaddle)
    {
        this.dataManager.set(DATA_ID_PADDLE[0], leftPaddle);
        this.dataManager.set(DATA_ID_PADDLE[1], rightPaddle);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getRowingTime(int side, float limbSwing)
    {
        return this.getPaddleState(side) ? (float) MathHelper.clampedLerp((double) this.paddlePositions[side] - 0.39269909262657166D, (double) this.paddlePositions[side], (double) limbSwing) : 0.0F;
    }

    private Status getBoatStatus()
    {
        Status status = this.getUnderLavaStatus();

        if(status != null)
        {
            this.lavaLevel = this.getEntityBoundingBox().maxY;
            return status;
        }
        else if(this.checkInLava())
        {
            return Status.IN_LAVA;
        }
        else
        {
            float f = this.getBoatGlide();

            if(f > 0.0F)
            {
                this.boatGlide = f;
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
        AxisAlignedBB boundingBox = this.getEntityBoundingBox();
        int minX = MathHelper.floor(boundingBox.minX);
        int maxX = MathHelper.ceil(boundingBox.maxX);
        int minY = MathHelper.floor(boundingBox.maxY);
        int maxY = MathHelper.ceil(boundingBox.maxY - this.lastYd);
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
                        IBlockState state = this.world.getBlockState(mutableBlockPos);

                        if(state.getMaterial() == Material.LAVA)
                        {
                            liquidHeight = Math.max(liquidHeight, BlockLiquid.getBlockLiquidHeight(state, this.world, mutableBlockPos));
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
        AxisAlignedBB entityBoundingBox = this.getEntityBoundingBox();
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
                                IBlockState state = this.world.getBlockState(mutableBlockPos);
                                state.addCollisionBoxToList(this.world, mutableBlockPos, boundingBox, list, this, false);

                                if(!list.isEmpty())
                                {
                                    slipperiness += state.getBlock().getSlipperiness(state, this.world, mutableBlockPos, this);
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
        AxisAlignedBB entityBoundingBox = this.getEntityBoundingBox();
        int minX = MathHelper.floor(entityBoundingBox.minX);
        int maxX = MathHelper.ceil(entityBoundingBox.maxX);
        int minY = MathHelper.floor(entityBoundingBox.minY);
        int maxY = MathHelper.ceil(entityBoundingBox.minY + 0.001D);
        int minZ = MathHelper.floor(entityBoundingBox.minZ);
        int maxZ = MathHelper.ceil(entityBoundingBox.maxZ);
        boolean flag = false;
        this.lavaLevel = Double.MIN_VALUE;
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
                        IBlockState state = this.world.getBlockState(mutableBlockPos);

                        if(state.getMaterial() == Material.LAVA)
                        {
                            float liquidHeight = BlockLiquid.getLiquidHeight(state, this.world, mutableBlockPos);
                            this.lavaLevel = Math.max((double) liquidHeight, this.lavaLevel);
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
        AxisAlignedBB entityBoundingBox = this.getEntityBoundingBox();
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
                        IBlockState state = this.world.getBlockState(mutableBlockPos);

                        if(state.getMaterial() == Material.LAVA && entityHeight < (double) BlockLiquid.getLiquidHeight(state, this.world, mutableBlockPos))
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
        double gravity = this.hasNoGravity() ? 0.0D : -0.03999999910593033D;
        double position = 0.0D;
        float momentum = 0.05F;

        if(this.previousStatus == Status.IN_AIR && this.status != Status.IN_AIR && this.status != Status.ON_LAND)
        {
            this.lavaLevel = this.getEntityBoundingBox().minY + (double) this.height;
            this.setPosition(this.posX, (double) (this.getLavaLevelAbove() - this.height) + 0.101D, this.posZ);
            this.motionY = 0.0D;
            this.lastYd = 0.0D;
            this.status = Status.IN_LAVA;
        }
        else
        {
            if(this.status == Status.IN_LAVA)
            {
                position = (this.lavaLevel - this.getEntityBoundingBox().minY) / (double) this.height;
                momentum = 0.9F;
            }
            else if(this.status == Status.UNDER_FLOWING_LAVA)
            {
                gravity = -7.0E-4D;
                momentum = 0.9F;
            }
            else if(this.status == Status.UNDER_LAVA)
            {
                position = 0.009999999776482582D;
                momentum = 0.45F;
            }
            else if(this.status == Status.IN_AIR)
            {
                momentum = 0.9F;
            }
            else if(this.status == Status.ON_LAND)
            {
                momentum = this.boatGlide;

                if(this.getControllingPassenger() instanceof EntityPlayer)
                {
                    this.boatGlide /= 2.0F;
                }
            }

            this.motionX *= (double) momentum;
            this.motionZ *= (double) momentum;
            this.deltaRotation *= momentum;
            this.motionY += gravity;

            if(position > 0.0D)
            {
                this.motionY += position * 0.06153846016296973D;
                this.motionY *= 0.75D;
            }
        }
    }

    private void controlBoat()
    {
        if(this.isBeingRidden())
        {
            float speed = 0.0F;

            if(this.leftInputDown)
            {
                this.deltaRotation += -1.0F;
            }

            if(this.rightInputDown)
            {
                this.deltaRotation++;
            }

            if(this.rightInputDown != this.leftInputDown && !this.forwardInputDown && !this.backInputDown)
            {
                speed += 0.005F;
            }

            this.rotationYaw += this.deltaRotation;

            if(this.forwardInputDown)
            {
                speed += 0.04F;
            }

            if(this.backInputDown)
            {
                speed -= 0.005F;
            }

            this.motionX += (double) (MathHelper.sin(-this.rotationYaw * 0.017453292F) * speed);
            this.motionZ += (double) (MathHelper.cos(this.rotationYaw * 0.017453292F) * speed);
            this.setPaddleState(this.rightInputDown && !this.leftInputDown || this.forwardInputDown, this.leftInputDown && !this.rightInputDown || this.forwardInputDown);
        }
    }

    @Override
    public void updatePassenger(Entity passenger)
    {
        if(this.isPassenger(passenger))
        {
            float x = 0.0F;
            float height = (float) ((this.isDead ? 0.009999999776482582D : this.getMountedYOffset()) + passenger.getYOffset());

            if(this.getPassengers().size() > 1)
            {
                int index = this.getPassengers().indexOf(passenger);

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

            Vec3d pos = (new Vec3d((double) x, 0.0D, 0.0D)).rotateYaw(-this.rotationYaw * 0.017453292F - ((float) Math.PI / 2F));
            passenger.setPosition(this.posX + pos.x, this.posY + (double) height, this.posZ + pos.z);
            passenger.rotationYaw += this.deltaRotation;
            passenger.setRotationYawHead(passenger.getRotationYawHead() + this.deltaRotation);
            this.applyYawToEntity(passenger);

            if(passenger instanceof EntityAnimal && this.getPassengers().size() > 1)
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
        entityToUpdate.setRenderYawOffset(this.rotationYaw);
        float yaw = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
        float clampedYaw = MathHelper.clamp(yaw, -105.0F, 105.0F);
        entityToUpdate.prevRotationYaw += clampedYaw - yaw;
        entityToUpdate.rotationYaw += clampedYaw - yaw;
        entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void applyOrientationToEntity(Entity entityToUpdate)
    {
        this.applyYawToEntity(entityToUpdate);
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
            if(!this.world.isRemote && this.outOfControlCounter < 60.0F)
            {
                player.startRiding(this);
            }

            return true;
        }
    }

    @Override
    protected void updateFallState(double y, boolean onGround, IBlockState state, BlockPos pos)
    {
        this.lastYd = this.motionY;

        if(!this.isRiding())
        {
            if(onGround)
            {
                if(this.fallDistance > 3.0F)
                {
                    if(this.status != Status.ON_LAND)
                    {
                        this.fallDistance = 0.0F;
                        return;
                    }

                    this.fall(this.fallDistance, 1.0F);

                    if(!this.world.isRemote && !this.isDead)
                    {
                        this.setDead();

                        if(this.world.getGameRules().getBoolean("doEntityDrops"))
                        {
                            for(int i = 0; i < 3; i++)
                            {
                                this.entityDropItem(new ItemStack(Blocks.OBSIDIAN, 1, 0), 0.0F);
                            }
                        }
                    }
                }

                this.fallDistance = 0.0F;
            }
            else if(this.world.getBlockState((new BlockPos(this)).down()).getMaterial() != Material.LAVA && y < 0.0D)
            {
                this.fallDistance = (float) ((double) this.fallDistance - y);
            }
        }
    }

    @Override
    public boolean getPaddleState(int state)
    {
        return this.dataManager.get(DATA_ID_PADDLE[state]) && this.getControllingPassenger() != null;
    }

    @Override
    public void setDamageTaken(float damageTaken)
    {
        this.dataManager.set(DAMAGE_TAKEN, damageTaken);
    }

    @Override
    public float getDamageTaken()
    {
        return this.dataManager.get(DAMAGE_TAKEN);
    }

    @Override
    public void setTimeSinceHit(int timeSinceHit)
    {
        this.dataManager.set(TIME_SINCE_HIT, timeSinceHit);
    }

    @Override
    public int getTimeSinceHit()
    {
        return this.dataManager.get(TIME_SINCE_HIT);
    }

    @Override
    public void setForwardDirection(int forwardDirection)
    {
        this.dataManager.set(FORWARD_DIRECTION, forwardDirection);
    }

    @Override
    public int getForwardDirection()
    {
        return this.dataManager.get(FORWARD_DIRECTION);
    }

    @Override
    protected boolean canFitPassenger(Entity passenger)
    {
        return this.getPassengers().size() < 2;
    }

    @Override
    public Entity getControllingPassenger()
    {
        List<Entity> passengers = this.getPassengers();
        return passengers.isEmpty() ? null : passengers.get(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateInputs(boolean left, boolean right, boolean forward, boolean back)
    {
        this.leftInputDown = left;
        this.rightInputDown = right;
        this.forwardInputDown = forward;
        this.backInputDown = back;
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

package logictechcorp.netherex.entity.ai;

import logictechcorp.libraryex.util.EntityHelper;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityAIFollowOwnerRideable extends EntityAIBase
{
    private final AbstractHorse rideable;
    private EntityLivingBase owner;
    private final World world;
    private final double followSpeed;
    private final PathNavigate navigator;
    private int timeToRecalculatePath;
    private float maxDistance;
    private float minDistance;
    private float oldWaterCost;

    public EntityAIFollowOwnerRideable(AbstractHorse rideable, double followSpeed, float minDistance, float maxDistance)
    {
        this.rideable = rideable;
        this.world = rideable.world;
        this.followSpeed = followSpeed;
        this.navigator = rideable.getNavigator();
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.setMutexBits(3);

        if(!(rideable.getNavigator() instanceof PathNavigateGround) && !(rideable.getNavigator() instanceof PathNavigateFlying) && !(rideable.getNavigator() instanceof PathNavigateClimber))
        {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    @Override
    public boolean shouldExecute()
    {
        Entity entity = EntityHelper.getFromUUID(this.rideable.getServer(), this.rideable.getOwnerUniqueId());

        if(entity == null)
        {
            return false;
        }
        else if(entity instanceof EntityPlayer && ((EntityPlayer) entity).isSpectator())
        {
            return false;
        }
        else if(this.rideable.getDistanceSq(entity) < (double) (this.minDistance * this.minDistance))
        {
            return false;
        }
        else
        {
            this.owner = (EntityLivingBase) entity;
            return true;
        }
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return !this.navigator.noPath() && this.rideable.getDistanceSq(this.owner) > (double) (this.maxDistance * this.maxDistance);
    }

    @Override
    public void startExecuting()
    {
        this.timeToRecalculatePath = 0;
        this.oldWaterCost = this.rideable.getPathPriority(PathNodeType.WATER);
        this.rideable.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    @Override
    public void resetTask()
    {
        this.owner = null;
        this.navigator.clearPath();
        this.rideable.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
    }

    @Override
    public void updateTask()
    {
        this.rideable.getLookHelper().setLookPositionWithEntity(this.owner, 10.0F, (float) this.rideable.getVerticalFaceSpeed());

        if(this.timeToRecalculatePath-- <= 0)
        {
            this.timeToRecalculatePath = 10;

            if(!this.navigator.tryMoveToEntityLiving(this.owner, this.followSpeed))
            {
                if(!this.rideable.getLeashed() && !this.rideable.isRiding())
                {
                    if(this.rideable.getDistanceSq(this.owner) >= 144.0D)
                    {
                        int posX = MathHelper.floor(this.owner.posX) - 2;
                        int posZ = MathHelper.floor(this.owner.posZ) - 2;
                        int posY = MathHelper.floor(this.owner.getEntityBoundingBox().minY);

                        for(int x = 0; x <= 4; x++)
                        {
                            for(int z = 0; z <= 4; z++)
                            {
                                if((x < 1 || z < 1 || x > 3 || z > 3) && this.isTeleportFriendlyBlock(posX, posZ, posY, x, z))
                                {
                                    this.rideable.setLocationAndAngles((double) ((float) (posX + x) + 0.5F), (double) posY, (double) ((float) (posZ + z) + 0.5F), this.rideable.rotationYaw, this.rideable.rotationPitch);
                                    this.navigator.clearPath();
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    protected boolean isTeleportFriendlyBlock(int x, int z, int y, int xOffset, int zOffset)
    {
        BlockPos pos = new BlockPos(x + xOffset, y - 1, z + zOffset);
        IBlockState state = this.world.getBlockState(pos);
        return state.getBlockFaceShape(this.world, pos, EnumFacing.DOWN) == BlockFaceShape.SOLID && state.canEntitySpawn(this.rideable) && this.world.isAirBlock(pos.up()) && this.world.isAirBlock(pos.up(2));
    }
}

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

package nex.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;

public abstract class EntityAIFenceGateInteract extends EntityAIBase
{
    protected EntityLiving theEntity;
    protected BlockPos fenceGatePos = BlockPos.ORIGIN;
    protected BlockFenceGate fenceGate;
    boolean hasStoppedFenceGateInteraction;
    float entityPositionX;
    float entityPositionZ;

    public EntityAIFenceGateInteract(EntityLiving entityIn)
    {
        theEntity = entityIn;

        if(!(entityIn.getNavigator() instanceof PathNavigateGround))
        {
            throw new IllegalArgumentException("Unsupported mob type for FenceGateInteractGoal");
        }
    }

    @Override
    public boolean shouldExecute()
    {
        if(!theEntity.isCollidedHorizontally)
        {
            return false;
        }
        else
        {
            PathNavigateGround pathnavigateground = (PathNavigateGround) theEntity.getNavigator();
            Path path = pathnavigateground.getPath();

            if(path != null && !path.isFinished() && pathnavigateground.getEnterDoors())
            {
                for(int i = 0; i < Math.min(path.getCurrentPathIndex() + 2, path.getCurrentPathLength()); ++i)
                {
                    PathPoint pathpoint = path.getPathPointFromIndex(i);
                    fenceGatePos = new BlockPos(pathpoint.xCoord, pathpoint.yCoord, pathpoint.zCoord);

                    if(theEntity.getDistanceSq((double) fenceGatePos.getX(), theEntity.posY, (double) fenceGatePos.getZ()) <= 2.25D)
                    {
                        fenceGate = getFenceGate(fenceGatePos);

                        if(fenceGate != null)
                        {
                            return true;
                        }
                    }
                }

                fenceGatePos = (new BlockPos(theEntity));
                fenceGate = getFenceGate(fenceGatePos);
                return fenceGate != null;
            }
            else
            {
                return false;
            }
        }
    }

    @Override
    public boolean continueExecuting()
    {
        return !hasStoppedFenceGateInteraction;
    }

    @Override
    public void startExecuting()
    {
        hasStoppedFenceGateInteraction = false;
        entityPositionX = (float) ((double) ((float) fenceGatePos.getX() + 0.5F) - theEntity.posX);
        entityPositionZ = (float) ((double) ((float) fenceGatePos.getZ() + 0.5F) - theEntity.posZ);
    }

    @Override
    public void updateTask()
    {
        float f = (float) ((double) ((float) fenceGatePos.getX() + 0.5F) - theEntity.posX);
        float f1 = (float) ((double) ((float) fenceGatePos.getZ() + 0.5F) - theEntity.posZ);
        float f2 = entityPositionX * f + entityPositionZ * f1;

        if(f2 < 0.0F)
        {
            hasStoppedFenceGateInteraction = true;
        }
    }

    private BlockFenceGate getFenceGate(BlockPos pos)
    {
        IBlockState iblockstate = theEntity.world.getBlockState(pos);
        Block block = iblockstate.getBlock();
        return block instanceof BlockFenceGate ? (BlockFenceGate) block : null;
    }
}

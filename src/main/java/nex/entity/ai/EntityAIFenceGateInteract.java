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
    protected EntityLiving entity;
    protected BlockPos fenceGatePos = BlockPos.ORIGIN;
    protected BlockFenceGate fenceGate;
    protected boolean stopInteraction;
    protected float entityPosX;
    protected float entityPosZ;

    public EntityAIFenceGateInteract(EntityLiving entity)
    {
        this.entity = entity;

        if(!(entity.getNavigator() instanceof PathNavigateGround))
        {
            throw new IllegalArgumentException("Unsupported mob type for EntityAIFenceGateInteract");
        }
    }

    @Override
    public boolean shouldExecute()
    {
        if(!entity.collidedHorizontally)
        {
            return false;
        }
        else
        {
            PathNavigateGround navigator = (PathNavigateGround) entity.getNavigator();
            Path path = navigator.getPath();

            if(path != null && !path.isFinished() && navigator.getEnterDoors())
            {
                for(int i = 0; i < Math.min(path.getCurrentPathIndex() + 2, path.getCurrentPathLength()); i++)
                {
                    PathPoint point = path.getPathPointFromIndex(i);
                    fenceGatePos = new BlockPos(point.x, point.y, point.z);

                    if(entity.getDistanceSq((double) fenceGatePos.getX(), entity.posY, (double) fenceGatePos.getZ()) <= 2.25D)
                    {
                        fenceGate = getFenceGate(fenceGatePos);

                        if(fenceGate != null)
                        {
                            return true;
                        }
                    }
                }

                fenceGatePos = (new BlockPos(entity));
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
    public boolean shouldContinueExecuting()
    {
        return !stopInteraction;
    }

    @Override
    public void startExecuting()
    {
        stopInteraction = false;
        entityPosX = (float) ((double) ((float) fenceGatePos.getX() + 0.5F) - entity.posX);
        entityPosZ = (float) ((double) ((float) fenceGatePos.getZ() + 0.5F) - entity.posZ);
    }

    @Override
    public void updateTask()
    {
        float distanceX = (float) ((double) ((float) fenceGatePos.getX() + 0.5F) - entity.posX);
        float distanceZ = (float) ((double) ((float) fenceGatePos.getZ() + 0.5F) - entity.posZ);
        float distance = entityPosX * distanceX + entityPosZ * distanceZ;

        if(distance < 0.0F)
        {
            stopInteraction = true;
        }
    }

    private BlockFenceGate getFenceGate(BlockPos pos)
    {
        IBlockState state = entity.world.getBlockState(pos);
        Block block = state.getBlock();
        return block instanceof BlockFenceGate ? (BlockFenceGate) block : null;
    }
}

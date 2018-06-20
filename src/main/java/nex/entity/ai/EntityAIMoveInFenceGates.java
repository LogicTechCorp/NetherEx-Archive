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

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import nex.village.NetherExVillage;
import nex.village.NetherExVillageFenceGateInfo;
import nex.village.NetherExVillageManager;

public class EntityAIMoveInFenceGates extends EntityAIBase
{
    private final EntityCreature entityObj;
    private NetherExVillageFenceGateInfo doorInfo;
    private int insidePosX = -1;
    private int insidePosZ = -1;

    public EntityAIMoveInFenceGates(EntityCreature entityObjIn)
    {
        entityObj = entityObjIn;
        setMutexBits(1);
    }

    @Override
    public boolean shouldExecute()
    {
        BlockPos blockpos = new BlockPos(entityObj);

        if((!entityObj.world.isDaytime() || entityObj.world.isRaining() && !entityObj.world.getBiome(blockpos).canRain()) && entityObj.world.provider.hasSkyLight())
        {
            if(entityObj.getRNG().nextInt(50) != 0)
            {
                return false;
            }
            else if(insidePosX != -1 && entityObj.getDistanceSq((double) insidePosX, entityObj.posY, (double) insidePosZ) < 4.0D)
            {
                return false;
            }
            else
            {
                NetherExVillage village = NetherExVillageManager.getNetherVillages(entityObj.getEntityWorld(), true).getNearestVillage(blockpos, 14);

                if(village == null)
                {
                    return false;
                }
                else
                {
                    doorInfo = village.getFenceGateInfo(blockpos);
                    return doorInfo != null;
                }
            }
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return !entityObj.getNavigator().noPath();
    }

    @Override
    public void startExecuting()
    {
        insidePosX = -1;
        BlockPos blockpos = doorInfo.getInsideBlockPos();
        int i = blockpos.getX();
        int j = blockpos.getY();
        int k = blockpos.getZ();

        if(entityObj.getDistanceSq(blockpos) > 256.0D)
        {
            Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockTowards(entityObj, 14, 3, new Vec3d((double) i + 0.5D, (double) j, (double) k + 0.5D));

            if(vec3d != null)
            {
                entityObj.getNavigator().tryMoveToXYZ(vec3d.x, vec3d.y, vec3d.z, 1.0D);
            }
        }
        else
        {
            entityObj.getNavigator().tryMoveToXYZ((double) i + 0.5D, (double) j, (double) k + 0.5D, 1.0D);
        }
    }

    @Override
    public void resetTask()
    {
        insidePosX = doorInfo.getInsideBlockPos().getX();
        insidePosZ = doorInfo.getInsideBlockPos().getZ();
        doorInfo = null;
    }
}

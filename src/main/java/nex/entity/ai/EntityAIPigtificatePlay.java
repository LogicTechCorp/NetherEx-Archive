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

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.Vec3d;
import nex.entity.passive.EntityPigtificate;

import java.util.List;

public class EntityAIPigtificatePlay extends EntityAIBase
{
    private final EntityPigtificate pigtificateObj;
    private EntityLivingBase targetPigtificate;
    private final double speed;
    private int playTime;

    public EntityAIPigtificatePlay(EntityPigtificate pigtificateObjIn, double speedIn)
    {
        pigtificateObj = pigtificateObjIn;
        speed = speedIn;
        setMutexBits(1);
    }

    @Override
    public boolean shouldExecute()
    {
        if(pigtificateObj.getGrowingAge() >= 0)
        {
            return false;
        }
        else if(pigtificateObj.getRNG().nextInt(400) != 0)
        {
            return false;
        }
        else
        {
            List<EntityPigtificate> list = pigtificateObj.world.getEntitiesWithinAABB(EntityPigtificate.class, pigtificateObj.getEntityBoundingBox().expand(6.0D, 3.0D, 6.0D));
            double d0 = Double.MAX_VALUE;

            for(EntityPigtificate entitypigtificate : list)
            {
                if(entitypigtificate != pigtificateObj && !entitypigtificate.isPlaying() && entitypigtificate.getGrowingAge() < 0)
                {
                    double d1 = entitypigtificate.getDistanceSq(pigtificateObj);

                    if(d1 <= d0)
                    {
                        d0 = d1;
                        targetPigtificate = entitypigtificate;
                    }
                }
            }

            if(targetPigtificate == null)
            {
                Vec3d vec3d = RandomPositionGenerator.findRandomTarget(pigtificateObj, 16, 3);

                if(vec3d == null)
                {
                    return false;
                }
            }

            return true;
        }
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return playTime > 0;
    }

    @Override
    public void startExecuting()
    {
        if(targetPigtificate != null)
        {
            pigtificateObj.setPlaying(true);
        }

        playTime = 1000;
    }

    @Override
    public void resetTask()
    {
        pigtificateObj.setPlaying(false);
        targetPigtificate = null;
    }

    @Override
    public void updateTask()
    {
        --playTime;

        if(targetPigtificate != null)
        {
            if(pigtificateObj.getDistanceSq(targetPigtificate) > 4.0D)
            {
                pigtificateObj.getNavigator().tryMoveToEntityLiving(targetPigtificate, speed);
            }
        }
        else if(pigtificateObj.getNavigator().noPath())
        {
            Vec3d vec3d = RandomPositionGenerator.findRandomTarget(pigtificateObj, 16, 3);

            if(vec3d == null)
            {
                return;
            }

            pigtificateObj.getNavigator().tryMoveToXYZ(vec3d.x, vec3d.y, vec3d.z, speed);
        }
    }
}

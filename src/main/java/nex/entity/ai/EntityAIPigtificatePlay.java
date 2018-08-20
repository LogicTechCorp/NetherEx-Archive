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
    private final EntityPigtificate pigtificate;
    private EntityLivingBase target;
    private final double speed;
    private int playTime;

    public EntityAIPigtificatePlay(EntityPigtificate pigtificate, double speed)
    {
        this.pigtificate = pigtificate;
        this.speed = speed;
        setMutexBits(1);
    }

    @Override
    public boolean shouldExecute()
    {
        if(pigtificate.getGrowingAge() >= 0)
        {
            return false;
        }
        else if(pigtificate.getRNG().nextInt(400) != 0)
        {
            return false;
        }
        else
        {
            List<EntityPigtificate> pigtificates = pigtificate.world.getEntitiesWithinAABB(EntityPigtificate.class, pigtificate.getEntityBoundingBox().expand(6.0D, 3.0D, 6.0D));
            double maxDistance = Double.MAX_VALUE;

            for(EntityPigtificate testPigtificate : pigtificates)
            {
                if(testPigtificate != pigtificate && !testPigtificate.isPlaying() && testPigtificate.getGrowingAge() < 0)
                {
                    double distance = testPigtificate.getDistanceSq(pigtificate);

                    if(distance <= maxDistance)
                    {
                        maxDistance = distance;
                        target = testPigtificate;
                    }
                }
            }

            if(target == null)
            {
                return RandomPositionGenerator.findRandomTarget(pigtificate, 16, 3) != null;
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
        if(target != null)
        {
            pigtificate.setPlaying(true);
        }

        playTime = 1000;
    }

    @Override
    public void resetTask()
    {
        pigtificate.setPlaying(false);
        target = null;
    }

    @Override
    public void updateTask()
    {
        playTime--;

        if(target != null)
        {
            if(pigtificate.getDistanceSq(target) > 4.0D)
            {
                pigtificate.getNavigator().tryMoveToEntityLiving(target, speed);
            }
        }
        else if(pigtificate.getNavigator().noPath())
        {
            Vec3d pos = RandomPositionGenerator.findRandomTarget(pigtificate, 16, 3);

            if(pos == null)
            {
                return;
            }

            pigtificate.getNavigator().tryMoveToXYZ(pos.x, pos.y, pos.z, speed);
        }
    }
}

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

package logictechcorp.netherex.entity.ai;

import logictechcorp.netherex.entity.passive.EntityPigtificate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.Vec3d;

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
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute()
    {
        if(this.pigtificate.getGrowingAge() >= 0)
        {
            return false;
        }
        else if(this.pigtificate.getRNG().nextInt(400) != 0)
        {
            return false;
        }
        else
        {
            List<EntityPigtificate> pigtificates = this.pigtificate.world.getEntitiesWithinAABB(EntityPigtificate.class, this.pigtificate.getEntityBoundingBox().expand(6.0D, 3.0D, 6.0D));
            double maxDistance = Double.MAX_VALUE;

            for(EntityPigtificate testPigtificate : pigtificates)
            {
                if(testPigtificate != this.pigtificate && !testPigtificate.isPlaying() && testPigtificate.getGrowingAge() < 0)
                {
                    double distance = testPigtificate.getDistanceSq(this.pigtificate);

                    if(distance <= maxDistance)
                    {
                        maxDistance = distance;
                        this.target = testPigtificate;
                    }
                }
            }

            if(this.target == null)
            {
                return RandomPositionGenerator.findRandomTarget(this.pigtificate, 16, 3) != null;
            }

            return true;
        }
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return this.playTime > 0;
    }

    @Override
    public void startExecuting()
    {
        if(this.target != null)
        {
            this.pigtificate.setPlaying(true);
        }

        this.playTime = 1000;
    }

    @Override
    public void resetTask()
    {
        this.pigtificate.setPlaying(false);
        this.target = null;
    }

    @Override
    public void updateTask()
    {
        this.playTime--;

        if(this.target != null)
        {
            if(this.pigtificate.getDistanceSq(this.target) > 4.0D)
            {
                this.pigtificate.getNavigator().tryMoveToEntityLiving(this.target, this.speed);
            }
        }
        else if(this.pigtificate.getNavigator().noPath())
        {
            Vec3d pos = RandomPositionGenerator.findRandomTarget(this.pigtificate, 16, 3);

            if(pos == null)
            {
                return;
            }

            this.pigtificate.getNavigator().tryMoveToXYZ(pos.x, pos.y, pos.z, this.speed);
        }
    }
}

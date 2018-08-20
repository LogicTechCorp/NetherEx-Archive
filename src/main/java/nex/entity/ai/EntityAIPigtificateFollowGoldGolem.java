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

import net.minecraft.entity.ai.EntityAIBase;
import nex.entity.neutral.EntityGoldGolem;
import nex.entity.passive.EntityPigtificate;

import java.util.List;

public class EntityAIPigtificateFollowGoldGolem extends EntityAIBase
{
    private final EntityPigtificate pigtificate;
    private EntityGoldGolem golem;
    private int flowerHeldCounter;
    private boolean hasFlower;

    public EntityAIPigtificateFollowGoldGolem(EntityPigtificate pigtificate)
    {
        this.pigtificate = pigtificate;
        setMutexBits(3);
    }

    @Override
    public boolean shouldExecute()
    {
        if(pigtificate.getGrowingAge() >= 0)
        {
            return false;
        }
        else if(!pigtificate.world.isDaytime())
        {
            return false;
        }
        else
        {
            List<EntityGoldGolem> list = pigtificate.world.getEntitiesWithinAABB(EntityGoldGolem.class, pigtificate.getEntityBoundingBox().expand(6.0D, 2.0D, 6.0D));

            if(list.isEmpty())
            {
                return false;
            }
            else
            {
                for(EntityGoldGolem goldGolem : list)
                {
                    if(goldGolem.getFlowerHeldCounter() > 0)
                    {
                        golem = goldGolem;
                        break;
                    }
                }

                return golem != null;
            }
        }
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return golem.getFlowerHeldCounter() > 0;
    }

    @Override
    public void startExecuting()
    {
        flowerHeldCounter = pigtificate.getRNG().nextInt(320);
        hasFlower = false;
        golem.getNavigator().clearPath();
    }

    @Override
    public void resetTask()
    {
        golem = null;
        pigtificate.getNavigator().clearPath();
    }

    @Override
    public void updateTask()
    {
        pigtificate.getLookHelper().setLookPositionWithEntity(golem, 30.0F, 30.0F);

        if(golem.getFlowerHeldCounter() == flowerHeldCounter)
        {
            pigtificate.getNavigator().tryMoveToEntityLiving(golem, 0.5D);
            hasFlower = true;
        }

        if(hasFlower && pigtificate.getDistanceSq(golem) < 4.0D)
        {
            golem.setHoldingFlower(false);
            pigtificate.getNavigator().clearPath();
        }
    }
}

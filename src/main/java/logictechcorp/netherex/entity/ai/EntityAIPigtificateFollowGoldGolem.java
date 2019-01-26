/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
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

import logictechcorp.libraryex.utility.WorldHelper;
import logictechcorp.netherex.entity.neutral.EntityGoldGolem;
import logictechcorp.netherex.entity.passive.EntityPigtificate;
import net.minecraft.entity.ai.EntityAIBase;

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
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute()
    {
        if(this.pigtificate.getGrowingAge() >= 0)
        {
            return false;
        }
        else if(!WorldHelper.isDaytime(this.pigtificate.getEntityWorld()))
        {
            return false;
        }
        else
        {
            List<EntityGoldGolem> list = this.pigtificate.world.getEntitiesWithinAABB(EntityGoldGolem.class, this.pigtificate.getEntityBoundingBox().expand(6.0D, 2.0D, 6.0D));

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
                        this.golem = goldGolem;
                        break;
                    }
                }

                return this.golem != null;
            }
        }
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return this.golem.getFlowerHeldCounter() > 0;
    }

    @Override
    public void startExecuting()
    {
        this.flowerHeldCounter = this.pigtificate.getRNG().nextInt(320);
        this.hasFlower = false;
        this.golem.getNavigator().clearPath();
    }

    @Override
    public void resetTask()
    {
        this.golem = null;
        this.pigtificate.getNavigator().clearPath();
    }

    @Override
    public void updateTask()
    {
        this.pigtificate.getLookHelper().setLookPositionWithEntity(this.golem, 30.0F, 30.0F);

        if(this.golem.getFlowerHeldCounter() == this.flowerHeldCounter)
        {
            this.pigtificate.getNavigator().tryMoveToEntityLiving(this.golem, 0.5D);
            this.hasFlower = true;
        }

        if(this.hasFlower && this.pigtificate.getDistanceSq(this.golem) < 4.0D)
        {
            this.golem.setHoldingFlower(false);
            this.pigtificate.getNavigator().clearPath();
        }
    }
}

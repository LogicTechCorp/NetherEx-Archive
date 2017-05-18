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

import net.minecraft.entity.ai.EntityAIBase;
import nex.entity.neutral.EntityGoldGolem;
import nex.entity.passive.EntityPigtificate;

import java.util.List;

public class EntityAIPigtificateFollowGoldGolem extends EntityAIBase
{
    private final EntityPigtificate thePigtificate;
    private EntityGoldGolem theGolem;
    private int takeGolemRoseTick;
    private boolean tookGolemRose;

    public EntityAIPigtificateFollowGoldGolem(EntityPigtificate thePigtificateIn)
    {
        thePigtificate = thePigtificateIn;
        setMutexBits(3);
    }

    @Override
    public boolean shouldExecute()
    {
        if(thePigtificate.getGrowingAge() >= 0)
        {
            return false;
        }
        else if(!thePigtificate.world.isDaytime())
        {
            return false;
        }
        else
        {
            List<EntityGoldGolem> list = thePigtificate.world.getEntitiesWithinAABB(EntityGoldGolem.class, thePigtificate.getEntityBoundingBox().expand(6.0D, 2.0D, 6.0D));

            if(list.isEmpty())
            {
                return false;
            }
            else
            {
                for(EntityGoldGolem goldGolem : list)
                {
                    if(goldGolem.getHoldFlowerTick() > 0)
                    {
                        theGolem = goldGolem;
                        break;
                    }
                }

                return theGolem != null;
            }
        }
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return theGolem.getHoldFlowerTick() > 0;
    }

    @Override
    public void startExecuting()
    {
        takeGolemRoseTick = thePigtificate.getRNG().nextInt(320);
        tookGolemRose = false;
        theGolem.getNavigator().clearPathEntity();
    }

    @Override
    public void resetTask()
    {
        theGolem = null;
        thePigtificate.getNavigator().clearPathEntity();
    }

    @Override
    public void updateTask()
    {
        thePigtificate.getLookHelper().setLookPositionWithEntity(theGolem, 30.0F, 30.0F);

        if(theGolem.getHoldFlowerTick() == takeGolemRoseTick)
        {
            thePigtificate.getNavigator().tryMoveToEntityLiving(theGolem, 0.5D);
            tookGolemRose = true;
        }

        if(tookGolemRose && thePigtificate.getDistanceSqToEntity(theGolem) < 4.0D)
        {
            theGolem.setHoldingFlower(false);
            thePigtificate.getNavigator().clearPathEntity();
        }
    }
}

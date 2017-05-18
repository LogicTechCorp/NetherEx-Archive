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

public class EntityAIGoldGolemLookAtPigtificate extends EntityAIBase
{
    private final EntityGoldGolem theGolem;
    private EntityPigtificate thePigtificate;
    private int lookTime;

    public EntityAIGoldGolemLookAtPigtificate(EntityGoldGolem theGolemIn)
    {
        theGolem = theGolemIn;
        setMutexBits(3);
    }

    @Override
    public boolean shouldExecute()
    {
        if(!theGolem.world.isDaytime())
        {
            return false;
        }
        else if(theGolem.getRNG().nextInt(8000) != 0)
        {
            return false;
        }
        else
        {
            thePigtificate = (EntityPigtificate) theGolem.world.findNearestEntityWithinAABB(EntityPigtificate.class, theGolem.getEntityBoundingBox().expand(6.0D, 2.0D, 6.0D), theGolem);
            return thePigtificate != null;
        }
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return lookTime > 0;
    }

    @Override
    public void startExecuting()
    {
        lookTime = 400;
    }

    @Override
    public void resetTask()
    {
        thePigtificate = null;
    }

    @Override
    public void updateTask()
    {
        theGolem.getLookHelper().setLookPositionWithEntity(thePigtificate, 30.0F, 30.0F);
        --lookTime;
    }
}

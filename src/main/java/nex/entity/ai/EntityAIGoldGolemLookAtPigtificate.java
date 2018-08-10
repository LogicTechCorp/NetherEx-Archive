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

public class EntityAIGoldGolemLookAtPigtificate extends EntityAIBase
{
    private final EntityGoldGolem golem;
    private EntityPigtificate thePigtificate;
    private int lookTime;

    public EntityAIGoldGolemLookAtPigtificate(EntityGoldGolem golem)
    {
        this.golem = golem;
        setMutexBits(3);
    }

    @Override
    public boolean shouldExecute()
    {
        if(!golem.world.isDaytime())
        {
            return false;
        }
        else if(golem.getRNG().nextInt(8000) != 0)
        {
            return false;
        }
        else
        {
            thePigtificate = (EntityPigtificate) golem.world.findNearestEntityWithinAABB(EntityPigtificate.class, golem.getEntityBoundingBox().expand(6.0D, 2.0D, 6.0D), golem);
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
        golem.getLookHelper().setLookPositionWithEntity(thePigtificate, 30.0F, 30.0F);
        --lookTime;
    }
}

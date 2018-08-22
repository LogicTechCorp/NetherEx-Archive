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

import lex.util.WorldHelper;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;
import nex.entity.neutral.EntityGoldGolem;
import nex.entity.passive.EntityPigtificate;

public class EntityAIGoldGolemLookAtPigtificate extends EntityAIBase
{
    private final EntityGoldGolem golem;
    private EntityPigtificate pigtificate;
    private int lookCounter;

    public EntityAIGoldGolemLookAtPigtificate(EntityGoldGolem golem)
    {
        this.golem = golem;
        setMutexBits(3);
    }

    @Override
    public boolean shouldExecute()
    {
        World world = golem.getEntityWorld();

        if(!WorldHelper.isDaytime(world))
        {
            return false;
        }
        else if(golem.getRNG().nextInt(8000) != 0)
        {
            return false;
        }
        else
        {
            pigtificate = (EntityPigtificate) world.findNearestEntityWithinAABB(EntityPigtificate.class, golem.getEntityBoundingBox().expand(6.0D, 2.0D, 6.0D), golem);
            return pigtificate != null;
        }
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return lookCounter > 0;
    }

    @Override
    public void startExecuting()
    {
        lookCounter = 400;
    }

    @Override
    public void resetTask()
    {
        pigtificate = null;
    }

    @Override
    public void updateTask()
    {
        golem.getLookHelper().setLookPositionWithEntity(pigtificate, 30.0F, 30.0F);
        lookCounter--;
    }
}

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

import logictechcorp.libraryex.util.WorldHelper;
import logictechcorp.netherex.entity.neutral.EntityGoldGolem;
import logictechcorp.netherex.entity.passive.EntityPigtificate;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class EntityAIGoldGolemLookAtPigtificate extends EntityAIBase
{
    private final EntityGoldGolem golem;
    private EntityPigtificate pigtificate;
    private int lookCounter;

    public EntityAIGoldGolemLookAtPigtificate(EntityGoldGolem golem)
    {
        this.golem = golem;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute()
    {
        World world = this.golem.getEntityWorld();

        if(!WorldHelper.isDaytime(world))
        {
            return false;
        }
        else if(this.golem.getRNG().nextInt(8000) != 0)
        {
            return false;
        }
        else
        {
            this.pigtificate = (EntityPigtificate) world.findNearestEntityWithinAABB(EntityPigtificate.class, this.golem.getEntityBoundingBox().expand(6.0D, 2.0D, 6.0D), this.golem);
            return this.pigtificate != null;
        }
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return this.lookCounter > 0;
    }

    @Override
    public void startExecuting()
    {
        this.lookCounter = 400;
    }

    @Override
    public void resetTask()
    {
        this.pigtificate = null;
    }

    @Override
    public void updateTask()
    {
        this.golem.getLookHelper().setLookPositionWithEntity(this.pigtificate, 30.0F, 30.0F);
        this.lookCounter--;
    }
}

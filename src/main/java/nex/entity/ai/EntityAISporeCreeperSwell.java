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
import nex.entity.monster.EntitySporeCreeper;

public class EntityAISporeCreeperSwell extends EntityAIBase
{
    private EntitySporeCreeper creeper;
    private EntityLivingBase target;

    public EntityAISporeCreeperSwell(EntitySporeCreeper creeper)
    {
        this.creeper = creeper;
        setMutexBits(1);
    }

    @Override
    public boolean shouldExecute()
    {
        EntityLivingBase entity = creeper.getAttackTarget();
        return creeper.getCreeperState() > 0 || entity != null && creeper.getDistanceSq(entity) < 9.0D;
    }

    @Override
    public void startExecuting()
    {
        creeper.getNavigator().clearPath();
        target = creeper.getAttackTarget();
    }

    @Override
    public void resetTask()
    {
        target = null;
    }

    @Override
    public void updateTask()
    {
        if(target == null)
        {
            creeper.setCreeperState(-1);
        }
        else if(creeper.getDistanceSq(target) > 49.0D)
        {
            creeper.setCreeperState(-1);
        }
        else if(!creeper.getEntitySenses().canSee(target))
        {
            creeper.setCreeperState(-1);
        }
        else
        {
            creeper.setCreeperState(1);
        }
    }

}

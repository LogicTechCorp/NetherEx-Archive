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

import logictechcorp.netherex.entity.monster.EntitySporeCreeper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAISporeCreeperSwell extends EntityAIBase
{
    private EntitySporeCreeper creeper;
    private EntityLivingBase target;

    public EntityAISporeCreeperSwell(EntitySporeCreeper creeper)
    {
        this.creeper = creeper;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute()
    {
        EntityLivingBase entity = this.creeper.getAttackTarget();
        return this.creeper.getCreeperState() > 0 || entity != null && this.creeper.getDistanceSq(entity) < 9.0D;
    }

    @Override
    public void startExecuting()
    {
        this.creeper.getNavigator().clearPath();
        this.target = this.creeper.getAttackTarget();
    }

    @Override
    public void resetTask()
    {
        this.target = null;
    }

    @Override
    public void updateTask()
    {
        if(this.target == null)
        {
            this.creeper.setCreeperState(-1);
        }
        else if(this.creeper.getDistanceSq(this.target) > 49.0D)
        {
            this.creeper.setCreeperState(-1);
        }
        else if(!this.creeper.getEntitySenses().canSee(this.target))
        {
            this.creeper.setCreeperState(-1);
        }
        else
        {
            this.creeper.setCreeperState(1);
        }
    }

}

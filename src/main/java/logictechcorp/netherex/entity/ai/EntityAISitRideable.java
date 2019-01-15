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

import logictechcorp.libraryex.util.EntityHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.AbstractHorse;

public class EntityAISitRideable extends EntityAIBase
{
    private final AbstractHorse rideable;
    private boolean sitting;

    public EntityAISitRideable(AbstractHorse rideable)
    {
        this.rideable = rideable;
        this.setMutexBits(5);
    }

    @Override
    public boolean shouldExecute()
    {
        if(!this.rideable.isTame())
        {
            return false;
        }
        else if(this.rideable.isInWater())
        {
            return false;
        }
        else if(!this.rideable.onGround)
        {
            return false;
        }
        else
        {
            EntityLivingBase entity = (EntityLivingBase) EntityHelper.getFromUUID(this.rideable.getServer(), this.rideable.getOwnerUniqueId());

            if(entity == null)
            {
                return true;
            }
            else
            {
                return (!(this.rideable.getDistanceSq(entity) < 144.0D) || entity.getRevengeTarget() == null) && this.sitting;
            }
        }
    }

    @Override
    public void startExecuting()
    {
        this.rideable.getNavigator().clearPath();
        this.setSitting(true);
    }

    @Override
    public void resetTask()
    {
        this.setSitting(false);
    }

    public boolean isSitting()
    {
        return this.sitting;
    }

    public void setSitting(boolean sitting)
    {
        this.sitting = sitting;
    }
}

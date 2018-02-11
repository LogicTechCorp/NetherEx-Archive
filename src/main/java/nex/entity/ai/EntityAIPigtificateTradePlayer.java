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
import net.minecraft.entity.player.EntityPlayer;
import nex.entity.passive.EntityPigtificate;

public class EntityAIPigtificateTradePlayer extends EntityAIBase
{
    private final EntityPigtificate pigtificate;

    public EntityAIPigtificateTradePlayer(EntityPigtificate pigtificateIn)
    {
        pigtificate = pigtificateIn;
        setMutexBits(5);
    }

    @Override
    public boolean shouldExecute()
    {
        if(!pigtificate.isEntityAlive())
        {
            return false;
        }
        else if(pigtificate.isInWater())
        {
            return false;
        }
        else if(!pigtificate.onGround)
        {
            return false;
        }
        else if(pigtificate.velocityChanged)
        {
            return false;
        }
        else
        {
            EntityPlayer customer = pigtificate.getCustomer();
            return customer != null && (!(pigtificate.getDistance(customer) > 16.0D) && customer.openContainer != null);
        }
    }

    @Override
    public void startExecuting()
    {
        pigtificate.getNavigator().clearPath();
    }

    @Override
    public void resetTask()
    {
        pigtificate.setCustomer(null);
    }
}

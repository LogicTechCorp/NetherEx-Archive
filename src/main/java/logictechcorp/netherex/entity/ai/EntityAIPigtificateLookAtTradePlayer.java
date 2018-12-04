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

import logictechcorp.netherex.entity.passive.EntityPigtificate;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAIPigtificateLookAtTradePlayer extends EntityAIWatchClosest
{
    private final EntityPigtificate pigtificate;

    public EntityAIPigtificateLookAtTradePlayer(EntityPigtificate pigtificate)
    {
        super(pigtificate, EntityPlayer.class, 8.0F);
        this.pigtificate = pigtificate;
    }

    @Override
    public boolean shouldExecute()
    {
        if(this.pigtificate.isTrading())
        {
            this.closestEntity = this.pigtificate.getCustomer();
            return true;
        }
        else
        {
            return false;
        }
    }
}

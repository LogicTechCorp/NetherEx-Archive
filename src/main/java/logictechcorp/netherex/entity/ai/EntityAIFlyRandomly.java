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

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;

import java.util.Random;

public class EntityAIFlyRandomly extends EntityAIBase
{
    private final EntityLiving flying;

    public EntityAIFlyRandomly(EntityLiving flying)
    {
        this.flying = flying;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute()
    {
        EntityMoveHelper moveHelper = this.flying.getMoveHelper();

        if(!moveHelper.isUpdating())
        {
            return true;
        }
        else
        {
            double distanceX = moveHelper.getX() - this.flying.posX;
            double distanceY = moveHelper.getY() - this.flying.posY;
            double distanceZ = moveHelper.getZ() - this.flying.posZ;
            double distanceSq = distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ;
            return distanceSq < 1.0D || distanceSq > 3600.0D;
        }
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return false;
    }

    @Override
    public void startExecuting()
    {
        Random random = this.flying.getRNG();
        double posX = this.flying.posX + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double posY = this.flying.posY + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double posZ = this.flying.posZ + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        this.flying.getMoveHelper().setMoveTo(posX, posY, posZ, 1.0D);
    }
}

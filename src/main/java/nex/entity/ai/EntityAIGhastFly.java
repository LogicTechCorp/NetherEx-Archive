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
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityGhast;

import java.util.Random;

public class EntityAIGhastFly extends EntityAIBase
{
    private final EntityGhast ghast;

    public EntityAIGhastFly(EntityGhast ghast)
    {
        this.ghast = ghast;
        setMutexBits(1);
    }

    @Override
    public boolean shouldExecute()
    {
        EntityMoveHelper moveHelper = ghast.getMoveHelper();

        if(!moveHelper.isUpdating())
        {
            return true;
        }
        else
        {
            double distanceX = moveHelper.getX() - ghast.posX;
            double distanceY = moveHelper.getY() - ghast.posY;
            double distanceZ = moveHelper.getZ() - ghast.posZ;
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
        Random rand = ghast.getRNG();
        double posX = ghast.posX + (double) ((rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double posY = ghast.posY + (double) ((rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double posZ = ghast.posZ + (double) ((rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
        ghast.getMoveHelper().setMoveTo(posX, posY, posZ, 1.0D);
    }
}

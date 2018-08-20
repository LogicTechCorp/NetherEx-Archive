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
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.util.math.MathHelper;

public class EntityAIGhastLookAround extends EntityAIBase
{
    private final EntityGhast ghast;

    public EntityAIGhastLookAround(EntityGhast ghast)
    {
        this.ghast = ghast;
        setMutexBits(2);
    }

    @Override
    public boolean shouldExecute()
    {
        return true;
    }

    @Override
    public void updateTask()
    {
        if(ghast.getAttackTarget() == null)
        {
            ghast.rotationYaw = -((float) MathHelper.atan2(ghast.motionX, ghast.motionZ)) * (180F / (float) Math.PI);
            ghast.renderYawOffset = ghast.rotationYaw;
        }
        else
        {
            EntityLivingBase target = ghast.getAttackTarget();

            if(target.getDistanceSq(ghast) < 4096.0D)
            {
                double distanceX = target.posX - ghast.posX;
                double distanceZ = target.posZ - ghast.posZ;
                ghast.rotationYaw = -((float) MathHelper.atan2(distanceX, distanceZ)) * (180F / (float) Math.PI);
                ghast.renderYawOffset = ghast.rotationYaw;
            }
        }
    }
}

/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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
    private final EntityGhast parentEntity;

    public EntityAIGhastLookAround(EntityGhast ghast)
    {
        parentEntity = ghast;
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
        if(parentEntity.getAttackTarget() == null)
        {
            parentEntity.rotationYaw = -((float) MathHelper.atan2(parentEntity.motionX, parentEntity.motionZ)) * (180F / (float) Math.PI);
            parentEntity.renderYawOffset = parentEntity.rotationYaw;
        }
        else
        {
            EntityLivingBase target = parentEntity.getAttackTarget();

            if(target.getDistanceSq(parentEntity) < 4096.0D)
            {
                double d1 = target.posX - parentEntity.posX;
                double d2 = target.posZ - parentEntity.posZ;
                parentEntity.rotationYaw = -((float) MathHelper.atan2(d1, d2)) * (180F / (float) Math.PI);
                parentEntity.renderYawOffset = parentEntity.rotationYaw;
            }
        }
    }
}

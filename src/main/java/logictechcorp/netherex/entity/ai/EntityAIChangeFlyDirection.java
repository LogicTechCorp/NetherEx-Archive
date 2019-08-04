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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;

public class EntityAIChangeFlyDirection extends EntityAIBase
{
    private final EntityLiving flying;

    public EntityAIChangeFlyDirection(EntityLiving flying)
    {
        this.flying = flying;
        this.setMutexBits(2);
    }

    @Override
    public boolean shouldExecute()
    {
        return true;
    }

    @Override
    public void updateTask()
    {
        if(this.flying.getAttackTarget() == null)
        {
            this.flying.rotationYaw = -((float) MathHelper.atan2(this.flying.motionX, this.flying.motionZ)) * (180F / (float) Math.PI);
            this.flying.renderYawOffset = this.flying.rotationYaw;
        }
        else
        {
            EntityLivingBase target = this.flying.getAttackTarget();

            if(target.getDistanceSq(this.flying) < 4096.0D)
            {
                double distanceX = target.posX - this.flying.posX;
                double distanceZ = target.posZ - this.flying.posZ;
                this.flying.rotationYaw = -((float) MathHelper.atan2(distanceX, distanceZ)) * (180F / (float) Math.PI);
                this.flying.renderYawOffset = this.flying.rotationYaw;
            }
        }
    }
}

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

package logictechcorp.netherex.entity.ai.controller;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.util.math.MathHelper;

public class FloatingMovementController extends EntityMoveHelper
{
    private boolean stickNearGround;

    public FloatingMovementController(EntityLiving livingEntity, boolean stickNearGround)
    {
        super(livingEntity);
        this.stickNearGround = stickNearGround;
    }

    @Override
    public void onUpdateMoveHelper()
    {
        if(this.action == EntityMoveHelper.Action.MOVE_TO)
        {
            this.action = EntityMoveHelper.Action.WAIT;

            if(!this.stickNearGround && !this.entity.hasNoGravity())
            {
                this.entity.setNoGravity(true);
            }

            double distanceX = this.posX - this.entity.posX;
            double distanceY = this.posY - this.entity.posY;
            double distanceZ = this.posZ - this.entity.posZ;
            double distance = distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ;

            if(distance < 2.500000277905201E-7D)
            {
                this.entity.setMoveVertical(0.0F);
                this.entity.setMoveForward(0.0F);
                return;
            }

            float yawAngle = (float) (MathHelper.atan2(distanceZ, distanceX) * (180D / Math.PI)) - 90.0F;
            this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, yawAngle, 10.0F);

            float speed = (float) (this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
            this.entity.setAIMoveSpeed(speed);

            double distanceXZ = (double) MathHelper.sqrt(distanceX * distanceX + distanceZ * distanceZ);
            float pitchAngle = (float) (-(MathHelper.atan2(distanceY, distanceXZ) * (180D / Math.PI)));
            this.entity.rotationPitch = this.limitAngle(this.entity.rotationPitch, pitchAngle, 10.0F);
            this.entity.setMoveVertical(distanceY > 0.0D ? speed : -speed);
        }
        else
        {
            if(!this.stickNearGround && this.entity.hasNoGravity())
            {
                this.entity.setNoGravity(false);
            }

            this.entity.setMoveVertical(-this.entity.moveVertical);
            this.entity.setMoveForward(0.0F);
        }
    }
}

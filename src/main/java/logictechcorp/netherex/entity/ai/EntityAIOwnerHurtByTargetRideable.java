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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.passive.AbstractHorse;

public class EntityAIOwnerHurtByTargetRideable extends EntityAITarget
{
    private final AbstractHorse rideable;
    private EntityLivingBase attacker;
    private int timestamp;

    public EntityAIOwnerHurtByTargetRideable(AbstractHorse rideable)
    {
        super(rideable, false);
        this.rideable = rideable;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute()
    {
        if(!this.rideable.isTame())
        {
            return false;
        }
        else
        {
            Entity entity = EntityHelper.getFromUUID(this.rideable.getServer(), this.rideable.getOwnerUniqueId());

            if(!(entity instanceof EntityLivingBase))
            {
                return false;
            }
            else
            {
                this.attacker = ((EntityLivingBase) entity).getRevengeTarget();
                int i = ((EntityLivingBase) entity).getRevengeTimer();
                return i != this.timestamp && this.isSuitableTarget(this.attacker, false) && EntityHelper.shouldAttackEntity(this.attacker, ((EntityLivingBase) entity));
            }
        }
    }

    @Override
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.attacker);

        if(this.rideable.getServer() != null && this.rideable.getOwnerUniqueId() != null)
        {
            Entity entity = this.rideable.getServer().getEntityFromUuid(this.rideable.getOwnerUniqueId());

            if(entity != null)
            {
                this.timestamp = ((EntityLivingBase) entity).getRevengeTimer();
            }
        }
        super.startExecuting();
    }
}

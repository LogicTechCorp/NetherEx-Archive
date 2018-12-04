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

import logictechcorp.netherex.entity.monster.EntitySporeCreeper;
import logictechcorp.netherex.entity.neutral.EntityGoldGolem;
import logictechcorp.netherex.village.PigtificateVillage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAIGoldGolemDefendVillage extends EntityAITarget
{
    private EntityGoldGolem golem;
    private EntityLivingBase villageAggressor;

    public EntityAIGoldGolemDefendVillage(EntityGoldGolem golem)
    {
        super(golem, false, true);
        this.golem = golem;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute()
    {
        PigtificateVillage village = this.golem.getVillage();

        if(village == null)
        {
            return false;
        }
        else
        {
            this.villageAggressor = village.findNearestVillageAggressor(this.golem);

            if(this.villageAggressor instanceof EntitySporeCreeper)
            {
                return false;
            }
            else if(this.isSuitableTarget(this.villageAggressor, false))
            {
                return true;
            }
            else if(this.taskOwner.getRNG().nextInt(20) == 0)
            {
                this.villageAggressor = village.getNearestTargetPlayer(this.golem);
                return this.isSuitableTarget(this.villageAggressor, false);
            }
            else
            {
                return false;
            }
        }
    }

    @Override
    public void startExecuting()
    {
        this.golem.setAttackTarget(this.villageAggressor);
        super.startExecuting();
    }
}

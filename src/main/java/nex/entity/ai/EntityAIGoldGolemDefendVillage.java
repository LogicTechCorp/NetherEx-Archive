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
import net.minecraft.entity.ai.EntityAITarget;
import nex.entity.monster.EntitySporeCreeper;
import nex.entity.neutral.EntityGoldGolem;
import nex.village.PigtificateVillage;

public class EntityAIGoldGolemDefendVillage extends EntityAITarget
{
    EntityGoldGolem golem;
    EntityLivingBase villageAgressorTarget;

    public EntityAIGoldGolemDefendVillage(EntityGoldGolem golem)
    {
        super(golem, false, true);
        this.golem = golem;
        setMutexBits(1);
    }

    @Override
    public boolean shouldExecute()
    {
        PigtificateVillage village = golem.getVillage();

        if(village == null)
        {
            return false;
        }
        else
        {
            villageAgressorTarget = village.findNearestVillageAggressor(golem);

            if(villageAgressorTarget instanceof EntitySporeCreeper)
            {
                return false;
            }
            else if(isSuitableTarget(villageAgressorTarget, false))
            {
                return true;
            }
            else if(taskOwner.getRNG().nextInt(20) == 0)
            {
                villageAgressorTarget = village.getNearestTargetPlayer(golem);
                return isSuitableTarget(villageAgressorTarget, false);
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
        golem.setAttackTarget(villageAgressorTarget);
        super.startExecuting();
    }
}

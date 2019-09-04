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

package logictechcorp.netherex.entity.ai.goal;

import logictechcorp.netherex.entity.hostile.SporeCreeperEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class SporeCreeperSwellGoal extends Goal
{
    private final SporeCreeperEntity sporeCreeper;
    private LivingEntity target;

    public SporeCreeperSwellGoal(SporeCreeperEntity sporeCreeper)
    {
        this.sporeCreeper = sporeCreeper;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public void tick()
    {
        if(this.target == null)
        {
            this.sporeCreeper.setCreeperState(-1);

        }
        else if(this.sporeCreeper.getDistanceSq(this.target) > 49.0D)
        {
            this.sporeCreeper.setCreeperState(-1);
        }
        else if(!this.sporeCreeper.getEntitySenses().canSee(this.target))
        {
            this.sporeCreeper.setCreeperState(-1);
        }
        else
        {
            this.sporeCreeper.setCreeperState(1);
        }
    }

    @Override
    public boolean shouldExecute()
    {
        LivingEntity target = this.sporeCreeper.getAttackTarget();
        return this.sporeCreeper.getCreeperState() > 0 || target != null && this.sporeCreeper.getDistanceSq(target) < 9.0D;
    }

    @Override
    public void startExecuting()
    {
        this.sporeCreeper.getNavigator().clearPath();
        this.target = this.sporeCreeper.getAttackTarget();
    }

    @Override
    public void resetTask()
    {
        this.target = null;
    }
}

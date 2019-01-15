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

import com.google.common.base.Predicate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.AbstractHorse;

public class EntityAITargetNonTamedRideable<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T>
{
    private final AbstractHorse rideable;

    public EntityAITargetNonTamedRideable(AbstractHorse rideable, Class<T> classTarget, boolean checkSight, Predicate<? super T> targetSelector)
    {
        super(rideable, classTarget, 10, checkSight, false, targetSelector);
        this.rideable = rideable;
    }

    @Override
    public boolean shouldExecute()
    {
        return !this.rideable.isTame() && super.shouldExecute();
    }
}

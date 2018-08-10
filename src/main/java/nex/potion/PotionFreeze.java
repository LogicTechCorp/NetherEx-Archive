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

package nex.potion;

import lex.potion.PotionLibEx;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import nex.NetherEx;

public class PotionFreeze extends PotionLibEx
{
    public PotionFreeze()
    {
        super(NetherEx.instance, "freeze", true, 93, 188, 210);
    }

    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entity, AbstractAttributeMap attributeMapIn, int amplifier)
    {
        super.applyAttributesModifiersToEntity(entity, attributeMapIn, amplifier);

        if(entity instanceof EntityLiving)
        {
            ((EntityLiving) entity).setNoAI(true);
        }

        entity.setSilent(true);
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entity, AbstractAttributeMap attributeMapIn, int amplifier)
    {
        super.removeAttributesModifiersFromEntity(entity, attributeMapIn, amplifier);

        if(entity instanceof EntityLiving)
        {
            if(((EntityLiving) entity).isAIDisabled())
            {
                ((EntityLiving) entity).setNoAI(false);
            }
        }

        entity.setSilent(false);
    }
}

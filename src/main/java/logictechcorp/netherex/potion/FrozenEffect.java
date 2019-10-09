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

package logictechcorp.netherex.potion;

import logictechcorp.netherex.NetherExConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

import java.awt.*;

public class FrozenEffect extends Effect
{
    public FrozenEffect()
    {
        super(EffectType.HARMFUL, new Color(93, 188, 210).getRGB());
    }

    @Override
    public void applyAttributesModifiersToEntity(LivingEntity entity, AbstractAttributeMap attributeMap, int amplifier)
    {
        if(this.canFreeze(entity))
        {
            super.applyAttributesModifiersToEntity(entity, attributeMap, amplifier);
        }
    }

    @Override
    public void performEffect(LivingEntity entity, int amplifier)
    {
        if(this.canFreeze(entity))
        {
            if(entity.getRNG().nextInt(NetherExConfig.EFFECT.frozenEffectThawChance.get()) == 0)
            {
                entity.removePotionEffect(this);
            }
        }
    }

    @Override
    public void removeAttributesModifiersFromEntity(LivingEntity entity, AbstractAttributeMap attributeMap, int amplifier)
    {
        if(this.canFreeze(entity))
        {
            super.removeAttributesModifiersFromEntity(entity, attributeMap, amplifier);
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }

    public boolean canFreeze(LivingEntity entity)
    {
        if(entity instanceof PlayerEntity && !((PlayerEntity) entity).isCreative())
        {
            return NetherExConfig.EFFECT.frozenEffectCanPlayersFreeze.get();
        }

        return NetherExConfig.EFFECT.frozenEffectEntityBlacklist.get().contains(entity.getType().getRegistryName().toString());
    }
}
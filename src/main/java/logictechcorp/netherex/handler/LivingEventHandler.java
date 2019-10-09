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

package logictechcorp.netherex.handler;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.potion.NetherExEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class LivingEventHandler
{
    @SubscribeEvent
    public static void onLivingHeal(LivingHealEvent event)
    {
        LivingEntity entity = event.getEntityLiving();

        if(!entity.isPotionActive(Effects.REGENERATION) && entity.isPotionActive(NetherExEffects.FROSTBITTEN.get()))
        {
            event.setCanceled(true);
        }
    }
}

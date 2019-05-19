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

package logictechcorp.netherex.init;

import logictechcorp.libraryex.potion.MobEffectMod;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.mobeffect.*;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExMobEffects
{
    public static final Potion FROZEN = new MobEffectFrozen();
    public static final Potion FROSTBITTEN = new MobEffectMod(NetherEx.instance, "frostbitten", true, 19, 226, 255);
    public static final Potion INFESTED = new MobEffectInfested();
    public static final Potion CRYING = new MobEffectCrying();
    public static final Potion FIRE_BURNING = new MobEffectFireBurning();
    public static final Potion SOUL_SUCKED = new MobEffectSoulSucked();

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterPotions(RegistryEvent.Register<Potion> event)
        {
            event.getRegistry().registerAll(
                    FROZEN,
                    FROSTBITTEN,
                    INFESTED,
                    CRYING,
                    FIRE_BURNING,
                    SOUL_SUCKED
            );
        }
    }
}

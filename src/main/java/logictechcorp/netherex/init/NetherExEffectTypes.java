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

import logictechcorp.libraryex.potion.PotionTypeLibEx;
import logictechcorp.libraryex.utility.InjectionHelper;
import logictechcorp.netherex.NetherEx;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExEffectTypes
{
    public static final PotionType NORMAL_FREEZE = InjectionHelper.nullValue();
    public static final PotionType NORMAL_FROSTBITE = InjectionHelper.nullValue();
    public static final PotionType NORMAL_SPORE = InjectionHelper.nullValue();
    public static final PotionType NORMAL_LOST = InjectionHelper.nullValue();

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterPotionTypes(RegistryEvent.Register<PotionType> event)
        {
            event.getRegistry().registerAll(
                    new PotionTypeLibEx(NetherEx.instance, "normal_freeze", new PotionEffect(NetherExEffects.FREEZE, 600)),
                    new PotionTypeLibEx(NetherEx.instance, "normal_frostbite", new PotionEffect(NetherExEffects.FROSTBITE, 600)),
                    new PotionTypeLibEx(NetherEx.instance, "normal_spore", new PotionEffect(NetherExEffects.SPORE, 600)),
                    new PotionTypeLibEx(NetherEx.instance, "normal_lost", new PotionEffect(NetherExEffects.LOST, 600))
            );
        }
    }
}

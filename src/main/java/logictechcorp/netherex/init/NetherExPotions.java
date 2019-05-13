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

import logictechcorp.libraryex.potion.PotionMod;
import logictechcorp.libraryex.utility.InjectionHelper;
import logictechcorp.netherex.NetherEx;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExPotions
{
    public static final PotionMod FREEZING = InjectionHelper.nullValue();
    public static final PotionMod FRIGID_HEALTH = InjectionHelper.nullValue();
    public static final PotionMod DISPERSAL = InjectionHelper.nullValue();
    public static final PotionMod SORROW = InjectionHelper.nullValue();

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterPotionTypes(RegistryEvent.Register<PotionType> event)
        {
            event.getRegistry().registerAll(
                    new PotionMod(NetherEx.instance, "freezing", new PotionEffect(NetherExMobEffects.FROZEN, 600)),
                    new PotionMod(NetherEx.instance, "frigid_health", new PotionEffect(NetherExMobEffects.FROSTBITTEN, 600)),
                    new PotionMod(NetherEx.instance, "dispersal", new PotionEffect(NetherExMobEffects.INFESTED, 600)),
                    new PotionMod(NetherEx.instance, "sorrow", new PotionEffect(NetherExMobEffects.CRYING, 600))
            );
        }
    }
}

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

package logictechcorp.netherex.init;

import logictechcorp.libraryex.potion.PotionLibEx;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.potion.PotionBlueFire;
import logictechcorp.netherex.potion.PotionFreeze;
import logictechcorp.netherex.potion.PotionLost;
import logictechcorp.netherex.potion.PotionSpore;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NetherExEffects
{
    public static final PotionLibEx FREEZE = new PotionFreeze();
    public static final PotionLibEx FROSTBITE = new PotionLibEx(NetherEx.instance, "frostbite", true, 19, 226, 255);
    public static final PotionLibEx SPORE = new PotionSpore();
    public static final PotionLibEx LOST = new PotionLost();
    public static final PotionLibEx BLUE_FIRE = new PotionBlueFire();

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterPotions(RegistryEvent.Register<Potion> event)
        {
            event.getRegistry().registerAll(
                    FREEZE,
                    FROSTBITE,
                    SPORE,
                    LOST,
                    BLUE_FIRE
            );
        }
    }
}

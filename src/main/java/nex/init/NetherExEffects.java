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

package nex.init;

import lex.potion.PotionLibEx;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.NetherEx;
import nex.potion.PotionFreeze;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExEffects
{
    public static final PotionLibEx FREEZE = new PotionFreeze();
    public static final PotionLibEx FROSTBITE = new PotionLibEx(NetherEx.instance, "frostbite", true, 19, 226, 255);
    public static final PotionLibEx SPORE = new PotionLibEx(NetherEx.instance, "spore", true, 142, 96, 40);
    public static final PotionLibEx LOST = new PotionLibEx(NetherEx.instance, "lost", true, 103, 62, 124);

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
                    LOST
            );
        }
    }
}

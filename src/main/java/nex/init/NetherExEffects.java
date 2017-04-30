/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.NetherEx;
import nex.potion.NetherExPotion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExEffects
{
    public static final NetherExPotion FREEZE = new NetherExPotion("freeze", true, 93, 188, 210);
    public static final NetherExPotion FROSTBITE = new NetherExPotion("frostbite", true, 19, 226, 255);
    public static final NetherExPotion SPORE = new NetherExPotion("spore", true, 142, 96, 40);
    public static final NetherExPotion LOST = new NetherExPotion("lost", true, 103, 62, 124);

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExEffects");

    @Mod.EventBusSubscriber
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterPotions(RegistryEvent.Register<Potion> event)
        {
            LOGGER.info("Effect registration started.");

            event.getRegistry().registerAll(
                    FREEZE,
                    FROSTBITE,
                    SPORE,
                    LOST
            );

            LOGGER.info("Effect registration completed.");
        }
    }
}

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

import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.NetherEx;
import nex.potion.NetherExPotionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("ConstantConditions")
@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExEffectTypes
{
    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":normal_freeze")
    public static final PotionType NORMAL_FREEZE = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":long_freeze")
    public static final PotionType LONG_FREEZE = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":strong_freeze")
    public static final PotionType STRONG_FREEZE = null;

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExEffectTypes");

    @Mod.EventBusSubscriber
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterBlocks(RegistryEvent.Register<PotionType> event)
        {
            LOGGER.info("Effect Type registration started.");

            event.getRegistry().registerAll(
                    new NetherExPotionType("normal_freeze", new PotionEffect(NetherExEffects.FREEZE, 1800)),
                    new NetherExPotionType("long_freeze", new PotionEffect(NetherExEffects.FREEZE, 4800)),
                    new NetherExPotionType("strong_freeze", new PotionEffect(NetherExEffects.FREEZE, 900))
            );

            LOGGER.info("Effect Type registration completed.");
        }
    }
}

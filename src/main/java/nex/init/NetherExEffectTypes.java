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

import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.NetherEx;
import nex.potion.PotionTypeNetherEx;

@SuppressWarnings("ConstantConditions")
@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExEffectTypes
{
    @GameRegistry.ObjectHolder("normal_freeze")
    public static final PotionType NORMAL_FREEZE = null;

    @GameRegistry.ObjectHolder("normal_frostbite")
    public static final PotionType NORMAL_FROSTBITE = null;

    @GameRegistry.ObjectHolder("normal_spore")
    public static final PotionType NORMAL_SPORE = null;

    @GameRegistry.ObjectHolder("normal_lost")
    public static final PotionType NORMAL_LOST = null;

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterPotionTypes(RegistryEvent.Register<PotionType> event)
        {
            NetherEx.LOGGER.info("Effect Type registration started.");

            event.getRegistry().registerAll(
                    new PotionTypeNetherEx("normal_freeze", new PotionEffect(NetherExEffects.FREEZE, 600)),
                    new PotionTypeNetherEx("normal_frostbite", new PotionEffect(NetherExEffects.FROSTBITE, 600)),
                    new PotionTypeNetherEx("normal_spore", new PotionEffect(NetherExEffects.SPORE, 600)),
                    new PotionTypeNetherEx("normal_lost", new PotionEffect(NetherExEffects.LOST, 600))
            );

            NetherEx.LOGGER.info("Effect Type registration completed.");
        }
    }
}

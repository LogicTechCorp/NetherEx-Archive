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

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.NetherEx;
import nex.sound.NetherExSoundEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExSoundEvents
{
    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":ghast_queen_moan")
    public static final SoundEvent GHAST_QUEEN_MOAN = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":ghast_queen_scream")
    public static final SoundEvent GHAST_QUEEN_SCREAM = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":ghast_queen_fireball")
    public static final SoundEvent GHAST_QUEEN_FIREBALL = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":ghast_queen_death")
    public static final SoundEvent GHAST_QUEEN_DEATH = null;

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExSoundEvents");

    @Mod.EventBusSubscriber
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterSounds(RegistryEvent.Register<SoundEvent> event)
        {
            LOGGER.info("Sound registration started.");

            event.getRegistry().registerAll(
                    new NetherExSoundEvent(new ResourceLocation(NetherEx.MOD_ID + ":ghast_queen_moan")),
                    new NetherExSoundEvent(new ResourceLocation(NetherEx.MOD_ID + ":ghast_queen_scream")),
                    new NetherExSoundEvent(new ResourceLocation(NetherEx.MOD_ID + ":ghast_queen_fireball")),
                    new NetherExSoundEvent(new ResourceLocation(NetherEx.MOD_ID + ":ghast_queen_death"))
            );

            LOGGER.info("Sound registration completed.");
        }
    }
}

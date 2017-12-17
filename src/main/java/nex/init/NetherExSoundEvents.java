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
import nex.sound.SoundEventNetherEx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExSoundEvents
{
    public static final SoundEvent PIGTIFICATE_AMBIENT = null;
    public static final SoundEvent PIGTIFICATE_HURT = null;
    public static final SoundEvent PIGTIFICATE_DEATH = null;
    public static final SoundEvent MOGUS_AMBIENT = null;
    public static final SoundEvent MOGUS_HURT = null;
    public static final SoundEvent MOGUS_DEATH = null;
    public static final SoundEvent SALAMANDER_AMBIENT = null;
    public static final SoundEvent SALAMANDER_HURT = null;
    public static final SoundEvent SALAMANDER_DEATH = null;
    public static final SoundEvent WIGHT_AMBIENT = null;
    public static final SoundEvent WIGHT_HURT = null;
    public static final SoundEvent WIGHT_DEATH = null;
    public static final SoundEvent EMBER_HURT = null;
    public static final SoundEvent EMBER_DEATH = null;
    public static final SoundEvent NETHERMITE_AMBIENT = null;
    public static final SoundEvent NETHERMITE_HURT = null;
    public static final SoundEvent NETHERMITE_DEATH = null;
    public static final SoundEvent SPINOUT_AMBIENT = null;
    public static final SoundEvent SPINOUT_HURT = null;
    public static final SoundEvent SPINOUT_DEATH = null;
    public static final SoundEvent SPORE_HURT = null;
    public static final SoundEvent SPORE_DEATH = null;
    public static final SoundEvent SPORE_WARN = null;
    public static final SoundEvent SPORE_EXPLODE = null;
    public static final SoundEvent GHASTLING_AMBIENT = null;
    public static final SoundEvent GHASTLING_HURT = null;
    public static final SoundEvent GHASTLING_DEATH = null;
    public static final SoundEvent GHASTLING_WARN = null;
    public static final SoundEvent GHASTLING_SHOOT = null;
    public static final SoundEvent GHAST_QUEEN_AMBIENT = null;
    public static final SoundEvent GHAST_QUEEN_HURT = null;
    public static final SoundEvent GHAST_QUEEN_DEATH = null;
    public static final SoundEvent GHAST_QUEEN_SHOOT = null;
    public static final SoundEvent GHAST_QUEEN_SUMMON = null;

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterSounds(RegistryEvent.Register<SoundEvent> event)
        {
            NetherEx.LOGGER.info("Sound registration started.");

            event.getRegistry().registerAll(
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":pigtificate_ambient")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":pigtificate_hurt")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":pigtificate_death")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":mogus_ambient")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":mogus_hurt")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":mogus_death")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":salamander_ambient")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":salamander_hurt")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":salamander_death")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":wight_ambient")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":wight_hurt")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":wight_death")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":ember_hurt")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":ember_death")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":nethermite_ambient")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":nethermite_hurt")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":nethermite_death")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":spinout_ambient")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":spinout_hurt")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":spinout_death")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":spore_hurt")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":spore_death")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":spore_warn")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":spore_explode")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":ghastling_ambient")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":ghastling_hurt")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":ghastling_death")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":ghastling_warn")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":ghastling_shoot")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":ghast_queen_ambient")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":ghast_queen_hurt")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":ghast_queen_death")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":ghast_queen_shoot")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":ghast_queen_summon"))
            );

            NetherEx.LOGGER.info("Sound registration completed.");
        }
    }
}

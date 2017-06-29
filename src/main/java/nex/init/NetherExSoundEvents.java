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
    @GameRegistry.ObjectHolder("ambient_pigtificate")
    public static final SoundEvent ENTITY_AMBIENT_PIGTIFICATE = null;

    @GameRegistry.ObjectHolder("hurt_pigtificate")
    public static final SoundEvent ENTITY_HURT_PIGTIFICATE = null;

    @GameRegistry.ObjectHolder("death_pigtificate")
    public static final SoundEvent ENTITY_DEATH_PIGTIFICATE = null;

    @GameRegistry.ObjectHolder("ambient_mogus")
    public static final SoundEvent ENTITY_AMBIENT_MOGUS = null;

    @GameRegistry.ObjectHolder("hurt_mogus")
    public static final SoundEvent ENTITY_HURT_MOGUS = null;

    @GameRegistry.ObjectHolder("death_mogus")
    public static final SoundEvent ENTITY_DEATH_MOGUS = null;

    @GameRegistry.ObjectHolder("ambient_salamander")
    public static final SoundEvent ENTITY_AMBIENT_SALAMANDER = null;

    @GameRegistry.ObjectHolder("hurt_salamander")
    public static final SoundEvent ENTITY_HURT_SALAMANDER = null;

    @GameRegistry.ObjectHolder("death_salamander")
    public static final SoundEvent ENTITY_DEATH_SALAMANDER = null;

    @GameRegistry.ObjectHolder("ambient_wight")
    public static final SoundEvent ENTITY_AMBIENT_WIGHT = null;

    @GameRegistry.ObjectHolder("hurt_wight")
    public static final SoundEvent ENTITY_HURT_WIGHT = null;

    @GameRegistry.ObjectHolder("death_wight")
    public static final SoundEvent ENTITY_DEATH_WIGHT = null;

    @GameRegistry.ObjectHolder("hurt_ember")
    public static final SoundEvent ENTITY_HURT_EMBER = null;

    @GameRegistry.ObjectHolder("death_ember")
    public static final SoundEvent ENTITY_DEATH_EMBER = null;

    @GameRegistry.ObjectHolder("ambient_nethermite")
    public static final SoundEvent ENTITY_AMBIENT_NETHERMITE = null;

    @GameRegistry.ObjectHolder("hurt_nethermite")
    public static final SoundEvent ENTITY_HURT_NETHERMITE = null;

    @GameRegistry.ObjectHolder("death_nethermite")
    public static final SoundEvent ENTITY_DEATH_NETHERMITE = null;

    @GameRegistry.ObjectHolder("ambient_spinout")
    public static final SoundEvent ENTITY_AMBIENT_SPINOUT = null;

    @GameRegistry.ObjectHolder("hurt_spinout")
    public static final SoundEvent ENTITY_HURT_SPINOUT = null;

    @GameRegistry.ObjectHolder("death_spinout")
    public static final SoundEvent ENTITY_DEATH_SPINOUT = null;

    @GameRegistry.ObjectHolder("hurt_spore")
    public static final SoundEvent ENTITY_HURT_SPORE = null;

    @GameRegistry.ObjectHolder("death_spore")
    public static final SoundEvent ENTITY_DEATH_SPORE = null;

    @GameRegistry.ObjectHolder("warn_spore")
    public static final SoundEvent ENTITY_WARN_SPORE = null;

    @GameRegistry.ObjectHolder("explode_spore")
    public static final SoundEvent ENTITY_EXPLODE_SPORE = null;

    @GameRegistry.ObjectHolder("ambient_ghastling")
    public static final SoundEvent ENTITY_AMBIENT_GHASTLING = null;

    @GameRegistry.ObjectHolder("hurt_ghastling")
    public static final SoundEvent ENTITY_HURT_GHASTLING = null;

    @GameRegistry.ObjectHolder("death_ghastling")
    public static final SoundEvent ENTITY_DEATH_GHASTLING = null;

    @GameRegistry.ObjectHolder("warn_ghastling")
    public static final SoundEvent ENTITY_WARN_GHASTLING = null;

    @GameRegistry.ObjectHolder("shoot_ghastling")
    public static final SoundEvent ENTITY_SHOOT_GHASTLING = null;

    @GameRegistry.ObjectHolder("ambient_ghast_queen")
    public static final SoundEvent ENTITY_AMBIENT_GHAST_QUEEN = null;

    @GameRegistry.ObjectHolder("hurt_ghast_queen")
    public static final SoundEvent ENTITY_HURT_GHAST_QUEEN = null;

    @GameRegistry.ObjectHolder("death_ghast_queen")
    public static final SoundEvent ENTITY_DEATH_GHAST_QUEEN = null;

    @GameRegistry.ObjectHolder("shoot_ghast_queen")
    public static final SoundEvent ENTITY_SHOOT_GHAST_QUEEN = null;

    @GameRegistry.ObjectHolder("summon_ghast_queen")
    public static final SoundEvent ENTITY_SUMMON_GHAST_QUEEN = null;

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExSoundEvents");

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterSounds(RegistryEvent.Register<SoundEvent> event)
        {
            LOGGER.info("Sound registration started.");

            event.getRegistry().registerAll(
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":ambient_pigtificate")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":hurt_pigtificate")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":death_pigtificate")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":ambient_mogus")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":hurt_mogus")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":death_mogus")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":ambient_salamander")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":hurt_salamander")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":death_salamander")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":ambient_wight")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":hurt_wight")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":death_wight")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":hurt_ember")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":death_ember")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":ambient_nethermite")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":hurt_nethermite")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":death_nethermite")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":ambient_spinout")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":hurt_spinout")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":death_spinout")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":hurt_spore")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":death_spore")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":warn_spore")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":explode_spore")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":ambient_ghastling")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":hurt_ghastling")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":death_ghastling")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":warn_ghastling")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":shoot_ghastling")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":ambient_ghast_queen")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":hurt_ghast_queen")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":death_ghast_queen")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":shoot_ghast_queen")),
                    new SoundEventNetherEx(new ResourceLocation(NetherEx.MOD_ID + ":summon_ghast_queen"))
            );

            LOGGER.info("Sound registration completed.");
        }
    }
}

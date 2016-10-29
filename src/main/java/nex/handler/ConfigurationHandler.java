/*
 * Copyright (C) 2016.  LogicTechCorp
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

package nex.handler;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import nex.NetherEx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.Optional;

@Mod.EventBusSubscriber
@Config(modid = NetherEx.MOD_ID, name = "NetherEx")
public class ConfigurationHandler
{
    private static Configuration configuration;

    public static Client client = new Client();
    public static BiomeHell biome_hell = new BiomeHell();

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|ConfigurationHandler");

    public static class Client
    {
        public static boolean disableNetherFog = false;
    }

    public static class BiomeHell
    {
        public static boolean generateLavaSprings = true;
        public static boolean generateFire = true;
        public static boolean generateGlowstonePass1 = true;
        public static boolean generateGlowstonePass2 = true;
        public static boolean generateBrownMushrooms = true;
        public static boolean generateRedMushrooms = true;
        public static boolean generateQuartzOre = true;
        public static boolean generateMagma = true;
        public static boolean generateLavaTraps = true;

        public static int biomeRarity = 10;
        public static int lavaSpringRarity = 8;
        public static int fireRarity = 10;
        public static int glowstonePass1Rarity = 10;
        public static int glowstonePass2Rarity = 10;
        public static int quartzOreRarity = 16;
        public static int magmaRarity = 4;
        public static int lavaTrapRarity = 16;
    }

    public static Configuration getConfiguration()
    {
        if(configuration == null)
        {
            try
            {
                MethodHandle CONFIGS = MethodHandles.lookup().unreflectGetter(ReflectionHelper.findField(ConfigManager.class, "CONFIGS"));
                Map<String, Configuration> configsMap = (Map<String, Configuration>) CONFIGS.invokeExact();

                String fileName = "NetherEx.cfg";
                Optional<Map.Entry<String, Configuration>> entryOptional = configsMap.entrySet().stream().filter(entry -> fileName.equals(new File(entry.getKey()).getName())).findFirst();

                if(entryOptional.isPresent())
                {
                    configuration = entryOptional.get().getValue();
                }
            }
            catch(Throwable ignored)
            {
            }
        }

        return configuration;
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if(event.getModID().equals(NetherEx.MOD_ID))
        {
            ConfigManager.load(NetherEx.MOD_ID, Config.Type.INSTANCE);
            LOGGER.info("Configuration has been saved.");
        }
    }
}

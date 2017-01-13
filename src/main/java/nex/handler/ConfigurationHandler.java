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

@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber
@Config(modid = NetherEx.MOD_ID, name = "NetherEx")
public class ConfigurationHandler
{
    private static Configuration configuration;

    public static Client client = new Client();
    public static BiomeHell biome_hell = new BiomeHell();
    public static BiomeRuthlessSands biome_ruthless_sands = new BiomeRuthlessSands();
    public static BiomeFungiForest biome_fungi_forest = new BiomeFungiForest();
    public static BiomeTorridWasteland biome_torrid_wasteland = new BiomeTorridWasteland();
    public static BiomeArcticAbyss biome_arctic_abyss = new BiomeArcticAbyss();
    public static Miscellaneous miscellaneous = new Miscellaneous();

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
        public static boolean generateHuts = true;

        public static int biomeRarity = 10;
        public static int lavaSpringRarity = 8;
        public static int fireRarity = 10;
        public static int glowstonePass1Rarity = 10;
        public static int glowstonePass2Rarity = 10;
        public static int quartzOreRarity = 16;
        public static int magmaRarity = 4;
        public static int lavaTrapRarity = 16;
        public static int hutRarity = 24;
    }

    public static class BiomeRuthlessSands
    {
        public static boolean generateLavaSprings = true;
        public static boolean generateGlowstonePass1 = true;
        public static boolean generateGlowstonePass2 = true;
        public static boolean generateQuartzOre = true;
        public static boolean generateLavaTraps = true;
        public static boolean generateThornstalk = true;
        public static boolean generateAltars = true;

        public static int biomeRarity = 7;
        public static int lavaSpringRarity = 8;
        public static int glowstonePass1Rarity = 10;
        public static int glowstonePass2Rarity = 10;
        public static int quartzOreRarity = 16;
        public static int lavaTrapRarity = 16;
        public static int thornstalkRarity = 10;
        public static int altarRarity = 32;
    }

    public static class BiomeFungiForest
    {
        public static boolean generateGlowstonePass1 = true;
        public static boolean generateGlowstonePass2 = true;
        public static boolean generateQuartzOre = true;
        public static boolean generateElderMushrooms = true;
        public static boolean generateEnokiMushrooms = true;

        public static int biomeRarity = 5;
        public static int glowstonePass1Rarity = 10;
        public static int glowstonePass2Rarity = 10;
        public static int quartzOreRarity = 16;
        public static int elderMushroomRarity = 16;
        public static int enokiMushroomRarity = 4;
    }

    public static class BiomeTorridWasteland
    {
        public static boolean generateLavaSprings = true;
        public static boolean generateFire = true;
        public static boolean generateGlowstonePass1 = true;
        public static boolean generateGlowstonePass2 = true;
        public static boolean generateQuartzOre = true;
        public static boolean generateBasalt = true;
        public static boolean generateMagma = true;
        public static boolean generateLavaTraps = true;
        public static boolean generateLavaPits = true;
        public static boolean generatePyramids = true;

        public static int biomeRarity = 4;
        public static int lavaSpringRarity = 24;
        public static int fireRarity = 30;
        public static int glowstonePass1Rarity = 10;
        public static int glowstonePass2Rarity = 10;
        public static int quartzOreRarity = 16;
        public static int basaltRarity = 12;
        public static int magmaRarity = 12;
        public static int lavaTrapRarity = 48;
        public static int lavaPitRarity = 8;
        public static int pyramidRarity = 8;
    }

    public static class BiomeArcticAbyss
    {
        public static boolean generateGlowstonePass1 = true;
        public static boolean generateGlowstonePass2 = true;
        public static boolean generateQuartzOre = true;
        public static boolean generateIchor = true;

        public static int biomeRarity = 1;
        public static int glowstonePass1Rarity = 10;
        public static int glowstonePass2Rarity = 10;
        public static int quartzOreRarity = 16;
        public static int ichorPitRarity = 1;
    }

    public static class Miscellaneous
    {
        public static boolean generateSoulSand = false;
        public static boolean generateGravel = false;
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

                entryOptional.ifPresent(stringConfigurationEntry -> configuration = stringConfigurationEntry.getValue());
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

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
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nex.NetherEx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber
@Config(modid = NetherEx.MOD_ID, name = "NetherEx")
public class ConfigHandler
{
    public static Client client = new Client();
    public static Dimension dimension = new Dimension();
    public static Feature feature = new Feature();
    public static Biome biome = new Biome();

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|ConfigHandler");

    public static class Client
    {
        public static Graphics graphics = new Graphics();

        public static class Graphics
        {
            public static boolean disableNetherFog = false;
        }
    }

    public static class Dimension
    {
        public static Nether nether = new Nether();

        public static class Nether
        {
            public static boolean generateSoulSand = false;
            public static boolean generateGravel = false;
            public static boolean isLavaInfinite = false;
            public static boolean enablePortalFix = true;
        }
    }

    public static class Feature
    {
        public static Farming farming = new Farming();
        public static Misc misc = new Misc();

        public static class Farming
        {
            public static boolean doesNetherwartUseNewGrowthSystem = true;
            public static boolean allowAllHoesToTillSoulSand = false;
            public static boolean doesTilledSoulSandRequireIchor = true;
        }

        public static class Misc
        {
            public static boolean turnMagmaIntoLava = false;
        }
    }

    public static class Biome
    {
        public static Hell hell = new Hell();
        public static RuthlessSands ruthless_sands = new RuthlessSands();
        public static FungiForest fungi_forest = new FungiForest();
        public static TorridWasteland torrid_wasteland = new TorridWasteland();
        public static ArcticAbyss arctic_abyss = new ArcticAbyss();

        public static class Hell
        {
            public static boolean generateBiome = true;
            public static boolean generateLavaSprings = true;
            public static boolean generateFire = true;
            public static boolean generateGlowstonePass1 = true;
            public static boolean generateGlowstonePass2 = true;
            public static boolean generateBrownMushrooms = true;
            public static boolean generateRedMushrooms = true;
            public static boolean generateQuartzOre = true;
            public static boolean generateMagma = true;
            public static boolean generateLavaTraps = true;
            public static boolean generateVillages = true;
            public static boolean generateGraves = true;

            public static int biomeRarity = 10;
            public static int lavaSpringRarity = 8;
            public static int fireRarity = 10;
            public static int glowstonePass1Rarity = 10;
            public static int glowstonePass2Rarity = 10;
            public static int quartzOreRarity = 16;
            public static int magmaRarity = 4;
            public static int lavaTrapRarity = 16;
            public static int villageRarity = 1;
            public static int graveRarity = 24;
        }

        public static class RuthlessSands
        {
            public static boolean generateBiome = true;
            public static boolean generateLavaSprings = true;
            public static boolean generateGlowstonePass1 = true;
            public static boolean generateGlowstonePass2 = true;
            public static boolean generateQuartzOre = true;
            public static boolean generateLavaTraps = true;
            public static boolean generateThornstalk = true;
            public static boolean generateAncientAltars = true;

            public static int biomeRarity = 8;
            public static int lavaSpringRarity = 8;
            public static int glowstonePass1Rarity = 10;
            public static int glowstonePass2Rarity = 10;
            public static int quartzOreRarity = 16;
            public static int lavaTrapRarity = 16;
            public static int thornstalkRarity = 10;
            public static int ancientAltarRarity = 40;
        }

        public static class FungiForest
        {
            public static boolean generateBiome = true;
            public static boolean generateGlowstonePass1 = true;
            public static boolean generateGlowstonePass2 = true;
            public static boolean generateQuartzOre = true;
            public static boolean generateElderMushrooms = true;
            public static boolean generateEnokiMushrooms = true;

            public static int biomeRarity = 4;
            public static int glowstonePass1Rarity = 10;
            public static int glowstonePass2Rarity = 10;
            public static int quartzOreRarity = 16;
            public static int elderMushroomRarity = 32;
            public static int enokiMushroomRarity = 4;
        }

        public static class TorridWasteland
        {
            public static boolean generateBiome = true;
            public static boolean generateLavaSprings = true;
            public static boolean generateFire = true;
            public static boolean generateGlowstonePass1 = true;
            public static boolean generateGlowstonePass2 = true;
            public static boolean generateQuartzOre = true;
            public static boolean generateBasalt = true;
            public static boolean generateMagma = true;
            public static boolean generateLavaTraps = true;
            public static boolean generateLavaPits = true;
            public static boolean generateBlazingPyramids = true;

            public static int biomeRarity = 6;
            public static int lavaSpringRarity = 24;
            public static int fireRarity = 32;
            public static int glowstonePass1Rarity = 10;
            public static int glowstonePass2Rarity = 10;
            public static int quartzOreRarity = 16;
            public static int basaltRarity = 12;
            public static int magmaRarity = 12;
            public static int lavaTrapRarity = 48;
            public static int lavaPitRarity = 8;
            public static int blazingPyramidRarity = 4;
        }

        public static class ArcticAbyss
        {
            public static boolean generateBiome = true;
            public static boolean generateGlowstonePass1 = true;
            public static boolean generateGlowstonePass2 = true;
            public static boolean generateQuartzOre = true;
            public static boolean generateRimeOre = true;
            public static boolean generateIchorPits = true;

            public static int biomeRarity = 2;
            public static int glowstonePass1Rarity = 10;
            public static int glowstonePass2Rarity = 10;
            public static int quartzOreRarity = 16;
            public static int rimeOreRarity = 16;
            public static int ichorPitRarity = 16;
        }
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if(event.getModID().equals(NetherEx.MOD_ID))
        {
            ConfigManager.load(NetherEx.MOD_ID, Config.Type.INSTANCE);
            LOGGER.info("Configuration saved.");
        }
    }
}

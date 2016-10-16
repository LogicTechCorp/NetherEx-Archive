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

import com.google.common.collect.Lists;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nex.NetherEx;
import nex.Settings;

import java.io.File;
import java.util.List;

@Mod.EventBusSubscriber
public class ConfigurationHandler
{
    private static Configuration config;

    public static void init(File file)
    {
        if(config == null)
        {
            config = new Configuration(file, true);
            syncConfig(true);
        }
    }

    private static void syncConfig(boolean load)
    {
        List<String> properties = Lists.newArrayList();

        if(load)
        {
            config.load();
        }

        Property prop;

        /* Client Settings */
        prop = config.get(Settings.CATEGORY_CLIENT_GRAPHICS, "renderNetherFog", true);
        prop.setLanguageKey("configGuiSettings.nex:clientGraphics.renderNetherFog");
        properties.add(prop.getName());

        config.setCategoryPropertyOrder(Settings.CATEGORY_CLIENT_GRAPHICS, properties);
        properties = Lists.newArrayList();

        /* Block Settings */
        prop = config.get(Settings.CATEGORY_BLOCK_PROPERTIES, "doesMyceliumSpread", true);
        prop.setLanguageKey("configGuiSettings.nex:blockProperties.doesMyceliumSpread");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BLOCK_PROPERTIES, "doesRimeFreezeWater", true);
        prop.setLanguageKey("configGuiSettings.nex:blockProperties.doesRimeFreezeWater");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BLOCK_PROPERTIES, "doesRimeFreezeLava", true);
        prop.setLanguageKey("configGuiSettings.nex:blockProperties.doesRimeFreezeLava");
        properties.add(prop.getName());

        config.setCategoryPropertyOrder(Settings.CATEGORY_BLOCK_PROPERTIES, properties);
        properties = Lists.newArrayList();


        /* Nether Settings */
        prop = config.get(Settings.CATEGORY_NETHER_DIMENSION, "generateGravel", false);
        prop.setLanguageKey("configGuiSettings.nex:netherDimension.generateGravel");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_NETHER_DIMENSION, "generateSoulSand", false);
        prop.setLanguageKey("configGuiSettings.nex:netherDimension.generateSoulSand");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_NETHER_DIMENSION, "generateTallNether", false);
        prop.setLanguageKey("configGuiSettings.nex:netherDimension.generateTallNether");
        properties.add(prop.getName());

        config.setCategoryPropertyOrder(Settings.CATEGORY_NETHER_DIMENSION, properties);
        properties = Lists.newArrayList();


        /* Hell Biome Settings */
        prop = config.get(Settings.CATEGORY_BIOME_HELL, "biomeWeight", 10);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHell.biomeWeight");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "generateLavaSprings", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHell.generateLavaSprings");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "lavaSpringRarity", 8);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHell.lavaSpringRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "generateFire", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHell.generateFire");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "fireRarity", 10);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHell.fireRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "generateGlowstone", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHell.generateGlowstone");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "glowstoneRarity", 10);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHell.glowstoneRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "generateMushrooms", false);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHell.generateMushrooms");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "mushroomRarity", 1);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHell.mushroomRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "generateQuartzOre", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHell.generateQuartzOre");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "quartzOreRarity", 16);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHell.quartzOreRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "generateMagma", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHell.generateMagma");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "magmaRarity", 4);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHell.magmaRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "generateLavaTraps", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHell.generateLavaTraps");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "lavaTrapRarity", 16);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHell.lavaTrapRarity");
        properties.add(prop.getName());

        config.setCategoryPropertyOrder(Settings.CATEGORY_BIOME_HELL, properties);
        properties = Lists.newArrayList();

        /* Ruthless Sands Biome Settings */
        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "biomeWeight", 4);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeRuthlessSands.biomeWeight");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "generateLavaSprings", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeRuthlessSands.generateLavaSprings");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "lavaSpringRarity", 16);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeRuthlessSands.lavaSpringRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "generateFire", false);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeRuthlessSands.generateFire");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "fireRarity", 10);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeRuthlessSands.fireRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "generateGlowstone", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeRuthlessSands.generateGlowstone");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "glowstoneRarity", 10);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeRuthlessSands.glowstoneRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "generateMushrooms", false);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeRuthlessSands.generateMushrooms");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "mushroomRarity", 1);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeRuthlessSands.mushroomRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "generateThornBushes", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeRuthlessSands.generateThornBushes");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "thornBushRarity", 64);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeRuthlessSands.thornBushRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "generateQuartzOre", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeRuthlessSands.generateQuartzOre");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "quartzOreRarity", 16);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeRuthlessSands.quartzOreRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "generateMagma", false);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeRuthlessSands.generateMagma");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "magmaRarity", 4);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeRuthlessSands.magmaRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "generateLavaTraps", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeRuthlessSands.generateLavaTraps");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "lavaTrapRarity", 32);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeRuthlessSands.lavaTrapRarity");
        properties.add(prop.getName());

        config.setCategoryPropertyOrder(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, properties);
        properties = Lists.newArrayList();

        /* Mushroom Grove Biome Settings */
        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "biomeWeight", 5);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeMushroomGrove.biomeWeight");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "generateLavaSprings", false);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeMushroomGrove.generateLavaSprings");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "lavaSpringRarity", 16);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeMushroomGrove.lavaSpringRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "generateFire", false);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeMushroomGrove.generateFire");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "fireRarity", 10);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeMushroomGrove.fireRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "generateGlowstone", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeMushroomGrove.generateGlowstone");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "glowstoneRarity", 10);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeMushroomGrove.glowstoneRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "generateFungalRoots", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeMushroomGrove.generateFungalRoots");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "fungalRootRarity", 8);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeMushroomGrove.fungalRootRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "generateEnoki", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeMushroomGrove.generateEnoki");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "enokiRarity", 8);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeMushroomGrove.enokiRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "generateBigMushrooms", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeMushroomGrove.generateBigMushrooms");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "bigMushroomRarity", 512);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeMushroomGrove.bigMushroomRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "generateMushrooms", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeMushroomGrove.generateMushrooms");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "mushroomRarity", 32);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeMushroomGrove.mushroomRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "generateQuartzOre", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeMushroomGrove.generateQuartzOre");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "quartzOreRarity", 16);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeMushroomGrove.quartzOreRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "generateMagma", false);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeMushroomGrove.generateMagma");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "magmaRarity", 4);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeMushroomGrove.magmaRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "generateLavaTraps", false);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeMushroomGrove.generateLavaTraps");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "lavaTrapRarity", 32);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeMushroomGrove.lavaTrapRarity");
        properties.add(prop.getName());

        config.setCategoryPropertyOrder(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, properties);
        properties = Lists.newArrayList();

        /* Hoar Frost Settings */
        prop = config.get(Settings.CATEGORY_BIOME_HOAR_FROST, "biomeWeight", 2);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHoarFrost.biomeWeight");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOAR_FROST, "generateLavaSprings", false);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHoarFrost.generateLavaSprings");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOAR_FROST, "lavaSpringRarity", 8);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHoarFrost.lavaSpringRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOAR_FROST, "generateFire", false);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHoarFrost.generateFire");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOAR_FROST, "fireRarity", 10);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHoarFrost.fireRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOAR_FROST, "generateGlowstone", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHoarFrost.generateGlowstone");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOAR_FROST, "glowstoneRarity", 10);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHoarFrost.glowstoneRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOAR_FROST, "generateFrost", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHoarFrost.generateFrost");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOAR_FROST, "frostRarity", 32);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHoarFrost.frostRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOAR_FROST, "generateMushrooms", false);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHoarFrost.generateMushrooms");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOAR_FROST, "mushroomRarity", 1);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHoarFrost.mushroomRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOAR_FROST, "generateRimeOre", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHoarFrost.generateRimeOre");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOAR_FROST, "rimeOreRarity", 16);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHoarFrost.rimeOreRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOAR_FROST, "generateQuartzOre", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHoarFrost.generateQuartzOre");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOAR_FROST, "quartzOreRarity", 16);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHoarFrost.quartzOreRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOAR_FROST, "generateMagma", false);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHoarFrost.generateMagma");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOAR_FROST, "magmaRarity", 4);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHoarFrost.magmaRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOAR_FROST, "generateLavaTraps", false);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHoarFrost.generateLavaTraps");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOAR_FROST, "lavaTrapRarity", 16);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHoarFrost.lavaTrapRarity");
        properties.add(prop.getName());

        config.setCategoryPropertyOrder(Settings.CATEGORY_BIOME_HOAR_FROST, properties);
        properties = Lists.newArrayList();

        /* Hot Springs Biome Settings */
        prop = config.get(Settings.CATEGORY_BIOME_HOT_SPRINGS, "biomeWeight", 7);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHotSprings.biomeWeight");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOT_SPRINGS, "generateLavaSprings", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHotSprings.generateLavaSprings");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOT_SPRINGS, "lavaSpringRarity", 64);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHotSprings.lavaSpringRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOT_SPRINGS, "generateFire", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHotSprings.generateFire");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOT_SPRINGS, "fireRarity", 16);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHotSprings.fireRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOT_SPRINGS, "generateGlowstone", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHotSprings.generateGlowstone");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOT_SPRINGS, "glowstoneRarity", 10);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHotSprings.glowstoneRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOT_SPRINGS, "generateMushrooms", false);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHotSprings.generateMushrooms");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOT_SPRINGS, "mushroomRarity", 1);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHotSprings.mushroomRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOT_SPRINGS, "generateQuartzOre", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHotSprings.generateQuartzOre");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOT_SPRINGS, "quartzOreRarity", 16);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHotSprings.quartzOreRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOT_SPRINGS, "generateLavaPools", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHotSprings.generateLavaPools");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOT_SPRINGS, "lavaPoolRarity", 8);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHotSprings.lavaPoolRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOT_SPRINGS, "generateMagma", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHotSprings.generateMagma");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOT_SPRINGS, "magmaRarity", 8);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHotSprings.magmaRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOT_SPRINGS, "generateLavaTraps", true);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHotSprings.generateLavaTraps");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HOT_SPRINGS, "lavaTrapRarity", 32);
        prop.setLanguageKey("configGuiSettings.nex:netherBiomeHotSprings.lavaTrapRarity");
        properties.add(prop.getName());

        config.setCategoryPropertyOrder(Settings.CATEGORY_BIOME_HOT_SPRINGS, properties);

        if(config.hasChanged())
        {
            config.save();
        }
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent event)
    {
        if(event.getModID().equalsIgnoreCase(NetherEx.MOD_ID))
        {
            syncConfig(false);
        }
    }

    public static Configuration getConfig()
    {
        return config;
    }
}
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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nex.NetherEx;
import nex.Settings;

import java.io.File;
import java.util.List;

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
        prop = config.get(Settings.CATEGORY_CLIENT, "renderNetherFog", true);
        prop.setLanguageKey("configGuiSettings.nex:client.renderNetherFog");
        properties.add(prop.getName());

        config.setCategoryPropertyOrder(Settings.CATEGORY_CLIENT, properties);
        properties = Lists.newArrayList();

        /* Hell Biome Settings */
        prop = config.get(Settings.CATEGORY_BIOME_HELL, "biomeWeight", 10);
        prop.setLanguageKey("configGuiSettings.nex:biomeHell.biomeWeight");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "generateLavaSprings", true);
        prop.setLanguageKey("configGuiSettings.nex:biomeHell.generateLavaSprings");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "lavaSpringRarity", 8);
        prop.setLanguageKey("configGuiSettings.nex:biomeHell.lavaSpringRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "generateFire", true);
        prop.setLanguageKey("configGuiSettings.nex:biomeHell.generateFire");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "fireRarity", 10);
        prop.setLanguageKey("configGuiSettings.nex:biomeHell.fireRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "generateGlowstone", true);
        prop.setLanguageKey("configGuiSettings.nex:biomeHell.generateGlowstone");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "glowstoneRarity", 10);
        prop.setLanguageKey("configGuiSettings.nex:biomeHell.glowstoneRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "generateMushrooms", true);
        prop.setLanguageKey("configGuiSettings.nex:biomeHell.generateMushrooms");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "generateQuartzOre", true);
        prop.setLanguageKey("configGuiSettings.nex:biomeHell.generateQuartzOre");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "quartzOreRarity", 16);
        prop.setLanguageKey("configGuiSettings.nex:biomeHell.quartzOreRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "generateMagma", true);
        prop.setLanguageKey("configGuiSettings.nex:biomeHell.generateMagma");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "magmaRarity", 4);
        prop.setLanguageKey("configGuiSettings.nex:biomeHell.magmaRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "generateLavaTraps", true);
        prop.setLanguageKey("configGuiSettings.nex:biomeHell.generateLavaTraps");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "lavaTrapRarity", 16);
        prop.setLanguageKey("configGuiSettings.nex:biomeHell.lavaTrapRarity");
        properties.add(prop.getName());

        config.setCategoryPropertyOrder(Settings.CATEGORY_BIOME_HELL, properties);
        properties = Lists.newArrayList();

        /* Ruthless Sands Biome Settings */
        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "biomeWeight", 10);
        prop.setLanguageKey("configGuiSettings.nex:biomeRuthlessSands.biomeWeight");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "generateLavaSprings", true);
        prop.setLanguageKey("configGuiSettings.nex:biomeRuthlessSands.generateLavaSprings");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "lavaSpringRarity", 16);
        prop.setLanguageKey("configGuiSettings.nex:biomeRuthlessSands.lavaSpringRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "generateGlowstone", true);
        prop.setLanguageKey("configGuiSettings.nex:biomeRuthlessSands.generateGlowstone");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "glowstoneRarity", 10);
        prop.setLanguageKey("configGuiSettings.nex:biomeRuthlessSands.glowstoneRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "generateThornBushes", true);
        prop.setLanguageKey("configGuiSettings.nex:biomeRuthlessSands.generateThornBushes");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "thornBushRarity", 32);
        prop.setLanguageKey("configGuiSettings.nex:biomeRuthlessSands.thornBushRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "generateQuartzOre", true);
        prop.setLanguageKey("configGuiSettings.nex:biomeRuthlessSands.generateQuartzOre");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "quartzOreRarity", 16);
        prop.setLanguageKey("configGuiSettings.nex:biomeRuthlessSands.quartzOreRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "generateLavaTraps", true);
        prop.setLanguageKey("configGuiSettings.nex:biomeRuthlessSands.generateLavaTraps");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, "lavaTrapRarity", 32);
        prop.setLanguageKey("configGuiSettings.nex:biomeRuthlessSands.lavaTrapRarity");
        properties.add(prop.getName());

        config.setCategoryPropertyOrder(Settings.CATEGORY_BIOME_RUTHLESS_SANDS, properties);
        properties = Lists.newArrayList();

        /* Mushroom Grove Biome Settings */
        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "biomeWeight", 10);
        prop.setLanguageKey("configGuiSettings.nex:biomeMushroomGrove.biomeWeight");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "generateGlowstone", true);
        prop.setLanguageKey("configGuiSettings.nex:biomeMushroomGrove.generateGlowstone");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "glowstoneRarity", 10);
        prop.setLanguageKey("configGuiSettings.nex:biomeMushroomGrove.glowstoneRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "generateFungalRoots", true);
        prop.setLanguageKey("configGuiSettings.nex:biomeMushroomGrove.generateFungalRoots");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "fungalRootRarity", 8);
        prop.setLanguageKey("configGuiSettings.nex:biomeMushroomGrove.fungalRootRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "generateEnoki", true);
        prop.setLanguageKey("configGuiSettings.nex:biomeMushroomGrove.generateEnoki");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "enokiRarity", 8);
        prop.setLanguageKey("configGuiSettings.nex:biomeMushroomGrove.enokiRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "generateBigMushrooms", true);
        prop.setLanguageKey("configGuiSettings.nex:biomeMushroomGrove.generateBigMushrooms");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "bigMushroomRarity", 256);
        prop.setLanguageKey("configGuiSettings.nex:biomeMushroomGrove.bigMushroomRarity");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "generateSmallMushrooms", true);
        prop.setLanguageKey("configGuiSettings.nex:biomeMushroomGrove.generateSmallMushrooms");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "generateQuartzOre", true);
        prop.setLanguageKey("configGuiSettings.nex:biomeMushroomGrove.generateQuartzOre");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, "quartzOreRarity", 16);
        prop.setLanguageKey("configGuiSettings.nex:biomeMushroomGrove.quartzOreRarity");
        properties.add(prop.getName());

        config.setCategoryPropertyOrder(Settings.CATEGORY_BIOME_MUSHROOM_GROVE, properties);

        if(config.hasChanged())
        {
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent event)
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
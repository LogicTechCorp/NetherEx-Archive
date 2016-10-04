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

package nex;

import nex.handler.ConfigurationHandler;

public class Settings
{
    /**
     * Setting Categories
     **/
    public static final String CATEGORY_CLIENT = "client";
    public static final String CATEGORY_BIOME = "biome";
    public static final String CATEGORY_BIOME_HELL = "biome.hell";
    public static final String CATEGORY_BIOME_RUTHLESS_SANDS = "biome.ruthlessSands";
    public static final String CATEGORY_BIOME_MUSHROOM_GROVE = "biome.mushroomGrove";
    public static final String CATEGORY_BIOME_HOAR_FROST = "biome.hoarFrost";

    /**
     * Client Settings
     **/
    public static boolean renderNetherFog = getSetting(CATEGORY_CLIENT, "renderNetherFog", true);

    /**
     * Biome Settings
     **/
    public static boolean generateThornBushes = getSetting(CATEGORY_BIOME_RUTHLESS_SANDS, "generateThornBushes", true);
    public static int thornBushRarity = getSetting(CATEGORY_BIOME_RUTHLESS_SANDS, "thornBushRarity", 32);

    public static boolean generateFungalRoots = getSetting(CATEGORY_BIOME_MUSHROOM_GROVE, "generateFungalRoots", true);
    public static int fungalRootRarity = getSetting(CATEGORY_BIOME_MUSHROOM_GROVE, "fungalRootRarity", 8);
    public static boolean generateEnoki = getSetting(CATEGORY_BIOME_MUSHROOM_GROVE, "generateEnoki", true);
    public static int enokiRarity = getSetting(CATEGORY_BIOME_MUSHROOM_GROVE, "enokiRarity", 8);
    public static boolean generateBigMushrooms = getSetting(CATEGORY_BIOME_MUSHROOM_GROVE, "generateBigMushrooms", true);
    public static int bigMushroomRarity = getSetting(CATEGORY_BIOME_MUSHROOM_GROVE, "bigMushroomRarity", 256);

    public static boolean generateFrost = getSetting(CATEGORY_BIOME_HOAR_FROST, "generateFrost", true);
    public static int frostRarity = getSetting(CATEGORY_BIOME_HOAR_FROST, "frostRarity", 32);
    public static boolean generateRimeOre = getSetting(CATEGORY_BIOME_HOAR_FROST, "generateRimeOre", true);
    public static int rimeOreRarity = getSetting(CATEGORY_BIOME_HOAR_FROST, "rimeOreRarity", 16);

    public static int biomeWeight(String category)
    {
        return getSetting(category, "biomeWeight", 10);
    }

    public static boolean generateLavaSprings(String category)
    {
        return getSetting(category, "generateLavaSprings", true);
    }

    public static int lavaSpringRarity(String category)
    {
        return getSetting(category, "lavaSpringRarity", 8);
    }

    public static boolean generateFire(String category)
    {
        return getSetting(category, "generateFire", true);
    }

    public static int fireRarity(String category)
    {
        return getSetting(category, "fireRarity", 10);
    }

    public static boolean generateGlowstone(String category)
    {
        return getSetting(category, "generateGlowstone", true);
    }

    public static int glowstoneRarity(String category)
    {
        return getSetting(category, "glowstoneRarity", 10);
    }

    public static boolean generateMushrooms(String category)
    {
        return getSetting(category, "generateMushrooms", true);
    }

    public static int mushroomRarity(String category)
    {
        return getSetting(category, "mushroomRarity", 10);
    }

    public static boolean generateQuartzOre(String category)
    {
        return getSetting(category, "generateQuartzOre", true);
    }

    public static int quartzOreRarity(String category)
    {
        return getSetting(category, "quartzOreRarity", 10);
    }

    public static boolean generateMagma(String category)
    {
        return getSetting(category, "generateMagma", true);
    }

    public static int magmaRarity(String category)
    {
        return getSetting(category, "magmaRarity", 10);
    }

    public static boolean generateLavaTraps(String category)
    {
        return getSetting(category, "generateLavaTraps", true);
    }

    public static int lavaTrapRarity(String category)
    {
        return getSetting(category, "lavaTrapRarity", 10);
    }

    private static boolean getSetting(String category, String key, boolean defaultValue)
    {
        return ConfigurationHandler.getConfig().get(category, key, defaultValue).getBoolean();
    }

    private static int getSetting(String category, String key, int defaultValue)
    {
        return ConfigurationHandler.getConfig().get(category, key, defaultValue).getInt();
    }
}
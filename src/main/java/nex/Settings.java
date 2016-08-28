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

    /**
     * Client Settings
     **/
    public static class Client
    {
        public static final boolean RENDER_NETHER_FOG = getSetting(CATEGORY_CLIENT, "renderNetherFog", true);
    }

    /**
     * Hell Biome Settings
     **/
    public static class BiomeHell
    {
        public static final boolean GENERATE_LAVA_SPRINGS = getSetting(CATEGORY_BIOME_HELL, "generateLavaSprings", true);
        public static final int LAVA_SPRINGS_RARITY = getSetting(CATEGORY_BIOME_HELL, "generateLavaSprings", 8);
        public static final boolean GENERATE_FIRE = getSetting(CATEGORY_BIOME_HELL, "generateFire", true);
        public static final int FIRE_RARITY = getSetting(CATEGORY_BIOME_HELL, "fireRarity", 10);
        public static final boolean GENERATE_GLOWSTONE = getSetting(CATEGORY_BIOME_HELL, "generateGlowstone", true);
        public static final int GLOWSTONE_RARITY = getSetting(CATEGORY_BIOME_HELL, "glowstoneRarity", 10);
        public static final boolean GENERATE_MUSHROOMS = getSetting(CATEGORY_BIOME_HELL, "generateMushrooms", true);
        public static final boolean GENERATE_QUARTZ_ORE = getSetting(CATEGORY_BIOME_HELL, "generateQuartzOre", true);
        public static final int QUARTZ_ORE_RARITY = getSetting(CATEGORY_BIOME_HELL, "quartzOreRarity", 16);
        public static final boolean GENERATE_MAGMA = getSetting(CATEGORY_BIOME_HELL, "generateMagma", true);
        public static final int MAGMA_RARITY = getSetting(CATEGORY_BIOME_HELL, "magmaRarity", 4);
        public static final boolean GENERATE_LAVA_TRAPS = getSetting(CATEGORY_BIOME_HELL, "generateLavaTraps", true);
        public static final int LAVA_TRAPS_RARITY = getSetting(CATEGORY_BIOME_HELL, "generateLavaTraps", 16);
    }

    /**
     * Ruthless Sands Biome Settings
     **/
    public static class BiomeRuthlessSands
    {
        public static final boolean GENERATE_LAVA_SPRINGS = getSetting(CATEGORY_BIOME_RUTHLESS_SANDS, "generateLavaSprings", true);
        public static final int LAVA_SPRINGS_RARITY = getSetting(CATEGORY_BIOME_RUTHLESS_SANDS, "generateLavaSprings", 16);
        public static final boolean GENERATE_GLOWSTONE = getSetting(CATEGORY_BIOME_RUTHLESS_SANDS, "generateGlowstone", true);
        public static final int GLOWSTONE_RARITY = getSetting(CATEGORY_BIOME_RUTHLESS_SANDS, "glowstoneRarity", 10);
        public static final boolean GENERATE_THORN_BUSHES = getSetting(CATEGORY_BIOME_RUTHLESS_SANDS, "generateThornBushes", true);
        public static final int THORN_BUSHES_RARITY = getSetting(CATEGORY_BIOME_RUTHLESS_SANDS, "thornBushesRarity", 32);
        public static final boolean GENERATE_QUARTZ_ORE = getSetting(CATEGORY_BIOME_RUTHLESS_SANDS, "generateQuartzOre", true);
        public static final int QUARTZ_ORE_RARITY = getSetting(CATEGORY_BIOME_RUTHLESS_SANDS, "quartzOreRarity", 16);
        public static final boolean GENERATE_LAVA_TRAPS = getSetting(CATEGORY_BIOME_RUTHLESS_SANDS, "generateLavaTraps", true);
        public static final int LAVA_TRAPS_RARITY = getSetting(CATEGORY_BIOME_RUTHLESS_SANDS, "generateLavaTraps", 32);
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
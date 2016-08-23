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

    /**
     * Client Settings
     **/
    public static final boolean RENDER_NETHER_FOG = getSetting(CATEGORY_CLIENT, "renderNetherFog", true);

    /**
     * Hell Biome Settings
     **/
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

    private static boolean getSetting(String category, String key, boolean defaultValue)
    {
        return ConfigurationHandler.getConfig().get(category, key, defaultValue).getBoolean();
    }

    private static int getSetting(String category, String key, int defaultValue)
    {
        return ConfigurationHandler.getConfig().get(category, key, defaultValue).getInt();
    }
}
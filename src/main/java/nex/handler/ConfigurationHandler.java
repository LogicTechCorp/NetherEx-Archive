package nex.handler;

import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nex.NetherEx;

import java.io.File;

public class ConfigurationHandler
{
    public static Configuration configuration;

    private static final String CATEGORY_WORLD = "general.world.gen";
    private static final String CATEGORY_GELID = "general.world.biome.gelid.gen";

    public static void init(File configFile)
    {
        if(configuration == null)
        {
            configuration = new Configuration(configFile, null);
            loadConfiguration();
        }
    }

    private static void loadConfiguration()
    {
        Settings.generateNetherDungeons = configuration.getBoolean(
                Settings.GENERATE_NETHER_DUNGEONS_NAME,
                CATEGORY_WORLD,
                Settings.GENERATE_NETHER_DUNGEONS_DEFAULT,
                I18n.translateToLocal(Settings.GENERATE_NETHER_DUNGEONS_COMMENT),
                Settings.GENERATE_NETHER_DUNGEONS_LABEL
        );
        
        Settings.netherDungeonRarity = configuration.getInt(
                Settings.NETHER_DUNGEON_RARITY_NAME,
                CATEGORY_WORLD,
                Settings.NETHER_DUNGEON_RARITY_DEFAULT,
                Settings.NETHER_DUNGEON_RARITY_MIN,
                Settings.NETHER_DUNGEON_RARITY_MAX,
                I18n.translateToLocal(Settings.NETHER_DUNGEON_RARITY_COMMENT),
                Settings.NETHER_DUNGEON_RARITY_LABEL
        );

        Settings.gelidIceSpikeRarity = configuration.getInt(
                Settings.GELID_ICE_SPIKE_RARITY_NAME,
                CATEGORY_GELID,
                Settings.GELID_ICE_SPIKE_RARITY_DEFAULT,
                Settings.GELID_ICE_SPIKE_RARITY_MIN,
                Settings.GELID_ICE_SPIKE_RARITY_MAX,
                I18n.translateToLocal(Settings.GELID_ICE_SPIKE_RARITY_COMMENT),
                Settings.GELID_ICE_SPIKE_RARITY_LABEL
        );

        Settings.rimeOreRarity = configuration.getInt(
                Settings.RIME_ORE_RARITY_NAME,
                CATEGORY_GELID,
                Settings.RIME_ORE_RARITY_DEFAULT,
                Settings.RIME_ORE_RARITY_MIN,
                Settings.RIME_ORE_RARITY_MAX,
                I18n.translateToLocal(Settings.RIME_ORE_RARITY_COMMENT),
                Settings.RIME_ORE_RARITY_LABEL
        );

        Settings.rimeOreVeinSize = configuration.getInt(
                Settings.RIME_ORE_VEIN_SIZE_NAME,
                CATEGORY_GELID,
                Settings.RIME_ORE_VEIN_SIZE_DEFAULT,
                Settings.RIME_ORE_VEIN_SIZE_MIN,
                Settings.RIME_ORE_VEIN_SIZE_MAX,
                I18n.translateToLocal(Settings.RIME_ORE_VEIN_SIZE_COMMENT),
                Settings.RIME_ORE_VEIN_SIZE_LABEL
        );

        if(configuration.hasChanged())
        {
            configuration.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChanged(ConfigChangedEvent event)
    {
        if(event.getModID().equalsIgnoreCase(NetherEx.MOD_ID))
        {
            loadConfiguration();
        }
    }

    public static class Settings
    {
        public static boolean generateNetherDungeons;
        private static final String GENERATE_NETHER_DUNGEONS_NAME = "generateNetherDungeons";
        private static final boolean GENERATE_NETHER_DUNGEONS_DEFAULT = true;
        private static final String GENERATE_NETHER_DUNGEONS_COMMENT = "world.gen.generateNetherDungeons.comment";
        private static final String GENERATE_NETHER_DUNGEONS_LABEL = "world.gen.generateNetherDungeons.label";
        
        public static int netherDungeonRarity;
        private static final String NETHER_DUNGEON_RARITY_NAME = "netherDungeonRarity";
        private static final int NETHER_DUNGEON_RARITY_DEFAULT = 64;
        private static final int NETHER_DUNGEON_RARITY_MIN = 1;
        private static final int NETHER_DUNGEON_RARITY_MAX = 88;
        private static final String NETHER_DUNGEON_RARITY_COMMENT = "world.gen.netherDungeonRarity.comment";
        private static final String NETHER_DUNGEON_RARITY_LABEL = "world.gen.netherDungeonRarity.label";

        public static int gelidIceSpikeRarity;
        private static final String GELID_ICE_SPIKE_RARITY_NAME = "gelidIceSpikeRarity";
        private static final int GELID_ICE_SPIKE_RARITY_DEFAULT = 3;
        private static final int GELID_ICE_SPIKE_RARITY_MIN = 1;
        private static final int GELID_ICE_SPIKE_RARITY_MAX = 8;
        private static final String GELID_ICE_SPIKE_RARITY_COMMENT = "world.biome.gen.gelidIceSpikeRarity.comment";
        private static final String GELID_ICE_SPIKE_RARITY_LABEL = "world.biome.gen.gelidIceSpikeRarity.label";

        public static int rimeOreRarity;
        private static final String RIME_ORE_RARITY_NAME = "rimeOreRarity";
        private static final int RIME_ORE_RARITY_DEFAULT = 20;
        private static final int RIME_ORE_RARITY_MIN = 1;
        private static final int RIME_ORE_RARITY_MAX = 24;
        private static final String RIME_ORE_RARITY_COMMENT = "world.biome.gen.rimeOreRarity.comment";
        private static final String RIME_ORE_RARITY_LABEL = "world.biome.gen.rimeOreRarity.label";

        public static int rimeOreVeinSize;
        private static final String RIME_ORE_VEIN_SIZE_NAME = "rimeOreVeinSize";
        private static final int RIME_ORE_VEIN_SIZE_DEFAULT = 8;
        private static final int RIME_ORE_VEIN_SIZE_MIN = 1;
        private static final int RIME_ORE_VEIN_SIZE_MAX = 16;
        private static final String RIME_ORE_VEIN_SIZE_COMMENT = "world.biome.gen.rimeOreVeinSize.comment";
        private static final String RIME_ORE_VEIN_SIZE_LABEL = "world.biome.gen.rimeOreVeinSize.label";

    }
}

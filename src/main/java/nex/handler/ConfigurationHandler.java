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

    private static final String CATEGORY_WORLD = "general.world";

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
        private static final String GENERATE_NETHER_DUNGEONS_COMMENT = "world.generateNetherDungeons.comment";
        private static final String GENERATE_NETHER_DUNGEONS_LABEL = "world.generateNetherDungeons.label";
    }
}

package nex;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class Settings
{
    public static Configuration config;
    public static boolean assignedBiomeIds = false;

    public static void init(File file)
    {
        if(config == null)
        {
            config = new Configuration(file);
            loadConfig();
        }

        MinecraftForge.EVENT_BUS.register(Settings.class);
    }

    private static void loadConfig()
    {
        hellBiomeId = config.get("biome.ids", "Hell", -1).getInt();
        forgottenSandsBiomeId = config.get("biome.ids", "Forgotten Sands", -1).getInt();
        fungiForestBiomeId = config.get("biome.ids", "Fungi Forest", -1).getInt();
        blazingInfernoBiomeId = config.get("biome.ids", "Blazing Inferno", -1).getInt();
        freezingBlizzardBiomeId = config.get("biome.ids", "Freezing Blizzard", -1).getInt();

        if(config.hasChanged())
        {
            config.save();
        }
    }

    public static void saveNewBiomeIds()
    {
        config.get("biome.ids", "Hell", -1).set(hellBiomeId);
        config.get("biome.ids", "Forgotten Sands", -1).set(forgottenSandsBiomeId);
        config.get("biome.ids", "Fungi Forest", -1).set(fungiForestBiomeId);
        config.get("biome.ids", "Blazing Inferno", -1).set(blazingInfernoBiomeId);
        config.get("biome.ids", "Freezing Blizzard", -1).set(freezingBlizzardBiomeId);

        config.save();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent event)
    {
        if(event.getModID().equalsIgnoreCase(NetherEx.MOD_ID))
        {
            loadConfig();
        }
    }

    public static int hellBiomeId;
    public static int forgottenSandsBiomeId;
    public static int fungiForestBiomeId;
    public static int blazingInfernoBiomeId;
    public static int freezingBlizzardBiomeId;
}

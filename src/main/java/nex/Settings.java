package nex;

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
    }

    private static void loadConfig()
    {
        hellBiomeId = config.get("biome.ids", "hell", -1).getInt();

        if(config.hasChanged())
        {
            config.save();
        }
    }

    public static void saveNewBiomeIds()
    {
        config.get("biome.ids", "hell", -1).set(hellBiomeId);

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
}

package nex;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class Configuration
{
    private static net.minecraftforge.common.config.Configuration config;

    public static void init(File file)
    {
        if(config == null)
        {
            config = new net.minecraftforge.common.config.Configuration(file);
            load();
        }

        MinecraftForge.EVENT_BUS.register(Configuration.class);
    }

    private static void load()
    {
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
            load();
        }
    }
}

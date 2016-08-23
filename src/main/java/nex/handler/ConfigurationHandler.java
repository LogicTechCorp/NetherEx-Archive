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
            config = new Configuration(file);
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

        prop = config.get(Settings.CATEGORY_CLIENT, "renderNetherFog", true);
        prop.setLanguageKey("configGuiSettings.nex:renderNetherFog");
        properties.add(prop.getName());

        config.setCategoryPropertyOrder(Settings.CATEGORY_CLIENT, properties);

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
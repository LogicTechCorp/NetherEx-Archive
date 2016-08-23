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

        /* Client Settings */
        prop = config.get(Settings.CATEGORY_CLIENT, "renderNetherFog", true);
        prop.setLanguageKey("configGuiSettings.nex:client.renderNetherFog");
        properties.add(prop.getName());

        config.setCategoryPropertyOrder(Settings.CATEGORY_CLIENT, properties);

        /* Hell Biome Settings */
        prop = config.get(Settings.CATEGORY_BIOME_HELL, "generateLavaSprings", true);
        prop.setLanguageKey("configGuiSettings.nex:biomeHell.generateLavaSprings");
        properties.add(prop.getName());

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "lavaSpringsRarity", 8);
        prop.setLanguageKey("configGuiSettings.nex:biomeHell.lavaSpringsRarity");
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

        prop = config.get(Settings.CATEGORY_BIOME_HELL, "lavaTrapsRarity", 16);
        prop.setLanguageKey("configGuiSettings.nex:biomeHell.lavaTrapsRarity");
        properties.add(prop.getName());

        config.setCategoryPropertyOrder(Settings.CATEGORY_BIOME_HELL, properties);

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
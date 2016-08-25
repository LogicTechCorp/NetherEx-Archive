package nex.client.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;
import nex.NetherEx;
import nex.Settings;
import nex.handler.ConfigurationHandler;

import java.util.ArrayList;
import java.util.List;

public class ModGuiConfig extends GuiConfig
{
    public ModGuiConfig(GuiScreen screen)
    {
        super(screen, getConfigElements(), NetherEx.MOD_ID, false, false, I18n.format("configGuiTitle.nex:config"));
    }

    private static List<IConfigElement> getConfigElements()
    {
        List<IConfigElement> elements = new ArrayList<>();

        elements.add(new DummyConfigElement.DummyCategoryElement(I18n.format("configGuiTitle.nex:client"), "configGuiCategory.nex:client", ClientEntry.class));
        elements.add(new DummyConfigElement.DummyCategoryElement(I18n.format("configGuiTitle.nex:biome"), "configGuiCategory.nex:biome", BiomeEntry.class));

        return elements;
    }

    public static class ClientEntry extends GuiConfigEntries.CategoryEntry
    {
        public ClientEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop)
        {
            super(owningScreen, owningEntryList, prop);
        }

        @Override
        protected GuiScreen buildChildScreen()
        {
            List<IConfigElement> elements = new ConfigElement(ConfigurationHandler.getConfig().getCategory(Settings.CATEGORY_CLIENT)).getChildElements();
            return new GuiConfig(owningScreen, elements, NetherEx.MOD_ID, Settings.CATEGORY_CLIENT, false, false, I18n.format("configGuiTitle.nex:client"));
        }
    }

    public static class BiomeEntry extends GuiConfigEntries.CategoryEntry
    {
        public BiomeEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop)
        {
            super(owningScreen, owningEntryList, prop);
        }

        @Override
        protected GuiScreen buildChildScreen()
        {
            List<IConfigElement> elements = Lists.newArrayList();

            elements.add(new DummyConfigElement.DummyCategoryElement(I18n.format("configGuiTitle.nex:biomeHell"), "configGuiCategory.nex:biomeHell", BiomeHellEntry.class));
            elements.add(new DummyConfigElement.DummyCategoryElement(I18n.format("configGuiTitle.nex:biomeRuthlessSands"), "configGuiCategory.nex:biomeRuthlessSands", BiomeRuthlessSandsEntry.class));

            return new GuiConfig(owningScreen, elements, NetherEx.MOD_ID, Settings.CATEGORY_BIOME, false, false, I18n.format("configGuiTitle.nex:biome"));
        }
    }

    public static class BiomeHellEntry extends GuiConfigEntries.CategoryEntry
    {
        public BiomeHellEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop)
        {
            super(owningScreen, owningEntryList, prop);
        }

        @Override
        protected GuiScreen buildChildScreen()
        {
            List<IConfigElement> elements = new ConfigElement(ConfigurationHandler.getConfig().getCategory(Settings.CATEGORY_BIOME_HELL)).getChildElements();
            return new GuiConfig(owningScreen, elements, NetherEx.MOD_ID, Settings.CATEGORY_BIOME_HELL, false, false, I18n.format("configGuiTitle.nex:biomeHell"));
        }
    }

    public static class BiomeRuthlessSandsEntry extends GuiConfigEntries.CategoryEntry
    {
        public BiomeRuthlessSandsEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop)
        {
            super(owningScreen, owningEntryList, prop);
        }

        @Override
        protected GuiScreen buildChildScreen()
        {
            List<IConfigElement> elements = new ConfigElement(ConfigurationHandler.getConfig().getCategory(Settings.CATEGORY_BIOME_RUTHLESS_SANDS)).getChildElements();
            return new GuiConfig(owningScreen, elements, NetherEx.MOD_ID, Settings.CATEGORY_BIOME_RUTHLESS_SANDS, false, false, I18n.format("configGuiTitle.nex:biomeRuthlessSands"));
        }
    }
}
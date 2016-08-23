package nex.client.gui;

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

        elements.add(new DummyConfigElement.DummyCategoryElement("NetherEx Config", "configGuiCategory.nex:client", ClientEntry.class));

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
}
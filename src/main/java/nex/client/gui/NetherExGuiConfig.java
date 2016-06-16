package nex.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import nex.NetherEx;
import nex.handler.ConfigurationHandler;

public class NetherExGuiConfig extends GuiConfig
{
    public NetherExGuiConfig(GuiScreen screen)
    {
        super(screen, new ConfigElement(ConfigurationHandler.configuration.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), NetherEx.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.configuration.toString()));
    }
}

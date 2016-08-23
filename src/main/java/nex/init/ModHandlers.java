package nex.init;

import net.minecraftforge.common.MinecraftForge;
import nex.handler.ConfigurationHandler;

public class ModHandlers
{
    public static void init()
    {
        MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());
    }
}

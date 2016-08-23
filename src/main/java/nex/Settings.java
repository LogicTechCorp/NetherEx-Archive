package nex;

import nex.handler.ConfigurationHandler;

public class Settings
{
    public static final String CATEGORY_CLIENT = "client";

    public static boolean renderNetherFog()
    {
        return ConfigurationHandler.getConfig().get(CATEGORY_CLIENT, "renderNetherFog", true).getBoolean();
    }
}
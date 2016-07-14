package nex;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import nex.init.ModRegistry;
import nex.proxy.IProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = NetherEx.MOD_ID, name = NetherEx.NAME, version = NetherEx.VERSION, dependencies = NetherEx.DEPEND)
public class NetherEx
{
    public static final String MOD_ID = "nex";
    public static final String NAME = "NetherEx";
    public static final String VERSION = "@VERSION@";
    public static final String DEPEND = "required-after:Forge@[1.10.2-12.18.1.2011,);";
    private static final String CLIENT_ENV = "nex.proxy.ClientProxy";
    private static final String SERVER_ENV = "nex.proxy.ServerProxy";

    public static Logger logger = LogManager.getLogger("NetherEx");

    @Mod.Instance(MOD_ID)
    public static NetherEx instance;

    @SidedProxy(clientSide = CLIENT_ENV, serverSide = SERVER_ENV)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ModRegistry.initBiomes();
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }
}
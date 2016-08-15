package nex;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import nex.init.ModBiomes;
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
    private static final String CLIENT_PROXY = "nex.proxy.CombinedClientProxy";
    private static final String SERVER_PROXY = "nex.proxy.DedicatedServerProxy";

    @Mod.Instance(MOD_ID)
    public static NetherEx instance;

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    public static IProxy proxy;

    public static final Logger LOGGER = LogManager.getLogger("NetherEx");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ModBiomes.init();
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        Compatibility.init();
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        Compatibility.postInit();
        proxy.postInit();
    }
}
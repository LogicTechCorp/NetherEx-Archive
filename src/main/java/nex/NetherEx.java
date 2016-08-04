package nex;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import nex.proxy.IProxy;
import nex.registry.*;
import nex.world.WorldProviderNether;
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
    public static final CreativeTabs CREATIVE_TAB = new NetherExCreativeTab();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ModBlocks.register();
        ModItems.register();
        ModBiomes.register();
        ModEntities.register();

        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        DimensionManager.unregisterDimension(-1);
        DimensionType nether = DimensionType.register("Nether", "_nether", -1, WorldProviderNether.class, false);
        DimensionManager.registerDimension(-1, nether);
        LOGGER.warn("The Nether has been overridden.");

        ModOreDict.register();

        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }
}
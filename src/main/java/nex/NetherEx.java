package nex;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import nex.handler.ConfigurationHandler;
import nex.init.ModRegistry;
import nex.proxy.IProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = NetherEx.MOD_ID, name = NetherEx.NAME, version = NetherEx.VERSION, dependencies = NetherEx.DEPEND, guiFactory = NetherEx.GUI_FACTORY)
public class NetherEx
{
    public static final String MOD_ID = "nex";
    public static final String NAME = "NetherEx";
    public static final String VERSION = "@VERSION@";
    public static final String DEPEND = "required-after:Forge@[1.9.4-12.17.0.1965,);";
    public static final String GUI_FACTORY = "nex.client.gui.GuiFactory";
    private static final String CLIENT_ENV = "nex.proxy.ClientProxy";
    private static final String SERVER_ENV = "nex.proxy.ServerProxy";

    @Mod.Instance(MOD_ID)
    public static NetherEx instance;

    @SidedProxy(clientSide = CLIENT_ENV, serverSide = SERVER_ENV)
    public static IProxy proxy;

    public static Logger logger = LogManager.getLogger("NetherEx");
    public static CreativeTabs creativeTab = new CreativeTabs("netherEx")
    {
        @Override
        public Item getTabIconItem()
        {
            return Item.getItemFromBlock(Blocks.NETHERRACK);
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        ModRegistry.INSTANCE.initBlocksAndItems();
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        ModRegistry.INSTANCE.initBiomes();
        proxy.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }
}

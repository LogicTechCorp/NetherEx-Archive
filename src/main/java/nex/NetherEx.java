/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nex;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import nex.handler.IMCHandler;
import nex.init.NetherExBiomes;
import nex.init.NetherExEntities;
import nex.init.NetherExRecipes;
import nex.init.NetherExStructures;
import nex.proxy.IProxy;
import nex.util.RemapUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = NetherEx.MOD_ID, name = NetherEx.NAME, version = NetherEx.VERSION, dependencies = NetherEx.DEPENDENCIES, guiFactory = NetherEx.GUI_FACTORY, updateJSON = NetherEx.UPDATE_JSON)
public class NetherEx
{
    public static final String MOD_ID = "nex";
    public static final String NAME = "NetherEx";
    public static final String VERSION = "@VERSION@";
    public static final String DEPENDENCIES = "required-after:forge@[1.11-13.19.1.2189,);";
    public static final String GUI_FACTORY = "nex.client.gui.GuiFactory";
    public static final String UPDATE_JSON = "https://gist.github.com/LogicTechCorp/0274bc72f4359c497d490c29c1ced425";
    private static final String CLIENT_PROXY = "nex.proxy.CombinedClientProxy";
    private static final String SERVER_PROXY = "nex.proxy.DedicatedServerProxy";

    @Mod.Instance(MOD_ID)
    public static NetherEx instance;

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    public static IProxy proxy;

    public static final CreativeTabs CREATIVE_TAB = new NetherExCreativeTab();

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|Main");

    static
    {
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.EventHandler
    public void onFMLPreInitialization(FMLPreInitializationEvent event)
    {
        LOGGER.info("PreInitialization started.");

        NetherExEntities.init();
        NetherExBiomes.init();
        NetherExStructures.init();
        proxy.preInit();

        LOGGER.info("PreInitialization completed.");
    }

    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent event)
    {
        LOGGER.info("Initialization started.");

        NetherExRecipes.init();
        proxy.init();

        LOGGER.info("Initialization completed.");
    }

    @Mod.EventHandler
    public void onInterModCommunication(FMLInterModComms.IMCEvent event)
    {
        LOGGER.info("Inter Mod Compatibility started.");

        IMCHandler.routeMessages(event);

        LOGGER.info("Inter Mod Compatibility completed.");
    }

    @Mod.EventHandler
    public void onFMLPostInitialization(FMLPostInitializationEvent event)
    {
        LOGGER.info("PostInitialization started.");

        proxy.postInit();

        LOGGER.info("PostInitialization completed.");
    }

    @Mod.EventHandler
    public void onMissingMappings(FMLMissingMappingsEvent event)
    {
        LOGGER.info("Missing Mappings started.");

        RemapUtil.remap(event.get());

        LOGGER.info("Missing Mappings completed.");
    }
}

/*
 * NetherEx
 * Copyright (c) 2016-2018 by MineEx
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

import lex.api.IModData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import nex.init.*;
import nex.proxy.IProxy;
import nex.village.NetherExTradeManager;
import nex.world.biome.NetherExBiomeManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = NetherEx.MOD_ID, name = NetherEx.NAME, version = NetherEx.VERSION, dependencies = NetherEx.DEPENDENCIES, updateJSON = NetherEx.UPDATE_JSON)
public class NetherEx implements IModData
{
    public static final String MOD_ID = "nex";
    public static final String NAME = "NetherEx";
    public static final String VERSION = "@MOD_VERSION@";
    public static final String DEPENDENCIES = "required-after:forge@[1.12.2-14.23.0.2491,);";
    public static final String UPDATE_JSON = "https://raw.githubusercontent.com/LogicTechCorp/NetherEx/1.12.x/src/main/resources/assets/nex/version.json";
    private static final String CLIENT_PROXY = "nex.proxy.CombinedClientProxy";
    private static final String SERVER_PROXY = "nex.proxy.DedicatedServerProxy";

    @Mod.Instance(MOD_ID)
    public static NetherEx instance;

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    public static IProxy proxy;

    public static final CreativeTabs CREATIVE_TAB = new NetherExCreativeTab();

    public static final Logger LOGGER = LogManager.getLogger("NetherEx");

    static
    {
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.EventHandler
    public void onFMLPreInitialization(FMLPreInitializationEvent event)
    {
        LOGGER.info("PreInitialization started.");
        NetherExBiomeManager.preInit();
        NetherExTradeManager.preInit();
        proxy.preInit();
        LOGGER.info("PreInitialization completed.");
    }

    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent event)
    {
        LOGGER.info("Initialization started.");
        NetherExBiomes.init();
        NetherExEntities.init();
        NetherExRecipes.init();
        NetherExOreDict.init();
        NetherExFeatures.init();
        proxy.init();
        LOGGER.info("Initialization completed.");
    }

    @Mod.EventHandler
    public void onFMLPostInitialization(FMLPostInitializationEvent event)
    {
        LOGGER.info("PostInitialization started.");
        NetherExBiomes.postInit();
        proxy.postInit();
        LOGGER.info("PostInitialization completed.");
    }

    @Mod.EventHandler
    public void onFMLServerStarting(FMLServerStartingEvent event)
    {
        NetherExBiomeManager.setupDefaultBiomes();
        NetherExBiomeManager.setupCompatibleBiomes(event.getServer());
        NetherExBiomeManager.setupCustomBiomes();
        NetherExTradeManager.setupDefaultTrades();
        NetherExTradeManager.setupCustomTrades();
    }

    @Mod.EventHandler
    public void onFMLServerStopping(FMLServerStoppingEvent event)
    {
        NetherExBiomeManager.clearBiomes();
        NetherExTradeManager.clearTrades();
    }

    @Override
    public String getModId()
    {
        return MOD_ID;
    }

    @Override
    public CreativeTabs getCreativeTab()
    {
        return CREATIVE_TAB;
    }
}

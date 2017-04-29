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
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import nex.handler.RemapHandler;
import nex.init.*;
import nex.proxy.IProxy;
import nex.village.trade.TradeListManager;
import nex.world.biome.additional.AdditionalBiomeManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = NetherEx.MOD_ID, name = NetherEx.NAME, version = NetherEx.VERSION, dependencies = NetherEx.DEPENDENCIES, updateJSON = NetherEx.UPDATE_JSON)
public class NetherEx
{
    public static final String MOD_ID = "nex";
    public static final String NAME = "NetherEx";
    public static final String VERSION = "@VERSION@";
    public static final String DEPENDENCIES = "required-after:forge@[1.11.2-13.20.0.2282,);after:biomesoplenty;";
    public static final String UPDATE_JSON = "https://gist.github.com/LogicTechCorp/6bfffa8b9e881eceb5a76a735eabba73";
    private static final String CLIENT_PROXY = "nex.proxy.CombinedClientProxy";
    private static final String SERVER_PROXY = "nex.proxy.DedicatedServerProxy";
    public static final boolean IS_DEV_ENV = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

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
        TradeListManager.init(new File(event.getModConfigurationDirectory(), "/NetherEx/Trade Lists"));
        AdditionalBiomeManager.init(new File(event.getModConfigurationDirectory(), "/NetherEx/Biome Lists"));
        proxy.preInit();

        LOGGER.info("PreInitialization completed.");
    }

    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent event)
    {
        LOGGER.info("Initialization started.");

        NetherExRecipes.init();
        NetherExOreDict.init();
        NetherExAchievements.init();
        proxy.init();

        LOGGER.info("Initialization completed.");
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

        RemapHandler.remap(event.get());

        LOGGER.info("Missing Mappings completed.");
    }
}

/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
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

package logictechcorp.netherex;

import logictechcorp.libraryex.IModData;
import logictechcorp.libraryex.LibraryEx;
import logictechcorp.libraryex.proxy.IProxy;
import logictechcorp.libraryex.world.biome.data.BiomeDataManager;
import logictechcorp.netherex.handler.IMCHandler;
import logictechcorp.netherex.init.*;
import logictechcorp.netherex.village.PigtificateVillageManager;
import logictechcorp.netherex.world.biome.data.BiomeDataManagerNetherEx;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Mod(modid = NetherEx.MOD_ID, name = NetherEx.NAME, version = NetherEx.VERSION, dependencies = NetherEx.DEPENDENCIES)
public class NetherEx implements IModData
{
    public static final String MOD_ID = "netherex";
    public static final String NAME = "NetherEx";
    public static final String VERSION = "2.2.1";
    public static final String DEPENDENCIES = "required-after:libraryex@[1.2.0,);";

    public static final boolean BIOMES_O_PLENTY_LOADED = Loader.isModLoaded("biomesoplenty");

    @Mod.Instance(MOD_ID)
    public static NetherEx instance;

    @SidedProxy(clientSide = "logictechcorp.netherex.proxy.ClientProxy", serverSide = "logictechcorp.netherex.proxy.ServerProxy")
    public static IProxy proxy;

    private static final CreativeTabs CREATIVE_TAB = new CreativeTabs(MOD_ID)
    {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack createIcon()
        {
            return new ItemStack(Blocks.NETHERRACK);
        }
    };
    public static final BiomeDataManager BIOME_DATA_MANAGER = new BiomeDataManagerNetherEx(NAME);
    public static final PigtificateVillageManager PIGTIFICATE_VILLAGE_MANAGER = new PigtificateVillageManager();
    public static final Logger LOGGER = LogManager.getLogger(NAME);

    static
    {
        FluidRegistry.enableUniversalBucket();

        File oldConfigDirectory = new File(LibraryEx.CONFIG_DIRECTORY, NetherEx.NAME);

        try
        {
            if(oldConfigDirectory.exists() && oldConfigDirectory.getCanonicalFile().getName().equals(NetherEx.NAME))
            {
                Files.move(oldConfigDirectory.toPath(), new File(LibraryEx.CONFIG_DIRECTORY, NetherEx.NAME + "_old").toPath());
            }
        }
        catch(IOException ignored)
        {
        }
    }

    @Mod.EventHandler
    public void onFMLPreInitialization(FMLPreInitializationEvent event)
    {
        NetherExOverrides.overrideObjects();
        proxy.preInit();
    }

    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent event)
    {
        NetherExDataFixers.registerFixes();
        NetherExMaterials.registerRepairItems();
        NetherExPigtificates.registerPigtificateCareers();
        NetherExBiomes.registerBiomeTypes();

        if(NetherExConfig.dimension.nether.overrideNether)
        {
            NetherExBiomes.registerBiomeData();
        }

        NetherExRecipes.registerRecipes();
        NetherExOreDictionary.registerOres();
        NetherExCriteria.registerCriteria();
        IMCHandler.sendCompatibilityMessages();
        proxy.init();
    }

    @Mod.EventHandler
    public void onFMLPostInitialization(FMLPostInitializationEvent event)
    {
        if(NetherExConfig.dimension.nether.overrideNether)
        {
            BIOME_DATA_MANAGER.setup();
        }

        PIGTIFICATE_VILLAGE_MANAGER.setup();
        proxy.postInit();
    }

    @Mod.EventHandler
    public void onFMLServerStarting(FMLServerStartingEvent event)
    {
        if(NetherExConfig.dimension.nether.overrideNether)
        {
            NetherExOverrides.overrideNether();
        }
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

    public static ResourceLocation getResource(String name)
    {
        return new ResourceLocation(NetherEx.MOD_ID + ":" + name);
    }
}

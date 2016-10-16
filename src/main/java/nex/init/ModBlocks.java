/*
 * Copyright (C) 2016.  LogicTechCorp
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

package nex.init;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.NetherEx;
import nex.block.*;
import nex.handler.ConfigurationHandler;
import nex.item.ItemBlockVariantContainer;

import java.io.File;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class ModBlocks
{
    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":netherrack")
    public static final BlockNetherrack NETHERRACK = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":quartz_ore")
    public static final BlockQuartzOre QUARTZ_ORE = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":thorn_bush")
    public static final BlockThornBush THORN_BUSH = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":mycelium")
    public static final BlockMycelium MYCELIUM = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":fungal_roots")
    public static final BlockFungalRoots FUNGAL_ROOTS = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":enoki_cap")
    public static final BlockEnokiCap ENOKI_CAP = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":enoki_stem")
    public static final BlockEnokiStem ENOKI_STEM = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":rime_ore")
    public static final BlockRimeOre RIME_ORE = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":rime_block")
    public static final BlockRime RIME_BLOCK = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":rime_ice")
    public static final BlockRimeIce RIME_ICE = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":frost")
    public static final BlockFrost FROST = null;

    @Mod.EventBusSubscriber
    public static class EventHandler
    {
        private static File mcConfigDir = null;

        @SubscribeEvent
        public static void onRegisterBlocks(RegistryEvent.Register<Block> event)
        {
            if(FMLCommonHandler.instance().getMinecraftServerInstance() != null && FMLCommonHandler.instance().getMinecraftServerInstance().isDedicatedServer())
            {
                mcConfigDir = new File("./config");
            }
            else
            {
                mcConfigDir = new File(Minecraft.getMinecraft().mcDataDir, "config");
            }

            ConfigurationHandler.init(new File(mcConfigDir, "NetherEx.cfg"));

            event.getRegistry().registerAll(
                    new BlockNetherrack(),
                    new BlockQuartzOre(),
                    new BlockThornBush(),
                    new BlockMycelium(),
                    new BlockFungalRoots(),
                    new BlockEnokiCap(),
                    new BlockEnokiStem(),
                    new BlockRimeOre(),
                    new BlockRime(),
                    new BlockRimeIce(),
                    new BlockFrost()
            );
        }

        @SubscribeEvent
        public static void onRegisterItems(RegistryEvent.Register<Item> event)
        {
            event.getRegistry().registerAll(
                    new ItemBlockVariantContainer(NETHERRACK, NETHERRACK.getPropertyName()).setRegistryName(NETHERRACK.getRegistryName()),
                    new ItemBlockVariantContainer(QUARTZ_ORE, QUARTZ_ORE.getPropertyName()).setRegistryName(QUARTZ_ORE.getRegistryName()),
                    new ItemBlockVariantContainer(THORN_BUSH, THORN_BUSH.getPropertyName()).setRegistryName(THORN_BUSH.getRegistryName()),
                    new ItemBlockVariantContainer(MYCELIUM, MYCELIUM.getPropertyName()).setRegistryName(MYCELIUM.getRegistryName()),
                    new ItemBlockVariantContainer(FUNGAL_ROOTS, FUNGAL_ROOTS.getPropertyName()).setRegistryName(FUNGAL_ROOTS.getRegistryName()),
                    new ItemBlockVariantContainer(ENOKI_CAP, ENOKI_CAP.getPropertyName()).setRegistryName(ENOKI_CAP.getRegistryName()),
                    new ItemBlockVariantContainer(ENOKI_STEM, ENOKI_STEM.getPropertyName()).setRegistryName(ENOKI_STEM.getRegistryName()),
                    new ItemBlockVariantContainer(RIME_ORE, RIME_ORE.getPropertyName()).setRegistryName(RIME_ORE.getRegistryName()),
                    new ItemBlockVariantContainer(RIME_BLOCK, RIME_BLOCK.getPropertyName()).setRegistryName(RIME_BLOCK.getRegistryName()),
                    new ItemBlockVariantContainer(RIME_ICE, RIME_ICE.getPropertyName()).setRegistryName(RIME_ICE.getRegistryName()),
                    new ItemBlockVariantContainer(FROST, FROST.getPropertyName()).setRegistryName(FROST.getRegistryName())
            );
        }
    }
}

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
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.NetherEx;
import nex.block.BlockNetherExStone;
import nex.block.BlockNetherExStoneSlab;
import nex.item.ItemBlockNetherExStone;
import nex.item.ItemBlockNetherExStoneSlab;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExBlocks
{
    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":stone")
    public static final BlockNetherExStone STONE = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":stone_slab")
    public static final BlockNetherExStoneSlab STONE_SLAB = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":stone_slab_double")
    public static final BlockNetherExStoneSlab STONE_SLAB_DOUBLE = null;

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExBlocks");

    @Mod.EventBusSubscriber
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterBlocks(RegistryEvent.Register<Block> event)
        {
            NetherExFluids.runStaticInit();

            event.getRegistry().registerAll(
                    new BlockNetherExStone(),
                    new BlockNetherExStoneSlab(false),
                    new BlockNetherExStoneSlab(true)
            );

            LOGGER.info("Block registration has been completed.");
        }

        @SubscribeEvent
        public static void onRegisterItems(RegistryEvent.Register<Item> event)
        {
            event.getRegistry().registerAll(
                    new ItemBlockNetherExStone(),
                    new ItemBlockNetherExStoneSlab(false),
                    new ItemBlockNetherExStoneSlab(true)
            );

            LOGGER.info("ItemBlock registration has been completed.");
        }
    }
}

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
import nex.block.*;
import nex.item.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExBlocks
{
    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":log")
    public static final BlockLog LOG = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":planks")
    public static final BlockPlanks PLANKS = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":sapling")
    public static final BlockSapling SAPLING = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":leaves")
    public static final BlockLeaves LEAVES = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":block_bone")
    public static final BlockBone BLOCK_BONE = null;

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExBlocks");

    @Mod.EventBusSubscriber
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterBlocks(RegistryEvent.Register<Block> event)
        {
            event.getRegistry().registerAll(
                    new BlockLog(),
                    new BlockPlanks(),
                    new BlockSapling(),
                    new BlockLeaves(),
                    new BlockBone()
            );

            LOGGER.info("Block registration has been completed.");
        }

        @SubscribeEvent
        public static void onRegisterItems(RegistryEvent.Register<Item> event)
        {
            event.getRegistry().registerAll(
                    new ItemBlockLog(),
                    new ItemBlockPlanks(),
                    new ItemBlockSapling(),
                    new ItemBlockLeaves(),
                    new ItemBlockBone()
            );

            LOGGER.info("ItemBlock registration has been completed.");
        }
    }
}

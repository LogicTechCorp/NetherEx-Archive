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
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
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

@SuppressWarnings("ConstantConditions")
@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExBlocks
{
    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":slab_vanilla")
    public static final BlockVanillaSlab SLAB_VANILLA = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":slab_vanilla_double")
    public static final BlockVanillaSlab SLAB_VANILLA_DOUBLE = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":stairs_nether_brick_red")
    public static final BlockNetherExStairs STAIRS_RED_NETHER_BRICK = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":wall_vanilla")
    public static final BlockVanillaWall WALL_VANILLA = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":fence_vanilla")
    public static final BlockVanillaFence FENCE_VANILLA = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":fence_gate_quartz")
    public static final BlockNetherExFenceGate FENCE_GATE_QUARTZ = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":fence_gate_nether_brick")
    public static final BlockNetherExFenceGate FENCE_GATE_NETHER_BRICK = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":fence_gate_nether_brick_red")
    public static final BlockNetherExFenceGate FENCE_GATE_RED_NETHER_BRICK = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":block_basalt")
    public static final BlockBasalt BLOCK_BASALT = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":slab_basalt")
    public static final BlockBasaltSlab SLAB_BASALT = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":slab_basalt_double")
    public static final BlockBasaltSlab SLAB_BASALT_DOUBLE = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":stairs_basalt_normal")
    public static final BlockNetherExStairs STAIRS_BASALT_NORMAL = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":stairs_basalt_smooth")
    public static final BlockNetherExStairs STAIRS_BASALT_SMOOTH = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":stairs_basalt_brick")
    public static final BlockNetherExStairs STAIRS_BASALT_BRICK = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":wall_basalt")
    public static final BlockBasaltWall WALL_BASALT = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":fence_basalt")
    public static final BlockBasaltFence FENCE_BASALT = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":fence_gate_basalt_normal")
    public static final BlockNetherExFenceGate FENCE_GATE_BASALT = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":fence_gate_basalt_smooth")
    public static final BlockNetherExFenceGate FENCE_GATE_BASALT_SMOOTH = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":fence_gate_basalt_brick")
    public static final BlockNetherExFenceGate FENCE_GATE_BASALT_BRICK = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":block_netherrack")
    public static final BlockNetherrack BLOCK_NETHERRACK = null;

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExBlocks");

    @Mod.EventBusSubscriber
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterBlocks(RegistryEvent.Register<Block> event)
        {
            NetherExFluids.runStaticInit();

            event.getRegistry().registerAll(
                    new BlockVanillaSlab(false),
                    new BlockVanillaSlab(true),
                    new BlockNetherExStairs("vanilla_nether_brick_red", Blocks.RED_NETHER_BRICK.getDefaultState()),
                    new BlockVanillaWall(),
                    new BlockVanillaFence(),
                    new BlockNetherExFenceGate("vanilla_quartz", Material.ROCK),
                    new BlockNetherExFenceGate("vanilla_nether_brick", Material.ROCK),
                    new BlockNetherExFenceGate("vanilla_nether_brick_red", Material.ROCK),
                    new BlockBasalt(),
                    new BlockBasaltSlab(false),
                    new BlockBasaltSlab(true),
                    new BlockNetherExStairs("basalt_normal", Blocks.STONE.getDefaultState()),
                    new BlockNetherExStairs("basalt_smooth", Blocks.STONE.getDefaultState()),
                    new BlockNetherExStairs("basalt_brick", Blocks.STONE.getDefaultState()),
                    new BlockBasaltWall(),
                    new BlockBasaltFence(),
                    new BlockNetherExFenceGate("basalt_normal", Material.ROCK),
                    new BlockNetherExFenceGate("basalt_smooth", Material.ROCK),
                    new BlockNetherExFenceGate("basalt_brick", Material.ROCK),
                    new BlockNetherrack()
            );

            LOGGER.info("Block registration has been completed.");
        }

        @SubscribeEvent
        public static void onRegisterItems(RegistryEvent.Register<Item> event)
        {
            event.getRegistry().registerAll(
                    new ItemBlockVanillaSlab(false),
                    new ItemBlockVanillaSlab(true),
                    new ItemBlockNetherEx(STAIRS_RED_NETHER_BRICK),
                    new ItemBlockVanillaWall(),
                    new ItemBlockVanillaFence(),
                    new ItemBlockNetherEx(FENCE_GATE_QUARTZ),
                    new ItemBlockNetherEx(FENCE_GATE_NETHER_BRICK),
                    new ItemBlockNetherEx(FENCE_GATE_RED_NETHER_BRICK),
                    new ItemBlockBasalt(),
                    new ItemBlockBasaltSlab(false),
                    new ItemBlockBasaltSlab(true),
                    new ItemBlockNetherEx(STAIRS_BASALT_NORMAL),
                    new ItemBlockNetherEx(STAIRS_BASALT_SMOOTH),
                    new ItemBlockNetherEx(STAIRS_BASALT_BRICK),
                    new ItemBlockBasaltWall(),
                    new ItemBlockBasaltFence(),
                    new ItemBlockNetherEx(FENCE_GATE_BASALT),
                    new ItemBlockNetherEx(FENCE_GATE_BASALT_SMOOTH),
                    new ItemBlockNetherEx(FENCE_GATE_BASALT_BRICK),
                    new ItemBlockNetherrack()
            );

            LOGGER.info("ItemBlock registration has been completed.");
        }
    }
}

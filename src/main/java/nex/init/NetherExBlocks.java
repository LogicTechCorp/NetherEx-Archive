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
    public static final BlockBasalt BASALT = null;
    public static final BlockNetherrack NETHERRACK = null;
    public static final BlockNetherBrick NETHER_BRICK = null;
    public static final BlockNetherrackPath NETHERRACK_PATH = null;
    public static final BlockHyphae HYPHAE = null;
    public static final BlockTilledSoulSand TILLED_SOUL_SAND = null;
    public static final BlockSoulGlass SOUL_GLASS = null;
    public static final BlockSoulGlassPane SOUL_GLASS_PANE = null;
    public static final BlockGem GEM_BLOCK = null;
    public static final BlockFrostburnIce FROSTBURN_ICE = null;
    public static final BlockBoneSliver BONE_SLIVER = null;
    public static final BlockBoneChunk BONE_CHUNK = null;
    public static final BlockWornIron WORN_IRON = null;
    public static final BlockBlueFire BLUE_FIRE = null;
    public static final BlockNetherPortal NETHER_PORTAL = null;
    public static final BlockUrnOfSorrow URN_OF_SORROW = null;
    public static final BlockQuartzOre QUARTZ_ORE = null;
    public static final BlockGemOre GEM_ORE = null;
    public static final BlockThornstalk THORNSTALK = null;
    public static final BlockElderMushroom ELDER_MUSHROOM = null;
    public static final BlockElderMushroomCap ELDER_MUSHROOM_CAP = null;
    public static final BlockElderMushroomStem ELDER_MUSHROOM_STEM = null;
    public static final BlockEnokiMushroomStem ENOKI_MUSHROOM_STEM = null;
    public static final BlockEnokiMushroomCap ENOKI_MUSHROOM_CAP = null;
    public static final BlockIchor ICHOR = null;
    public static final BlockVanillaSlab VANILLA_SLAB = null;
    public static final BlockBasaltSlab BASALT_SLAB = null;
    public static final BlockNetherBrickSlab NETHER_BRICK_SLAB = null;
    public static final BlockVanillaSlab.Double VANILLA_SLAB_DOUBLE = null;
    public static final BlockBasaltSlab.Double BASALT_SLAB_DOUBLE = null;
    public static final BlockNetherBrickSlab.Double NETHER_BRICK_SLAB_DOUBLE = null;
    public static final BlockNetherExStairs RED_NETHER_BRICK_STAIRS = null;
    public static final BlockNetherExStairs BASALT_STAIRS = null;
    public static final BlockNetherExStairs BASALT_SMOOTH_STAIRS = null;
    public static final BlockNetherExStairs BASALT_BRICK_STAIRS = null;
    public static final BlockNetherExStairs BASALT_PILLAR_STAIRS = null;
    public static final BlockNetherExStairs FIERY_NETHER_BRICK_STAIRS = null;
    public static final BlockNetherExStairs ICY_NETHER_BRICK_STAIRS = null;
    public static final BlockNetherExStairs LIVELY_NETHER_BRICK_STAIRS = null;
    public static final BlockNetherExStairs GLOOMY_NETHER_BRICK_STAIRS = null;
    public static final BlockVanillaWall VANILLA_WALL = null;
    public static final BlockBasaltWall BASALT_WALL = null;
    public static final BlockNetherBrickWall NETHER_BRICK_WALL = null;
    public static final BlockVanillaFence VANILLA_FENCE = null;
    public static final BlockBasaltFence BASALT_FENCE = null;
    public static final BlockNetherBrickFence NETHER_BRICK_FENCE = null;
    public static final BlockNetherExFenceGate QUARTZ_FENCE_GATE = null;
    public static final BlockNetherExFenceGate NETHER_BRICK_FENCE_GATE = null;
    public static final BlockNetherExFenceGate RED_NETHER_BRICK_FENCE_GATE = null;
    public static final BlockNetherExFenceGate BASALT_FENCE_GATE = null;
    public static final BlockNetherExFenceGate BASALT_SMOOTH_FENCE_GATE = null;
    public static final BlockNetherExFenceGate BASALT_BRICK_FENCE_GATE = null;
    public static final BlockNetherExFenceGate BASALT_PILLAR_FENCE_GATE = null;
    public static final BlockNetherExFenceGate FIERY_NETHER_BRICK_FENCE_GATE = null;
    public static final BlockNetherExFenceGate ICY_NETHER_BRICK_FENCE_GATE = null;
    public static final BlockNetherExFenceGate LIVELY_NETHER_BRICK_FENCE_GATE = null;
    public static final BlockNetherExFenceGate GLOOMY_NETHER_BRICK_FENCE_GATE = null;

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExBlocks");

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterBlocks(RegistryEvent.Register<Block> event)
        {
            NetherExTextures.LOGGER.info("Texture registration started.");

            NetherExTextures.init();

            NetherExTextures.LOGGER.info("Texture registration completed.");
            NetherExFluids.LOGGER.info("Fluid registration started.");

            NetherExFluids.init();

            NetherExFluids.LOGGER.info("Fluid registration completed.");
            NetherExMaterials.LOGGER.info("Material registration started.");

            NetherExMaterials.init();

            NetherExMaterials.LOGGER.info("Material registration completed.");
            NetherExLootTables.LOGGER.info("Loot Table registration started.");

            NetherExLootTables.init();

            NetherExLootTables.LOGGER.info("Loot Table registration completed.");

            LOGGER.info("BlockConfig registration started.");

            event.getRegistry().registerAll(
                    new BlockBasalt(),
                    new BlockNetherrack(),
                    new BlockNetherBrick(),
                    new BlockNetherrackPath(),
                    new BlockHyphae(),
                    new BlockTilledSoulSand(),
                    new BlockSoulGlass(),
                    new BlockSoulGlassPane(),
                    new BlockGem(),
                    new BlockFrostburnIce(),
                    new BlockBoneSliver(),
                    new BlockBoneChunk(),
                    new BlockWornIron(),
                    new BlockBlueFire(),
                    new BlockNetherPortal(),
                    new BlockUrnOfSorrow(),
                    new BlockQuartzOre(),
                    new BlockGemOre(),
                    new BlockThornstalk(),
                    new BlockElderMushroom(),
                    new BlockElderMushroomCap(),
                    new BlockElderMushroomStem(),
                    new BlockEnokiMushroomStem(),
                    new BlockEnokiMushroomCap(),
                    new BlockIchor(),
                    new BlockVanillaSlab(),
                    new BlockBasaltSlab(),
                    new BlockNetherBrickSlab(),
                    new BlockVanillaSlab.Double(),
                    new BlockBasaltSlab.Double(),
                    new BlockNetherBrickSlab.Double(),
                    new BlockNetherExStairs("red_nether_brick", Blocks.RED_NETHER_BRICK.getDefaultState()),
                    new BlockNetherExStairs("basalt", Blocks.STONE.getDefaultState()),
                    new BlockNetherExStairs("basalt_smooth", Blocks.STONE.getDefaultState()),
                    new BlockNetherExStairs("basalt_brick", Blocks.STONE.getDefaultState()),
                    new BlockNetherExStairs("basalt_pillar", Blocks.STONE.getDefaultState()),
                    new BlockNetherExStairs("fiery_nether_brick", Blocks.NETHER_BRICK.getDefaultState()),
                    new BlockNetherExStairs("icy_nether_brick", Blocks.NETHER_BRICK.getDefaultState()),
                    new BlockNetherExStairs("lively_nether_brick", Blocks.NETHER_BRICK.getDefaultState()),
                    new BlockNetherExStairs("gloomy_nether_brick", Blocks.NETHER_BRICK.getDefaultState()),
                    new BlockVanillaWall(),
                    new BlockBasaltWall(),
                    new BlockNetherBrickWall(),
                    new BlockVanillaFence(),
                    new BlockBasaltFence(),
                    new BlockNetherBrickFence(),
                    new BlockNetherExFenceGate("quartz", Material.ROCK),
                    new BlockNetherExFenceGate("nether_brick", Material.ROCK),
                    new BlockNetherExFenceGate("red_nether_brick", Material.ROCK),
                    new BlockNetherExFenceGate("basalt", Material.ROCK),
                    new BlockNetherExFenceGate("basalt_smooth", Material.ROCK),
                    new BlockNetherExFenceGate("basalt_brick", Material.ROCK),
                    new BlockNetherExFenceGate("basalt_pillar", Material.ROCK),
                    new BlockNetherExFenceGate("fiery_nether_brick", Material.ROCK),
                    new BlockNetherExFenceGate("icy_nether_brick", Material.ROCK),
                    new BlockNetherExFenceGate("lively_nether_brick", Material.ROCK),
                    new BlockNetherExFenceGate("gloomy_nether_brick", Material.ROCK)
            );

            LOGGER.info("BlockConfig registration completed.");
        }

        @SubscribeEvent
        public static void onRegisterItems(RegistryEvent.Register<Item> event)
        {
            LOGGER.info("ItemBlock registration started.");

            event.getRegistry().registerAll(
                    new ItemBlockBasalt(),
                    new ItemBlockNetherrack(),
                    new ItemBlockNetherBrick(),
                    new ItemBlockNetherrackPath(),
                    new ItemBlockNetherEx(HYPHAE),
                    new ItemBlockNetherEx(TILLED_SOUL_SAND),
                    new ItemBlockNetherEx(SOUL_GLASS),
                    new ItemBlockNetherEx(SOUL_GLASS_PANE),
                    new ItemBlockGem(),
                    new ItemBlockNetherEx(FROSTBURN_ICE),
                    new ItemBlockNetherEx(BONE_SLIVER),
                    new ItemBlockNetherEx(BONE_CHUNK),
                    new ItemBlockNetherEx(WORN_IRON),
                    new ItemBlockUrnOfSorrow(),
                    new ItemBlockQuartzOre(),
                    new ItemBlockGemOre(),
                    new ItemBlockElderMushroom(),
                    new ItemBlockElderMushroomCap(),
                    new ItemBlockNetherEx(ELDER_MUSHROOM_STEM),
                    new ItemBlockNetherEx(ENOKI_MUSHROOM_STEM),
                    new ItemBlockNetherEx(ENOKI_MUSHROOM_CAP),
                    new ItemBlockThornstalk(),
                    new ItemBlockNetherEx(ICHOR),
                    new ItemBlockVanillaSlab(false),
                    new ItemBlockBasaltSlab(false),
                    new ItemBlockNetherBrickSlab(false),
                    new ItemBlockVanillaSlab(true),
                    new ItemBlockBasaltSlab(true),
                    new ItemBlockNetherBrickSlab(true),
                    new ItemBlockNetherEx(RED_NETHER_BRICK_STAIRS),
                    new ItemBlockNetherEx(FIERY_NETHER_BRICK_STAIRS),
                    new ItemBlockNetherEx(ICY_NETHER_BRICK_STAIRS),
                    new ItemBlockNetherEx(LIVELY_NETHER_BRICK_STAIRS),
                    new ItemBlockNetherEx(GLOOMY_NETHER_BRICK_STAIRS),
                    new ItemBlockNetherEx(BASALT_STAIRS),
                    new ItemBlockNetherEx(BASALT_SMOOTH_STAIRS),
                    new ItemBlockNetherEx(BASALT_BRICK_STAIRS),
                    new ItemBlockNetherEx(BASALT_PILLAR_STAIRS),
                    new ItemBlockVanillaWall(),
                    new ItemBlockBasaltWall(),
                    new ItemBlockNetherBrickWall(),
                    new ItemBlockVanillaFence(),
                    new ItemBlockBasaltFence(),
                    new ItemBlockNetherBrickFence(),
                    new ItemBlockNetherEx(QUARTZ_FENCE_GATE),
                    new ItemBlockNetherEx(NETHER_BRICK_FENCE_GATE),
                    new ItemBlockNetherEx(RED_NETHER_BRICK_FENCE_GATE),
                    new ItemBlockNetherEx(BASALT_FENCE_GATE),
                    new ItemBlockNetherEx(BASALT_SMOOTH_FENCE_GATE),
                    new ItemBlockNetherEx(BASALT_BRICK_FENCE_GATE),
                    new ItemBlockNetherEx(BASALT_PILLAR_FENCE_GATE),
                    new ItemBlockNetherEx(FIERY_NETHER_BRICK_FENCE_GATE),
                    new ItemBlockNetherEx(ICY_NETHER_BRICK_FENCE_GATE),
                    new ItemBlockNetherEx(LIVELY_NETHER_BRICK_FENCE_GATE),
                    new ItemBlockNetherEx(GLOOMY_NETHER_BRICK_FENCE_GATE)
            );

            LOGGER.info("ItemBlock registration completed.");
        }
    }
}

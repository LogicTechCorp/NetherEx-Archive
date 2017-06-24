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
    @GameRegistry.ObjectHolder("block_basalt")
    public static final BlockBasalt BLOCK_BASALT = null;

    @GameRegistry.ObjectHolder("block_netherrack")
    public static final BlockNetherrack BLOCK_NETHERRACK = null;

    @GameRegistry.ObjectHolder("block_brick_nether")
    public static final BlockNetherBrick BLOCK_BRICK_NETHER = null;

    @GameRegistry.ObjectHolder("block_netherrack_path")
    public static final BlockNetherrackPath BLOCK_NETHERRACK_PATH = null;

    @GameRegistry.ObjectHolder("block_hyphae")
    public static final BlockHyphae BLOCK_HYPHAE = null;

    @GameRegistry.ObjectHolder("block_sand_soul_tilled")
    public static final BlockTilledSoulSand BLOCK_SAND_SOUL_TILLED = null;

    @GameRegistry.ObjectHolder("block_glass_soul")
    public static final BlockSoulGlass BLOCK_GLASS_SOUL = null;

    @GameRegistry.ObjectHolder("block_glass_pane_soul")
    public static final BlockSoulGlassPane BLOCK_GLASS_PANE_SOUL = null;

    @GameRegistry.ObjectHolder("block_amethyst")
    public static final BlockAmethyst BLOCK_AMETHYST = null;

    @GameRegistry.ObjectHolder("block_rime")
    public static final BlockRime BLOCK_RIME = null;

    @GameRegistry.ObjectHolder("block_ice_frostburn")
    public static final BlockFrostburnIce BLOCK_ICE_FROSTBURN = null;

    @GameRegistry.ObjectHolder("block_bone_sliver")
    public static final BlockBoneSliver BLOCK_BONE_SLIVER = null;

    @GameRegistry.ObjectHolder("block_bone_chunk")
    public static final BlockBoneChunk BLOCK_BONE_CHUNK = null;

    @GameRegistry.ObjectHolder("block_iron_worn")
    public static final BlockWornIron BLOCK_IRON_WORN = null;

    @GameRegistry.ObjectHolder("block_fire_blue")
    public static final BlockBlueFire BLOCK_FIRE_BLUE = null;

    @GameRegistry.ObjectHolder("block_portal_nether")
    public static final BlockNetherPortal BLOCK_PORTAL_NETHER = null;

    @GameRegistry.ObjectHolder("tile_urn_sorrow")
    public static final BlockUrnOfSorrow TILE_URN_SORROW = null;

    @GameRegistry.ObjectHolder("ore_quartz")
    public static final BlockQuartzOre ORE_QUARTZ = null;

    @GameRegistry.ObjectHolder("ore_amethyst")
    public static final BlockAmethystOre ORE_AMETHYST = null;

    @GameRegistry.ObjectHolder("ore_rime")
    public static final BlockRimeOre ORE_RIME = null;

    @GameRegistry.ObjectHolder("plant_thornstalk")
    public static final BlockThornstalk PLANT_THORNSTALK = null;

    @GameRegistry.ObjectHolder("plant_mushroom_elder")
    public static final BlockElderMushroom PLANT_MUSHROOM_ELDER = null;

    @GameRegistry.ObjectHolder("plant_mushroom_elder_cap")
    public static final BlockElderMushroomCap PLANT_MUSHROOM_ELDER_CAP = null;

    @GameRegistry.ObjectHolder("plant_mushroom_elder_stem")
    public static final BlockElderMushroomStem PLANT_MUSHROOM_ELDER_STEM = null;

    @GameRegistry.ObjectHolder("plant_mushroom_enoki_stem")
    public static final BlockEnokiMushroomStem PLANT_MUSHROOM_ENOKI_STEM = null;

    @GameRegistry.ObjectHolder("plant_mushroom_enoki_cap")
    public static final BlockEnokiMushroomCap PLANT_MUSHROOM_ENOKI_CAP = null;

    @GameRegistry.ObjectHolder("fluid_ichor")
    public static final BlockIchor FLUID_ICHOR = null;

    @GameRegistry.ObjectHolder("slab_vanilla")
    public static final BlockVanillaSlab SLAB_VANILLA = null;

    @GameRegistry.ObjectHolder("slab_basalt")
    public static final BlockBasaltSlab SLAB_BASALT = null;

    @GameRegistry.ObjectHolder("slab_brick_nether")
    public static final BlockNetherBrickSlab SLAB_BRICK_NETHER = null;

    @GameRegistry.ObjectHolder("slab_vanilla_double")
    public static final BlockVanillaSlab SLAB_VANILLA_DOUBLE = null;

    @GameRegistry.ObjectHolder("slab_basalt_double")
    public static final BlockBasaltSlab SLAB_BASALT_DOUBLE = null;

    @GameRegistry.ObjectHolder("slab_brick_nether_double")
    public static final BlockNetherBrickSlab SLAB_BRICK_NETHER_DOUBLE = null;

    @GameRegistry.ObjectHolder("stairs_brick_nether_red")
    public static final BlockNetherExStairs STAIRS_RED_BRICK_NETHER = null;

    @GameRegistry.ObjectHolder("stairs_basalt_normal")
    public static final BlockNetherExStairs STAIRS_BASALT_NORMAL = null;

    @GameRegistry.ObjectHolder("stairs_basalt_smooth")
    public static final BlockNetherExStairs STAIRS_BASALT_SMOOTH = null;

    @GameRegistry.ObjectHolder("stairs_basalt_brick")
    public static final BlockNetherExStairs STAIRS_BASALT_BRICK = null;

    @GameRegistry.ObjectHolder("stairs_basalt_pillar")
    public static final BlockNetherExStairs STAIRS_BASALT_PILLAR = null;

    @GameRegistry.ObjectHolder("stairs_brick_nether_fiery")
    public static final BlockNetherExStairs STAIRS_BRICK_NETHER_FIERY = null;

    @GameRegistry.ObjectHolder("stairs_brick_nether_icy")
    public static final BlockNetherExStairs STAIRS_BRICK_NETHER_ICY = null;

    @GameRegistry.ObjectHolder("stairs_brick_nether_lively")
    public static final BlockNetherExStairs STAIRS_BRICK_NETHER_LIVELY = null;

    @GameRegistry.ObjectHolder("stairs_brick_nether_gloomy")
    public static final BlockNetherExStairs STAIRS_BRICK_NETHER_GLOOMY = null;

    @GameRegistry.ObjectHolder("wall_vanilla")
    public static final BlockVanillaWall WALL_VANILLA = null;

    @GameRegistry.ObjectHolder("wall_basalt")
    public static final BlockBasaltWall WALL_BASALT = null;

    @GameRegistry.ObjectHolder("wall_brick_nether")
    public static final BlockNetherBrickWall WALL_BRICK_NETHER = null;

    @GameRegistry.ObjectHolder("fence_vanilla")
    public static final BlockVanillaFence FENCE_VANILLA = null;

    @GameRegistry.ObjectHolder("fence_basalt")
    public static final BlockBasaltFence FENCE_BASALT = null;

    @GameRegistry.ObjectHolder("fence_brick_nether")
    public static final BlockNetherBrickFence FENCE_BRICK_NETHER = null;

    @GameRegistry.ObjectHolder("fence_gate_quartz")
    public static final BlockNetherExFenceGate FENCE_GATE_QUARTZ = null;

    @GameRegistry.ObjectHolder("fence_gate_brick_nether")
    public static final BlockNetherExFenceGate FENCE_GATE_BRICK_NETHER = null;

    @GameRegistry.ObjectHolder("fence_gate_brick_nether_red")
    public static final BlockNetherExFenceGate FENCE_GATE_RED_BRICK_NETHER = null;

    @GameRegistry.ObjectHolder("fence_gate_basalt_normal")
    public static final BlockNetherExFenceGate FENCE_GATE_BASALT = null;

    @GameRegistry.ObjectHolder("fence_gate_basalt_smooth")
    public static final BlockNetherExFenceGate FENCE_GATE_BASALT_SMOOTH = null;

    @GameRegistry.ObjectHolder("fence_gate_basalt_brick")
    public static final BlockNetherExFenceGate FENCE_GATE_BASALT_BRICK = null;

    @GameRegistry.ObjectHolder("fence_gate_basalt_pillar")
    public static final BlockNetherExFenceGate FENCE_GATE_BASALT_PILLAR = null;

    @GameRegistry.ObjectHolder("fence_gate_brick_nether_fiery")
    public static final BlockNetherExFenceGate FENCE_GATE_BRICK_NETHER_FIERY = null;

    @GameRegistry.ObjectHolder("fence_gate_brick_nether_icy")
    public static final BlockNetherExFenceGate FENCE_GATE_BRICK_NETHER_ICY = null;

    @GameRegistry.ObjectHolder("fence_gate_brick_nether_lively")
    public static final BlockNetherExFenceGate FENCE_GATE_BRICK_NETHER_LIVELY = null;

    @GameRegistry.ObjectHolder("fence_gate_brick_nether_gloomy")
    public static final BlockNetherExFenceGate FENCE_GATE_BRICK_NETHER_GLOOMY = null;

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExBlocks");

    @Mod.EventBusSubscriber
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

            LOGGER.info("Block registration started.");

            event.getRegistry().registerAll(
                    new BlockBasalt(),
                    new BlockNetherrack(),
                    new BlockNetherBrick(),
                    new BlockNetherrackPath(),
                    new BlockHyphae(),
                    new BlockTilledSoulSand(),
                    new BlockSoulGlass(),
                    new BlockSoulGlassPane(),
                    new BlockAmethyst(),
                    new BlockRime(),
                    new BlockFrostburnIce(),
                    new BlockBoneSliver(),
                    new BlockBoneChunk(),
                    new BlockWornIron(),
                    new BlockBlueFire(),
                    new BlockNetherPortal(),
                    new BlockUrnOfSorrow(),
                    new BlockQuartzOre(),
                    new BlockAmethystOre(),
                    new BlockRimeOre(),
                    new BlockThornstalk(),
                    new BlockElderMushroom(),
                    new BlockElderMushroomCap(),
                    new BlockElderMushroomStem(),
                    new BlockEnokiMushroomStem(),
                    new BlockEnokiMushroomCap(),
                    new BlockIchor(),
                    new BlockVanillaSlab(false),
                    new BlockBasaltSlab(false),
                    new BlockNetherBrickSlab(false),
                    new BlockVanillaSlab(true),
                    new BlockBasaltSlab(true),
                    new BlockNetherBrickSlab(true),
                    new BlockNetherExStairs("vanilla_brick_nether_red", Blocks.RED_NETHER_BRICK.getDefaultState()),
                    new BlockNetherExStairs("basalt_normal", Blocks.STONE.getDefaultState()),
                    new BlockNetherExStairs("basalt_smooth", Blocks.STONE.getDefaultState()),
                    new BlockNetherExStairs("basalt_brick", Blocks.STONE.getDefaultState()),
                    new BlockNetherExStairs("basalt_pillar", Blocks.STONE.getDefaultState()),
                    new BlockNetherExStairs("brickNether_fiery", Blocks.NETHER_BRICK.getDefaultState()),
                    new BlockNetherExStairs("brickNether_icy", Blocks.NETHER_BRICK.getDefaultState()),
                    new BlockNetherExStairs("brickNether_lively", Blocks.NETHER_BRICK.getDefaultState()),
                    new BlockNetherExStairs("brickNether_gloomy", Blocks.NETHER_BRICK.getDefaultState()),
                    new BlockVanillaWall(),
                    new BlockBasaltWall(),
                    new BlockNetherBrickWall(),
                    new BlockVanillaFence(),
                    new BlockBasaltFence(),
                    new BlockNetherBrickFence(),
                    new BlockNetherExFenceGate("vanilla_quartz", Material.ROCK),
                    new BlockNetherExFenceGate("vanilla_brick_nether", Material.ROCK),
                    new BlockNetherExFenceGate("vanilla_brick_nether_red", Material.ROCK),
                    new BlockNetherExFenceGate("basalt_normal", Material.ROCK),
                    new BlockNetherExFenceGate("basalt_smooth", Material.ROCK),
                    new BlockNetherExFenceGate("basalt_brick", Material.ROCK),
                    new BlockNetherExFenceGate("basalt_pillar", Material.ROCK),
                    new BlockNetherExFenceGate("brickNether_fiery", Material.ROCK),
                    new BlockNetherExFenceGate("brickNether_icy", Material.ROCK),
                    new BlockNetherExFenceGate("brickNether_lively", Material.ROCK),
                    new BlockNetherExFenceGate("brickNether_gloomy", Material.ROCK)
            );

            LOGGER.info("Block registration completed.");
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
                    new ItemBlockNetherEx(BLOCK_HYPHAE),
                    new ItemBlockNetherEx(BLOCK_SAND_SOUL_TILLED),
                    new ItemBlockNetherEx(BLOCK_GLASS_SOUL),
                    new ItemBlockNetherEx(BLOCK_GLASS_PANE_SOUL),
                    new ItemBlockNetherEx(BLOCK_AMETHYST),
                    new ItemBlockNetherEx(BLOCK_RIME),
                    new ItemBlockNetherEx(BLOCK_ICE_FROSTBURN),
                    new ItemBlockNetherEx(BLOCK_BONE_SLIVER),
                    new ItemBlockNetherEx(BLOCK_BONE_CHUNK),
                    new ItemBlockNetherEx(BLOCK_IRON_WORN),
                    new ItemBlockUrnOfSorrow(),
                    new ItemBlockQuartzOre(),
                    new ItemBlockNetherEx(ORE_AMETHYST),
                    new ItemBlockNetherEx(ORE_RIME),
                    new ItemBlockElderMushroom(),
                    new ItemBlockElderMushroomCap(),
                    new ItemBlockNetherEx(PLANT_MUSHROOM_ELDER_STEM),
                    new ItemBlockNetherEx(PLANT_MUSHROOM_ENOKI_STEM),
                    new ItemBlockNetherEx(PLANT_MUSHROOM_ENOKI_CAP),
                    new ItemBlockThornstalk(),
                    new ItemBlockNetherEx(FLUID_ICHOR),
                    new ItemBlockVanillaSlab(false),
                    new ItemBlockBasaltSlab(false),
                    new ItemBlockNetherBrickSlab(false),
                    new ItemBlockVanillaSlab(true),
                    new ItemBlockBasaltSlab(true),
                    new ItemBlockNetherBrickSlab(true),
                    new ItemBlockNetherEx(STAIRS_RED_BRICK_NETHER),
                    new ItemBlockNetherEx(STAIRS_BRICK_NETHER_FIERY),
                    new ItemBlockNetherEx(STAIRS_BRICK_NETHER_ICY),
                    new ItemBlockNetherEx(STAIRS_BRICK_NETHER_LIVELY),
                    new ItemBlockNetherEx(STAIRS_BRICK_NETHER_GLOOMY),
                    new ItemBlockNetherEx(STAIRS_BASALT_NORMAL),
                    new ItemBlockNetherEx(STAIRS_BASALT_SMOOTH),
                    new ItemBlockNetherEx(STAIRS_BASALT_BRICK),
                    new ItemBlockNetherEx(STAIRS_BASALT_PILLAR),
                    new ItemBlockVanillaWall(),
                    new ItemBlockBasaltWall(),
                    new ItemBlockNetherBrickWall(),
                    new ItemBlockVanillaFence(),
                    new ItemBlockBasaltFence(),
                    new ItemBlockNetherBrickFence(),
                    new ItemBlockNetherEx(FENCE_GATE_QUARTZ),
                    new ItemBlockNetherEx(FENCE_GATE_BRICK_NETHER),
                    new ItemBlockNetherEx(FENCE_GATE_RED_BRICK_NETHER),
                    new ItemBlockNetherEx(FENCE_GATE_BASALT),
                    new ItemBlockNetherEx(FENCE_GATE_BASALT_SMOOTH),
                    new ItemBlockNetherEx(FENCE_GATE_BASALT_BRICK),
                    new ItemBlockNetherEx(FENCE_GATE_BASALT_PILLAR),
                    new ItemBlockNetherEx(FENCE_GATE_BRICK_NETHER_FIERY),
                    new ItemBlockNetherEx(FENCE_GATE_BRICK_NETHER_ICY),
                    new ItemBlockNetherEx(FENCE_GATE_BRICK_NETHER_LIVELY),
                    new ItemBlockNetherEx(FENCE_GATE_BRICK_NETHER_GLOOMY)
            );

            LOGGER.info("ItemBlock registration completed.");
        }
    }
}

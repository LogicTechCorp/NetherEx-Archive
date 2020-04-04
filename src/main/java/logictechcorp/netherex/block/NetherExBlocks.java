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

package logictechcorp.netherex.block;

import logictechcorp.libraryex.block.*;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.world.generation.feature.NetherExFeatures;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class NetherExBlocks
{
    private static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, NetherEx.MOD_ID);
    private static final DeferredRegister<Item> ITEM_BLOCKS = new DeferredRegister<>(ForgeRegistries.ITEMS, NetherEx.MOD_ID);

    // @formatter:off
    public static final RegistryObject<Block> GLOOMY_NETHERRACK              = registerBlockWithBasicItem("gloomy_netherrack", () -> new FireSustainingBlock(NetherExBlockProperties.GLOOMY_NETHERRACK));
    public static final RegistryObject<Block> GLOOMY_NETHERRACK_PATH         = registerBlockWithBasicItem("gloomy_netherrack_path", () -> new PathBlock(() -> GLOOMY_NETHERRACK.get().getDefaultState(), NetherExBlockProperties.GLOOMY_NETHERRACK_PATH));
    public static final RegistryObject<Block> GLOOMY_NETHER_BRICKS           = registerBlockWithBasicItem("gloomy_nether_bricks", NetherExBlockProperties.GLOOMY_NETHER_BRICKS);
    public static final RegistryObject<Block> GLOOMY_NETHER_BRICK_SLAB       = registerBlockWithBasicItem("gloomy_nether_brick_slab", () -> new SlabBlock(NetherExBlockProperties.GLOOMY_NETHER_BRICK_SLAB));
    public static final RegistryObject<Block> GLOOMY_NETHER_BRICK_STAIRS     = registerBlockWithBasicItem("gloomy_nether_brick_stairs", () -> new StairsBlock(() -> GLOOMY_NETHER_BRICKS.get().getDefaultState(),NetherExBlockProperties.GLOOMY_NETHER_BRICK_STAIRS));
    public static final RegistryObject<Block> GLOOMY_NETHER_BRICK_WALL       = registerBlockWithBasicItem("gloomy_nether_brick_wall", () -> new WallBlock(NetherExBlockProperties.GLOOMY_NETHER_BRICK_WALL));
    public static final RegistryObject<Block> GLOOMY_NETHER_BRICK_FENCE      = registerBlockWithBasicItem("gloomy_nether_brick_fence", () -> new FenceBlock(NetherExBlockProperties.GLOOMY_NETHER_BRICK_FENCE));
    public static final RegistryObject<Block> GLOOMY_NETHER_BRICK_FENCE_GATE = registerBlockWithBasicItem("gloomy_nether_brick_fence_gate", () -> new FenceGateBlock(NetherExBlockProperties.GLOOMY_NETHER_BRICK_FENCE_GATE));
    public static final RegistryObject<Block> LIVELY_NETHERRACK              = registerBlockWithBasicItem("lively_netherrack", () -> new FireSustainingBlock(NetherExBlockProperties.LIVELY_NETHERRACK));
    public static final RegistryObject<Block> LIVELY_NETHERRACK_PATH         = registerBlockWithBasicItem("lively_netherrack_path", () -> new PathBlock(() -> LIVELY_NETHERRACK.get().getDefaultState(), NetherExBlockProperties.LIVELY_NETHERRACK_PATH));
    public static final RegistryObject<Block> LIVELY_NETHER_BRICKS           = registerBlockWithBasicItem("lively_nether_bricks", NetherExBlockProperties.LIVELY_NETHER_BRICKS);
    public static final RegistryObject<Block> LIVELY_NETHER_BRICK_SLAB       = registerBlockWithBasicItem("lively_nether_brick_slab", () -> new SlabBlock(NetherExBlockProperties.LIVELY_NETHER_BRICK_SLAB));
    public static final RegistryObject<Block> LIVELY_NETHER_BRICK_STAIRS     = registerBlockWithBasicItem("lively_nether_brick_stairs", () -> new StairsBlock(() -> LIVELY_NETHER_BRICKS.get().getDefaultState(), NetherExBlockProperties.LIVELY_NETHER_BRICK_STAIRS));
    public static final RegistryObject<Block> LIVELY_NETHER_BRICK_WALL       = registerBlockWithBasicItem("lively_nether_brick_wall", () -> new WallBlock(NetherExBlockProperties.LIVELY_NETHER_BRICK_WALL));
    public static final RegistryObject<Block> LIVELY_NETHER_BRICK_FENCE      = registerBlockWithBasicItem("lively_nether_brick_fence", () -> new FenceBlock(NetherExBlockProperties.LIVELY_NETHER_BRICK_FENCE));
    public static final RegistryObject<Block> LIVELY_NETHER_BRICK_FENCE_GATE = registerBlockWithBasicItem("lively_nether_brick_fence_gate", () -> new FenceGateBlock(NetherExBlockProperties.LIVELY_NETHER_BRICK_FENCE_GATE));
    public static final RegistryObject<Block> FIERY_NETHERRACK               = registerBlockWithBasicItem("fiery_netherrack", () -> new FireSustainingBlock(NetherExBlockProperties.FIERY_NETHERRACK));
    public static final RegistryObject<Block> FIERY_NETHERRACK_PATH          = registerBlockWithBasicItem("fiery_netherrack_path", () -> new PathBlock(() -> FIERY_NETHERRACK.get().getDefaultState(), NetherExBlockProperties.FIERY_NETHERRACK_PATH));
    public static final RegistryObject<Block> FIERY_NETHER_BRICKS            = registerBlockWithBasicItem("fiery_nether_bricks", NetherExBlockProperties.FIERY_NETHER_BRICKS);
    public static final RegistryObject<Block> FIERY_NETHER_BRICK_SLAB        = registerBlockWithBasicItem("fiery_nether_brick_slab", () -> new SlabBlock(NetherExBlockProperties.FIERY_NETHER_BRICK_SLAB));
    public static final RegistryObject<Block> FIERY_NETHER_BRICK_STAIRS      = registerBlockWithBasicItem("fiery_nether_brick_stairs", () -> new StairsBlock(() -> FIERY_NETHER_BRICKS.get().getDefaultState(), NetherExBlockProperties.FIERY_NETHER_BRICK_STAIRS));
    public static final RegistryObject<Block> FIERY_NETHER_BRICK_WALL        = registerBlockWithBasicItem("fiery_nether_brick_wall", () -> new WallBlock(NetherExBlockProperties.FIERY_NETHER_BRICK_WALL));
    public static final RegistryObject<Block> FIERY_NETHER_BRICK_FENCE       = registerBlockWithBasicItem("fiery_nether_brick_fence", () -> new FenceBlock(NetherExBlockProperties.FIERY_NETHER_BRICK_FENCE));
    public static final RegistryObject<Block> FIERY_NETHER_BRICK_FENCE_GATE  = registerBlockWithBasicItem("fiery_nether_brick_fence_gate", () -> new FenceGateBlock(NetherExBlockProperties.FIERY_NETHER_BRICK_FENCE_GATE));
    public static final RegistryObject<Block> ICY_NETHERRACK                 = registerBlockWithBasicItem("icy_netherrack", () -> new FireSustainingBlock(NetherExBlockProperties.ICY_NETHERRACK));
    public static final RegistryObject<Block> ICY_NETHERRACK_PATH            = registerBlockWithBasicItem("icy_netherrack_path", () -> new PathBlock(() -> ICY_NETHERRACK.get().getDefaultState(), NetherExBlockProperties.ICY_NETHERRACK_PATH));
    public static final RegistryObject<Block> ICY_NETHER_BRICKS              = registerBlockWithBasicItem("icy_nether_bricks", NetherExBlockProperties.ICY_NETHER_BRICKS);
    public static final RegistryObject<Block> ICY_NETHER_BRICK_SLAB          = registerBlockWithBasicItem("icy_nether_brick_slab", () -> new SlabBlock(NetherExBlockProperties.ICY_NETHER_BRICK_SLAB));
    public static final RegistryObject<Block> ICY_NETHER_BRICK_STAIRS        = registerBlockWithBasicItem("icy_nether_brick_stairs", () -> new StairsBlock(() -> ICY_NETHER_BRICKS.get().getDefaultState(), NetherExBlockProperties.ICY_NETHER_BRICK_STAIRS));
    public static final RegistryObject<Block> ICY_NETHER_BRICK_WALL          = registerBlockWithBasicItem("icy_nether_brick_wall", () -> new WallBlock(NetherExBlockProperties.ICY_NETHER_BRICK_WALL));
    public static final RegistryObject<Block> ICY_NETHER_BRICK_FENCE         = registerBlockWithBasicItem("icy_nether_brick_fence", () -> new FenceBlock(NetherExBlockProperties.ICY_NETHER_BRICK_FENCE));
    public static final RegistryObject<Block> ICY_NETHER_BRICK_FENCE_GATE    = registerBlockWithBasicItem("icy_nether_brick_fence_gate", () -> new FenceGateBlock(NetherExBlockProperties.ICY_NETHER_BRICK_FENCE_GATE));
    public static final RegistryObject<Block> NETHERRACK_PATH                = registerBlockWithBasicItem("netherrack_path", () -> new PathBlock(Blocks.NETHERRACK::getDefaultState, NetherExBlockProperties.NETHERRACK_PATH));
    public static final RegistryObject<Block> BASALT                         = registerBlockWithBasicItem("basalt", NetherExBlockProperties.BASALT);
    public static final RegistryObject<Block> BASALT_SLAB                    = registerBlockWithBasicItem("basalt_slab", () -> new SlabBlock(NetherExBlockProperties.BASALT_SLAB));
    public static final RegistryObject<Block> BASALT_STAIRS                  = registerBlockWithBasicItem("basalt_stairs", () -> new StairsBlock(() -> BASALT.get().getDefaultState(), NetherExBlockProperties.BASALT_STAIRS));
    public static final RegistryObject<Block> BASALT_WALL                    = registerBlockWithBasicItem("basalt_wall", () -> new WallBlock(NetherExBlockProperties.BASALT_WALL));
    public static final RegistryObject<Block> BASALT_FENCE                   = registerBlockWithBasicItem("basalt_fence", () -> new FenceBlock(NetherExBlockProperties.BASALT_FENCE));
    public static final RegistryObject<Block> BASALT_FENCE_GATE              = registerBlockWithBasicItem("basalt_fence_gate", () -> new FenceGateBlock(NetherExBlockProperties.BASALT_FENCE_GATE));
    public static final RegistryObject<Block> SMOOTH_BASALT                  = registerBlockWithBasicItem("smooth_basalt", NetherExBlockProperties.SMOOTH_BASALT);
    public static final RegistryObject<Block> SMOOTH_BASALT_SLAB             = registerBlockWithBasicItem("smooth_basalt_slab", () -> new SlabBlock(NetherExBlockProperties.SMOOTH_BASALT_SLAB));
    public static final RegistryObject<Block> SMOOTH_BASALT_STAIRS           = registerBlockWithBasicItem("smooth_basalt_stairs", () -> new StairsBlock(() -> SMOOTH_BASALT.get().getDefaultState(), NetherExBlockProperties.SMOOTH_BASALT_STAIRS));
    public static final RegistryObject<Block> SMOOTH_BASALT_WALL             = registerBlockWithBasicItem("smooth_basalt_wall", () -> new WallBlock(NetherExBlockProperties.SMOOTH_BASALT_WALL));
    public static final RegistryObject<Block> SMOOTH_BASALT_FENCE            = registerBlockWithBasicItem("smooth_basalt_fence", () -> new FenceBlock(NetherExBlockProperties.SMOOTH_BASALT_FENCE));
    public static final RegistryObject<Block> SMOOTH_BASALT_FENCE_GATE       = registerBlockWithBasicItem("smooth_basalt_fence_gate", () -> new FenceGateBlock(NetherExBlockProperties.SMOOTH_BASALT_FENCE_GATE));
    public static final RegistryObject<Block> BASALT_BRICK                   = registerBlockWithBasicItem("basalt_brick", NetherExBlockProperties.BASALT_BRICK);
    public static final RegistryObject<Block> BASALT_BRICK_SLAB              = registerBlockWithBasicItem("basalt_brick_slab", () -> new SlabBlock(NetherExBlockProperties.BASALT_BRICK_SLAB));
    public static final RegistryObject<Block> BASALT_BRICK_STAIRS            = registerBlockWithBasicItem("basalt_brick_stairs", () -> new StairsBlock(() -> BASALT_BRICK.get().getDefaultState(), NetherExBlockProperties.BASALT_BRICK_STAIRS));
    public static final RegistryObject<Block> BASALT_BRICK_WALL              = registerBlockWithBasicItem("basalt_brick_wall", () -> new WallBlock(NetherExBlockProperties.BASALT_BRICK_WALL));
    public static final RegistryObject<Block> BASALT_BRICK_FENCE             = registerBlockWithBasicItem("basalt_brick_fence", () -> new FenceBlock(NetherExBlockProperties.BASALT_BRICK_FENCE));
    public static final RegistryObject<Block> BASALT_BRICK_FENCE_GATE        = registerBlockWithBasicItem("basalt_brick_fence_gate", () -> new FenceGateBlock(NetherExBlockProperties.BASALT_BRICK_FENCE_GATE));
    public static final RegistryObject<Block> BASALT_PILLAR                  = registerBlockWithBasicItem("basalt_pillar", NetherExBlockProperties.BASALT_PILLAR);
    public static final RegistryObject<Block> BASALT_PILLAR_SLAB             = registerBlockWithBasicItem("basalt_pillar_slab", () -> new SlabBlock(NetherExBlockProperties.BASALT_PILLAR_SLAB));
    public static final RegistryObject<Block> BASALT_PILLAR_STAIRS           = registerBlockWithBasicItem("basalt_pillar_stairs", () -> new StairsBlock(() -> BASALT_PILLAR.get().getDefaultState(), NetherExBlockProperties.BASALT_PILLAR_STAIRS));
    public static final RegistryObject<Block> BASALT_PILLAR_WALL             = registerBlockWithBasicItem("basalt_pillar_wall", () -> new WallBlock(NetherExBlockProperties.BASALT_PILLAR_WALL));
    public static final RegistryObject<Block> BASALT_PILLAR_FENCE            = registerBlockWithBasicItem("basalt_pillar_fence", () -> new FenceBlock(NetherExBlockProperties.BASALT_PILLAR_FENCE));
    public static final RegistryObject<Block> BASALT_PILLAR_FENCE_GATE       = registerBlockWithBasicItem("basalt_pillar_fence_gate", () -> new FenceGateBlock(NetherExBlockProperties.BASALT_PILLAR_FENCE_GATE));
    public static final RegistryObject<Block> HYPHAE                         = registerBlockWithBasicItem("hyphae", HyphaeBlock::new);
    public static final RegistryObject<Block> FROSTBURN_ICE                  = registerBlockWithBasicItem("frostburn_ice", () -> new UnmeltableIceBlock(NetherExBlockProperties.FROSTBURN_ICE));
    public static final RegistryObject<Block> QUARTZ_ORE                     = registerBlockWithBasicItem("quartz_ore", QuartzOreBlock::new);
    public static final RegistryObject<Block> RIME_ORE                       = registerBlockWithBasicItem("rime_ore", () -> new ExperienceDroppingBlock(2, 6, NetherExBlockProperties.RIME_ORE));
    public static final RegistryObject<Block> RIME_BLOCK                     = registerBlockWithBasicItem("rime_block", () -> new RimeBlock(NetherExBlockProperties.RIME_BLOCK));
    public static final RegistryObject<Block> THORNSTALK                     = registerBlockWithBasicItem("thornstalk", ThornstalkBlock::new);
    public static final RegistryObject<Block> BROWN_ELDER_MUSHROOM           = registerBlockWithBasicItem("brown_elder_mushroom", () -> new ModMushroomBlock(NetherExFeatures.BROWN_ELDER_MUSHROOM, NetherExBlockProperties.BROWN_ELDER_MUSHROOM));
    public static final RegistryObject<Block> RED_ELDER_MUSHROOM             = registerBlockWithBasicItem("red_elder_mushroom", () -> new ModMushroomBlock(NetherExFeatures.RED_ELDER_MUSHROOM, NetherExBlockProperties.RED_ELDER_MUSHROOM));
    public static final RegistryObject<Block> BROWN_ELDER_MUSHROOM_CAP       = registerBlockWithBasicItem("brown_elder_mushroom_cap", () -> new HugeMushroomBlock(NetherExBlockProperties.BROWN_ELDER_MUSHROOM_CAP));
    public static final RegistryObject<Block> RED_ELDER_MUSHROOM_CAP         = registerBlockWithBasicItem("red_elder_mushroom_cap", () -> new HugeMushroomBlock(NetherExBlockProperties.RED_ELDER_MUSHROOM_CAP));
    public static final RegistryObject<Block> ELDER_MUSHROOM_STEM            = registerBlockWithBasicItem("elder_mushroom_stem", () -> new HugeMushroomBlock(NetherExBlockProperties.ELDER_MUSHROOM_STEM));
    public static final RegistryObject<Block> ENOKI_MUSHROOM_CAP             = registerBlockWithBasicItem("enoki_mushroom_cap", () -> new EnokiCapBlock(NetherExBlockProperties.ENOKI_MUSHROOM_CAP));
    public static final RegistryObject<Block> ENOKI_MUSHROOM_STEM            = registerBlockWithBasicItem("enoki_mushroom_stem", () -> new EnokiStemBlock(NetherExBlockProperties.ENOKI_MUSHROOM_STEM));
    public static final RegistryObject<Block> BLUE_FIRE                      = registerBlock("blue_fire", () -> new BlueFireBlock(NetherExBlockProperties.BLUE_FIRE));
    public static final RegistryObject<Block> SOUL_GLASS                     = registerBlockWithBasicItem("soul_glass", () -> new SoulGlassBlock(NetherExBlockProperties.SOUL_GLASS));
    public static final RegistryObject<Block> SOUL_GLASS_PANE                = registerBlockWithBasicItem("soul_glass_pane", () -> new SoulGlassPaneBlock(NetherExBlockProperties.SOUL_GLASS_PANE));
    // @formatter:on

    public static void register(IEventBus modEventBus)
    {
        BLOCKS.register(modEventBus);
        ITEM_BLOCKS.register(modEventBus);
    }

    private static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<B> supplier)
    {
        return BLOCKS.register(name, supplier);
    }

    private static RegistryObject<Block> registerBlockWithBasicItem(String name, Block.Properties properties, ItemGroup group)
    {
        RegistryObject<Block> registryObject = registerBlock(name, () -> new Block(properties));
        ITEM_BLOCKS.register(name, () -> new BlockItem(registryObject.get(), new Item.Properties().group(group)));
        return registryObject;
    }

    private static RegistryObject<Block> registerBlockWithBasicItem(String name, Block.Properties properties)
    {
        return registerBlockWithBasicItem(name, properties, NetherEx.ITEM_GROUP);
    }

    private static <B extends Block> RegistryObject<B> registerBlockWithBasicItem(String name, Supplier<B> supplier, ItemGroup group)
    {
        RegistryObject<B> registryObject = registerBlock(name, supplier);
        ITEM_BLOCKS.register(name, () -> new BlockItem(registryObject.get(), new Item.Properties().group(group)));
        return registryObject;
    }

    private static <B extends Block> RegistryObject<B> registerBlockWithBasicItem(String name, Supplier<B> supplier)
    {
        return registerBlockWithBasicItem(name, supplier, NetherEx.ITEM_GROUP);
    }
}

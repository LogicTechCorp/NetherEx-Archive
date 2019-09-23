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

import logictechcorp.libraryex.block.ExperienceDroppingBlock;
import logictechcorp.libraryex.block.FireSustainingBlock;
import logictechcorp.libraryex.block.PathBlock;
import logictechcorp.libraryex.block.UnmeltableIceBlock;
import logictechcorp.libraryex.utility.InjectionHelper;
import logictechcorp.netherex.NetherEx;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import java.util.HashSet;
import java.util.Set;

@ObjectHolder(NetherEx.MOD_ID)
@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetherExBlocks
{
    private static final Set<Item> BLOCK_ITEMS = new HashSet<>();

    public static final Block GLOOMY_NETHERRACK = InjectionHelper.nullValue();
    public static final Block GLOOMY_NETHERRACK_PATH = InjectionHelper.nullValue();
    public static final Block GLOOMY_NETHER_BRICKS = InjectionHelper.nullValue();
    public static final Block GLOOMY_NETHER_BRICK_SLAB = InjectionHelper.nullValue();
    public static final Block GLOOMY_NETHER_BRICK_STAIRS = InjectionHelper.nullValue();
    public static final Block GLOOMY_NETHER_BRICK_WALL = InjectionHelper.nullValue();
    public static final Block GLOOMY_NETHER_BRICK_FENCE = InjectionHelper.nullValue();
    public static final Block GLOOMY_NETHER_BRICK_FENCE_GATE = InjectionHelper.nullValue();
    public static final Block LIVELY_NETHERRACK = InjectionHelper.nullValue();
    public static final Block LIVELY_NETHERRACK_PATH = InjectionHelper.nullValue();
    public static final Block LIVELY_NETHER_BRICKS = InjectionHelper.nullValue();
    public static final Block LIVELY_NETHER_BRICK_SLAB = InjectionHelper.nullValue();
    public static final Block LIVELY_NETHER_BRICK_STAIRS = InjectionHelper.nullValue();
    public static final Block LIVELY_NETHER_BRICK_WALL = InjectionHelper.nullValue();
    public static final Block LIVELY_NETHER_BRICK_FENCE = InjectionHelper.nullValue();
    public static final Block LIVELY_NETHER_BRICK_FENCE_GATE = InjectionHelper.nullValue();
    public static final Block FIERY_NETHERRACK = InjectionHelper.nullValue();
    public static final Block FIERY_NETHERRACK_PATH = InjectionHelper.nullValue();
    public static final Block FIERY_NETHER_BRICKS = InjectionHelper.nullValue();
    public static final Block FIERY_NETHER_BRICK_SLAB = InjectionHelper.nullValue();
    public static final Block FIERY_NETHER_BRICK_STAIRS = InjectionHelper.nullValue();
    public static final Block FIERY_NETHER_BRICK_WALL = InjectionHelper.nullValue();
    public static final Block FIERY_NETHER_BRICK_FENCE = InjectionHelper.nullValue();
    public static final Block FIERY_NETHER_BRICK_FENCE_GATE = InjectionHelper.nullValue();
    public static final Block ICY_NETHERRACK = InjectionHelper.nullValue();
    public static final Block ICY_NETHERRACK_PATH = InjectionHelper.nullValue();
    public static final Block ICY_NETHER_BRICKS = InjectionHelper.nullValue();
    public static final Block ICY_NETHER_BRICK_SLAB = InjectionHelper.nullValue();
    public static final Block ICY_NETHER_BRICK_STAIRS = InjectionHelper.nullValue();
    public static final Block ICY_NETHER_BRICK_WALL = InjectionHelper.nullValue();
    public static final Block ICY_NETHER_BRICK_FENCE = InjectionHelper.nullValue();
    public static final Block ICY_NETHER_BRICK_FENCE_GATE = InjectionHelper.nullValue();
    public static final Block NETHERRACK_PATH = InjectionHelper.nullValue();
    public static final Block BASALT = InjectionHelper.nullValue();
    public static final Block BASALT_SLAB = InjectionHelper.nullValue();
    public static final Block BASALT_STAIRS = InjectionHelper.nullValue();
    public static final Block BASALT_WALL = InjectionHelper.nullValue();
    public static final Block BASALT_FENCE = InjectionHelper.nullValue();
    public static final Block BASALT_FENCE_GATE = InjectionHelper.nullValue();
    public static final Block SMOOTH_BASALT = InjectionHelper.nullValue();
    public static final Block SMOOTH_BASALT_SLAB = InjectionHelper.nullValue();
    public static final Block SMOOTH_BASALT_STAIRS = InjectionHelper.nullValue();
    public static final Block SMOOTH_BASALT_WALL = InjectionHelper.nullValue();
    public static final Block SMOOTH_BASALT_FENCE = InjectionHelper.nullValue();
    public static final Block SMOOTH_BASALT_FENCE_GATE = InjectionHelper.nullValue();
    public static final Block BASALT_BRICK = InjectionHelper.nullValue();
    public static final Block BASALT_BRICK_SLAB = InjectionHelper.nullValue();
    public static final Block BASALT_BRICK_STAIRS = InjectionHelper.nullValue();
    public static final Block BASALT_BRICK_WALL = InjectionHelper.nullValue();
    public static final Block BASALT_BRICK_FENCE = InjectionHelper.nullValue();
    public static final Block BASALT_BRICK_FENCE_GATE = InjectionHelper.nullValue();
    public static final Block BASALT_PILLAR = InjectionHelper.nullValue();
    public static final Block BASALT_PILLAR_SLAB = InjectionHelper.nullValue();
    public static final Block BASALT_PILLAR_STAIRS = InjectionHelper.nullValue();
    public static final Block BASALT_PILLAR_WALL = InjectionHelper.nullValue();
    public static final Block BASALT_PILLAR_FENCE = InjectionHelper.nullValue();
    public static final Block BASALT_PILLAR_FENCE_GATE = InjectionHelper.nullValue();
    public static final Block HYPHAE = InjectionHelper.nullValue();
    public static final Block FROSTBURN_ICE = InjectionHelper.nullValue();
    public static final Block QUARTZ_ORE = InjectionHelper.nullValue();
    public static final Block AMETHYST_ORE = InjectionHelper.nullValue();
    public static final Block AMETHYST_BLOCK = InjectionHelper.nullValue();
    public static final Block RIME_ORE = InjectionHelper.nullValue();
    public static final Block RIME_BLOCK = InjectionHelper.nullValue();
    public static final Block THORNSTALK = InjectionHelper.nullValue();
    public static final Block BROWN_ELDER_MUSHROOM_CAP = InjectionHelper.nullValue();
    public static final Block RED_ELDER_MUSHROOM_CAP = InjectionHelper.nullValue();
    public static final Block ELDER_MUSHROOM_STEM = InjectionHelper.nullValue();
    public static final Block ENOKI_MUSHROOM_CAP = InjectionHelper.nullValue();
    public static final Block ENOKI_MUSHROOM_STEM = InjectionHelper.nullValue();
    public static final Block BLUE_FIRE = InjectionHelper.nullValue();
    public static final Block SOUL_GLASS = InjectionHelper.nullValue();
    public static final Block SOUL_GLASS_PANE = InjectionHelper.nullValue();

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event)
    {
        registerBlock("gloomy_netherrack", new FireSustainingBlock(Block.Properties.create(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).hardnessAndResistance(0.4F)));
        registerBlock("gloomy_netherrack_path", new PathBlock(Block.Properties.create(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 2.0F), RegistryObject.of(NetherEx.MOD_ID + ":gloomy_netherrack", () -> Block.class)));
        registerDecorativeBlocks("gloomy_nether_bricks", "gloomy_nether_brick", new Block(Block.Properties.create(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
        registerBlock("lively_netherrack", new FireSustainingBlock(Block.Properties.create(Material.ROCK, DyeColor.PURPLE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(0.4F)));
        registerBlock("lively_netherrack_path", new PathBlock(Block.Properties.create(Material.ROCK, DyeColor.PURPLE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 2.0F), RegistryObject.of(NetherEx.MOD_ID + ":lively_netherrack", () -> Block.class)));
        registerDecorativeBlocks("lively_nether_bricks", "lively_nether_brick", new Block(Block.Properties.create(Material.ROCK, DyeColor.PURPLE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
        registerBlock("fiery_netherrack", new FireSustainingBlock(Block.Properties.create(Material.ROCK, DyeColor.ORANGE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(0.4F)));
        registerBlock("fiery_netherrack_path", new PathBlock(Block.Properties.create(Material.ROCK, DyeColor.ORANGE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 2.0F), RegistryObject.of(NetherEx.MOD_ID + ":fiery_netherrack", () -> Block.class)));
        registerDecorativeBlocks("fiery_nether_bricks", "fiery_nether_brick", new Block(Block.Properties.create(Material.ROCK, DyeColor.ORANGE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
        registerBlock("icy_netherrack", new FireSustainingBlock(Block.Properties.create(Material.ROCK, DyeColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(0.4F)));
        registerBlock("icy_netherrack_path", new PathBlock(Block.Properties.create(Material.ROCK, DyeColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 2.0F), RegistryObject.of(NetherEx.MOD_ID + ":icy_netherrack", () -> Block.class)));
        registerDecorativeBlocks("icy_nether_bricks", "icy_nether_brick", new Block(Block.Properties.create(Material.ROCK, DyeColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
        registerBlock("netherrack_path", new PathBlock(Block.Properties.create(Material.ROCK, DyeColor.RED).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 2.0F), RegistryObject.of("minecraft:netherrack", () -> Block.class)));
        registerDecorativeBlocks("basalt", new Block(Block.Properties.create(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
        registerDecorativeBlocks("smooth_basalt", new Block(Block.Properties.create(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
        registerDecorativeBlocks("basalt_brick", new Block(Block.Properties.create(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
        registerDecorativeBlocks("basalt_pillar", new Block(Block.Properties.create(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
        registerBlock("hyphae", new HyphaeBlock());
        registerBlock("frostburn_ice", new UnmeltableIceBlock(Block.Properties.create(Material.ICE, DyeColor.LIGHT_BLUE).sound(SoundType.GLASS).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 1.0F)));
        registerBlock("quartz_ore", new QuartzOreBlock());
        registerBlock("amethyst_ore", new ExperienceDroppingBlock(1, 5, Block.Properties.create(Material.ROCK, MaterialColor.NETHERRACK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(3.0F)));
        registerBlock("amethyst_block", new Block(Block.Properties.create(Material.ROCK, DyeColor.PURPLE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(5.0F, 10.0F)));
        registerBlock("rime_ore", new ExperienceDroppingBlock(2, 6, Block.Properties.create(Material.ROCK, DyeColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(3.0F)));
        registerBlock("rime_block", new RimeBlock(Block.Properties.create(Material.ROCK, DyeColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(5.0F, 10.0F).lightValue(13)));
        registerBlock("thornstalk", new ThornstalkBlock());
        registerBlock("brown_elder_mushroom_cap", new HugeMushroomBlock(Block.Properties.from(Blocks.BROWN_MUSHROOM_BLOCK)));
        registerBlock("red_elder_mushroom_cap", new HugeMushroomBlock(Block.Properties.from(Blocks.RED_MUSHROOM_BLOCK)));
        registerBlock("elder_mushroom_stem", new HugeMushroomBlock(Block.Properties.from(Blocks.MUSHROOM_STEM)));
        registerBlock("enoki_mushroom_stem", new EnokiStemBlock(Block.Properties.from(Blocks.CHORUS_PLANT)));
        registerBlock("enoki_mushroom_cap", new EnokiCapBlock(Block.Properties.from(Blocks.CHORUS_FLOWER)));
        registerBlockWithoutItem("blue_fire", new BlueFireBlock(Block.Properties.create(Material.FIRE, MaterialColor.LIGHT_BLUE).tickRandomly().doesNotBlockMovement().hardnessAndResistance(0.0F).lightValue(15).sound(SoundType.CLOTH).noDrops()));
        registerBlock("soul_glass", new SoulGlassBlock(Block.Properties.from(Blocks.BROWN_STAINED_GLASS)));
        registerBlock("soul_glass_pane", new SoulGlassPaneBlock(Block.Properties.from(Blocks.BROWN_STAINED_GLASS_PANE)));
    }

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event)
    {
        BLOCK_ITEMS.forEach(item -> event.getRegistry().register(item));
    }

    private static void registerBlock(String name, Block block)
    {
        ForgeRegistries.BLOCKS.register(block.setRegistryName(name));
        BLOCK_ITEMS.add(new BlockItem(block, new Item.Properties().group(NetherEx.ITEM_GROUP)).setRegistryName(name));
    }

    private static void registerBlockWithoutItem(String name, Block block)
    {
        ForgeRegistries.BLOCKS.register(block.setRegistryName(name));
    }

    private static void registerDecorativeBlocks(String name, Block block)
    {
        registerDecorativeBlocks(name, name, block);
    }

    private static void registerDecorativeBlocks(String name, String decorativePrefix, Block block)
    {
        Block.Properties blockProperties = Block.Properties.from(block);
        Item.Properties itemProperties = new Item.Properties().group(NetherEx.ITEM_GROUP);
        Block slabBlock = new SlabBlock(blockProperties);
        Block stairBlock = new StairsBlock(block.getDefaultState(), blockProperties);
        Block fenceBlock = new FenceBlock(blockProperties);
        Block fenceGateBlock = new FenceGateBlock(blockProperties);
        Block wallBlock = new WallBlock(blockProperties);
        ForgeRegistries.BLOCKS.register(block.setRegistryName(name));
        ForgeRegistries.BLOCKS.register(slabBlock.setRegistryName(decorativePrefix + "_slab"));
        ForgeRegistries.BLOCKS.register(stairBlock.setRegistryName(decorativePrefix + "_stairs"));
        ForgeRegistries.BLOCKS.register(fenceBlock.setRegistryName(decorativePrefix + "_fence"));
        ForgeRegistries.BLOCKS.register(fenceGateBlock.setRegistryName(decorativePrefix + "_fence_gate"));
        ForgeRegistries.BLOCKS.register(wallBlock.setRegistryName(decorativePrefix + "_wall"));
        BLOCK_ITEMS.add(new BlockItem(block, itemProperties).setRegistryName(block.getRegistryName()));
        BLOCK_ITEMS.add(new BlockItem(slabBlock, itemProperties).setRegistryName(slabBlock.getRegistryName()));
        BLOCK_ITEMS.add(new BlockItem(stairBlock, itemProperties).setRegistryName(stairBlock.getRegistryName()));
        BLOCK_ITEMS.add(new BlockItem(fenceBlock, itemProperties).setRegistryName(fenceBlock.getRegistryName()));
        BLOCK_ITEMS.add(new BlockItem(fenceGateBlock, itemProperties).setRegistryName(fenceGateBlock.getRegistryName()));
        BLOCK_ITEMS.add(new BlockItem(wallBlock, itemProperties).setRegistryName(wallBlock.getRegistryName()));
    }
}

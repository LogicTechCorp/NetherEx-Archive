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
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.DyeColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class NetherExBlocks
{
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, NetherEx.MOD_ID);

    public static final RegistryObject<Block> GLOOMY_NETHERRACK = BLOCKS.register("gloomy_netherrack", () -> new FireSustainingBlock(Block.Properties.create(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).hardnessAndResistance(0.4F)));
    public static final RegistryObject<Block> GLOOMY_NETHERRACK_PATH = BLOCKS.register("gloomy_netherrack_path", () -> new PathBlock(() -> GLOOMY_NETHERRACK.get().getDefaultState(), Block.Properties.create(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 2.0F)));
    public static final RegistryObject<Block> GLOOMY_NETHER_BRICKS = BLOCKS.register("gloomy_nether_bricks", () -> new Block(Block.Properties.create(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
    public static final RegistryObject<Block> GLOOMY_NETHER_BRICK_SLAB = BLOCKS.register("gloomy_nether_brick_slab", () -> new SlabBlock(Block.Properties.from(GLOOMY_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> GLOOMY_NETHER_BRICK_STAIRS = BLOCKS.register("gloomy_nether_brick_stairs", () -> new StairsBlock(() -> GLOOMY_NETHER_BRICKS.get().getDefaultState(), Block.Properties.from(GLOOMY_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> GLOOMY_NETHER_BRICK_WALL = BLOCKS.register("gloomy_nether_brick_wall", () -> new WallBlock(Block.Properties.from(GLOOMY_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> GLOOMY_NETHER_BRICK_FENCE = BLOCKS.register("gloomy_nether_brick_fence", () -> new FenceBlock(Block.Properties.from(GLOOMY_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> GLOOMY_NETHER_BRICK_FENCE_GATE = BLOCKS.register("gloomy_nether_brick_fence_gate", () -> new FenceGateBlock(Block.Properties.from(GLOOMY_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> LIVELY_NETHERRACK = BLOCKS.register("lively_netherrack", () -> new FireSustainingBlock(Block.Properties.create(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).hardnessAndResistance(0.4F)));
    public static final RegistryObject<Block> LIVELY_NETHERRACK_PATH = BLOCKS.register("lively_netherrack_path", () -> new PathBlock(() -> LIVELY_NETHERRACK.get().getDefaultState(), Block.Properties.create(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 2.0F)));
    public static final RegistryObject<Block> LIVELY_NETHER_BRICKS = BLOCKS.register("lively_nether_bricks", () -> new Block(Block.Properties.create(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
    public static final RegistryObject<Block> LIVELY_NETHER_BRICK_SLAB = BLOCKS.register("lively_nether_brick_slab", () -> new SlabBlock(Block.Properties.from(LIVELY_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> LIVELY_NETHER_BRICK_STAIRS = BLOCKS.register("lively_nether_brick_stairs", () -> new StairsBlock(() -> LIVELY_NETHER_BRICKS.get().getDefaultState(), Block.Properties.from(LIVELY_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> LIVELY_NETHER_BRICK_WALL = BLOCKS.register("lively_nether_brick_wall", () -> new WallBlock(Block.Properties.from(LIVELY_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> LIVELY_NETHER_BRICK_FENCE = BLOCKS.register("lively_nether_brick_fence", () -> new FenceBlock(Block.Properties.from(LIVELY_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> LIVELY_NETHER_BRICK_FENCE_GATE = BLOCKS.register("lively_nether_brick_fence_gate", () -> new FenceGateBlock(Block.Properties.from(LIVELY_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> FIERY_NETHERRACK = BLOCKS.register("fiery_netherrack", () -> new FireSustainingBlock(Block.Properties.create(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).hardnessAndResistance(0.4F)));
    public static final RegistryObject<Block> FIERY_NETHERRACK_PATH = BLOCKS.register("fiery_netherrack_path", () -> new PathBlock(() -> FIERY_NETHERRACK.get().getDefaultState(), Block.Properties.create(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 2.0F)));
    public static final RegistryObject<Block> FIERY_NETHER_BRICKS = BLOCKS.register("fiery_nether_bricks", () -> new Block(Block.Properties.create(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
    public static final RegistryObject<Block> FIERY_NETHER_BRICK_SLAB = BLOCKS.register("fiery_nether_brick_slab", () -> new SlabBlock(Block.Properties.from(FIERY_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> FIERY_NETHER_BRICK_STAIRS = BLOCKS.register("fiery_nether_brick_stairs", () -> new StairsBlock(() -> FIERY_NETHER_BRICKS.get().getDefaultState(), Block.Properties.from(FIERY_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> FIERY_NETHER_BRICK_WALL = BLOCKS.register("fiery_nether_brick_wall", () -> new WallBlock(Block.Properties.from(FIERY_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> FIERY_NETHER_BRICK_FENCE = BLOCKS.register("fiery_nether_brick_fence", () -> new FenceBlock(Block.Properties.from(FIERY_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> FIERY_NETHER_BRICK_FENCE_GATE = BLOCKS.register("fiery_nether_brick_fence_gate", () -> new FenceGateBlock(Block.Properties.from(FIERY_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> ICY_NETHERRACK = BLOCKS.register("icy_netherrack", () -> new FireSustainingBlock(Block.Properties.create(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).hardnessAndResistance(0.4F)));
    public static final RegistryObject<Block> ICY_NETHERRACK_PATH = BLOCKS.register("icy_netherrack_path", () -> new PathBlock(() -> ICY_NETHERRACK.get().getDefaultState(), Block.Properties.create(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 2.0F)));
    public static final RegistryObject<Block> ICY_NETHER_BRICKS = BLOCKS.register("icy_nether_bricks", () -> new Block(Block.Properties.create(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
    public static final RegistryObject<Block> ICY_NETHER_BRICK_SLAB = BLOCKS.register("icy_nether_brick_slab", () -> new SlabBlock(Block.Properties.from(ICY_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> ICY_NETHER_BRICK_STAIRS = BLOCKS.register("icy_nether_brick_stairs", () -> new StairsBlock(() -> ICY_NETHER_BRICKS.get().getDefaultState(), Block.Properties.from(ICY_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> ICY_NETHER_BRICK_WALL = BLOCKS.register("icy_nether_brick_wall", () -> new WallBlock(Block.Properties.from(ICY_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> ICY_NETHER_BRICK_FENCE = BLOCKS.register("icy_nether_brick_fence", () -> new FenceBlock(Block.Properties.from(ICY_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> ICY_NETHER_BRICK_FENCE_GATE = BLOCKS.register("icy_nether_brick_fence_gate", () -> new FenceGateBlock(Block.Properties.from(ICY_NETHER_BRICKS.get())));
    public static final RegistryObject<Block> NETHERRACK_PATH = BLOCKS.register("netherrack_path", () -> new PathBlock(() -> Blocks.NETHERRACK.getDefaultState(), Block.Properties.create(Material.ROCK, DyeColor.RED).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 2.0F)));
    public static final RegistryObject<Block> BASALT = BLOCKS.register("basalt", () -> new Block(Block.Properties.create(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
    public static final RegistryObject<Block> BASALT_SLAB = BLOCKS.register("basalt_slab", () -> new SlabBlock(Block.Properties.from(BASALT.get())));
    public static final RegistryObject<Block> BASALT_STAIRS = BLOCKS.register("basalt_stairs", () -> new StairsBlock(() -> BASALT.get().getDefaultState(), Block.Properties.from(BASALT.get())));
    public static final RegistryObject<Block> BASALT_WALL = BLOCKS.register("basalt_wall", () -> new WallBlock(Block.Properties.from(BASALT.get())));
    public static final RegistryObject<Block> BASALT_FENCE = BLOCKS.register("basalt_fence", () -> new FenceBlock(Block.Properties.from(BASALT.get())));
    public static final RegistryObject<Block> BASALT_FENCE_GATE = BLOCKS.register("basalt_fence_gate", () -> new FenceGateBlock(Block.Properties.from(BASALT.get())));
    public static final RegistryObject<Block> SMOOTH_BASALT = BLOCKS.register("smooth_basalt", () -> new Block(Block.Properties.create(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
    public static final RegistryObject<Block> SMOOTH_BASALT_SLAB = BLOCKS.register("smooth_basalt_slab", () -> new SlabBlock(Block.Properties.from(BASALT.get())));
    public static final RegistryObject<Block> SMOOTH_BASALT_STAIRS = BLOCKS.register("smooth_basalt_stairs", () -> new StairsBlock(() -> BASALT.get().getDefaultState(), Block.Properties.from(BASALT.get())));
    public static final RegistryObject<Block> SMOOTH_BASALT_WALL = BLOCKS.register("smooth_basalt_wall", () -> new WallBlock(Block.Properties.from(BASALT.get())));
    public static final RegistryObject<Block> SMOOTH_BASALT_FENCE = BLOCKS.register("smooth_basalt_fence", () -> new FenceBlock(Block.Properties.from(BASALT.get())));
    public static final RegistryObject<Block> SMOOTH_BASALT_FENCE_GATE = BLOCKS.register("smooth_basalt_fence_gate", () -> new FenceGateBlock(Block.Properties.from(BASALT.get())));
    public static final RegistryObject<Block> BASALT_BRICK = BLOCKS.register("basalt_brick", () -> new Block(Block.Properties.create(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
    public static final RegistryObject<Block> BASALT_BRICK_SLAB = BLOCKS.register("basalt_brick_slab", () -> new SlabBlock(Block.Properties.from(BASALT.get())));
    public static final RegistryObject<Block> BASALT_BRICK_STAIRS = BLOCKS.register("basalt_brick_stairs", () -> new StairsBlock(() -> BASALT.get().getDefaultState(), Block.Properties.from(BASALT.get())));
    public static final RegistryObject<Block> BASALT_BRICK_WALL = BLOCKS.register("basalt_brick_wall", () -> new WallBlock(Block.Properties.from(BASALT.get())));
    public static final RegistryObject<Block> BASALT_BRICK_FENCE = BLOCKS.register("basalt_brick_fence", () -> new FenceBlock(Block.Properties.from(BASALT.get())));
    public static final RegistryObject<Block> BASALT_BRICK_FENCE_GATE = BLOCKS.register("basalt_brick_fence_gate", () -> new FenceGateBlock(Block.Properties.from(BASALT.get())));
    public static final RegistryObject<Block> BASALT_PILLAR = BLOCKS.register("basalt_pillar", () -> new Block(Block.Properties.create(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
    public static final RegistryObject<Block> BASALT_PILLAR_SLAB = BLOCKS.register("basalt_pillar_slab", () -> new SlabBlock(Block.Properties.from(BASALT.get())));
    public static final RegistryObject<Block> BASALT_PILLAR_STAIRS = BLOCKS.register("basalt_pillar_stairs", () -> new StairsBlock(() -> BASALT.get().getDefaultState(), Block.Properties.from(BASALT.get())));
    public static final RegistryObject<Block> BASALT_PILLAR_WALL = BLOCKS.register("basalt_pillar_wall", () -> new WallBlock(Block.Properties.from(BASALT.get())));
    public static final RegistryObject<Block> BASALT_PILLAR_FENCE = BLOCKS.register("basalt_pillar_fence", () -> new FenceBlock(Block.Properties.from(BASALT.get())));
    public static final RegistryObject<Block> BASALT_PILLAR_FENCE_GATE = BLOCKS.register("basalt_pillar_fence_gate", () -> new FenceGateBlock(Block.Properties.from(BASALT.get())));
    public static final RegistryObject<Block> HYPHAE = BLOCKS.register("hyphae", () -> new HyphaeBlock());
    public static final RegistryObject<Block> FROSTBURN_ICE = BLOCKS.register("frostburn_ice", () -> new UnmeltableIceBlock(Block.Properties.create(Material.ICE, DyeColor.LIGHT_BLUE).sound(SoundType.GLASS).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 1.0F)));
    public static final RegistryObject<Block> QUARTZ_ORE = BLOCKS.register("quartz_ore", () -> new QuartzOreBlock());
    public static final RegistryObject<Block> RIME_ORE = BLOCKS.register("rime_ore", () -> new ExperienceDroppingBlock(2, 6, Block.Properties.create(Material.ROCK, DyeColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(3.0F)));
    public static final RegistryObject<Block> RIME_BLOCK = BLOCKS.register("rime_block", () -> new RimeBlock(Block.Properties.create(Material.ROCK, DyeColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(5.0F, 10.0F).lightValue(13)));
    public static final RegistryObject<Block> THORNSTALK = BLOCKS.register("thornstalk", () -> new ThornstalkBlock());
    public static final RegistryObject<Block> BROWN_ELDER_MUSHROOM = BLOCKS.register("brown_elder_mushroom", () -> new ModMushroomBlock(() -> NetherExFeatures.BROWN_ELDER_MUSHROOM.get(), Block.Properties.from(Blocks.BROWN_MUSHROOM)));
    public static final RegistryObject<Block> RED_ELDER_MUSHROOM = BLOCKS.register("red_elder_mushroom", () -> new ModMushroomBlock(() -> NetherExFeatures.RED_ELDER_MUSHROOM.get(), Block.Properties.from(Blocks.BROWN_MUSHROOM)));
    public static final RegistryObject<Block> BROWN_ELDER_MUSHROOM_CAP = BLOCKS.register("brown_elder_mushroom_cap", () -> new HugeMushroomBlock(Block.Properties.from(Blocks.BROWN_MUSHROOM_BLOCK)));
    public static final RegistryObject<Block> RED_ELDER_MUSHROOM_CAP = BLOCKS.register("red_elder_mushroom_cap", () -> new HugeMushroomBlock(Block.Properties.from(Blocks.RED_MUSHROOM_BLOCK)));
    public static final RegistryObject<Block> ELDER_MUSHROOM_STEM = BLOCKS.register("elder_mushroom_stem", () -> new HugeMushroomBlock(Block.Properties.from(Blocks.MUSHROOM_STEM)));
    public static final RegistryObject<Block> ENOKI_MUSHROOM_CAP = BLOCKS.register("enoki_mushroom_cap", () -> new EnokiCapBlock(Block.Properties.from(Blocks.CHORUS_FLOWER)));
    public static final RegistryObject<Block> ENOKI_MUSHROOM_STEM = BLOCKS.register("enoki_mushroom_stem", () -> new EnokiStemBlock(Block.Properties.from(Blocks.CHORUS_PLANT)));
    public static final RegistryObject<Block> BLUE_FIRE = BLOCKS.register("blue_fire", () -> new BlueFireBlock(Block.Properties.create(Material.FIRE, MaterialColor.LIGHT_BLUE).tickRandomly().doesNotBlockMovement().hardnessAndResistance(0.0F).lightValue(15).sound(SoundType.CLOTH).noDrops()));
    public static final RegistryObject<Block> SOUL_GLASS = BLOCKS.register("soul_glass", () -> new SoulGlassBlock(Block.Properties.from(Blocks.BROWN_STAINED_GLASS)));
    public static final RegistryObject<Block> SOUL_GLASS_PANE = BLOCKS.register("soul_glass_pane", () -> new SoulGlassPaneBlock(Block.Properties.from(Blocks.BROWN_STAINED_GLASS_PANE)));
}

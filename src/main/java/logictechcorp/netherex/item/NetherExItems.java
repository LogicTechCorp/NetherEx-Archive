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

package logictechcorp.netherex.item;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.block.NetherExBlocks;
import logictechcorp.netherex.entity.NetherExEntities;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("all")
public class NetherExItems
{
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, NetherEx.MOD_ID);

    //Block items
    public static final RegistryObject<Item> GLOOMY_NETHERRACK = ITEMS.register("gloomy_netherrack", () -> new BlockItem(NetherExBlocks.GLOOMY_NETHERRACK.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> GLOOMY_NETHERRACK_PATH = ITEMS.register("gloomy_netherrack_path", () -> new BlockItem(NetherExBlocks.GLOOMY_NETHERRACK_PATH.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> GLOOMY_NETHER_BRICKS = ITEMS.register("gloomy_nether_bricks", () -> new BlockItem(NetherExBlocks.GLOOMY_NETHER_BRICKS.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> GLOOMY_NETHER_BRICK_SLAB = ITEMS.register("gloomy_nether_brick_slab", () -> new BlockItem(NetherExBlocks.GLOOMY_NETHER_BRICK_SLAB.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> GLOOMY_NETHER_BRICK_STAIRS = ITEMS.register("gloomy_nether_brick_stairs", () -> new BlockItem(NetherExBlocks.GLOOMY_NETHER_BRICK_STAIRS.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> GLOOMY_NETHER_BRICK_WALL = ITEMS.register("gloomy_nether_brick_wall", () -> new BlockItem(NetherExBlocks.GLOOMY_NETHER_BRICK_WALL.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> GLOOMY_NETHER_BRICK_FENCE = ITEMS.register("gloomy_nether_brick_fence", () -> new BlockItem(NetherExBlocks.GLOOMY_NETHER_BRICK_FENCE.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> GLOOMY_NETHER_BRICK_FENCE_GATE = ITEMS.register("gloomy_nether_brick_fence_gate", () -> new BlockItem(NetherExBlocks.GLOOMY_NETHER_BRICK_FENCE_GATE.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> LIVELY_NETHERRACK = ITEMS.register("lively_netherrack", () -> new BlockItem(NetherExBlocks.LIVELY_NETHERRACK.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> LIVELY_NETHERRACK_PATH = ITEMS.register("lively_netherrack_path", () -> new BlockItem(NetherExBlocks.LIVELY_NETHERRACK_PATH.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> LIVELY_NETHER_BRICKS = ITEMS.register("lively_nether_bricks", () -> new BlockItem(NetherExBlocks.LIVELY_NETHER_BRICKS.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> LIVELY_NETHER_BRICK_SLAB = ITEMS.register("lively_nether_brick_slab", () -> new BlockItem(NetherExBlocks.LIVELY_NETHER_BRICK_SLAB.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> LIVELY_NETHER_BRICK_STAIRS = ITEMS.register("lively_nether_brick_stairs", () -> new BlockItem(NetherExBlocks.LIVELY_NETHER_BRICK_STAIRS.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> LIVELY_NETHER_BRICK_WALL = ITEMS.register("lively_nether_brick_wall", () -> new BlockItem(NetherExBlocks.LIVELY_NETHER_BRICK_WALL.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> LIVELY_NETHER_BRICK_FENCE = ITEMS.register("lively_nether_brick_fence", () -> new BlockItem(NetherExBlocks.LIVELY_NETHER_BRICK_FENCE.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> LIVELY_NETHER_BRICK_FENCE_GATE = ITEMS.register("lively_nether_brick_fence_gate", () -> new BlockItem(NetherExBlocks.LIVELY_NETHER_BRICK_FENCE_GATE.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> FIERY_NETHERRACK = ITEMS.register("fiery_netherrack", () -> new BlockItem(NetherExBlocks.FIERY_NETHERRACK.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> FIERY_NETHERRACK_PATH = ITEMS.register("fiery_netherrack_path", () -> new BlockItem(NetherExBlocks.FIERY_NETHERRACK_PATH.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> FIERY_NETHER_BRICKS = ITEMS.register("fiery_nether_bricks", () -> new BlockItem(NetherExBlocks.FIERY_NETHER_BRICKS.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> FIERY_NETHER_BRICK_SLAB = ITEMS.register("fiery_nether_brick_slab", () -> new BlockItem(NetherExBlocks.FIERY_NETHER_BRICK_SLAB.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> FIERY_NETHER_BRICK_STAIRS = ITEMS.register("fiery_nether_brick_stairs", () -> new BlockItem(NetherExBlocks.FIERY_NETHER_BRICK_STAIRS.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> FIERY_NETHER_BRICK_WALL = ITEMS.register("fiery_nether_brick_wall", () -> new BlockItem(NetherExBlocks.FIERY_NETHER_BRICK_WALL.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> FIERY_NETHER_BRICK_FENCE = ITEMS.register("fiery_nether_brick_fence", () -> new BlockItem(NetherExBlocks.FIERY_NETHER_BRICK_FENCE.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> FIERY_NETHER_BRICK_FENCE_GATE = ITEMS.register("fiery_nether_brick_fence_gate", () -> new BlockItem(NetherExBlocks.FIERY_NETHER_BRICK_FENCE_GATE.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> ICY_NETHERRACK = ITEMS.register("icy_netherrack", () -> new BlockItem(NetherExBlocks.ICY_NETHERRACK.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> ICY_NETHERRACK_PATH = ITEMS.register("icy_netherrack_path", () -> new BlockItem(NetherExBlocks.ICY_NETHERRACK_PATH.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> ICY_NETHER_BRICKS = ITEMS.register("icy_nether_bricks", () -> new BlockItem(NetherExBlocks.ICY_NETHER_BRICKS.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> ICY_NETHER_BRICK_SLAB = ITEMS.register("icy_nether_brick_slab", () -> new BlockItem(NetherExBlocks.ICY_NETHER_BRICK_SLAB.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> ICY_NETHER_BRICK_STAIRS = ITEMS.register("icy_nether_brick_stairs", () -> new BlockItem(NetherExBlocks.ICY_NETHER_BRICK_STAIRS.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> ICY_NETHER_BRICK_WALL = ITEMS.register("icy_nether_brick_wall", () -> new BlockItem(NetherExBlocks.ICY_NETHER_BRICK_WALL.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> ICY_NETHER_BRICK_FENCE = ITEMS.register("icy_nether_brick_fence", () -> new BlockItem(NetherExBlocks.ICY_NETHER_BRICK_FENCE.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> ICY_NETHER_BRICK_FENCE_GATE = ITEMS.register("icy_nether_brick_fence_gate", () -> new BlockItem(NetherExBlocks.ICY_NETHER_BRICK_FENCE_GATE.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> NETHERRACK_PATH = ITEMS.register("netherrack_path", () -> new BlockItem(NetherExBlocks.NETHERRACK_PATH.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> BASALT = ITEMS.register("basalt", () -> new BlockItem(NetherExBlocks.BASALT.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> BASALT_SLAB = ITEMS.register("basalt_slab", () -> new BlockItem(NetherExBlocks.BASALT_SLAB.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> BASALT_STAIRS = ITEMS.register("basalt_stairs", () -> new BlockItem(NetherExBlocks.BASALT_STAIRS.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> BASALT_WALL = ITEMS.register("basalt_wall", () -> new BlockItem(NetherExBlocks.BASALT_WALL.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> BASALT_FENCE = ITEMS.register("basalt_fence", () -> new BlockItem(NetherExBlocks.BASALT_FENCE.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> BASALT_FENCE_GATE = ITEMS.register("basalt_fence_gate", () -> new BlockItem(NetherExBlocks.BASALT_FENCE_GATE.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> SMOOTH_BASALT = ITEMS.register("smooth_basalt", () -> new BlockItem(NetherExBlocks.SMOOTH_BASALT.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> SMOOTH_BASALT_SLAB = ITEMS.register("smooth_basalt_slab", () -> new BlockItem(NetherExBlocks.SMOOTH_BASALT_SLAB.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> SMOOTH_BASALT_STAIRS = ITEMS.register("smooth_basalt_stairs", () -> new BlockItem(NetherExBlocks.SMOOTH_BASALT_STAIRS.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> SMOOTH_BASALT_WALL = ITEMS.register("smooth_basalt_wall", () -> new BlockItem(NetherExBlocks.SMOOTH_BASALT_WALL.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> SMOOTH_BASALT_FENCE = ITEMS.register("smooth_basalt_fence", () -> new BlockItem(NetherExBlocks.SMOOTH_BASALT_FENCE.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> SMOOTH_BASALT_FENCE_GATE = ITEMS.register("smooth_basalt_fence_gate", () -> new BlockItem(NetherExBlocks.SMOOTH_BASALT_FENCE_GATE.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> BASALT_BRICK = ITEMS.register("basalt_brick", () -> new BlockItem(NetherExBlocks.BASALT_BRICK.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> BASALT_BRICK_SLAB = ITEMS.register("basalt_brick_slab", () -> new BlockItem(NetherExBlocks.BASALT_BRICK_SLAB.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> BASALT_BRICK_STAIRS = ITEMS.register("basalt_brick_stairs", () -> new BlockItem(NetherExBlocks.BASALT_BRICK_STAIRS.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> BASALT_BRICK_WALL = ITEMS.register("basalt_brick_wall", () -> new BlockItem(NetherExBlocks.BASALT_BRICK_WALL.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> BASALT_BRICK_FENCE = ITEMS.register("basalt_brick_fence", () -> new BlockItem(NetherExBlocks.BASALT_BRICK_FENCE.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> BASALT_BRICK_FENCE_GATE = ITEMS.register("basalt_brick_fence_gate", () -> new BlockItem(NetherExBlocks.BASALT_BRICK_FENCE_GATE.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> BASALT_PILLAR = ITEMS.register("basalt_pillar", () -> new BlockItem(NetherExBlocks.BASALT_PILLAR.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> BASALT_PILLAR_SLAB = ITEMS.register("basalt_pillar_slab", () -> new BlockItem(NetherExBlocks.BASALT_PILLAR_SLAB.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> BASALT_PILLAR_STAIRS = ITEMS.register("basalt_pillar_stairs", () -> new BlockItem(NetherExBlocks.BASALT_PILLAR_STAIRS.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> BASALT_PILLAR_WALL = ITEMS.register("basalt_pillar_wall", () -> new BlockItem(NetherExBlocks.BASALT_PILLAR_WALL.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> BASALT_PILLAR_FENCE = ITEMS.register("basalt_pillar_fence", () -> new BlockItem(NetherExBlocks.BASALT_PILLAR_FENCE.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> BASALT_PILLAR_FENCE_GATE = ITEMS.register("basalt_pillar_fence_gate", () -> new BlockItem(NetherExBlocks.BASALT_PILLAR_FENCE_GATE.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> HYPHAE = ITEMS.register("hyphae", () -> new BlockItem(NetherExBlocks.HYPHAE.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> FROSTBURN_ICE = ITEMS.register("frostburn_ice", () -> new BlockItem(NetherExBlocks.FROSTBURN_ICE.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> QUARTZ_ORE = ITEMS.register("quartz_ore", () -> new BlockItem(NetherExBlocks.QUARTZ_ORE.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> RIME_ORE = ITEMS.register("rime_ore", () -> new BlockItem(NetherExBlocks.RIME_ORE.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> RIME_BLOCK = ITEMS.register("rime_block", () -> new BlockItem(NetherExBlocks.RIME_BLOCK.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> THORNSTALK = ITEMS.register("thornstalk", () -> new BlockItem(NetherExBlocks.THORNSTALK.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> BROWN_ELDER_MUSHROOM = ITEMS.register("brown_elder_mushroom", () -> new BlockItem(NetherExBlocks.BROWN_ELDER_MUSHROOM.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> RED_ELDER_MUSHROOM = ITEMS.register("red_elder_mushroom", () -> new BlockItem(NetherExBlocks.RED_ELDER_MUSHROOM.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> BROWN_ELDER_MUSHROOM_CAP = ITEMS.register("brown_elder_mushroom_cap", () -> new BlockItem(NetherExBlocks.BROWN_ELDER_MUSHROOM_CAP.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> RED_ELDER_MUSHROOM_CAP = ITEMS.register("red_elder_mushroom_cap", () -> new BlockItem(NetherExBlocks.RED_ELDER_MUSHROOM_CAP.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> ELDER_MUSHROOM_STEM = ITEMS.register("elder_mushroom_stem", () -> new BlockItem(NetherExBlocks.ELDER_MUSHROOM_STEM.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> ENOKI_MUSHROOM_CAP = ITEMS.register("enoki_mushroom_stem", () -> new BlockItem(NetherExBlocks.ENOKI_MUSHROOM_CAP.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> ENOKI_MUSHROOM_STEM = ITEMS.register("enoki_mushroom_cap", () -> new BlockItem(NetherExBlocks.ENOKI_MUSHROOM_STEM.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> SOUL_GLASS = ITEMS.register("soul_glass", () -> new BlockItem(NetherExBlocks.SOUL_GLASS.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> SOUL_GLASS_PANE = ITEMS.register("soul_glass_pane", () -> new BlockItem(NetherExBlocks.SOUL_GLASS_PANE.get(), new Item.Properties().group(NetherEx.ITEM_GROUP)));

    //Items
    public static final RegistryObject<Item> GLOOMY_NETHER_BRICK = ITEMS.register("gloomy_nether_brick", () -> new Item(new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> LIVELY_NETHER_BRICK = ITEMS.register("lively_nether_brick", () -> new Item(new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> FIERY_NETHER_BRICK = ITEMS.register("fiery_nether_brick", () -> new Item(new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> ICY_NETHER_BRICK = ITEMS.register("icy_nether_brick", () -> new Item(new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> MOGUS_SPAWN_EGG = ITEMS.register("mogus_spawn_egg", () -> new SpawnEggItem(NetherExEntities.MOGUS.get(), 10489616, 10051392, new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> SALAMANDER_SPAWN_EGG = ITEMS.register("salamander_spawn_egg", () -> new SpawnEggItem(NetherExEntities.SALAMANDER.get(), 15690005, 0, new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> ORANGE_SALAMANDER_HIDE = ITEMS.register("orange_salamander_hide", () -> new Item(new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> BLACK_SALAMANDER_HIDE = ITEMS.register("black_salamander_hide", () -> new Item(new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> SPINOUT_SPAWN_EGG = ITEMS.register("spinout_spawn_egg", () -> new SpawnEggItem(NetherExEntities.SPINOUT.get(), 5651507, 16382457, new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> WIGHT_SPAWN_EGG = ITEMS.register("wight_spawn_egg", () -> new SpawnEggItem(NetherExEntities.WIGHT.get(), 15198183, 7375001, new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> RIME_CRYSTAL = ITEMS.register("rime_crystal", () -> new Item(new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> RIME_AND_STEEL = ITEMS.register("rime_and_steel", () -> new RimeAndSteelItem(new Item.Properties().group(NetherEx.ITEM_GROUP).defaultMaxDamage(64)));
    public static final RegistryObject<Item> SPORE_SPAWN_EGG = ITEMS.register("spore_spawn_egg", () -> new SpawnEggItem(NetherExEntities.SPORE.get(), 16579584, 11013646, new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> SPORE_CREEPER_SPAWN_EGG = ITEMS.register("spore_creeper_spawn_egg", () -> new SpawnEggItem(NetherExEntities.SPORE_CREEPER.get(), 11013646, 16579584, new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> SPORE = ITEMS.register("spore", () -> new Item(new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> COOLMAR_SPIDER_SPAWN_EGG = ITEMS.register("coolmar_spider_spawn_egg", () -> new SpawnEggItem(NetherExEntities.COOLMAR_SPIDER.get(), 14144467, 8032420, new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> COOLMAR_SPIDER_FANG = ITEMS.register("coolmar_spider_fang", () -> new Item(new Item.Properties().group(NetherEx.ITEM_GROUP)));
    public static final RegistryObject<Item> GHAST_MEAT = ITEMS.register("ghast_meat", () -> new Item(new Item.Properties().group(NetherEx.ITEM_GROUP).food(new Food.Builder().effect(new EffectInstance(Effects.LEVITATION, 100), 1.0F).meat().hunger(4).saturation(0.5F).setAlwaysEdible().build())));
    public static final RegistryObject<Item> COOKED_GHAST_MEAT = ITEMS.register("cooked_ghast_meat", () -> new Item(new Item.Properties().group(NetherEx.ITEM_GROUP).food(new Food.Builder().effect(new EffectInstance(Effects.LEVITATION, 200), 1.0F).meat().hunger(8).saturation(1.0F).setAlwaysEdible().build())));
    public static final RegistryObject<Item> ENOKI_MUSHROOM = ITEMS.register("enoki_mushroom", () -> new Item(new Item.Properties().group(NetherEx.ITEM_GROUP).food(new Food.Builder().effect(new EffectInstance(Effects.LEVITATION, 200), 1.0F).hunger(3).saturation(0.7F).build())));
    public static final RegistryObject<Item> DULL_MIRROR = ITEMS.register("dull_mirror", () -> new DullMirrorItem(new Item.Properties().group(NetherEx.ITEM_GROUP).maxDamage(6)));
}

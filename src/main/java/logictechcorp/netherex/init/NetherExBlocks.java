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

package logictechcorp.netherex.init;

import logictechcorp.libraryex.block.*;
import logictechcorp.libraryex.block.property.BlockProperties;
import logictechcorp.libraryex.item.ItemBlockMod;
import logictechcorp.libraryex.item.ItemBlockModSlab;
import logictechcorp.libraryex.item.builder.ItemProperties;
import logictechcorp.libraryex.utility.InjectionHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.block.*;
import logictechcorp.netherex.item.ItemBlockElderMushroom;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExBlocks
{
    public static final BlockNetherrack GLOOMY_NETHERRACK = InjectionHelper.nullValue();
    public static final BlockNetherrack LIVELY_NETHERRACK = InjectionHelper.nullValue();
    public static final BlockNetherrack FIERY_NETHERRACK = InjectionHelper.nullValue();
    public static final BlockNetherrack ICY_NETHERRACK = InjectionHelper.nullValue();
    public static final BlockModPath NETHERRACK_PATH = InjectionHelper.nullValue();
    public static final BlockModPath GLOOMY_NETHERRACK_PATH = InjectionHelper.nullValue();
    public static final BlockModPath LIVELY_NETHERRACK_PATH = InjectionHelper.nullValue();
    public static final BlockModPath FIERY_NETHERRACK_PATH = InjectionHelper.nullValue();
    public static final BlockModPath ICY_NETHERRACK_PATH = InjectionHelper.nullValue();
    public static final BlockMod GLOOMY_NETHER_BRICK = InjectionHelper.nullValue();
    public static final BlockMod LIVELY_NETHER_BRICK = InjectionHelper.nullValue();
    public static final BlockMod FIERY_NETHER_BRICK = InjectionHelper.nullValue();
    public static final BlockMod ICY_NETHER_BRICK = InjectionHelper.nullValue();
    public static final BlockMod BASALT = InjectionHelper.nullValue();
    public static final BlockMod SMOOTH_BASALT = InjectionHelper.nullValue();
    public static final BlockMod BASALT_BRICK = InjectionHelper.nullValue();
    public static final BlockMod BASALT_PILLAR = InjectionHelper.nullValue();
    public static final BlockHyphae HYPHAE = InjectionHelper.nullValue();
    public static final BlockFrostburnIce FROSTBURN_ICE = InjectionHelper.nullValue();
    public static final BlockSoulGlass SOUL_GLASS = InjectionHelper.nullValue();
    public static final BlockSoulGlassPane SOUL_GLASS_PANE = InjectionHelper.nullValue();
    public static final BlockAmethyst AMETHYST_BLOCK = InjectionHelper.nullValue();
    public static final BlockRime RIME_BLOCK = InjectionHelper.nullValue();
    public static final BlockBoneSliver BONE_SLIVER = InjectionHelper.nullValue();
    public static final BlockBoneChunk BONE_CHUNK = InjectionHelper.nullValue();
    public static final BlockWornIron WORN_IRON = InjectionHelper.nullValue();
    public static final BlockBlueFire BLUE_FIRE = InjectionHelper.nullValue();
    public static final BlockNetherPortal NETHER_PORTAL = InjectionHelper.nullValue();
    public static final BlockUrnOfSorrow URN_OF_SORROW = InjectionHelper.nullValue();
    public static final BlockQuartzOre QUARTZ_ORE = InjectionHelper.nullValue();
    public static final BlockModOre AMETHYST_ORE = InjectionHelper.nullValue();
    public static final BlockModOre RIME_ORE = InjectionHelper.nullValue();
    public static final BlockThornstalk THORNSTALK = InjectionHelper.nullValue();
    public static final BlockElderMushroom BROWN_ELDER_MUSHROOM = InjectionHelper.nullValue();
    public static final BlockElderMushroom RED_ELDER_MUSHROOM = InjectionHelper.nullValue();
    public static final BlockModBigMushroom BROWN_ELDER_MUSHROOM_CAP = InjectionHelper.nullValue();
    public static final BlockModBigMushroom RED_ELDER_MUSHROOM_CAP = InjectionHelper.nullValue();
    public static final BlockEnokiMushroomCap ENOKI_MUSHROOM_CAP = InjectionHelper.nullValue();
    public static final BlockElderMushroomStem ELDER_MUSHROOM_STEM = InjectionHelper.nullValue();
    public static final BlockEnokiMushroomStem ENOKI_MUSHROOM_STEM = InjectionHelper.nullValue();
    public static final BlockModInfiniteFluid ICHOR = InjectionHelper.nullValue();
    public static final BlockModSlab RED_NETHER_BRICK_SLAB = InjectionHelper.nullValue();
    public static final BlockModSlab GLOOMY_NETHER_BRICK_SLAB = InjectionHelper.nullValue();
    public static final BlockModSlab LIVELY_NETHER_BRICK_SLAB = InjectionHelper.nullValue();
    public static final BlockModSlab FIERY_NETHER_BRICK_SLAB = InjectionHelper.nullValue();
    public static final BlockModSlab ICY_NETHER_BRICK_SLAB = InjectionHelper.nullValue();
    public static final BlockModSlab BASALT_SLAB = InjectionHelper.nullValue();
    public static final BlockModSlab SMOOTH_BASALT_SLAB = InjectionHelper.nullValue();
    public static final BlockModSlab BASALT_BRICK_SLAB = InjectionHelper.nullValue();
    public static final BlockModSlab BASALT_PILLAR_SLAB = InjectionHelper.nullValue();
    public static final BlockModStairs RED_NETHER_BRICK_STAIRS = InjectionHelper.nullValue();
    public static final BlockModStairs GLOOMY_NETHER_BRICK_STAIRS = InjectionHelper.nullValue();
    public static final BlockModStairs LIVELY_NETHER_BRICK_STAIRS = InjectionHelper.nullValue();
    public static final BlockModStairs FIERY_NETHER_BRICK_STAIRS = InjectionHelper.nullValue();
    public static final BlockModStairs ICY_NETHER_BRICK_STAIRS = InjectionHelper.nullValue();
    public static final BlockModStairs BASALT_STAIRS = InjectionHelper.nullValue();
    public static final BlockModStairs SMOOTH_BASALT_STAIRS = InjectionHelper.nullValue();
    public static final BlockModStairs BASALT_BRICK_STAIRS = InjectionHelper.nullValue();
    public static final BlockModStairs BASALT_PILLAR_STAIRS = InjectionHelper.nullValue();
    public static final BlockModWall QUARTZ_WALL = InjectionHelper.nullValue();
    public static final BlockModWall NETHER_BRICK_WALL = InjectionHelper.nullValue();
    public static final BlockModWall RED_NETHER_BRICK_WALL = InjectionHelper.nullValue();
    public static final BlockModWall GLOOMY_NETHER_BRICK_WALL = InjectionHelper.nullValue();
    public static final BlockModWall LIVELY_NETHER_BRICK_WALL = InjectionHelper.nullValue();
    public static final BlockModWall FIERY_NETHER_BRICK_WALL = InjectionHelper.nullValue();
    public static final BlockModWall ICY_NETHER_BRICK_WALL = InjectionHelper.nullValue();
    public static final BlockModWall BASALT_WALL = InjectionHelper.nullValue();
    public static final BlockModWall SMOOTH_BASALT_WALL = InjectionHelper.nullValue();
    public static final BlockModWall BASALT_BRICK_WALL = InjectionHelper.nullValue();
    public static final BlockModWall BASALT_PILLAR_WALL = InjectionHelper.nullValue();
    public static final BlockModFence QUARTZ_FENCE = InjectionHelper.nullValue();
    public static final BlockModFence RED_NETHER_BRICK_FENCE = InjectionHelper.nullValue();
    public static final BlockModFence GLOOMY_NETHER_BRICK_FENCE = InjectionHelper.nullValue();
    public static final BlockModFence LIVELY_NETHER_BRICK_FENCE = InjectionHelper.nullValue();
    public static final BlockModFence FIERY_NETHER_BRICK_FENCE = InjectionHelper.nullValue();
    public static final BlockModFence ICY_NETHER_BRICK_FENCE = InjectionHelper.nullValue();
    public static final BlockModFence BASALT_FENCE = InjectionHelper.nullValue();
    public static final BlockModFence SMOOTH_BASALT_FENCE = InjectionHelper.nullValue();
    public static final BlockModFence BASALT_BRICK_FENCE = InjectionHelper.nullValue();
    public static final BlockModFence BASALT_PILLAR_FENCE = InjectionHelper.nullValue();
    public static final BlockModFenceGate QUARTZ_FENCE_GATE = InjectionHelper.nullValue();
    public static final BlockModFenceGate NETHER_BRICK_FENCE_GATE = InjectionHelper.nullValue();
    public static final BlockModFenceGate RED_NETHER_BRICK_FENCE_GATE = InjectionHelper.nullValue();
    public static final BlockModFenceGate BASALT_FENCE_GATE = InjectionHelper.nullValue();
    public static final BlockModFenceGate SMOOTH_BASALT_FENCE_GATE = InjectionHelper.nullValue();
    public static final BlockModFenceGate BASALT_BRICK_FENCE_GATE = InjectionHelper.nullValue();
    public static final BlockModFenceGate BASALT_PILLAR_FENCE_GATE = InjectionHelper.nullValue();
    public static final BlockModFenceGate GLOOMY_NETHER_BRICK_FENCE_GATE = InjectionHelper.nullValue();
    public static final BlockModFenceGate LIVELY_NETHER_BRICK_FENCE_GATE = InjectionHelper.nullValue();
    public static final BlockModFenceGate FIERY_NETHER_BRICK_FENCE_GATE = InjectionHelper.nullValue();
    public static final BlockModFenceGate ICY_NETHER_BRICK_FENCE_GATE = InjectionHelper.nullValue();

    private static final ItemProperties DEFAULT_ITEM_BLOCK_PROPERTIES = new ItemProperties().creativeTab(NetherEx.instance.getCreativeTab());

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterBlocks(RegistryEvent.Register<Block> event)
        {
            event.getRegistry().registerAll(
                    new BlockNetherrack(NetherEx.getResource("gloomy_netherrack"), new BlockProperties(Material.ROCK, MapColor.BROWN).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.STONE).hardness(0.5F).resistance(2.0F)),
                    new BlockNetherrack(NetherEx.getResource("lively_netherrack"), new BlockProperties(Material.ROCK, MapColor.MAGENTA).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.STONE).hardness(0.5F).resistance(2.0F)),
                    new BlockNetherrack(NetherEx.getResource("fiery_netherrack"), new BlockProperties(Material.ROCK, MapColor.ADOBE).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.STONE).hardness(0.5F).resistance(2.0F)),
                    new BlockNetherrack(NetherEx.getResource("icy_netherrack"), new BlockProperties(Material.ROCK, MapColor.LIGHT_BLUE).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.STONE).hardness(0.5F).resistance(2.0F)),
                    new BlockModPath(NetherEx.getResource("netherrack_path"), Blocks.NETHERRACK, new BlockProperties(Material.ROCK, MapColor.RED).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModPath(NetherEx.getResource("gloomy_netherrack_path"), GLOOMY_NETHERRACK, new BlockProperties(Material.GROUND, MapColor.BROWN).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.STONE).hardness(0.5F).resistance(2.0F)),
                    new BlockModPath(NetherEx.getResource("lively_netherrack_path"), LIVELY_NETHERRACK, new BlockProperties(Material.GROUND, MapColor.MAGENTA).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.STONE).hardness(0.5F).resistance(2.0F)),
                    new BlockModPath(NetherEx.getResource("fiery_netherrack_path"), FIERY_NETHERRACK, new BlockProperties(Material.GROUND, MapColor.ADOBE).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.STONE).hardness(0.5F).resistance(2.0F)),
                    new BlockModPath(NetherEx.getResource("icy_netherrack_path"), ICY_NETHERRACK, new BlockProperties(Material.GROUND, MapColor.LIGHT_BLUE).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.STONE).hardness(0.5F).resistance(2.0F)),
                    new BlockMod(NetherEx.getResource("gloomy_nether_brick"), new BlockProperties(Material.ROCK, MapColor.BROWN).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.STONE).hardness(1.5F).resistance(10.0F)),
                    new BlockMod(NetherEx.getResource("lively_nether_brick"), new BlockProperties(Material.ROCK, MapColor.MAGENTA).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.STONE).hardness(1.5F).resistance(10.0F)),
                    new BlockMod(NetherEx.getResource("fiery_nether_brick"), new BlockProperties(Material.ROCK, MapColor.ADOBE).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.STONE).hardness(1.5F).resistance(10.0F)),
                    new BlockMod(NetherEx.getResource("icy_nether_brick"), new BlockProperties(Material.ROCK, MapColor.LIGHT_BLUE).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.STONE).hardness(1.5F).resistance(10.0F)),
                    new BlockMod(NetherEx.getResource("basalt"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockMod(NetherEx.getResource("smooth_basalt"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockMod(NetherEx.getResource("basalt_brick"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockMod(NetherEx.getResource("basalt_pillar"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockHyphae(),
                    new BlockFrostburnIce(),
                    new BlockSoulGlass(),
                    new BlockSoulGlassPane(),
                    new BlockAmethyst(),
                    new BlockRime(),
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
                    new BlockElderMushroom(NetherEx.getResource("brown_elder_mushroom")),
                    new BlockElderMushroom(NetherEx.getResource("red_elder_mushroom")),
                    new BlockModBigMushroom(NetherEx.getResource("brown_elder_mushroom_cap"), new BlockProperties(Material.WOOD, MapColor.BROWN).sound(SoundType.WOOD).harvestLevel(HarvestTool.AXE, HarvestLevel.WOOD).hardness(0.5F).hardness(2.0F)),
                    new BlockModBigMushroom(NetherEx.getResource("red_elder_mushroom_cap"), new BlockProperties(Material.WOOD, MapColor.RED).sound(SoundType.WOOD).harvestLevel(HarvestTool.AXE, HarvestLevel.WOOD).hardness(0.5F).hardness(2.0F)),
                    new BlockEnokiMushroomCap(),
                    new BlockElderMushroomStem(),
                    new BlockEnokiMushroomStem(),
                    new BlockModInfiniteFluid(NetherEx.getResource("ichor"), NetherExFluids.ICHOR, new BlockProperties(Material.WATER, MapColor.RED)),
                    new BlockModSlab(NetherEx.getResource("red_nether_brick_slab"), new BlockProperties(Material.ROCK, MapColor.RED).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModSlab(NetherEx.getResource("gloomy_nether_brick_slab"), new BlockProperties(Material.ROCK, MapColor.BROWN).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModSlab(NetherEx.getResource("lively_nether_brick_slab"), new BlockProperties(Material.ROCK, MapColor.MAGENTA).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModSlab(NetherEx.getResource("fiery_nether_brick_slab"), new BlockProperties(Material.ROCK, MapColor.ADOBE).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModSlab(NetherEx.getResource("icy_nether_brick_slab"), new BlockProperties(Material.ROCK, MapColor.LIGHT_BLUE).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModSlab(NetherEx.getResource("basalt_slab"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModSlab(NetherEx.getResource("smooth_basalt_slab"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModSlab(NetherEx.getResource("basalt_brick_slab"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModSlab(NetherEx.getResource("basalt_pillar_slab"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModStairs(NetherEx.getResource("red_nether_brick_stairs"), new BlockProperties(Material.ROCK, MapColor.RED).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModStairs(NetherEx.getResource("gloomy_nether_brick_stairs"), new BlockProperties(Material.ROCK, MapColor.BROWN).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModStairs(NetherEx.getResource("lively_nether_brick_stairs"), new BlockProperties(Material.ROCK, MapColor.MAGENTA).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModStairs(NetherEx.getResource("fiery_nether_brick_stairs"), new BlockProperties(Material.ROCK, MapColor.ADOBE).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModStairs(NetherEx.getResource("icy_nether_brick_stairs"), new BlockProperties(Material.ROCK, MapColor.LIGHT_BLUE).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModStairs(NetherEx.getResource("basalt_stairs"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModStairs(NetherEx.getResource("smooth_basalt_stairs"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModStairs(NetherEx.getResource("basalt_brick_stairs"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModStairs(NetherEx.getResource("basalt_pillar_stairs"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModWall(NetherEx.getResource("quartz_wall"), new BlockProperties(Material.ROCK, MapColor.SNOW).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModWall(NetherEx.getResource("nether_brick_wall"), new BlockProperties(Material.ROCK, MapColor.NETHERRACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModWall(NetherEx.getResource("red_nether_brick_wall"), new BlockProperties(Material.ROCK, MapColor.RED).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModWall(NetherEx.getResource("gloomy_nether_brick_wall"), new BlockProperties(Material.ROCK, MapColor.BROWN).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModWall(NetherEx.getResource("lively_nether_brick_wall"), new BlockProperties(Material.ROCK, MapColor.MAGENTA).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModWall(NetherEx.getResource("fiery_nether_brick_wall"), new BlockProperties(Material.ROCK, MapColor.ADOBE).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModWall(NetherEx.getResource("icy_nether_brick_wall"), new BlockProperties(Material.ROCK, MapColor.LIGHT_BLUE).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModWall(NetherEx.getResource("basalt_wall"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModWall(NetherEx.getResource("smooth_basalt_wall"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModWall(NetherEx.getResource("basalt_brick_wall"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModWall(NetherEx.getResource("basalt_pillar_wall"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModFence(NetherEx.getResource("quartz_fence"), new BlockProperties(Material.ROCK, MapColor.SNOW).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModFence(NetherEx.getResource("red_nether_brick_fence"), new BlockProperties(Material.ROCK, MapColor.RED).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModFence(NetherEx.getResource("gloomy_nether_brick_fence"), new BlockProperties(Material.ROCK, MapColor.BROWN).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModFence(NetherEx.getResource("lively_nether_brick_fence"), new BlockProperties(Material.ROCK, MapColor.MAGENTA).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModFence(NetherEx.getResource("fiery_nether_brick_fence"), new BlockProperties(Material.ROCK, MapColor.ADOBE).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModFence(NetherEx.getResource("icy_nether_brick_fence"), new BlockProperties(Material.ROCK, MapColor.LIGHT_BLUE).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModFence(NetherEx.getResource("basalt_fence"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModFence(NetherEx.getResource("smooth_basalt_fence"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModFence(NetherEx.getResource("basalt_brick_fence"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModFence(NetherEx.getResource("basalt_pillar_fence"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModFenceGate(NetherEx.getResource("quartz_fence_gate"), new BlockProperties(Material.ROCK, MapColor.SNOW).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModFenceGate(NetherEx.getResource("nether_brick_fence_gate"), new BlockProperties(Material.ROCK, MapColor.NETHERRACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModFenceGate(NetherEx.getResource("red_nether_brick_fence_gate"), new BlockProperties(Material.ROCK, MapColor.RED).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModFenceGate(NetherEx.getResource("gloomy_nether_brick_fence_gate"), new BlockProperties(Material.ROCK, MapColor.BROWN).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModFenceGate(NetherEx.getResource("lively_nether_brick_fence_gate"), new BlockProperties(Material.ROCK, MapColor.MAGENTA).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModFenceGate(NetherEx.getResource("fiery_nether_brick_fence_gate"), new BlockProperties(Material.ROCK, MapColor.ADOBE).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModFenceGate(NetherEx.getResource("icy_nether_brick_fence_gate"), new BlockProperties(Material.ROCK, MapColor.LIGHT_BLUE).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModFenceGate(NetherEx.getResource("basalt_fence_gate"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModFenceGate(NetherEx.getResource("smooth_basalt_fence_gate"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModFenceGate(NetherEx.getResource("basalt_brick_fence_gate"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F)),
                    new BlockModFenceGate(NetherEx.getResource("basalt_pillar_fence_gate"), new BlockProperties(Material.ROCK, MapColor.BLACK).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.WOOD).hardness(1.5F).resistance(10.0F))
            );
        }

        @SubscribeEvent
        public static void onRegisterItems(RegistryEvent.Register<Item> event)
        {
            event.getRegistry().registerAll(
                    new ItemBlockMod(GLOOMY_NETHERRACK, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(LIVELY_NETHERRACK, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(FIERY_NETHERRACK, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(ICY_NETHERRACK, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(NETHERRACK_PATH, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(GLOOMY_NETHERRACK_PATH, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(LIVELY_NETHERRACK_PATH, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(FIERY_NETHERRACK_PATH, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(ICY_NETHERRACK_PATH, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(GLOOMY_NETHER_BRICK, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(LIVELY_NETHER_BRICK, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(FIERY_NETHER_BRICK, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(ICY_NETHER_BRICK, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(BASALT, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(SMOOTH_BASALT, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(BASALT_BRICK, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(BASALT_PILLAR, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(HYPHAE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(FROSTBURN_ICE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(SOUL_GLASS, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(SOUL_GLASS_PANE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(AMETHYST_BLOCK, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(RIME_BLOCK, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(BONE_SLIVER, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(BONE_CHUNK, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(WORN_IRON, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(URN_OF_SORROW, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(QUARTZ_ORE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(AMETHYST_ORE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(RIME_ORE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockElderMushroom(BROWN_ELDER_MUSHROOM),
                    new ItemBlockElderMushroom(RED_ELDER_MUSHROOM),
                    new ItemBlockMod(BROWN_ELDER_MUSHROOM_CAP, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(RED_ELDER_MUSHROOM_CAP, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(ENOKI_MUSHROOM_CAP, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(ELDER_MUSHROOM_STEM, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(ENOKI_MUSHROOM_STEM, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(THORNSTALK, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(ICHOR, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockModSlab(RED_NETHER_BRICK_SLAB, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockModSlab(GLOOMY_NETHER_BRICK_SLAB, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockModSlab(LIVELY_NETHER_BRICK_SLAB, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockModSlab(FIERY_NETHER_BRICK_SLAB, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockModSlab(ICY_NETHER_BRICK_SLAB, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockModSlab(BASALT_SLAB, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockModSlab(SMOOTH_BASALT_SLAB, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockModSlab(BASALT_BRICK_SLAB, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockModSlab(BASALT_PILLAR_SLAB, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(RED_NETHER_BRICK_STAIRS, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(GLOOMY_NETHER_BRICK_STAIRS, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(LIVELY_NETHER_BRICK_STAIRS, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(FIERY_NETHER_BRICK_STAIRS, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(ICY_NETHER_BRICK_STAIRS, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(BASALT_STAIRS, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(SMOOTH_BASALT_STAIRS, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(BASALT_BRICK_STAIRS, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(BASALT_PILLAR_STAIRS, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(QUARTZ_WALL, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(NETHER_BRICK_WALL, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(RED_NETHER_BRICK_WALL, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(GLOOMY_NETHER_BRICK_WALL, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(LIVELY_NETHER_BRICK_WALL, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(FIERY_NETHER_BRICK_WALL, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(ICY_NETHER_BRICK_WALL, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(BASALT_WALL, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(SMOOTH_BASALT_WALL, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(BASALT_BRICK_WALL, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(BASALT_PILLAR_WALL, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(QUARTZ_FENCE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(RED_NETHER_BRICK_FENCE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(GLOOMY_NETHER_BRICK_FENCE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(LIVELY_NETHER_BRICK_FENCE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(FIERY_NETHER_BRICK_FENCE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(ICY_NETHER_BRICK_FENCE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(BASALT_FENCE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(SMOOTH_BASALT_FENCE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(BASALT_BRICK_FENCE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(BASALT_PILLAR_FENCE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(QUARTZ_FENCE_GATE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(NETHER_BRICK_FENCE_GATE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(RED_NETHER_BRICK_FENCE_GATE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(BASALT_FENCE_GATE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(SMOOTH_BASALT_FENCE_GATE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(BASALT_BRICK_FENCE_GATE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(BASALT_PILLAR_FENCE_GATE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(GLOOMY_NETHER_BRICK_FENCE_GATE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(LIVELY_NETHER_BRICK_FENCE_GATE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(FIERY_NETHER_BRICK_FENCE_GATE, DEFAULT_ITEM_BLOCK_PROPERTIES),
                    new ItemBlockMod(ICY_NETHER_BRICK_FENCE_GATE, DEFAULT_ITEM_BLOCK_PROPERTIES)
            );
        }
    }

    public static void addDispenserBehaviors()
    {
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Item.getItemFromBlock(AMETHYST_BLOCK), new Bootstrap.BehaviorDispenseOptional()
        {
            @Override
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
            {
                World world = source.getWorld();
                BlockPos pos = source.getBlockPos().offset(source.getBlockState().getValue(BlockDispenser.FACING));
                this.successful = true;

                if(world.isAirBlock(pos) && AMETHYST_BLOCK.canDispenserPlace(world, pos))
                {
                    if(!world.isRemote)
                    {
                        world.setBlockState(pos, AMETHYST_BLOCK.getDefaultState(), 3);
                    }

                    stack.shrink(1);
                }
                else
                {
                    ItemStack armorStack = ItemArmor.dispenseArmor(source, stack);

                    if(armorStack.isEmpty())
                    {
                        this.successful = false;
                    }
                }

                return stack;
            }
        });
    }

    public static ItemProperties getDefaultItemBlockProperties()
    {
        return DEFAULT_ITEM_BLOCK_PROPERTIES;
    }
}

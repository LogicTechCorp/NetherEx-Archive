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

package logictechcorp.netherex.handler;

import logictechcorp.libraryex.block.*;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.block.HyphaeBlock;
import logictechcorp.netherex.block.QuartzOreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.GameData;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockRegister
{
    private static final Set<Item> BLOCK_ITEMS = new HashSet<>();

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event)
    {
        registerBlock("gloomy_netherrack", new FireSustainingBlock(Block.Properties.create(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).hardnessAndResistance(0.4F)));
        registerBlock("lively_netherrack", new FireSustainingBlock(Block.Properties.create(Material.ROCK, DyeColor.PURPLE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(0.4F)));
        registerBlock("fiery_netherrack", new FireSustainingBlock(Block.Properties.create(Material.ROCK, DyeColor.ORANGE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(0.4F)));
        registerBlock("icy_netherrack", new FireSustainingBlock(Block.Properties.create(Material.ROCK, DyeColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(0.4F)));

        registerBlock("basalt", new Block(Block.Properties.create(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
        registerBlock("hyphae", new HyphaeBlock());
        registerBlock("frostburn_ice", new UnmeltableIceBlock(Block.Properties.create(Material.ICE, DyeColor.LIGHT_BLUE).sound(SoundType.GLASS).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 1.0F)));

        registerBlock("quartz_ore", new QuartzOreBlock());
        registerBlock("amethyst_ore", new ExperienceDroppingBlock(1, 5, Block.Properties.create(Material.ROCK, MaterialColor.NETHERRACK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(3.0F)));
        registerBlock("rime_ore", new ExperienceDroppingBlock(2, 6, Block.Properties.create(Material.ROCK, DyeColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).hardnessAndResistance(3.0F)));

        registerBlock("thornstalk", new TriplePlantBlock(Block.Properties.create(Material.PLANTS, DyeColor.BROWN).doesNotBlockMovement().hardnessAndResistance(0.0F).sound(SoundType.PLANT)));

        registerBlock("netherrack_path", new PathBlock(Block.Properties.create(Material.ROCK, DyeColor.RED).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 2.0F), RegistryObject.of("minecraft:netherrack", () -> Block.class)));
        registerBlock("gloomy_netherrack_path", new PathBlock(Block.Properties.create(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 2.0F), RegistryObject.of(NetherEx.MOD_ID + ":gloomy_netherrack", () -> Block.class)));
        registerBlock("lively_netherrack_path", new PathBlock(Block.Properties.create(Material.ROCK, DyeColor.PURPLE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 2.0F), RegistryObject.of(NetherEx.MOD_ID + ":lively_netherrack", () -> Block.class)));
        registerBlock("fiery_netherrack_path", new PathBlock(Block.Properties.create(Material.ROCK, DyeColor.ORANGE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 2.0F), RegistryObject.of(NetherEx.MOD_ID + ":fiery_netherrack", () -> Block.class)));
        registerBlock("icy_netherrack_path", new PathBlock(Block.Properties.create(Material.ROCK, DyeColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(0.5F, 2.0F), RegistryObject.of(NetherEx.MOD_ID + ":icy_netherrack", () -> Block.class)));

        registerBlock("gloomy_nether_bricks", new Block(Block.Properties.create(Material.ROCK, DyeColor.BROWN).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
        registerBlock("lively_nether_bricks", new Block(Block.Properties.create(Material.ROCK, DyeColor.PURPLE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
        registerBlock("fiery_nether_bricks", new Block(Block.Properties.create(Material.ROCK, DyeColor.ORANGE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
        registerBlock("icy_nether_bricks", new Block(Block.Properties.create(Material.ROCK, DyeColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));

        registerBlock("smooth_basalt", new Block(Block.Properties.create(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
        registerBlock("basalt_brick", new Block(Block.Properties.create(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
        registerBlock("basalt_pillar", new Block(Block.Properties.create(Material.ROCK, DyeColor.BLACK).harvestTool(ToolType.PICKAXE).harvestLevel(0).hardnessAndResistance(1.5F, 10.0F)));
    }

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event)
    {
        BLOCK_ITEMS.forEach(item -> event.getRegistry().register(item));
    }

    private static void registerBlock(String name, Block block)
    {
        ResourceLocation registryName = GameData.checkPrefix(name, false);
        ForgeRegistries.BLOCKS.register(block.setRegistryName(registryName));
        BLOCK_ITEMS.add(new BlockItem(block, new Item.Properties().group(NetherEx.ITEM_GROUP)).setRegistryName(registryName));
    }

    private static void registerBlock(String name, Block block, BlockItem item)
    {
        ResourceLocation registryName = new ResourceLocation(name);
        ForgeRegistries.BLOCKS.register(block.setRegistryName(registryName));
        BLOCK_ITEMS.add(item.setRegistryName(registryName));
    }
}

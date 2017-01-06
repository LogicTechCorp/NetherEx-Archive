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

package nex.client.model.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockWall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import nex.NetherEx;
import nex.block.BlockBasalt;
import nex.block.BlockMushroom;
import nex.block.BlockNetherrack;
import nex.block.BlockVanilla;
import nex.item.ItemSalamanderHide;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static nex.init.NetherExBlocks.*;
import static nex.init.NetherExItems.*;

@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber(Side.CLIENT)
public class NetherExModels
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExModels");

    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event)
    {
        LOGGER.info("Model registration started.");

        ModelLoader.setCustomStateMapper(SLAB_VANILLA_DOUBLE, new StateMap.Builder().ignore(BlockSlab.HALF).build());
        ModelLoader.setCustomStateMapper(SLAB_BASALT_DOUBLE, new StateMap.Builder().ignore(BlockSlab.HALF).build());
        ModelLoader.setCustomStateMapper(WALL_VANILLA, new StateMap.Builder().ignore(BlockWall.VARIANT).build());
        ModelLoader.setCustomStateMapper(WALL_BASALT, new StateMap.Builder().ignore(BlockWall.VARIANT).build());
        ModelLoader.setCustomStateMapper(FENCE_GATE_QUARTZ, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(FENCE_GATE_NETHER_BRICK, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(FENCE_GATE_RED_NETHER_BRICK, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(FENCE_GATE_BASALT, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(FENCE_GATE_BASALT_SMOOTH, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(FENCE_GATE_BASALT_BRICK, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(FENCE_GATE_BASALT_PILLAR, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(FENCE_GATE_NETHER_BRICK_FIERY, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(FENCE_GATE_NETHER_BRICK_ICY, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(FENCE_GATE_NETHER_BRICK_LIVELY, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(FENCE_GATE_NETHER_BRICK_GLOOMY, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(FENCE_GATE_NETHER_BRICK_HOLY, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());

        for(BlockBasalt.EnumType type : BlockBasalt.EnumType.values())
        {
            registerModel(BLOCK_BASALT, type.ordinal(), BLOCK_BASALT.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(SLAB_BASALT, type.ordinal(), SLAB_BASALT.getRegistryName().toString(), String.format("half=bottom,type=%s", type.getName()));
            registerModel(SLAB_BASALT_DOUBLE, type.ordinal(), SLAB_BASALT_DOUBLE.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(WALL_BASALT, type.ordinal(), String.format("nex:wall_basalt_%s", type.getName()), "inventory");
            registerModel(FENCE_BASALT, type.ordinal(), String.format("nex:fence_basalt_%s", type.getName()), "inventory");
        }

        for(BlockNetherrack.EnumType type : BlockNetherrack.EnumType.values())
        {
            registerModel(BLOCK_NETHERRACK, type.ordinal(), BLOCK_NETHERRACK.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(ORE_QUARTZ, type.ordinal(), ORE_QUARTZ.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(BLOCK_NETHER_BRICK, type.ordinal(), BLOCK_NETHER_BRICK.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(SLAB_NETHER_BRICK, type.ordinal(), SLAB_NETHER_BRICK.getRegistryName().toString(), String.format("half=bottom,type=%s", type.getName()));
            registerModel(SLAB_NETHER_BRICK_DOUBLE, type.ordinal(), SLAB_NETHER_BRICK_DOUBLE.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(WALL_NETHER_BRICK, type.ordinal(), String.format("nex:wall_nether_brick_%s", type.getName()), "inventory");
            registerModel(FENCE_NETHER_BRICK, type.ordinal(), String.format("nex:fence_nether_brick_%s", type.getName()), "inventory");
            registerModel(ITEM_NETHER_BRICK, type.ordinal(), ITEM_NETHER_BRICK.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }

        registerModel(BLOCK_HYPHAE, "normal");
        registerModel(BLOCK_SAND_SOUL_TILLED, "moisture=0");

        registerModel(PLANT_THORNSTALK, "normal");

        for(BlockMushroom.EnumType type : BlockMushroom.EnumType.values())
        {
            registerModel(PLANT_MUSHROOM_BROWN, type.ordinal(), PLANT_MUSHROOM_BROWN.getRegistryName().toString(), String.format("variant=%s", type.getName()));
            registerModel(PLANT_MUSHROOM_RED, type.ordinal(), PLANT_MUSHROOM_RED.getRegistryName().toString(), String.format("variant=%s", type.getName()));
        }

        registerModel(PLANT_ENOKI_STEM, "normal");
        registerModel(PLANT_ENOKI_CAP, "normal");

        registerModel(FLUID_ICHOR);

        for(BlockVanilla.EnumTypeSlab type : BlockVanilla.EnumTypeSlab.values())
        {
            registerModel(SLAB_VANILLA, type.ordinal(), SLAB_VANILLA.getRegistryName().toString(), String.format("half=bottom,type=%s", type.getName()));
            registerModel(SLAB_VANILLA_DOUBLE, type.ordinal(), SLAB_VANILLA_DOUBLE.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }

        ModelLoader.setCustomStateMapper(SLAB_NETHER_BRICK_DOUBLE, new StateMap.Builder().ignore(BlockSlab.HALF).build());

        registerModel(STAIRS_RED_NETHER_BRICK, "normal");
        registerModel(STAIRS_BASALT_NORMAL, "normal");
        registerModel(STAIRS_BASALT_SMOOTH, "normal");
        registerModel(STAIRS_BASALT_BRICK, "normal");
        registerModel(STAIRS_BASALT_PILLAR, "normal");
        registerModel(STAIRS_NETHER_BRICK_FIERY, "normal");
        registerModel(STAIRS_NETHER_BRICK_ICY, "normal");
        registerModel(STAIRS_NETHER_BRICK_LIVELY, "normal");
        registerModel(STAIRS_NETHER_BRICK_GLOOMY, "normal");
        registerModel(STAIRS_NETHER_BRICK_HOLY, "normal");

        for(BlockVanilla.EnumTypeWall type : BlockVanilla.EnumTypeWall.values())
        {
            registerModel(WALL_VANILLA, type.ordinal(), String.format("nex:wall_%s", type.getName()), "inventory");
        }
        ModelLoader.setCustomStateMapper(WALL_NETHER_BRICK, new StateMap.Builder().ignore(BlockWall.VARIANT).build());

        for(BlockVanilla.EnumTypeFence type : BlockVanilla.EnumTypeFence.values())
        {
            registerModel(FENCE_VANILLA, type.ordinal(), String.format("nex:fence_%s", type.getName()), "inventory");
        }

        registerModel(FENCE_GATE_QUARTZ, "normal");
        registerModel(FENCE_GATE_NETHER_BRICK, "normal");
        registerModel(FENCE_GATE_RED_NETHER_BRICK, "normal");
        registerModel(FENCE_GATE_BASALT, "normal");
        registerModel(FENCE_GATE_BASALT_SMOOTH, "normal");
        registerModel(FENCE_GATE_BASALT_BRICK, "normal");
        registerModel(FENCE_GATE_BASALT_PILLAR, "normal");
        registerModel(FENCE_GATE_NETHER_BRICK_FIERY, "normal");
        registerModel(FENCE_GATE_NETHER_BRICK_ICY, "normal");
        registerModel(FENCE_GATE_NETHER_BRICK_LIVELY, "normal");
        registerModel(FENCE_GATE_NETHER_BRICK_GLOOMY, "normal");
        registerModel(FENCE_GATE_NETHER_BRICK_HOLY, "normal");

        registerModel(ITEM_BONE_WITHERED, "normal");
        registerModel(ITEM_BONE_MEAL_WITHERED, "normal");

        for(ItemSalamanderHide.EnumType type : ItemSalamanderHide.EnumType.values())
        {
            registerModel(ITEM_HIDE_SALAMANDER, type.ordinal(), ITEM_HIDE_SALAMANDER.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }

        registerModel(FOOD_MEAT_GHAST_RAW, "normal");
        registerModel(FOOD_MEAT_GHAST_COOKED, "normal");
        registerModel(FOOD_MAGMA_CREAM_CONGEALED, "normal");
        registerModel(FOOD_MUSHROOM_ENOKI, "normal");

        registerModel(TOOL_SWORD_BONE, "normal");
        registerModel(TOOL_PICKAXE_BONE, "normal");
        registerModel(TOOL_SHOVEL_BONE, "normal");
        registerModel(TOOL_AXE_BONE, "normal");
        registerModel(TOOL_HOE_BONE, "normal");

        registerModel(ARMOR_HELMET_BONE, "normal");
        registerModel(ARMOR_CHESTPLATE_BONE, "normal");
        registerModel(ARMOR_LEGGINGS_BONE, "normal");
        registerModel(ARMOR_BOOTS_BONE, "normal");
        registerModel(ARMOR_HELMET_HIDE_SALAMANDER, "normal");
        registerModel(ARMOR_CHESTPLATE_HIDE_SALAMANDER, "normal");
        registerModel(ARMOR_LEGGINGS_HIDE_SALAMANDER, "normal");
        registerModel(ARMOR_BOOTS_HIDE_SALAMANDER, "normal");

        LOGGER.info("Model registration completed.");
    }

    private static void registerModel(IFluidBlock block)
    {
        Item item = Item.getItemFromBlock((Block) block);
        ModelBakery.registerItemVariants(item);

        ModelResourceLocation modelLocation = new ModelResourceLocation(NetherEx.MOD_ID + ":fluid", block.getFluid().getName());

        ModelLoader.setCustomMeshDefinition(item, MeshDefinitionFix.create(stack -> modelLocation));

        ModelLoader.setCustomStateMapper((Block) block, new StateMapperBase()
        {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state)
            {
                return modelLocation;
            }
        });
    }

    private static void registerModel(Block block, String location)
    {
        ModelResourceLocation modelLocation = new ModelResourceLocation(block.getRegistryName(), location);
        ModelBakery.registerItemVariants(Item.getItemFromBlock(block), modelLocation);
        registerModel(block, MeshDefinitionFix.create(stack -> modelLocation));
    }

    private static void registerModel(Item item, String location)
    {
        ModelResourceLocation modelLocation = new ModelResourceLocation(item.getRegistryName(), location);
        ModelBakery.registerItemVariants(item, modelLocation);
        registerModel(item, MeshDefinitionFix.create(stack -> modelLocation));

    }

    private static void registerModel(Block block, int metadata, String location, String variant)
    {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), metadata, new ModelResourceLocation(location, variant));
    }

    private static void registerModel(Item item, int metadata, String location, String variant)
    {
        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(location, variant));
    }

    private static void registerModel(Block block, ItemMeshDefinition definition)
    {
        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(block), definition);
    }

    private static void registerModel(Item item, ItemMeshDefinition definition)
    {
        ModelLoader.setCustomMeshDefinition(item, definition);
    }
}

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
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.NetherEx;
import nex.block.*;
import nex.item.ItemSalamanderHide;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static nex.init.NetherExBlocks.*;
import static nex.init.NetherExItems.*;

@SideOnly(Side.CLIENT)
@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, value = Side.CLIENT)
public class NetherExModels
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExModels");

    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event)
    {
        LOGGER.info("Model registration started.");

        ModelLoader.setCustomStateMapper(BLUE_FIRE, new StateMap.Builder().ignore(BlockBlueFire.AGE).build());
        ModelLoader.setCustomStateMapper(VANILLA_SLAB_DOUBLE, new StateMap.Builder().ignore(BlockSlab.HALF).build());
        ModelLoader.setCustomStateMapper(BASALT_SLAB_DOUBLE, new StateMap.Builder().ignore(BlockSlab.HALF).build());
        ModelLoader.setCustomStateMapper(NETHER_BRICK_SLAB_DOUBLE, new StateMap.Builder().ignore(BlockSlab.HALF).build());
        ModelLoader.setCustomStateMapper(VANILLA_WALL, new StateMap.Builder().ignore(BlockWall.VARIANT).build());
        ModelLoader.setCustomStateMapper(BASALT_WALL, new StateMap.Builder().ignore(BlockWall.VARIANT).build());
        ModelLoader.setCustomStateMapper(NETHER_BRICK_WALL, new StateMap.Builder().ignore(BlockWall.VARIANT).build());
        ModelLoader.setCustomStateMapper(QUARTZ_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(NETHER_BRICK_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(RED_NETHER_BRICK_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(BASALT_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(BASALT_SMOOTH_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(BASALT_BRICK_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(BASALT_PILLAR_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(FIERY_NETHER_BRICK_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(ICY_NETHER_BRICK_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(LIVELY_NETHER_BRICK_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(GLOOMY_NETHER_BRICK_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());

        for(BlockBasalt.EnumType type : BlockBasalt.EnumType.values())
        {
            registerModel(BASALT, type.ordinal(), BASALT.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(BASALT_SLAB, type.ordinal(), BASALT_SLAB.getRegistryName().toString(), String.format("half=bottom,type=%s", type.getName()));
            registerModel(BASALT_SLAB_DOUBLE, type.ordinal(), BASALT_SLAB_DOUBLE.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(BASALT_WALL, type.ordinal(), String.format(BASALT_WALL.getRegistryName().toString().replace("fence", "%s_wall"), type.getName()), "inventory");
            registerModel(BASALT_FENCE, type.ordinal(), String.format(BASALT_FENCE.getRegistryName().toString().replace("fence", "%s_fence"), type.getName()).replace("normal_", ""), "inventory");
        }

        for(BlockNetherrack.EnumType type : BlockNetherrack.EnumType.values())
        {
            registerModel(NETHERRACK, type.ordinal(), String.format(NetherEx.MOD_ID + ":%s_" + NETHERRACK.getRegistryName().getResourcePath(), type.getName()), "inventory");
            registerModel(QUARTZ_ORE, type.ordinal(), QUARTZ_ORE.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(NETHER_BRICK, type.ordinal(), NETHER_BRICK.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(NETHER_BRICK_SLAB, type.ordinal(), NETHER_BRICK_SLAB.getRegistryName().toString(), String.format("half=bottom,type=%s", type.getName()));
            registerModel(NETHER_BRICK_SLAB_DOUBLE, type.ordinal(), NETHER_BRICK_SLAB_DOUBLE.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(NETHER_BRICK_WALL, type.ordinal(), String.format(NetherEx.MOD_ID + ":%s_" + NETHER_BRICK_WALL.getRegistryName().getResourcePath(), type.getName()), "inventory");
            registerModel(NETHER_BRICK_FENCE, type.ordinal(), String.format(NetherEx.MOD_ID + ":%s_" + NETHER_BRICK_FENCE.getRegistryName().getResourcePath(), type.getName()), "inventory");
            registerModel(ITEM_BRICK_NETHER, type.ordinal(), ITEM_BRICK_NETHER.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }

        for(BlockNetherrackPath.EnumType type : BlockNetherrackPath.EnumType.values())
        {
            registerModel(NETHERRACK_PATH, type.ordinal(), NETHERRACK_PATH.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }

        registerModel(HYPHAE, "normal");
        registerModel(TILLED_SOUL_SAND, "moisture=0");
        registerModel(SOUL_GLASS, "normal");
        registerModel(SOUL_GLASS_PANE, "normal");

        for(BlockGem.EnumType type : BlockGem.EnumType.values())
        {
            registerModel(GEM_BLOCK, type.ordinal(), GEM_BLOCK.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(GEM_ORE, type.ordinal(), GEM_ORE.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }


        registerModel(RIME_BLOCK, "normal");
        registerModel(FROSTBURN_ICE, "normal");
        registerModel(BONE_SLIVER, "axis=y");
        registerModel(BONE_CHUNK, "facing=up");
        registerModel(WORN_IRON, "normal");
        registerModel(BLUE_FIRE, "normal");

        for(EnumFacing.Axis axis : EnumFacing.Axis.values())
        {
            registerModel(NETHER_PORTAL, axis.ordinal(), NETHER_PORTAL.getRegistryName().toString(), String.format("axis=%s", axis.getName()));
        }

        for(BlockUrnOfSorrow.EnumType type : BlockUrnOfSorrow.EnumType.values())
        {
            registerModel(URN_OF_SORROW, type.ordinal(), URN_OF_SORROW.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }

        registerModel(RIME_ORE, "normal");

        registerModel(THORNSTALK, "normal");

        for(BlockElderMushroom.EnumType type : BlockElderMushroom.EnumType.values())
        {
            registerModel(ELDER_MUSHROOM, type.ordinal(), String.format(NetherEx.MOD_ID + ":%s_" + ELDER_MUSHROOM.getRegistryName().getResourcePath(), type.getName()), "inventory");
            registerModel(ELDER_MUSHROOM_CAP, type.ordinal(), ELDER_MUSHROOM_CAP.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }

        for(BlockElderMushroomStem.EnumType type : BlockElderMushroomStem.EnumType.values())
        {
            registerModel(ELDER_MUSHROOM_STEM, type.ordinal(), ELDER_MUSHROOM_STEM.getRegistryName().toString(), String.format("axis=%s", type.getName()));
        }

        registerModel(ENOKI_MUSHROOM_STEM, "normal");
        registerModel(ENOKI_MUSHROOM_CAP, "normal");

        registerModel(ICHOR);

        for(BlockVanilla.EnumTypeSlab type : BlockVanilla.EnumTypeSlab.values())
        {
            registerModel(VANILLA_SLAB, type.ordinal(), VANILLA_SLAB.getRegistryName().toString(), String.format("half=bottom,type=%s", type.getName()));
            registerModel(VANILLA_SLAB_DOUBLE, type.ordinal(), VANILLA_SLAB_DOUBLE.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }

        registerModel(RED_NETHER_BRICK_STAIRS, "normal");
        registerModel(BASALT_STAIRS, "normal");
        registerModel(BASALT_SMOOTH_STAIRS, "normal");
        registerModel(BASALT_BRICK_STAIRS, "normal");
        registerModel(BASALT_PILLAR_STAIRS, "normal");
        registerModel(FIERY_NETHER_BRICK_STAIRS, "normal");
        registerModel(ICY_NETHER_BRICK_STAIRS, "normal");
        registerModel(LIVELY_NETHER_BRICK_STAIRS, "normal");
        registerModel(GLOOMY_NETHER_BRICK_STAIRS, "normal");

        for(BlockVanilla.EnumTypeWall type : BlockVanilla.EnumTypeWall.values())
        {
            registerModel(VANILLA_WALL, type.ordinal(), String.format(NetherEx.MOD_ID + ":%s_wall", type.getName()), "inventory");
        }

        for(BlockVanilla.EnumTypeFence type : BlockVanilla.EnumTypeFence.values())
        {
            registerModel(VANILLA_FENCE, type.ordinal(), String.format(NetherEx.MOD_ID + ":%s_fence", type.getName()), "inventory");
        }

        registerModel(QUARTZ_FENCE_GATE, "normal");
        registerModel(NETHER_BRICK_FENCE_GATE, "normal");
        registerModel(RED_NETHER_BRICK_FENCE_GATE, "normal");
        registerModel(BASALT_FENCE_GATE, "normal");
        registerModel(BASALT_SMOOTH_FENCE_GATE, "normal");
        registerModel(BASALT_BRICK_FENCE_GATE, "normal");
        registerModel(BASALT_PILLAR_FENCE_GATE, "normal");
        registerModel(FIERY_NETHER_BRICK_FENCE_GATE, "normal");
        registerModel(ICY_NETHER_BRICK_FENCE_GATE, "normal");
        registerModel(LIVELY_NETHER_BRICK_FENCE_GATE, "normal");
        registerModel(GLOOMY_NETHER_BRICK_FENCE_GATE, "normal");

        registerModel(ITEM_BONE_WITHER, "normal");
        registerModel(ITEM_DUST_WITHER, "normal");

        for(ItemSalamanderHide.EnumType type : ItemSalamanderHide.EnumType.values())
        {
            registerModel(ITEM_HIDE_SALAMANDER, type.ordinal(), ITEM_HIDE_SALAMANDER.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }

        registerModel(ITEM_CRYSTAL_AMETHYST, "normal");
        registerModel(ITEM_CRYSTAL_RIME, "normal");
        registerModel(ITEM_CRYSTAL_RIME_STEEL, "normal");
        registerModel(ITEM_SPORE, "normal");
        registerModel(ITEM_FANG_SPIDER_BONE, "normal");
        registerModel(ITEM_TEAR_GHAST_QUEEN, "normal");
        registerModel(ITEM_BOAT_OBSIDIAN, "normal");

        registerModel(FOOD_MEAT_GHAST_RAW, "normal");
        registerModel(FOOD_MEAT_GHAST_COOKED, "normal");
        registerModel(FOOD_MAGMA_CREAM_CONGEALED, "normal");
        registerModel(FOOD_MUSHROOM_ENOKI, "normal");

        registerModel(TOOL_SWORD_BONE, "normal");
        registerModel(TOOL_PICKAXE_BONE, "normal");
        registerModel(TOOL_SHOVEL_BONE, "normal");
        registerModel(TOOL_AXE_BONE, "normal");
        registerModel(TOOL_HOE_BONE, "normal");
        registerModel(TOOL_HAMMER_BONE, "normal");

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

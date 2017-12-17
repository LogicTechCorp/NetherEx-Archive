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
import nex.init.NetherExBlocks;
import nex.init.NetherExItems;
import nex.item.ItemSalamanderHide;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SideOnly(Side.CLIENT)
@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, value = Side.CLIENT)
public class NetherExModels
{
    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event)
    {
        NetherEx.LOGGER.info("Model registration started.");

        ModelLoader.setCustomStateMapper(NetherExBlocks.BLUE_FIRE, new StateMap.Builder().ignore(BlockBlueFire.AGE).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.VANILLA_SLAB_DOUBLE, new StateMap.Builder().ignore(BlockSlab.HALF).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.BASALT_SLAB_DOUBLE, new StateMap.Builder().ignore(BlockSlab.HALF).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.NETHER_BRICK_SLAB_DOUBLE, new StateMap.Builder().ignore(BlockSlab.HALF).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.VANILLA_WALL, new StateMap.Builder().ignore(BlockWall.VARIANT).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.BASALT_WALL, new StateMap.Builder().ignore(BlockWall.VARIANT).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.NETHER_BRICK_WALL, new StateMap.Builder().ignore(BlockWall.VARIANT).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.QUARTZ_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.NETHER_BRICK_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.RED_NETHER_BRICK_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.BASALT_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.BASALT_SMOOTH_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.BASALT_BRICK_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.BASALT_PILLAR_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.FIERY_NETHER_BRICK_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.ICY_NETHER_BRICK_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.LIVELY_NETHER_BRICK_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.GLOOMY_NETHER_BRICK_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());

        for(BlockBasalt.EnumType type : BlockBasalt.EnumType.values())
        {
            registerModel(NetherExBlocks.BASALT, type.ordinal(), NetherExBlocks.BASALT.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(NetherExBlocks.BASALT_SLAB, type.ordinal(), NetherExBlocks.BASALT_SLAB.getRegistryName().toString(), String.format("half=bottom,type=%s", type.getName()));
            registerModel(NetherExBlocks.BASALT_SLAB_DOUBLE, type.ordinal(), NetherExBlocks.BASALT_SLAB_DOUBLE.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(NetherExBlocks.BASALT_WALL, type.ordinal(), String.format(NetherExBlocks.BASALT_WALL.getRegistryName().toString().replace("fence", "%s_wall"), type.getName()), "inventory");
            registerModel(NetherExBlocks.BASALT_FENCE, type.ordinal(), String.format(NetherExBlocks.BASALT_FENCE.getRegistryName().toString().replace("fence", "%s_fence"), type.getName()).replace("normal_", ""), "inventory");
        }

        for(BlockNetherrack.EnumType type : BlockNetherrack.EnumType.values())
        {
            registerModel(NetherExBlocks.NETHERRACK, type.ordinal(), String.format(NetherEx.MOD_ID + ":%s_" + NetherExBlocks.NETHERRACK.getRegistryName().getResourcePath(), type.getName()), "inventory");
            registerModel(NetherExBlocks.QUARTZ_ORE, type.ordinal(), NetherExBlocks.QUARTZ_ORE.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(NetherExBlocks.NETHER_BRICK, type.ordinal(), NetherExBlocks.NETHER_BRICK.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(NetherExBlocks.NETHER_BRICK_SLAB, type.ordinal(), NetherExBlocks.NETHER_BRICK_SLAB.getRegistryName().toString(), String.format("half=bottom,type=%s", type.getName()));
            registerModel(NetherExBlocks.NETHER_BRICK_SLAB_DOUBLE, type.ordinal(), NetherExBlocks.NETHER_BRICK_SLAB_DOUBLE.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(NetherExBlocks.NETHER_BRICK_WALL, type.ordinal(), String.format(NetherEx.MOD_ID + ":%s_" + NetherExBlocks.NETHER_BRICK_WALL.getRegistryName().getResourcePath(), type.getName()), "inventory");
            registerModel(NetherExBlocks.NETHER_BRICK_FENCE, type.ordinal(), String.format(NetherEx.MOD_ID + ":%s_" + NetherExBlocks.NETHER_BRICK_FENCE.getRegistryName().getResourcePath(), type.getName()), "inventory");
            registerModel(NetherExItems.NETHERBRICK, type.ordinal(), NetherExItems.NETHERBRICK.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }

        for(BlockNetherrackPath.EnumType type : BlockNetherrackPath.EnumType.values())
        {
            registerModel(NetherExBlocks.NETHERRACK_PATH, type.ordinal(), NetherExBlocks.NETHERRACK_PATH.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }

        registerModel(NetherExBlocks.HYPHAE, "normal");
        registerModel(NetherExBlocks.TILLED_SOUL_SAND, "moisture=0");
        registerModel(NetherExBlocks.SOUL_GLASS, "normal");
        registerModel(NetherExBlocks.SOUL_GLASS_PANE, "normal");

        for(BlockGem.EnumType type : BlockGem.EnumType.values())
        {
            registerModel(NetherExBlocks.GEM_BLOCK, type.ordinal(), NetherExBlocks.GEM_BLOCK.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(NetherExBlocks.GEM_ORE, type.ordinal(), NetherExBlocks.GEM_ORE.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(NetherExItems.GEM, type.ordinal(), NetherExItems.GEM.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }


        registerModel(NetherExBlocks.FROSTBURN_ICE, "normal");
        registerModel(NetherExBlocks.BONE_SLIVER, "axis=y");
        registerModel(NetherExBlocks.BONE_CHUNK, "facing=up");
        registerModel(NetherExBlocks.WORN_IRON, "normal");
        registerModel(NetherExBlocks.BLUE_FIRE, "normal");

        for(EnumFacing.Axis axis : EnumFacing.Axis.values())
        {
            registerModel(NetherExBlocks.NETHER_PORTAL, axis.ordinal(), NetherExBlocks.NETHER_PORTAL.getRegistryName().toString(), String.format("axis=%s", axis.getName()));
        }

        for(BlockUrnOfSorrow.EnumType type : BlockUrnOfSorrow.EnumType.values())
        {
            registerModel(NetherExBlocks.URN_OF_SORROW, type.ordinal(), NetherExBlocks.URN_OF_SORROW.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }

        registerModel(NetherExBlocks.THORNSTALK, "normal");

        for(BlockElderMushroom.EnumType type : BlockElderMushroom.EnumType.values())
        {
            registerModel(NetherExBlocks.ELDER_MUSHROOM, type.ordinal(), String.format(NetherEx.MOD_ID + ":%s_" + NetherExBlocks.ELDER_MUSHROOM.getRegistryName().getResourcePath(), type.getName()), "inventory");
            registerModel(NetherExBlocks.ELDER_MUSHROOM_CAP, type.ordinal(), NetherExBlocks.ELDER_MUSHROOM_CAP.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }

        for(BlockElderMushroomStem.EnumType type : BlockElderMushroomStem.EnumType.values())
        {
            registerModel(NetherExBlocks.ELDER_MUSHROOM_STEM, type.ordinal(), NetherExBlocks.ELDER_MUSHROOM_STEM.getRegistryName().toString(), String.format("axis=%s", type.getName()));
        }

        registerModel(NetherExBlocks.ENOKI_MUSHROOM_STEM, "normal");
        registerModel(NetherExBlocks.ENOKI_MUSHROOM_CAP, "normal");

        registerModel(NetherExBlocks.ICHOR);

        for(BlockVanilla.EnumTypeSlab type : BlockVanilla.EnumTypeSlab.values())
        {
            registerModel(NetherExBlocks.VANILLA_SLAB, type.ordinal(), NetherExBlocks.VANILLA_SLAB.getRegistryName().toString(), String.format("half=bottom,type=%s", type.getName()));
            registerModel(NetherExBlocks.VANILLA_SLAB_DOUBLE, type.ordinal(), NetherExBlocks.VANILLA_SLAB_DOUBLE.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }

        registerModel(NetherExBlocks.RED_NETHER_BRICK_STAIRS, "normal");
        registerModel(NetherExBlocks.BASALT_STAIRS, "normal");
        registerModel(NetherExBlocks.BASALT_SMOOTH_STAIRS, "normal");
        registerModel(NetherExBlocks.BASALT_BRICK_STAIRS, "normal");
        registerModel(NetherExBlocks.BASALT_PILLAR_STAIRS, "normal");
        registerModel(NetherExBlocks.FIERY_NETHER_BRICK_STAIRS, "normal");
        registerModel(NetherExBlocks.ICY_NETHER_BRICK_STAIRS, "normal");
        registerModel(NetherExBlocks.LIVELY_NETHER_BRICK_STAIRS, "normal");
        registerModel(NetherExBlocks.GLOOMY_NETHER_BRICK_STAIRS, "normal");

        for(BlockVanilla.EnumTypeWall type : BlockVanilla.EnumTypeWall.values())
        {
            registerModel(NetherExBlocks.VANILLA_WALL, type.ordinal(), String.format(NetherEx.MOD_ID + ":%s_wall", type.getName()), "inventory");
        }

        for(BlockVanilla.EnumTypeFence type : BlockVanilla.EnumTypeFence.values())
        {
            registerModel(NetherExBlocks.VANILLA_FENCE, type.ordinal(), String.format(NetherEx.MOD_ID + ":%s_fence", type.getName()), "inventory");
        }

        registerModel(NetherExBlocks.QUARTZ_FENCE_GATE, "normal");
        registerModel(NetherExBlocks.NETHER_BRICK_FENCE_GATE, "normal");
        registerModel(NetherExBlocks.RED_NETHER_BRICK_FENCE_GATE, "normal");
        registerModel(NetherExBlocks.BASALT_FENCE_GATE, "normal");
        registerModel(NetherExBlocks.BASALT_SMOOTH_FENCE_GATE, "normal");
        registerModel(NetherExBlocks.BASALT_BRICK_FENCE_GATE, "normal");
        registerModel(NetherExBlocks.BASALT_PILLAR_FENCE_GATE, "normal");
        registerModel(NetherExBlocks.FIERY_NETHER_BRICK_FENCE_GATE, "normal");
        registerModel(NetherExBlocks.ICY_NETHER_BRICK_FENCE_GATE, "normal");
        registerModel(NetherExBlocks.LIVELY_NETHER_BRICK_FENCE_GATE, "normal");
        registerModel(NetherExBlocks.GLOOMY_NETHER_BRICK_FENCE_GATE, "normal");

        registerModel(NetherExItems.WITHER_BONE, "normal");
        registerModel(NetherExItems.WITHER_DUST, "normal");

        for(ItemSalamanderHide.EnumType type : ItemSalamanderHide.EnumType.values())
        {
            registerModel(NetherExItems.SALAMANDER_HIDE, type.ordinal(), NetherExItems.SALAMANDER_HIDE.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }

        registerModel(NetherExItems.RIME_AND_STEEL, "normal");
        registerModel(NetherExItems.SPORE, "normal");
        registerModel(NetherExItems.BONE_SPIDER_FANG, "normal");
        registerModel(NetherExItems.GHAST_QUEEN_TEAR, "normal");
        registerModel(NetherExItems.OBSIDIAN_BOAT, "normal");
        registerModel(NetherExItems.GHAST_MEAT_RAW, "normal");
        registerModel(NetherExItems.GHAST_MEAT_COOKED, "normal");
        registerModel(NetherExItems.CONGEALED_MAGMA_CREAM, "normal");
        registerModel(NetherExItems.ENOKI_MUSHROOM, "normal");
        registerModel(NetherExItems.GOLDEN_WITHER_BONE_SWORD, "normal");
        registerModel(NetherExItems.GOLDEN_WITHER_BONE_PICKAXE, "normal");
        registerModel(NetherExItems.GOLDEN_WITHER_BONE_SHOVEL, "normal");
        registerModel(NetherExItems.GOLDEN_WITHER_BONE_AXE, "normal");
        registerModel(NetherExItems.GOLDEN_WITHER_BONE_HOE, "normal");
        registerModel(NetherExItems.GOLDEN_WITHER_BONE_HAMMER, "normal");
        registerModel(NetherExItems.WITHER_BONE_HELMET, "normal");
        registerModel(NetherExItems.WITHER_BONE_CHESTPLATE, "normal");
        registerModel(NetherExItems.WITHER_BONE_LEGGINGS, "normal");
        registerModel(NetherExItems.WITHER_BONE_BOOTS, "normal");
        registerModel(NetherExItems.SALAMANDER_HIDE_HELMET, "normal");
        registerModel(NetherExItems.SALAMANDER_HIDE_CHESTPLATE, "normal");
        registerModel(NetherExItems.SALAMANDER_HIDE_LEGGINGS, "normal");
        registerModel(NetherExItems.SALAMANDER_HIDE_BOOTS, "normal");

        NetherEx.LOGGER.info("Model registration completed.");
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

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

package logictechcorp.netherex.client.model.item;

import logictechcorp.libraryex.block.BlockModLeaf;
import logictechcorp.libraryex.client.util.ModelHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.block.BlockBlueFire;
import logictechcorp.netherex.init.NetherExBlocks;
import logictechcorp.netherex.init.NetherExItems;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockWall;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = NetherEx.MOD_ID)
public class NetherExModels
{
    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event)
    {
        registerBlockModels();
        registerItemModels();
    }

    private static void registerBlockModels()
    {
        StateMapperBase leafDecayMapper = new StateMap.Builder().ignore(BlockModLeaf.DECAY).build();
        StateMapperBase slabHalfMapper = new StateMap.Builder().ignore(BlockSlab.HALF).build();
        StateMapperBase wallVariantMapper = new StateMap.Builder().ignore(BlockWall.VARIANT).build();
        StateMapperBase fenceGatePowerMapper = new StateMap.Builder().ignore(BlockFenceGate.POWERED).build();

        ModelLoader.setCustomStateMapper(NetherExBlocks.GENESIS_LEAF, leafDecayMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.BLUE_FIRE, new StateMap.Builder().ignore(BlockBlueFire.AGE).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.RED_NETHER_BRICK_SLAB, slabHalfMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.GLOOMY_NETHER_BRICK_SLAB, slabHalfMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.LIVELY_NETHER_BRICK_SLAB, slabHalfMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.FIERY_NETHER_BRICK_SLAB, slabHalfMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.ICY_NETHER_BRICK_SLAB, slabHalfMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.BASALT_SLAB, slabHalfMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.SMOOTH_BASALT_SLAB, slabHalfMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.BASALT_BRICK_SLAB, slabHalfMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.BASALT_PILLAR_SLAB, slabHalfMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.GENESIS_PLANK_SLAB, slabHalfMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.QUARTZ_WALL, wallVariantMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.NETHER_BRICK_WALL, wallVariantMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.RED_NETHER_BRICK_WALL, wallVariantMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.GLOOMY_NETHER_BRICK_WALL, wallVariantMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.LIVELY_NETHER_BRICK_WALL, wallVariantMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.FIERY_NETHER_BRICK_WALL, wallVariantMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.ICY_NETHER_BRICK_WALL, wallVariantMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.BASALT_WALL, wallVariantMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.SMOOTH_BASALT_WALL, wallVariantMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.BASALT_BRICK_WALL, wallVariantMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.BASALT_PILLAR_WALL, wallVariantMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.QUARTZ_FENCE_GATE, fenceGatePowerMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.NETHER_BRICK_FENCE_GATE, fenceGatePowerMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.RED_NETHER_BRICK_FENCE_GATE, fenceGatePowerMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.BASALT_FENCE_GATE, fenceGatePowerMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.SMOOTH_BASALT_FENCE_GATE, fenceGatePowerMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.BASALT_BRICK_FENCE_GATE, fenceGatePowerMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.BASALT_PILLAR_FENCE_GATE, fenceGatePowerMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.FIERY_NETHER_BRICK_FENCE_GATE, fenceGatePowerMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.ICY_NETHER_BRICK_FENCE_GATE, fenceGatePowerMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.LIVELY_NETHER_BRICK_FENCE_GATE, fenceGatePowerMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.GLOOMY_NETHER_BRICK_FENCE_GATE, fenceGatePowerMapper);
        ModelLoader.setCustomStateMapper(NetherExBlocks.GENESIS_PLANK_FENCE_GATE, fenceGatePowerMapper);

        ModelHelper.registerBlockItemModel(NetherExBlocks.GLOOMY_NETHERRACK.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.LIVELY_NETHERRACK.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.FIERY_NETHERRACK.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.ICY_NETHERRACK.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.NETHERRACK_PATH.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.GLOOMY_NETHERRACK_PATH.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.LIVELY_NETHERRACK_PATH.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.FIERY_NETHERRACK_PATH.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.ICY_NETHERRACK_PATH.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.GLOOMY_NETHER_BRICK.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.LIVELY_NETHER_BRICK.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.FIERY_NETHER_BRICK.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.ICY_NETHER_BRICK.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.CRYING_OBSIDIAN.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.GLOWING_OBSIDIAN.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.BASALT.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.SMOOTH_BASALT.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.BASALT_BRICK.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.BASALT_PILLAR.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.HYPHAE.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.FROSTBURN_ICE.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.TILLED_SOUL_SAND.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.GENESIS_GRASS.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.GENESIS_LOG.getDefaultState(), "axis=y");
        ModelHelper.registerBlockItemModel(NetherExBlocks.GENESIS_PLANK.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.SOUL_GLASS.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.SOUL_GLASS_PANE.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.AMETHYST_BLOCK.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.RIME_BLOCK.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.COBALT_BLOCK.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.ARDITE_BLOCK.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.BONE_SLIVER.getDefaultState(), "axis=y");
        ModelHelper.registerBlockItemModel(NetherExBlocks.BONE_CHUNK.getDefaultState(), "facing=up");
        ModelHelper.registerBlockItemModel(NetherExBlocks.WORN_IRON.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.URN_OF_SORROW.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.QUARTZ_ORE.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.AMETHYST_ORE.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.RIME_ORE.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.COBALT_ORE.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.ARDITE_ORE.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.THORNSTALK.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.BROWN_ELDER_MUSHROOM.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.RED_ELDER_MUSHROOM.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.BROWN_ELDER_MUSHROOM_CAP.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.RED_ELDER_MUSHROOM_CAP.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.ELDER_MUSHROOM_STEM.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.ENOKI_MUSHROOM_CAP.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.ENOKI_MUSHROOM_STEM.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.GENESIS_LEAF.getDefaultState(), "normal");
        ModelHelper.registerBlockItemModel(NetherExBlocks.GENESIS_SAPLING.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.CYAN_ROSE.getDefaultState());
        ModelHelper.registerBlockItemModel(NetherExBlocks.RED_NETHER_BRICK_SLAB.getDefaultState(), "type=bottom");
        ModelHelper.registerBlockItemModel(NetherExBlocks.GLOOMY_NETHER_BRICK_SLAB.getDefaultState(), "type=bottom");
        ModelHelper.registerBlockItemModel(NetherExBlocks.LIVELY_NETHER_BRICK_SLAB.getDefaultState(), "type=bottom");
        ModelHelper.registerBlockItemModel(NetherExBlocks.FIERY_NETHER_BRICK_SLAB.getDefaultState(), "type=bottom");
        ModelHelper.registerBlockItemModel(NetherExBlocks.ICY_NETHER_BRICK_SLAB.getDefaultState(), "type=bottom");
        ModelHelper.registerBlockItemModel(NetherExBlocks.BASALT_SLAB.getDefaultState(), "type=bottom");
        ModelHelper.registerBlockItemModel(NetherExBlocks.SMOOTH_BASALT_SLAB.getDefaultState(), "type=bottom");
        ModelHelper.registerBlockItemModel(NetherExBlocks.BASALT_BRICK_SLAB.getDefaultState(), "type=bottom");
        ModelHelper.registerBlockItemModel(NetherExBlocks.BASALT_PILLAR_SLAB.getDefaultState(), "type=bottom");
        ModelHelper.registerBlockItemModel(NetherExBlocks.GENESIS_PLANK_SLAB.getDefaultState(), "type=bottom");
        ModelHelper.registerBlockItemModel(NetherExBlocks.RED_NETHER_BRICK_STAIRS.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.FIERY_NETHER_BRICK_STAIRS.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.ICY_NETHER_BRICK_STAIRS.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.LIVELY_NETHER_BRICK_STAIRS.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.GLOOMY_NETHER_BRICK_STAIRS.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.BASALT_STAIRS.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.SMOOTH_BASALT_STAIRS.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.BASALT_BRICK_STAIRS.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.BASALT_PILLAR_STAIRS.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.GENESIS_PLANK_STAIRS.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.QUARTZ_WALL.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.NETHER_BRICK_WALL.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.RED_NETHER_BRICK_WALL.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.GLOOMY_NETHER_BRICK_WALL.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.LIVELY_NETHER_BRICK_WALL.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.FIERY_NETHER_BRICK_WALL.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.ICY_NETHER_BRICK_WALL.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.BASALT_WALL.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.SMOOTH_BASALT_WALL.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.BASALT_BRICK_WALL.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.BASALT_PILLAR_WALL.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.QUARTZ_FENCE.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.RED_NETHER_BRICK_FENCE.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.GLOOMY_NETHER_BRICK_FENCE.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.LIVELY_NETHER_BRICK_FENCE.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.FIERY_NETHER_BRICK_FENCE.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.ICY_NETHER_BRICK_FENCE.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.BASALT_FENCE.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.SMOOTH_BASALT_FENCE.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.BASALT_BRICK_FENCE.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.BASALT_PILLAR_FENCE.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.GENESIS_PLANK_FENCE.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.QUARTZ_FENCE_GATE.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.NETHER_BRICK_FENCE_GATE.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.RED_NETHER_BRICK_FENCE_GATE.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.FIERY_NETHER_BRICK_FENCE_GATE.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.ICY_NETHER_BRICK_FENCE_GATE.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.LIVELY_NETHER_BRICK_FENCE_GATE.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.GLOOMY_NETHER_BRICK_FENCE_GATE.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.BASALT_FENCE_GATE.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.SMOOTH_BASALT_FENCE_GATE.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.BASALT_BRICK_FENCE_GATE.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.BASALT_PILLAR_FENCE_GATE.getDefaultState(), "inventory");
        ModelHelper.registerBlockItemModel(NetherExBlocks.GENESIS_PLANK_FENCE_GATE.getDefaultState(), "inventory");

        ModelHelper.registerFluidModel(NetherEx.instance, NetherExBlocks.ICHOR);
    }

    private static void registerItemModels()
    {
        ModelHelper.registerItemModel(NetherExItems.GLOOMY_NETHERBRICK);
        ModelHelper.registerItemModel(NetherExItems.LIVELY_NETHERBRICK);
        ModelHelper.registerItemModel(NetherExItems.FIERY_NETHERBRICK);
        ModelHelper.registerItemModel(NetherExItems.ICY_NETHERBRICK);
        ModelHelper.registerItemModel(NetherExItems.WITHER_BONE);
        ModelHelper.registerItemModel(NetherExItems.WITHER_DUST);
        ModelHelper.registerItemModel(NetherExItems.FROST_ROD);
        ModelHelper.registerItemModel(NetherExItems.FROST_POWDER);
        ModelHelper.registerItemModel(NetherExItems.BLAZED_WITHER_BONE);
        ModelHelper.registerItemModel(NetherExItems.FROSTED_WITHER_BONE);
        ModelHelper.registerItemModel(NetherExItems.ORANGE_SALAMANDER_HIDE);
        ModelHelper.registerItemModel(NetherExItems.BLACK_SALAMANDER_HIDE);
        ModelHelper.registerItemModel(NetherExItems.AMETHYST_CRYSTAL);
        ModelHelper.registerItemModel(NetherExItems.RIME_CRYSTAL);
        ModelHelper.registerItemModel(NetherExItems.COBALT_INGOT);
        ModelHelper.registerItemModel(NetherExItems.ARDITE_INGOT);
        ModelHelper.registerItemModel(NetherExItems.RIME_AND_STEEL);
        ModelHelper.registerItemModel(NetherExItems.SPORE);
        ModelHelper.registerItemModel(NetherExItems.COOLMAR_SPIDER_FANG);
        ModelHelper.registerItemModel(NetherExItems.GHAST_QUEEN_TEAR);
        ModelHelper.registerItemModel(NetherExItems.OBSIDIAN_BOAT);
        ModelHelper.registerItemModel(NetherExItems.GHAST_MEAT_RAW);
        ModelHelper.registerItemModel(NetherExItems.GHAST_MEAT_COOKED);
        ModelHelper.registerItemModel(NetherExItems.CONGEALED_MAGMA_CREAM);
        ModelHelper.registerItemModel(NetherExItems.ENOKI_MUSHROOM);
        ModelHelper.registerItemModel(NetherExItems.DULL_MIRROR);
        ModelHelper.registerItemModel(NetherExItems.WITHERED_AMEDIAN_SWORD);
        ModelHelper.registerItemModel(NetherExItems.WITHERED_AMEDIAN_PICKAXE);
        ModelHelper.registerItemModel(NetherExItems.WITHERED_AMEDIAN_SHOVEL);
        ModelHelper.registerItemModel(NetherExItems.WITHERED_AMEDIAN_AXE);
        ModelHelper.registerItemModel(NetherExItems.WITHERED_AMEDIAN_HOE);
        ModelHelper.registerItemModel(NetherExItems.WITHERED_AMEDIAN_HAMMER);
        ModelHelper.registerItemModel(NetherExItems.BLAZED_AMEDIAN_SWORD);
        ModelHelper.registerItemModel(NetherExItems.BLAZED_AMEDIAN_PICKAXE);
        ModelHelper.registerItemModel(NetherExItems.BLAZED_AMEDIAN_SHOVEL);
        ModelHelper.registerItemModel(NetherExItems.BLAZED_AMEDIAN_AXE);
        ModelHelper.registerItemModel(NetherExItems.BLAZED_AMEDIAN_HOE);
        ModelHelper.registerItemModel(NetherExItems.BLAZED_AMEDIAN_HAMMER);
        ModelHelper.registerItemModel(NetherExItems.FROSTED_AMEDIAN_SWORD);
        ModelHelper.registerItemModel(NetherExItems.FROSTED_AMEDIAN_PICKAXE);
        ModelHelper.registerItemModel(NetherExItems.FROSTED_AMEDIAN_SHOVEL);
        ModelHelper.registerItemModel(NetherExItems.FROSTED_AMEDIAN_AXE);
        ModelHelper.registerItemModel(NetherExItems.FROSTED_AMEDIAN_HOE);
        ModelHelper.registerItemModel(NetherExItems.FROSTED_AMEDIAN_HAMMER);
        ModelHelper.registerItemModel(NetherExItems.WITHER_BONE_HELMET);
        ModelHelper.registerItemModel(NetherExItems.WITHER_BONE_CHESTPLATE);
        ModelHelper.registerItemModel(NetherExItems.WITHER_BONE_LEGGINGS);
        ModelHelper.registerItemModel(NetherExItems.WITHER_BONE_BOOTS);
        ModelHelper.registerItemModel(NetherExItems.ORANGE_SALAMANDER_HIDE_HELMET);
        ModelHelper.registerItemModel(NetherExItems.ORANGE_SALAMANDER_HIDE_CHESTPLATE);
        ModelHelper.registerItemModel(NetherExItems.ORANGE_SALAMANDER_HIDE_LEGGINGS);
        ModelHelper.registerItemModel(NetherExItems.ORANGE_SALAMANDER_HIDE_BOOTS);
        ModelHelper.registerItemModel(NetherExItems.BLACK_SALAMANDER_HIDE_HELMET);
        ModelHelper.registerItemModel(NetherExItems.BLACK_SALAMANDER_HIDE_CHESTPLATE);
        ModelHelper.registerItemModel(NetherExItems.BLACK_SALAMANDER_HIDE_LEGGINGS);
        ModelHelper.registerItemModel(NetherExItems.BLACK_SALAMANDER_HIDE_BOOTS);

    }
}

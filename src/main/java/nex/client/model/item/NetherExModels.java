/*
 * Copyright (C) 2016.  LogicTechCorp
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
import nex.block.BlockNetherrack;
import nex.block.BlockVanilla;
import nex.init.NetherExBlocks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber(Side.CLIENT)
public class NetherExModels
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExModels");

    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event)
    {
        ModelLoader.setCustomStateMapper(NetherExBlocks.SLAB_VANILLA, new StateMap.Builder().ignore(NetherExBlocks.SLAB_VANILLA.HALF).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.SLAB_VANILLA_DOUBLE, new StateMap.Builder().ignore(NetherExBlocks.SLAB_VANILLA_DOUBLE.HALF).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.WALL_VANILLA, new StateMap.Builder().ignore(NetherExBlocks.WALL_VANILLA.VARIANT).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.FENCE_GATE_QUARTZ, new StateMap.Builder().ignore(NetherExBlocks.FENCE_GATE_QUARTZ.POWERED).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.FENCE_GATE_NETHER_BRICK, new StateMap.Builder().ignore(NetherExBlocks.FENCE_GATE_NETHER_BRICK.POWERED).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.FENCE_GATE_RED_NETHER_BRICK, new StateMap.Builder().ignore(NetherExBlocks.FENCE_GATE_RED_NETHER_BRICK.POWERED).build());

        for(BlockVanilla.EnumTypeSlab type : BlockVanilla.EnumTypeSlab.values())
        {
            registerModel(NetherExBlocks.SLAB_VANILLA, type.ordinal(), NetherExBlocks.SLAB_VANILLA.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(NetherExBlocks.SLAB_VANILLA_DOUBLE, type.ordinal(), NetherExBlocks.SLAB_VANILLA_DOUBLE.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }

        for(BlockVanilla.EnumTypeWall type : BlockVanilla.EnumTypeWall.values())
        {
            registerModel(NetherExBlocks.WALL_VANILLA, type.ordinal(), String.format("nex:wall_%s", type.getName()), "inventory");
        }

        for(BlockVanilla.EnumTypeFence type : BlockVanilla.EnumTypeFence.values())
        {
            registerModel(NetherExBlocks.FENCE_VANILLA, type.ordinal(), String.format("nex:fence_%s", type.getName()), "inventory");
        }

        registerModel(NetherExBlocks.STAIRS_RED_NETHER_BRICK, "normal");
        registerModel(NetherExBlocks.FENCE_GATE_QUARTZ, "normal");
        registerModel(NetherExBlocks.FENCE_GATE_NETHER_BRICK, "normal");
        registerModel(NetherExBlocks.FENCE_GATE_RED_NETHER_BRICK, "normal");

        ModelLoader.setCustomStateMapper(NetherExBlocks.SLAB_BASALT, new StateMap.Builder().ignore(NetherExBlocks.SLAB_BASALT.HALF).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.SLAB_BASALT_DOUBLE, new StateMap.Builder().ignore(NetherExBlocks.SLAB_BASALT.HALF).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.WALL_BASALT, new StateMap.Builder().ignore(NetherExBlocks.WALL_BASALT.VARIANT).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.FENCE_GATE_BASALT, new StateMap.Builder().ignore(NetherExBlocks.FENCE_GATE_BASALT.POWERED).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.FENCE_GATE_BASALT_SMOOTH, new StateMap.Builder().ignore(NetherExBlocks.FENCE_GATE_BASALT_SMOOTH.POWERED).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.FENCE_GATE_BASALT_BRICK, new StateMap.Builder().ignore(NetherExBlocks.FENCE_GATE_BASALT_BRICK.POWERED).build());

        for(BlockBasalt.EnumType type : BlockBasalt.EnumType.values())
        {
            registerModel(NetherExBlocks.BLOCK_BASALT, type.ordinal(), NetherExBlocks.BLOCK_BASALT.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(NetherExBlocks.SLAB_BASALT, type.ordinal(), NetherExBlocks.SLAB_BASALT.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(NetherExBlocks.SLAB_BASALT_DOUBLE, type.ordinal(), NetherExBlocks.SLAB_BASALT_DOUBLE.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(NetherExBlocks.FENCE_BASALT, type.ordinal(), NetherExBlocks.FENCE_BASALT.getRegistryName().toString(), String.format("east=false,north=true,south=true,type=%s,west=false", type.getName()));
        }

        for(BlockBasalt.EnumType type : BlockBasalt.EnumType.values())
        {
            registerModel(NetherExBlocks.WALL_BASALT, type.ordinal(), String.format("nex:wall_basalt_%s", type.getName()), "inventory");
            registerModel(NetherExBlocks.FENCE_BASALT, type.ordinal(), String.format("nex:fence_basalt_%s", type.getName()), "inventory");
        }

        registerModel(NetherExBlocks.STAIRS_BASALT_NORMAL, "normal");
        registerModel(NetherExBlocks.STAIRS_BASALT_SMOOTH, "normal");
        registerModel(NetherExBlocks.STAIRS_BASALT_BRICK, "normal");
        registerModel(NetherExBlocks.FENCE_GATE_BASALT, "normal");
        registerModel(NetherExBlocks.FENCE_GATE_BASALT_SMOOTH, "normal");
        registerModel(NetherExBlocks.FENCE_GATE_BASALT_BRICK, "normal");

        for(BlockNetherrack.EnumType type : BlockNetherrack.EnumType.values())
        {
            registerModel(NetherExBlocks.BLOCK_NETHERRACK, type.ordinal(), NetherExBlocks.BLOCK_NETHERRACK.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(NetherExBlocks.BLOCK_NETHER_BRICK, type.ordinal(), NetherExBlocks.BLOCK_NETHER_BRICK.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }

        LOGGER.info("Model registration has been completed.");
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

    private static void registerModel(Object object, String location)
    {
        if(object instanceof Block)
        {
            ModelResourceLocation modelLocation = new ModelResourceLocation(((Block) object).getRegistryName(), location);
            ModelBakery.registerItemVariants((Item.getItemFromBlock((Block) object)), modelLocation);
            registerModel(object, MeshDefinitionFix.create(stack -> modelLocation));
        }
        else if(object instanceof Item)
        {
            ModelResourceLocation modelLocation = new ModelResourceLocation(((Item) object).getRegistryName(), location);
            ModelBakery.registerItemVariants(((Item) object), modelLocation);
            registerModel(object, MeshDefinitionFix.create(stack -> modelLocation));
        }
    }

    private static void registerModel(Object object, int metadata, String location, String variant)
    {
        if(object instanceof Block)
        {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock((Block) object), metadata, new ModelResourceLocation(location, variant));
        }
        else if(object instanceof Item)
        {
            ModelLoader.setCustomModelResourceLocation((Item) object, metadata, new ModelResourceLocation(location, variant));
        }
    }

    private static void registerModel(Object object, ItemMeshDefinition definition)
    {
        if(object instanceof Block)
        {
            ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock((Block) object), definition);
        }
        else if(object instanceof Item)
        {
            ModelLoader.setCustomMeshDefinition((Item) object, definition);
        }
    }
}
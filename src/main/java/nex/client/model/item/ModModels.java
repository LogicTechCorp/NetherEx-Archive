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
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.block.IVariantContainer;
import nex.init.ModItems;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ModModels
{
    @SubscribeEvent
    public static void registerAllModels(ModelRegistryEvent event)
    {
        ModItems.variantContainers.forEach(ModModels::registerModels);
    }

    private static void registerModels(IVariantContainer container)
    {
        Item item = (Item) container;
        String[] variants = container.getVariants();

        if(item instanceof ItemBlock && ((ItemBlock) item).getBlock() instanceof IVariantContainer)
        {
            Block block = ((ItemBlock) item).getBlock();

            if(!item.getHasSubtypes())
            {
                registerBlockItemModel(block, new ModelResourceLocation(block.getRegistryName(), "normal"));
            }
            else
            {
                for(int i = 0; i < variants.length; i++)
                {
                    registerBlockItemModelForMeta(block, i, container.getPropertyName() + "=" + variants[i].toLowerCase());
                }
            }
        }
        else
        {
            if(!item.getHasSubtypes())
            {
                registerItemModel(item, new ModelResourceLocation(item.getRegistryName(), "normal"));
            }
            else
            {
                for(int i = 0; i < variants.length; i++)
                {
                    registerItemModelForMeta(item, i, container.getPropertyName() + "=" + variants[i].toLowerCase());
                }
            }
        }
    }

    private static void registerBlockItemModel(Block block, ModelResourceLocation location)
    {
        registerItemModel(Item.getItemFromBlock(block), location);
    }

    private static void registerBlockItemModelForMeta(Block block, int metadata, String variant)
    {
        registerItemModelForMeta(Item.getItemFromBlock(block), metadata, variant);
    }

    private static void registerItemModel(Item item, ModelResourceLocation location)
    {
        ModelBakery.registerItemVariants(item, location);
        registerItemModel(item, MeshDefinitionFix.create(stack -> location));
    }

    private static void registerItemModel(Item item, ItemMeshDefinition definition)
    {
        ModelLoader.setCustomMeshDefinition(item, definition);
    }

    private static void registerItemModelForMeta(Item item, int metadata, String variant)
    {
        registerItemModelForMeta(item, metadata, new ModelResourceLocation(item.getRegistryName(), variant));
    }

    private static void registerItemModelForMeta(Item item, int metadata, ModelResourceLocation location)
    {
        ModelLoader.setCustomModelResourceLocation(item, metadata, location);
    }
}
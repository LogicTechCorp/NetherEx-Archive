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
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import nex.block.BlockBone;
import nex.block.BlockLog;
import nex.init.NetherExBlocks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(Side.CLIENT)
public class NetherExModels
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExModels");

    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event)
    {
        ModelLoader.setCustomStateMapper(NetherExBlocks.SAPLING, new StateMap.Builder().ignore(NetherExBlocks.SAPLING.STAGE).build());
        ModelLoader.setCustomStateMapper(NetherExBlocks.LEAVES, new StateMap.Builder().ignore(NetherExBlocks.LEAVES.DECAYABLE, NetherExBlocks.LEAVES.CHECK_DECAY).build());

        for(BlockLog.EnumType type : BlockLog.EnumType.values())
        {
            registerModel(NetherExBlocks.LOG, type.ordinal(), NetherExBlocks.LOG.getRegistryName().toString(), String.format("axis=y,type=%s", type.getName()));
            registerModel(NetherExBlocks.PLANKS, type.ordinal(), NetherExBlocks.PLANKS.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(NetherExBlocks.SAPLING, type.ordinal(), NetherExBlocks.SAPLING.getRegistryName().toString(), String.format("type=%s", type.getName()));
            registerModel(NetherExBlocks.LEAVES, type.ordinal(), NetherExBlocks.LEAVES.getRegistryName().toString(), String.format("type=%s", type.getName()));

        }

        for(BlockBone.EnumType type : BlockBone.EnumType.values())
        {
            registerModel(NetherExBlocks.BLOCK_BONE, type.ordinal(), NetherExBlocks.BLOCK_BONE.getRegistryName().toString(), String.format("axis=y,size=%s", type.getName()));
        }

        LOGGER.info("Model registration has been completed.");
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
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

package nex.handler;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Remaps this mod's {@link Block}s and {@link Item}s after registry names have been changed.
 * <p>
 * Written by Choonster here:
 * https://github.com/Choonster/TestMod3/blob/1.11.2/src/main/java/choonster/testmod3/remap/Remapper.java
 *
 * @author Choonster
 */
public class RemapHandler
{
    private static final Map<String, String> customNames = ImmutableMap.<String, String>builder()
            .put("block_nether_brick", "block_brick_nether")
            .put("slab_nether_brick", "slab_brick_nether")
            .put("slab_nether_brick_double", "slab_brick_nether_double")
            .put("stairs_nether_brick_red", "stairs_brick_nether_red")
            .put("stairs_nether_brick_fiery", "stairs_brick_nether_fiery")
            .put("stairs_nether_brick_icy", "stairs_brick_nether_icy")
            .put("stairs_nether_brick_lively", "stairs_brick_nether_lively")
            .put("stairs_nether_brick_gloomy", "stairs_brick_nether_gloomy")
            .put("wall_nether_brick", "wall_brick_nether")
            .put("fence_nether_brick", "fence_brick_nether")
            .put("fence_nether_brick_red", "fence_brick_nether_red")
            .put("fence_gate_nether_brick", "fence_gate_brick_nether")
            .put("fence_gate_nether_brick_red", "fence_gate_brick_nether_red")
            .put("fence_gate_nether_brick_fiery", "fence_gate_brick_nether_fiery")
            .put("fence_gate_nether_brick_icy", "fence_gate_brick_nether_icy")
            .put("fence_gate_nether_brick_lively", "fence_gate_brick_nether_lively")
            .put("fence_gate_nether_brick_gloomy", "fence_gate_brick_nether_gloomy")
            .put("item_nether_brick", "item_brick_nether")
            .put("plant_enoki_cap", "plant_mushroom_enoki_cap")
            .put("plant_enoki_stem", "plant_mushroom_enoki_stem")
            .put("plant_mushroom_elder", "plant_mushroom_elder_cap")
            .build();

    private static List<Predicate<FMLMissingMappingsEvent.MissingMapping>> mappingsToFix = ImmutableList.of(RemapHandler::remapRegistryName);

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|RemapHandler");

    public static void remap(List<FMLMissingMappingsEvent.MissingMapping> mappings)
    {
        LOGGER.info("Fix Missing Mappings started.");

        for(FMLMissingMappingsEvent.MissingMapping mapping : mappings)
        {
            for(Predicate<FMLMissingMappingsEvent.MissingMapping> remappingFunction : mappingsToFix)
            {
                if(remappingFunction.test(mapping))
                {
                    break;
                }
            }
        }

        LOGGER.info("Fix Missing Mappings completed.");
    }

    private static boolean attemptRemap(FMLMissingMappingsEvent.MissingMapping mapping, ResourceLocation registryName)
    {
        switch(mapping.type)
        {
            case BLOCK:
                IForgeRegistry<Block> blockRegistry = ForgeRegistries.BLOCKS;

                if(blockRegistry.containsKey(registryName))
                {
                    mapping.remap(blockRegistry.getValue(registryName));
                    return true;
                }

                break;
            case ITEM:
                IForgeRegistry<Item> itemRegistry = ForgeRegistries.ITEMS;

                if(itemRegistry.containsKey(registryName))
                {
                    mapping.remap(itemRegistry.getValue(registryName));
                    return true;
                }
                break;
        }

        return false;
    }

    private static boolean remapRegistryName(FMLMissingMappingsEvent.MissingMapping mapping)
    {
        String missingPath = mapping.resourceLocation.getResourcePath();

        if(!customNames.containsKey(missingPath))
        {
            return false;
        }

        String newPath = customNames.get(missingPath);
        ResourceLocation registryName = new ResourceLocation(mapping.resourceLocation.getResourceDomain() + newPath);
        return attemptRemap(mapping, registryName);
    }
}

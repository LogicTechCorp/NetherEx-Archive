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
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import nex.NetherEx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Remaps this mod's {@link Block}s and {@link Item}s after registry names have been changed.
 * <p>
 * Written by Choonster here:
 * https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/2cb7b67adf7ab41e066c3308ac898224b2891752/src/main/java/choonster/testmod3/remap/Remapper.java
 *
 * @author Choonster
 */
public class RemapHandler<T extends IForgeRegistryEntry<T>>
{
    private static final Map<String, String> customNames = ImmutableMap.<String, String>builder()
            .put("block_basalt", "basalt")
            .put("block_netherrack", "netherrack")
            .put("block_brick_nether", "nether_brick")
            .put("block_netherrack_path", "netherrack_path")
            .put("block_hyphae", "hyphae")
            .put("block_sand_soul_tilled", "tilled_soul_sand")
            .put("block_glass_soul", "soul_glass")
            .put("block_glass_pane_soul", "soul_glass_pane")
            .put("block_amethyst", "gem_block")
            .put("block_rime", "rime_block")
            .put("block_ice_frostburn", "frostburn_ice")
            .put("block_bone_sliver", "bone_sliver")
            .put("block_bone_chunk", "bone_chunk")
            .put("block_iron_worn", "worn_iron")
            .put("block_fire_blue", "blue_fire")
            .put("block_portal_nether", "nether_portal")
            .put("tile_urn_sorrow", "urn_of_sorrow")
            .put("ore_quartz", "quartz_ore")
            .put("ore_amethyst", "gem_ore")
            .put("ore_rime", "rime_ore")
            .put("plant_thornstalk", "thornstalk")
            .put("plant_mushroom_elder", "elder_mushroom")
            .put("plant_mushroom_elder_cap", "elder_mushroom_cap")
            .put("plant_mushroom_elder_stem", "elder_mushroom_stem")
            .put("plant_mushroom_enoki_stem", "enoki_mushroom_stem")
            .put("plant_mushroom_enoki_cap", "enoki_mushroom_cap")
            .put("fluid_ichor", "ichor")
            .put("slab_vanilla", "vanilla_slab")
            .put("slab_basalt", "basalt_slab")
            .put("slab_brick_nether", "nether_brick_slab")
            .put("slab_vanilla_double", "vanilla_slab_double")
            .put("slab_basalt_double", "basalt_slab_double")
            .put("slab_brick_nether_double", "nether_brick_slab_double")
            .put("stairs_brick_nether_red", "red_nether_brick_stairs")
            .put("stairs_basalt_normal", "basalt_stairs")
            .put("stairs_basalt_smooth", "basalt_smooth_stairs")
            .put("stairs_basalt_brick", "basalt_brick_stairs")
            .put("stairs_basalt_pillar", "basalt_pillar_stairs")
            .put("stairs_brick_nether_fiery", "fiery_nether_brick_stairs")
            .put("stairs_brick_nether_icy", "icy_nether_brick_stairs")
            .put("stairs_brick_nether_lively", "lively_nether_brick_stairs")
            .put("stairs_brick_nether_gloomy", "gloomy_nether_brick_stairs")
            .put("wall_vanilla", "vanilla_wall")
            .put("wall_basalt", "basalt_wall")
            .put("wall_brick_nether", "nether_brick_wall")
            .put("fence_vanilla", "vanilla_fence")
            .put("fence_basalt", "basalt_fence")
            .put("fence_brick_nether", "nether_brick_fence")
            .put("fence_gate_quartz", "quartz_fence_gate")
            .put("fence_gate_brick_nether", "nether_brick_fence_gate")
            .put("fence_gate_brick_nether_red", "red_nether_brick_fence_gate")
            .put("fence_gate_basalt_normal", "basalt_fence_gate")
            .put("fence_gate_basalt_smooth", "basalt_smooth_fence_gate")
            .put("fence_gate_basalt_brick", "basalt_brick_fence_gate")
            .put("fence_gate_basalt_pillar", "basalt_pillar_fence_gate")
            .put("fence_gate_brick_nether_fiery", "fiery_nether_brick_fence_gate")
            .put("fence_gate_brick_nether_icy", "icy_nether_brick_fence_gate")
            .put("fence_gate_brick_nether_lively", "lively_nether_brick_fence_gate")
            .put("fence_gate_brick_nether_gloomy", "gloomy_nether_brick_fence_gate")
            .build();

    private final List<Predicate<RegistryEvent.MissingMappings.Mapping<T>>> remappingFunctions = ImmutableList.of(this::remapRegistryName);

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|RemapHandler");

    public void remapAll(List<RegistryEvent.MissingMappings.Mapping<T>> missingMappings)
    {
        LOGGER.info("Fix Missing Mappings started.");

        for(RegistryEvent.MissingMappings.Mapping<T> missingMapping : missingMappings)
        {
            LOGGER.info("Trying to remap {}", missingMapping.key);

            boolean remapped = remappingFunctions.stream().anyMatch(mappingPredicate -> mappingPredicate.test(missingMapping));

            if(!remapped)
            {
                LOGGER.info("Couldn't remap {}", missingMapping.key);
            }
        }

        LOGGER.info("Fix Missing Mappings completed.");
    }

    private boolean attemptRemap(RegistryEvent.MissingMappings.Mapping<T> missingMapping, ResourceLocation registryName)
    {
        IForgeRegistry<T> registry = missingMapping.registry;

        T value = registry.getValue(registryName);

        if(registry.containsKey(registryName) && value != null)
        {
            LOGGER.info("Remapped {} {} to {}", registry.getRegistrySuperType().getSimpleName(), missingMapping.key, registryName);
            missingMapping.remap(value);
            return true;
        }

        return false;
    }

    private boolean remapRegistryName(RegistryEvent.MissingMappings.Mapping<T> missingMapping)
    {
        final String missingPath = missingMapping.key.getResourcePath();

        if(!customNames.containsKey(missingPath))
        {
            return false;
        }

        String newPath = customNames.get(missingPath);
        ResourceLocation newRegistryName = new ResourceLocation(missingMapping.key.getResourceDomain(), newPath);
        return attemptRemap(missingMapping, newRegistryName);
    }

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    private static class EventHandler
    {
        private static final RemapHandler<Block> blockRemapper = new RemapHandler<>();
        private static final RemapHandler<Item> itemRemapper = new RemapHandler<>();

        @SubscribeEvent
        public static void missingBlockMappings(RegistryEvent.MissingMappings<Block> event)
        {
            blockRemapper.remapAll(event.getMappings());
        }

        @SubscribeEvent
        public static void missingItemMappings(RegistryEvent.MissingMappings<Item> event)
        {
            itemRemapper.remapAll(event.getMappings());
        }
    }
}

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

import java.util.Map;

/**
 * Remaps this mod's {@link Block}s and {@link Item}s after registry names have been changed.
 * <p>
 * Written by Choonster here:
 * https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/2cb7b67adf7ab41e066c3308ac898224b2891752/src/main/java/choonster/testmod3/remap/Remapper.java
 *
 * @author Choonster
 */
@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class RemapHandler
{
    private static final Map<String, String> MAPPINGS_TO_REPLACE = ImmutableMap.<String, String>builder()
            //Blocks
            .put("block_basalt", "basalt")
            .put("block_netherrack", "netherrack")
            .put("block_brick_nether", "nether_brick")
            .put("block_netherrack_path", "netherrack_path")
            .put("block_hyphae", "hyphae")
            .put("block_sand_soul_tilled", "tilled_soul_sand")
            .put("block_glass_soul", "soul_glass")
            .put("block_glass_pane_soul", "soul_glass_pane")
            .put("block_amethyst", "gem_block")
            .put("block_rime", "gem_block")
            .put("block_ice_frostburn", "frostburn_ice")
            .put("block_bone_sliver", "bone_sliver")
            .put("block_bone_chunk", "bone_chunk")
            .put("block_iron_worn", "worn_iron")
            .put("block_fire_blue", "blue_fire")
            .put("block_portal_nether", "nether_portal")
            .put("tile_urn_sorrow", "urn_of_sorrow")
            .put("ore_quartz", "quartz_ore")
            .put("ore_amethyst", "gem_ore")
            .put("ore_rime", "gem_ore")
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

            //Items
            .put("item_brick_nether", "netherbrick")
            .put("item_bone_wither", "wither_bone")
            .put("item_dust_wither", "wither_dust")
            .put("item_hide_salamander", "salamander_hide")
            .put("item_crystal_amethyst", "gem")
            .put("item_crystal_rime", "gem")
            .put("item_crystal_rime_steel", "rime_and_steel")
            .put("item_spore", "spore")
            .put("item_fang_spider_bone", "bone_spider_fang")
            .put("item_tear_ghast_queen", "ghast_queen_tear")
            .put("item_boat_obsidian", "obsidian_boat")
            .put("food_meat_ghast_raw", "ghast_meat_raw")
            .put("food_meat_ghast_cooked", "ghast_meat_cooked")
            .put("food_congealed_magma_cream", "congealed_magma_cream")
            .put("food_mushroom_enoki", "enoki_mushroom")
            .put("tool_sword_bone", "golden_bone_sword")
            .put("tool_pickaxe_bone", "golden_bone_pickaxe")
            .put("tool_shovel_bone", "golden_bone_shovel")
            .put("tool_axe_bone", "golden_bone_axe")
            .put("tool_hoe_bone", "golden_bone_hoe")
            .put("tool_hammer_bone", "golden_bone_hammer")
            .put("armor_helmet_bone", "wither_bone_helmet")
            .put("armor_chestplate_bone", "wither_bone_chestplate")
            .put("armor_leggings_bone", "wither_bone_leggings")
            .put("armor_boots_bone", "wither_bone_boots")
            .put("armor_helmet_hide_salamander", "salamander_hide_helmet")
            .put("armor_chestplate_hide_salamander", "salamander_hide_chestplate")
            .put("armor_leggings_hide_salamander", "salamander_hide_leggings")
            .put("armor_boots_hide_salamander", "salamander_hide_boots")
            .build();

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|RemapHandler");

    @SubscribeEvent
    public static void missingBlockMappings(RegistryEvent.MissingMappings<Block> event)
    {
        LOGGER.info("Block remapping started.");
        event.getAllMappings().forEach(RemapHandler::remapEntries);
        LOGGER.info("Block remapping completed.");
    }

    @SubscribeEvent
    public static void missingItemMappings(RegistryEvent.MissingMappings<Item> event)
    {
        LOGGER.info("Item remapping started.");
        event.getAllMappings().forEach(RemapHandler::remapEntries);
        LOGGER.info("Item remapping completed.");
    }

    private static void remapEntries(RegistryEvent.MissingMappings.Mapping missingMapping)
    {
        String oldMapping = missingMapping.key.getResourcePath();

        if(MAPPINGS_TO_REPLACE.containsKey(oldMapping))
        {
            String newMapping = MAPPINGS_TO_REPLACE.get(oldMapping);
            ResourceLocation replacementMapping = new ResourceLocation(missingMapping.key.getResourceDomain(), newMapping);

            IForgeRegistry registry = missingMapping.registry;
            IForgeRegistryEntry entry = registry.getValue(replacementMapping);

            if(registry.containsKey(replacementMapping) && entry != null)
            {
                LOGGER.info("Remapped {} {} to {}.", registry.getRegistrySuperType().getSimpleName(), missingMapping.key, replacementMapping);
                missingMapping.remap(entry);
            }
        }
    }
}

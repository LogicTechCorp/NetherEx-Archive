/*
 * NetherEx
 * Copyright (c) 2016-2018 by MineEx
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
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import nex.NetherEx;

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
            .put(NetherEx.MOD_ID + ":block_basalt", NetherEx.MOD_ID + ":basalt")
            .put(NetherEx.MOD_ID + ":block_netherrack", NetherEx.MOD_ID + ":netherrack")
            .put(NetherEx.MOD_ID + ":block_brick_nether", NetherEx.MOD_ID + ":nether_brick")
            .put(NetherEx.MOD_ID + ":block_netherrack_path", NetherEx.MOD_ID + ":netherrack_path")
            .put(NetherEx.MOD_ID + ":block_hyphae", NetherEx.MOD_ID + ":hyphae")
            .put(NetherEx.MOD_ID + ":block_sand_soul_tilled", NetherEx.MOD_ID + ":tilled_soul_sand")
            .put(NetherEx.MOD_ID + ":block_glass_soul", NetherEx.MOD_ID + ":soul_glass")
            .put(NetherEx.MOD_ID + ":block_glass_pane_soul", NetherEx.MOD_ID + ":soul_glass_pane")
            .put(NetherEx.MOD_ID + ":block_amethyst", NetherEx.MOD_ID + ":amethyst_block")
            .put(NetherEx.MOD_ID + ":block_rime", NetherEx.MOD_ID + ":rime_block")
            .put(NetherEx.MOD_ID + ":block_ice_frostburn", NetherEx.MOD_ID + ":frostburn_ice")
            .put(NetherEx.MOD_ID + ":block_bone_sliver", NetherEx.MOD_ID + ":bone_sliver")
            .put(NetherEx.MOD_ID + ":block_bone_chunk", NetherEx.MOD_ID + ":bone_chunk")
            .put(NetherEx.MOD_ID + ":block_iron_worn", NetherEx.MOD_ID + ":worn_iron")
            .put(NetherEx.MOD_ID + ":block_fire_blue", NetherEx.MOD_ID + ":blue_fire")
            .put(NetherEx.MOD_ID + ":block_portal_nether", NetherEx.MOD_ID + ":nether_portal")
            .put(NetherEx.MOD_ID + ":tile_urn_sorrow", NetherEx.MOD_ID + ":urn_of_sorrow")
            .put(NetherEx.MOD_ID + ":ore_quartz", NetherEx.MOD_ID + ":quartz_ore")
            .put(NetherEx.MOD_ID + ":ore_amethyst", NetherEx.MOD_ID + ":amethyst_ore")
            .put(NetherEx.MOD_ID + ":ore_rime", NetherEx.MOD_ID + ":rime_ore")
            .put(NetherEx.MOD_ID + ":plant_thornstalk", NetherEx.MOD_ID + ":thornstalk")
            .put(NetherEx.MOD_ID + ":plant_mushroom_elder", NetherEx.MOD_ID + ":elder_mushroom")
            .put(NetherEx.MOD_ID + ":plant_mushroom_elder_cap", NetherEx.MOD_ID + ":elder_mushroom_cap")
            .put(NetherEx.MOD_ID + ":plant_mushroom_elder_stem", NetherEx.MOD_ID + ":elder_mushroom_stem")
            .put(NetherEx.MOD_ID + ":plant_mushroom_enoki_stem", NetherEx.MOD_ID + ":enoki_mushroom_stem")
            .put(NetherEx.MOD_ID + ":plant_mushroom_enoki_cap", NetherEx.MOD_ID + ":enoki_mushroom_cap")
            .put(NetherEx.MOD_ID + ":fluid_ichor", NetherEx.MOD_ID + ":ichor")
            .put(NetherEx.MOD_ID + ":slab_vanilla", NetherEx.MOD_ID + ":vanilla_slab")
            .put(NetherEx.MOD_ID + ":slab_basalt", NetherEx.MOD_ID + ":basalt_slab")
            .put(NetherEx.MOD_ID + ":slab_brick_nether", NetherEx.MOD_ID + ":nether_brick_slab")
            .put(NetherEx.MOD_ID + ":slab_vanilla_double", NetherEx.MOD_ID + ":vanilla_slab_double")
            .put(NetherEx.MOD_ID + ":slab_basalt_double", NetherEx.MOD_ID + ":basalt_slab_double")
            .put(NetherEx.MOD_ID + ":slab_brick_nether_double", NetherEx.MOD_ID + ":nether_brick_slab_double")
            .put(NetherEx.MOD_ID + ":stairs_brick_nether_red", NetherEx.MOD_ID + ":red_nether_brick_stairs")
            .put(NetherEx.MOD_ID + ":stairs_basalt_normal", NetherEx.MOD_ID + ":basalt_stairs")
            .put(NetherEx.MOD_ID + ":stairs_basalt_smooth", NetherEx.MOD_ID + ":basalt_smooth_stairs")
            .put(NetherEx.MOD_ID + ":stairs_basalt_brick", NetherEx.MOD_ID + ":basalt_brick_stairs")
            .put(NetherEx.MOD_ID + ":stairs_basalt_pillar", NetherEx.MOD_ID + ":basalt_pillar_stairs")
            .put(NetherEx.MOD_ID + ":stairs_brick_nether_fiery", NetherEx.MOD_ID + ":fiery_nether_brick_stairs")
            .put(NetherEx.MOD_ID + ":stairs_brick_nether_icy", NetherEx.MOD_ID + ":icy_nether_brick_stairs")
            .put(NetherEx.MOD_ID + ":stairs_brick_nether_lively", NetherEx.MOD_ID + ":lively_nether_brick_stairs")
            .put(NetherEx.MOD_ID + ":stairs_brick_nether_gloomy", NetherEx.MOD_ID + ":gloomy_nether_brick_stairs")
            .put(NetherEx.MOD_ID + ":wall_vanilla", NetherEx.MOD_ID + ":vanilla_wall")
            .put(NetherEx.MOD_ID + ":wall_basalt", NetherEx.MOD_ID + ":basalt_wall")
            .put(NetherEx.MOD_ID + ":wall_brick_nether", NetherEx.MOD_ID + ":nether_brick_wall")
            .put(NetherEx.MOD_ID + ":fence_vanilla", NetherEx.MOD_ID + ":vanilla_fence")
            .put(NetherEx.MOD_ID + ":fence_basalt", NetherEx.MOD_ID + ":basalt_fence")
            .put(NetherEx.MOD_ID + ":fence_brick_nether", NetherEx.MOD_ID + ":nether_brick_fence")
            .put(NetherEx.MOD_ID + ":fence_gate_quartz", NetherEx.MOD_ID + ":quartz_fence_gate")
            .put(NetherEx.MOD_ID + ":fence_gate_brick_nether", NetherEx.MOD_ID + ":nether_brick_fence_gate")
            .put(NetherEx.MOD_ID + ":fence_gate_brick_nether_red", NetherEx.MOD_ID + ":red_nether_brick_fence_gate")
            .put(NetherEx.MOD_ID + ":fence_gate_basalt_normal", NetherEx.MOD_ID + ":basalt_fence_gate")
            .put(NetherEx.MOD_ID + ":fence_gate_basalt_smooth", NetherEx.MOD_ID + ":basalt_smooth_fence_gate")
            .put(NetherEx.MOD_ID + ":fence_gate_basalt_brick", NetherEx.MOD_ID + ":basalt_brick_fence_gate")
            .put(NetherEx.MOD_ID + ":fence_gate_basalt_pillar", NetherEx.MOD_ID + ":basalt_pillar_fence_gate")
            .put(NetherEx.MOD_ID + ":fence_gate_brick_nether_fiery", NetherEx.MOD_ID + ":fiery_nether_brick_fence_gate")
            .put(NetherEx.MOD_ID + ":fence_gate_brick_nether_icy", NetherEx.MOD_ID + ":icy_nether_brick_fence_gate")
            .put(NetherEx.MOD_ID + ":fence_gate_brick_nether_lively", NetherEx.MOD_ID + ":lively_nether_brick_fence_gate")
            .put(NetherEx.MOD_ID + ":fence_gate_brick_nether_gloomy", NetherEx.MOD_ID + ":gloomy_nether_brick_fence_gate")

            //Items
            .put(NetherEx.MOD_ID + ":item_brick_nether", NetherEx.MOD_ID + ":netherbrick")
            .put(NetherEx.MOD_ID + ":item_bone_wither", NetherEx.MOD_ID + ":wither_bone")
            .put(NetherEx.MOD_ID + ":item_dust_wither", NetherEx.MOD_ID + ":wither_dust")
            .put(NetherEx.MOD_ID + ":item_hide_salamander", NetherEx.MOD_ID + ":salamander_hide")
            .put(NetherEx.MOD_ID + ":item_crystal_amethyst", NetherEx.MOD_ID + ":amethyst_crystal")
            .put(NetherEx.MOD_ID + ":item_crystal_rime", NetherEx.MOD_ID + ":rime_crystal")
            .put(NetherEx.MOD_ID + ":item_crystal_rime_steel", NetherEx.MOD_ID + ":rime_and_steel")
            .put(NetherEx.MOD_ID + ":item_spore", NetherEx.MOD_ID + ":spore")
            .put(NetherEx.MOD_ID + ":item_fang_spider_bone", NetherEx.MOD_ID + ":bone_spider_fang")
            .put(NetherEx.MOD_ID + ":item_tear_ghast_queen", NetherEx.MOD_ID + ":ghast_queen_tear")
            .put(NetherEx.MOD_ID + ":item_boat_obsidian", NetherEx.MOD_ID + ":obsidian_boat")
            .put(NetherEx.MOD_ID + ":food_meat_ghast_raw", NetherEx.MOD_ID + ":ghast_meat_raw")
            .put(NetherEx.MOD_ID + ":food_meat_ghast_cooked", NetherEx.MOD_ID + ":ghast_meat_cooked")
            .put(NetherEx.MOD_ID + ":food_congealed_magma_cream", NetherEx.MOD_ID + ":congealed_magma_cream")
            .put(NetherEx.MOD_ID + ":food_mushroom_enoki", NetherEx.MOD_ID + ":enoki_mushroom")
            .put(NetherEx.MOD_ID + ":tool_sword_bone", NetherEx.MOD_ID + ":golden_wither_bone_sword")
            .put(NetherEx.MOD_ID + ":tool_pickaxe_bone", NetherEx.MOD_ID + ":golden_wither_bone_pickaxe")
            .put(NetherEx.MOD_ID + ":tool_shovel_bone", NetherEx.MOD_ID + ":golden_wither_bone_shovel")
            .put(NetherEx.MOD_ID + ":tool_axe_bone", NetherEx.MOD_ID + ":golden_wither_bone_axe")
            .put(NetherEx.MOD_ID + ":tool_hoe_bone", NetherEx.MOD_ID + ":golden_wither_bone_hoe")
            .put(NetherEx.MOD_ID + ":tool_hammer_bone", NetherEx.MOD_ID + ":golden_wither_bone_hammer")
            .put(NetherEx.MOD_ID + ":armor_helmet_bone", NetherEx.MOD_ID + ":wither_bone_helmet")
            .put(NetherEx.MOD_ID + ":armor_chestplate_bone", NetherEx.MOD_ID + ":wither_bone_chestplate")
            .put(NetherEx.MOD_ID + ":armor_leggings_bone", NetherEx.MOD_ID + ":wither_bone_leggings")
            .put(NetherEx.MOD_ID + ":armor_boots_bone", NetherEx.MOD_ID + ":wither_bone_boots")
            .put(NetherEx.MOD_ID + ":armor_helmet_hide_salamander", NetherEx.MOD_ID + ":salamander_hide_helmet")
            .put(NetherEx.MOD_ID + ":armor_chestplate_hide_salamander", NetherEx.MOD_ID + ":salamander_hide_chestplate")
            .put(NetherEx.MOD_ID + ":armor_leggings_hide_salamander", NetherEx.MOD_ID + ":salamander_hide_leggings")
            .put(NetherEx.MOD_ID + ":armor_boots_hide_salamander", NetherEx.MOD_ID + ":salamander_hide_boots")

            //Biomes
            .put(NetherEx.MOD_ID + ":hell", "minecraft:hell")

            //Entities
            .put(NetherEx.MOD_ID + ":projectile_ghast_queen_fireball", NetherEx.MOD_ID + ":ghast_queen_fireball")
            .put(NetherEx.MOD_ID + ":projectile_ghastling_fireball", NetherEx.MOD_ID + ":ghastling_fireball")
            // This is handled higher up in the items -> .put(NetherEx.MOD_ID + ":item_boat_obsidian", NetherEx.MOD_ID + ":obsidian_boat")
            .put(NetherEx.MOD_ID + ":passive_pigtificate_leader", NetherEx.MOD_ID + ":pigtificate_leader")
            .put(NetherEx.MOD_ID + ":passive_pigtificate", NetherEx.MOD_ID + ":pigtificate")
            .put(NetherEx.MOD_ID + ":neutral_gold_golem", NetherEx.MOD_ID + ":gold_golem")
            .put(NetherEx.MOD_ID + ":neutral_mogus", NetherEx.MOD_ID + ":mogus")
            .put(NetherEx.MOD_ID + ":neutral_salamander", NetherEx.MOD_ID + ":salamander")
            .put(NetherEx.MOD_ID + ":monster_wight", NetherEx.MOD_ID + ":wight")
            .put(NetherEx.MOD_ID + ":monster_ember", NetherEx.MOD_ID + ":ember")
            .put(NetherEx.MOD_ID + ":monster_nethermite", NetherEx.MOD_ID + ":nethermite")
            .put(NetherEx.MOD_ID + ":monster_spinout", NetherEx.MOD_ID + ":spinout")
            .put(NetherEx.MOD_ID + ":monster_spore_creeper", NetherEx.MOD_ID + ":spore_creeper")
            .put(NetherEx.MOD_ID + ":monster_spore", NetherEx.MOD_ID + ":spore")
            .put(NetherEx.MOD_ID + ":monster_ghastling", NetherEx.MOD_ID + ":ghastling")
            .put(NetherEx.MOD_ID + ":monster_bone_spider", NetherEx.MOD_ID + ":bone_spider")
            .put(NetherEx.MOD_ID + ":monster_brute", NetherEx.MOD_ID + ":brute")
            .put(NetherEx.MOD_ID + ":boss_ghast_queen", NetherEx.MOD_ID + ":ghast_queen")

            //SoundEvents
            .put(NetherEx.MOD_ID + ":ambient_pigtificate", NetherEx.MOD_ID + ":pigtificate_ambient")
            .put(NetherEx.MOD_ID + ":hurt_pigtificate", NetherEx.MOD_ID + ":pigtificate_hurt")
            .put(NetherEx.MOD_ID + ":death_pigtificate", NetherEx.MOD_ID + ":pigtificate_death")
            .put(NetherEx.MOD_ID + ":ambient_mogus", NetherEx.MOD_ID + ":mogus_ambient")
            .put(NetherEx.MOD_ID + ":hurt_mogus", NetherEx.MOD_ID + ":mogus_hurt")
            .put(NetherEx.MOD_ID + ":death_mogus", NetherEx.MOD_ID + ":mogus_death")
            .put(NetherEx.MOD_ID + ":ambient_salamander", NetherEx.MOD_ID + ":salamander_ambient")
            .put(NetherEx.MOD_ID + ":hurt_salamander", NetherEx.MOD_ID + ":salamander_hurt")
            .put(NetherEx.MOD_ID + ":death_salamander", NetherEx.MOD_ID + ":salamander_death")
            .put(NetherEx.MOD_ID + ":ambient_wight", NetherEx.MOD_ID + ":wight_ambient")
            .put(NetherEx.MOD_ID + ":hurt_wight", NetherEx.MOD_ID + ":wight_hurt")
            .put(NetherEx.MOD_ID + ":death_wight", NetherEx.MOD_ID + ":wight_death")
            .put(NetherEx.MOD_ID + ":hurt_ember", NetherEx.MOD_ID + ":ember_hurt")
            .put(NetherEx.MOD_ID + ":death_ember", NetherEx.MOD_ID + ":ember_death")
            .put(NetherEx.MOD_ID + ":ambient_nethermite", NetherEx.MOD_ID + ":nethermite_ambient")
            .put(NetherEx.MOD_ID + ":hurt_nethermite", NetherEx.MOD_ID + ":nethermite_hurt")
            .put(NetherEx.MOD_ID + ":death_nethermite", NetherEx.MOD_ID + ":nethermite_death")
            .put(NetherEx.MOD_ID + ":ambient_spinout", NetherEx.MOD_ID + ":spinout_ambient")
            .put(NetherEx.MOD_ID + ":hurt_spinout", NetherEx.MOD_ID + ":spinout_hurt")
            .put(NetherEx.MOD_ID + ":death_spinout", NetherEx.MOD_ID + ":spinout_death")
            .put(NetherEx.MOD_ID + ":hurt_spore", NetherEx.MOD_ID + ":spore_hurt")
            .put(NetherEx.MOD_ID + ":death_spore", NetherEx.MOD_ID + ":spore_death")
            .put(NetherEx.MOD_ID + ":warn_spore", NetherEx.MOD_ID + ":spore_warn")
            .put(NetherEx.MOD_ID + ":explode_spore", NetherEx.MOD_ID + ":spore_explode")
            .put(NetherEx.MOD_ID + ":ambient_ghastling", NetherEx.MOD_ID + ":ghastling_ambient")
            .put(NetherEx.MOD_ID + ":hurt_ghastling", NetherEx.MOD_ID + ":ghastling_hurt")
            .put(NetherEx.MOD_ID + ":death_ghastling", NetherEx.MOD_ID + ":ghastling_death")
            .put(NetherEx.MOD_ID + ":warn_ghastling", NetherEx.MOD_ID + ":ghastling_warn")
            .put(NetherEx.MOD_ID + ":shoot_ghastling", NetherEx.MOD_ID + ":ghastling_shoot")
            .put(NetherEx.MOD_ID + ":ambient_ghast_queen", NetherEx.MOD_ID + ":ghast_queen_ambient")
            .put(NetherEx.MOD_ID + ":hurt_ghast_queen", NetherEx.MOD_ID + ":ghast_queen_hurt")
            .put(NetherEx.MOD_ID + ":death_ghast_queen", NetherEx.MOD_ID + ":ghast_queen_death")
            .put(NetherEx.MOD_ID + ":shoot_ghast_queen", NetherEx.MOD_ID + ":ghast_queen_shoot")
            .put(NetherEx.MOD_ID + ":summon_ghast_queen", NetherEx.MOD_ID + ":ghast_queen_summon")
            .build();

    private static void remapEntries(RegistryEvent.MissingMappings.Mapping missingMapping, IForgeRegistry registry)
    {
        String oldMapping = missingMapping.key.toString();

        if(MAPPINGS_TO_REPLACE.containsKey(oldMapping))
        {
            String newMapping = MAPPINGS_TO_REPLACE.get(oldMapping);
            ResourceLocation replacementMapping = new ResourceLocation(newMapping);
            IForgeRegistryEntry entry = registry.getValue(replacementMapping);

            if(registry.containsKey(replacementMapping) && entry != null)
            {
                missingMapping.remap(entry);
                NetherEx.LOGGER.info("Remapped {} {} to {}.", registry.getRegistrySuperType().getSimpleName(), missingMapping.key, replacementMapping);
            }
        }
    }

    @SubscribeEvent
    public static void onMissingBlockMappings(RegistryEvent.MissingMappings<Block> event)
    {
        for(RegistryEvent.MissingMappings.Mapping<Block> mapping : event.getAllMappings())
        {
            remapEntries(mapping, ForgeRegistries.BLOCKS);
        }
    }

    @SubscribeEvent
    public static void onMissingItemMappings(RegistryEvent.MissingMappings<Item> event)
    {
        for(RegistryEvent.MissingMappings.Mapping<Item> mapping : event.getAllMappings())
        {
            remapEntries(mapping, ForgeRegistries.ITEMS);
        }
    }

    @SubscribeEvent
    public static void onMissingBiomeMappings(RegistryEvent.MissingMappings<Biome> event)
    {
        for(RegistryEvent.MissingMappings.Mapping<Biome> mapping : event.getAllMappings())
        {
            remapEntries(mapping, ForgeRegistries.BIOMES);
        }
    }


    @SubscribeEvent
    public static void onMissingEntityMappings(RegistryEvent.MissingMappings<EntityEntry> event)
    {
        for(RegistryEvent.MissingMappings.Mapping<EntityEntry> mapping : event.getAllMappings())
        {
            remapEntries(mapping, ForgeRegistries.ENTITIES);
        }
    }

    @SubscribeEvent
    public static void onMissingSoundEventMappings(RegistryEvent.MissingMappings<SoundEvent> event)
    {
        for(RegistryEvent.MissingMappings.Mapping<SoundEvent> mapping : event.getAllMappings())
        {
            remapEntries(mapping, ForgeRegistries.SOUND_EVENTS);
        }
    }
}

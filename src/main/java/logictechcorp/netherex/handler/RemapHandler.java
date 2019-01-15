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

package logictechcorp.netherex.handler;

import com.google.common.collect.ImmutableMap;
import logictechcorp.netherex.NetherEx;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
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
            .put("nex:block_basalt", "netherex:basalt")
            .put("nex:block_netherrack", "netherex:netherrack")
            .put("nex:block_brick_nether", "netherex:nether_brick")
            .put("nex:block_netherrack_path", "netherex:netherrack_path")
            .put("nex:block_hyphae", "netherex:hyphae")
            .put("nex:block_sand_soul_tilled", "netherex:tilled_soul_sand")
            .put("nex:block_glass_soul", "netherex:soul_glass")
            .put("nex:block_glass_pane_soul", "netherex:soul_glass_pane")
            .put("nex:block_amethyst", "netherex:amethyst_block")
            .put("nex:block_rime", "netherex:rime_block")
            .put("nex:block_ice_frostburn", "netherex:frostburn_ice")
            .put("nex:block_bone_sliver", "netherex:bone_sliver")
            .put("nex:block_bone_chunk", "netherex:bone_chunk")
            .put("nex:block_iron_worn", "netherex:worn_iron")
            .put("nex:block_fire_blue", "netherex:blue_fire")
            .put("nex:block_portal_nether", "netherex:nether_portal")
            .put("nex:tile_urn_sorrow", "netherex:urn_of_sorrow")
            .put("nex:ore_quartz", "netherex:quartz_ore")
            .put("nex:ore_amethyst", "netherex:amethyst_ore")
            .put("nex:ore_rime", "netherex:rime_ore")
            .put("nex:plant_thornstalk", "netherex:thornstalk")
            .put("nex:plant_mushroom_elder", "netherex:elder_mushroom")
            .put("nex:plant_mushroom_elder_cap", "netherex:elder_mushroom_cap")
            .put("nex:plant_mushroom_elder_stem", "netherex:elder_mushroom_stem")
            .put("nex:plant_mushroom_enoki_stem", "netherex:enoki_mushroom_stem")
            .put("nex:plant_mushroom_enoki_cap", "netherex:enoki_mushroom_cap")
            .put("nex:fluid_ichor", "netherex:ichor")
            .put("nex:slab_vanilla", "netherex:vanilla_slab")
            .put("nex:slab_basalt", "netherex:basalt_slab")
            .put("nex:slab_brick_nether", "netherex:nether_brick_slab")
            .put("nex:slab_vanilla_double", "netherex:vanilla_slab_double")
            .put("nex:slab_basalt_double", "netherex:basalt_slab_double")
            .put("nex:slab_brick_nether_double", "netherex:nether_brick_slab_double")
            .put("nex:stairs_brick_nether_red", "netherex:red_nether_brick_stairs")
            .put("nex:stairs_basalt_normal", "netherex:basalt_stairs")
            .put("nex:stairs_basalt_smooth", "netherex:smooth_basalt_stairs")
            .put("nex:basalt_smooth_stairs", "netherex:smooth_basalt_stairs")
            .put("nex:stairs_basalt_brick", "netherex:basalt_brick_stairs")
            .put("nex:stairs_basalt_pillar", "netherex:basalt_pillar_stairs")
            .put("nex:stairs_brick_nether_fiery", "netherex:fiery_nether_brick_stairs")
            .put("nex:stairs_brick_nether_icy", "netherex:icy_nether_brick_stairs")
            .put("nex:stairs_brick_nether_lively", "netherex:lively_nether_brick_stairs")
            .put("nex:stairs_brick_nether_gloomy", "netherex:gloomy_nether_brick_stairs")
            .put("nex:wall_vanilla", "netherex:vanilla_wall")
            .put("nex:wall_basalt", "netherex:basalt_wall")
            .put("nex:wall_brick_nether", "netherex:nether_brick_wall")
            .put("nex:fence_vanilla", "netherex:vanilla_fence")
            .put("nex:fence_basalt", "netherex:basalt_fence")
            .put("nex:fence_brick_nether", "netherex:nether_brick_fence")
            .put("nex:fence_gate_quartz", "netherex:quartz_fence_gate")
            .put("nex:fence_gate_brick_nether", "netherex:nether_brick_fence_gate")
            .put("nex:fence_gate_brick_nether_red", "netherex:red_nether_brick_fence_gate")
            .put("nex:fence_gate_basalt_normal", "netherex:basalt_fence_gate")
            .put("nex:fence_gate_basalt_smooth", "netherex:smooth_basalt_fence_gate")
            .put("nex:basalt_smooth_fence_gate", "netherex:smooth_basalt_fence_gate")
            .put("nex:fence_gate_basalt_brick", "netherex:basalt_brick_fence_gate")
            .put("nex:fence_gate_basalt_pillar", "netherex:basalt_pillar_fence_gate")
            .put("nex:fence_gate_brick_nether_fiery", "netherex:fiery_nether_brick_fence_gate")
            .put("nex:fence_gate_brick_nether_icy", "netherex:icy_nether_brick_fence_gate")
            .put("nex:fence_gate_brick_nether_lively", "netherex:lively_nether_brick_fence_gate")
            .put("nex:fence_gate_brick_nether_gloomy", "netherex:gloomy_nether_brick_fence_gate")

            //Items
            .put("nex:item_brick_nether", "netherex:netherbrick")
            .put("nex:item_bone_wither", "netherex:wither_bone")
            .put("nex:item_dust_wither", "netherex:wither_dust")
            .put("nex:item_hide_salamander", "netherex:salamander_hide")
            .put("nex:item_crystal_amethyst", "netherex:amethyst_crystal")
            .put("nex:item_crystal_rime", "netherex:rime_crystal")
            .put("nex:item_crystal_rime_steel", "netherex:rime_and_steel")
            .put("nex:item_spore", "netherex:spore")
            .put("nex:item_fang_spider_bone", "netherex:coolmar_spider_fang")
            .put("nex:item_tear_ghast_queen", "netherex:ghast_queen_tear")
            .put("nex:item_boat_obsidian", "netherex:obsidian_boat")
            .put("nex:food_meat_ghast_raw", "netherex:ghast_meat_raw")
            .put("nex:food_meat_ghast_cooked", "netherex:ghast_meat_cooked")
            .put("nex:food_congealed_magma_cream", "netherex:congealed_magma_cream")
            .put("nex:food_mushroom_enoki", "netherex:enoki_mushroom")
            .put("nex:armor_helmet_bone", "netherex:wither_bone_helmet")
            .put("nex:armor_chestplate_bone", "netherex:wither_bone_chestplate")
            .put("nex:armor_leggings_bone", "netherex:wither_bone_leggings")
            .put("nex:armor_boots_bone", "netherex:wither_bone_boots")
            .put("nex:armor_helmet_hide_salamander", "netherex:salamander_hide_helmet")
            .put("nex:armor_chestplate_hide_salamander", "netherex:salamander_hide_chestplate")
            .put("nex:armor_leggings_hide_salamander", "netherex:salamander_hide_leggings")
            .put("nex:armor_boots_hide_salamander", "netherex:salamander_hide_boots")

            //Biomes
            .put("nex:hell", "minecraft:hell")

            //Entities
            .put("nex:projectile_ghast_queen_fireball", "netherex:ghast_queen_fireball")
            .put("nex:projectile_ghastling_fireball", "netherex:ghastling_fireball")
            // This is handled higher up with the items -> .put("nex:item_boat_obsidian", "netherex:obsidian_boat")
            .put("nex:passive_pigtificate_leader", "netherex:pigtificate_leader")
            .put("nex:passive_pigtificate", "netherex:pigtificate")
            .put("nex:neutral_gold_golem", "netherex:gold_golem")
            .put("nex:neutral_mogus", "netherex:mogus")
            .put("nex:neutral_salamander", "netherex:salamander")
            .put("nex:monster_wight", "netherex:wight")
            .put("nex:monster_ember", "netherex:ember")
            .put("nex:monster_nethermite", "netherex:nethermite")
            .put("nex:monster_spinout", "netherex:spinout")
            .put("nex:monster_spore_creeper", "netherex:spore_creeper")
            .put("nex:monster_spore", "netherex:spore")
            .put("nex:monster_ghastling", "netherex:ghastling")
            .put("nex:monster_bone_spider", "netherex:coolmar_spider")
            .put("nex:bone_spider", "netherex:coolmar_spider")
            .put("nex:monster_brute", "netherex:brute")
            .put("nex:boss_ghast_queen", "netherex:ghast_queen")

            //SoundEvents
            .put("nex:ambient_pigtificate", "netherex:pigtificate_ambient")
            .put("nex:hurt_pigtificate", "netherex:pigtificate_hurt")
            .put("nex:death_pigtificate", "netherex:pigtificate_death")
            .put("nex:ambient_mogus", "netherex:mogus_ambient")
            .put("nex:hurt_mogus", "netherex:mogus_hurt")
            .put("nex:death_mogus", "netherex:mogus_death")
            .put("nex:ambient_salamander", "netherex:salamander_ambient")
            .put("nex:hurt_salamander", "netherex:salamander_hurt")
            .put("nex:death_salamander", "netherex:salamander_death")
            .put("nex:ambient_wight", "netherex:wight_ambient")
            .put("nex:hurt_wight", "netherex:wight_hurt")
            .put("nex:death_wight", "netherex:wight_death")
            .put("nex:hurt_ember", "netherex:ember_hurt")
            .put("nex:death_ember", "netherex:ember_death")
            .put("nex:ambient_nethermite", "netherex:nethermite_ambient")
            .put("nex:hurt_nethermite", "netherex:nethermite_hurt")
            .put("nex:death_nethermite", "netherex:nethermite_death")
            .put("nex:ambient_spinout", "netherex:spinout_ambient")
            .put("nex:hurt_spinout", "netherex:spinout_hurt")
            .put("nex:death_spinout", "netherex:spinout_death")
            .put("nex:hurt_spore", "netherex:spore_hurt")
            .put("nex:death_spore", "netherex:spore_death")
            .put("nex:warn_spore", "netherex:spore_warn")
            .put("nex:explode_spore", "netherex:spore_explode")
            .put("nex:ambient_ghastling", "netherex:ghastling_ambient")
            .put("nex:hurt_ghastling", "netherex:ghastling_hurt")
            .put("nex:death_ghastling", "netherex:ghastling_death")
            .put("nex:warn_ghastling", "netherex:ghastling_warn")
            .put("nex:shoot_ghastling", "netherex:ghastling_shoot")
            .put("nex:ambient_ghast_queen", "netherex:ghast_queen_ambient")
            .put("nex:hurt_ghast_queen", "netherex:ghast_queen_hurt")
            .put("nex:death_ghast_queen", "netherex:ghast_queen_death")
            .put("nex:shoot_ghast_queen", "netherex:ghast_queen_shoot")
            .put("nex:summon_ghast_queen", "netherex:ghast_queen_summon")
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
        else if(oldMapping.contains("nex"))
        {
            String newMapping = oldMapping.replace("nex", NetherEx.MOD_ID);
            ResourceLocation replacementMapping = new ResourceLocation(newMapping);
            IForgeRegistryEntry entry = registry.getValue(replacementMapping);

            if(registry.containsKey(replacementMapping) && entry != null)
            {
                missingMapping.remap(entry);
            }
            else
            {
                missingMapping.ignore();
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
    public static void onMissingPotionEventMappings(RegistryEvent.MissingMappings<Potion> event)
    {
        for(RegistryEvent.MissingMappings.Mapping<Potion> mapping : event.getAllMappings())
        {
            remapEntries(mapping, ForgeRegistries.POTIONS);
        }
    }

    @SubscribeEvent
    public static void onMissingPotionTypeEventMappings(RegistryEvent.MissingMappings<PotionType> event)
    {
        for(RegistryEvent.MissingMappings.Mapping<PotionType> mapping : event.getAllMappings())
        {
            remapEntries(mapping, ForgeRegistries.POTION_TYPES);
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

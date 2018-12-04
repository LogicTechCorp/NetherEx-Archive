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
            .put("nex:block_basalt", NetherEx.MOD_ID + ":basalt")
            .put("nex:block_netherrack", NetherEx.MOD_ID + ":netherrack")
            .put("nex:block_brick_nether", NetherEx.MOD_ID + ":nether_brick")
            .put("nex:block_netherrack_path", NetherEx.MOD_ID + ":netherrack_path")
            .put("nex:block_hyphae", NetherEx.MOD_ID + ":hyphae")
            .put("nex:block_sand_soul_tilled", NetherEx.MOD_ID + ":tilled_soul_sand")
            .put("nex:block_glass_soul", NetherEx.MOD_ID + ":soul_glass")
            .put("nex:block_glass_pane_soul", NetherEx.MOD_ID + ":soul_glass_pane")
            .put("nex:block_amethyst", NetherEx.MOD_ID + ":amethyst_block")
            .put("nex:block_rime", NetherEx.MOD_ID + ":rime_block")
            .put("nex:block_ice_frostburn", NetherEx.MOD_ID + ":frostburn_ice")
            .put("nex:block_bone_sliver", NetherEx.MOD_ID + ":bone_sliver")
            .put("nex:block_bone_chunk", NetherEx.MOD_ID + ":bone_chunk")
            .put("nex:block_iron_worn", NetherEx.MOD_ID + ":worn_iron")
            .put("nex:block_fire_blue", NetherEx.MOD_ID + ":blue_fire")
            .put("nex:block_portal_nether", NetherEx.MOD_ID + ":nether_portal")
            .put("nex:tile_urn_sorrow", NetherEx.MOD_ID + ":urn_of_sorrow")
            .put("nex:ore_quartz", NetherEx.MOD_ID + ":quartz_ore")
            .put("nex:ore_amethyst", NetherEx.MOD_ID + ":amethyst_ore")
            .put("nex:ore_rime", NetherEx.MOD_ID + ":rime_ore")
            .put("nex:plant_thornstalk", NetherEx.MOD_ID + ":thornstalk")
            .put("nex:plant_mushroom_elder", NetherEx.MOD_ID + ":elder_mushroom")
            .put("nex:plant_mushroom_elder_cap", NetherEx.MOD_ID + ":elder_mushroom_cap")
            .put("nex:plant_mushroom_elder_stem", NetherEx.MOD_ID + ":elder_mushroom_stem")
            .put("nex:plant_mushroom_enoki_stem", NetherEx.MOD_ID + ":enoki_mushroom_stem")
            .put("nex:plant_mushroom_enoki_cap", NetherEx.MOD_ID + ":enoki_mushroom_cap")
            .put("nex:fluid_ichor", NetherEx.MOD_ID + ":ichor")
            .put("nex:slab_vanilla", NetherEx.MOD_ID + ":vanilla_slab")
            .put("nex:slab_basalt", NetherEx.MOD_ID + ":basalt_slab")
            .put("nex:slab_brick_nether", NetherEx.MOD_ID + ":nether_brick_slab")
            .put("nex:slab_vanilla_double", NetherEx.MOD_ID + ":vanilla_slab_double")
            .put("nex:slab_basalt_double", NetherEx.MOD_ID + ":basalt_slab_double")
            .put("nex:slab_brick_nether_double", NetherEx.MOD_ID + ":nether_brick_slab_double")
            .put("nex:stairs_brick_nether_red", NetherEx.MOD_ID + ":red_nether_brick_stairs")
            .put("nex:stairs_basalt_normal", NetherEx.MOD_ID + ":basalt_stairs")
            .put("nex:stairs_basalt_smooth", NetherEx.MOD_ID + ":basalt_smooth_stairs")
            .put("nex:stairs_basalt_brick", NetherEx.MOD_ID + ":basalt_brick_stairs")
            .put("nex:stairs_basalt_pillar", NetherEx.MOD_ID + ":basalt_pillar_stairs")
            .put("nex:stairs_brick_nether_fiery", NetherEx.MOD_ID + ":fiery_nether_brick_stairs")
            .put("nex:stairs_brick_nether_icy", NetherEx.MOD_ID + ":icy_nether_brick_stairs")
            .put("nex:stairs_brick_nether_lively", NetherEx.MOD_ID + ":lively_nether_brick_stairs")
            .put("nex:stairs_brick_nether_gloomy", NetherEx.MOD_ID + ":gloomy_nether_brick_stairs")
            .put("nex:wall_vanilla", NetherEx.MOD_ID + ":vanilla_wall")
            .put("nex:wall_basalt", NetherEx.MOD_ID + ":basalt_wall")
            .put("nex:wall_brick_nether", NetherEx.MOD_ID + ":nether_brick_wall")
            .put("nex:fence_vanilla", NetherEx.MOD_ID + ":vanilla_fence")
            .put("nex:fence_basalt", NetherEx.MOD_ID + ":basalt_fence")
            .put("nex:fence_brick_nether", NetherEx.MOD_ID + ":nether_brick_fence")
            .put("nex:fence_gate_quartz", NetherEx.MOD_ID + ":quartz_fence_gate")
            .put("nex:fence_gate_brick_nether", NetherEx.MOD_ID + ":nether_brick_fence_gate")
            .put("nex:fence_gate_brick_nether_red", NetherEx.MOD_ID + ":red_nether_brick_fence_gate")
            .put("nex:fence_gate_basalt_normal", NetherEx.MOD_ID + ":basalt_fence_gate")
            .put("nex:fence_gate_basalt_smooth", NetherEx.MOD_ID + ":basalt_smooth_fence_gate")
            .put("nex:fence_gate_basalt_brick", NetherEx.MOD_ID + ":basalt_brick_fence_gate")
            .put("nex:fence_gate_basalt_pillar", NetherEx.MOD_ID + ":basalt_pillar_fence_gate")
            .put("nex:fence_gate_brick_nether_fiery", NetherEx.MOD_ID + ":fiery_nether_brick_fence_gate")
            .put("nex:fence_gate_brick_nether_icy", NetherEx.MOD_ID + ":icy_nether_brick_fence_gate")
            .put("nex:fence_gate_brick_nether_lively", NetherEx.MOD_ID + ":lively_nether_brick_fence_gate")
            .put("nex:fence_gate_brick_nether_gloomy", NetherEx.MOD_ID + ":gloomy_nether_brick_fence_gate")

            //Items
            .put("nex:item_brick_nether", NetherEx.MOD_ID + ":netherbrick")
            .put("nex:item_bone_wither", NetherEx.MOD_ID + ":wither_bone")
            .put("nex:item_dust_wither", NetherEx.MOD_ID + ":wither_dust")
            .put("nex:item_hide_salamander", NetherEx.MOD_ID + ":salamander_hide")
            .put("nex:item_crystal_amethyst", NetherEx.MOD_ID + ":amethyst_crystal")
            .put("nex:item_crystal_rime", NetherEx.MOD_ID + ":rime_crystal")
            .put("nex:item_crystal_rime_steel", NetherEx.MOD_ID + ":rime_and_steel")
            .put("nex:item_spore", NetherEx.MOD_ID + ":spore")
            .put("nex:item_fang_spider_bone", NetherEx.MOD_ID + ":bone_spider_fang")
            .put("nex:item_tear_ghast_queen", NetherEx.MOD_ID + ":ghast_queen_tear")
            .put("nex:item_boat_obsidian", NetherEx.MOD_ID + ":obsidian_boat")
            .put("nex:food_meat_ghast_raw", NetherEx.MOD_ID + ":ghast_meat_raw")
            .put("nex:food_meat_ghast_cooked", NetherEx.MOD_ID + ":ghast_meat_cooked")
            .put("nex:food_congealed_magma_cream", NetherEx.MOD_ID + ":congealed_magma_cream")
            .put("nex:food_mushroom_enoki", NetherEx.MOD_ID + ":enoki_mushroom")
            .put("nex:armor_helmet_bone", NetherEx.MOD_ID + ":wither_bone_helmet")
            .put("nex:armor_chestplate_bone", NetherEx.MOD_ID + ":wither_bone_chestplate")
            .put("nex:armor_leggings_bone", NetherEx.MOD_ID + ":wither_bone_leggings")
            .put("nex:armor_boots_bone", NetherEx.MOD_ID + ":wither_bone_boots")
            .put("nex:armor_helmet_hide_salamander", NetherEx.MOD_ID + ":salamander_hide_helmet")
            .put("nex:armor_chestplate_hide_salamander", NetherEx.MOD_ID + ":salamander_hide_chestplate")
            .put("nex:armor_leggings_hide_salamander", NetherEx.MOD_ID + ":salamander_hide_leggings")
            .put("nex:armor_boots_hide_salamander", NetherEx.MOD_ID + ":salamander_hide_boots")

            //Biomes
            .put("nex:hell", "minecraft:hell")

            //Entities
            .put("nex:projectile_ghast_queen_fireball", NetherEx.MOD_ID + ":ghast_queen_fireball")
            .put("nex:projectile_ghastling_fireball", NetherEx.MOD_ID + ":ghastling_fireball")
            // This is handled higher up with the items -> .put("nex:item_boat_obsidian", NetherEx.MOD_ID + ":obsidian_boat")
            .put("nex:passive_pigtificate_leader", NetherEx.MOD_ID + ":pigtificate_leader")
            .put("nex:passive_pigtificate", NetherEx.MOD_ID + ":pigtificate")
            .put("nex:neutral_gold_golem", NetherEx.MOD_ID + ":gold_golem")
            .put("nex:neutral_mogus", NetherEx.MOD_ID + ":mogus")
            .put("nex:neutral_salamander", NetherEx.MOD_ID + ":salamander")
            .put("nex:monster_wight", NetherEx.MOD_ID + ":wight")
            .put("nex:monster_ember", NetherEx.MOD_ID + ":ember")
            .put("nex:monster_nethermite", NetherEx.MOD_ID + ":nethermite")
            .put("nex:monster_spinout", NetherEx.MOD_ID + ":spinout")
            .put("nex:monster_spore_creeper", NetherEx.MOD_ID + ":spore_creeper")
            .put("nex:monster_spore", NetherEx.MOD_ID + ":spore")
            .put("nex:monster_ghastling", NetherEx.MOD_ID + ":ghastling")
            .put("nex:monster_bone_spider", NetherEx.MOD_ID + ":bone_spider")
            .put("nex:monster_brute", NetherEx.MOD_ID + ":brute")
            .put("nex:boss_ghast_queen", NetherEx.MOD_ID + ":ghast_queen")

            //SoundEvents
            .put("nex:ambient_pigtificate", NetherEx.MOD_ID + ":pigtificate_ambient")
            .put("nex:hurt_pigtificate", NetherEx.MOD_ID + ":pigtificate_hurt")
            .put("nex:death_pigtificate", NetherEx.MOD_ID + ":pigtificate_death")
            .put("nex:ambient_mogus", NetherEx.MOD_ID + ":mogus_ambient")
            .put("nex:hurt_mogus", NetherEx.MOD_ID + ":mogus_hurt")
            .put("nex:death_mogus", NetherEx.MOD_ID + ":mogus_death")
            .put("nex:ambient_salamander", NetherEx.MOD_ID + ":salamander_ambient")
            .put("nex:hurt_salamander", NetherEx.MOD_ID + ":salamander_hurt")
            .put("nex:death_salamander", NetherEx.MOD_ID + ":salamander_death")
            .put("nex:ambient_wight", NetherEx.MOD_ID + ":wight_ambient")
            .put("nex:hurt_wight", NetherEx.MOD_ID + ":wight_hurt")
            .put("nex:death_wight", NetherEx.MOD_ID + ":wight_death")
            .put("nex:hurt_ember", NetherEx.MOD_ID + ":ember_hurt")
            .put("nex:death_ember", NetherEx.MOD_ID + ":ember_death")
            .put("nex:ambient_nethermite", NetherEx.MOD_ID + ":nethermite_ambient")
            .put("nex:hurt_nethermite", NetherEx.MOD_ID + ":nethermite_hurt")
            .put("nex:death_nethermite", NetherEx.MOD_ID + ":nethermite_death")
            .put("nex:ambient_spinout", NetherEx.MOD_ID + ":spinout_ambient")
            .put("nex:hurt_spinout", NetherEx.MOD_ID + ":spinout_hurt")
            .put("nex:death_spinout", NetherEx.MOD_ID + ":spinout_death")
            .put("nex:hurt_spore", NetherEx.MOD_ID + ":spore_hurt")
            .put("nex:death_spore", NetherEx.MOD_ID + ":spore_death")
            .put("nex:warn_spore", NetherEx.MOD_ID + ":spore_warn")
            .put("nex:explode_spore", NetherEx.MOD_ID + ":spore_explode")
            .put("nex:ambient_ghastling", NetherEx.MOD_ID + ":ghastling_ambient")
            .put("nex:hurt_ghastling", NetherEx.MOD_ID + ":ghastling_hurt")
            .put("nex:death_ghastling", NetherEx.MOD_ID + ":ghastling_death")
            .put("nex:warn_ghastling", NetherEx.MOD_ID + ":ghastling_warn")
            .put("nex:shoot_ghastling", NetherEx.MOD_ID + ":ghastling_shoot")
            .put("nex:ambient_ghast_queen", NetherEx.MOD_ID + ":ghast_queen_ambient")
            .put("nex:hurt_ghast_queen", NetherEx.MOD_ID + ":ghast_queen_hurt")
            .put("nex:death_ghast_queen", NetherEx.MOD_ID + ":ghast_queen_death")
            .put("nex:shoot_ghast_queen", NetherEx.MOD_ID + ":ghast_queen_shoot")
            .put("nex:summon_ghast_queen", NetherEx.MOD_ID + ":ghast_queen_summon")
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

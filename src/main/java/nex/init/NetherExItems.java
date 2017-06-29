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

package nex.init;

import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.NetherEx;
import nex.item.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExItems
{
    @GameRegistry.ObjectHolder("item_brick_nether")
    public static final ItemNetherBrick ITEM_BRICK_NETHER = null;

    @GameRegistry.ObjectHolder("item_bone_wither")
    public static final ItemNetherEx ITEM_BONE_WITHER = null;

    @GameRegistry.ObjectHolder("item_dust_wither")
    public static final ItemWitherDust ITEM_DUST_WITHER = null;

    @GameRegistry.ObjectHolder("item_hide_salamander")
    public static final ItemSalamanderHide ITEM_HIDE_SALAMANDER = null;

    @GameRegistry.ObjectHolder("item_crystal_amethyst")
    public static final ItemNetherEx ITEM_CRYSTAL_AMETHYST = null;

    @GameRegistry.ObjectHolder("item_crystal_rime")
    public static final ItemNetherEx ITEM_CRYSTAL_RIME = null;

    @GameRegistry.ObjectHolder("item_crystal_rime_steel")
    public static final ItemNetherEx ITEM_CRYSTAL_RIME_STEEL = null;

    @GameRegistry.ObjectHolder("item_spore")
    public static final ItemNetherEx ITEM_SPORE = null;

    @GameRegistry.ObjectHolder("item_fang_spider_bone")
    public static final ItemNetherEx ITEM_FANG_SPIDER_BONE = null;

    @GameRegistry.ObjectHolder("item_tear_ghast_queen")
    public static final ItemNetherEx ITEM_TEAR_GHAST_QUEEN = null;

    @GameRegistry.ObjectHolder("item_boat_obsidian")
    public static final ItemObsidianBoat ITEM_BOAT_OBSIDIAN = null;

    @GameRegistry.ObjectHolder("food_meat_ghast_raw")
    public static final ItemNetherExFood FOOD_MEAT_GHAST_RAW = null;

    @GameRegistry.ObjectHolder("food_meat_ghast_cooked")
    public static final ItemNetherExFood FOOD_MEAT_GHAST_COOKED = null;

    @GameRegistry.ObjectHolder("food_congealed_magma_cream")
    public static final ItemNetherExFood FOOD_MAGMA_CREAM_CONGEALED = null;

    @GameRegistry.ObjectHolder("food_mushroom_enoki")
    public static final ItemNetherExFood FOOD_MUSHROOM_ENOKI = null;

    @GameRegistry.ObjectHolder("tool_sword_bone")
    public static final ItemBoneSword TOOL_SWORD_BONE = null;

    @GameRegistry.ObjectHolder("tool_pickaxe_bone")
    public static final ItemBonePickaxe TOOL_PICKAXE_BONE = null;

    @GameRegistry.ObjectHolder("tool_shovel_bone")
    public static final ItemBoneShovel TOOL_SHOVEL_BONE = null;

    @GameRegistry.ObjectHolder("tool_axe_bone")
    public static final ItemBoneAxe TOOL_AXE_BONE = null;

    @GameRegistry.ObjectHolder("tool_hoe_bone")
    public static final ItemBoneHoe TOOL_HOE_BONE = null;

    @GameRegistry.ObjectHolder("tool_hammer_bone")
    public static final ItemBoneHammer TOOL_HAMMER_BONE = null;

    @GameRegistry.ObjectHolder("armor_helmet_bone")
    public static final ItemBoneArmor ARMOR_HELMET_BONE = null;

    @GameRegistry.ObjectHolder("armor_chestplate_bone")
    public static final ItemBoneArmor ARMOR_CHESTPLATE_BONE = null;

    @GameRegistry.ObjectHolder("armor_leggings_bone")
    public static final ItemBoneArmor ARMOR_LEGGINGS_BONE = null;

    @GameRegistry.ObjectHolder("armor_boots_bone")
    public static final ItemBoneArmor ARMOR_BOOTS_BONE = null;

    @GameRegistry.ObjectHolder("armor_helmet_hide_salamander")
    public static final ItemSalamanderHideArmor ARMOR_HELMET_HIDE_SALAMANDER = null;

    @GameRegistry.ObjectHolder("armor_chestplate_hide_salamander")
    public static final ItemSalamanderHideArmor ARMOR_CHESTPLATE_HIDE_SALAMANDER = null;

    @GameRegistry.ObjectHolder("armor_leggings_hide_salamander")
    public static final ItemSalamanderHideArmor ARMOR_LEGGINGS_HIDE_SALAMANDER = null;

    @GameRegistry.ObjectHolder("armor_boots_hide_salamander")
    public static final ItemSalamanderHideArmor ARMOR_BOOTS_HIDE_SALAMANDER = null;

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExItems");

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterItems(RegistryEvent.Register<Item> event)
        {
            LOGGER.info("Item registration started.");

            event.getRegistry().registerAll(
                    new ItemNetherBrick(),
                    new ItemNetherEx("item_bone_wither"),
                    new ItemWitherDust(),
                    new ItemSalamanderHide(),
                    new ItemNetherEx("item_crystal_amethyst"),
                    new ItemNetherEx("item_crystal_rime"),
                    new ItemRimeAndSteel(),
                    new ItemNetherEx("item_spore"),
                    new ItemNetherEx("item_fang_spider_bone"),
                    new ItemGhastQueenTear(),
                    new ItemObsidianBoat(),
                    new ItemNetherExFood("food_meat_ghast_raw", 4, 0.5F, false).setPotionEffect(new PotionEffect(MobEffects.LEVITATION, 100, 1), 1.0F).setAlwaysEdible(),
                    new ItemNetherExFood("food_meat_ghast_cooked", 8, 1.0F, false).setPotionEffect(new PotionEffect(MobEffects.LEVITATION, 200, 1), 1.0F).setAlwaysEdible(),
                    new ItemNetherExFood("food_congealed_magma_cream", 1, 0.3F, false).setPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 200, 1), 1.0F).setAlwaysEdible(),
                    new ItemNetherExFood("food_mushroom_enoki", 3, 0.7F, false),
                    new ItemBoneSword(),
                    new ItemBonePickaxe(),
                    new ItemBoneShovel(),
                    new ItemBoneAxe(),
                    new ItemBoneHoe(),
                    new ItemBoneHammer(),
                    new ItemBoneArmor("helmet", 1, EntityEquipmentSlot.HEAD),
                    new ItemBoneArmor("chestplate", 1, EntityEquipmentSlot.CHEST),
                    new ItemBoneArmor("leggings", 2, EntityEquipmentSlot.LEGS),
                    new ItemBoneArmor("boots", 1, EntityEquipmentSlot.FEET),
                    new ItemSalamanderHideArmor("helmet", 1, EntityEquipmentSlot.HEAD),
                    new ItemSalamanderHideArmor("chestplate", 1, EntityEquipmentSlot.CHEST),
                    new ItemSalamanderHideArmor("leggings", 2, EntityEquipmentSlot.LEGS),
                    new ItemSalamanderHideArmor("boots", 1, EntityEquipmentSlot.FEET)
            );

            LOGGER.info("Item registration completed.");
        }
    }
}

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

package logictechcorp.netherex.init;

import logictechcorp.libraryex.item.*;
import logictechcorp.libraryex.item.builder.ItemArmorBuilder;
import logictechcorp.libraryex.item.builder.ItemBuilder;
import logictechcorp.libraryex.item.builder.ItemEdibleBuilder;
import logictechcorp.libraryex.item.builder.ItemToolBuilder;
import logictechcorp.libraryex.utility.InjectionHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.item.ItemDullMirror;
import logictechcorp.netherex.item.ItemObsidianBoat;
import logictechcorp.netherex.item.ItemRimeAndSteel;
import logictechcorp.netherex.item.ItemWitherDust;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExItems
{
    public static final ItemMod GLOOMY_NETHERBRICK = InjectionHelper.nullValue();
    public static final ItemMod LIVELY_NETHERBRICK = InjectionHelper.nullValue();
    public static final ItemMod FIERY_NETHERBRICK = InjectionHelper.nullValue();
    public static final ItemMod ICY_NETHERBRICK = InjectionHelper.nullValue();
    public static final ItemMod WITHER_BONE = InjectionHelper.nullValue();
    public static final ItemWitherDust WITHER_DUST = InjectionHelper.nullValue();
    public static final ItemMod FROST_ROD = InjectionHelper.nullValue();
    public static final ItemMod FROST_POWDER = InjectionHelper.nullValue();
    public static final ItemMod BLAZED_WITHER_BONE = InjectionHelper.nullValue();
    public static final ItemMod FROSTED_WITHER_BONE = InjectionHelper.nullValue();
    public static final ItemMod ORANGE_SALAMANDER_HIDE = InjectionHelper.nullValue();
    public static final ItemMod BLACK_SALAMANDER_HIDE = InjectionHelper.nullValue();
    public static final ItemMod AMETHYST_CRYSTAL = InjectionHelper.nullValue();
    public static final ItemMod RIME_CRYSTAL = InjectionHelper.nullValue();
    public static final ItemMod COBALT_INGOT = InjectionHelper.nullValue();
    public static final ItemMod ARDITE_INGOT = InjectionHelper.nullValue();
    public static final ItemRimeAndSteel RIME_AND_STEEL = InjectionHelper.nullValue();
    public static final ItemMod SPORE = InjectionHelper.nullValue();
    public static final ItemMod COOLMAR_SPIDER_FANG = InjectionHelper.nullValue();
    public static final ItemMod GHAST_QUEEN_TEAR = InjectionHelper.nullValue();
    public static final ItemObsidianBoat OBSIDIAN_BOAT = InjectionHelper.nullValue();
    public static final ItemModEdible GHAST_MEAT_RAW = InjectionHelper.nullValue();
    public static final ItemModEdible GHAST_MEAT_COOKED = InjectionHelper.nullValue();
    public static final ItemModEdible CONGEALED_MAGMA_CREAM = InjectionHelper.nullValue();
    public static final ItemModEdible ENOKI_MUSHROOM = InjectionHelper.nullValue();
    public static final ItemDullMirror DULL_MIRROR = InjectionHelper.nullValue();
    public static final ItemModSword WITHERED_AMEDIAN_SWORD = InjectionHelper.nullValue();
    public static final ItemModPickaxe WITHERED_AMEDIAN_PICKAXE = InjectionHelper.nullValue();
    public static final ItemModShovel WITHERED_AMEDIAN_SHOVEL = InjectionHelper.nullValue();
    public static final ItemModAxe WITHERED_AMEDIAN_AXE = InjectionHelper.nullValue();
    public static final ItemModHoe WITHERED_AMEDIAN_HOE = InjectionHelper.nullValue();
    public static final ItemModHammer WITHERED_AMEDIAN_HAMMER = InjectionHelper.nullValue();
    public static final ItemModSword BLAZED_AMEDIAN_SWORD = InjectionHelper.nullValue();
    public static final ItemModPickaxe BLAZED_AMEDIAN_PICKAXE = InjectionHelper.nullValue();
    public static final ItemModShovel BLAZED_AMEDIAN_SHOVEL = InjectionHelper.nullValue();
    public static final ItemModAxe BLAZED_AMEDIAN_AXE = InjectionHelper.nullValue();
    public static final ItemModHoe BLAZED_AMEDIAN_HOE = InjectionHelper.nullValue();
    public static final ItemModHammer BLAZED_AMEDIAN_HAMMER = InjectionHelper.nullValue();
    public static final ItemModSword FROSTED_AMEDIAN_SWORD = InjectionHelper.nullValue();
    public static final ItemModPickaxe FROSTED_AMEDIAN_PICKAXE = InjectionHelper.nullValue();
    public static final ItemModShovel FROSTED_AMEDIAN_SHOVEL = InjectionHelper.nullValue();
    public static final ItemModAxe FROSTED_AMEDIAN_AXE = InjectionHelper.nullValue();
    public static final ItemModHoe FROSTED_AMEDIAN_HOE = InjectionHelper.nullValue();
    public static final ItemModHammer FROSTED_AMEDIAN_HAMMER = InjectionHelper.nullValue();
    public static final ItemModArmor WITHER_BONE_HELMET = InjectionHelper.nullValue();
    public static final ItemModArmor WITHER_BONE_CHESTPLATE = InjectionHelper.nullValue();
    public static final ItemModArmor WITHER_BONE_LEGGINGS = InjectionHelper.nullValue();
    public static final ItemModArmor WITHER_BONE_BOOTS = InjectionHelper.nullValue();
    public static final ItemModArmor ORANGE_SALAMANDER_HIDE_HELMET = InjectionHelper.nullValue();
    public static final ItemModArmor ORANGE_SALAMANDER_HIDE_CHESTPLATE = InjectionHelper.nullValue();
    public static final ItemModArmor ORANGE_SALAMANDER_HIDE_LEGGINGS = InjectionHelper.nullValue();
    public static final ItemModArmor ORANGE_SALAMANDER_HIDE_BOOTS = InjectionHelper.nullValue();
    public static final ItemModArmor BLACK_SALAMANDER_HIDE_HELMET = InjectionHelper.nullValue();
    public static final ItemModArmor BLACK_SALAMANDER_HIDE_CHESTPLATE = InjectionHelper.nullValue();
    public static final ItemModArmor BLACK_SALAMANDER_HIDE_LEGGINGS = InjectionHelper.nullValue();
    public static final ItemModArmor BLACK_SALAMANDER_HIDE_BOOTS = InjectionHelper.nullValue();

    private static final ItemBuilder DEFAULT_ITEM_BUILDER = new ItemBuilder().creativeTab(NetherEx.instance.getCreativeTab());

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterItems(RegistryEvent.Register<Item> event)
        {
            event.getRegistry().registerAll(
                    new ItemMod(NetherEx.getResource("gloomy_netherbrick"), DEFAULT_ITEM_BUILDER),
                    new ItemMod(NetherEx.getResource("lively_netherbrick"), DEFAULT_ITEM_BUILDER),
                    new ItemMod(NetherEx.getResource("fiery_netherbrick"), DEFAULT_ITEM_BUILDER),
                    new ItemMod(NetherEx.getResource("icy_netherbrick"), DEFAULT_ITEM_BUILDER),
                    new ItemMod(NetherEx.getResource("wither_bone"), DEFAULT_ITEM_BUILDER),
                    new ItemWitherDust(),
                    new ItemMod(NetherEx.getResource("frost_rod"), DEFAULT_ITEM_BUILDER),
                    new ItemMod(NetherEx.getResource("frost_powder"), DEFAULT_ITEM_BUILDER),
                    new ItemMod(NetherEx.getResource("blazed_wither_bone"), DEFAULT_ITEM_BUILDER),
                    new ItemMod(NetherEx.getResource("frosted_wither_bone"), DEFAULT_ITEM_BUILDER),
                    new ItemMod(NetherEx.getResource("orange_salamander_hide"), DEFAULT_ITEM_BUILDER),
                    new ItemMod(NetherEx.getResource("black_salamander_hide"), DEFAULT_ITEM_BUILDER),
                    new ItemMod(NetherEx.getResource("amethyst_crystal"), DEFAULT_ITEM_BUILDER),
                    new ItemMod(NetherEx.getResource("rime_crystal"), DEFAULT_ITEM_BUILDER),
                    new ItemMod(NetherEx.getResource("cobalt_ingot"), DEFAULT_ITEM_BUILDER),
                    new ItemMod(NetherEx.getResource("ardite_ingot"), DEFAULT_ITEM_BUILDER),
                    new ItemRimeAndSteel(),
                    new ItemMod(NetherEx.getResource("spore"), DEFAULT_ITEM_BUILDER),
                    new ItemMod(NetherEx.getResource("coolmar_spider_fang"), DEFAULT_ITEM_BUILDER),
                    new ItemMod(NetherEx.getResource("ghast_queen_tear"), DEFAULT_ITEM_BUILDER),
                    new ItemObsidianBoat(),
                    new ItemModEdible(NetherEx.getResource("ghast_meat_raw"), new ItemEdibleBuilder(4, 0.5F, false)).setPotionEffect(new PotionEffect(MobEffects.LEVITATION, 100, 1), 1.0F).setAlwaysEdible(),
                    new ItemModEdible(NetherEx.getResource("ghast_meat_cooked"), new ItemEdibleBuilder(8, 1.0F, false)).setPotionEffect(new PotionEffect(MobEffects.LEVITATION, 200, 1), 1.0F).setAlwaysEdible(),
                    new ItemModEdible(NetherEx.getResource("congealed_magma_cream"), new ItemEdibleBuilder(1, 0.3F, false)).setPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 200, 1), 1.0F).setAlwaysEdible(),
                    new ItemModEdible(NetherEx.getResource("enoki_mushroom"), new ItemEdibleBuilder(3, 0.7F, false)),
                    new ItemDullMirror(),
                    new ItemModSword(NetherEx.getResource("withered_amedian_sword"), new ItemToolBuilder(NetherExMaterials.AMEDIAN)),
                    new ItemModPickaxe(NetherEx.getResource("withered_amedian_pickaxe"), new ItemToolBuilder(NetherExMaterials.AMEDIAN)),
                    new ItemModShovel(NetherEx.getResource("withered_amedian_shovel"), new ItemToolBuilder(NetherExMaterials.AMEDIAN)),
                    new ItemModAxe(NetherEx.getResource("withered_amedian_axe"), new ItemToolBuilder(NetherExMaterials.AMEDIAN).attackDamage(10.0F).attackSpeed(-2.0F)),
                    new ItemModHoe(NetherEx.getResource("withered_amedian_hoe"), new ItemToolBuilder(NetherExMaterials.AMEDIAN)),
                    new ItemModHammer(NetherEx.getResource("withered_amedian_hammer"), new ItemToolBuilder(NetherExMaterials.AMEDIAN)),
                    new ItemModSword(NetherEx.getResource("blazed_amedian_sword"), new ItemToolBuilder(NetherExMaterials.AMEDIAN)),
                    new ItemModPickaxe(NetherEx.getResource("blazed_amedian_pickaxe"), new ItemToolBuilder(NetherExMaterials.AMEDIAN)),
                    new ItemModShovel(NetherEx.getResource("blazed_amedian_shovel"), new ItemToolBuilder(NetherExMaterials.AMEDIAN)),
                    new ItemModAxe(NetherEx.getResource("blazed_amedian_axe"), new ItemToolBuilder(NetherExMaterials.AMEDIAN).attackDamage(10.0F).attackSpeed(-2.0F)),
                    new ItemModHoe(NetherEx.getResource("blazed_amedian_hoe"), new ItemToolBuilder(NetherExMaterials.AMEDIAN)),
                    new ItemModHammer(NetherEx.getResource("blazed_amedian_hammer"), new ItemToolBuilder(NetherExMaterials.AMEDIAN)),
                    new ItemModSword(NetherEx.getResource("frosted_amedian_sword"), new ItemToolBuilder(NetherExMaterials.AMEDIAN)),
                    new ItemModPickaxe(NetherEx.getResource("frosted_amedian_pickaxe"), new ItemToolBuilder(NetherExMaterials.AMEDIAN)),
                    new ItemModShovel(NetherEx.getResource("frosted_amedian_shovel"), new ItemToolBuilder(NetherExMaterials.AMEDIAN)),
                    new ItemModAxe(NetherEx.getResource("frosted_amedian_axe"), new ItemToolBuilder(NetherExMaterials.AMEDIAN).attackDamage(10.0F).attackSpeed(-2.0F)),
                    new ItemModHoe(NetherEx.getResource("frosted_amedian_hoe"), new ItemToolBuilder(NetherExMaterials.AMEDIAN)),
                    new ItemModHammer(NetherEx.getResource("frosted_amedian_hammer"), new ItemToolBuilder(NetherExMaterials.AMEDIAN)),
                    new ItemModArmor(NetherEx.getResource("wither_bone_helmet"), new ItemArmorBuilder(NetherExMaterials.WITHER_BONE, EntityEquipmentSlot.HEAD)),
                    new ItemModArmor(NetherEx.getResource("wither_bone_chestplate"), new ItemArmorBuilder(NetherExMaterials.WITHER_BONE, EntityEquipmentSlot.CHEST)),
                    new ItemModArmor(NetherEx.getResource("wither_bone_leggings"), new ItemArmorBuilder(NetherExMaterials.WITHER_BONE, EntityEquipmentSlot.LEGS)),
                    new ItemModArmor(NetherEx.getResource("wither_bone_boots"), new ItemArmorBuilder(NetherExMaterials.WITHER_BONE, EntityEquipmentSlot.FEET)),
                    new ItemModArmor(NetherEx.getResource("orange_salamander_hide_helmet"), new ItemArmorBuilder(NetherExMaterials.SALAMANDER_HIDE, EntityEquipmentSlot.HEAD)),
                    new ItemModArmor(NetherEx.getResource("orange_salamander_hide_chestplate"), new ItemArmorBuilder(NetherExMaterials.SALAMANDER_HIDE, EntityEquipmentSlot.CHEST)),
                    new ItemModArmor(NetherEx.getResource("orange_salamander_hide_leggings"), new ItemArmorBuilder(NetherExMaterials.SALAMANDER_HIDE, EntityEquipmentSlot.LEGS)),
                    new ItemModArmor(NetherEx.getResource("orange_salamander_hide_boots"), new ItemArmorBuilder(NetherExMaterials.SALAMANDER_HIDE, EntityEquipmentSlot.FEET)),
                    new ItemModArmor(NetherEx.getResource("black_salamander_hide_helmet"), new ItemArmorBuilder(NetherExMaterials.SALAMANDER_HIDE, EntityEquipmentSlot.HEAD)),
                    new ItemModArmor(NetherEx.getResource("black_salamander_hide_chestplate"), new ItemArmorBuilder(NetherExMaterials.SALAMANDER_HIDE, EntityEquipmentSlot.CHEST)),
                    new ItemModArmor(NetherEx.getResource("black_salamander_hide_leggings"), new ItemArmorBuilder(NetherExMaterials.SALAMANDER_HIDE, EntityEquipmentSlot.LEGS)),
                    new ItemModArmor(NetherEx.getResource("black_salamander_hide_boots"), new ItemArmorBuilder(NetherExMaterials.SALAMANDER_HIDE, EntityEquipmentSlot.FEET))
            );
        }
    }

    public static ItemBuilder getDefaultItemBuilder()
    {
        return DEFAULT_ITEM_BUILDER;
    }
}

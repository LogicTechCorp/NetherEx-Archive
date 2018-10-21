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

package nex.init;

import lex.item.ItemEdibleLibEx;
import lex.item.ItemLibEx;
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

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class NetherExItems
{
    public static final ItemNetherbrick NETHERBRICK = null;
    public static final ItemLibEx WITHER_BONE = null;
    public static final ItemWitherDust WITHER_DUST = null;
    public static final ItemLibEx FROST_ROD = null;
    public static final ItemLibEx FROST_POWDER = null;
    public static final ItemLibEx BLAZED_WITHER_BONE = null;
    public static final ItemLibEx FROSTED_WITHER_BONE = null;
    public static final ItemSalamanderHide SALAMANDER_HIDE = null;
    public static final ItemLibEx AMETHYST_CRYSTAL = null;
    public static final ItemLibEx RIME_CRYSTAL = null;
    public static final ItemLibEx COBALT_INGOT = null;
    public static final ItemLibEx ARDITE_INGOT = null;
    public static final ItemRimeAndSteel RIME_AND_STEEL = null;
    public static final ItemLibEx SPORE = null;
    public static final ItemLibEx BONE_SPIDER_FANG = null;
    public static final ItemLibEx GHAST_QUEEN_TEAR = null;
    public static final ItemObsidianBoat OBSIDIAN_BOAT = null;
    public static final ItemEdibleLibEx GHAST_MEAT_RAW = null;
    public static final ItemEdibleLibEx GHAST_MEAT_COOKED = null;
    public static final ItemEdibleLibEx CONGEALED_MAGMA_CREAM = null;
    public static final ItemEdibleLibEx ENOKI_MUSHROOM = null;
    public static final ItemAmedianSword AMEDIAN_SWORD = null;
    public static final ItemAmedianPickaxe AMEDIAN_PICKAXE = null;
    public static final ItemAmedianShovel AMEDIAN_SHOVEL = null;
    public static final ItemAmedianAxe AMEDIAN_AXE = null;
    public static final ItemAmedianHoe AMEDIAN_HOE = null;
    public static final ItemAmedianHammer AMEDIAN_HAMMER = null;
    public static final ItemWitherBoneArmor WITHER_BONE_HELMET = null;
    public static final ItemWitherBoneArmor WITHER_BONE_CHESTPLATE = null;
    public static final ItemWitherBoneArmor WITHER_BONE_LEGGINGS = null;
    public static final ItemWitherBoneArmor WITHER_BONE_BOOTS = null;
    public static final ItemSalamanderHideArmor SALAMANDER_HIDE_HELMET = null;
    public static final ItemSalamanderHideArmor SALAMANDER_HIDE_CHESTPLATE = null;
    public static final ItemSalamanderHideArmor SALAMANDER_HIDE_LEGGINGS = null;
    public static final ItemSalamanderHideArmor SALAMANDER_HIDE_BOOTS = null;

    @Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterItems(RegistryEvent.Register<Item> event)
        {
            event.getRegistry().registerAll(
                    new ItemNetherbrick(),
                    new ItemLibEx(NetherEx.instance, "wither_bone"),
                    new ItemWitherDust(),
                    new ItemLibEx(NetherEx.instance, "frost_rod"),
                    new ItemLibEx(NetherEx.instance, "frost_powder"),
                    new ItemLibEx(NetherEx.instance, "blazed_wither_bone"),
                    new ItemLibEx(NetherEx.instance, "frosted_wither_bone"),
                    new ItemSalamanderHide(),
                    new ItemLibEx(NetherEx.instance, "amethyst_crystal"),
                    new ItemLibEx(NetherEx.instance, "rime_crystal"),
                    new ItemLibEx(NetherEx.instance, "cobalt_ingot"),
                    new ItemLibEx(NetherEx.instance, "ardite_ingot"),
                    new ItemRimeAndSteel(),
                    new ItemLibEx(NetherEx.instance, "spore"),
                    new ItemLibEx(NetherEx.instance, "bone_spider_fang"),
                    new ItemLibEx(NetherEx.instance, "ghast_queen_tear"), new ItemObsidianBoat(),
                    new ItemEdibleLibEx(NetherEx.instance, "ghast_meat_raw", 4, 0.5F, false).setPotionEffect(new PotionEffect(MobEffects.LEVITATION, 100, 1), 1.0F).setAlwaysEdible(),
                    new ItemEdibleLibEx(NetherEx.instance, "ghast_meat_cooked", 8, 1.0F, false).setPotionEffect(new PotionEffect(MobEffects.LEVITATION, 200, 1), 1.0F).setAlwaysEdible(),
                    new ItemEdibleLibEx(NetherEx.instance, "congealed_magma_cream", 1, 0.3F, false).setPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 200, 1), 1.0F).setAlwaysEdible(),
                    new ItemEdibleLibEx(NetherEx.instance, "enoki_mushroom", 3, 0.7F, false),
                    new ItemAmedianSword(),
                    new ItemAmedianPickaxe(),
                    new ItemAmedianShovel(),
                    new ItemAmedianAxe(),
                    new ItemAmedianHoe(),
                    new ItemAmedianHammer(),
                    new ItemWitherBoneArmor("helmet", 1, EntityEquipmentSlot.HEAD),
                    new ItemWitherBoneArmor("chestplate", 1, EntityEquipmentSlot.CHEST),
                    new ItemWitherBoneArmor("leggings", 2, EntityEquipmentSlot.LEGS),
                    new ItemWitherBoneArmor("boots", 1, EntityEquipmentSlot.FEET),
                    new ItemSalamanderHideArmor("helmet", 1, EntityEquipmentSlot.HEAD),
                    new ItemSalamanderHideArmor("chestplate", 1, EntityEquipmentSlot.CHEST),
                    new ItemSalamanderHideArmor("leggings", 2, EntityEquipmentSlot.LEGS),
                    new ItemSalamanderHideArmor("boots", 1, EntityEquipmentSlot.FEET)
            );
        }
    }
}

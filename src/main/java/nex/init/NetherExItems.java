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
    public static final ItemNetherbrick NETHERBRICK = null;
    public static final ItemNetherEx WITHER_BONE = null;
    public static final ItemWitherDust WITHER_DUST = null;
    public static final ItemSalamanderHide SALAMANDER_HIDE = null;
    public static final ItemGem GEM = null;
    public static final ItemRimeAndSteel RIME_AND_STEEL = null;
    public static final ItemNetherEx SPORE = null;
    public static final ItemNetherEx BONE_SPIDER_FANG = null;
    public static final ItemGhastQueenTear GHAST_QUEEN_TEAR = null;
    public static final ItemObsidianBoat OBSIDIAN_BOAT = null;
    public static final ItemNetherExFood GHAST_MEAT_RAW = null;
    public static final ItemNetherExFood GHAST_MEAT_COOKED = null;
    public static final ItemNetherExFood CONGEALED_MAGMA_CREAM = null;
    public static final ItemNetherExFood ENOKI_MUSHROOM = null;
    public static final ItemGoldenWitherBoneSword GOLDEN_WITHER_BONE_SWORD = null;
    public static final ItemGoldenWitherBonePickaxe GOLDEN_WITHER_BONE_PICKAXE = null;
    public static final ItemGoldenWitherBoneShovel GOLDEN_WITHER_BONE_SHOVEL = null;
    public static final ItemGoldenWitherBoneAxe GOLDEN_WITHER_BONE_AXE = null;
    public static final ItemGoldenWitherBoneHoe GOLDEN_WITHER_BONE_HOE = null;
    public static final ItemGoldenWitherBoneHammer GOLDEN_WITHER_BONE_HAMMER = null;
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
            NetherEx.LOGGER.info("Item registration started.");

            event.getRegistry().registerAll(
                    new ItemNetherbrick(),
                    new ItemNetherEx("wither_bone"),
                    new ItemWitherDust(),
                    new ItemSalamanderHide(),
                    new ItemGem(),
                    new ItemRimeAndSteel(),
                    new ItemNetherEx("spore"),
                    new ItemNetherEx("bone_spider_fang"),
                    new ItemGhastQueenTear(),
                    new ItemObsidianBoat(),
                    new ItemNetherExFood("ghast_meat_raw", 4, 0.5F, false).setPotionEffect(new PotionEffect(MobEffects.LEVITATION, 100, 1), 1.0F).setAlwaysEdible(),
                    new ItemNetherExFood("ghast_meat_cooked", 8, 1.0F, false).setPotionEffect(new PotionEffect(MobEffects.LEVITATION, 200, 1), 1.0F).setAlwaysEdible(),
                    new ItemNetherExFood("congealed_magma_cream", 1, 0.3F, false).setPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 200, 1), 1.0F).setAlwaysEdible(),
                    new ItemNetherExFood("enoki_mushroom", 3, 0.7F, false),
                    new ItemGoldenWitherBoneSword(),
                    new ItemGoldenWitherBonePickaxe(),
                    new ItemGoldenWitherBoneShovel(),
                    new ItemGoldenWitherBoneAxe(),
                    new ItemGoldenWitherBoneHoe(),
                    new ItemGoldenWitherBoneHammer(),
                    new ItemWitherBoneArmor("helmet", 1, EntityEquipmentSlot.HEAD),
                    new ItemWitherBoneArmor("chestplate", 1, EntityEquipmentSlot.CHEST),
                    new ItemWitherBoneArmor("leggings", 2, EntityEquipmentSlot.LEGS),
                    new ItemWitherBoneArmor("boots", 1, EntityEquipmentSlot.FEET),
                    new ItemSalamanderHideArmor("helmet", 1, EntityEquipmentSlot.HEAD),
                    new ItemSalamanderHideArmor("chestplate", 1, EntityEquipmentSlot.CHEST),
                    new ItemSalamanderHideArmor("leggings", 2, EntityEquipmentSlot.LEGS),
                    new ItemSalamanderHideArmor("boots", 1, EntityEquipmentSlot.FEET)
            );

            NetherEx.LOGGER.info("Item registration completed.");
        }
    }
}

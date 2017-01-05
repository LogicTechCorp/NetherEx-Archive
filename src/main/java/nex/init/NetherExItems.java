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
    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":item_nether_brick")
    public static final ItemNetherBrick ITEM_NETHER_BRICK = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":item_bone_withered")
    public static final ItemNetherEx ITEM_BONE_WITHERED = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":item_bone_meal_withered")
    public static final ItemNetherEx ITEM_BONE_MEAL_WITHERED = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":food_meat_ghast_raw")
    public static final ItemNetherExFood FOOD_MEAT_GHAST_RAW = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":food_meat_ghast_cooked")
    public static final ItemNetherExFood FOOD_MEAT_GHAST_COOKED = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":food_congealed_magma_cream")
    public static final ItemNetherExFood FOOD_MAGMA_CREAM_CONGEALED = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":item_hide_salamander")
    public static final ItemSalamanderHide ITEM_HIDE_SALAMANDER = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":food_mushroom_enoki")
    public static final ItemNetherExFood FOOD_MUSHROOM_ENOKI = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":tool_bone_sword")
    public static final ItemBoneSword TOOL_BONE_SWORD = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":tool_bone_pickaxe")
    public static final ItemBonePickaxe TOOL_BONE_PICKAXE = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":tool_bone_shovel")
    public static final ItemBoneShovel TOOL_BONE_SHOVEL = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":tool_bone_axe")
    public static final ItemBoneAxe TOOL_BONE_AXE = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":tool_bone_hoe")
    public static final ItemBoneHoe TOOL_BONE_HOE = null;

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExItems");

    @Mod.EventBusSubscriber
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterItems(RegistryEvent.Register<Item> event)
        {
            LOGGER.info("Item registration started.");

            event.getRegistry().registerAll(
                    new ItemNetherBrick(),
                    new ItemNetherEx("item_bone_withered"),
                    new ItemNetherEx("item_bone_meal_withered"),
                    new ItemNetherExFood("food_meat_ghast_raw", 4, 0.5F, false).setPotionEffect(new PotionEffect(MobEffects.LEVITATION, 100, 1), 1.0F).setAlwaysEdible(),
                    new ItemNetherExFood("food_meat_ghast_cooked", 8, 1.0F, false).setPotionEffect(new PotionEffect(MobEffects.LEVITATION, 200, 1), 1.0F).setAlwaysEdible(),
                    new ItemNetherExFood("food_congealed_magma_cream", 1, 0.3F, false).setPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 200, 1), 1.0F).setAlwaysEdible(),
                    new ItemSalamanderHide(),
                    new ItemNetherExFood("food_mushroom_enoki", 3, 0.7F, false),
                    new ItemBoneSword(),
                    new ItemBonePickaxe(),
                    new ItemBoneShovel(),
                    new ItemBoneAxe(),
                    new ItemBoneHoe()
            );

            LOGGER.info("Item registration completed.");
        }
    }
}

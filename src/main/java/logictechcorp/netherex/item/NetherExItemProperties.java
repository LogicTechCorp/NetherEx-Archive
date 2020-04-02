/*
 * NetherEx
 * Copyright (c) 2016-2020 by LogicTechCorp
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

package logictechcorp.netherex.item;

import logictechcorp.netherex.NetherEx;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class NetherExItemProperties
{
    // @formatter:off
    public static final Item.Properties GLOOMY_NETHER_BRICK      = createProperties(NetherEx.ITEM_GROUP);
    public static final Item.Properties LIVELY_NETHER_BRICK      = createProperties(NetherEx.ITEM_GROUP);
    public static final Item.Properties FIERY_NETHER_BRICK       = createProperties(NetherEx.ITEM_GROUP);
    public static final Item.Properties ICY_NETHER_BRICK         = createProperties(NetherEx.ITEM_GROUP);
    public static final Item.Properties MOGUS_SPAWN_EGG          = createProperties(NetherEx.ITEM_GROUP);
    public static final Item.Properties SALAMANDER_SPAWN_EGG     = createProperties(NetherEx.ITEM_GROUP);
    public static final Item.Properties ORANGE_SALAMANDER_HIDE   = createProperties(NetherEx.ITEM_GROUP);
    public static final Item.Properties BLACK_SALAMANDER_HIDE    = createProperties(NetherEx.ITEM_GROUP);
    public static final Item.Properties SPINOUT_SPAWN_EGG        = createProperties(NetherEx.ITEM_GROUP);
    public static final Item.Properties WIGHT_SPAWN_EGG          = createProperties(NetherEx.ITEM_GROUP);
    public static final Item.Properties RIME_CRYSTAL             = createProperties(NetherEx.ITEM_GROUP);
    public static final Item.Properties RIME_AND_STEEL           = createProperties(NetherEx.ITEM_GROUP).defaultMaxDamage(64);
    public static final Item.Properties SPORE_SPAWN_EGG          = createProperties(NetherEx.ITEM_GROUP);
    public static final Item.Properties SPORE_CREEPER_SPAWN_EGG  = createProperties(NetherEx.ITEM_GROUP);
    public static final Item.Properties SPORE                    = createProperties(NetherEx.ITEM_GROUP);
    public static final Item.Properties COOLMAR_SPIDER_SPAWN_EGG = createProperties(NetherEx.ITEM_GROUP);
    public static final Item.Properties COOLMAR_SPIDER_FANG      = createProperties(NetherEx.ITEM_GROUP);
    public static final Item.Properties GHAST_MEAT               = createProperties(NetherEx.ITEM_GROUP).food(new Food.Builder().effect(new EffectInstance(Effects.LEVITATION, 100), 1.0F).meat().hunger(4).saturation(0.5F).setAlwaysEdible().build());
    public static final Item.Properties COOKED_GHAST_MEAT        = createProperties(NetherEx.ITEM_GROUP).food(new Food.Builder().effect(new EffectInstance(Effects.LEVITATION, 200), 1.0F).meat().hunger(8).saturation(1.0F).setAlwaysEdible().build());
    public static final Item.Properties ENOKI_MUSHROOM           = createProperties(NetherEx.ITEM_GROUP).food(new Food.Builder().effect(new EffectInstance(Effects.LEVITATION, 200), 1.0F).hunger(3).saturation(0.7F).build());
    public static final Item.Properties DULL_MIRROR              = createProperties(NetherEx.ITEM_GROUP).maxDamage(6);
    // @formatter:on

    private static Item.Properties createProperties(ItemGroup group)
    {
        return new Item.Properties().group(group);
    }
}

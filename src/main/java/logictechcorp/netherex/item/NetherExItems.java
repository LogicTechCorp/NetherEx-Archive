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

package logictechcorp.netherex.item;

import logictechcorp.libraryex.utility.InjectionHelper;
import logictechcorp.netherex.NetherEx;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(NetherEx.MOD_ID)
@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetherExItems
{
    public static final Item GLOOMY_NETHERBRICK = InjectionHelper.nullValue();
    public static final Item LIVELY_NETHERBRICK = InjectionHelper.nullValue();
    public static final Item FIERY_NETHERBRICK = InjectionHelper.nullValue();
    public static final Item ICY_NETHERBRICK = InjectionHelper.nullValue();
    public static final Item ORANGE_SALAMANDER_HIDE = InjectionHelper.nullValue();
    public static final Item BLACK_SALAMANDER_HIDE = InjectionHelper.nullValue();
    public static final Item AMETHYST_CRYSTAL = InjectionHelper.nullValue();
    public static final Item RIME_CRYSTAL = InjectionHelper.nullValue();
    public static final Item RIME_AND_STEEL = InjectionHelper.nullValue();
    public static final Item SPORE = InjectionHelper.nullValue();
    public static final Item COOLMAR_SPIDER_FANG = InjectionHelper.nullValue();
    public static final Item GHAST_MEAT = InjectionHelper.nullValue();
    public static final Item COOKED_GHAST_MEAT = InjectionHelper.nullValue();
    public static final Item ENOKI_MUSHROOM = InjectionHelper.nullValue();
    public static final Item DULL_MIRROR = InjectionHelper.nullValue();

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event)
    {
        registerItem("gloomy_nether_brick", new Item(new Item.Properties().group(NetherEx.ITEM_GROUP)));
        registerItem("lively_nether_brick", new Item(new Item.Properties().group(NetherEx.ITEM_GROUP)));
        registerItem("fiery_nether_brick", new Item(new Item.Properties().group(NetherEx.ITEM_GROUP)));
        registerItem("icy_nether_brick", new Item(new Item.Properties().group(NetherEx.ITEM_GROUP)));
        registerItem("orange_salamander_hide", new Item(new Item.Properties().group(NetherEx.ITEM_GROUP)));
        registerItem("black_salamander_hide", new Item(new Item.Properties().group(NetherEx.ITEM_GROUP)));
        registerItem("amethyst_crystal", new Item(new Item.Properties().group(NetherEx.ITEM_GROUP)));
        registerItem("rime_crystal", new Item(new Item.Properties().group(NetherEx.ITEM_GROUP)));
        registerItem("rime_and_steel", new RimeAndSteelItem(new Item.Properties().group(NetherEx.ITEM_GROUP).defaultMaxDamage(64)));
        registerItem("spore", new Item(new Item.Properties().group(NetherEx.ITEM_GROUP)));
        registerItem("coolmar_spider_fang", new Item(new Item.Properties().group(NetherEx.ITEM_GROUP)));
        registerItem("ghast_meat", new Item(new Item.Properties().group(NetherEx.ITEM_GROUP).food(new Food.Builder().effect(new EffectInstance(Effects.LEVITATION, 100), 1.0F).meat().hunger(4).saturation(0.5F).setAlwaysEdible().build())));
        registerItem("cooked_ghast_meat", new Item(new Item.Properties().group(NetherEx.ITEM_GROUP).food(new Food.Builder().effect(new EffectInstance(Effects.LEVITATION, 200), 1.0F).meat().hunger(8).saturation(1.0F).setAlwaysEdible().build())));
        registerItem("enoki_mushroom", new Item(new Item.Properties().group(NetherEx.ITEM_GROUP).food(new Food.Builder().effect(new EffectInstance(Effects.LEVITATION, 200), 1.0F).hunger(3).saturation(0.7F).build())));
        registerItem("dull_mirror", new DullMirrorItem(new Item.Properties().group(NetherEx.ITEM_GROUP).maxDamage(6)));
    }

    private static void registerItem(String name, Item item)
    {
        ForgeRegistries.ITEMS.register(item.setRegistryName(name));
    }
}

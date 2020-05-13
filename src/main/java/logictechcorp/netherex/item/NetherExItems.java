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

import logictechcorp.libraryex.item.ModSpawnEggItem;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.NetherExEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.function.Supplier;

public class NetherExItems
{
    private static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, NetherEx.MOD_ID);

    // @formatter:off
    public static final RegistryObject<Item> GLOOMY_NETHER_BRICK      = registerItem("gloomy_nether_brick", NetherExItemProperties.GLOOMY_NETHER_BRICK);
    public static final RegistryObject<Item> LIVELY_NETHER_BRICK      = registerItem("lively_nether_brick", NetherExItemProperties.LIVELY_NETHER_BRICK);
    public static final RegistryObject<Item> FIERY_NETHER_BRICK       = registerItem("fiery_nether_brick", NetherExItemProperties.FIERY_NETHER_BRICK);
    public static final RegistryObject<Item> ICY_NETHER_BRICK         = registerItem("icy_nether_brick", NetherExItemProperties.ICY_NETHER_BRICK);
    public static final RegistryObject<Item> MOGUS_SPAWN_EGG          = registerSpawnEgg("mogus_spawn_egg", NetherExEntityTypes.MOGUS::get, 10489616, 10051392, NetherExItemProperties.MOGUS_SPAWN_EGG);
    public static final RegistryObject<Item> SALAMANDER_SPAWN_EGG     = registerSpawnEgg("salamander_spawn_egg", NetherExEntityTypes.SALAMANDER::get, 15690005, 0, NetherExItemProperties.SALAMANDER_SPAWN_EGG);
    public static final RegistryObject<Item> ORANGE_SALAMANDER_HIDE   = registerItem("orange_salamander_hide", NetherExItemProperties.ORANGE_SALAMANDER_HIDE);
    public static final RegistryObject<Item> BLACK_SALAMANDER_HIDE    = registerItem("black_salamander_hide", NetherExItemProperties.BLACK_SALAMANDER_HIDE);
    public static final RegistryObject<Item> SPINOUT_SPAWN_EGG        = registerSpawnEgg("spinout_spawn_egg", NetherExEntityTypes.SPINOUT::get, 5651507, 16382457, NetherExItemProperties.SPINOUT_SPAWN_EGG);
    public static final RegistryObject<Item> WIGHT_SPAWN_EGG          = registerSpawnEgg("wight_spawn_egg", NetherExEntityTypes.WIGHT::get, 15198183, 7375001, NetherExItemProperties.WIGHT_SPAWN_EGG);
    public static final RegistryObject<Item> RIME_CRYSTAL             = registerItem("rime_crystal", NetherExItemProperties.RIME_CRYSTAL);
    public static final RegistryObject<Item> RIME_AND_STEEL           = registerItem("rime_and_steel", () -> new RimeAndSteelItem(NetherExItemProperties.RIME_AND_STEEL));
    public static final RegistryObject<Item> SPORE_SPAWN_EGG          = registerSpawnEgg("spore_spawn_egg", NetherExEntityTypes.SPORE::get, 16579584, 11013646, NetherExItemProperties.SPORE_SPAWN_EGG);
    public static final RegistryObject<Item> SPORE_CREEPER_SPAWN_EGG  = registerSpawnEgg("spore_creeper_spawn_egg", NetherExEntityTypes.SPORE_CREEPER::get, 11013646, 16579584, NetherExItemProperties.SPORE_CREEPER_SPAWN_EGG);
    public static final RegistryObject<Item> SPORE                    = registerItem("spore", NetherExItemProperties.SPORE);
    public static final RegistryObject<Item> COOLMAR_SPIDER_SPAWN_EGG = registerSpawnEgg("coolmar_spider_spawn_egg", NetherExEntityTypes.COOLMAR_SPIDER::get, 14144467, 8032420, NetherExItemProperties.COOLMAR_SPIDER_SPAWN_EGG);
    public static final RegistryObject<Item> COOLMAR_SPIDER_FANG      = registerItem("coolmar_spider_fang", NetherExItemProperties.COOLMAR_SPIDER_FANG);
    public static final RegistryObject<Item> GHAST_MEAT               = registerItem("ghast_meat", NetherExItemProperties.GHAST_MEAT);
    public static final RegistryObject<Item> COOKED_GHAST_MEAT        = registerItem("cooked_ghast_meat", NetherExItemProperties.COOKED_GHAST_MEAT);
    public static final RegistryObject<Item> ENOKI_MUSHROOM           = registerItem("enoki_mushroom", NetherExItemProperties.ENOKI_MUSHROOM);
    public static final RegistryObject<Item> DULL_MIRROR              = registerItem("dull_mirror", NetherExItemProperties.DULL_MIRROR);
    // @formatter:on

    public static void register(IEventBus modEventBus)
    {
        ITEMS.register(modEventBus);
    }

    public static Collection<RegistryObject<Item>> getItems()
    {
        return ITEMS.getEntries();
    }

    private static <I extends Item> RegistryObject<I> registerItem(String name, Supplier<I> supplier)
    {
        return ITEMS.register(name, supplier);
    }

    private static RegistryObject<Item> registerItem(String name, Item.Properties properties)
    {
        return ITEMS.register(name, () -> new Item(properties));
    }

    private static RegistryObject<Item> registerSpawnEgg(String name, Supplier<EntityType<?>> supplier, int primaryColor, int secondaryColor, Item.Properties properties)
    {
        return registerItem(name, () -> new ModSpawnEggItem(supplier, primaryColor, secondaryColor, properties));
    }
}

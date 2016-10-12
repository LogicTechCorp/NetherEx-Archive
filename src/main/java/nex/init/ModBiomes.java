/*
 * Copyright (C) 2016.  LogicTechCorp
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.NetherEx;
import nex.Settings;
import nex.world.WorldProviderNether;
import nex.world.biome.*;

import java.util.Set;

@GameRegistry.ObjectHolder(NetherEx.MOD_ID)
public class ModBiomes
{
    private static Set<BiomeManager.BiomeEntry> biomeEntries = Sets.newHashSet();

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":hell")
    public static final BiomeHell HELL = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":ruthless_sands")
    public static final BiomeRuthlessSands RUTHLESS_SANDS = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":mushroom_grove")
    public static final BiomeMushroomGrove MUSHROOM_GROVE = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":hoar_frost")
    public static final BiomeHoarFrost HOAR_FROST = null;

    @GameRegistry.ObjectHolder(NetherEx.MOD_ID + ":hot_springs")
    public static final BiomeHotSprings HOT_SPRINGS = null;

    @Mod.EventBusSubscriber
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onRegisterBiomes(RegistryEvent.Register<Biome> event)
        {
            event.getRegistry().registerAll(
                    new BiomeHell(),
                    new BiomeRuthlessSands(),
                    new BiomeMushroomGrove(),
                    new BiomeHoarFrost(),
                    new BiomeHotSprings()
            );
        }
    }

    public static void init()
    {
        BiomeDictionary.registerBiomeType(HELL, BiomeDictionary.Type.NETHER);
        BiomeDictionary.registerBiomeType(RUTHLESS_SANDS, BiomeDictionary.Type.NETHER);
        BiomeDictionary.registerBiomeType(MUSHROOM_GROVE, BiomeDictionary.Type.NETHER);
        BiomeDictionary.registerBiomeType(HOAR_FROST, BiomeDictionary.Type.NETHER);
        BiomeDictionary.registerBiomeType(HOT_SPRINGS, BiomeDictionary.Type.NETHER);

        addBiome(new BiomeManager.BiomeEntry(HELL, Settings.biomeWeight(HELL.settingCategory)));
        addBiome(new BiomeManager.BiomeEntry(RUTHLESS_SANDS, Settings.biomeWeight(RUTHLESS_SANDS.settingCategory)));
        addBiome(new BiomeManager.BiomeEntry(MUSHROOM_GROVE, Settings.biomeWeight(MUSHROOM_GROVE.settingCategory)));
        addBiome(new BiomeManager.BiomeEntry(HOAR_FROST, Settings.biomeWeight(HOAR_FROST.settingCategory)));
        addBiome(new BiomeManager.BiomeEntry(HOT_SPRINGS, Settings.biomeWeight(HOT_SPRINGS.settingCategory)));

        DimensionManager.unregisterDimension(-1);
        DimensionType nether = DimensionType.register("Nether", "_nether", -1, WorldProviderNether.class, false);
        DimensionManager.registerDimension(-1, nether);
    }

    public static boolean addBiome(BiomeManager.BiomeEntry entry)
    {
        for(BiomeManager.BiomeEntry e : biomeEntries)
        {
            if(entry.biome == e.biome)
            {
                e.itemWeight += entry.itemWeight;
                return false;
            }
        }

        return biomeEntries.add(entry);
    }

    public static boolean removeBiome(Biome biome)
    {
        for(BiomeManager.BiomeEntry e : biomeEntries)
        {
            if(biome == e.biome)
            {
                return biomeEntries.remove(e);
            }
        }

        return false;
    }

    public static int getBiomeId(String name)
    {
        for(BiomeManager.BiomeEntry entry : biomeEntries)
        {
            if(entry.biome.getRegistryName().getResourcePath().equals(name))
            {
                return Biome.getIdForBiome(entry.biome);
            }
        }

        return -1;
    }

    public static ImmutableList<BiomeManager.BiomeEntry> getBiomeEntries()
    {
        return ImmutableList.copyOf(biomeEntries);
    }
}
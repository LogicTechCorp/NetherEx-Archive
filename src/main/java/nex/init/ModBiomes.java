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
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.DimensionManager;
import nex.world.WorldProviderNether;
import nex.world.biome.BiomeHell;
import nex.world.biome.BiomeMushroomGrove;
import nex.world.biome.BiomeRuthlessSands;

import java.util.Set;

public class ModBiomes
{
    private static Set<BiomeManager.BiomeEntry> biomeEntries = Sets.newHashSet();

    public static final Biome HELL;
    public static final Biome RUTHLESS_SANDS;
    public static final Biome MUSHROOM_GROVE;

    static
    {
        HELL = new BiomeHell();
        RUTHLESS_SANDS = new BiomeRuthlessSands();
        MUSHROOM_GROVE = new BiomeMushroomGrove();

        DimensionManager.unregisterDimension(-1);
        DimensionType nether = DimensionType.register("Nether", "_nether", -1, WorldProviderNether.class, false);
        DimensionManager.registerDimension(-1, nether);
    }

    public static void init()
    {

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
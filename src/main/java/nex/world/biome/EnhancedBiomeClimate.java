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

package nex.world.biome;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.world.biome.Biome;

import java.util.Map;

public enum EnhancedBiomeClimate
{
    HOT,
    WARM,
    TEMPERATE,
    COOL,
    COLD;

    private final Map<Biome, EnhancedBiomeEntry> biomeEntries = Maps.newHashMap();

    public void addBiome(EnhancedBiome enhancedBiome)
    {
        biomeEntries.put(enhancedBiome.getBiome(), new EnhancedBiomeEntry(enhancedBiome));
    }

    public void removeBiome(Biome biome)
    {
        biomeEntries.remove(biome);
    }

    public static EnhancedBiomeClimate getFromBiome(Biome biome)
    {
        for(EnhancedBiomeClimate enhancedBiomeClimate : values())
        {
            for(EnhancedBiomeEntry biomeEntry : enhancedBiomeClimate.getBiomeEntryMap().values())
            {
                if(biomeEntry.biome == biome)
                {
                    return enhancedBiomeClimate;
                }
            }
        }

        return TEMPERATE;
    }

    public static EnhancedBiomeClimate getFromString(String string)
    {
        if(!Strings.isNullOrEmpty(string))
        {
            for(EnhancedBiomeClimate enhancedBiomeClimate : values())
            {
                if(enhancedBiomeClimate.name().equalsIgnoreCase(string))
                {
                    return enhancedBiomeClimate;
                }
            }
        }

        return TEMPERATE;
    }

    public Map<Biome, EnhancedBiomeEntry> getBiomeEntryMap()
    {
        return ImmutableMap.copyOf(biomeEntries);
    }
}

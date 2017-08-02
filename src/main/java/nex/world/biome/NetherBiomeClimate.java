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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.world.biome.Biome;

import java.util.List;
import java.util.Map;

public enum NetherBiomeClimate
{
    HOT,
    WARM,
    TEMPERATE,
    COOL,
    COLD;

    private static final Map<Biome, NetherBiomeEntry> BIOME_ENTRIES = Maps.newHashMap();

    public void addBiome(EnhancedBiome enhancedBiome)
    {
        BIOME_ENTRIES.put(enhancedBiome.getBiome(), new NetherBiomeEntry(enhancedBiome));
    }

    public static NetherBiomeClimate getFromBiome(Biome biome)
    {
        for(NetherBiomeClimate biomeClimate : values())
        {
            for(NetherBiomeEntry biomeEntry : biomeClimate.getBiomeEntries())
            {
                if(biomeEntry.biome == biome)
                {
                    return biomeClimate;
                }
            }
        }

        return TEMPERATE;
    }

    public static NetherBiomeClimate getFromString(String string)
    {
        if(!Strings.isNullOrEmpty(string))
        {
            for(NetherBiomeClimate biomeClimate : values())
            {
                if(biomeClimate.name().equalsIgnoreCase(string))
                {
                    return biomeClimate;
                }
            }
        }

        return TEMPERATE;
    }

    public Map<Biome, NetherBiomeEntry> getBiomeEntryMap()
    {
        return ImmutableMap.copyOf(BIOME_ENTRIES);
    }

    public List<NetherBiomeEntry> getBiomeEntries()
    {
        return ImmutableList.copyOf(BIOME_ENTRIES.values());
    }
}

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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;

import java.util.List;
import java.util.Set;

public enum BiomeTypeNetherEx
{
    HOT,
    WARM,
    COOL,
    COLD;

    private static Set<BiomeManager.BiomeEntry> biomeEntries = Sets.newHashSet();
    private static Set<BiomeOceanEntry> oceanEntries = Sets.newHashSet();

    public BiomeTypeNetherEx addBiomeEntry(Biome biome, int weight)
    {
        if(weight <= 0)
        {
            weight = 1;
        }

        BiomeManager.BiomeEntry biomeEntry = new BiomeManager.BiomeEntry(biome, weight);

        for(BiomeManager.BiomeEntry entry : biomeEntries)
        {
            if(biomeEntry.biome == entry.biome)
            {
                entry.itemWeight += biomeEntry.itemWeight;
                break;
            }
        }

        biomeEntries.add(biomeEntry);
        return this;
    }

    public void addOceanEntry(Biome biome, IBlockState state)
    {
        BiomeOceanEntry oceanEntry = new BiomeOceanEntry(biome, state == Blocks.AIR.getDefaultState() ? Blocks.LAVA.getDefaultState() : state);

        if(!oceanEntries.contains(oceanEntry))
        {
            oceanEntries.add(oceanEntry);
        }
    }

    public IBlockState getBiomeOceanBlock(Biome biome)
    {
        for(BiomeOceanEntry oceanEntry : oceanEntries)
        {
            if(biome == oceanEntry.biome)
            {
                return oceanEntry.state;
            }
        }

        return Blocks.LAVA.getDefaultState();
    }

    public ImmutableList<BiomeManager.BiomeEntry> getBiomeEntries()
    {
        return ImmutableList.copyOf(biomeEntries);
    }

    public static BiomeTypeNetherEx getTypeFromBiome(Biome biome)
    {
        for(BiomeTypeNetherEx type : values())
        {
            for(BiomeManager.BiomeEntry entry : type.getBiomeEntries())
            {
                if(entry.biome == biome)
                {
                    return type;
                }
            }
        }

        return null;
    }

    public static ImmutableList<BiomeManager.BiomeEntry> getAllBiomeEntries()
    {
        List<BiomeManager.BiomeEntry> biomeEntries = Lists.newArrayList();

        for(BiomeTypeNetherEx type : values())
        {
            biomeEntries.addAll(type.getBiomeEntries());
        }

        return ImmutableList.copyOf(biomeEntries);
    }
}

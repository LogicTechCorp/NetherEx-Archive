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
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import nex.world.gen.layer.GenLayerNetherEx;

import java.util.List;
import java.util.Map;
import java.util.Random;

public enum NetherBiomeType
{
    HOT,
    WARM,
    TEMPERATE,
    COOL,
    COLD;

    private static final Map<Biome, NetherBiomeEntry> biomes = Maps.newHashMap();

    public void addBiome(Biome biome, int weight, IBlockState state)
    {
        biomes.put(biome, new NetherBiomeEntry(biome, weight, state));
    }

    public static NetherBiomeType getTypeFromBiome(Biome biome)
    {
        for(NetherBiomeType type : values())
        {
            for(NetherBiomeEntry entry : type.getBiomes())
            {
                if(entry.biome == biome)
                {
                    return type;
                }
            }
        }

        return TEMPERATE;
    }

    public static NetherBiomeType getFromString(String string)
    {
        if(!Strings.isNullOrEmpty(string))
        {
            for(NetherBiomeType type : values())
            {
                if(type.name().equalsIgnoreCase(string))
                {
                    return type;
                }
            }
        }

        return TEMPERATE;
    }

    public static List<NetherBiomeEntry> getAllBiomes()
    {
        List<NetherBiomeEntry> biomes = Lists.newArrayList();

        for(NetherBiomeType type : values())
        {
            biomes.addAll(type.getBiomes());
        }

        return biomes;
    }

    public static Biome getRandomBiome(List<NetherBiomeEntry> biomes, Random rand)
    {
        return WeightedRandom.getRandomItem(biomes, rand.nextInt(WeightedRandom.getTotalWeight(biomes))).getBiome();
    }

    public static Biome getRandomBiome(List<NetherBiomeEntry> biomes, GenLayerNetherEx layer)
    {
        return WeightedRandom.getRandomItem(biomes, layer.nextInt(WeightedRandom.getTotalWeight(biomes))).getBiome();
    }

    public List<NetherBiomeEntry> getBiomes()
    {
        return ImmutableList.copyOf(biomes.values());
    }

    public IBlockState getBiomeOceanBlock(Biome biome)
    {
        if(biomes.containsKey(biome))
        {
            return biomes.get(biome).getOceanBlock();
        }

        return Blocks.LAVA.getDefaultState();
    }
}

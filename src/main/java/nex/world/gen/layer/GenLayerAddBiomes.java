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

package nex.world.gen.layer;

import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeManager;
import nex.init.NetherExBiomes;

import java.util.List;

public class GenLayerAddBiomes extends GenLayerNether
{
    public GenLayerAddBiomes(long seed)
    {
        super(seed);
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
    {
        int[] biomeIds = IntCache.getIntCache(areaWidth * areaHeight);

        for(int i = 0; i < areaHeight; ++i)
        {
            for(int j = 0; j < areaWidth; ++j)
            {
                initChunkSeed((long) (j + areaX), (long) (i + areaY));
                biomeIds[j + i * areaWidth] = Biome.getIdForBiome(getWeightedBiomeEntry(NetherExBiomes.getBiomeEntries()).biome);
            }
        }

        return biomeIds;
    }

    private BiomeManager.BiomeEntry getWeightedBiomeEntry(List<BiomeManager.BiomeEntry> biomeEntries)
    {
        return WeightedRandom.getRandomItem(biomeEntries, nextInt(WeightedRandom.getTotalWeight(biomeEntries)));
    }
}

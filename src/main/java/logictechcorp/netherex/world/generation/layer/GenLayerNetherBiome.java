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

package logictechcorp.netherex.world.generation.layer;

import logictechcorp.netherex.api.NetherExAPI;
import net.minecraft.init.Biomes;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeManager;

import java.util.ArrayList;
import java.util.List;

public class GenLayerNetherBiome extends GenLayerNetherEx
{
    public GenLayerNetherBiome(long seed, GenLayer parent)
    {
        super(seed);
        this.parent = parent;
    }

    @Override
    public int[] getInts(int areaX, int areaZ, int areaWidth, int areaHeight)
    {
        int[] outputs = IntCache.getIntCache(areaWidth * areaHeight);

        for(int z = 0; z < areaHeight; z++)
        {
            for(int x = 0; x < areaWidth; x++)
            {
                this.initChunkSeed(x + areaX, z + areaZ);
                outputs[x + z * areaWidth] = Biome.getIdForBiome(this.getRandomBiome());
            }
        }

        return outputs;
    }

    private Biome getRandomBiome()
    {
        List<BiomeManager.BiomeEntry> biomeEntries = new ArrayList<>(NetherExAPI.getInstance().getBiomeDataRegistry().getBiomeEntries().values());
        int biomeWeights = WeightedRandom.getTotalWeight(biomeEntries);
        return biomeWeights <= 0 ? Biomes.HELL : WeightedRandom.getRandomItem(biomeEntries, this.nextInt(biomeWeights)).biome;
    }
}

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

import logictechcorp.libraryex.world.biome.BiomeData;
import logictechcorp.netherex.handler.NetherDimensionManager;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset1Transformer;
import net.minecraftforge.common.BiomeManager;

import java.util.ArrayList;
import java.util.List;

public enum NetherSubBiomeLayer implements IAreaTransformer2, IDimOffset1Transformer
{
    INSTANCE;

    @Override
    public int apply(INoiseRandom random, IArea biomeArea, IArea subBiomeArea, int x, int z)
    {
        int biomeId = biomeArea.getValue(this.func_215721_a(x + 1), this.func_215722_b(z + 1));
        int subBiomeChance = (subBiomeArea.getValue(this.func_215721_a(x + 1), this.func_215722_b(z + 1)) - 2) % 29;

        boolean tryRareBiome = (subBiomeChance == 1);
        boolean tryRareHillsBiome = (subBiomeChance == 2);

        if(biomeId != 0 && tryRareBiome)
        {
            return this.getRandomBiome(random, biomeId);
        }
        else if(random.random(3) != 0 && !tryRareHillsBiome)
        {
            return biomeId;
        }
        else
        {
            int subBiomeId = this.getRandomBiome(random, biomeId);

            if(subBiomeId == biomeId)
            {
                return biomeId;
            }
            else
            {
                if(tryRareHillsBiome)
                {
                    subBiomeId = this.getRandomBiome(random, subBiomeId);
                }

                int similarCount = 0;

                if(LayerUtil.areBiomesSimilar(biomeArea.getValue(x + 1, z), biomeId))
                {
                    similarCount++;
                }
                if(LayerUtil.areBiomesSimilar(biomeArea.getValue(x, z + 1), biomeId))
                {
                    similarCount++;
                }
                if(LayerUtil.areBiomesSimilar(biomeArea.getValue(x + 1, z + 2), biomeId))
                {
                    similarCount++;
                }
                if(LayerUtil.areBiomesSimilar(biomeArea.getValue(x + 2, z + 1), biomeId))
                {
                    similarCount++;
                }

                if(similarCount >= 3)
                {
                    return subBiomeId;
                }
            }
        }

        return biomeId;
    }

    private int getRandomBiome(INoiseRandom random, int biomeId)
    {
        Biome biome = Registry.BIOME.getByValue(biomeId);

        if(biome != null)
        {
            BiomeData biomeData = NetherDimensionManager.getBiomeData().get(biome.getRegistryName());

            if(biomeData != null)
            {
                List<BiomeManager.BiomeEntry> biomeEntries = new ArrayList<>();

                for(BiomeData subBiomeData : biomeData.getSubBiomes())
                {
                    if(subBiomeData != null)
                    {
                        biomeEntries.add(new BiomeManager.BiomeEntry(subBiomeData.getBiome(), subBiomeData.getGenerationWeight()));
                    }
                }

                int biomeWeights = WeightedRandom.getTotalWeight(biomeEntries);

                if(biomeWeights > 0)
                {
                    return Registry.BIOME.getId(WeightedRandom.getRandomItem(biomeEntries, random.random(biomeWeights)).biome);
                }
            }

            return biomeId;
        }

        return Registry.BIOME.getId(Biomes.NETHER);
    }
}

/*
 * Copyright 2014-2016, the Biomes O' Plenty Team
 *
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 *
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 *
 * Original: https://github.com/Glitchfiend/BiomesOPlenty/blob/71f8cd626617e08caa70b6127ee887ab9342b204/src/main/java/biomesoplenty/common/world/layer/GenLayerSubBiomesBOP.java
 */

package logictechcorp.netherex.world.generation.layer;

import logictechcorp.libraryex.api.world.biome.data.IBiomeData;
import logictechcorp.netherex.api.NetherExAPI;
import net.minecraft.init.Biomes;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeManager;

import java.util.ArrayList;
import java.util.List;

public class GenLayerNetherSubBiome extends GenLayer
{
    private GenLayer subBiomeLayer;

    public GenLayerNetherSubBiome(long seed, GenLayer biomeLayer, GenLayer subBiomeLayer)
    {
        super(seed);
        this.parent = biomeLayer;
        this.subBiomeLayer = subBiomeLayer;

    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
    {
        int outerWidth = areaWidth + 2;
        int outerHeight = areaHeight + 2;
        int[] biomeIds = this.parent.getInts(areaX - 1, areaY - 1, outerWidth, outerHeight);
        int[] subBiomeChances = this.subBiomeLayer.getInts(areaX - 1, areaY - 1, outerWidth, outerHeight);
        int[] outputs = IntCache.getIntCache(areaWidth * areaHeight);

        for(int y = 0; y < areaHeight; y++)
        {
            for(int x = 0; x < areaWidth; x++)
            {
                this.initChunkSeed((long) (x + areaX), (long) (y + areaY));
                int biomeId = biomeIds[x + 1 + (y + 1) * (outerWidth)];

                int subBiomeChance = (subBiomeChances[x + 1 + (y + 1) * (outerWidth)] % 29);

                boolean tryRareBiome = (subBiomeChance == 1);
                boolean tryRareHillsBiome = (subBiomeChance == 2);

                if(biomeId != 0 && tryRareBiome)
                {
                    outputs[x + y * areaWidth] = this.getRandomBiome(biomeId);
                }
                else if(this.nextInt(3) != 0 && !tryRareHillsBiome)
                {
                    outputs[x + y * areaWidth] = biomeId;
                }
                else
                {
                    int subBiomeId = this.getRandomBiome(biomeId);

                    if(subBiomeId == biomeId)
                    {
                        outputs[x + y * areaWidth] = biomeId;
                    }
                    else
                    {
                        if(tryRareHillsBiome)
                        {
                            subBiomeId = this.getRandomBiome(subBiomeId);
                        }

                        int biomeNorth = biomeIds[x + 1 + (y + 1 - 1) * (outerWidth)];
                        int biomeEast = biomeIds[x + 1 + 1 + (y + 1) * (outerWidth)];
                        int biomeWest = biomeIds[x + 1 - 1 + (y + 1) * (outerWidth)];
                        int biomeSouth = biomeIds[x + 1 + (y + 1 + 1) * (outerWidth)];
                        int surroundingSameCount = 0;

                        if(biomesEqualOrMesaPlateau(biomeNorth, biomeId))
                        {
                            surroundingSameCount++;
                        }
                        if(biomesEqualOrMesaPlateau(biomeEast, biomeId))
                        {
                            surroundingSameCount++;
                        }
                        if(biomesEqualOrMesaPlateau(biomeWest, biomeId))
                        {
                            surroundingSameCount++;
                        }
                        if(biomesEqualOrMesaPlateau(biomeSouth, biomeId))
                        {
                            surroundingSameCount++;
                        }

                        if(surroundingSameCount >= 3)
                        {
                            outputs[x + y * areaWidth] = subBiomeId;
                        }
                        else
                        {
                            outputs[x + y * areaWidth] = biomeId;
                        }
                    }
                }
            }
        }

        return outputs;
    }

    private int getRandomBiome(int biomeId)
    {
        Biome biome = Biome.getBiomeForId(biomeId);

        if(biome != null)
        {
            IBiomeData biomeData = NetherExAPI.getInstance().getBiomeDataRegistry().getBiomeData(biome);

            if(biomeData != null)
            {
                List<BiomeManager.BiomeEntry> biomeEntries = new ArrayList<>();

                for(IBiomeData subBiomeData : biomeData.getSubBiomeData())
                {
                    if(subBiomeData != null && subBiomeData.generateBiome())
                    {
                        biomeEntries.add(new BiomeManager.BiomeEntry(subBiomeData.getBiome(), subBiomeData.getBiomeGenerationWeight()));
                    }
                }

                int biomeWeights = WeightedRandom.getTotalWeight(biomeEntries);

                if(biomeWeights > 0)
                {
                    return Biome.getIdForBiome(WeightedRandom.getRandomItem(biomeEntries, this.nextInt(biomeWeights)).biome);
                }
            }

            return biomeId;
        }

        return Biome.getIdForBiome(Biomes.HELL);
    }
}

/*
 * Copyright 2014-2016, the Biomes O' Plenty Team
 *
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 *
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 *
 * Original: https://github.com/Glitchfiend/BiomesOPlenty/blob/71f8cd626617e08caa70b6127ee887ab9342b204/src/main/java/biomesoplenty/common/world/layer/GenLayerSubBiomesBOP.java
 * Edited to fit a newer Minecraft version
 */

package logictechcorp.netherex.world.generation.layer;

import logictechcorp.libraryex.world.biome.BiomeData;
import logictechcorp.netherex.NetherEx;
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
            BiomeData biomeData = NetherEx.BIOME_DATA_MANAGER.getBiomeData().get(biome.getRegistryName());

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

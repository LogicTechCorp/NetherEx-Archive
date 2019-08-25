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

import logictechcorp.netherex.NetherEx;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC0Transformer;
import net.minecraftforge.common.BiomeManager;

import java.util.ArrayList;
import java.util.List;

public enum NetherBiomeLayer implements IC0Transformer
{
    INSTANCE;

    @Override
    public int apply(INoiseRandom random, int value)
    {
        return Registry.BIOME.getId(this.getBiome(random));
    }

    private Biome getBiome(INoiseRandom random)
    {
        List<BiomeManager.BiomeEntry> biomeEntries = new ArrayList<>(NetherEx.BIOME_DATA_MANAGER.getBiomeEntries().values());
        int biomeWeights = WeightedRandom.getTotalWeight(biomeEntries);
        return biomeWeights <= 0 ? Biomes.NETHER : WeightedRandom.getRandomItem(biomeEntries, random.random(biomeWeights)).biome;
    }
}

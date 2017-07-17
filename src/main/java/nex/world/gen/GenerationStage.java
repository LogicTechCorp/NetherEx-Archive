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

package nex.world.gen;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.world.biome.Biome;
import nex.world.gen.feature.BiomeFeature;

import java.util.List;
import java.util.Map;

public enum GenerationStage
{
    PRE_POPULATE,
    POST_POPULATE,
    PRE_DECORATE,
    PRE_ORES,
    POST_ORES,
    POST_DECORATE;

    private static final Map<Biome, List<BiomeFeature>> BIOME_FEATURE = Maps.newHashMap();

    public void addBiomeFeature(Biome biome, BiomeFeature feature)
    {
        BIOME_FEATURE.computeIfAbsent(biome, k -> Lists.newArrayList()).add(feature);
    }

    public List<BiomeFeature> getBiomeFeatures(Biome biome)
    {
        return Lists.newArrayList(BIOME_FEATURE.get(biome));
    }

    public static GenerationStage getFromString(String string)
    {
        if(!Strings.isNullOrEmpty(string))
        {
            for(GenerationStage generationStage : values())
            {
                if(generationStage.name().equalsIgnoreCase(string))
                {
                    return generationStage;
                }
            }
        }

        return POST_DECORATE;
    }
}

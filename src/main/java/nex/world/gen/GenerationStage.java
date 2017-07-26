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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.world.biome.Biome;
import nex.api.world.gen.feature.IBiomeFeature;

import java.util.List;
import java.util.Map;

public enum GenerationStage
{
    PRE_POPULATE,
    LAVA_FALL,
    FIRE,
    GLOWSTONE,
    POST_POPULATE,
    PRE_DECORATE,
    MUSHROOM,
    PRE_ORE,
    QUARTZ,
    POST_ORE,
    MAGMA,
    LAVA_TRAP,
    POST_DECORATE;

    private final Map<Biome, List<IBiomeFeature>> BIOME_FEATURE_MAP = Maps.newHashMap();

    public void addBiomeFeature(Biome biome, IBiomeFeature feature)
    {
        BIOME_FEATURE_MAP.computeIfAbsent(biome, k -> Lists.newArrayList()).add(feature);
    }

    public static GenerationStage getFromString(String string)
    {
        if(!Strings.isNullOrEmpty(string))
        {
            for(GenerationStage generationStage : values())
            {
                if(generationStage.name().replace("_", "").equalsIgnoreCase(string))
                {
                    return generationStage;
                }
            }
        }

        return POST_DECORATE;
    }

    public Map<Biome, List<IBiomeFeature>> getBiomeFeatureMap()
    {
        return ImmutableMap.copyOf(BIOME_FEATURE_MAP);
    }
}

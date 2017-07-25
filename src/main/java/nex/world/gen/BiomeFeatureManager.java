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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.world.biome.Biome;
import nex.world.gen.feature.BiomeFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class BiomeFeatureManager
{
    private static final Map<String, Class<? extends BiomeFeature>> BIOME_FEATURE_CLASS_IDENTIFIER_MAP = Maps.newHashMap();

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|BiomeFeatureManager");

    public static void registerBiomeFeature(String identifier, Class<? extends BiomeFeature> biomeFeatureClass)
    {
        if(!BIOME_FEATURE_CLASS_IDENTIFIER_MAP.containsKey(identifier))
        {
            BIOME_FEATURE_CLASS_IDENTIFIER_MAP.put(identifier, biomeFeatureClass);
        }
        else
        {
            LOGGER.warn("A biome feature with the identifier, " + identifier + ", is already registered.");
        }
    }

    public static Class<? extends BiomeFeature> getBiomeFeatureClass(String identifier)
    {
        if(BIOME_FEATURE_CLASS_IDENTIFIER_MAP.containsKey(identifier))
        {
            return BIOME_FEATURE_CLASS_IDENTIFIER_MAP.get(identifier);
        }

        return null;
    }

    public static List<BiomeFeature> getBiomeFeatures(Biome biome, GenerationStage generationStage)
    {
        Map<Biome, List<BiomeFeature>> biomeFeatureMap = generationStage.getBiomeFeatureMap();

        if(biomeFeatureMap.containsKey(biome))
        {
            return biomeFeatureMap.get(biome);
        }

        return Lists.newArrayList();
    }
}

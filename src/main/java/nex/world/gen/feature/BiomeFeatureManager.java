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

package nex.world.gen.feature;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import nex.init.NetherExRegistries;
import nex.world.gen.GenerationStage;

import java.util.List;
import java.util.Map;

public class BiomeFeatureManager
{
    public static BiomeFeature deserialize(JsonObject config)
    {
        BiomeFeature feature = getBiomeFeature(new ResourceLocation(JsonUtils.getString(config, "featureType", "")));

        if(feature != null)
        {
            return feature.deserialize(config);
        }

        return null;
    }

    public static BiomeFeature getBiomeFeature(ResourceLocation identifier)
    {
        if(NetherExRegistries.BIOME_FEATURES.containsKey(identifier))
        {
            return NetherExRegistries.BIOME_FEATURES.getValue(identifier);
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

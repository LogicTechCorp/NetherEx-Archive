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

import com.google.common.base.Strings;
import com.google.gson.JsonObject;
import net.minecraft.util.JsonUtils;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import nex.world.gen.GenerationStage;

public abstract class BiomeFeature extends WorldGenerator
{
    private Biome biome;
    private GenerationStage generationStage;
    private final int generationAttempts;
    private final int minHeight;
    private final int maxHeight;


    public BiomeFeature(Biome biomeIn, GenerationStage generationStageIn, int generationAttemptsIn, int minHeightIn, int maxHeightIn)
    {
        biome = biomeIn;
        generationStage = generationStageIn;
        generationAttempts = generationAttemptsIn;
        minHeight = minHeightIn;
        maxHeight = maxHeightIn;
    }

    public static BiomeFeature deserialize(JsonObject config)
    {
        String biomeFeature = JsonUtils.getString(config, "featureType", "");

        try
        {
            return Type.getFromString(biomeFeature).getBiomeFeatureClass().newInstance().deserializeFeature(config);
        }
        catch(InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public abstract BiomeFeature deserializeFeature(JsonObject config);

    public Biome getBiome()
    {
        return biome;
    }

    public GenerationStage getGenerationStage()
    {
        return generationStage;
    }

    public int getGenerationAttempts()
    {
        return generationAttempts;
    }

    public int getMinHeight()
    {
        return minHeight;
    }

    public int getMaxHeight()
    {
        return maxHeight;
    }

    private enum Type
    {
        SCATTERED(BiomeFeatureScattered.class),
        CLUMPED(BiomeFeatureClumped.class),
        ORE(BiomeFeatureOre.class),
        FLUID(BiomeFeatureFluid.class),
        POOL(BiomeFeaturePool.class),
        UNKNOWN(BiomeFeature.class);

        private Class<? extends BiomeFeature> featureCls;

        Type(Class<? extends BiomeFeature> featureClsIn)
        {
            featureCls = featureClsIn;
        }

        public static Type getFromString(String string)
        {
            if(!Strings.isNullOrEmpty(string))
            {
                for(Type type : values())
                {
                    if(type.name().equalsIgnoreCase(string))
                    {
                        return type;
                    }
                }
            }

            return UNKNOWN;
        }

        public Class<? extends  BiomeFeature> getBiomeFeatureClass()
        {
            return featureCls;
        }
    }
}

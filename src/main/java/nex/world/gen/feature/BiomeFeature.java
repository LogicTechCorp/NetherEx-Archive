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

import com.google.gson.JsonObject;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import nex.world.gen.BiomeFeatureManager;

import java.util.Random;

public abstract class BiomeFeature<F extends BiomeFeature> extends WorldGenerator
{
    private float amountPerChunk;
    private int minHeight;
    private int maxHeight;

    public BiomeFeature()
    {

    }

    protected BiomeFeature(float amountPerChunkIn, int minHeightIn, int maxHeightIn)
    {
        amountPerChunk = amountPerChunkIn;
        minHeight = minHeightIn;
        maxHeight = maxHeightIn;
    }

    public abstract F deserialize(JsonObject config);

    @Override
    public abstract boolean generate(World world, Random rand, BlockPos pos);

    public float getAmountPerChunk()
    {
        return amountPerChunk;
    }

    public int getMinHeight()
    {
        return minHeight;
    }

    public int getMaxHeight()
    {
        return maxHeight;
    }

    public static class Factory
    {
        public static BiomeFeature deserialize(JsonObject config)
        {
            String featureClassIdentifier = JsonUtils.getString(config, "featureType", "");
            Class<? extends BiomeFeature> featureClass = BiomeFeatureManager.getBiomeFeatureClass(featureClassIdentifier);

            if(featureClass != null)
            {
                try
                {
                    return featureClass.newInstance().deserialize(config);
                }
                catch(InstantiationException | IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }
}

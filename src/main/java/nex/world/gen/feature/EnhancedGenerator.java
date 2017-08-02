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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public abstract class EnhancedGenerator extends WorldGenerator
{
    private final int generationAttempts;
    private final float generationProbability;
    private final int minHeight;
    private final int maxHeight;

    protected EnhancedGenerator(int generationAttemptsIn, float generationProbabilityIn, int minHeightIn, int maxHeightIn)
    {
        generationAttempts = generationAttemptsIn;
        generationProbability = generationProbabilityIn;
        minHeight = minHeightIn;
        maxHeight = maxHeightIn;
    }

    public static EnhancedGenerator deserialize(JsonObject config)
    {
        GeneratorType generatorType = GeneratorType.getFromString(JsonUtils.getString(config, "generator", ""));

        switch(generatorType)
        {
            case STRUCTURE:
                return EnhancedGeneratorStructure.INSTANCE.deserializeConfig(config);
            case FLUID:
                return EnhancedGeneratorFluid.INSTANCE.deserializeConfig(config);
            case SCATTER:
                return EnhancedGeneratorScatter.INSTANCE.deserializeConfig(config);
            case CLUSTER:
                return EnhancedGeneratorCluster.INSTANCE.deserializeConfig(config);
            case ORE:
                return EnhancedGeneratorOre.INSTANCE.deserializeConfig(config);
            case POOL:
                return EnhancedGeneratorPool.INSTANCE.deserializeConfig(config);
            case THORNSTALK:
                return EnhancedGeneratorThornstalk.INSTANCE.deserializeConfig(config);
            case ENOKI:
                return EnhancedGeneratorEnoki.INSTANCE.deserializeConfig(config);
            case UNKNOWN:
            default:
                return null;
        }
    }

    public abstract EnhancedGenerator deserializeConfig(JsonObject config);

    @Override
    public abstract boolean generate(World world, Random rand, BlockPos pos);

    public int getGenerationAttempts()
    {
        return generationAttempts;
    }

    public int getGenAttempts(Random rand)
    {
        int attempts = getGenerationAttempts();
        float probability = getGenerationProbability();

        if(probability > 0.0F && probability < 1.0F && rand.nextFloat() > probability)
        {
            attempts = 0;
        }
        if(attempts < 0)
        {
            attempts = rand.nextInt(MathHelper.abs(attempts)) + 1;
        }

        return attempts;
    }

    public float getGenerationProbability()
    {
        return generationProbability;
    }

    public int getMinHeight()
    {
        return minHeight;
    }

    public int getMaxHeight()
    {
        return maxHeight;
    }

    private enum GeneratorType
    {
        STRUCTURE,
        FLUID,
        SCATTER,
        CLUSTER,
        ORE,
        POOL,
        THORNSTALK,
        ENOKI,
        UNKNOWN;

        public static GeneratorType getFromString(String string)
        {
            if(!Strings.isNullOrEmpty(string))
            {
                for(GeneratorType generatorType : values())
                {
                    if(generatorType.name().equalsIgnoreCase(string))
                    {
                        return generatorType;
                    }
                }
            }

            return UNKNOWN;
        }

    }
}

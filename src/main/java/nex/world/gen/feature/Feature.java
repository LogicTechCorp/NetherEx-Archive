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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import nex.world.biome.NetherBiome;

import java.util.Random;

public abstract class Feature
{
    private final Biome biome;
    private final int rarity;
    private final int minHeight;
    private final int maxHeight;
    private final boolean randomRarity;
    private final boolean superRare;

    public Feature(Biome biomeIn, NetherBiome.BiomeFeature feature)
    {
        biome = biomeIn;
        rarity = feature.getRarity() <= 0 ? 10 : feature.getRarity();
        minHeight = feature.getMinHeight() <= 0 || feature.getMinHeight() >= 128 ? 4 : feature.getMinHeight();
        maxHeight = feature.getMaxHeight() >= 120 || feature.getMaxHeight() <= 0 ? 108 : feature.getMaxHeight();
        randomRarity = feature.useRandomRarity();
        superRare = feature.isSuperRare();
    }

    public abstract boolean generate(World world, BlockPos pos, Random rand);

    public abstract boolean canGenerate();

    public abstract FeatureType getType();

    public Biome getBiome()
    {
        return biome;
    }

    public int getRarity()
    {
        return rarity;
    }

    public int getMinHeight()
    {
        return minHeight;
    }

    public int getMaxHeight()
    {
        return maxHeight;
    }

    public boolean useRandomRarity()
    {
        return randomRarity;
    }

    public boolean isSuperRare()
    {
        return superRare;
    }

    public enum FeatureType
    {
        SCATTERED,
        CLUMPED,
        ORE,
        FLUID,
        POOL,
        STRUCTURE,
        UNKNOWN;

        public static FeatureType getFromString(String string)
        {
            if(!Strings.isNullOrEmpty(string))
            {
                for(FeatureType type : values())
                {
                    if(type.name().equalsIgnoreCase(string))
                    {
                        return type;
                    }
                }
            }

            return UNKNOWN;
        }
    }
}

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

import java.util.Random;

public abstract class Feature
{
    private final FeatureType type;
    private final int rarity;
    private final int minHeight;
    private final int maxHeight;
    protected boolean canGenerate;

    public Feature(FeatureType typeIn, int rarityIn, int minHeightIn, int maxHeightIn)
    {
        type = typeIn;
        rarity = rarityIn;
        minHeight = minHeightIn;
        maxHeight = maxHeightIn;
        canGenerate = true;
    }

    public abstract boolean generate(World world, BlockPos pos, Random rand);

    public boolean canGenerate()
    {
        return canGenerate;
    }

    public FeatureType getType()
    {
        return type;
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

    public enum FeatureType
    {
        FIRE,
        GLOWSTONE,
        ORE,
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

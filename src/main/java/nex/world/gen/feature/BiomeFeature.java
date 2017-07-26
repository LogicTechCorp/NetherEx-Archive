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

import nex.api.world.gen.feature.IBiomeFeature;

public abstract class BiomeFeature<F extends BiomeFeature> implements IBiomeFeature<F>
{
    private final float genAttempts;
    private final int minHeight;
    private final int maxHeight;

    protected BiomeFeature(float genAttemptsIn, int minHeightIn, int maxHeightIn)
    {
        genAttempts = genAttemptsIn;
        minHeight = minHeightIn;
        maxHeight = maxHeightIn;
    }

    @Override
    public float getGenAttempts()
    {
        return genAttempts;
    }

    @Override
    public int getMinHeight()
    {
        return minHeight;
    }

    @Override
    public int getMaxHeight()
    {
        return maxHeight;
    }
}

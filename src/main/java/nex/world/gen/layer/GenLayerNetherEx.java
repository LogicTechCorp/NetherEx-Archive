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

package nex.world.gen.layer;

import net.minecraft.world.WorldType;
import net.minecraft.world.gen.layer.*;

public abstract class GenLayerNetherEx extends GenLayer
{
    public GenLayerNetherEx(long seed)
    {
        super(seed);
    }

    public static GenLayer[] initializeAllBiomeGenerators(long seed, WorldType type)
    {
        int biomeSize = type == WorldType.LARGE_BIOMES ? 6 : 4;
        biomeSize = getModdedBiomeSize(type, biomeSize);

        GenLayer genLayer = new GenLayerNetherExBiome(1L);
        genLayer = new GenLayerFuzzyZoom(1000L, genLayer);
        genLayer = GenLayerZoom.magnify(1000L, genLayer, biomeSize);
        genLayer = new GenLayerSmooth(1000L, genLayer);
        GenLayer genLayerVoronoiZoom = new GenLayerVoronoiZoom(10L, genLayer);

        genLayer.initWorldGenSeed(seed);
        genLayerVoronoiZoom.initWorldGenSeed(seed);

        return new GenLayer[]{genLayer, genLayerVoronoiZoom};
    }

    @Override
    public int nextInt(int i)
    {
        return super.nextInt(i);
    }
}

/*
 * Copyright (C) 2016.  LogicTechCorp
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

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public abstract class GenLayerNether extends GenLayer
{
    public GenLayerNether(long seed)
    {
        super(seed);
    }

    public static GenLayer[] initializeAllBiomeGenerators(long seed)
    {
        GenLayer genLayer = new GenLayerNetherBiome(1L);
        genLayer = new GenLayerZoom(1000L, genLayer);
        genLayer = new GenLayerZoom(1001L, genLayer);
        genLayer = new GenLayerZoom(1002L, genLayer);
        genLayer = new GenLayerZoom(1003L, genLayer);
        genLayer = new GenLayerZoom(1004L, genLayer);
        genLayer = new GenLayerZoom(1005L, genLayer);
        GenLayer genLayerVZoom = new GenLayerVoronoiZoom(10L, genLayer);

        genLayer.initWorldGenSeed(seed);
        genLayerVZoom.initWorldGenSeed(seed);

        return new GenLayer[]{genLayer, genLayerVZoom};
    }
}
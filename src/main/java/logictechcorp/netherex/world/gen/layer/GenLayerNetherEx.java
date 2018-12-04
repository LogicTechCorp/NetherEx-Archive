/*
 * NetherEx
 * Copyright (c) 2016-2018 by MineEx
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

package logictechcorp.netherex.world.gen.layer;

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

        GenLayer genBiomesLayer = new GenLayerIsland(1L);
        genBiomesLayer = new GenLayerNetherBiome(200L, genBiomesLayer);
        genBiomesLayer = new GenLayerZoom(1000L, genBiomesLayer);
        genBiomesLayer = new GenLayerZoom(1000L, genBiomesLayer);

        for(int i = 0; i < biomeSize; i++)
        {
            genBiomesLayer = new GenLayerZoom(1000L + i, genBiomesLayer);
        }

        genBiomesLayer = new GenLayerSmooth(1000L, genBiomesLayer);
        GenLayer biomeIndexLayer = new GenLayerVoronoiZoom(10L, genBiomesLayer);
        genBiomesLayer.initWorldGenSeed(seed);
        biomeIndexLayer.initWorldGenSeed(seed);
        return new GenLayer[]{genBiomesLayer, biomeIndexLayer};
    }
}

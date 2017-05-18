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

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.IntCache;
import nex.world.biome.NetherExBiomeType;

public class GenLayerNetherExBiome extends GenLayerNetherEx
{
    public GenLayerNetherExBiome(long seed)
    {
        super(seed);
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
    {
        int[] outputs = IntCache.getIntCache(areaWidth * areaHeight);

        for(int x = 0; x < areaHeight; x++)
        {
            for(int y = 0; y < areaWidth; y++)
            {
                initChunkSeed(x + areaX, y + areaY);
                outputs[x + y * areaWidth] = Biome.getIdForBiome(NetherExBiomeType.getRandomBiome(NetherExBiomeType.getAllBiomes(), this));
            }
        }

        return outputs;
    }
}

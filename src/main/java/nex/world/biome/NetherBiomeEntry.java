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

package nex.world.biome;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;

public class NetherBiomeEntry extends BiomeManager.BiomeEntry
{
    private final IBlockState topBlock;
    private final IBlockState fillerBlock;
    private final IBlockState oceanBlock;

    public NetherBiomeEntry(NetherBiome netherBiome)
    {
        super(netherBiome.getBiome(), netherBiome.getWeight());

        topBlock = netherBiome.getTopBlock();
        fillerBlock = netherBiome.getFillerBlock();
        oceanBlock = netherBiome.getOceanBlock();
    }

    public Biome getBiome()
    {
        return biome;
    }

    public int getWeight()
    {
        return itemWeight;
    }

    public IBlockState getTopBlock()
    {
        return topBlock;
    }

    public IBlockState getFillerBlock()
    {
        return fillerBlock;
    }

    public IBlockState getOceanBlock()
    {
        return oceanBlock;
    }
}

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

public class EnhancedBiomeEntry extends BiomeManager.BiomeEntry
{
    private final IBlockState floorTopBlock;
    private final IBlockState floorFillerBlock;
    private IBlockState wallBlock;
    private IBlockState roofBottomBlock;
    private IBlockState roofFillerBlock;
    private final IBlockState oceanBlock;

    public EnhancedBiomeEntry(EnhancedBiome enhancedBiome)
    {
        super(enhancedBiome.getBiome(), enhancedBiome.getWeight());

        floorTopBlock = enhancedBiome.getFloorTopBlock();
        floorFillerBlock = enhancedBiome.getFloorFillerBlock();
        wallBlock = enhancedBiome.getWallBlock();
        roofBottomBlock = enhancedBiome.getRoofBottomBlock();
        roofFillerBlock = enhancedBiome.getRoofFillerBlock();
        oceanBlock = enhancedBiome.getOceanBlock();
    }

    public Biome getBiome()
    {
        return biome;
    }

    public int getWeight()
    {
        return itemWeight;
    }

    public IBlockState getFloorTopBlock()
    {
        return floorTopBlock;
    }

    public IBlockState getFloorFillerBlock()
    {
        return floorFillerBlock;
    }

    public IBlockState getWallBlock()
    {
        return wallBlock;
    }

    public IBlockState getRoofBottomBlock()
    {
        return roofBottomBlock;
    }

    public IBlockState getRoofFillerBlock()
    {
        return roofFillerBlock;
    }

    public IBlockState getOceanBlock()
    {
        return oceanBlock;
    }
}

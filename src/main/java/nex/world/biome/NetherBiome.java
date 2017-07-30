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

import com.google.gson.JsonObject;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class NetherBiome extends IForgeRegistryEntry.Impl<NetherBiome>
{
    private final Biome biome;
    private final int weight;
    private final IBlockState floorTopBlock;
    private final IBlockState floorFillerBlock;
    private final IBlockState wallBlock;
    private final IBlockState roofBottomBlock;
    private final IBlockState roofFillerBlock;
    private final IBlockState oceanBlock;

    protected NetherBiome(Biome biomeIn, int weightIn, IBlockState floorTopBlockIn, IBlockState floorFillerBlockIn, IBlockState wallBlockIn, IBlockState roofBottomBlockIn, IBlockState roofFillerBlockIn, IBlockState oceanBlockIn)
    {
        biome = biomeIn;
        weight = weightIn;
        floorTopBlock = floorTopBlockIn;
        floorFillerBlock = floorFillerBlockIn;
        wallBlock = wallBlockIn;
        roofBottomBlock = roofBottomBlockIn;
        roofFillerBlock = roofFillerBlockIn;
        oceanBlock = oceanBlockIn;
    }

    public abstract NetherBiome deserialize(JsonObject config);

    public Biome getBiome()
    {
        return biome;
    }

    public int getWeight()
    {
        return weight;
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

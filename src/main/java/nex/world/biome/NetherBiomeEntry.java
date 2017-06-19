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
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;

public class NetherBiomeEntry extends BiomeManager.BiomeEntry
{
    private final IBlockState oceanBlock;

    public NetherBiomeEntry(Biome biome, int weight, IBlockState oceanBlockIn)
    {
        super(biome, weight <= 0 ? 10 : weight);

        oceanBlock = oceanBlockIn.getBlock() == Blocks.AIR ? Blocks.LAVA.getDefaultState() : oceanBlockIn;
    }

    public Biome getBiome()
    {
        return biome;
    }

    public int getWeight()
    {
        return itemWeight;
    }

    public IBlockState getOceanBlock()
    {
        return oceanBlock;
    }
}

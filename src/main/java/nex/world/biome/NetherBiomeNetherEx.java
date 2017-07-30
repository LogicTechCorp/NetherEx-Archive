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
import nex.NetherEx;

public abstract class NetherBiomeNetherEx extends NetherBiome
{
    protected NetherBiomeNetherEx(String identifier, Biome biomeIn, int weightIn, IBlockState floorTopBlockIn, IBlockState floorFillerBlockIn, IBlockState wallBlockIn, IBlockState roofBottomBlockIn, IBlockState roofFillerBlockIn, IBlockState oceanBlockIn)
    {
        super(biomeIn, weightIn, floorTopBlockIn, floorFillerBlockIn, wallBlockIn, roofBottomBlockIn, roofFillerBlockIn, oceanBlockIn);

        setRegistryName(NetherEx.MOD_ID + ":" + identifier);
    }
}

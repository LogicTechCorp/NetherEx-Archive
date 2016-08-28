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

package nex.api;

import net.minecraft.block.state.IBlockState;

/**
 * An interface that will allow you to enhance different aspects of
 * a Nether biome
 * <p>
 * Do not implement this interface unless you want to change a specific
 * aspect of your Nether biome
 */
public interface IEnhancedNetherBiome
{
    /**
     * Return an IBlockState that will replace the default ocean block
     * The IBlockState's material must not be water
     *
     * @return An IBlockState that will replace the default ocean block
     */
    IBlockState getOceanBlock();
}

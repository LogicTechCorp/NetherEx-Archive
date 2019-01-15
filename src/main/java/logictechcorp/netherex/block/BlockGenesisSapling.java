/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
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

package logictechcorp.netherex.block;

import logictechcorp.libraryex.block.BlockModSapling;
import logictechcorp.libraryex.block.builder.BlockBuilder;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExBlocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockGenesisSapling extends BlockModSapling
{
    public BlockGenesisSapling()
    {
        super(NetherEx.getResource("genesis_sapling"), new BlockBuilder(Material.PLANTS, MapColor.GREEN).sound(SoundType.PLANT).tickRandomly());
    }

    @Override
    protected IBlockState getLog()
    {
        return NetherExBlocks.GENESIS_LOG.getDefaultState();
    }

    @Override
    protected IBlockState getLeaf()
    {
        return NetherExBlocks.GENESIS_LEAF.getDefaultState();
    }
}

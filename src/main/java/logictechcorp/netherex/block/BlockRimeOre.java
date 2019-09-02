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

import logictechcorp.libraryex.block.BlockModOre;
import logictechcorp.libraryex.block.HarvestLevel;
import logictechcorp.libraryex.block.HarvestTool;
import logictechcorp.libraryex.block.property.BlockProperties;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExItems;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockRimeOre extends BlockModOre
{
    public BlockRimeOre()
    {
        super(NetherEx.getResource("rime_ore"), new BlockProperties(Material.ROCK, MapColor.RED).harvestLevel(HarvestTool.PICKAXE, HarvestLevel.IRON).lightLevel(0.5F).hardness(3.0F).resistance(5.0F));
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return NetherExItems.RIME_CRYSTAL;
    }
}

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

package nex.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import nex.block.BlockNetherExSlab;

public class ItemBlockNetherExSlab extends ItemSlab
{
    private final BlockNetherExSlab slab;

    public ItemBlockNetherExSlab(Block block, BlockSlab singleSlab, BlockSlab doubleSlab)
    {
        super(block, singleSlab, doubleSlab);

        slab = (BlockNetherExSlab) block;

        setRegistryName(block.getRegistryName().toString());
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return slab.getUnlocalizedName(stack.getMetadata());
    }

    @Override
    public int getMetadata(int metadata)
    {
        return metadata;
    }
}

/*
 * NetherEx
 * Copyright (c) 2016-2018 by MineEx
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

package nex.block;

import com.google.common.base.CaseFormat;
import lex.block.BlockSlabLibEx;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import nex.NetherEx;
import nex.init.NetherExBlocks;

import java.util.Random;

public class BlockBasaltSlab extends BlockSlabLibEx
{
    public static final PropertyEnum<BlockBasalt.EnumType> TYPE = PropertyEnum.create("type", BlockBasalt.EnumType.class);

    public BlockBasaltSlab()
    {
        super(NetherEx.instance, "basalt_slab", Material.ROCK);
        this.setHardness(2.0F);
        this.setResistance(10F);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for(BlockBasalt.EnumType type : BlockBasalt.EnumType.values())
        {
            list.add(new ItemStack(this, 1, type.ordinal()));
        }
    }

    @Override
    public String getTranslationKey(int meta)
    {
        return super.getTranslationKey() + "." + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, BlockBasalt.EnumType.fromMeta(meta).getName());
    }

    @Override
    public boolean isDouble()
    {
        return false;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack)
    {
        return BlockBasalt.EnumType.fromMeta(stack.getMetadata() & 7);
    }

    @Override
    public IProperty<?> getVariantProperty()
    {
        return TYPE;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(NetherExBlocks.BASALT_SLAB);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState state = this.getDefaultState().withProperty(TYPE, BlockBasalt.EnumType.fromMeta(meta & 7));

        if(!this.isDouble())
        {
            state = state.withProperty(HALF, (meta & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
        }

        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = state.getValue(TYPE).ordinal();

        if(!this.isDouble() && state.getValue(HALF) == EnumBlockHalf.TOP)
        {
            meta |= 8;
        }

        return meta;
    }

    public static class Double extends BlockBasaltSlab
    {
        @Override
        public boolean isDouble()
        {
            return true;
        }
    }
}

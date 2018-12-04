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

package logictechcorp.netherex.block;

import logictechcorp.libraryex.block.BlockLibEx;
import logictechcorp.libraryex.client.model.item.ItemModelHandler;
import logictechcorp.netherex.NetherEx;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBasalt extends BlockLibEx
{
    public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);

    public BlockBasalt()
    {
        super(NetherEx.instance, "basalt", "pickaxe", 0, 1.5F, 10.0F, Material.ROCK);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for(EnumType type : EnumType.values())
        {
            list.add(new ItemStack(this, 1, type.ordinal()));
        }
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return this.getMetaFromState(state);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(TYPE, EnumType.fromMeta(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, TYPE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerModel()
    {
        for(BlockBasalt.EnumType type : BlockBasalt.EnumType.values())
        {
            ItemModelHandler.registerBlockModel(this, type.ordinal(), this.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }
    }

    public enum EnumType implements IStringSerializable
    {
        NORMAL,
        SMOOTH,
        BRICK,
        PILLAR;

        @Override
        public String getName()
        {
            return this.toString().toLowerCase();
        }

        public static EnumType fromMeta(int meta)
        {
            if(meta < 0 || meta >= values().length)
            {
                meta = 0;
            }

            return values()[meta];
        }
    }
}

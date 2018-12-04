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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockNetherrack extends BlockLibEx
{
    public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);

    public BlockNetherrack()
    {
        super(NetherEx.instance, "netherrack", "pickaxe", 0, 0.5F, 2.0F, Material.ROCK);
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
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
    {
        return side == EnumFacing.UP;
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(TYPE).ordinal();
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
        for(BlockNetherrack.EnumType type : BlockNetherrack.EnumType.values())
        {
            ItemModelHandler.registerBlockModel(this, type.ordinal(), String.format(NetherEx.MOD_ID + ":%s_" + this.getRegistryName().getPath(), type.getName()), "inventory");
        }
    }

    public enum EnumType implements IStringSerializable
    {
        FIERY,
        ICY,
        LIVELY,
        GLOOMY;

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

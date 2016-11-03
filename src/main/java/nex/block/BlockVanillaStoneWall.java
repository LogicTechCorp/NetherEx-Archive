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

package nex.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import nex.block.state.DualBlockStateContainer;

import java.util.List;

public class BlockVanillaStoneWall extends BlockNetherExWall
{
    public static final PropertyEnum<BlockVanillaStone.EnumTypeWith> TYPE = PropertyEnum.create("type", BlockVanillaStone.EnumTypeWith.class);

    public BlockVanillaStoneWall()
    {
        super("vanilla_stone_wall", Material.ROCK);

        ((DualBlockStateContainer) blockState).destroySuper();
        setDefaultState(blockState.getBaseState());

        setHardness(1.5F);
        setResistance(10.0F);
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for(BlockVanillaStone.EnumTypeWith type : BlockVanillaStone.EnumTypeWith.values())
        {
            list.add(new ItemStack(itemIn, 1, type.ordinal()));
        }
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(TYPE, BlockVanillaStone.EnumTypeWith.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new DualBlockStateContainer(super.createBlockState(), this, UP, NORTH, EAST, WEST, SOUTH, TYPE);
    }
}

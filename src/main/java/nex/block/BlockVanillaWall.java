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

import lex.block.BlockWallLibEx;
import lex.block.state.VariableBlockStateContainer;
import lex.client.model.item.ItemModelHandler;
import net.minecraft.block.BlockWall;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.NetherEx;

public class BlockVanillaWall extends BlockWallLibEx
{
    public static final PropertyEnum<BlockVanilla.EnumTypeWall> TYPE = PropertyEnum.create("type", BlockVanilla.EnumTypeWall.class);

    public BlockVanillaWall()
    {
        super(NetherEx.instance, "vanilla_wall", Blocks.STONE);
        ((VariableBlockStateContainer) this.blockState).destroyContainer();
        this.setDefaultState(this.blockState.getBaseState());
        this.setHardness(1.5F);
        this.setResistance(10.0F);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for(BlockVanilla.EnumTypeWall type : BlockVanilla.EnumTypeWall.values())
        {
            list.add(new ItemStack(this, 1, type.ordinal()));
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
        return this.getDefaultState().withProperty(TYPE, BlockVanilla.EnumTypeWall.fromMeta(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new VariableBlockStateContainer(super.createBlockState(), this, UP, NORTH, EAST, WEST, SOUTH, TYPE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerModel()
    {
        ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(BlockWall.VARIANT).build());

        for(BlockVanilla.EnumTypeWall type : BlockVanilla.EnumTypeWall.values())
        {
            ItemModelHandler.registerBlockModel(this, type.ordinal(), String.format(NetherEx.MOD_ID + ":%s_wall", type.getName()), "inventory");
        }
    }
}

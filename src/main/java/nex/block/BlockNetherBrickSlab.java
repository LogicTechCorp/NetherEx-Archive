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

package nex.block;

import com.google.common.base.CaseFormat;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import nex.init.NetherExBlocks;

import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class BlockNetherBrickSlab extends BlockNetherExSlab
{
    public static final PropertyEnum<BlockNetherrack.EnumType> TYPE = PropertyEnum.create("type", BlockNetherrack.EnumType.class);

    public BlockNetherBrickSlab(boolean isDoubleIn)
    {
        super("slab_nether_brick", Material.ROCK, isDoubleIn);

        setHardness(2.0F);
        setResistance(10F);
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for(BlockNetherrack.EnumType type : BlockNetherrack.EnumType.values())
        {
            list.add(new ItemStack(item, 1, type.ordinal()));
        }
    }

    @Override
    public String getUnlocalizedName(int meta)
    {
        return super.getUnlocalizedName() + "." + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, BlockNetherrack.EnumType.values()[meta].getName());
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(NetherExBlocks.SLAB_NETHER_BRICK);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(NetherExBlocks.SLAB_NETHER_BRICK, 1, damageDropped(state));
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack)
    {
        return BlockNetherrack.EnumType.values()[stack.getMetadata() & 7];
    }

    @Override
    public IProperty<?> getVariantProperty()
    {
        return TYPE;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState state = getDefaultState();
        state = state.withProperty(TYPE, BlockNetherrack.EnumType.values()[meta & 7]);

        if(!isDouble())
        {
            state = state.withProperty(HALF, (meta & 8) == 8 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
        }

        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = state.getValue(TYPE).ordinal();

        if(!isDouble() && state.getValue(HALF) == EnumBlockHalf.TOP)
        {
            meta |= 8;
        }

        return meta;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return !isDoubleStatic ? new BlockStateContainer(this, getVariantProperty(), HALF) : new BlockStateContainer(this, getVariantProperty());
    }
}

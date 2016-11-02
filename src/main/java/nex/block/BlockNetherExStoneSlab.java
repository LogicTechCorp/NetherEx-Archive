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

import com.google.common.base.CaseFormat;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import nex.init.NetherExBlocks;

import java.util.List;
import java.util.Random;

public class BlockNetherExStoneSlab extends BlockNetherExSlab
{
    public static final PropertyEnum<BlockNetherExStone.EnumType> TYPE = PropertyEnum.create("type", BlockNetherExStone.EnumType.class);

    public BlockNetherExStoneSlab(boolean isDoubleIn)
    {
        super("stone_slab", Material.ROCK, isDoubleIn);

        setSoundType(SoundType.STONE);
        setHardness(2.0F);
        setResistance(10F);
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list)
    {
        for(BlockNetherExStone.EnumType type : BlockNetherExStone.EnumType.values())
        {
            list.add(new ItemStack(item, 1, type.ordinal()));
        }
    }

    @Override
    public String getUnlocalizedName(int meta)
    {
        return super.getUnlocalizedName() + "." + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, BlockNetherExStone.EnumType.values()[meta].getName());
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(NetherExBlocks.STONE_SLAB);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(NetherExBlocks.STONE_SLAB, 1, getMetaFromState(state));
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack)
    {
        return BlockNetherExStone.EnumType.values()[stack.getMetadata() & 7];
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
        state = state.withProperty(TYPE, BlockNetherExStone.EnumType.values()[meta & 7]);

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

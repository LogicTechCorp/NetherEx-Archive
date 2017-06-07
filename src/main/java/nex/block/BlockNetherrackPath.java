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

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.init.NetherExBlocks;

import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class BlockNetherrackPath extends BlockNetherEx
{
    public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);

    private static final AxisAlignedBB PATH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

    public BlockNetherrackPath()
    {
        super("block_netherrack_path", Material.ROCK);

        setLightOpacity(255);
        setHardness(0.4F);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return PATH_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing facing)
    {
        switch(facing)
        {
            case UP:
                return true;
            case NORTH:
            case SOUTH:
            case WEST:
            case EAST:
                IBlockState testState = world.getBlockState(pos.offset(facing));
                Block block = testState.getBlock();
                return !testState.isOpaqueCube() && block != Blocks.FARMLAND && block != Blocks.GRASS_PATH && block != NetherExBlocks.BLOCK_NETHERRACK_PATH;
            default:
                return super.shouldSideBeRendered(state, world, pos, facing);
        }
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for(EnumType type : EnumType.values())
        {
            list.add(new ItemStack(item, 1, type.ordinal()));
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(getItemDropped(state, player.getRNG(), 0), 1, damageDropped(state));
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        if(world.getBlockState(pos.up()).getMaterial().isSolid())
        {
            if(state.getValue(TYPE) == EnumType.NORMAL)
            {
                world.setBlockState(pos, Blocks.NETHERRACK.getDefaultState());
            }
            else
            {
                world.setBlockState(pos, NetherExBlocks.BLOCK_NETHERRACK.getDefaultState().withProperty(TYPE, EnumType.fromMeta(state.getValue(TYPE).ordinal() - 1)));
            }
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if(world.getBlockState(pos.up()).getMaterial().isSolid())
        {
            if(state.getValue(TYPE) == EnumType.NORMAL)
            {
                world.setBlockState(pos, Blocks.NETHERRACK.getDefaultState());
            }
            else
            {
                world.setBlockState(pos, NetherExBlocks.BLOCK_NETHERRACK.getDefaultState().withProperty(TYPE, EnumType.fromMeta(state.getValue(TYPE).ordinal() - 1)));
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return state.getValue(TYPE) == EnumType.NORMAL ? Item.getItemFromBlock(Blocks.NETHERRACK) : Item.getItemFromBlock(NetherExBlocks.BLOCK_NETHERRACK);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(TYPE) == EnumType.NORMAL ? state.getValue(TYPE).ordinal() : state.getValue(TYPE).ordinal() - 1;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(TYPE, EnumType.fromMeta(meta));
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

    public enum EnumType implements IStringSerializable
    {
        NORMAL,
        FIERY,
        ICY,
        LIVELY,
        GLOOMY;

        @Override
        public String getName()
        {
            return toString().toLowerCase();
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

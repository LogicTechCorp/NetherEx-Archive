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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockBone extends BlockNetherEx
{
    private static final PropertyEnum<EnumAxis> AXIS = PropertyEnum.create("axis", EnumAxis.class);
    private static final PropertyEnum<EnumType> SIZE = PropertyEnum.create("size", EnumType.class);

    private static final AxisAlignedBB DU_AABB_MEDIUM = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
    private static final AxisAlignedBB DU_AABB_SMALL = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);
    private static final AxisAlignedBB NS_AABB_MEDIUM = new AxisAlignedBB(0.75D, 0.25D, 0.0D, 0.25D, 0.75D, 1.0D);
    private static final AxisAlignedBB NS_AABB_SMALL = new AxisAlignedBB(0.625D, 0.375D, 0.0D, 0.375D, 0.625D, 1.0D);
    private static final AxisAlignedBB WE_AABB_MEDIUM = new AxisAlignedBB(0.0D, 0.25D, 0.75D, 1.0D, 0.75D, 0.25D);
    private static final AxisAlignedBB WE_AABB_SMALL = new AxisAlignedBB(0.0D, 0.375D, 0.625D, 1.0D, 0.625D, 0.375D);

    public BlockBone()
    {
        super("block_bone", Material.ROCK);

        setHardness(2.0F);
        setResistance(5.0F);
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
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for(EnumType type : EnumType.values())
        {
            list.add(new ItemStack(this, 1, type.ordinal()));
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        switch(getMetaFromState(state))
        {
            default:
            case 4:
            case 8:
            case 12:
                return FULL_BLOCK_AABB;
            case 5:
                return WE_AABB_MEDIUM;
            case 9:
                return NS_AABB_MEDIUM;
            case 13:
                return DU_AABB_MEDIUM;
            case 6:
                return WE_AABB_SMALL;
            case 10:
                return NS_AABB_SMALL;
            case 14:
                return DU_AABB_SMALL;

        }
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getStateFromMeta(meta).withProperty(AXIS, EnumAxis.fromFacingAxis(facing.getAxis()));
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        switch(rot)
        {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:

                switch(state.getValue(AXIS))
                {
                    case X:
                        return state.withProperty(AXIS, EnumAxis.Z);
                    case Z:
                        return state.withProperty(AXIS, EnumAxis.X);
                    default:
                        return state;
                }

            default:
                return state;
        }
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return (state.getValue(SIZE)).ordinal();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState state = getDefaultState().withProperty(SIZE, EnumType.values()[(meta & 3) % 4]);

        switch(meta & 12)
        {
            case 0:
                state = state.withProperty(AXIS, EnumAxis.Y);
                break;
            case 4:
                state = state.withProperty(AXIS, EnumAxis.X);
                break;
            case 8:
                state = state.withProperty(AXIS, EnumAxis.Z);
                break;
            default:
                state = state.withProperty(AXIS, EnumAxis.Y);
        }

        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | (state.getValue(SIZE)).ordinal();

        switch(state.getValue(AXIS))
        {
            case X:
                i |= 4;
                break;
            case Z:
                i |= 8;
                break;
            case Y:
                i |= 12;
        }

        return i;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, AXIS, SIZE);
    }

    public enum EnumAxis implements IStringSerializable
    {
        X("x"),
        Y("y"),
        Z("z");

        private final String name;

        EnumAxis(String nameIn)
        {
            name = nameIn;
        }

        public String toString()
        {
            return name;
        }

        public static EnumAxis fromFacingAxis(EnumFacing.Axis axis)
        {
            switch(axis)
            {
                case X:
                    return X;
                case Y:
                    return Y;
                case Z:
                    return Z;
                default:
                    return Y;
            }
        }

        public String getName()
        {
            return name;
        }
    }

    public enum EnumType implements IStringSerializable
    {
        LARGE,
        MEDIUM,
        SMALL;

        @Override
        public String getName()
        {
            return toString().toLowerCase();
        }
    }
}

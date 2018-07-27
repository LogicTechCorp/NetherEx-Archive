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

import lex.block.BlockLibEx;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.NetherEx;

public class BlockBoneChunk extends BlockLibEx
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    private static final AxisAlignedBB D_AABB = new AxisAlignedBB(0.25D, 0.5D, 0.25D, 0.75D, 1.0D, 0.75D);
    private static final AxisAlignedBB U_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.5D, 0.75D);
    private static final AxisAlignedBB N_AABB = new AxisAlignedBB(0.75D, 0.25D, 0.5D, 0.25D, 0.75D, 1.0D);
    private static final AxisAlignedBB S_AABB = new AxisAlignedBB(0.75D, 0.25D, 0.0D, 0.25D, 0.75D, 0.5D);
    private static final AxisAlignedBB W_AABB = new AxisAlignedBB(0.5D, 0.25D, 0.75D, 1.0D, 0.75D, 0.25D);
    private static final AxisAlignedBB E_AABB = new AxisAlignedBB(0.0D, 0.25D, 0.75D, 0.5D, 0.75D, 0.25D);

    public BlockBoneChunk()
    {
        super(NetherEx.instance, "bone_chunk", Material.ROCK);
        setHardness(2.0F);
        setResistance(5.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        switch((state.getValue(FACING)))
        {
            case DOWN:
                return D_AABB;
            default:
            case UP:
                return U_AABB;
            case NORTH:
                return N_AABB;
            case SOUTH:
                return S_AABB;
            case WEST:
                return W_AABB;
            case EAST:
                return E_AABB;
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState state = world.getBlockState(pos.offset(facing.getOpposite()));

        if(state.getBlock() == Blocks.END_ROD)
        {
            EnumFacing enumfacing = state.getValue(FACING);

            if(enumfacing == facing)
            {
                return getDefaultState().withProperty(FACING, facing.getOpposite());
            }
        }

        return getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rotation)
    {
        return state.withProperty(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirror)
    {
        return state.withProperty(FACING, mirror.mirror(state.getValue(FACING)));
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }
}

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

import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.NetherEx;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;

public class BlockNetherExFenceGate extends Block
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyBool OPEN = PropertyBool.create("open");
    public static final PropertyBool POWERED = PropertyBool.create("powered");
    public static final PropertyBool IN_WALL = PropertyBool.create("in_wall");

    private static final AxisAlignedBB AABB_COLLIDE_ZAXIS = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D);
    private static final AxisAlignedBB AABB_COLLIDE_XAXIS = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D);
    private static final AxisAlignedBB AABB_COLLIDE_ZAXIS_INWALL = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 0.8125D, 0.625D);
    private static final AxisAlignedBB AABB_COLLIDE_XAXIS_INWALL = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 0.8125D, 1.0D);
    private static final AxisAlignedBB AABB_CLOSED_SELECTED_ZAXIS = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.5D, 0.625D);
    private static final AxisAlignedBB AABB_CLOSED_SELECTED_XAXIS = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.5D, 1.0D);

    public BlockNetherExFenceGate(String name, Material material)
    {
        super(material);

        String[] nameParts = name.split("_");
        String gateName = nameParts[0] + StringUtils.capitalize(nameParts[1]) + StringUtils.capitalize(nameParts[2]);
        String gateType = nameParts[3];

        for(int i = 4; i < nameParts.length; i++)
        {
            gateType += StringUtils.capitalize(nameParts[i]);
        }

        useNeighborBrightness = true;

        setCreativeTab(NetherEx.CREATIVE_TAB);
        setSoundType(SoundType.STONE);
        setRegistryName(NetherEx.MOD_ID + ":" + name);
        setUnlocalizedName(NetherEx.MOD_ID + ":" + gateName + "." + gateType);
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
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return true;
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos).getValue(OPEN);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = getActualState(state, source, pos);
        return state.getValue(IN_WALL) ? (state.getValue(FACING).getAxis() == EnumFacing.Axis.X ? AABB_COLLIDE_XAXIS_INWALL : AABB_COLLIDE_ZAXIS_INWALL) : (state.getValue(FACING).getAxis() == EnumFacing.Axis.X ? AABB_COLLIDE_XAXIS : AABB_COLLIDE_ZAXIS);
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return blockState.getValue(OPEN) ? NULL_AABB : (blockState.getValue(FACING).getAxis() == EnumFacing.Axis.Z ? AABB_CLOSED_SELECTED_ZAXIS : AABB_CLOSED_SELECTED_XAXIS);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
    {
        if(!worldIn.isRemote)
        {
            boolean flag = worldIn.isBlockPowered(pos);

            if(flag || blockIn.getDefaultState().canProvidePower())
            {
                if(flag && !state.getValue(OPEN) && !state.getValue(POWERED))
                {
                    worldIn.setBlockState(pos, state.withProperty(OPEN, true).withProperty(POWERED, true), 2);
                    worldIn.playEvent(null, 1008, pos, 0);
                }
                else if(!flag && state.getValue(OPEN) && state.getValue(POWERED))
                {
                    worldIn.setBlockState(pos, state.withProperty(OPEN, false).withProperty(POWERED, false), 2);
                    worldIn.playEvent(null, 1014, pos, 0);
                }
                else if(flag != state.getValue(POWERED))
                {
                    worldIn.setBlockState(pos, state.withProperty(POWERED, flag), 2);
                }
            }
        }
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.down()).getMaterial().isSolid() && super.canPlaceBlockAt(worldIn, pos);
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing()).withProperty(OPEN, false).withProperty(POWERED, false).withProperty(IN_WALL, false);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(state.getValue(OPEN))
        {
            state = state.withProperty(OPEN, false);
            worldIn.setBlockState(pos, state, 10);
        }
        else
        {
            EnumFacing facing = EnumFacing.fromAngle((double) playerIn.rotationYaw);

            if(state.getValue(FACING) == facing.getOpposite())
            {
                state = state.withProperty(FACING, facing);
            }

            state = state.withProperty(OPEN, true);
            worldIn.setBlockState(pos, state, 10);
        }

        worldIn.playEvent(playerIn, state.getValue(OPEN) ? 1008 : 1014, pos, 0);
        return true;
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        EnumFacing.Axis facing = state.getValue(FACING).getAxis();

        Block northBlock = worldIn.getBlockState(pos.north()).getBlock();
        Block eastBlock = worldIn.getBlockState(pos.east()).getBlock();
        Block southBlock = worldIn.getBlockState(pos.south()).getBlock();
        Block westBlock = worldIn.getBlockState(pos.west()).getBlock();

        if(facing == EnumFacing.Axis.Z && ((westBlock instanceof BlockWall || westBlock instanceof BlockNetherExWall) || (eastBlock instanceof BlockWall || eastBlock instanceof BlockNetherExWall)) || facing == EnumFacing.Axis.X && ((northBlock instanceof BlockWall || northBlock instanceof BlockNetherExWall) || (southBlock instanceof BlockWall || southBlock instanceof BlockNetherExWall)))
        {
            return state.withProperty(IN_WALL, true);
        }

        return state.withProperty(IN_WALL, false);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(OPEN, (meta & 4) != 0).withProperty(POWERED, (meta & 8) != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | (state.getValue(FACING)).getHorizontalIndex();

        if(state.getValue(POWERED))
        {
            i |= 8;
        }

        if(state.getValue(OPEN))
        {
            i |= 4;
        }

        return i;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, OPEN, POWERED, IN_WALL);
    }
}

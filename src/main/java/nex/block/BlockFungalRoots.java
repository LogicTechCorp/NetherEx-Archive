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
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFungalRoots extends BlockNetherEx
{
    private static final PropertyEnum<EnumBlockHalf> HALF = PropertyEnum.create("half", EnumBlockHalf.class);

    public BlockFungalRoots()
    {
        super("fungal_roots", Material.PLANTS, SoundType.PLANT, "half", "top", "bottom");
        setHardness(0.0F);
    }

    @Override
    public boolean shouldDisplayVariant(int meta)
    {
        return meta == 1;
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
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World world, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block)
    {
        if(!canBlockStay(world, pos, state))
        {
            boolean flag = state.getValue(HALF) == EnumBlockHalf.BOTTOM;

            BlockPos blockPos = flag ? pos : pos.down();
            BlockPos blockPos1 = flag ? pos.up() : pos;
            Block block1 = (flag ? this : world.getBlockState(blockPos).getBlock());
            Block block2 = (flag ? world.getBlockState(blockPos1).getBlock() : this);

            if(!flag)
            {
                dropBlockAsItem(world, pos, state, 0);
            }

            if(block1 == this)
            {
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 2);
            }

            if(block2 == this)
            {
                world.setBlockState(blockPos1, Blocks.AIR.getDefaultState(), 3);
            }
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        Block blockUp = world.getBlockState(pos.up()).getBlock();
        return super.canPlaceBlockAt(world, pos) && blockUp != this && blockUp.isBlockSolid(world, pos.up(), null) && world.isAirBlock(pos.down());
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        world.setBlockState(pos.down(), getDefaultState().withProperty(HALF, EnumBlockHalf.BOTTOM), 2);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        if(state.getValue(HALF) == EnumBlockHalf.BOTTOM)
        {
            if(world.getBlockState(pos.up()).getBlock() == this)
            {
                if(player.capabilities.isCreativeMode)
                {
                    world.setBlockToAir(pos.up());
                }
                else
                {
                    world.destroyBlock(pos.up(), true);
                }
            }
        }
        else if(world.getBlockState(pos.down()).getBlock() == this)
        {
            world.setBlockState(pos.down(), Blocks.AIR.getDefaultState(), 2);
        }
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, HALF);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(HALF, EnumBlockHalf.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(HALF).ordinal();
    }

    private boolean canBlockStay(World world, BlockPos pos, IBlockState state)
    {
        if(state.getValue(HALF) == EnumBlockHalf.BOTTOM)
        {
            return world.getBlockState(pos.up()).getBlock() == this;
        }
        else
        {
            IBlockState stateDown = world.getBlockState(pos.down());
            return stateDown.getBlock() == this;
        }
    }

    public void generate(World world, BlockPos pos)
    {
        world.setBlockState(pos, getDefaultState().withProperty(HALF, EnumBlockHalf.TOP), 2);
        world.setBlockState(pos.down(), getDefaultState().withProperty(HALF, EnumBlockHalf.BOTTOM), 2);
    }

    public enum EnumBlockHalf implements IStringSerializable
    {
        TOP,
        BOTTOM;

        public String getName()
        {
            return toString().toLowerCase();
        }
    }
}
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
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.NetherEx;
import nex.init.NetherExBlocks;
import nex.init.NetherExItems;

import java.util.List;
import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class BlockEnokiMushroomStem extends BlockLibEx
{
    private static final PropertyBool NORTH = PropertyBool.create("north");
    private static final PropertyBool EAST = PropertyBool.create("east");
    private static final PropertyBool SOUTH = PropertyBool.create("south");
    private static final PropertyBool WEST = PropertyBool.create("west");
    private static final PropertyBool UP = PropertyBool.create("up");
    private static final PropertyBool DOWN = PropertyBool.create("down");

    public BlockEnokiMushroomStem()
    {
        super(NetherEx.instance, "enoki_mushroom_stem", Material.PLANTS);
        setSoundType(SoundType.WOOD);
        setHardness(0.4F);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if(!canSurviveAt(world, pos))
        {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        Block block = world.getBlockState(pos.down()).getBlock();
        Block block1 = world.getBlockState(pos.up()).getBlock();
        Block block2 = world.getBlockState(pos.north()).getBlock();
        Block block3 = world.getBlockState(pos.east()).getBlock();
        Block block4 = world.getBlockState(pos.south()).getBlock();
        Block block5 = world.getBlockState(pos.west()).getBlock();

        return state.withProperty(DOWN, block == this || block == NetherExBlocks.ENOKI_MUSHROOM_CAP)
                .withProperty(UP, block1 == this || block1 == Blocks.NETHERRACK || block1 == NetherExBlocks.NETHERRACK || block1 == NetherExBlocks.ENOKI_MUSHROOM_CAP)
                .withProperty(NORTH, block2 == this || block2 == NetherExBlocks.ENOKI_MUSHROOM_CAP)
                .withProperty(EAST, block3 == this || block3 == NetherExBlocks.ENOKI_MUSHROOM_CAP)
                .withProperty(SOUTH, block4 == this || block4 == NetherExBlocks.ENOKI_MUSHROOM_CAP)
                .withProperty(WEST, block5 == this || block5 == NetherExBlocks.ENOKI_MUSHROOM_CAP);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = state.getActualState(source, pos);

        float f1 = state.getValue(WEST) ? 0.0F : 0.1875F;
        float f2 = state.getValue(DOWN) ? 0.0F : 0.1875F;
        float f3 = state.getValue(NORTH) ? 0.0F : 0.1875F;
        float f4 = state.getValue(EAST) ? 1.0F : 0.8125F;
        float f5 = state.getValue(UP) ? 1.0F : 0.8125F;
        float f6 = state.getValue(SOUTH) ? 1.0F : 0.8125F;

        return new AxisAlignedBB((double) f1, (double) f2, (double) f3, (double) f4, (double) f5, (double) f6);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entity, boolean bool)
    {
        state = state.getActualState(world, pos);

        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.8125D, 0.8125D));

        if(state.getValue(WEST))
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.0D, 0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.8125D));
        }

        if(state.getValue(EAST))
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.8125D, 0.1875D, 0.1875D, 1.0D, 0.8125D, 0.8125D));
        }

        if(state.getValue(UP))
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.8125D, 0.1875D, 0.8125D, 1.0D, 0.8125D));
        }

        if(state.getValue(DOWN))
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.1875D, 0.8125D));
        }

        if(state.getValue(NORTH))
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.1875D, 0.0D, 0.8125D, 0.8125D, 0.1875D));
        }

        if(state.getValue(SOUTH))
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.1875D, 0.8125D, 0.8125D, 0.8125D, 1.0D));
        }
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        return super.canPlaceBlockAt(world, pos) && canSurviveAt(world, pos);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if(!canSurviveAt(world, pos))
        {
            world.scheduleUpdate(pos, this, 1);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        if(rand.nextInt(4) == 0)
        {
            return NetherExItems.ENOKI_MUSHROOM;
        }
        else
        {
            return Items.AIR;
        }
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    @Override
    public boolean isPassable(IBlockAccess world, BlockPos pos)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        Block block = blockAccess.getBlockState(pos.offset(side)).getBlock();
        return block != this && (side != EnumFacing.UP);
    }

    private boolean canSurviveAt(World wordIn, BlockPos pos)
    {
        boolean flag = wordIn.isAirBlock(pos.up());
        boolean flag1 = wordIn.isAirBlock(pos.down());

        for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
        {
            BlockPos blockpos = pos.offset(enumfacing);
            Block block = wordIn.getBlockState(blockpos).getBlock();

            if(block == this)
            {
                if(!flag && !flag1)
                {
                    return false;
                }

                IBlockState state1 = wordIn.getBlockState(blockpos.up());

                if(state1.getBlock() == this || state1.getBlock() == Blocks.NETHERRACK || state1 == NetherExBlocks.NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.LIVELY))
                {
                    return true;
                }
            }
        }

        IBlockState state2 = wordIn.getBlockState(pos.up());
        return state2.getBlock() == this || state2.getBlock() == Blocks.NETHERRACK || state2 == NetherExBlocks.NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.LIVELY);
    }
}

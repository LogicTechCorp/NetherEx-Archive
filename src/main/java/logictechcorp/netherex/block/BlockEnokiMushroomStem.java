/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
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

package logictechcorp.netherex.block;

import logictechcorp.libraryex.block.BlockMod;
import logictechcorp.libraryex.block.property.BlockProperties;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExBlocks;
import logictechcorp.netherex.init.NetherExItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
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

import java.util.List;
import java.util.Random;

public class BlockEnokiMushroomStem extends BlockMod
{
    private static final PropertyBool NORTH = PropertyBool.create("north");
    private static final PropertyBool EAST = PropertyBool.create("east");
    private static final PropertyBool SOUTH = PropertyBool.create("south");
    private static final PropertyBool WEST = PropertyBool.create("west");
    private static final PropertyBool UP = PropertyBool.create("up");
    private static final PropertyBool DOWN = PropertyBool.create("down");

    public BlockEnokiMushroomStem()
    {
        super(NetherEx.getResource("enoki_mushroom_stem"), new BlockProperties(Material.PLANTS, MapColor.SNOW).sound(SoundType.WOOD).hardness(0.4F));
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

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
    {
        if(!this.canSurviveAt(world, pos))
        {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        Block blockDown = world.getBlockState(pos.down()).getBlock();
        Block blockUp = world.getBlockState(pos.up()).getBlock();
        Block blockNorth = world.getBlockState(pos.north()).getBlock();
        Block blockEast = world.getBlockState(pos.east()).getBlock();
        Block blockSouth = world.getBlockState(pos.south()).getBlock();
        Block blockWest = world.getBlockState(pos.west()).getBlock();

        return state.withProperty(DOWN, blockDown == this || blockDown == NetherExBlocks.ENOKI_MUSHROOM_CAP)
                .withProperty(UP, blockUp == this || blockUp == NetherExBlocks.LIVELY_NETHERRACK || blockUp == NetherExBlocks.ENOKI_MUSHROOM_CAP)
                .withProperty(NORTH, blockNorth == this || blockNorth == NetherExBlocks.ENOKI_MUSHROOM_CAP)
                .withProperty(EAST, blockEast == this || blockEast == NetherExBlocks.ENOKI_MUSHROOM_CAP)
                .withProperty(SOUTH, blockSouth == this || blockSouth == NetherExBlocks.ENOKI_MUSHROOM_CAP)
                .withProperty(WEST, blockWest == this || blockWest == NetherExBlocks.ENOKI_MUSHROOM_CAP);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        state = state.getActualState(source, pos);

        float minX = state.getValue(WEST) ? 0.0F : 0.1875F;
        float minY = state.getValue(DOWN) ? 0.0F : 0.1875F;
        float minZ = state.getValue(NORTH) ? 0.0F : 0.1875F;
        float maxX = state.getValue(EAST) ? 1.0F : 0.8125F;
        float maxY = state.getValue(UP) ? 1.0F : 0.8125F;
        float maxZ = state.getValue(SOUTH) ? 1.0F : 0.8125F;

        return new AxisAlignedBB((double) minX, (double) minY, (double) minZ, (double) maxX, (double) maxY, (double) maxZ);
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
        return super.canPlaceBlockAt(world, pos) && this.canSurviveAt(world, pos);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if(!this.canSurviveAt(world, pos))
        {
            world.scheduleUpdate(pos, this, 1);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random random, int fortune)
    {
        if(random.nextInt(4) == 0)
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

    private boolean canSurviveAt(World world, BlockPos pos)
    {
        boolean airOnTop = world.isAirBlock(pos.up());
        boolean airOnBottom = world.isAirBlock(pos.down());

        for(EnumFacing facing : EnumFacing.Plane.HORIZONTAL)
        {
            BlockPos offsetPos = pos.offset(facing);
            Block block = world.getBlockState(offsetPos).getBlock();

            if(block == this)
            {
                if(!airOnTop && !airOnBottom)
                {
                    return false;
                }

                IBlockState stateUp = world.getBlockState(offsetPos.up());

                if(stateUp.getBlock() == this || stateUp.getBlock() == Blocks.NETHERRACK || stateUp == NetherExBlocks.LIVELY_NETHERRACK)
                {
                    return true;
                }
            }
        }

        IBlockState stateUp = world.getBlockState(pos.up());
        return stateUp.getBlock() == this || stateUp.getBlock() == Blocks.NETHERRACK || stateUp == NetherExBlocks.LIVELY_NETHERRACK;
    }
}

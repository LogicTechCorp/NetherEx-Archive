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

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SixWayBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class EnokiStemBlock extends SixWayBlock
{
    public EnokiStemBlock(Properties properties)
    {
        super(0.3125F, properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(UP, false).with(DOWN, false));
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        if(!state.isValidPosition(world, pos))
        {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader world, BlockPos pos, PathType type)
    {
        return false;
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos)
    {
        if(!state.isValidPosition(world, currentPos))
        {
            world.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
            return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
        }
        else
        {
            Block block = facingState.getBlock();
            boolean flag = block == this || block == NetherExBlocks.ENOKI_MUSHROOM_CAP.get() || facing == Direction.UP && block == NetherExBlocks.LIVELY_NETHERRACK.get();
            return state.with(FACING_TO_PROPERTY_MAP.get(facing), flag);
        }
    }

    public BlockState makeConnections(IBlockReader world, BlockPos pos)
    {
        Block downBlock = world.getBlockState(pos.down()).getBlock();
        Block upBlock = world.getBlockState(pos.up()).getBlock();
        Block northBlock = world.getBlockState(pos.north()).getBlock();
        Block eastBlock = world.getBlockState(pos.east()).getBlock();
        Block southBlock = world.getBlockState(pos.south()).getBlock();
        Block westBlock = world.getBlockState(pos.west()).getBlock();
        return this.getDefaultState().with(DOWN, downBlock == this || downBlock == NetherExBlocks.ENOKI_MUSHROOM_CAP.get()).with(UP, upBlock == this || upBlock == NetherExBlocks.ENOKI_MUSHROOM_CAP.get() || upBlock == NetherExBlocks.LIVELY_NETHERRACK.get()).with(NORTH, northBlock == this || northBlock == NetherExBlocks.ENOKI_MUSHROOM_CAP.get()).with(EAST, eastBlock == this || eastBlock == NetherExBlocks.ENOKI_MUSHROOM_CAP.get()).with(SOUTH, southBlock == this || southBlock == NetherExBlocks.ENOKI_MUSHROOM_CAP.get()).with(WEST, westBlock == this || westBlock == NetherExBlocks.ENOKI_MUSHROOM_CAP.get());
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos)
    {
        BlockState upState = world.getBlockState(pos.up());
        boolean flag = !world.getBlockState(pos.down()).isAir() && !upState.isAir();

        for(Direction direction : Direction.Plane.HORIZONTAL)
        {
            BlockPos offsetPos = pos.offset(direction);
            Block block = world.getBlockState(offsetPos).getBlock();

            if(block == this)
            {
                if(flag)
                {
                    return false;
                }

                Block upBlock = world.getBlockState(offsetPos.up()).getBlock();

                if(upBlock == this || upBlock == NetherExBlocks.LIVELY_NETHERRACK.get())
                {
                    return true;
                }
            }
        }

        Block upBlock = upState.getBlock();
        return upBlock == this || upBlock == NetherExBlocks.LIVELY_NETHERRACK.get();
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.makeConnections(context.getWorld(), context.getPos());
    }
}

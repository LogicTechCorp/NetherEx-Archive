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
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;

import java.util.Random;

public class EnokiCapBlock extends Block
{
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_5;

    public EnokiCapBlock(Block.Properties builder)
    {
        super(builder);
        this.setDefaultState(this.stateContainer.getBaseState().with(AGE, 0));
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        if(!state.isValidPosition(world, pos))
        {
            world.destroyBlock(pos, true);
        }
        else
        {
            BlockPos downPos = pos.down();

            if(world.isAirBlock(downPos) && downPos.getY() < world.getDimension().getHeight())
            {
                int age = state.get(AGE);

                if(age < 5 && ForgeHooks.onCropsGrowPre(world, downPos, state, true))
                {
                    boolean validPosition = false;
                    boolean foundNetherrack = false;
                    BlockState upState = world.getBlockState(pos.up());
                    Block upBlock = upState.getBlock();

                    if(upBlock == NetherExBlocks.LIVELY_NETHERRACK.get())
                    {
                        validPosition = true;
                    }
                    else if(upBlock == NetherExBlocks.ENOKI_MUSHROOM_STEM.get())
                    {
                        int height = 1;

                        for(int k = 0; k < 4; k++)
                        {
                            Block block1 = world.getBlockState(pos.up(height + 1)).getBlock();

                            if(block1 != NetherExBlocks.ENOKI_MUSHROOM_STEM.get())
                            {
                                if(block1 == NetherExBlocks.LIVELY_NETHERRACK.get())
                                {
                                    foundNetherrack = true;
                                }

                                break;
                            }

                            height++;
                        }

                        if(height < 2 || height <= random.nextInt(foundNetherrack ? 5 : 4))
                        {
                            validPosition = true;
                        }
                    }
                    else if(upState.isAir(world, pos.down()))
                    {
                        validPosition = true;
                    }

                    if(validPosition && areAllNeighborsEmpty(world, downPos, null) && world.isAirBlock(pos.up(2)))
                    {
                        world.setBlockState(pos, ((EnokiStemBlock) NetherExBlocks.ENOKI_MUSHROOM_STEM.get()).makeConnections(world, pos), 2);
                        this.placeCap(world, downPos, age);
                    }
                    else if(age < 4)
                    {
                        int randomHeight = random.nextInt(4);

                        if(foundNetherrack)
                        {
                            randomHeight++;
                        }

                        boolean placedCap = false;

                        for(int i1 = 0; i1 < randomHeight; ++i1)
                        {
                            Direction direction = Direction.Plane.HORIZONTAL.random(random);
                            BlockPos offsetPos = pos.offset(direction);

                            if(world.isAirBlock(offsetPos) && world.isAirBlock(offsetPos.down()) && areAllNeighborsEmpty(world, offsetPos, direction.getOpposite()))
                            {
                                this.placeCap(world, offsetPos, age + 1);
                                placedCap = true;
                            }
                        }

                        if(placedCap)
                        {
                            world.setBlockState(pos, ((EnokiStemBlock) NetherExBlocks.ENOKI_MUSHROOM_STEM.get()).makeConnections(world, pos), 2);
                        }
                        else
                        {
                            this.placeDeadCap(world, pos);
                        }
                    }
                    else
                    {
                        this.placeDeadCap(world, pos);
                    }

                    ForgeHooks.onCropsGrowPost(world, pos, state);
                }
            }
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(AGE);
    }

    @Override
    public void onProjectileCollision(World world, BlockState state, BlockRayTraceResult hit, Entity projectile)
    {
        BlockPos hitPos = hit.getPos();
        spawnAsEntity(world, hitPos, new ItemStack(this));
        world.destroyBlock(hitPos, true);
    }

    private void placeCap(World world, BlockPos pos, int age)
    {
        world.setBlockState(pos, this.getDefaultState().with(AGE, age), 2);
        world.playEvent(1033, pos, 0);
    }

    private void placeDeadCap(World world, BlockPos pos)
    {
        world.setBlockState(pos, this.getDefaultState().with(AGE, 5), 2);
        world.playEvent(1034, pos, 0);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos)
    {
        if(facing != Direction.DOWN && !state.isValidPosition(world, currentPos))
        {
            world.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
        }

        return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos)
    {
        BlockState upState = world.getBlockState(pos.up());
        Block upBlock = upState.getBlock();

        if(upBlock != NetherExBlocks.ENOKI_MUSHROOM_STEM.get() && upBlock != NetherExBlocks.LIVELY_NETHERRACK.get())
        {
            if(!upState.isAir(world, pos.up()))
            {
                return false;
            }
            else
            {
                boolean foundStem = false;

                for(Direction direction : Direction.Plane.HORIZONTAL)
                {
                    BlockState offsetState = world.getBlockState(pos.offset(direction));

                    if(offsetState.getBlock() == NetherExBlocks.ENOKI_MUSHROOM_STEM.get())
                    {
                        if(foundStem)
                        {
                            return false;
                        }

                        foundStem = true;
                    }
                    else if(!offsetState.isAir(world, pos.offset(direction)))
                    {
                        return false;
                    }
                }

                return foundStem;
            }
        }
        else
        {
            return true;
        }
    }

    public static void generatePlant(IWorld world, BlockPos pos, Random random, int maximumHeight)
    {
        world.setBlockState(pos, ((EnokiStemBlock) NetherExBlocks.ENOKI_MUSHROOM_STEM.get()).makeConnections(world, pos), 2);
        growTreeRecursive(world, pos, random, pos, maximumHeight, 0);
    }

    private static void growTreeRecursive(IWorld world, BlockPos pos, Random random, BlockPos otherPos, int maximumHeight, int minimumHeight)
    {
        int randomHeight = random.nextInt(4) + 1;

        if(minimumHeight == 0)
        {
            randomHeight++;
        }

        for(int j = 0; j < randomHeight; j++)
        {
            BlockPos downPos = pos.down(j + 1);

            if(!areAllNeighborsEmpty(world, downPos, null))
            {
                return;
            }

            world.setBlockState(downPos, ((EnokiStemBlock) NetherExBlocks.ENOKI_MUSHROOM_STEM.get()).makeConnections(world, downPos), 2);
            world.setBlockState(downPos.up(), ((EnokiStemBlock) NetherExBlocks.ENOKI_MUSHROOM_STEM.get()).makeConnections(world, downPos.up()), 2);
        }

        boolean continueGrowing = false;

        if(minimumHeight < 4)
        {
            int additionalHeight = random.nextInt(4);

            if(minimumHeight == 0)
            {
                additionalHeight++;
            }

            for(int additional = 0; additional < additionalHeight; additional++)
            {
                Direction direction = Direction.Plane.HORIZONTAL.random(random);
                BlockPos offsetPos = pos.down(randomHeight).offset(direction);

                if(Math.abs(offsetPos.getX() - otherPos.getX()) < maximumHeight && Math.abs(offsetPos.getZ() - otherPos.getZ()) < maximumHeight && world.isAirBlock(offsetPos) && world.isAirBlock(offsetPos.up()) && areAllNeighborsEmpty(world, offsetPos, direction.getOpposite()))
                {
                    continueGrowing = true;
                    world.setBlockState(offsetPos, ((EnokiStemBlock) NetherExBlocks.ENOKI_MUSHROOM_STEM.get()).makeConnections(world, offsetPos), 2);
                    world.setBlockState(offsetPos.offset(direction.getOpposite()), ((EnokiStemBlock) NetherExBlocks.ENOKI_MUSHROOM_STEM.get()).makeConnections(world, offsetPos.offset(direction.getOpposite())), 2);
                    growTreeRecursive(world, offsetPos, random, otherPos, maximumHeight, minimumHeight + 1);
                }
            }
        }

        if(!continueGrowing)
        {
            world.setBlockState(pos.down(randomHeight), NetherExBlocks.ENOKI_MUSHROOM_CAP.get().getDefaultState().with(AGE, 5), 2);
        }
    }

    private static boolean areAllNeighborsEmpty(IWorldReader worldIn, BlockPos pos, Direction excludingSide)
    {
        for(Direction direction : Direction.Plane.HORIZONTAL)
        {
            if(direction != excludingSide && !worldIn.isAirBlock(pos.offset(direction)))
            {
                return false;
            }
        }

        return true;
    }
}

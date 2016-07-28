package nex.world.biome.feature;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nex.api.biome.feature.BiomeFeature;

import java.util.Random;

public class BiomeFeatureBigMushroom extends BiomeFeature
{
    private final Block mushroomType;
    private final IBlockState target;

    public BiomeFeatureBigMushroom(int genAttemptsIn, int minYIn, int maxYIn, Block mushroomTypeIn, IBlockState targetIn)
    {
        super(genAttemptsIn, minYIn, maxYIn);

        mushroomType = mushroomTypeIn;
        target = targetIn;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        Block block = mushroomType;

        if(block == null)
        {
            block = rand.nextBoolean() ? Blocks.BROWN_MUSHROOM_BLOCK : Blocks.RED_MUSHROOM_BLOCK;
        }

        int i = rand.nextInt(3) + 4;

        if(rand.nextInt(12) == 0)
        {
            i *= 2;
        }

        boolean flag = true;

        if(pos.getY() >= 1 && pos.getY() + i + 1 < 256)
        {
            for(int j = pos.getY(); j <= pos.getY() + 1 + i; ++j)
            {
                int k = 3;

                if(j <= pos.getY() + 3)
                {
                    k = 0;
                }

                BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

                for(int l = pos.getX() - k; l <= pos.getX() + k && flag; ++l)
                {
                    for(int i1 = pos.getZ() - k; i1 <= pos.getZ() + k && flag; ++i1)
                    {
                        if(j >= 0 && j < 256)
                        {
                            IBlockState state = world.getBlockState(mutablePos.setPos(l, j, i1));

                            if(!state.getBlock().isAir(state, world, mutablePos) && !state.getBlock().isLeaves(state, world, mutablePos))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if(!flag)
            {
                return false;
            }
            else
            {
                if(world.getBlockState(pos.down()) != target)
                {
                    return false;
                }
                else
                {
                    int k2 = pos.getY() + i;

                    if(block == Blocks.RED_MUSHROOM_BLOCK)
                    {
                        k2 = pos.getY() + i - 3;
                    }

                    for(int l2 = k2; l2 <= pos.getY() + i; ++l2)
                    {
                        int j3 = 1;

                        if(l2 < pos.getY() + i)
                        {
                            ++j3;
                        }

                        if(block == Blocks.BROWN_MUSHROOM_BLOCK)
                        {
                            j3 = 3;
                        }

                        int k3 = pos.getX() - j3;
                        int l3 = pos.getX() + j3;
                        int j1 = pos.getZ() - j3;
                        int k1 = pos.getZ() + j3;

                        for(int l1 = k3; l1 <= l3; ++l1)
                        {
                            for(int i2 = j1; i2 <= k1; ++i2)
                            {
                                int j2 = 5;

                                if(l1 == k3)
                                {
                                    --j2;
                                }
                                else if(l1 == l3)
                                {
                                    ++j2;
                                }

                                if(i2 == j1)
                                {
                                    j2 -= 3;
                                }
                                else if(i2 == k1)
                                {
                                    j2 += 3;
                                }

                                BlockHugeMushroom.EnumType type = BlockHugeMushroom.EnumType.byMetadata(j2);

                                if(block == Blocks.BROWN_MUSHROOM_BLOCK || l2 < pos.getY() + i)
                                {
                                    if((l1 == k3 || l1 == l3) && (i2 == j1 || i2 == k1))
                                    {
                                        continue;
                                    }

                                    if(l1 == pos.getX() - (j3 - 1) && i2 == j1)
                                    {
                                        type = BlockHugeMushroom.EnumType.NORTH_WEST;
                                    }

                                    if(l1 == k3 && i2 == pos.getZ() - (j3 - 1))
                                    {
                                        type = BlockHugeMushroom.EnumType.NORTH_WEST;
                                    }

                                    if(l1 == pos.getX() + (j3 - 1) && i2 == j1)
                                    {
                                        type = BlockHugeMushroom.EnumType.NORTH_EAST;
                                    }

                                    if(l1 == l3 && i2 == pos.getZ() - (j3 - 1))
                                    {
                                        type = BlockHugeMushroom.EnumType.NORTH_EAST;
                                    }

                                    if(l1 == pos.getX() - (j3 - 1) && i2 == k1)
                                    {
                                        type = BlockHugeMushroom.EnumType.SOUTH_WEST;
                                    }

                                    if(l1 == k3 && i2 == pos.getZ() + (j3 - 1))
                                    {
                                        type = BlockHugeMushroom.EnumType.SOUTH_WEST;
                                    }

                                    if(l1 == pos.getX() + (j3 - 1) && i2 == k1)
                                    {
                                        type = BlockHugeMushroom.EnumType.SOUTH_EAST;
                                    }

                                    if(l1 == l3 && i2 == pos.getZ() + (j3 - 1))
                                    {
                                        type = BlockHugeMushroom.EnumType.SOUTH_EAST;
                                    }
                                }

                                if(type == BlockHugeMushroom.EnumType.CENTER && l2 < pos.getY() + i)
                                {
                                    type = BlockHugeMushroom.EnumType.ALL_INSIDE;
                                }

                                if(pos.getY() >= pos.getY() + i - 1 || type != BlockHugeMushroom.EnumType.ALL_INSIDE)
                                {
                                    BlockPos blockPos = new BlockPos(l1, l2, i2);
                                    IBlockState state = world.getBlockState(blockPos);

                                    if(state.getBlock().canBeReplacedByLeaves(state, world, blockPos))
                                    {
                                        setBlockAndNotifyAdequately(world, blockPos, block.getDefaultState().withProperty(BlockHugeMushroom.VARIANT, type));
                                    }
                                }
                            }
                        }
                    }

                    for(int i3 = 0; i3 < i; ++i3)
                    {
                        IBlockState iblockstate = world.getBlockState(pos.up(i3));

                        if(iblockstate.getBlock().canBeReplacedByLeaves(iblockstate, world, pos.up(i3)))
                        {
                            setBlockAndNotifyAdequately(world, pos.up(i3), block.getDefaultState().withProperty(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.STEM));
                        }
                    }

                    return true;
                }
            }
        }
        else
        {
            return false;
        }
    }
}
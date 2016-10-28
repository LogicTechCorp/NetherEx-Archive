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

package nex.world.gen.feature;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import nex.block.BlockLeaves;
import nex.block.BlockLog;
import nex.init.NetherExBlocks;

import java.util.List;
import java.util.Random;

public class WorldGenBigTree extends WorldGenAbstractTree
{
    private Random rand;
    private World world;
    private BlockPos basePos = BlockPos.ORIGIN;
    int heightLimit;
    int height;
    double heightAttenuation = 0.618D;
    double branchSlope = 0.381D;
    double scaleWidth = 1.0D;
    double leafDensity = 1.0D;
    int trunkSize = 1;
    int heightLimitLimit = 12;
    int leafDistanceLimit = 4;
    List<FoliageCoordinates> foliageCoords;

    public WorldGenBigTree(boolean notify)
    {
        super(notify);
    }

    void generateLeafNodeList()
    {
        height = (int) ((double) heightLimit * heightAttenuation);

        if(height >= heightLimit)
        {
            height = heightLimit - 1;
        }

        int i = (int) (1.382D + Math.pow(leafDensity * (double) heightLimit / 13.0D, 2.0D));

        if(i < 1)
        {
            i = 1;
        }

        int j = basePos.getY() + height;
        int k = heightLimit - leafDistanceLimit;
        foliageCoords = Lists.newArrayList();
        foliageCoords.add(new WorldGenBigTree.FoliageCoordinates(basePos.up(k), j));

        for(; k >= 0; --k)
        {
            float f = layerSize(k);

            if(f >= 0.0F)
            {
                for(int l = 0; l < i; ++l)
                {
                    double d0 = scaleWidth * (double) f * ((double) rand.nextFloat() + 0.328D);
                    double d1 = (double) (rand.nextFloat() * 2.0F) * Math.PI;
                    double d2 = d0 * Math.sin(d1) + 0.5D;
                    double d3 = d0 * Math.cos(d1) + 0.5D;
                    BlockPos pos = basePos.add(d2, (double) (k - 1), d3);
                    BlockPos pos1 = pos.up(leafDistanceLimit);

                    if(checkBlockLine(pos, pos1) == -1)
                    {
                        int i1 = basePos.getX() - pos.getX();
                        int j1 = basePos.getZ() - pos.getZ();
                        double d4 = (double) pos.getY() - Math.sqrt((double) (i1 * i1 + j1 * j1)) * branchSlope;
                        int k1 = d4 > (double) j ? j : (int) d4;
                        BlockPos pos2 = new BlockPos(basePos.getX(), k1, basePos.getZ());

                        if(checkBlockLine(pos2, pos) == -1)
                        {
                            foliageCoords.add(new WorldGenBigTree.FoliageCoordinates(pos, pos2.getY()));
                        }
                    }
                }
            }
        }
    }

    void crossSection(BlockPos pos, float p_181631_2_, IBlockState p_181631_3_)
    {
        int i = (int) ((double) p_181631_2_ + 0.618D);

        for(int j = -i; j <= i; ++j)
        {
            for(int k = -i; k <= i; ++k)
            {
                if(Math.pow((double) Math.abs(j) + 0.5D, 2.0D) + Math.pow((double) Math.abs(k) + 0.5D, 2.0D) <= (double) (p_181631_2_ * p_181631_2_))
                {
                    BlockPos newPos = pos.add(j, 0, k);
                    IBlockState state = world.getBlockState(newPos);

                    if(state.getBlock().isAir(state, world, newPos) || state.getBlock().isLeaves(state, world, newPos))
                    {
                        setBlockAndNotifyAdequately(world, newPos, p_181631_3_);
                    }
                }
            }
        }
    }

    float layerSize(int y)
    {
        if((float) y < (float) heightLimit * 0.3F)
        {
            return -1.0F;
        }
        else
        {
            float f = (float) heightLimit / 2.0F;
            float f1 = f - (float) y;
            float f2 = MathHelper.sqrt_float(f * f - f1 * f1);

            if(f1 == 0.0F)
            {
                f2 = f;
            }
            else if(Math.abs(f1) >= f)
            {
                return 0.0F;
            }

            return f2 * 0.5F;
        }
    }

    float leafSize(int y)
    {
        return y >= 0 && y < leafDistanceLimit ? (y != 0 && y != leafDistanceLimit - 1 ? 3.0F : 2.0F) : -1.0F;
    }

    void generateLeafNode(BlockPos pos)
    {
        for(int i = 0; i < leafDistanceLimit; ++i)
        {
            crossSection(pos.up(i), leafSize(i), NetherExBlocks.LEAVES.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false));
        }
    }

    void limb(BlockPos p_175937_1_, BlockPos p_175937_2_, Block p_175937_3_)
    {
        BlockPos pos = p_175937_2_.add(-p_175937_1_.getX(), -p_175937_1_.getY(), -p_175937_1_.getZ());
        int i = getGreatestDistance(pos);
        float f = (float) pos.getX() / (float) i;
        float f1 = (float) pos.getY() / (float) i;
        float f2 = (float) pos.getZ() / (float) i;

        for(int j = 0; j <= i; ++j)
        {
            BlockPos pos1 = p_175937_1_.add((double) (0.5F + (float) j * f), (double) (0.5F + (float) j * f1), (double) (0.5F + (float) j * f2));
            BlockLog.EnumAxis axis = getLogAxis(p_175937_1_, pos1);
            setBlockAndNotifyAdequately(world, pos1, p_175937_3_.getDefaultState().withProperty(BlockLog.AXIS, axis));
        }
    }

    private int getGreatestDistance(BlockPos posIn)
    {
        int i = MathHelper.abs_int(posIn.getX());
        int j = MathHelper.abs_int(posIn.getY());
        int k = MathHelper.abs_int(posIn.getZ());
        return k > i && k > j ? k : (j > i ? j : i);
    }

    private BlockLog.EnumAxis getLogAxis(BlockPos p_175938_1_, BlockPos p_175938_2_)
    {
        BlockLog.EnumAxis axis = BlockLog.EnumAxis.Y;
        int i = Math.abs(p_175938_2_.getX() - p_175938_1_.getX());
        int j = Math.abs(p_175938_2_.getZ() - p_175938_1_.getZ());
        int k = Math.max(i, j);

        if(k > 0)
        {
            if(i == k)
            {
                axis = BlockLog.EnumAxis.X;
            }
            else if(j == k)
            {
                axis = BlockLog.EnumAxis.Z;
            }
        }

        return axis;
    }

    void generateLeaves()
    {
        for(WorldGenBigTree.FoliageCoordinates coordinates : foliageCoords)
        {
            generateLeafNode(coordinates);
        }
    }

    boolean leafNodeNeedsBase(int p_76493_1_)
    {
        return (double) p_76493_1_ >= (double) heightLimit * 0.2D;
    }

    void generateTrunk()
    {
        BlockPos pos = basePos;
        BlockPos pos1 = basePos.up(height);
        Block block = NetherExBlocks.LOG;
        limb(pos, pos1, block);

        if(trunkSize == 2)
        {
            limb(pos.east(), pos1.east(), block);
            limb(pos.east().south(), pos1.east().south(), block);
            limb(pos.south(), pos1.south(), block);
        }
    }

    void generateLeafNodeBases()
    {
        for(WorldGenBigTree.FoliageCoordinates coordinates : foliageCoords)
        {
            int i = coordinates.getBranchBase();
            BlockPos pos = new BlockPos(basePos.getX(), i, basePos.getZ());

            if(!pos.equals(coordinates) && leafNodeNeedsBase(i - basePos.getY()))
            {
                limb(pos, coordinates, NetherExBlocks.LOG);
            }
        }
    }

    int checkBlockLine(BlockPos posOne, BlockPos posTwo)
    {
        BlockPos pos = posTwo.add(-posOne.getX(), -posOne.getY(), -posOne.getZ());
        int i = getGreatestDistance(pos);
        float f = (float) pos.getX() / (float) i;
        float f1 = (float) pos.getY() / (float) i;
        float f2 = (float) pos.getZ() / (float) i;

        if(i == 0)
        {
            return -1;
        }
        else
        {
            for(int j = 0; j <= i; ++j)
            {
                BlockPos pos1 = posOne.add((double) (0.5F + (float) j * f), (double) (0.5F + (float) j * f1), (double) (0.5F + (float) j * f2));

                if(!isReplaceable(world, pos1))
                {
                    return j;
                }
            }

            return -1;
        }
    }

    public void setDecorationDefaults()
    {
        leafDistanceLimit = 5;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        world = worldIn;
        basePos = position;
        rand = new Random(rand.nextLong());

        if(heightLimit == 0)
        {
            heightLimit = 5 + rand.nextInt(heightLimitLimit);
        }

        if(!validTreeLocation())
        {
            world = null;
            return false;
        }
        else
        {
            generateLeafNodeList();
            generateLeaves();
            generateTrunk();
            generateLeafNodeBases();
            world = null;
            return true;
        }
    }

    private boolean validTreeLocation()
    {
        BlockPos down = basePos.down();
        net.minecraft.block.state.IBlockState state = world.getBlockState(down);
        boolean isSoil = state.getBlock().canSustainPlant(state, world, down, net.minecraft.util.EnumFacing.UP, NetherExBlocks.SAPLING);

        if(!isSoil)
        {
            return false;
        }
        else
        {
            int i = checkBlockLine(basePos, basePos.up(heightLimit - 1));

            if(i == -1)
            {
                return true;
            }
            else if(i < 6)
            {
                return false;
            }
            else
            {
                heightLimit = i;
                return true;
            }
        }
    }

    static class FoliageCoordinates extends BlockPos
    {
        private final int branchBase;

        public FoliageCoordinates(BlockPos pos, int p_i45635_2_)
        {
            super(pos.getX(), pos.getY(), pos.getZ());
            branchBase = p_i45635_2_;
        }

        public int getBranchBase()
        {
            return branchBase;
        }
    }
}
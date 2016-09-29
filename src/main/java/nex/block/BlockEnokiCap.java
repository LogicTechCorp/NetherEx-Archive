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
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.init.ModBlocks;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockEnokiCap extends BlockNetherEx
{
    private static final PropertyInteger AGE = PropertyInteger.create("age", 0, 5);

    public BlockEnokiCap()
    {
        super("enoki_cap", Material.PLANTS, SoundType.WOOD, "age");

        setHardness(0.4F);
        setTickRandomly(true);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if(!canSurvive(world, pos))
        {
            world.destroyBlock(pos, true);
        }
        else
        {
            BlockPos blockpos = pos.down();

            if(world.isAirBlock(blockpos) && blockpos.getY() > 0)
            {
                int i = state.getValue(AGE);

                if(i < 5 && rand.nextInt(1) == 0)
                {
                    boolean flag = false;
                    boolean flag1 = false;
                    Block block = world.getBlockState(pos.up()).getBlock();

                    if(block == Blocks.NETHERRACK || block == ModBlocks.NETHERRACK)
                    {
                        flag = true;
                    }
                    else if(block == ModBlocks.ENOKI_STEM)
                    {
                        int j = 1;

                        for(int k = 0; k < 4; ++k)
                        {
                            Block block1 = world.getBlockState(pos.up(j + 1)).getBlock();

                            if(block1 != ModBlocks.ENOKI_STEM)
                            {
                                if(block1 == Blocks.NETHERRACK || block1 == ModBlocks.NETHERRACK)
                                {
                                    flag1 = true;
                                }

                                break;
                            }

                            ++j;
                        }

                        int i1 = 4;

                        if(flag1)
                        {
                            ++i1;
                        }

                        if(j < 2 || rand.nextInt(i1) >= j)
                        {
                            flag = true;
                        }
                    }
                    else if(block == Blocks.AIR)
                    {
                        flag = true;
                    }

                    if(flag && areAllNeighborsEmpty(world, blockpos, null) && world.isAirBlock(pos.down(2)))
                    {
                        world.setBlockState(pos, ModBlocks.ENOKI_STEM.getDefaultState(), 2);
                        placeGrownCap(world, blockpos, i);
                    }
                    else if(i < 4)
                    {
                        int l = rand.nextInt(4);
                        boolean flag2 = false;

                        if(flag1)
                        {
                            ++l;
                        }

                        for(int j1 = 0; j1 < l; ++j1)
                        {
                            EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
                            BlockPos blockpos1 = pos.offset(enumfacing);

                            if(world.isAirBlock(blockpos1) && world.isAirBlock(blockpos1.up()) && areAllNeighborsEmpty(world, blockpos1, enumfacing.getOpposite()))
                            {
                                placeGrownCap(world, blockpos1, i + 1);
                                flag2 = true;
                            }
                        }

                        if(flag2)
                        {
                            world.setBlockState(pos, ModBlocks.ENOKI_STEM.getDefaultState(), 2);
                        }
                        else
                        {
                            placeDeadCap(world, pos);
                        }
                    }
                    else if(i == 4)
                    {
                        placeDeadCap(world, pos);
                    }
                }
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return null;
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
        return super.canPlaceBlockAt(world, pos) && canSurvive(world, pos);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn)
    {
        if(!canSurvive(world, pos))
        {
            world.scheduleUpdate(pos, this, 1);
        }
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack)
    {
        super.harvestBlock(world, player, pos, state, te, stack);
        spawnAsEntity(world, pos, new ItemStack(Item.getItemFromBlock(this)));
    }

    @Override
    protected ItemStack createStackedBlock(IBlockState state)
    {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, AGE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(AGE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(AGE);
    }

    private boolean canSurvive(World world, BlockPos pos)
    {
        Block block = world.getBlockState(pos.up()).getBlock();

        if(block != ModBlocks.ENOKI_STEM && (block != Blocks.NETHERRACK || block != ModBlocks.NETHERRACK))
        {
            if(block == Blocks.AIR)
            {
                int i = 0;

                for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
                {
                    Block block1 = world.getBlockState(pos.offset(enumfacing)).getBlock();

                    if(block1 == ModBlocks.ENOKI_STEM)
                    {
                        ++i;
                    }
                    else if(block1 != Blocks.AIR)
                    {
                        return false;
                    }
                }

                return i == 1;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    private static boolean areAllNeighborsEmpty(World world, BlockPos pos, EnumFacing face)
    {
        for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
        {
            if(enumfacing != face && !world.isAirBlock(pos.offset(enumfacing)))
            {
                return false;
            }
        }

        return true;
    }

    public static void generatePlant(World world, BlockPos pos, Random rand, int x)
    {
        world.setBlockState(pos.down(), ModBlocks.ENOKI_STEM.getDefaultState(), 2);
        growTreeRecursive(world, pos.down(), rand, pos, x, 0);
    }

    private static void growTreeRecursive(World world, BlockPos pos, Random rand, BlockPos pos1, int x, int z)
    {
        int i = rand.nextInt(4) + 1;

        if(z == 0)
        {
            ++i;
        }

        for(int j = 0; j < i; ++j)
        {
            BlockPos blockpos = pos.down(j + 1);

            if(!areAllNeighborsEmpty(world, blockpos, null))
            {
                return;
            }

            world.setBlockState(blockpos, ModBlocks.ENOKI_STEM.getDefaultState(), 2);
        }

        boolean flag = false;

        if(z < 4)
        {
            int l = rand.nextInt(4);

            if(z == 0)
            {
                ++l;
            }

            for(int k = 0; k < l; ++k)
            {
                EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
                BlockPos blockpos1 = pos.down(i).offset(enumfacing);

                if(Math.abs(blockpos1.getX() - pos1.getX()) < x && Math.abs(blockpos1.getZ() - pos1.getZ()) < x && world.isAirBlock(blockpos1) && world.isAirBlock(blockpos1.up()) && areAllNeighborsEmpty(world, blockpos1, enumfacing.getOpposite()))
                {
                    flag = true;
                    world.setBlockState(blockpos1, ModBlocks.ENOKI_STEM.getDefaultState(), 2);
                    growTreeRecursive(world, blockpos1, rand, pos1, x, z + 1);
                }
            }
        }

        if(!flag)
        {
            world.setBlockState(pos.down(i), ModBlocks.ENOKI_CAP.getDefaultState().withProperty(AGE, 5), 2);
        }
    }

    private void placeGrownCap(World world, BlockPos pos, int age)
    {
        world.setBlockState(pos, getDefaultState().withProperty(AGE, age), 2);
        world.playEvent(1033, pos, 0);
    }

    private void placeDeadCap(World world, BlockPos pos)
    {
        world.setBlockState(pos, getDefaultState().withProperty(AGE, 5), 2);
        world.playEvent(1034, pos, 0);
    }
}

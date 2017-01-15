/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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
import nex.init.NetherExBlocks;

import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class BlockEnokiCap extends BlockNetherEx
{
    private static final PropertyInteger AGE = PropertyInteger.create("age", 0, 5);

    public BlockEnokiCap()
    {
        super("plant_enoki_cap", Material.PLANTS);

        setSoundType(SoundType.WOOD);
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

                    if(block == Blocks.NETHERRACK || block == NetherExBlocks.BLOCK_NETHERRACK)
                    {
                        flag = true;
                    }
                    else if(block == NetherExBlocks.PLANT_ENOKI_STEM)
                    {
                        int j = 1;

                        for(int k = 0; k < 4; ++k)
                        {
                            Block block1 = world.getBlockState(pos.up(j + 1)).getBlock();

                            if(block1 != NetherExBlocks.PLANT_ENOKI_STEM)
                            {
                                if(block1 == Blocks.NETHERRACK || block1 == NetherExBlocks.BLOCK_NETHERRACK)
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
                        world.setBlockState(pos, NetherExBlocks.PLANT_ENOKI_STEM.getDefaultState(), 2);
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
                            world.setBlockState(pos, NetherExBlocks.PLANT_ENOKI_STEM.getDefaultState(), 2);
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
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if(!canSurvive(world, pos))
        {
            world.scheduleUpdate(pos, this, 1);
        }
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
    {
        super.harvestBlock(world, player, pos, state, te, stack);
        spawnAsEntity(world, pos, new ItemStack(Item.getItemFromBlock(this)));
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

    public boolean canSurvive(World world, BlockPos pos)
    {
        IBlockState stateUp = world.getBlockState(pos.up());
        return stateUp.getBlock() == NetherExBlocks.PLANT_ENOKI_STEM || stateUp == NetherExBlocks.BLOCK_NETHERRACK.getStateFromMeta(2);
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
        world.setBlockState(pos.down(), NetherExBlocks.PLANT_ENOKI_STEM.getDefaultState(), 2);
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

            world.setBlockState(blockpos, NetherExBlocks.PLANT_ENOKI_STEM.getDefaultState(), 2);
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
                BlockPos blockPos = pos.down(i).offset(enumfacing);

                if(Math.abs(blockPos.getX() - pos1.getX()) < x && Math.abs(blockPos.getZ() - pos1.getZ()) < x && world.isAirBlock(blockPos) && world.isAirBlock(blockPos.up()) && areAllNeighborsEmpty(world, blockPos, enumfacing.getOpposite()))
                {
                    flag = true;
                    world.setBlockState(blockPos, NetherExBlocks.PLANT_ENOKI_STEM.getDefaultState(), 2);
                    growTreeRecursive(world, blockPos, rand, pos1, x, z + 1);
                }
            }
        }

        if(!flag)
        {
            world.setBlockState(pos.down(i), NetherExBlocks.PLANT_ENOKI_CAP.getDefaultState().withProperty(AGE, 5), 2);
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

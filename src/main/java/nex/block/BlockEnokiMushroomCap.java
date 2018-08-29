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
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.NetherEx;
import nex.init.NetherExBlocks;

import java.util.Random;

public class BlockEnokiMushroomCap extends BlockLibEx
{
    private static final PropertyInteger AGE = PropertyInteger.create("age", 0, 5);

    public BlockEnokiMushroomCap()
    {
        super(NetherEx.instance, "enoki_mushroom_cap", Material.PLANTS);
        this.setSoundType(SoundType.WOOD);
        this.setHardness(0.4F);
        this.setTickRandomly(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if(!this.canSurvive(world, pos))
        {
            world.destroyBlock(pos, true);
        }
        else
        {
            BlockPos blockPos = pos.down();

            if(world.isAirBlock(blockPos) && blockPos.getY() > 0)
            {
                int age = state.getValue(AGE);

                if(age < 5 && rand.nextInt(1) == 0)
                {
                    boolean canGrow = false;
                    boolean nearNetherrack = false;
                    Block block = world.getBlockState(pos.up()).getBlock();

                    if(block == Blocks.NETHERRACK || block == NetherExBlocks.NETHERRACK)
                    {
                        canGrow = true;
                    }
                    else if(block == NetherExBlocks.ENOKI_MUSHROOM_STEM)
                    {
                        int growthChance = 1;

                        for(int i = 0; i < 4; i++)
                        {
                            Block checkBlock = world.getBlockState(pos.up(growthChance + 1)).getBlock();

                            if(checkBlock != NetherExBlocks.ENOKI_MUSHROOM_STEM)
                            {
                                if(checkBlock == Blocks.NETHERRACK || checkBlock == NetherExBlocks.NETHERRACK)
                                {
                                    nearNetherrack = true;
                                }

                                break;
                            }

                            growthChance++;
                        }

                        int randomGrowthChance = 4;

                        if(nearNetherrack)
                        {
                            randomGrowthChance++;
                        }

                        if(growthChance < 2 || rand.nextInt(randomGrowthChance) >= growthChance)
                        {
                            canGrow = true;
                        }
                    }
                    else if(block == Blocks.AIR)
                    {
                        canGrow = true;
                    }

                    if(canGrow && areAllNeighborsEmpty(world, blockPos, null) && world.isAirBlock(pos.down(2)))
                    {
                        world.setBlockState(pos, NetherExBlocks.ENOKI_MUSHROOM_STEM.getDefaultState(), 2);
                        this.placeGrownCap(world, blockPos, age);
                    }
                    else if(age < 4)
                    {
                        int growths = rand.nextInt(4);
                        boolean grew = false;

                        if(nearNetherrack)
                        {
                            growths++;
                        }

                        for(int i = 0; i < growths; i++)
                        {
                            EnumFacing facing = EnumFacing.Plane.HORIZONTAL.random(rand);
                            BlockPos offsetPos = pos.offset(facing);

                            if(world.isAirBlock(offsetPos) && world.isAirBlock(offsetPos.up()) && areAllNeighborsEmpty(world, offsetPos, facing.getOpposite()))
                            {
                                this.placeGrownCap(world, offsetPos, age + 1);
                                grew = true;
                            }
                        }

                        if(grew)
                        {
                            world.setBlockState(pos, NetherExBlocks.ENOKI_MUSHROOM_STEM.getDefaultState(), 2);
                        }
                        else
                        {
                            this.placeDeadCap(world, pos);
                        }
                    }
                    else if(age == 4)
                    {
                        this.placeDeadCap(world, pos);
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
        return super.canPlaceBlockAt(world, pos) && this.canSurvive(world, pos);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if(!this.canSurvive(world, pos))
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

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Items.AIR;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, AGE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(AGE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(AGE);
    }

    public boolean canSurvive(World world, BlockPos pos)
    {
        IBlockState stateUp = world.getBlockState(pos.up());
        Block blockUp = stateUp.getBlock();

        if(blockUp != NetherExBlocks.ENOKI_MUSHROOM_STEM && stateUp != NetherExBlocks.NETHERRACK.getDefaultState().withProperty(BlockNetherrack.TYPE, BlockNetherrack.EnumType.LIVELY))
        {
            if(stateUp.getMaterial() == Material.AIR)
            {
                int blocks = 0;

                for(EnumFacing facing : EnumFacing.Plane.HORIZONTAL)
                {
                    IBlockState stateSide = world.getBlockState(pos.offset(facing));
                    Block blockSide = stateSide.getBlock();

                    if(blockSide == NetherExBlocks.ENOKI_MUSHROOM_STEM)
                    {
                        blocks++;
                    }
                    else if(stateSide.getMaterial() != Material.AIR)
                    {
                        return false;
                    }
                }

                return blocks == 1;
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

    private static boolean areAllNeighborsEmpty(World world, BlockPos pos, EnumFacing facing)
    {
        for(EnumFacing enumFacing : EnumFacing.Plane.HORIZONTAL)
        {
            if(enumFacing != facing && !world.isAirBlock(pos.offset(enumFacing)))
            {
                return false;
            }
        }

        return true;
    }

    public static void generatePlant(World world, BlockPos pos, Random rand, int x)
    {
        BlockPos newPos = !world.isAirBlock(pos) ? pos.down() : pos;
        world.setBlockState(newPos, NetherExBlocks.ENOKI_MUSHROOM_STEM.getDefaultState(), 2);
        growTreeRecursive(world, newPos, rand, newPos, x, 0);
    }

    private static void growTreeRecursive(World world, BlockPos pos, Random rand, BlockPos pos1, int x, int z)
    {
        int height = rand.nextInt(4) + 1;

        if(z == 0)
        {
            height++;
        }

        for(int j = 0; j < height; j++)
        {
            BlockPos blockPos = pos.down(j + 1);

            if(!areAllNeighborsEmpty(world, blockPos, null))
            {
                return;
            }

            world.setBlockState(blockPos, NetherExBlocks.ENOKI_MUSHROOM_STEM.getDefaultState(), 2);
        }

        boolean grew = false;

        if(z < 4)
        {
            int growthChances = rand.nextInt(4);

            if(z == 0)
            {
                growthChances++;
            }

            for(int i = 0; i < growthChances; i++)
            {
                EnumFacing facing = EnumFacing.Plane.HORIZONTAL.random(rand);
                BlockPos blockPos = pos.down(height).offset(facing);

                if(Math.abs(blockPos.getX() - pos1.getX()) < x && Math.abs(blockPos.getZ() - pos1.getZ()) < x && world.isAirBlock(blockPos) && world.isAirBlock(blockPos.up()) && areAllNeighborsEmpty(world, blockPos, facing.getOpposite()))
                {
                    grew = true;
                    world.setBlockState(blockPos, NetherExBlocks.ENOKI_MUSHROOM_STEM.getDefaultState(), 2);
                    growTreeRecursive(world, blockPos, rand, pos1, x, z + 1);
                }
            }
        }

        if(!grew)
        {
            world.setBlockState(pos.down(height), NetherExBlocks.ENOKI_MUSHROOM_CAP.getDefaultState().withProperty(AGE, 5), 2);
        }
    }

    private void placeGrownCap(World world, BlockPos pos, int age)
    {
        world.setBlockState(pos, this.getDefaultState().withProperty(AGE, age), 2);
        world.playEvent(1033, pos, 0);
    }

    private void placeDeadCap(World world, BlockPos pos)
    {
        world.setBlockState(pos, this.getDefaultState().withProperty(AGE, 5), 2);
        world.playEvent(1034, pos, 0);
    }
}

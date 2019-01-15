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
import logictechcorp.libraryex.block.builder.BlockBuilder;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExBlocks;
import net.minecraft.block.*;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class BlockGenesisGrass extends BlockMod implements IGrowable
{
    public static final PropertyBool SNOWY = PropertyBool.create("snowy");

    public BlockGenesisGrass()
    {
        super(NetherEx.getResource("genesis_grass"), new BlockBuilder(Material.GRASS, MapColor.GREEN).sound(SoundType.PLANT).hardness(0.6F).tickRandomly().creativeTab(NetherEx.instance.getCreativeTab()));
        this.setDefaultState(this.blockState.getBaseState().withProperty(SNOWY, false));
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if(!world.isRemote)
        {
            if(!world.isAreaLoaded(pos, 3))
            {
                return;
            }

            if(world.getLightFromNeighbors(pos.up()) < 4 && world.getBlockState(pos.up()).getLightOpacity(world, pos.up()) > 2)
            {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState());
            }
            else
            {
                if(world.getLightFromNeighbors(pos.up()) >= 9)
                {
                    for(int i = 0; i < 4; i++)
                    {
                        BlockPos newPos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

                        if(newPos.getY() >= 0 && newPos.getY() < 256 && !world.isBlockLoaded(newPos))
                        {
                            return;
                        }

                        IBlockState newState = world.getBlockState(newPos);
                        IBlockState newStateUp = world.getBlockState(newPos.up());

                        if(newState.getBlock() == Blocks.DIRT && newState.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT && world.getLightFromNeighbors(newPos.up()) >= 4 && newStateUp.getLightOpacity(world, pos.up()) <= 2)
                        {
                            world.setBlockState(newPos, this.getDefaultState());
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable)
    {
        switch(plantable.getPlantType(world, pos.offset(direction)))
        {
            case Plains:
                return true;
            case Beach:
                return (world.getBlockState(pos.east()).getMaterial() == Material.WATER ||
                        world.getBlockState(pos.west()).getMaterial() == Material.WATER ||
                        world.getBlockState(pos.north()).getMaterial() == Material.WATER ||
                        world.getBlockState(pos.south()).getMaterial() == Material.WATER);
        }

        return super.canSustainPlant(state, world, pos, direction, plantable);
    }

    @Override
    public void onPlantGrow(IBlockState state, World world, BlockPos pos, BlockPos source)
    {
        world.setBlockState(pos, Blocks.DIRT.getDefaultState(), 2);
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient)
    {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state)
    {
        return true;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state)
    {
        BlockPos blockPosUp = pos.up();

        for(int i = 0; i < 128; i++)
        {
            BlockPos blockPosUpHolder = blockPosUp;
            int j = 0;

            while(true)
            {
                if(j >= i / 16)
                {
                    if(world.isAirBlock(blockPosUpHolder))
                    {
                        if(rand.nextInt(8) == 0)
                        {
                            world.setBlockState(blockPosUpHolder, NetherExBlocks.CYAN_ROSE.getDefaultState());
                        }
                        else if(rand.nextInt(4) == 0)
                        {
                            world.getBiome(blockPosUpHolder).plantFlower(world, rand, blockPosUpHolder);
                        }
                        else
                        {
                            IBlockState tallgrassState = Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS);

                            if(Blocks.TALLGRASS.canBlockStay(world, blockPosUpHolder, tallgrassState))
                            {
                                world.setBlockState(blockPosUpHolder, tallgrassState, 3);
                            }
                        }
                    }

                    break;
                }

                blockPosUpHolder = blockPosUpHolder.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);

                if(world.getBlockState(blockPosUpHolder.down()).getBlock() != this || world.getBlockState(blockPosUpHolder).isNormalCube())
                {
                    break;
                }

                j++;
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Blocks.DIRT.getItemDropped(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), rand, fortune);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        Block block = worldIn.getBlockState(pos.up()).getBlock();
        return state.withProperty(SNOWY, block == Blocks.SNOW || block == Blocks.SNOW_LAYER);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, SNOWY);
    }

}

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

import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import nex.init.NetherExBlocks;

import java.util.List;
import java.util.Random;

public class BlockOvergrownNetherrack extends BlockNetherEx implements IGrowable
{
    public static final PropertyEnum<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);

    public BlockOvergrownNetherrack()
    {
        super("netherrack_overgrown", Material.ROCK);

        setSoundType(SoundType.STONE);
        setHardness(0.4F);
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for(EnumType type : EnumType.values())
        {
            list.add(new ItemStack(itemIn, 1, type.ordinal()));
        }
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side)
    {
        return side == EnumFacing.UP;
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable)
    {
        return true;
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(TYPE, EnumType.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, TYPE);
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return true;
    }

    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        pos = pos.up();

        for(int i = 0; i < 128; ++i)
        {
            BlockPos newPos = pos;
            int j = 0;

            while(true)
            {
                if(j >= i / 16)
                {
                    if(worldIn.isAirBlock(newPos))
                    {
                        if(rand.nextInt(8) == 0)
                        {
                            worldIn.getBiome(newPos).plantFlower(worldIn, rand, newPos);
                        }
                        else
                        {
                            IBlockState blockState = NetherExBlocks.TALL_GRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.CORRUPTED);

                            if(NetherExBlocks.TALL_GRASS.canBlockStay(worldIn, newPos, blockState))
                            {
                                worldIn.setBlockState(newPos, blockState, 3);
                            }
                        }
                    }

                    break;
                }

                newPos = newPos.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);

                if(worldIn.getBlockState(newPos.down()).getBlock() != NetherExBlocks.OVERGROWN_NETHERRACK || worldIn.getBlockState(newPos).isNormalCube())
                {
                    break;
                }

                ++j;
            }
        }
    }

    public enum EnumType implements IStringSerializable
    {
        CORRUPTED;

        @Override
        public String getName()
        {
            return toString().toLowerCase();
        }
    }
}

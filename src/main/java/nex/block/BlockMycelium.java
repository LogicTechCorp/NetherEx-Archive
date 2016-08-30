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
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.init.ModBlocks;

import java.util.Random;

public class BlockMycelium extends BlockNetherEx
{
    public BlockMycelium()
    {
        super("mycelium", true, Material.ROCK, SoundType.STONE, "");

        setHardness(0.4F);
        setTickRandomly(true);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if(!world.isRemote)
        {
            for(int i = 0; i < 1; i++)
            {
                BlockPos newPos = pos.add(rand.nextInt(3) - 1, rand.nextInt(3) - 1, rand.nextInt(3) - 1);
                Block block = world.getBlockState(newPos).getBlock();

                if((block == Blocks.NETHERRACK || block == ModBlocks.NETHERRACK) && world.isAirBlock(newPos.up()))
                {
                    world.setBlockState(newPos, getDefaultState());
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random rand)
    {
        if(rand.nextInt(10) == 0)
        {
            worldIn.spawnParticle(EnumParticleTypes.TOWN_AURA, (double) ((float) pos.getX() + rand.nextFloat()), (double) ((float) pos.getY() + 1.1F), (double) ((float) pos.getZ() + rand.nextFloat()), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable)
    {
        return true;
    }
}

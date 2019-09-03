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

package logictechcorp.netherex.world.generation.feature;

import com.mojang.datafixers.Dynamic;
import logictechcorp.netherex.data.NetherExTags;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.HellLavaConfig;
import net.minecraft.world.gen.feature.NetherSpringFeature;

import java.util.Random;
import java.util.function.Function;

public class NetherSpringOverride extends NetherSpringFeature
{
    public NetherSpringOverride(Function<Dynamic<?>, ? extends HellLavaConfig> configFactory)
    {
        super(configFactory);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos pos, HellLavaConfig config)
    {
        if(!world.getBlockState(pos.up()).isIn(NetherExTags.Blocks.NETHERRACK))
        {
            return false;
        }
        else if(!world.getBlockState(pos).isAir(world, pos) && !world.getBlockState(pos).isIn(NetherExTags.Blocks.NETHERRACK))
        {
            return false;
        }
        else
        {
            int surroundingNetherrack = 0;

            if(world.getBlockState(pos.west()).isIn(NetherExTags.Blocks.NETHERRACK))
            {
                surroundingNetherrack++;
            }

            if(world.getBlockState(pos.east()).isIn(NetherExTags.Blocks.NETHERRACK))
            {
                surroundingNetherrack++;
            }

            if(world.getBlockState(pos.north()).isIn(NetherExTags.Blocks.NETHERRACK))
            {
                surroundingNetherrack++;
            }

            if(world.getBlockState(pos.south()).isIn(NetherExTags.Blocks.NETHERRACK))
            {
                surroundingNetherrack++;
            }

            if(world.getBlockState(pos.down()).isIn(NetherExTags.Blocks.NETHERRACK))
            {
                surroundingNetherrack++;
            }

            int surroundingAir = 0;

            if(world.isAirBlock(pos.west()))
            {
                surroundingAir++;
            }

            if(world.isAirBlock(pos.east()))
            {
                surroundingAir++;
            }

            if(world.isAirBlock(pos.north()))
            {
                surroundingAir++;
            }

            if(world.isAirBlock(pos.south()))
            {
                surroundingAir++;
            }

            if(world.isAirBlock(pos.down()))
            {
                surroundingAir++;
            }

            if(!config.insideRock && surroundingNetherrack == 4 && surroundingAir == 1 || surroundingNetherrack == 5)
            {
                world.setBlockState(pos, Blocks.LAVA.getDefaultState(), 2);
                world.getPendingFluidTicks().scheduleTick(pos, Fluids.LAVA, 0);
            }

            return true;
        }
    }

}

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

package nex.world.gen.feature;

import com.google.gson.JsonObject;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import nex.world.gen.GenerationStage;

import java.util.Random;

public class BiomeFeatureFluid extends BiomeFeature
{
    private final IBlockState blockToSpawn;
    private final IBlockState blockToReplace;
    private final boolean hidden;

    public BiomeFeatureFluid(Biome biome, GenerationStage generationStage, int generationAttempts, int minHeight, int maxHeight, IBlockState blockToSpawnIn, IBlockState blockToReplaceIn, boolean hiddenIn)
    {
        super(biome, generationStage, generationAttempts, minHeight, maxHeight);

        blockToSpawn = blockToSpawnIn;
        blockToReplace = blockToReplaceIn;
        hidden = hiddenIn;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        if(world.getBlockState(pos.up()) != blockToReplace)
        {
            return false;
        }
        else if(!world.isAirBlock(pos) && world.getBlockState(pos) != blockToReplace)
        {
            return false;
        }
        else
        {
            int i = 0;

            if(world.getBlockState(pos.west()) == blockToReplace)
            {
                ++i;
            }

            if(world.getBlockState(pos.east()) == blockToReplace)
            {
                ++i;
            }

            if(world.getBlockState(pos.north()) == blockToReplace)
            {
                ++i;
            }

            if(world.getBlockState(pos.south()) == blockToReplace)
            {
                ++i;
            }

            if(world.getBlockState(pos.down()) == blockToReplace)
            {
                ++i;
            }

            int j = 0;

            if(world.isAirBlock(pos.west()))
            {
                ++j;
            }

            if(world.isAirBlock(pos.east()))
            {
                ++j;
            }

            if(world.isAirBlock(pos.north()))
            {
                ++j;
            }

            if(world.isAirBlock(pos.south()))
            {
                ++j;
            }

            if(world.isAirBlock(pos.down()))
            {
                ++j;
            }

            if(!hidden && i == 4 && j == 1 || i == 5)
            {
                IBlockState state = blockToSpawn;
                world.setBlockState(pos, state, 2);
                world.immediateBlockTick(pos, state, rand);
            }

            return true;
        }
    }

    @Override
    public BiomeFeature deserializeFeature(JsonObject config)
    {
        return null;
    }
}

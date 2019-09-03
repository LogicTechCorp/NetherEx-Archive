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
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.FireFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class FireFeatureOverride extends FireFeature
{
    public FireFeatureOverride(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory)
    {
        super(configFactory);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        for(int i = 0; i < 64; i++)
        {
            BlockPos randomPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            BlockState randomState = world.getBlockState(randomPos.down());

            if(world.isAirBlock(randomPos) && randomState.getBlock().isIn(NetherExTags.Blocks.NETHERRACK))
            {
                world.setBlockState(randomPos, Blocks.FIRE.getDefaultState(), 2);
            }
        }

        return true;
    }
}

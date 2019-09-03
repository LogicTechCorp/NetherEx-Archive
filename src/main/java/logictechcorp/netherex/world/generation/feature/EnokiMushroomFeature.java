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
import logictechcorp.netherex.block.EnokiCapBlock;
import logictechcorp.netherex.block.NetherExBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class EnokiMushroomFeature extends Feature<NoFeatureConfig>
{
    public EnokiMushroomFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory)
    {
        super(configFactory);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos pos, NoFeatureConfig config)
    {
        if(world.isAirBlock(pos.down()) && world.getBlockState(pos).getBlock() == NetherExBlocks.LIVELY_NETHERRACK)
        {
            EnokiCapBlock.generatePlant(world, pos.down(), random, 8);
            return true;
        }
        else
        {
            return false;
        }
    }
}

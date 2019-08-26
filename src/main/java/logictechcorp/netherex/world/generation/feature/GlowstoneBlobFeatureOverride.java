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
import logictechcorp.netherex.init.NetherExTags;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.GlowstoneBlobFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class GlowstoneBlobFeatureOverride extends GlowstoneBlobFeature
{
    public GlowstoneBlobFeatureOverride(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory)
    {
        super(configFactory);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, BlockPos pos, NoFeatureConfig config)
    {
        BlockPos upPos = pos.up();

        if(!world.isAirBlock(pos))
        {
            return false;
        }
        else if(!world.getBlockState(upPos).isIn(NetherExTags.Blocks.NETHERRACK))
        {
            return false;
        }
        else
        {
            world.setBlockState(pos, Blocks.GLOWSTONE.getDefaultState(), 2);

            for(int i = 0; i < 1500; i++)
            {
                BlockPos randomPos = pos.add(random.nextInt(8) - random.nextInt(8), -random.nextInt(12), random.nextInt(8) - random.nextInt(8));

                if(world.getBlockState(randomPos).isAir(world, randomPos))
                {
                    int sidesTouching = 0;

                    for(Direction direction : Direction.values())
                    {
                        if(world.getBlockState(randomPos.offset(direction)).getBlock() == Blocks.GLOWSTONE)
                        {
                            sidesTouching++;
                        }

                        if(sidesTouching > 1)
                        {
                            break;
                        }
                    }

                    if(sidesTouching == 1)
                    {
                        world.setBlockState(randomPos, Blocks.GLOWSTONE.getDefaultState(), 2);
                    }
                }
            }

            return true;
        }
    }
}

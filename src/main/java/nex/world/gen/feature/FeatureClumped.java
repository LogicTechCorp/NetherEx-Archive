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

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import nex.util.BlockUtil;
import nex.world.biome.NetherBiome;

import java.util.Random;

public class FeatureClumped extends Feature
{
    private final IBlockState blockToSpawn;

    public FeatureClumped(Biome biome, NetherBiome.BiomeFeature feature)
    {
        super(biome, feature);

        blockToSpawn = BlockUtil.getBlock(feature.getBlockToSpawn(), "minecraft:air");
    }

    @Override
    public boolean generate(World world, BlockPos pos, Random rand)
    {
        if(!world.isAirBlock(pos))
        {
            return false;
        }
        else if(!world.getBlockState(pos.up()).isSideSolid(world, pos.up(), EnumFacing.DOWN))
        {
            return false;
        }
        else
        {
            world.setBlockState(pos, blockToSpawn, 3);

            for(int i = 0; i < 1500; ++i)
            {
                BlockPos newPos = pos.add(rand.nextInt(8) - rand.nextInt(8), -rand.nextInt(12), rand.nextInt(8) - rand.nextInt(8));

                if(world.isAirBlock(newPos))
                {
                    int j = 0;

                    for(EnumFacing enumfacing : EnumFacing.values())
                    {
                        if(world.getBlockState(newPos.offset(enumfacing)).getBlock() == blockToSpawn.getBlock())
                        {
                            ++j;
                        }

                        if(j > 1)
                        {
                            break;
                        }
                    }

                    if(j == 1)
                    {
                        world.setBlockState(newPos, blockToSpawn, 3);
                    }
                }
            }

            return true;
        }
    }

    @Override
    public boolean canGenerate()
    {
        return blockToSpawn != Blocks.AIR.getDefaultState();
    }

    @Override
    public FeatureType getType()
    {
        return FeatureType.CLUMPED;
    }
}

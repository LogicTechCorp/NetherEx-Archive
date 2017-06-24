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

import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import nex.util.BlockUtil;
import nex.world.biome.NetherBiome;

import java.util.Random;

public class FeatureScattered extends Feature
{
    private final IBlockState blockToSpawn;
    private final IBlockState targetBlock;

    public FeatureScattered(Biome biomeIn, IBlockState blockToSpawnIn, IBlockState targetBlockIn, int rarityIn, int minHeightIn, int maxHeightIn)
    {
        super(biomeIn, rarityIn, minHeightIn, maxHeightIn);

        blockToSpawn = blockToSpawnIn;
        targetBlock = targetBlockIn;
    }

    public FeatureScattered(Biome biome, NetherBiome.BiomeFeature feature)
    {
        super(biome, feature.getRarity() <= 0 ? 10 : feature.getRarity(), feature.getMinHeight() <= 0 || feature.getMinHeight() >= 128 ? 4 : feature.getMinHeight(), feature.getMaxHeight() >= 120 || feature.getMaxHeight() <= 0 ? 108 : feature.getMaxHeight());

        blockToSpawn = BlockUtil.getBlock(feature.getBlockToSpawn());
        targetBlock = BlockUtil.getBlock(feature.getTargetBlock());
    }

    @Override
    public boolean generate(World world, BlockPos pos, Random rand)
    {
        for(int i = 0; i < 64; ++i)
        {
            BlockPos newPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if(world.isAirBlock(newPos) && world.getBlockState(newPos.down()) == targetBlock)
            {
                if(blockToSpawn instanceof BlockBush && ((BlockBush) blockToSpawn).canBlockStay(world, newPos, ((BlockBush) blockToSpawn).getDefaultState()))
                {
                    world.setBlockState(newPos, blockToSpawn, 2);
                }
                else
                {
                    world.setBlockState(newPos, blockToSpawn, 2);
                }
            }
        }

        return true;
    }

    @Override
    public boolean canGenerate()
    {
        return !(blockToSpawn == Blocks.AIR.getDefaultState() || targetBlock == Blocks.AIR.getDefaultState());
    }

    @Override
    public FeatureType getType()
    {
        return FeatureType.SCATTERED;
    }
}

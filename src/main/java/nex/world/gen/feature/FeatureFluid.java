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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nex.util.BlockUtil;
import nex.world.biome.NetherBiome;

import java.util.Random;

public class FeatureFluid extends Feature
{
    private final IBlockState blockToSpawn;
    private final IBlockState targetBlock;
    private final boolean hidden;

    public FeatureFluid(IBlockState blockToSpawnIn, IBlockState targetBlockIn, boolean hiddenIn, int rarityIn, int minHeightIn, int maxHeightIn)
    {
        super(rarityIn, minHeightIn, maxHeightIn);

        blockToSpawn = blockToSpawnIn;
        targetBlock = targetBlockIn;
        hidden = hiddenIn;
    }

    public FeatureFluid(NetherBiome.BiomeFeature feature)
    {
        super(feature.getRarity() <= 0 ? 10 : feature.getRarity(), feature.getMinHeight() <= 0 || feature.getMinHeight() >= 128 ? 10 : feature.getMinHeight(), feature.getMaxHeight() >= 128 || feature.getMaxHeight() <= 0 ? 108 : feature.getMaxHeight());

        blockToSpawn = BlockUtil.getBlock(feature.getBlockToSpawn());
        targetBlock = BlockUtil.getBlock(feature.getTargetBlock());
        hidden = feature.isHidden();
    }

    @Override
    public boolean generate(World world, BlockPos pos, Random rand)
    {
        if(world.getBlockState(pos.up()) != targetBlock)
        {
            return false;
        }
        else if(!world.isAirBlock(pos) && world.getBlockState(pos) != targetBlock)
        {
            return false;
        }
        else
        {
            int i = 0;

            if(world.getBlockState(pos.west()) == targetBlock)
            {
                ++i;
            }

            if(world.getBlockState(pos.east()) == targetBlock)
            {
                ++i;
            }

            if(world.getBlockState(pos.north()) == targetBlock)
            {
                ++i;
            }

            if(world.getBlockState(pos.south()) == targetBlock)
            {
                ++i;
            }

            if(world.getBlockState(pos.down()) == targetBlock)
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
    public boolean canGenerate()
    {
        return !(blockToSpawn == Blocks.AIR.getDefaultState() || targetBlock == Blocks.AIR.getDefaultState());
    }

    @Override
    public FeatureType getType()
    {
        return FeatureType.FLUID;
    }
}

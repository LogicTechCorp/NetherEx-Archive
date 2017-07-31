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
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nex.init.NetherExBlocks;

import java.util.Random;

public class NetherGeneratorThornstalk extends NetherGenerator
{
    public static final NetherGeneratorThornstalk INSTANCE = new NetherGeneratorThornstalk(0, 0.0F, 0, 0);

    private NetherGeneratorThornstalk(int generationAttempts, float generationProbability, int minHeight, int maxHeight)
    {
        super(generationAttempts, generationProbability, minHeight, maxHeight);
    }

    @Override
    public NetherGeneratorThornstalk deserializeConfig(JsonObject config)
    {
        int generationAttempts = JsonUtils.getInt(config, "generationAttempts", 10);
        float generationProbability = JsonUtils.getFloat(config, "generationProbability", 1.0F);
        int minHeight = JsonUtils.getInt(config, "minHeight", 32);
        int maxHeight = JsonUtils.getInt(config, "maxHeight", 128);

        return new NetherGeneratorThornstalk(generationAttempts, generationProbability, minHeight, maxHeight);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        for(int i = 0; i < 64; ++i)
        {
            BlockPos newPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            Block blockDown = world.getBlockState(newPos.down()).getBlock();

            if(blockDown == Blocks.SOUL_SAND && NetherExBlocks.PLANT_THORNSTALK.canPlaceBlockAt(world, newPos))
            {
                NetherExBlocks.PLANT_THORNSTALK.generate(world, rand, newPos);
            }
        }

        return true;
    }
}

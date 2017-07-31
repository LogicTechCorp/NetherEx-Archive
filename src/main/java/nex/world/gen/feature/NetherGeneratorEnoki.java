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
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nex.block.BlockEnokiMushroomCap;
import nex.init.NetherExBlocks;

import java.util.Random;

public class NetherGeneratorEnoki extends NetherGenerator
{
    public static final NetherGeneratorEnoki INSTANCE = new NetherGeneratorEnoki(0, 0.0F, 0, 0);

    private NetherGeneratorEnoki(int generationAttempts, float generationProbability, int minHeight, int maxHeight)
    {
        super(generationAttempts, generationProbability, minHeight, maxHeight);
    }

    @Override
    public NetherGeneratorEnoki deserializeConfig(JsonObject config)
    {
        int generationAttempts = JsonUtils.getInt(config, "generationAttempts", 10);
        float generationProbability = JsonUtils.getFloat(config, "generationProbability", 1.0F);
        int minHeight = JsonUtils.getInt(config, "minHeight", 32);
        int maxHeight = JsonUtils.getInt(config, "maxHeight", 128);

        return new NetherGeneratorEnoki(generationAttempts, generationProbability, minHeight, maxHeight);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        if(world.isAirBlock(pos.down()) && NetherExBlocks.PLANT_MUSHROOM_ENOKI_CAP.canSurvive(world, pos) && rand.nextInt(8) == 0)
        {
            BlockEnokiMushroomCap.generatePlant(world, pos, rand, 8);
            return true;
        }

        return false;
    }
}

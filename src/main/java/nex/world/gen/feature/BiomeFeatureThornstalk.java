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

public class BiomeFeatureThornstalk extends BiomeFeature<BiomeFeatureThornstalk>
{
    public static final BiomeFeatureThornstalk INSTANCE = new BiomeFeatureThornstalk(0.0F, 0, 0);

    private BiomeFeatureThornstalk(float genAttempts, int minHeight, int maxHeight)
    {
        super(genAttempts, minHeight, maxHeight);
    }

    @Override
    public BiomeFeatureThornstalk deserialize(JsonObject config)
    {
        float genAttempts = JsonUtils.getFloat(config, "genAttempts", 10.0F);
        int minHeight = JsonUtils.getInt(config, "minHeight", 32);
        int maxHeight = JsonUtils.getInt(config, "maxHeight", 128);

        return new BiomeFeatureThornstalk(genAttempts, minHeight, maxHeight);
    }

    @Override
    public boolean generate(World world, BlockPos pos, Random rand)
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

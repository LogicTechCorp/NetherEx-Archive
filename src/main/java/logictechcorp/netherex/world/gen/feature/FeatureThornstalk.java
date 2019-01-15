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

package logictechcorp.netherex.world.gen.feature;

import com.electronwill.nightconfig.core.Config;
import logictechcorp.libraryex.world.gen.feature.Feature;
import logictechcorp.netherex.init.NetherExBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class FeatureThornstalk extends Feature
{

    public FeatureThornstalk(Config config)
    {
        super(config);
    }

    public FeatureThornstalk(int genAttempts, double genProbability, boolean randomizeGenAttempts, int minGenHeight, int maxGenHeight)
    {
        super(genAttempts, genProbability, randomizeGenAttempts, minGenHeight, maxGenHeight);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        for(int i = 0; i < 64; i++)
        {
            BlockPos newPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            Block blockDown = world.getBlockState(newPos.down()).getBlock();

            if(blockDown == Blocks.SOUL_SAND && NetherExBlocks.THORNSTALK.canPlaceBlockAt(world, newPos))
            {
                NetherExBlocks.THORNSTALK.generate(world, rand, newPos);
            }
        }

        return true;
    }
}

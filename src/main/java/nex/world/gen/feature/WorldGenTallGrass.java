/*
 * Copyright (C) 2016.  LogicTechCorp
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import nex.block.BlockTallGrass;
import nex.init.NetherExBlocks;

import java.util.Random;

public class WorldGenTallGrass extends WorldGenerator
{
    private final IBlockState tallGrassState;

    public WorldGenTallGrass(BlockTallGrass.EnumType type)
    {
        tallGrassState = NetherExBlocks.TALL_GRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, type);
    }

    public boolean generate(World world, Random rand, BlockPos pos)
    {
        for(IBlockState state = world.getBlockState(pos); (state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos)) && pos.getY() > 0; state = world.getBlockState(pos))
        {
            pos = pos.down();
        }

        for(int i = 0; i < 128; ++i)
        {
            BlockPos newPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if(world.isAirBlock(newPos) && NetherExBlocks.TALL_GRASS.canBlockStay(world, newPos, tallGrassState))
            {
                world.setBlockState(newPos, tallGrassState, 2);
            }
        }

        return true;
    }
}
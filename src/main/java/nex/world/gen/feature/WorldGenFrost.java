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

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import nex.Settings;
import nex.init.ModBlocks;

import java.util.Random;

public class WorldGenFrost extends WorldGenerator
{
    private int heightMultiplier = Settings.generateTallNether ? 2 : 1;

    public boolean generate(World world, Random rand, BlockPos pos)
    {
        for(; pos.getY() < 128 * heightMultiplier; pos = pos.up())
        {
            if(world.isAirBlock(pos))
            {
                for(EnumFacing facing : EnumFacing.Plane.HORIZONTAL.facings())
                {
                    if(ModBlocks.FROST.canPlaceBlockOnSide(world, pos, facing))
                    {
                        world.setBlockState(pos, ModBlocks.FROST.getStateFromMeta(facing.getOpposite().ordinal()), 2);
                        break;
                    }
                }
            }
            else
            {
                pos = pos.add(rand.nextInt(4) - rand.nextInt(4), 0, rand.nextInt(4) - rand.nextInt(4));
            }
        }

        return true;
    }
}

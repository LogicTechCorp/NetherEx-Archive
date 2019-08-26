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

package logictechcorp.netherex.world.generation.placement;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EnokiMushroomPlacement extends Placement<NoPlacementConfig>
{
    public EnokiMushroomPlacement(Function<Dynamic<?>, ? extends NoPlacementConfig> configFactory)
    {
        super(configFactory);
    }

    @Override
    public Stream<BlockPos> getPositions(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random random, NoPlacementConfig config, BlockPos pos)
    {
        int amount = random.nextInt(9) + 8;

        return IntStream.range(8, amount).mapToObj(ignored ->
        {
            int posX = random.nextInt(16);
            int posZ = random.nextInt(16);
            int posY = random.nextInt(91) + 36;

            if(posY > 0)
            {
                return new BlockPos(pos.getX() + posX, ++posY, pos.getZ() + posZ);
            }
            else
            {
                return null;
            }
        }).filter(Objects::nonNull);
    }
}

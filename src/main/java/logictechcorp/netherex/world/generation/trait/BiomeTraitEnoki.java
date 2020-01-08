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

package logictechcorp.netherex.world.generation.trait;

import logictechcorp.libraryex.world.generation.trait.BiomeTrait;
import logictechcorp.netherex.block.BlockEnokiMushroomCap;
import logictechcorp.netherex.init.NetherExBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Consumer;

public class BiomeTraitEnoki extends BiomeTrait
{
    private BiomeTraitEnoki(Builder builder)
    {
        super(builder);
    }

    public static BiomeTraitEnoki create(Consumer<Builder> consumer)
    {
        Builder builder = new Builder();
        consumer.accept(builder);
        return builder.create();
    }

    @Override
    public boolean generate(World world, BlockPos pos, Random random)
    {
        if(world.isAirBlock(pos.down()) && NetherExBlocks.ENOKI_MUSHROOM_CAP.canSurvive(world, pos) && random.nextInt(8) == 7)
        {
            BlockEnokiMushroomCap.generatePlant(world, pos, random, 8);
            return true;
        }

        return false;
    }

    public static class Builder extends BiomeTrait.Builder<BiomeTraitEnoki>
    {
        public Builder()
        {
            super();
        }

        @Override
        public BiomeTraitEnoki create()
        {
            return new BiomeTraitEnoki(this);
        }
    }

}

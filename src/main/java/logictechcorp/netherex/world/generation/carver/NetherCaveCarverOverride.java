/*
 * NetherEx
 * Copyright (c) 2016-2020 by LogicTechCorp
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

package logictechcorp.netherex.world.generation.carver;

import com.mojang.datafixers.Dynamic;
import logictechcorp.libraryex.world.biome.BiomeData;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.data.NetherExTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.NetherCaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class NetherCaveCarverOverride extends NetherCaveWorldCarver
{
    public NetherCaveCarverOverride(Function<Dynamic<?>, ? extends ProbabilityConfig> configFactory)
    {
        super(configFactory);
    }

    @Override
    protected boolean func_225556_a_(IChunk chunk, Function<BlockPos, Biome> posBiomeFunction, BitSet carvingMask, Random random, BlockPos.Mutable mutablePos, BlockPos.Mutable mutablePosUp, BlockPos.Mutable mutablePosDown, int minY, int minX, int minZ, int posX, int posZ, int p_222703_12_, int posY, int p_222703_14_, AtomicBoolean isDefaultTopBlock)
    {
        int bitIndex = p_222703_12_ | p_222703_14_ << 4 | posY << 8;

        if(carvingMask.get(bitIndex))
        {
            return false;
        }
        else
        {
            carvingMask.set(bitIndex);
            mutablePos.setPos(posX, posY, posZ);
            mutablePosUp.setPos(mutablePos).move(Direction.UP);

            if(this.canCarveBlock(chunk.getBlockState(mutablePos), chunk.getBlockState(mutablePosUp)))
            {
                BlockState state;

                if(posY <= 31)
                {
                    state = LAVA.getBlockState();
                    BiomeData biomeData = NetherEx.BIOME_DATA_MANAGER.getBiomeData(posBiomeFunction.apply(mutablePos));

                    if(biomeData != BiomeData.EMPTY)
                    {
                        state = biomeData.getBiomeBlock(BiomeData.BlockType.LIQUID_BLOCK);
                    }
                }
                else
                {
                    state = CAVE_AIR;
                }

                chunk.setBlockState(mutablePos, state, false);
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    @Override
    protected boolean canCarveBlock(BlockState state, BlockState aboveState)
    {
        Block block = state.getBlock();
        return this.func_222706_a(state) || state.isIn(NetherExTags.Blocks.NETHERRACK) || ((block == Blocks.SAND || block == Blocks.GRAVEL) && !aboveState.getFluidState().isTagged(FluidTags.WATER));
    }
}

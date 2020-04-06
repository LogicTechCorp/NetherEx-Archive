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

package logictechcorp.netherex.world.generation.surfacebuilder;

import com.mojang.datafixers.Dynamic;
import logictechcorp.libraryex.world.biome.BiomeData;
import net.minecraft.block.BlockState;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

import java.util.Random;
import java.util.function.Function;

public class NetherSurfaceBuilderWrapper extends SurfaceBuilder<NetherSurfaceBuilderWrapper.Config>
{
    protected long seed;
    protected OctavesNoiseGenerator noiseGenerator;

    public NetherSurfaceBuilderWrapper(Function<Dynamic<?>, ? extends NetherSurfaceBuilderWrapper.Config> ops)
    {
        super(ops);
    }

    @Override
    public void buildSurface(Random random, IChunk chunk, Biome biome, int chunkX, int chunkZ, int startHeight, double noise, BlockState defaultState, BlockState defaultLiquid, int seaLevel, long seed, Config config)
    {
        if(seaLevel != 32)
        {
            config.getOriginalBuilder().buildSurface(random, chunk, biome, chunkX, chunkZ, startHeight, noise, defaultState, defaultLiquid, seaLevel, seed);
        }
        else
        {
            int posX = chunkX & 15;
            int posZ = chunkZ & 15;

            BlockPos.Mutable mutablePos = new BlockPos.Mutable();

            boolean wasLastBlockNonSolid = false;

            for(int posY = 127; posY >= 0; posY--)
            {
                mutablePos = mutablePos.setPos(posX, posY, posZ);
                BlockState checkState = chunk.getBlockState(mutablePos);

                if(checkState.isAir(chunk, mutablePos))
                {
                    wasLastBlockNonSolid = true;
                }
                else if(checkState == defaultState)
                {
                    if(wasLastBlockNonSolid)
                    {
                        if(posY < seaLevel)
                        {
                            chunk.setBlockState(mutablePos, config.getLiquid(), false);
                        }
                        else
                        {
                            chunk.setBlockState(mutablePos, config.getTop(), false);
                        }
                    }
                    else
                    {
                        chunk.setBlockState(mutablePos, config.getUnder(), false);
                    }

                    wasLastBlockNonSolid = false;
                }
                else if(checkState == defaultLiquid)
                {
                    chunk.setBlockState(mutablePos, config.getLiquid(), false);
                    wasLastBlockNonSolid = true;
                }
            }
        }
    }

    @Override
    public void setSeed(long seed)
    {
        if(this.seed != seed || this.noiseGenerator == null)
        {
            this.noiseGenerator = new OctavesNoiseGenerator(new SharedSeedRandom(seed), 3, 0);
        }

        this.seed = seed;
    }

    public static class Config implements ISurfaceBuilderConfig
    {
        private final BlockState surfaceState;
        private final BlockState subsurfaceState;
        private final BlockState liquidState;
        private final ConfiguredSurfaceBuilder<?> originalBuilder;

        public Config(BiomeData biomeData)
        {
            this.surfaceState = biomeData.getBiomeBlock(BiomeData.BlockType.SURFACE_BLOCK);
            this.subsurfaceState = biomeData.getBiomeBlock(BiomeData.BlockType.SUBSURFACE_BLOCK);
            this.liquidState = biomeData.getBiomeBlock(BiomeData.BlockType.LIQUID_BLOCK);
            this.originalBuilder = biomeData.getBiome().getSurfaceBuilder();
        }

        public static <T> Config deserialize(Dynamic<T> dynamic)
        {
            return new Config(BiomeData.EMPTY);
        }

        @Override
        public BlockState getTop()
        {
            return this.surfaceState;
        }

        @Override
        public BlockState getUnder()
        {
            return this.subsurfaceState;
        }

        public BlockState getLiquid()
        {
            return this.liquidState;
        }

        public ConfiguredSurfaceBuilder<?> getOriginalBuilder()
        {
            return this.originalBuilder;
        }
    }
}

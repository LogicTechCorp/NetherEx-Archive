/*
 * Copyright 2014-2017, the Biomes O' Plenty Team
 *
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 *
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 *
 * Original: https://github.com/Glitchfiend/BiomesOPlenty/blob/9873b7ad56ab8f32e6073dea060c4b67aad8b77e/src/main/java/biomesoplenty/common/biome/nether/BOPHellBiome.java#L84
 * Edited to fit a newer Minecraft version
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
            BlockState surfaceState = config.getTop();
            BlockState subsurfaceState = config.getUnder();
            BlockState caveCeilingState = config.getCaveCeiling();
            BlockState caveWallState = config.getCaveWall();
            BlockState caveFloorState = config.getCaveFloor();
            BlockState liquidState = config.getLiquid();

            BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            boolean wasLastBlockSolid = true;

            for(int posY = 127; posY >= 0; posY--)
            {
                mutableBlockPos = mutableBlockPos.setPos(posX, posY, posZ);

                BlockState checkState = chunk.getBlockState(mutableBlockPos);
                int blocksToSkip = 0;

                if(checkState == defaultState)
                {
                    chunk.setBlockState(mutableBlockPos, caveWallState, false);

                    if(!wasLastBlockSolid)
                    {
                        chunk.setBlockState(mutableBlockPos, surfaceState, false);

                        for(int floorOffset = 1; floorOffset <= 4 - 1 && posY - floorOffset >= 0; floorOffset++)
                        {
                            BlockState state = chunk.getBlockState(mutableBlockPos.setPos(posX, posY - floorOffset, posZ));
                            blocksToSkip = floorOffset;

                            if(state == defaultState)
                            {
                                chunk.setBlockState(mutableBlockPos, subsurfaceState, false);
                            }
                            else
                            {
                                break;
                            }
                        }
                    }

                    wasLastBlockSolid = true;
                }
                else if(checkState.isAir())
                {
                    if(wasLastBlockSolid)
                    {
                        if(posY + 1 <= 127)
                        {
                            chunk.setBlockState(mutableBlockPos.setPos(posX, posY + 1, posZ), caveFloorState, false);
                        }

                        for(int roofOffset = 2; roofOffset <= 4 && posY + roofOffset <= 127; roofOffset++)
                        {
                            BlockState state = chunk.getBlockState(mutableBlockPos.setPos(posX, posY + roofOffset, posZ));

                            if(state == defaultState || state == caveWallState)
                            {
                                chunk.setBlockState(mutableBlockPos, caveCeilingState, false);
                            }
                            else
                            {
                                break;
                            }
                        }
                    }

                    wasLastBlockSolid = false;
                }
                else if(checkState == defaultLiquid)
                {
                    chunk.setBlockState(mutableBlockPos, liquidState, false);
                    wasLastBlockSolid = false;
                }

                posY -= blocksToSkip;
            }
        }
    }

    @Override
    public void setSeed(long seed)
    {
        if (this.seed != seed || this.noiseGenerator == null)
        {
            this.noiseGenerator = new OctavesNoiseGenerator(new SharedSeedRandom(seed), 4);
        }

        this.seed = seed;
    }

    public static class Config implements ISurfaceBuilderConfig
    {
        private final BlockState surfaceState;
        private final BlockState subsurfaceState;
        private final BlockState caveCeilingState;
        private final BlockState caveWallState;
        private final BlockState caveFloorState;
        private final BlockState liquidState;
        private final ConfiguredSurfaceBuilder<?> originalBuilder;

        public Config(BiomeData biomeData)
        {
            this.surfaceState = biomeData.getBiomeBlock(BiomeData.BlockType.SURFACE_BLOCK);
            this.subsurfaceState = biomeData.getBiomeBlock(BiomeData.BlockType.SUBSURFACE_BLOCK);
            this.caveCeilingState = biomeData.getBiomeBlock(BiomeData.BlockType.CAVE_CEILING_BLOCK);
            this.caveWallState = biomeData.getBiomeBlock(BiomeData.BlockType.CAVE_WALL_BLOCK);
            this.caveFloorState = biomeData.getBiomeBlock(BiomeData.BlockType.CAVE_FLOOR_BLOCK);
            this.liquidState = biomeData.getBiomeBlock(BiomeData.BlockType.LIQUID_BLOCK);
            this.originalBuilder = biomeData.getBiome().getSurfaceBuilder();
        }

        public static Config deserialize(Dynamic<?> ops)
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

        public BlockState getCaveCeiling()
        {
            return this.caveCeilingState;
        }

        public BlockState getCaveWall()
        {
            return this.caveWallState;
        }

        public BlockState getCaveFloor()
        {
            return this.caveFloorState;
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

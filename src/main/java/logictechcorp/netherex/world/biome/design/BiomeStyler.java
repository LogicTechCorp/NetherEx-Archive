/*
 * Copyright 2014-2017, the Biomes O' Plenty Team
 *
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 *
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 *
 * Original: https://github.com/Glitchfiend/BiomesOPlenty/blob/9873b7ad56ab8f32e6073dea060c4b67aad8b77e/src/main/java/biomesoplenty/common/biome/nether/BOPHellBiome.java#L84
 */

package logictechcorp.netherex.world.biome.design;

import logictechcorp.libraryex.api.world.biome.data.IBiomeData;
import logictechcorp.libraryex.world.biome.BiomeBlock;
import logictechcorp.netherex.api.NetherExAPI;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Random;

public class BiomeStyler
{
    public static void styleBiome(World world, IChunkGenerator generator, ChunkPrimer primer, int chunkX, int chunkZ, NoiseGeneratorPerlin terrainNoiseGen, double[] terrainNoise, Biome[] biomes, Random random)
    {
        if(!ForgeEventFactory.onReplaceBiomeBlocks(generator, chunkX, chunkZ, primer, world))
        {
            return;
        }

        terrainNoise = terrainNoiseGen.getRegion(terrainNoise, (double) (chunkX * 16), (double) (chunkZ * 16), 16, 16, 0.03125D * 2.0D, 0.03125D * 2.0D, 1.0D);

        for(int x = 0; x < 16; x++)
        {
            for(int z = 0; z < 16; z++)
            {
                IBiomeData biomeData = NetherExAPI.getInstance().getBiomeDataRegistry().getBiomeData(biomes[x + z * 16]);

                if(biomeData != null)
                {
                    Biome biome = biomeData.getBiome();
                    ResourceLocation biomeRegistryName = biome.getRegistryName();

                    if(!biomeRegistryName.getNamespace().equalsIgnoreCase("biomesoplenty"))
                    {
                        IBlockState surfaceBlock = biomeData.getBiomeBlock(BiomeBlock.FLOOR_TOP_BLOCK, biome.topBlock);
                        IBlockState fillerBlock = biomeData.getBiomeBlock(BiomeBlock.FLOOR_FILLER_BLOCK, biome.fillerBlock);
                        IBlockState wallBlock = biomeData.getBiomeBlock(BiomeBlock.WALL_BLOCK, biome.fillerBlock);
                        IBlockState ceilingFillerBlock = biomeData.getBiomeBlock(BiomeBlock.CEILING_FILLER_BLOCK, biome.fillerBlock);
                        IBlockState ceilingBottomBlock = biomeData.getBiomeBlock(BiomeBlock.CEILING_BOTTOM_BLOCK, biome.fillerBlock);
                        IBlockState oceanBlock = biomeData.getBiomeBlock(BiomeBlock.OCEAN_BLOCK, Blocks.LAVA.getDefaultState());

                        int localX = ((chunkX * 16) + x) & 15;
                        int localZ = ((chunkZ * 16) + z) & 15;

                        boolean wasLastBlockSolid = true;

                        for(int localY = 127; localY >= 0; localY--)
                        {
                            int blocksToSkip = 0;

                            IBlockState checkState = primer.getBlockState(localX, localY, localZ);

                            if(checkState.getBlock() == Blocks.NETHERRACK)
                            {
                                primer.setBlockState(localX, localY, localZ, wallBlock);

                                if(!wasLastBlockSolid)
                                {
                                    primer.setBlockState(localX, localY, localZ, surfaceBlock);

                                    for(int floorOffset = 1; floorOffset <= 4 - 1 && localY - floorOffset >= 0; floorOffset++)
                                    {
                                        IBlockState state = primer.getBlockState(localX, localY - floorOffset, localZ);
                                        blocksToSkip = floorOffset;

                                        if(state.getBlock() == Blocks.NETHERRACK)
                                        {
                                            primer.setBlockState(localX, localY - floorOffset, localZ, fillerBlock);
                                        }
                                        else
                                        {
                                            break;
                                        }
                                    }
                                }

                                wasLastBlockSolid = true;
                            }
                            else if(checkState.getBlock() == Blocks.AIR)
                            {
                                if(wasLastBlockSolid)
                                {
                                    if(localY + 1 < 128)
                                    {
                                        primer.setBlockState(localX, localY + 1, localZ, ceilingBottomBlock);
                                    }

                                    for(int roofOffset = 2; roofOffset <= 4 && localY + roofOffset <= 127; roofOffset++)
                                    {
                                        IBlockState state = primer.getBlockState(localX, localY + roofOffset, localZ);

                                        if(state.getBlock() == Blocks.NETHERRACK || state == wallBlock)
                                        {
                                            primer.setBlockState(localX, localY + roofOffset, localZ, ceilingFillerBlock);
                                        }
                                        else
                                        {
                                            break;
                                        }
                                    }
                                }

                                wasLastBlockSolid = false;
                            }
                            else if(checkState == Blocks.LAVA.getDefaultState())
                            {
                                primer.setBlockState(localX, localY, localZ, oceanBlock);
                                wasLastBlockSolid = false;
                            }

                            localY -= blocksToSkip;
                        }
                    }
                    else
                    {
                        biome.genTerrainBlocks(world, random, primer, chunkX * 16 + x, chunkZ * 16 + z, terrainNoise[x + z * 16]);
                    }
                }
            }
        }
    }
}

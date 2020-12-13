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

package logictechcorp.netherex.world.biome.data;

import logictechcorp.libraryex.world.biome.data.BiomeData;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class BiomeDataNetherEx extends BiomeData
{
    public BiomeDataNetherEx(Biome biome, int generationWeight, boolean useDefaultDecorations, boolean isSubBiome)
    {
        super(biome, generationWeight, useDefaultDecorations, isSubBiome);
    }

    public BiomeDataNetherEx(ResourceLocation biomeRegistryName, int generationWeight, boolean useDefaultDecorations, boolean isSubBiome)
    {
        super(biomeRegistryName, generationWeight, useDefaultDecorations, isSubBiome);
    }

    @Override
    public void generateTerrain(World world, Random random, ChunkPrimer primer, int posX, int posZ, double noise)
    {
        IBlockState surfaceState = this.getBiomeBlock(BiomeData.BlockType.SURFACE_BLOCK);
        IBlockState subSurfaceState = this.getBiomeBlock(BiomeData.BlockType.SUBSURFACE_BLOCK);
        IBlockState liquidState = this.getBiomeBlock(BiomeData.BlockType.LIQUID_BLOCK);

        int chunkPosX = (posX & 15);
        int chunkPosZ = (posZ & 15);
        boolean wasLastBlockNonSolid = false;

        for(int posY = 127; posY >= 0; posY--)
        {
            IBlockState checkState = primer.getBlockState(chunkPosX, posY, chunkPosZ);

            if(checkState.getMaterial() == Material.AIR)
            {
                wasLastBlockNonSolid = true;
            }
            else if(checkState == Blocks.NETHERRACK.getDefaultState())
            {
                if(wasLastBlockNonSolid)
                {
                    if(posY < world.getSeaLevel())
                    {
                        primer.setBlockState(chunkPosX, posY, chunkPosZ, liquidState);
                    }
                    else
                    {
                        primer.setBlockState(chunkPosX, posY, chunkPosZ, surfaceState);
                    }
                }
                else
                {
                    primer.setBlockState(chunkPosX, posY, chunkPosZ, subSurfaceState);
                }

                wasLastBlockNonSolid = false;
            }
            else if(checkState == Blocks.LAVA.getDefaultState())
            {
                primer.setBlockState(chunkPosX, posY, chunkPosZ, liquidState);
                wasLastBlockNonSolid = true;
            }
        }
    }
}

/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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

package nex.world.biome;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.ChunkPrimer;
import nex.NetherEx;

import java.util.Random;

public abstract class BiomeNetherEx extends Biome
{
    private static final IBlockState AIR = Blocks.AIR.getDefaultState();
    private static final IBlockState NETHERRACK = Blocks.NETHERRACK.getDefaultState();
    private static final IBlockState LAVA = Blocks.LAVA.getDefaultState();

    public BiomeNetherEx(BiomeProperties properties, String name)
    {
        super(properties);

        spawnableMonsterList.clear();
        spawnableCreatureList.clear();
        spawnableWaterCreatureList.clear();
        spawnableCaveCreatureList.clear();

        setRegistryName(NetherEx.MOD_ID + ":" + name);
    }

    @Override
    public BiomeDecorator createBiomeDecorator()
    {
        return new BiomeDecoratorNether();
    }

    @Override
    public void genTerrainBlocks(World world, Random rand, ChunkPrimer primer, int chunkX, int chunkZ, double noise)
    {
        IBlockState topState = topBlock;
        IBlockState fillerState = fillerBlock;
        IBlockState oceanState = BiomeTypeNetherEx.getTypeFromBiome(this).getBiomeOceanBlock(this);

        int x = chunkX & 15;
        int z = chunkZ & 15;

        boolean belowAir = false;

        for(int y = 127; y >= 0; y--)
        {
            IBlockState checkState = primer.getBlockState(x, y, z);

            if(checkState == AIR)
            {
                belowAir = true;
            }
            else if(checkState == NETHERRACK)
            {
                if(belowAir)
                {
                    if(topState != NETHERRACK)
                    {
                        primer.setBlockState(x, y, z, topState);
                    }
                }
                else
                {
                    if(fillerState != NETHERRACK)
                    {
                        primer.setBlockState(x, y, z, fillerState);
                    }
                }

                belowAir = false;
            }
            else if(checkState == LAVA)
            {
                if(oceanState != LAVA)
                {
                    primer.setBlockState(x, y, z, oceanState);
                }

                belowAir = false;
            }
        }
    }
}

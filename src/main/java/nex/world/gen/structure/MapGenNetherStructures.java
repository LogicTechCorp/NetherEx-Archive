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

package nex.world.gen.structure;

import com.google.common.collect.Lists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;
import nex.init.NetherExBiomes;

import java.util.List;
import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class MapGenNetherStructures extends MapGenStructure
{
    private static final List<Biome> BIOMES = Lists.newArrayList(NetherExBiomes.HELL, NetherExBiomes.RUTHLESS_SANDS, NetherExBiomes.FUNGI_FOREST);
    private int maxChunkDistance = 32;

    @Override
    public String getStructureName()
    {
        return "Nether Structures";
    }

    @Override
    public BlockPos getClosestStrongholdPos(World worldIn, BlockPos pos, boolean p_180706_3_)
    {
        world = worldIn;
        return findNearestStructurePosBySpacing(worldIn, this, pos, maxChunkDistance, 8, 14357617, false, 100, p_180706_3_);

    }

    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        int i = chunkX;
        int j = chunkZ;

        if(chunkX < 0)
        {
            chunkX -= maxChunkDistance - 1;
        }

        if(chunkZ < 0)
        {
            chunkZ -= maxChunkDistance - 1;
        }

        int k = chunkX / maxChunkDistance;
        int l = chunkZ / maxChunkDistance;
        Random random = world.setRandomSeed(k, l, 14357617);
        k = k * maxChunkDistance;
        l = l * maxChunkDistance;
        k = k + random.nextInt(maxChunkDistance - 8);
        l = l + random.nextInt(maxChunkDistance - 8);

        if(i == k && j == l)
        {
            Biome biome = world.getBiomeProvider().getBiome(new BlockPos(i * 16 + 8, 0, j * 16 + 8));

            if(biome == null)
            {
                return false;
            }

            for(Biome biome1 : BIOMES)
            {
                if(biome == biome1)
                {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        return new Start(world, rand, chunkX, chunkZ);
    }

    public static class Start extends StructureStart
    {
        public Start()
        {
        }

        public Start(World world, Random random, int chunkX, int chunkZ)
        {
            this(world, random, chunkX, chunkZ, world.getBiome(new BlockPos(chunkX * 16 + 8, 0, chunkZ * 16 + 8)));
        }

        public Start(World world, Random random, int chunkX, int chunkZ, Biome biome)
        {
            if(biome == NetherExBiomes.HELL)
            {

            }
            else if(biome == NetherExBiomes.RUTHLESS_SANDS)
            {

            }
            else if(biome == NetherExBiomes.FUNGI_FOREST)
            {

            }
            else
            {

            }

            updateBoundingBox();
        }
    }
}

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
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;
import nex.init.NetherExBiomes;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class MapGenNetherStructures extends MapGenStructure
{
    private static final List<Biome> BIOMES = Lists.newArrayList(NetherExBiomes.HELL, NetherExBiomes.RUTHLESS_SANDS, NetherExBiomes.FUNGI_FOREST);
    private int maxChunkDistance = 16;
    private int minChunkDistance = 8;

    @Override
    public String getStructureName()
    {
        return "Nether Structures";
    }

    @Override
    public BlockPos getClosestStrongholdPos(World worldIn, BlockPos pos, boolean p_180706_3_)
    {
        world = worldIn;
        return findNearestStructurePosBySpacing(worldIn, this, pos, maxChunkDistance, minChunkDistance, 14357617, false, 100, p_180706_3_);

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
        k = k + random.nextInt(maxChunkDistance - minChunkDistance);
        l = l + random.nextInt(maxChunkDistance - minChunkDistance);

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

        public Start(World world, Random rand, int chunkX, int chunkZ)
        {
            this(world, rand, chunkX, chunkZ, world.getBiome(new BlockPos(chunkX * 16 + 8, 0, chunkZ * 16 + 8)));
        }

        public Start(World world, Random rand, int chunkX, int chunkZ, Biome biome)
        {
            if(biome == NetherExBiomes.HELL)
            {
                int structureType = rand.nextInt(4);

                if(structureType == 0)
                {
                    NetherStructures.ArtifactTower tower = new NetherStructures.ArtifactTower(rand, chunkX * 16, chunkZ * 16, 8, 12, 8);
                    components.add(tower);
                }
                else if(structureType == 1)
                {
                    NetherStructures.BlacksmithHut hut = new NetherStructures.BlacksmithHut(rand, chunkX * 16, chunkZ * 16, 8, 5, 9);
                    components.add(hut);
                }
                else if(structureType == 2)
                {
                    NetherStructures.ChiefHut hut = new NetherStructures.ChiefHut(rand, chunkX * 16, chunkZ * 16, 11, 6, 9);
                    components.add(hut);
                }
                else if(structureType == 3)
                {
                    NetherStructures.PigmanHut hut = new NetherStructures.PigmanHut(rand, chunkX * 16, chunkZ * 16, 9, 5, 9);
                    components.add(hut);
                }
            }
            else if(biome == NetherExBiomes.RUTHLESS_SANDS)
            {
                int structureType = rand.nextInt(2);

                if(structureType == 0)
                {
                    int[] altarTypeChance = new int[]{0, 1, 1, 3, 3, 3};
                    int altarType = altarTypeChance[rand.nextInt(altarTypeChance.length)];

                    NetherStructures.AncientAltar altar;

                    if(altarType == 0)
                    {
                        altar = new NetherStructures.AncientAltar(altarType, rand, chunkX * 16, chunkZ * 16, 5, 5, 5);
                    }
                    else if(altarType == 1)
                    {
                        altar = new NetherStructures.AncientAltar(altarType, rand, chunkX * 16, chunkZ * 16, 6, 4, 7);

                    }
                    else
                    {
                        altar = new NetherStructures.AncientAltar(altarType, rand, chunkX * 16, chunkZ * 16, 5, 2, 5);
                    }

                    components.add(altar);
                }
                else if(structureType == 1)
                {
                    NetherStructures.AncientThrone throne = new NetherStructures.AncientThrone(rand, chunkX * 16, chunkZ * 16, 5, 5, 5);
                    components.add(throne);
                }
            }
            else if(biome == NetherExBiomes.FUNGI_FOREST)
            {

            }
            else
            {

            }

            updateBoundingBox();
        }

        @Override
        public void generateStructure(World world, Random rand, StructureBoundingBox structureBB)
        {
            Iterator<StructureComponent> iterator = components.iterator();
            List<StructureComponent> list = Lists.newArrayList();

            while(iterator.hasNext())
            {
                StructureComponent structurecomponent = iterator.next();

                if (structurecomponent.getBoundingBox().intersectsWith(structureBB) && !structurecomponent.addComponentParts(world, rand, structureBB))
                {
                    list.add(iterator.next());
                }
            }

            components.removeAll(list);
        }
    }
}

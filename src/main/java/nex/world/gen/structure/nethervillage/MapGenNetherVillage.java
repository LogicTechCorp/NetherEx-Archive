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

package nex.world.gen.structure.nethervillage;

import com.google.common.collect.Lists;
import net.minecraft.init.Biomes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;
import nex.init.NetherExBiomes;

import java.util.List;
import java.util.Random;

public class MapGenNetherVillage extends MapGenStructure
{
    private static List<Biome> SPAWN_BIOMES = Lists.newArrayList(NetherExBiomes.HELL);

    private int distanceBetweenVillages;

    public MapGenNetherVillage()
    {
        distanceBetweenVillages = 32;
    }

    @Override
    public String getStructureName()
    {
        return "NetherVillage";
    }

    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        int i = chunkX;
        int j = chunkZ;

        if(chunkX < 0)
        {
            chunkX -= distanceBetweenVillages - 1;
        }

        if(chunkZ < 0)
        {
            chunkZ -= distanceBetweenVillages - 1;
        }

        int k = chunkX / distanceBetweenVillages;
        int l = chunkZ / distanceBetweenVillages;
        Random random = world.setRandomSeed(k, l, 10387312);
        k = k * distanceBetweenVillages;
        l = l * distanceBetweenVillages;
        k = k + random.nextInt(distanceBetweenVillages - 8);
        l = l + random.nextInt(distanceBetweenVillages - 8);

        if(i == k && j == l)
        {
            boolean flag = world.getBiomeProvider().areBiomesViable(i * 16 + 8, j * 16 + 8, 0, SPAWN_BIOMES);

            if(flag)
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored)
    {
        world = worldIn;
        return findNearestStructurePosBySpacing(worldIn, this, pos, distanceBetweenVillages, 8, 10387312, false, 100, findUnexplored);
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        return new Start(world, rand, chunkX, chunkZ, 0);
    }

    public static class Start extends StructureStart
    {
        private boolean hasMoreThanTwoRoads;

        public Start()
        {
        }

        public Start(World world, Random rand, int x, int z, int villageSize)
        {
            super(x, z);
            List<StructureNetherVillage.Piece> pieces = StructureNetherVillage.getStructureVillagePieceList(rand, villageSize);
            StructureNetherVillageWell.Controller controller = new StructureNetherVillageWell.Controller(world.getBiomeProvider().getBiome(new BlockPos(x, 0, z), Biomes.DEFAULT), rand, (x << 4) + 2, (z << 4) + 2, pieces, villageSize);
            components.add(controller);
            controller.buildComponent(controller, components, rand);
            List<StructureComponent> pendingRoads = controller.getPendingRoads();
            List<StructureComponent> pendingHouses = controller.getPendingHouses();

            while(!pendingRoads.isEmpty() || !pendingHouses.isEmpty())
            {
                if(pendingRoads.isEmpty())
                {
                    int i = rand.nextInt(pendingHouses.size());
                    StructureComponent component = pendingHouses.remove(i);
                    component.buildComponent(component, components, rand);
                }
                else
                {
                    int j = rand.nextInt(pendingRoads.size());
                    StructureComponent component = pendingRoads.remove(j);
                    component.buildComponent(controller, components, rand);
                }
            }

            updateBoundingBox();
            int k = 0;

            for(StructureComponent component : components)
            {
                if(!(component instanceof StructureNetherVillage.Road))
                {
                    ++k;
                }
            }

            hasMoreThanTwoRoads = k > 2;
        }

        @Override
        public boolean isSizeableStructure()
        {
            return hasMoreThanTwoRoads;
        }

        @Override
        public void writeToNBT(NBTTagCompound tagCompound)
        {
            super.writeToNBT(tagCompound);
            tagCompound.setBoolean("Valid", hasMoreThanTwoRoads);
        }

        @Override
        public void readFromNBT(NBTTagCompound tagCompound)
        {
            super.readFromNBT(tagCompound);
            hasMoreThanTwoRoads = tagCompound.getBoolean("Valid");
        }
    }
}

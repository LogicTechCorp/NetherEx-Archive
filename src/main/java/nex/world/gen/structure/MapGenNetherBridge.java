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
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureNetherBridgePieces;
import net.minecraft.world.gen.structure.StructureStart;

import java.util.List;
import java.util.Random;

public class MapGenNetherBridge extends MapGenStructure
{
    private static int heightMultiplier;
    private final List<Biome.SpawnListEntry> spawnList = Lists.<Biome.SpawnListEntry>newArrayList();

    public MapGenNetherBridge(int heightMultiplierIn)
    {
        heightMultiplier = heightMultiplierIn;

        spawnList.add(new Biome.SpawnListEntry(EntityBlaze.class, 10, 2, 3));
        spawnList.add(new Biome.SpawnListEntry(EntityPigZombie.class, 5, 4, 4));
        spawnList.add(new Biome.SpawnListEntry(EntitySkeleton.class, 10, 4, 4));
        spawnList.add(new Biome.SpawnListEntry(EntityMagmaCube.class, 3, 4, 4));
    }

    @Override
    public String getStructureName()
    {
        return "Nether Fortress";
    }

    public List<Biome.SpawnListEntry> getSpawnList()
    {
        return spawnList;
    }

    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        int i = chunkX >> 4;
        int j = chunkZ >> 4;
        rand.setSeed((long) (i ^ j << 4) ^ worldObj.getSeed());
        rand.nextInt();
        return rand.nextInt(3) == 0 && (chunkX == (i << 4) + 4 + rand.nextInt(8) && chunkZ == (j << 4) + 4 + rand.nextInt(8));
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        return new MapGenNetherBridge.Start(worldObj, rand, chunkX, chunkZ);
    }

    public static class Start extends StructureStart
    {
        public Start()
        {
        }

        public Start(World worldIn, Random random, int chunkX, int chunkZ)
        {
            super(chunkX, chunkZ);

            StructureNetherBridgePieces.Start pieces = new StructureNetherBridgePieces.Start(random, (chunkX << 4) + 2, (chunkZ << 4) + 2);
            components.add(pieces);
            pieces.buildComponent(pieces, components, random);
            List<StructureComponent> list = pieces.pendingChildren;

            while(!list.isEmpty())
            {
                int i = random.nextInt(list.size());
                StructureComponent structurecomponent = list.remove(i);
                structurecomponent.buildComponent(pieces, components, random);
            }

            updateBoundingBox();
            setRandomHeight(worldIn, random, 48 * heightMultiplier, 72 * heightMultiplier);
        }
    }
}
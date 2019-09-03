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

package logictechcorp.netherex.world.generation;

import logictechcorp.libraryex.world.biome.BiomeData;
import logictechcorp.netherex.NetherEx;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.NetherChunkGenerator;
import net.minecraft.world.gen.NetherGenSettings;
import net.minecraft.world.gen.feature.Feature;

import java.util.ArrayList;
import java.util.List;

public class CaveChunkGenerator extends NetherChunkGenerator
{
    public CaveChunkGenerator(World world, BiomeProvider biomeProvider, NetherGenSettings settings)
    {
        super(world, biomeProvider, settings);
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EntityClassification classification, BlockPos pos)
    {
        if (classification == EntityClassification.MONSTER)
        {
            if (Feature.NETHER_BRIDGE.isPositionInsideStructure(this.world, pos))
            {
                return Feature.NETHER_BRIDGE.getSpawnList();
            }

            if (Feature.NETHER_BRIDGE.isPositionInStructure(this.world, pos) && this.world.getBlockState(pos.down()).getBlock() == Blocks.NETHER_BRICKS)
            {
                return Feature.NETHER_BRIDGE.getSpawnList();
            }
        }

        Biome biome = this.world.getBiome(pos);
        BiomeData biomeData = NetherEx.BIOME_DATA_MANAGER.getBiomeData().get(biome.getRegistryName());
        List<Biome.SpawnListEntry> spawns = new ArrayList<>(biome.getSpawns(classification));

        if(biomeData != null)
        {
            spawns.addAll(biomeData.getSpawns(classification));
        }

        return spawns;
    }
}

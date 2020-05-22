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

package logictechcorp.netherex.world.generation;

import logictechcorp.libraryex.world.biome.BiomeData;
import logictechcorp.netherex.NetherEx;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CaveChunkGenerator extends NetherChunkGenerator
{
    public CaveChunkGenerator(World world, BiomeProvider biomeProvider, NetherGenSettings settings)
    {
        super(world, biomeProvider, settings);
    }

    @Override
    public void carve(IChunk chunk, GenerationStage.Carving stage)
    {
        BiomeData biomeData = NetherEx.BIOME_DATA_MANAGER.getBiomeData(this.getBiome(chunk));

        if(biomeData != BiomeData.EMPTY)
        {
            biomeData.carve(stage, chunk, this.seed, this.getSeaLevel());
        }
    }

    @Override
    public void decorate(WorldGenRegion region)
    {
        int chunkX = region.getMainChunkX();
        int chunkZ = region.getMainChunkZ();
        int posX = chunkX * 16;
        int posZ = chunkZ * 16;
        BlockPos pos = new BlockPos(posX, 0, posZ);
        BiomeData biomeData = NetherEx.BIOME_DATA_MANAGER.getBiomeData(this.getBiome(region, pos.add(8, 8, 8)));
        SharedSeedRandom random = new SharedSeedRandom();
        long decorationSeed = random.setDecorationSeed(region.getSeed(), posX, posZ);

        if(biomeData != BiomeData.EMPTY)
        {
            for(GenerationStage.Decoration stage : GenerationStage.Decoration.values())
            {
                biomeData.decorate(stage, this, region, decorationSeed, random, pos);
            }
        }
    }

    @Override
    public boolean hasStructure(Biome biome, Structure<? extends IFeatureConfig> structure)
    {
        return NetherEx.BIOME_DATA_MANAGER.getBiomeData(biome).hasStructure(structure);
    }

    @Override
    public <C extends IFeatureConfig> C getStructureConfig(Biome biome, Structure<C> structure)
    {
        return NetherEx.BIOME_DATA_MANAGER.getBiomeData(biome).getStructureConfig(structure);
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EntityClassification classification, BlockPos pos)
    {
        if(classification == EntityClassification.MONSTER)
        {
            if(Feature.NETHER_BRIDGE.isPositionInsideStructure(this.world, pos))
            {
                return Feature.NETHER_BRIDGE.getSpawnList();
            }

            if(Feature.NETHER_BRIDGE.isPositionInStructure(this.world, pos) && this.world.getBlockState(pos.down()).getBlock() == Blocks.NETHER_BRICKS)
            {
                return Feature.NETHER_BRIDGE.getSpawnList();
            }
        }

        Biome biome = this.world.getBiome(pos);
        BiomeData biomeData = NetherEx.BIOME_DATA_MANAGER.getBiomeData(biome);
        List<Biome.SpawnListEntry> spawns = new ArrayList<>();

        if(biomeData != BiomeData.EMPTY && biomeData.useDefaultEntities())
        {
            spawns.addAll(biome.getSpawns(classification));
        }

        spawns.addAll(biomeData.getSpawns(classification));
        return spawns;
    }
}

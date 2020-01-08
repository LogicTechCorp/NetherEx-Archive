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

package logictechcorp.netherex.world.biome.provider;

import com.google.common.collect.Sets;
import logictechcorp.netherex.world.generation.layer.NetherLayerUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.storage.WorldInfo;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class NetherBiomeProvider extends BiomeProvider
{
    private final Layer genBiomes;
    private final Layer biomeFactoryLayer;

    public NetherBiomeProvider(OverworldBiomeProviderSettings biomeProviderSettings)
    {
        WorldInfo worldInfo = biomeProviderSettings.getWorldInfo();
        OverworldGenSettings generatorSettings = biomeProviderSettings.getGeneratorSettings();
        Layer[] layers = NetherLayerUtil.createLayers(worldInfo.getSeed(), worldInfo.getGenerator(), generatorSettings);
        this.genBiomes = layers[0];
        this.biomeFactoryLayer = layers[1];
    }

    @Override
    public Biome getBiome(int x, int z)
    {
        return this.biomeFactoryLayer.func_215738_a(x, z);
    }

    @Override
    public Biome getBiomeAtFactorFour(int x, int z)
    {
        return this.genBiomes.func_215738_a(x, z);
    }

    @Override
    public Biome[] getBiomes(int x, int z, int width, int length, boolean cacheFlag)
    {
        return this.biomeFactoryLayer.generateBiomes(x, z, width, length);
    }

    @Override
    public Set<Biome> getBiomesInSquare(int centerX, int centerZ, int range)
    {
        int x = centerX - range >> 2;
        int z = centerZ - range >> 2;
        int sizeX = centerX + range >> 2;
        int sizeZ = centerZ + range >> 2;
        int width = sizeX - x + 1;
        int length = sizeZ - z + 1;
        Set<Biome> set = Sets.newHashSet();
        Collections.addAll(set, this.genBiomes.generateBiomes(x, z, width, length));
        return set;
    }

    @Override
    public BlockPos findBiomePosition(int centerX, int centerZ, int range, List<Biome> biomes, Random random)
    {
        int x = centerX - range >> 2;
        int z = centerZ - range >> 2;
        int sizeX = centerX + range >> 2;
        int sizeZ = centerZ + range >> 2;
        int width = sizeX - x + 1;
        int length = sizeZ - z + 1;
        Biome[] localBiomes = this.genBiomes.generateBiomes(x, z, width, length);
        BlockPos pos = null;
        int areaCount = 0;

        for(int area = 0; area < width * length; area++)
        {
            int posX = x + area % width << 2;
            int posZ = z + area / width << 2;

            if(biomes.contains(localBiomes[area]))
            {
                if(pos == null || random.nextInt(areaCount + 1) == 0)
                {
                    pos = new BlockPos(posX, 0, posZ);
                }

                areaCount++;
            }
        }

        return pos;
    }

    @Override
    public boolean hasStructure(Structure<?> structure)
    {
        return true;
    }

    @Override
    public Set<BlockState> getSurfaceBlocks()
    {
        if(this.topBlocksCache.isEmpty())
        {
            this.topBlocksCache.add(Blocks.NETHERRACK.getDefaultState());
        }

        return this.topBlocksCache;
    }
}

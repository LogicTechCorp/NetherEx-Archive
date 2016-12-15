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

package nex.world.biome;

import com.google.common.collect.Lists;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import nex.init.NetherExBiomes;
import nex.world.gen.layer.GenLayerNether;

import java.util.List;
import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class BiomeProviderNether extends BiomeProvider
{
    private final BiomeCache biomeCache;
    private final List<Biome> biomesToSpawnIn;
    private GenLayer genBiomes;
    private GenLayer biomeIndexLayer;

    private BiomeProviderNether()
    {
        biomeCache = new BiomeCache(this);
        biomesToSpawnIn = Lists.newArrayList();
    }

    public BiomeProviderNether(long seed)
    {
        this();
        GenLayer[] genLayers = GenLayerNether.initializeAllBiomeGenerators(seed);
        genBiomes = genLayers[0];
        biomeIndexLayer = genLayers[1];
    }

    @Override
    public List<Biome> getBiomesToSpawnIn()
    {
        return biomesToSpawnIn;
    }

    @Override
    public Biome getBiome(BlockPos pos)
    {
        return getBiome(pos, null);
    }

    @Override
    public Biome getBiome(BlockPos pos, Biome biomeGenBaseIn)
    {
        return biomeCache.getBiome(pos.getX(), pos.getZ(), biomeGenBaseIn);
    }

    @Override
    public float getTemperatureAtHeight(float temperature, int y)
    {
        return temperature;
    }

    @Override
    public Biome[] getBiomesForGeneration(Biome[] biomes, int chunkX, int chunkZ, int width, int height)
    {
        IntCache.resetIntCache();

        if(biomes == null || biomes.length < width * height)
        {
            biomes = new Biome[width * height];
        }

        int[] biomeIds = genBiomes.getInts(chunkX, chunkZ, width, height);

        try
        {
            for(int i = 0; i < width * height; ++i)
            {
                biomes[i] = Biome.getBiome(biomeIds[i], NetherExBiomes.HELL);
            }

            return biomes;
        }
        catch(Throwable throwable)
        {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Invalid biome id");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("RawBiomeBlock");
            crashReportCategory.addCrashSection("biomes[] size", biomes.length);
            crashReportCategory.addCrashSection("x", chunkX);
            crashReportCategory.addCrashSection("z", chunkZ);
            crashReportCategory.addCrashSection("w", width);
            crashReportCategory.addCrashSection("h", height);
            throw new ReportedException(crashReport);
        }
    }

    @Override
    public Biome[] getBiomes(Biome[] biomes, int chunkX, int chunkZ, int width, int depth)
    {
        return getBiomes(biomes, chunkX, chunkZ, width, depth, true);
    }

    @Override
    public Biome[] getBiomes(Biome[] biomes, int chunkX, int chunkZ, int width, int length, boolean cacheFlag)
    {
        IntCache.resetIntCache();

        if(biomes == null || biomes.length < width * length)
        {
            biomes = new Biome[width * length];
        }

        if(cacheFlag && width == 16 && length == 16 && (chunkX & 15) == 0 && (chunkZ & 15) == 0)
        {
            Biome[] cachedBiomes = biomeCache.getCachedBiomes(chunkX, chunkZ);
            System.arraycopy(cachedBiomes, 0, biomes, 0, width * length);
            return biomes;
        }
        else
        {
            int[] biomeIds = biomeIndexLayer.getInts(chunkX, chunkZ, width, length);

            for(int i = 0; i < width * length; ++i)
            {
                biomes[i] = Biome.getBiome(biomeIds[i], NetherExBiomes.HELL);
            }

            return biomes;
        }
    }

    @Override
    public boolean areBiomesViable(int chunkX, int chunkZ, int radius, List<Biome> allowed)
    {
        IntCache.resetIntCache();

        int i = chunkX - radius >> 2;
        int j = chunkZ - radius >> 2;
        int k = chunkX + radius >> 2;
        int l = chunkZ + radius >> 2;
        int i1 = k - i + 1;
        int j1 = l - j + 1;
        int[] biomeIds = genBiomes.getInts(i, j, i1, j1);

        try
        {
            for(int k1 = 0; k1 < i1 * j1; ++k1)
            {
                Biome biome = Biome.getBiome(biomeIds[k1]);

                if(!allowed.contains(biome))
                {
                    return false;
                }
            }

            return true;
        }
        catch(Throwable throwable)
        {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Invalid biome id");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Layer");
            crashReportCategory.addCrashSection("Layer", genBiomes.toString());
            crashReportCategory.addCrashSection("x", chunkX);
            crashReportCategory.addCrashSection("z", chunkZ);
            crashReportCategory.addCrashSection("radius", radius);
            crashReportCategory.addCrashSection("allowed", allowed);
            throw new ReportedException(crashReport);
        }
    }

    @Override
    public BlockPos findBiomePosition(int chunkX, int chunkZ, int range, List<Biome> biomes, Random random)
    {
        IntCache.resetIntCache();

        int i = chunkX - range >> 2;
        int j = chunkZ - range >> 2;
        int k = chunkX + range >> 2;
        int l = chunkZ + range >> 2;
        int i1 = k - i + 1;
        int j1 = l - j + 1;
        int[] biomeIds = genBiomes.getInts(i, j, i1, j1);
        int k1 = 0;

        BlockPos blockPos = null;

        for(int l1 = 0; l1 < i1 * j1; ++l1)
        {
            int i2 = i + l1 % i1 << 2;
            int j2 = j + l1 / i1 << 2;
            Biome biome = Biome.getBiome(biomeIds[l1]);

            if(biomes.contains(biome) && (blockPos == null || random.nextInt(k1 + 1) == 0))
            {
                blockPos = new BlockPos(i2, 0, j2);
                ++k1;
            }
        }

        return blockPos;
    }

    @Override
    public void cleanupCache()
    {
        biomeCache.cleanupCache();
    }
}

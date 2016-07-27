package nex.world.biome;

import com.google.common.collect.Lists;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Biomes;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import nex.registry.ModBiomes;
import nex.world.gen.layer.GenLayerNether;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BiomeProviderNether extends BiomeProvider
{
    private final BiomeCache biomeCache;
    private final List<Biome> biomesToSpawnIn;
    private GenLayer genBiomes;
    private GenLayer biomeIndexLayer;

    protected BiomeProviderNether()
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
    public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height)
    {
        IntCache.resetIntCache();

        if(biomes == null || biomes.length < width * height)
        {
            biomes = new Biome[width * height];
        }

        int[] biomeIDs = genBiomes.getInts(x, z, width, height);

        try
        {
            for(int i = 0; i < width * height; ++i)
            {
                biomes[i] = Biome.getBiome(biomeIDs[i], Biomes.DEFAULT);
            }

            return biomes;
        }
        catch(Throwable throwable)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("RawBiomeBlock");
            crashreportcategory.addCrashSection("biomes[] size", biomes.length);
            crashreportcategory.addCrashSection("x", x);
            crashreportcategory.addCrashSection("z", z);
            crashreportcategory.addCrashSection("w", width);
            crashreportcategory.addCrashSection("h", height);
            throw new ReportedException(crashreport);
        }
    }

    @Override
    public Biome[] getBiomes(@Nullable Biome[] oldBiomeList, int x, int z, int width, int depth)
    {
        return getBiomes(oldBiomeList, x, z, width, depth, true);
    }

    @Override
    public Biome[] getBiomes(@Nullable Biome[] listToReuse, int x, int z, int width, int length, boolean cacheFlag)
    {
        IntCache.resetIntCache();

        if(listToReuse == null || listToReuse.length < width * length)
        {
            listToReuse = new Biome[width * length];
        }

        if(cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (z & 15) == 0)
        {
            Biome[] biomes = biomeCache.getCachedBiomes(x, z);
            System.arraycopy(biomes, 0, listToReuse, 0, width * length);
            return listToReuse;
        }
        else
        {
            int[] biomeIDs = biomeIndexLayer.getInts(x, z, width, length);

            for(int i = 0; i < width * length; ++i)
            {
                listToReuse[i] = Biome.getBiome(biomeIDs[i], ModBiomes.HELL);
            }

            return listToReuse;
        }
    }

    @Override
    public boolean areBiomesViable(int x, int z, int radius, List<Biome> allowed)
    {
        IntCache.resetIntCache();

        int i = x - radius >> 2;
        int j = z - radius >> 2;
        int k = x + radius >> 2;
        int l = z + radius >> 2;
        int i1 = k - i + 1;
        int j1 = l - j + 1;
        int[] biomeIDs = genBiomes.getInts(i, j, i1, j1);

        try
        {
            for(int k1 = 0; k1 < i1 * j1; ++k1)
            {
                Biome biome = Biome.getBiome(biomeIDs[k1]);

                if(!allowed.contains(biome))
                {
                    return false;
                }
            }

            return true;
        }
        catch(Throwable throwable)
        {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Layer");
            crashreportcategory.addCrashSection("Layer", genBiomes.toString());
            crashreportcategory.addCrashSection("x", x);
            crashreportcategory.addCrashSection("z", z);
            crashreportcategory.addCrashSection("radius", radius);
            crashreportcategory.addCrashSection("allowed", allowed);
            throw new ReportedException(crashreport);
        }
    }

    @Override
    public BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random random)
    {
        IntCache.resetIntCache();

        int i = x - range >> 2;
        int j = z - range >> 2;
        int k = x + range >> 2;
        int l = z + range >> 2;
        int i1 = k - i + 1;
        int j1 = l - j + 1;
        int[] biomeIDs = genBiomes.getInts(i, j, i1, j1);
        int k1 = 0;

        BlockPos blockpos = null;

        for(int l1 = 0; l1 < i1 * j1; ++l1)
        {
            int i2 = i + l1 % i1 << 2;
            int j2 = j + l1 / i1 << 2;
            Biome biome = Biome.getBiome(biomeIDs[l1]);

            if(biomes.contains(biome) && (blockpos == null || random.nextInt(k1 + 1) == 0))
            {
                blockpos = new BlockPos(i2, 0, j2);
                ++k1;
            }
        }

        return blockpos;
    }

    @Override
    public void cleanupCache()
    {
        biomeCache.cleanupCache();
    }
}

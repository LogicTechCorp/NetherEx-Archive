/*
 * LibraryEx
 * Copyright (c) 2017-2019 by LogicTechCorp
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

package logictechcorp.netherex.world.biome.design;

import logictechcorp.netherex.world.generation.layer.GenLayerNetherBiome;
import logictechcorp.netherex.world.generation.layer.GenLayerNetherSubBiome;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.init.Biomes;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.*;

public class BiomeProviderNetherEx extends BiomeProvider
{
    public BiomeProviderNetherEx(World world)
    {
        super();
        GenLayer[] genLayers = this.createGenLayers(world.getWorldType(), world.getSeed());
        this.genBiomes = genLayers[0];
        this.biomeIndexLayer = genLayers[1];
    }

    @Override
    public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height)
    {
        IntCache.resetIntCache();

        if(biomes == null || biomes.length < width * height)
        {
            biomes = new Biome[width * height];
        }

        int[] biomeIds = this.genBiomes.getInts(x, z, width, height);

        try
        {
            for(int i = 0; i < width * height; i++)
            {
                biomes[i] = Biome.getBiome(biomeIds[i], Biomes.HELL);
            }

            return biomes;
        }
        catch(Throwable throwable)
        {
            CrashReport crashReport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("RawBiomeBlock");
            crashReportCategory.addCrashSection("biomes[] size", biomes.length);
            crashReportCategory.addCrashSection("x", x);
            crashReportCategory.addCrashSection("z", z);
            crashReportCategory.addCrashSection("w", width);
            crashReportCategory.addCrashSection("h", height);
            throw new ReportedException(crashReport);
        }
    }

    private GenLayer[] createGenLayers(WorldType worldType, long worldSeed)
    {
        GenLayer baseLayer = new GenLayerIsland(1L);
        GenLayer biomeLayer = new GenLayerNetherBiome(200L, baseLayer);
        GenLayer subBiomeLayer = new GenLayerRiverInit(200L, biomeLayer);
        biomeLayer = GenLayerZoom.magnify(1000L, biomeLayer, 2);
        subBiomeLayer = GenLayerZoom.magnify(1000L, subBiomeLayer, 2);
        baseLayer = new GenLayerNetherSubBiome(1000L, biomeLayer, subBiomeLayer);
        baseLayer = GenLayerZoom.magnify(1000L, baseLayer, GenLayer.getModdedBiomeSize(worldType, 3));
        baseLayer = new GenLayerSmooth(1000L, baseLayer);
        GenLayer zoomedLayer = new GenLayerVoronoiZoom(10L, baseLayer);
        baseLayer.initWorldGenSeed(worldSeed);
        zoomedLayer.initWorldGenSeed(worldSeed);
        return new GenLayer[]{baseLayer, zoomedLayer};
    }
}

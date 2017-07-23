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

package nex.world.biome;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import nex.NetherEx;
import nex.util.FileUtil;
import nex.world.gen.GenerationStage;
import nex.world.gen.feature.BiomeFeature;
import nex.world.gen.layer.GenLayerNetherEx;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class NetherBiomeManager
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherBiomeManager");

    public static void postInit(File directory)
    {
        copyBiomeConfigsToConfigDirectory(directory);
        parseBiomeConfigs(directory);
    }

    private static void copyBiomeConfigsToConfigDirectory(File directory)
    {
        try
        {
            if(!directory.exists())
            {
                directory.mkdir();
            }

            LOGGER.info("Copying the Biome Configs Directory to the config folder.");

            if(NetherEx.IS_DEV_ENV)
            {
                FileUtils.copyDirectory(new File(NetherEx.class.getResource("/assets/nex/biome_configs").getFile()), directory);
            }
            else
            {
                FileUtil.extractFromJar("/assets/nex/biome_configs", directory.getPath());
            }
        }
        catch(IOException e)
        {
            LOGGER.fatal("The attempt to copy the Biome Configs Directory to the config folder was unsuccessful.");
            LOGGER.fatal(e);
        }
    }

    private static void parseBiomeConfigs(File directory)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        Path directoryPath = directory.toPath();

        try
        {
            Iterator<Path> pathIter = Files.walk(directoryPath).iterator();

            while(pathIter.hasNext())
            {
                Path configPath = pathIter.next();

                if(FilenameUtils.getExtension(configPath.toString()).equals("json"))
                {
                    BufferedReader reader = Files.newBufferedReader(configPath);
                    parseBiomeConfig(JsonUtils.fromJson(gson, reader, JsonObject.class), configPath.toString());
                    IOUtils.closeQuietly(reader);
                }
                else if(!configPath.toFile().isDirectory())
                {
                    LOGGER.warn("Skipping file located at, " + configPath.toString() + ", as it is not a json file.");
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void parseBiomeConfig(JsonObject config, String configPath)
    {
        String configType = JsonUtils.getString(config, "configType");

        if(configType.equalsIgnoreCase("biome"))
        {
            NetherBiome netherBiome = NetherBiome.deserialize(config);
            NetherBiomeClimate netherBiomeClimate = NetherBiomeClimate.getFromString(JsonUtils.getString(config, "climate"));

            if(netherBiome != null)
            {
                netherBiomeClimate.addBiome(netherBiome);
                LOGGER.info("Added the " + netherBiome.getBiome().getRegistryName().getResourcePath() + " biome, from " + netherBiome.getBiome().getRegistryName().getResourceDomain() + ", to the Nether.");
            }
        }
        else if(configType.equalsIgnoreCase("feature"))
        {
            BiomeFeature biomeFeature = BiomeFeature.Factory.deserialize(config);
            GenerationStage generationStage = GenerationStage.getFromString(JsonUtils.getString(config, "stage"));

            if(biomeFeature != null)
            {
                Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(JsonUtils.getString(config, "biome")));

                if(biome != null)
                {
                    generationStage.addBiomeFeature(biome, biomeFeature);
                }
            }
        }
        else
        {
            LOGGER.warn("The file located at, " + configPath + ", contains an incorrect type of " + configType);
        }
    }

    public static List<NetherBiomeEntry> getAllBiomeEntries()
    {
        List<NetherBiomeEntry> biomes = Lists.newArrayList();

        for(NetherBiomeClimate biomeClimate : NetherBiomeClimate.values())
        {
            biomes.addAll(biomeClimate.getBiomeEntries());
        }

        return biomes;
    }

    public static Biome getRandomBiome(List<NetherBiomeEntry> biomeEntryList, Random rand)
    {
        return WeightedRandom.getRandomItem(biomeEntryList, rand.nextInt(WeightedRandom.getTotalWeight(biomeEntryList))).getBiome();
    }

    public static Biome getRandomBiome(List<NetherBiomeEntry> biomeEntryList, GenLayerNetherEx layer)
    {
        return WeightedRandom.getRandomItem(biomeEntryList, layer.nextInt(WeightedRandom.getTotalWeight(biomeEntryList))).getBiome();
    }

    public static IBlockState getBiomeFloorTopBlock(Biome biome)
    {
        Map<Biome, NetherBiomeEntry> biomeEntryMap = NetherBiomeClimate.getFromBiome(biome).getBiomeEntryMap();

        if(biomeEntryMap.containsKey(biome))
        {
            return biomeEntryMap.get(biome).getFloorTopBlock();
        }

        return biome.topBlock;
    }

    public static IBlockState getBiomeFloorFillerBlock(Biome biome)
    {
        Map<Biome, NetherBiomeEntry> biomeEntryMap = NetherBiomeClimate.getFromBiome(biome).getBiomeEntryMap();

        if(biomeEntryMap.containsKey(biome))
        {
            return biomeEntryMap.get(biome).getFloorFillerBlock();
        }

        return biome.fillerBlock;
    }

    public static IBlockState getBiomeWallBlock(Biome biome)
    {
        Map<Biome, NetherBiomeEntry> biomeEntryMap = NetherBiomeClimate.getFromBiome(biome).getBiomeEntryMap();

        if(biomeEntryMap.containsKey(biome))
        {
            return biomeEntryMap.get(biome).getWallBlock();
        }

        return biome.fillerBlock;
    }

    public static IBlockState getBiomeRoofBottomBlock(Biome biome)
    {
        Map<Biome, NetherBiomeEntry> biomeEntryMap = NetherBiomeClimate.getFromBiome(biome).getBiomeEntryMap();

        if(biomeEntryMap.containsKey(biome))
        {
            return biomeEntryMap.get(biome).getRoofBottomBlock();
        }

        return biome.fillerBlock;
    }

    public static IBlockState getBiomeRoofFillerBlock(Biome biome)
    {
        Map<Biome, NetherBiomeEntry> biomeEntryMap = NetherBiomeClimate.getFromBiome(biome).getBiomeEntryMap();

        if(biomeEntryMap.containsKey(biome))
        {
            return biomeEntryMap.get(biome).getRoofFillerBlock();
        }

        return biome.fillerBlock;
    }

    public static IBlockState getBiomeOceanBlock(Biome biome)
    {
        Map<Biome, NetherBiomeEntry> biomeEntryMap = NetherBiomeClimate.getFromBiome(biome).getBiomeEntryMap();

        if(biomeEntryMap.containsKey(biome))
        {
            return biomeEntryMap.get(biome).getOceanBlock();
        }

        return Blocks.LAVA.getDefaultState();
    }
}

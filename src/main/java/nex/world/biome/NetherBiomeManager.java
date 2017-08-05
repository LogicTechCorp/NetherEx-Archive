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
import com.google.gson.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import nex.world.gen.GenerationStage;
import nex.world.gen.feature.EnhancedGenerator;
import nex.world.gen.layer.GenLayerNetherEx;
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

@SuppressWarnings("ConstantConditions")
public class NetherBiomeManager
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherBiomeManager");

    public static void parseBiomeConfigs(File directory)
    {
        if(!directory.exists())
        {
            directory.mkdir();
        }

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
                    parseBiomeConfig(JsonUtils.fromJson(gson, reader, JsonObject.class));
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

    private static void parseBiomeConfig(JsonObject config)
    {
        EnhancedBiome enhancedBiome = EnhancedBiome.deserialize(config);

        if(enhancedBiome != null)
        {
            Biome biome = enhancedBiome.getBiome();

            EnhancedBiomeClimate.getFromString(JsonUtils.getString(config, "climate")).addBiome(enhancedBiome);
            LOGGER.info("Added the " + biome.getRegistryName().getResourcePath() + " biome, from " + biome.getRegistryName().getResourceDomain() + ", to the Nether.");

            JsonArray entityConfigs = JsonUtils.getJsonArray(config, "entities", new JsonArray());

            if(entityConfigs.size() > 0)
            {
                for(JsonElement entityConfig : entityConfigs)
                {
                    BiomeEntity entity = BiomeEntity.deserialize(entityConfig.getAsJsonObject());
                    biome.getSpawnableList(entity.getCreatureType()).add(entity.getSpawnListEntry());
                }
            }

            JsonArray generatorConfigs = JsonUtils.getJsonArray(config, "generators", new JsonArray());

            if(generatorConfigs.size() > 0)
            {
                for(JsonElement generatorConfig : generatorConfigs)
                {
                    EnhancedGenerator generator = EnhancedGenerator.deserialize(generatorConfig.getAsJsonObject());

                    if(generator != null)
                    {
                        GenerationStage.getFromString(JsonUtils.getString(generatorConfig.getAsJsonObject(), "generationStage")).addGenerator(biome, generator);
                    }
                }
            }
        }
    }

    public static void clearBiomes()
    {
        for(EnhancedBiomeClimate enhancedBiomeClimate : EnhancedBiomeClimate.values())
        {
            for(Biome biome : enhancedBiomeClimate.getBiomeEntryMap().keySet())
            {
                enhancedBiomeClimate.removeBiome(biome);
            }
        }
    }

    public static Biome getRandomBiome(GenLayerNetherEx layer)
    {
        List<EnhancedBiomeEntry> biomeEntryList = Lists.newArrayList();

        for(EnhancedBiomeClimate enhancedBiomeClimate : EnhancedBiomeClimate.values())
        {
            biomeEntryList.addAll(enhancedBiomeClimate.getBiomeEntryMap().values());
        }

        return WeightedRandom.getRandomItem(biomeEntryList, layer.nextInt(WeightedRandom.getTotalWeight(biomeEntryList))).getBiome();
    }

    public static List<EnhancedGenerator> getBiomeGenerators(Biome biome, GenerationStage generationStage)
    {
        Map<Biome, List<EnhancedGenerator>> biomeGeneratorMap = generationStage.getBiomeGeneratorMap();

        if(biomeGeneratorMap.containsKey(biome))
        {
            return biomeGeneratorMap.get(biome);
        }

        return Lists.newArrayList();
    }

    public static IBlockState getBiomeFloorTopBlock(Biome biome)
    {
        Map<Biome, EnhancedBiomeEntry> biomeEntryMap = EnhancedBiomeClimate.getFromBiome(biome).getBiomeEntryMap();

        if(biomeEntryMap.containsKey(biome))
        {
            return biomeEntryMap.get(biome).getFloorTopBlock();
        }

        return biome.topBlock;
    }

    public static IBlockState getBiomeFloorFillerBlock(Biome biome)
    {
        Map<Biome, EnhancedBiomeEntry> biomeEntryMap = EnhancedBiomeClimate.getFromBiome(biome).getBiomeEntryMap();

        if(biomeEntryMap.containsKey(biome))
        {
            return biomeEntryMap.get(biome).getFloorFillerBlock();
        }

        return biome.fillerBlock;
    }

    public static IBlockState getBiomeWallBlock(Biome biome)
    {
        Map<Biome, EnhancedBiomeEntry> biomeEntryMap = EnhancedBiomeClimate.getFromBiome(biome).getBiomeEntryMap();

        if(biomeEntryMap.containsKey(biome))
        {
            return biomeEntryMap.get(biome).getWallBlock();
        }

        return biome.fillerBlock;
    }

    public static IBlockState getBiomeRoofBottomBlock(Biome biome)
    {
        Map<Biome, EnhancedBiomeEntry> biomeEntryMap = EnhancedBiomeClimate.getFromBiome(biome).getBiomeEntryMap();

        if(biomeEntryMap.containsKey(biome))
        {
            return biomeEntryMap.get(biome).getRoofBottomBlock();
        }

        return biome.fillerBlock;
    }

    public static IBlockState getBiomeRoofFillerBlock(Biome biome)
    {
        Map<Biome, EnhancedBiomeEntry> biomeEntryMap = EnhancedBiomeClimate.getFromBiome(biome).getBiomeEntryMap();

        if(biomeEntryMap.containsKey(biome))
        {
            return biomeEntryMap.get(biome).getRoofFillerBlock();
        }

        return biome.fillerBlock;
    }

    public static IBlockState getBiomeOceanBlock(Biome biome)
    {
        Map<Biome, EnhancedBiomeEntry> biomeEntryMap = EnhancedBiomeClimate.getFromBiome(biome).getBiomeEntryMap();

        if(biomeEntryMap.containsKey(biome))
        {
            return biomeEntryMap.get(biome).getOceanBlock();
        }

        return Blocks.LAVA.getDefaultState();
    }
}

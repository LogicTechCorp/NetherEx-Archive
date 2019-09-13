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

package logictechcorp.netherex.world.biome;

import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.json.JsonFormat;
import logictechcorp.libraryex.LibraryEx;
import logictechcorp.libraryex.api.world.biome.data.IBiomeData;
import logictechcorp.libraryex.api.world.biome.data.IBiomeDataRegistry;
import logictechcorp.libraryex.utility.FileHelper;
import logictechcorp.libraryex.world.biome.data.BiomeData;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.api.NetherExAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public final class NetherBiomeDataConfigManager
{
    private NetherBiomeDataConfigManager()
    {
    }

    public static void readBiomeDataConfigs()
    {
        Path path = new File(LibraryEx.CONFIG_DIRECTORY, NetherEx.MOD_ID + "/biomes").toPath();
        NetherEx.LOGGER.info("Reading Nether biome data configs from disk.");

        try
        {
            Files.createDirectories(path);
            Iterator<Path> pathIter = Files.walk(path).iterator();

            while(pathIter.hasNext())
            {
                File configFile = pathIter.next().toFile();

                if(FileHelper.getFileExtension(configFile).equals("json"))
                {
                    String fileText = FileUtils.readFileToString(configFile, Charset.defaultCharset()).trim();

                    if(fileText.isEmpty() || !fileText.startsWith("{") || !fileText.endsWith("}"))
                    {
                        String filePath = configFile.getPath();
                        String fileBackupPath = filePath + "_backup";
                        Files.move(configFile.toPath(), Paths.get(fileBackupPath));
                        NetherEx.LOGGER.warn("The biome config at {} was invalid and was backed up as {}.", filePath, fileBackupPath);
                        continue;
                    }

                    FileConfig config = FileConfig.builder(configFile, JsonFormat.fancyInstance()).preserveInsertionOrder().build();
                    config.load();

                    Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(config.get("biome")));

                    if(biome != null)
                    {
                        IBiomeDataRegistry biomeDataRegistry = NetherExAPI.getInstance().getBiomeDataRegistry();
                        IBiomeData biomeData;

                        if(biomeDataRegistry.hasBiomeData(biome))
                        {
                            biomeData = biomeDataRegistry.getBiomeData(biome);
                        }
                        else
                        {
                            biomeData = new BiomeData(biome.getRegistryName(), 10, true, false, true);
                        }

                        biomeData.readFromConfig(biomeDataRegistry, config);
                        biomeDataRegistry.registerBiomeData(biomeData);
                    }

                    config.save();
                    config.close();
                }
                else if(!configFile.isDirectory() && !FileHelper.getFileExtension(configFile).equals("json_backup"))
                {
                    NetherEx.LOGGER.warn("Skipping file located at, {}, as it is not a json file.", configFile.getPath());
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void writeBiomeDataConfigs()
    {
        NetherEx.LOGGER.info("Writing Nether biome data configs to disk.");
        IBiomeDataRegistry biomeDataRegistry = NetherExAPI.getInstance().getBiomeDataRegistry();

        for(IBiomeData biomeData : biomeDataRegistry.getBiomeData().values())
        {
            File configFile = new File(LibraryEx.CONFIG_DIRECTORY, NetherEx.MOD_ID + "/biomes/" + biomeData.getBiome().getRegistryName().toString().replace(":", "/") + ".json");
            FileConfig config = FileConfig.builder(configFile, JsonFormat.fancyInstance()).preserveInsertionOrder().build();

            if(!configFile.exists())
            {
                try
                {
                    Files.createDirectories(configFile.getParentFile().toPath());
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                config.load();
            }

            biomeData.writeToConfig(config);
            config.save();
            config.close();
        }
    }
}

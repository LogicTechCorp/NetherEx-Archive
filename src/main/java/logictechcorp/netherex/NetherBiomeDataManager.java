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

package logictechcorp.netherex;

import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.json.JsonFormat;
import logictechcorp.libraryex.api.world.biome.data.IBiomeData;
import logictechcorp.libraryex.api.world.biome.data.IBiomeDataAPI;
import logictechcorp.libraryex.api.world.biome.data.IBiomeDataManager;
import logictechcorp.libraryex.api.world.biome.data.IBiomeDataRegistry;
import logictechcorp.libraryex.utility.FileHelper;
import logictechcorp.libraryex.utility.WorldHelper;
import logictechcorp.libraryex.world.biome.data.BiomeData;
import logictechcorp.netherex.api.NetherExAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

final class NetherBiomeDataManager implements IBiomeDataManager
{
    static final IBiomeDataManager INSTANCE = new NetherBiomeDataManager();
    private static boolean readConfigs = false;
    private final Marker marker = MarkerManager.getMarker("NetherBiomeDataManager");

    private NetherBiomeDataManager()
    {
    }

    @Override
    public void readBiomeDataConfigs(WorldEvent.Load event)
    {
        World world = event.getWorld();
        int dimensionId = world.provider.getDimension();

        if(dimensionId != DimensionType.OVERWORLD.getId() && dimensionId != DimensionType.NETHER.getId())
        {
            return;
        }

        if(world.provider.getDimension() == DimensionType.OVERWORLD.getId())
        {
            NetherBiomeDataManager.readConfigs = true;
            return;
        }

        if(NetherBiomeDataManager.readConfigs)
        {
            Path path = new File(WorldHelper.getSaveDirectory(world), "/config/" + NetherEx.MOD_ID + "/biomes").toPath();
            NetherEx.LOGGER.info(this.marker, "Reading Nether biome data configs from disk.");

            try
            {
                Files.createDirectories(path);
                Iterator<Path> pathIter = Files.walk(path).iterator();

                while(pathIter.hasNext())
                {
                    File configFile = pathIter.next().toFile();

                    if(FileHelper.getFileExtension(configFile).equals("json"))
                    {
                        FileConfig config = FileConfig.builder(configFile, JsonFormat.fancyInstance()).preserveInsertionOrder().build();
                        config.load();

                        Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(config.get("biome")));

                        if(biome != null)
                        {
                            IBiomeDataAPI biomeDataAPI = NetherExAPI.getInstance();
                            IBiomeDataRegistry biomeDataRegistry = biomeDataAPI.getBiomeDataRegistry();
                            IBiomeData biomeData;

                            if(biomeDataRegistry.hasBiomeData(biome))
                            {
                                biomeData = biomeDataRegistry.getBiomeData(biome);
                            }
                            else
                            {
                                biomeData = new BiomeData(biome.getRegistryName());
                            }

                            biomeData.readFromConfig(biomeDataAPI, config);
                            biomeDataRegistry.registerBiomeData(biomeData);
                        }

                        config.save();
                        config.close();
                    }
                    else if(!configFile.isDirectory())
                    {
                        NetherEx.LOGGER.warn("Skipping file located at, {}, as it is not a json file.", configFile.getPath());
                    }
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            NetherBiomeDataManager.readConfigs = false;
        }
    }

    @Override
    public void writeBiomeDataConfigs(WorldEvent.Unload event)
    {
        World world = event.getWorld();

        if(world.provider.getDimension() == DimensionType.OVERWORLD.getId())
        {
            NetherEx.LOGGER.info(this.marker, "Writing Nether biome data configs to disk.");

            IBiomeDataAPI biomeDataAPI = NetherExAPI.getInstance();

            for(IBiomeData biomeData : biomeDataAPI.getBiomeDataRegistry().getBiomeData().values())
            {
                File configFile = new File(WorldHelper.getSaveDirectory(event.getWorld()), "config/" + NetherEx.MOD_ID + "/" + biomeData.getRelativeSaveFile());
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
                biomeData.resetToDefaults(biomeDataAPI);
            }
        }
    }
}

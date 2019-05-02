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
import logictechcorp.libraryex.api.IBiomeDataAPI;
import logictechcorp.libraryex.config.ModJsonConfigFormat;
import logictechcorp.libraryex.utility.FileHelper;
import logictechcorp.libraryex.utility.WorldHelper;
import logictechcorp.libraryex.world.biome.data.iface.IBiomeData;
import logictechcorp.libraryex.world.biome.data.iface.IBiomeDataConfigurable;
import logictechcorp.libraryex.world.biome.data.iface.IBiomeDataManager;
import logictechcorp.libraryex.world.biome.data.iface.IBiomeDataRegistry;
import logictechcorp.libraryex.world.biome.data.impl.BiomeDataConfigurable;
import logictechcorp.netherex.api.NetherExAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.DimensionManager;
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
            if(DimensionManager.getWorld(DimensionType.NETHER.getId()).getWorldInfo().getTerrainType() == NetherEx.WORLD_TYPE)
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
                            FileConfig config = FileConfig.of(configFile, ModJsonConfigFormat.instance());
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

                                    if(!(biomeData instanceof IBiomeDataConfigurable))
                                    {
                                        continue;
                                    }

                                    ((IBiomeDataConfigurable) biomeData).readFromConfig(biomeDataAPI, config);
                                }
                                else
                                {
                                    biomeData = new BiomeDataConfigurable(biome.getRegistryName());
                                    ((IBiomeDataConfigurable) biomeData).readFromConfig(biomeDataAPI, config);
                                }

                                if(biomeData.generateBiome())
                                {
                                    biomeDataRegistry.registerBiomeData(biomeData);
                                }
                                else
                                {
                                    biomeDataRegistry.unregisterBiomeData(biome);
                                }
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
                if(!(biomeData instanceof IBiomeDataConfigurable))
                {
                    continue;
                }

                IBiomeDataConfigurable biomeDataConfigurable = (IBiomeDataConfigurable) biomeData;
                File configFile = new File(WorldHelper.getSaveDirectory(event.getWorld()), "config/" + NetherEx.MOD_ID + "/" + biomeDataConfigurable.getRelativeSaveFile());
                FileConfig fileConfig = FileConfig.of(configFile, ModJsonConfigFormat.instance());

                if(!configFile.getParentFile().mkdirs() && configFile.exists() || configFile.exists())
                {
                    fileConfig.load();
                    biomeDataConfigurable.readFromConfig(biomeDataAPI, fileConfig);
                }

                biomeDataConfigurable.writeToConfig(fileConfig);
                fileConfig.save();
                fileConfig.close();
            }
        }
    }
}

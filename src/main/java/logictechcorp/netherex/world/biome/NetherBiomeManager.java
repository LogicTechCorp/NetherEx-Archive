/*
 * NetherEx
 * Copyright (c) 2016-2018 by MineEx
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
import logictechcorp.libraryex.LibraryEx;
import logictechcorp.libraryex.config.IConfigData;
import logictechcorp.libraryex.util.ConfigHelper;
import logictechcorp.libraryex.util.FileHelper;
import logictechcorp.libraryex.world.biome.DimensionBiomeManager;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.world.biome.wrapper.INetherBiomeWrapper;
import logictechcorp.netherex.world.biome.wrapper.NetherBiomeWrapper;
import logictechcorp.netherex.world.biome.wrapper.NetherBiomeWrapperHell;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;

public class NetherBiomeManager extends DimensionBiomeManager<INetherBiomeWrapper>
{
    public static final NetherBiomeManager INSTANCE = new NetherBiomeManager();

    private NetherBiomeManager()
    {
        this.setupDefaultBiomes();
    }

    private void setupDefaultBiomes()
    {
        this.addBiome(new NetherBiomeWrapperHell());
        this.addConfigPath(LibraryEx.CONFIG_DIRECTORY.getPath() + "/NetherEx/Biomes");
    }

    public void readBiomeConfigs()
    {
        NetherEx.LOGGER.info("Reading Nether biome configs.");

        try
        {
            for(Path path : this.configPaths)
            {
                Files.createDirectories(path);
                Iterator<Path> pathIter = Files.walk(path).iterator();

                while(pathIter.hasNext())
                {
                    File configFile = pathIter.next().toFile();

                    if(FileHelper.getFileExtension(configFile).equals("json"))
                    {
                        FileConfig config = ConfigHelper.newConfig(configFile, true, true, true);
                        config.load();

                        Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation("biome"));

                        if(biome != null && config.getOrElse("enabled", true))
                        {
                            int biomeId = Biome.getIdForBiome(biome);
                            INetherBiomeWrapper wrapper;

                            if(this.moddedBiomes.containsKey(biomeId) && this.moddedBiomes.get(biomeId) instanceof IConfigData)
                            {
                                wrapper = this.moddedBiomes.get(biomeId);
                                ((IConfigData) wrapper).deserialize(config);

                                if(wrapper.isEnabled())
                                {
                                    this.moddedBiomes.put(biomeId, wrapper);
                                    this.biomeEntries.add(new BiomeManager.BiomeEntry(biome, wrapper.getWeight()));
                                }
                            }
                            else
                            {
                                wrapper = new NetherBiomeWrapper();
                                ((NetherBiomeWrapper) wrapper).deserialize(config);

                                if(wrapper.isEnabled())
                                {
                                    this.playerBiomes.put(biomeId, wrapper);
                                    this.biomeEntries.add(new BiomeManager.BiomeEntry(biome, wrapper.getWeight()));
                                }
                            }
                        }

                        config.close();
                    }
                    else if(!configFile.isDirectory())
                    {
                        NetherEx.LOGGER.warn("Skipping file located at, {}, as it is not a json file.", configFile.getPath());
                    }
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void writeBiomeConfigs()
    {
        NetherEx.LOGGER.info("Writing Nether biome configs.");

        for(Map.Entry<Integer, INetherBiomeWrapper> entry : this.moddedBiomes.entrySet())
        {
            if(entry.getValue() instanceof IConfigData)
            {
                FileConfig config = ((IConfigData) entry.getValue()).serialize();
                config.save();
                config.close();
            }
        }

        for(Map.Entry<Integer, INetherBiomeWrapper> entry : this.playerBiomes.entrySet())
        {
            if(entry.getValue() instanceof IConfigData)
            {
                FileConfig config = ((IConfigData) entry.getValue()).serialize();
                config.save();
                config.close();
            }
        }
    }
}

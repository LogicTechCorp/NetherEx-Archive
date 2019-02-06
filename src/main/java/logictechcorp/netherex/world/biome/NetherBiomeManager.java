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

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.FileConfig;
import logictechcorp.libraryex.utility.FileHelper;
import logictechcorp.libraryex.utility.WorldHelper;
import logictechcorp.libraryex.world.biome.BiomeInfo;
import logictechcorp.libraryex.world.biome.DimensionBiomeManager;
import logictechcorp.netherex.NetherEx;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class NetherBiomeManager extends DimensionBiomeManager
{
    public static final NetherBiomeManager INSTANCE = new NetherBiomeManager();

    private NetherBiomeManager()
    {
        this.setupDefaultBiomes();
    }

    private void setupDefaultBiomes()
    {
        this.addBiome(NetherBiomeInfoHell.INSTANCE);
    }

    @Override
    public void readBiomeInfoFromConfigs()
    {
        if(DimensionManager.getWorld(DimensionType.NETHER.getId()).getWorldInfo().getTerrainType() == NetherEx.WORLD_TYPE)
        {
            Path path = new File(WorldHelper.getSaveFile(), "/config/NetherEx/Biomes").toPath();
            NetherEx.LOGGER.info("Reading Nether biome configs.");

            try
            {
                Files.createDirectories(path);
                Iterator<Path> pathIter = Files.walk(path).iterator();

                while(pathIter.hasNext())
                {
                    File configFile = pathIter.next().toFile();

                    if(FileHelper.getFileExtension(configFile).equals("json"))
                    {
                        FileConfig config = FileConfig.builder(configFile).autoreload().autosave().sync().build();
                        config.load();

                        Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(config.get("biome")));

                        if(biome != null)
                        {
                            BiomeInfo info = this.getBiomeInfo(biome);

                            if(info == null)
                            {
                                info = new NetherBiomeInfo();
                            }

                            info.setupFromConfig(config);

                            if(info.isEnabled())
                            {
                                this.addBiome(info);
                            }
                            else
                            {
                                this.removeBiome(info);
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
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void writeBiomeInfoToConfigs()
    {
        if(DimensionManager.getWorld(DimensionType.NETHER.getId()).getWorldInfo().getTerrainType() == NetherEx.WORLD_TYPE)
        {
            NetherEx.LOGGER.info("Writing Nether biome configs.");

            for(BiomeInfo info : this.getAllBiomeInfo())
            {
                File configFile = this.getBiomeInfoSaveFile(info);
                FileConfig fileConfig = FileConfig.of(configFile);

                if(!configFile.exists() && configFile.getParentFile().mkdirs() || !configFile.exists())
                {
                    Config config = info.getAsConfig();
                    config.entrySet().forEach(entry -> fileConfig.add(entry.getKey(), entry.getValue()));
                }
                else
                {
                    fileConfig.load();
                    info.setupFromConfig(fileConfig);
                }

                fileConfig.save();
            }
        }
    }

    @Override
    public File getBiomeInfoSaveFile(BiomeInfo biomeInfo)
    {
        return new File(WorldHelper.getSaveFile(), "/config/NetherEx/Biomes/" + biomeInfo.getFileName());
    }
}

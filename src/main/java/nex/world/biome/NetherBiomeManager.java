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

package nex.world.biome;

import com.electronwill.nightconfig.core.file.FileConfig;
import lex.LibEx;
import lex.util.ConfigHelper;
import lex.util.FileHelper;
import lex.world.biome.BiomeRegistry;
import lex.world.biome.ISerializableBiomeWrapper;
import lex.world.gen.GenerationStage;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import nex.NetherEx;
import nex.handler.ConfigHandler;
import nex.init.NetherExBiomes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;

public class NetherBiomeManager extends BiomeRegistry<INetherBiomeWrapper>
{
    public static final NetherBiomeManager INSTANCE = new NetherBiomeManager();

    private NetherBiomeManager()
    {
        this.setupDefaultBiomes();
    }

    private void setupDefaultBiomes()
    {
        this.addBiome(new BiomeWrapperHell());
        this.addBiome(NetherExBiomes.RUTHLESS_SANDS.getWrapper());
        this.addBiome(NetherExBiomes.FUNGI_FOREST.getWrapper());
        this.addBiome(NetherExBiomes.TORRID_WASTELAND.getWrapper());
        this.addBiome(NetherExBiomes.ARCTIC_ABYSS.getWrapper());

        if(NetherEx.IS_BOP_LOADED && ConfigHandler.compatibilityConfig.biomesOPlenty.enableCompatibility)
        {
            this.addBiome(new NetherBiomeWrapper(new ResourceLocation("biomesoplenty:corrupted_sands"), 8, true, true));
            this.addBiome(new NetherBiomeWrapper(new ResourceLocation("biomesoplenty:fungi_forest"), 4, true, true));
            this.addBiome(new NetherBiomeWrapper(new ResourceLocation("biomesoplenty:phantasmagoric_inferno"), 6, true, true));
            this.addBiome(new NetherBiomeWrapper(new ResourceLocation("biomesoplenty:undergarden"), 4, true, true));
            this.addBiome(new NetherBiomeWrapper(new ResourceLocation("biomesoplenty:visceral_heap"), 4, true, true));
        }
    }

    public void readBiomeConfigs()
    {
        NetherEx.LOGGER.info("Reading Nether biome configs.");

        try
        {
            Path directoryPath = new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Biomes").toPath();
            Files.createDirectories(directoryPath);
            Iterator<Path> pathIter = Files.walk(directoryPath).iterator();

            while(pathIter.hasNext())
            {
                Path configPath = pathIter.next();
                File configFile = configPath.toFile();

                String extension = FileHelper.getFileExtension(configFile);

                if(extension.equals("json"))
                {
                    FileConfig config = ConfigHelper.newConfig(configFile, false, true, true, true);
                    config.load();

                    Biome biome = ForgeRegistries.BIOMES.getValue(ConfigHelper.getOrSetResourceLocation(config, "biome", null));

                    if(biome != null && ConfigHelper.getOrSet(config, "enabled", true))
                    {
                        int biomeId = Biome.getIdForBiome(biome);
                        INetherBiomeWrapper wrapper;

                        if(this.moddedBiomes.containsKey(biomeId) && this.moddedBiomes.get(biomeId) instanceof ISerializableBiomeWrapper)
                        {
                            wrapper = this.moddedBiomes.get(biomeId);
                            ((ISerializableBiomeWrapper) wrapper).deserialize(config);

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
                    NetherEx.LOGGER.warn("Skipping file located at, {}, as it is not a json file.", configPath.toString());
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
            if(entry.getValue() instanceof ISerializableBiomeWrapper)
            {
                FileConfig config = ((ISerializableBiomeWrapper) entry.getValue()).serialize();
                config.save();
                config.close();
            }

            INetherBiomeWrapper wrapper = entry.getValue();

            for(EnumCreatureType type : EnumCreatureType.values())
            {
                wrapper.getEntitySpawnEntries(type).clear();
            }
            for(GenerationStage stage : GenerationStage.values())
            {
                wrapper.getFeatures(stage).clear();
            }
        }

        for(Map.Entry<Integer, INetherBiomeWrapper> entry : this.playerBiomes.entrySet())
        {
            if(entry.getValue() instanceof ISerializableBiomeWrapper)
            {
                FileConfig config = ((ISerializableBiomeWrapper) entry.getValue()).serialize();
                config.save();
                config.close();
            }

            INetherBiomeWrapper wrapper = entry.getValue();

            for(EnumCreatureType type : EnumCreatureType.values())
            {
                wrapper.getEntitySpawnEntries(type).clear();
            }
            for(GenerationStage stage : GenerationStage.values())
            {
                wrapper.getFeatures(stage).clear();
            }
        }
    }
}

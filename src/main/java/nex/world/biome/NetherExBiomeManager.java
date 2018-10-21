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
import lex.world.biome.BiomeConfigurations;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import nex.NetherEx;
import nex.handler.ConfigHandler;
import nex.init.NetherExBiomes;
import nex.world.biome.vanilla.BiomeConfigurationsHell;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class NetherExBiomeManager
{
    private static final List<BiomeManager.BiomeEntry> BIOMES = new ArrayList<>();
    private static final Map<Biome, BiomeConfigurations> BIOME_CONFIGURATIONS = new HashMap<>();

    public static void createBiomeConfigs()
    {
        List<FileConfig> biomeConfigs = new ArrayList<>(Arrays.asList(
                new BiomeConfigurationsHell().serialize(new File(LibEx.CONFIG_DIRECTORY, "/NetherEx/Biomes/minecraft/hell.json")),
                NetherExBiomes.RUTHLESS_SANDS.getConfigurations().serialize(new File(LibEx.CONFIG_DIRECTORY, "/NetherEx/Biomes/nex/ruthless_sands.json")),
                NetherExBiomes.FUNGI_FOREST.getConfigurations().serialize(new File(LibEx.CONFIG_DIRECTORY, "/NetherEx/Biomes/nex/fungi_forest.json")),
                NetherExBiomes.TORRID_WASTELAND.getConfigurations().serialize(new File(LibEx.CONFIG_DIRECTORY, "/NetherEx/Biomes/nex/torrid_wasteland.json")),
                NetherExBiomes.ARCTIC_ABYSS.getConfigurations().serialize(new File(LibEx.CONFIG_DIRECTORY, "/NetherEx/Biomes/nex/arctic_abyss.json")),
                NetherExBiomes.REGROWTHS_COLLAPSE.getConfigurations().serialize(new File(LibEx.CONFIG_DIRECTORY, "/NetherEx/Biomes/nex/regrowths_collapse.json"))
        ));

        if(NetherEx.IS_BOP_LOADED)
        {
            biomeConfigs.addAll(Arrays.asList(
                    new BiomeConfigurations(new ResourceLocation("biomesoplenty:corrupted_sands"), 8, true, true, new HashMap<>(), new HashMap<>(), new HashMap<>()).serialize(new File(LibEx.CONFIG_DIRECTORY, "/NetherEx/Biomes/biomesoplenty/corrupted_sands.json")),
                    new BiomeConfigurations(new ResourceLocation("biomesoplenty:fungi_forest"), 4, true, true, new HashMap<>(), new HashMap<>(), new HashMap<>()).serialize(new File(LibEx.CONFIG_DIRECTORY, "/NetherEx/Biomes/biomesoplenty/fungi_forest.json")),
                    new BiomeConfigurations(new ResourceLocation("biomesoplenty:phantasmagoric_inferno"), 6, true, true, new HashMap<>(), new HashMap<>(), new HashMap<>()).serialize(new File(LibEx.CONFIG_DIRECTORY, "/NetherEx/Biomes/biomesoplenty/phantasmagoric_inferno.json")),
                    new BiomeConfigurations(new ResourceLocation("biomesoplenty:undergarden"), 4, true, true, new HashMap<>(), new HashMap<>(), new HashMap<>()).serialize(new File(LibEx.CONFIG_DIRECTORY, "/NetherEx/Biomes/biomesoplenty/undergarden.json")),
                    new BiomeConfigurations(new ResourceLocation("biomesoplenty:visceral_heap"), 4, true, true, new HashMap<>(), new HashMap<>(), new HashMap<>()).serialize(new File(LibEx.CONFIG_DIRECTORY, "/NetherEx/Biomes/biomesoplenty/visceral_heap.json"))
            ));
        }

        biomeConfigs.forEach(FileConfig::save);
        biomeConfigs.forEach(FileConfig::close);
    }

    public static void readBiomeConfigs(MinecraftServer server)
    {
        World world = server.getEntityWorld();

        NetherEx.LOGGER.info("Setting up NetherEx biomes.");
        parseBiomeConfigs(new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Biomes/minecraft"));
        parseBiomeConfigs(new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Biomes/nex"));

        if(NetherEx.IS_BOP_LOADED && ConfigHandler.compatibilityConfig.biomesOPlenty.enableCompatibility)
        {
            WorldType worldType = world.getWorldType();

            if(worldType.getName().equalsIgnoreCase("BIOMESOP") || worldType.getName().equalsIgnoreCase("lostcities_bop"))
            {
                NetherEx.LOGGER.info("Setting up Biomes O' Plenty biomes.");
                parseBiomeConfigs(new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Biomes/biomesoplenty"));
            }
        }

        NetherEx.LOGGER.info("Setting up custom biomes.");
        parseBiomeConfigs(new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Biomes/custom"));
    }

    private static void parseBiomeConfigs(File directory)
    {
        try
        {
            Path directoryPath = directory.toPath();
            Files.createDirectories(directoryPath);
            Iterator<Path> pathIter = Files.walk(directoryPath).iterator();

            while(pathIter.hasNext())
            {
                Path configPath = pathIter.next();
                File configFile = configPath.toFile();

                String extension = FileHelper.getFileExtension(configFile);

                if(extension.equals("json") || extension.equals("toml"))
                {
                    FileConfig config = ConfigHelper.newConfig(configFile, false, true, true, true);
                    config.load();
                    createBiomeConfigurations(config);
                }
                else if(!configFile.isDirectory())
                {
                    NetherEx.LOGGER.warn("Skipping file located at, {}, as it is not a json or toml file.", configPath.toString());
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void createBiomeConfigurations(FileConfig config)
    {
        BiomeConfigurations configurations = new BiomeConfigurations(config);
        Biome biome = configurations.getBiome();

        if(biome != null && configurations.isEnabled())
        {
            BIOMES.add(new BiomeManager.BiomeEntry(biome, ConfigHelper.getOrSet(config, "weight", 10)));
            BIOME_CONFIGURATIONS.put(biome, configurations);
        }

        config.close();
    }

    public static void clearBiomes()
    {
        BIOMES.clear();
        BIOME_CONFIGURATIONS.clear();
    }

    public static List<BiomeManager.BiomeEntry> getBiomes()
    {
        return BIOMES;
    }

    public static BiomeConfigurations getBiomeConfigurations(Biome key)
    {
        return BIOME_CONFIGURATIONS.get(key);
    }
}

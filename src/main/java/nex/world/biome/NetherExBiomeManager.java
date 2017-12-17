package nex.world.biome;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lex.LibEx;
import lex.config.FileConfig;
import lex.config.IConfig;
import lex.init.LibExRegistries;
import lex.util.ConfigHelper;
import lex.util.FileHelper;
import lex.world.biome.BiomeWrapperBuilder;
import lex.world.biome.IBiomeWrapper;
import net.minecraft.init.Biomes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import nex.NetherEx;
import nex.handler.ConfigHandler;
import nex.init.NetherExBiomeWrapperBuilders;
import nex.init.NetherExBiomes;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@SuppressWarnings("ConstantConditions")
public class NetherExBiomeManager
{
    private static final List<Biome> BIOMES = new ArrayList<>();
    private static final Map<Biome, IConfig> BIOME_CONFIGS = new HashMap<>();
    private static final Map<Biome, IBiomeWrapper> BIOME_WRAPPERS = new HashMap<>();

    public static void unpackBiomeConfigs()
    {
        File directory = new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Biomes");

        if(LibEx.IS_DEV_ENV)
        {
            try
            {
                FileUtils.copyDirectory(new File(NetherEx.class.getResource("/assets/nex/biome_configs").getFile()), directory);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            FileHelper.extractFromJar(NetherEx.class.getResource("/assets/nex/biome_configs"), directory.getPath());
        }
    }

    public static void setupDefaultBiomes()
    {
        NetherEx.LOGGER.info("Setting up default biomes.");
        parseBiomeConfigs(new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Biomes/nex"));
    }

    public static void setupCompatibleBiomes(MinecraftServer server)
    {
        World world = server.getEntityWorld();

        if(Loader.isModLoaded("biomesoplenty") && ConfigHandler.compatibilityConfig.biomesOPlenty.enableCompat)
        {
            WorldType worldType = world.getWorldType();

            if(worldType.getName().equalsIgnoreCase("BIOMESOP") || worldType.getName().equalsIgnoreCase("lostcities_bop"))
            {
                NetherEx.LOGGER.info("Setting up Biomes O' Plenty biomes.");
                parseBiomeConfigs(new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Biomes/biomesoplenty"));
            }
        }
    }

    public static void setupCustomBiomes()
    {
        NetherEx.LOGGER.info("Setting up custom biomes.");
        parseBiomeConfigs(new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Biomes/custom"));
    }

    private static void parseBiomeConfigs(File directory)
    {
        if(!directory.exists())
        {
            directory.mkdirs();
        }

        Path directoryPath = directory.toPath();

        try
        {
            Iterator<Path> pathIter = Files.walk(directoryPath).iterator();

            while(pathIter.hasNext())
            {
                Path configPath = pathIter.next();
                File configFile = configPath.toFile();

                if(FilenameUtils.getExtension(configPath.toString()).equals("json"))
                {
                    wrapBiome(new FileConfig(configFile), configFile);
                }
                else if(!configFile.isDirectory())
                {
                    NetherEx.LOGGER.warn("Skipping file located at, " + configPath.toString() + ", as it is not a json file.");
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void wrapBiome(IConfig biomeConfig, File biomeConfigFile)
    {
        Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(biomeConfig.getString("biome")));

        if(biome != null)
        {
            BIOMES.add(biome);
            BIOME_CONFIGS.put(biome, biomeConfig);

            BiomeWrapperBuilder builder = LibExRegistries.BIOME_WRAPPER_BUILDERS.getValue(biomeConfig.getResource("wrapper", NetherExBiomeWrapperBuilders.NETHER.getRegistryName()));

            if(builder != null)
            {
                BIOME_WRAPPERS.put(biome, builder.configure(biomeConfig).create());
            }

            ConfigHelper.saveConfig(biomeConfig, biomeConfigFile);
        }
    }

    public static List<Biome> getBiomes()
    {
        return ImmutableList.copyOf(BIOMES);
    }

    public static IConfig getBiomeConfig(Biome key)
    {
        return BIOME_CONFIGS.get(key);
    }

    public static IBiomeWrapper getBiomeWrapper(Biome key)
    {
        return BIOME_WRAPPERS.get(key);
    }
}

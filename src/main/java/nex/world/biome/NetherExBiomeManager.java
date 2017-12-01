package nex.world.biome;

import com.google.common.collect.ImmutableList;
import lex.LibEx;
import lex.config.FileConfig;
import lex.config.IConfig;
import lex.util.ConfigHelper;
import lex.util.FileHelper;
import lex.world.biome.BiomeWrapperManager;
import lex.world.biome.IBiomeWrapper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import nex.NetherEx;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetherExBiomeManager
{
    private static final List<Biome> BIOMES = new ArrayList<>();
    private static final Map<Biome, IConfig> BIOME_CONFIGS = new HashMap<>();
    private static final Map<Biome, IBiomeWrapper> BIOME_WRAPPERS = new HashMap<>();

    public static void init()
    {
        copyDefaultBiomeConfigs();
        readBiomeConfig();
        wrapBiomes();
    }

    private static void copyDefaultBiomeConfigs()
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

    private static void readBiomeConfig()
    {
        File configFile = new File("~/NetherEx/Biomes/nether_biome_list.json");
        IConfig config = new FileConfig(configFile);
        List<String> defaultBiomeList = new ArrayList<>();
        defaultBiomeList.add("nex:hell");
        //defaultBiomeList.add("nex:ruthless_sands");
        //defaultBiomeList.add("nex:fungi_forest");
        //defaultBiomeList.add("nex:torrid_wasteland");
        //defaultBiomeList.add("nex:arctic_abyss");

        List<String> biomeList = config.getStrings("biomes", defaultBiomeList);

        for(String biomeName : biomeList)
        {
            Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(biomeName));

            if(biome != null)
            {
                BIOMES.add(biome);
                BIOME_CONFIGS.put(biome, new FileConfig(getBiomeConfigFile(biome)));
            }
        }

        ConfigHelper.saveConfig(config, configFile);
    }

    private static void wrapBiomes()
    {
        for(Biome biome : BIOMES)
        {
            IConfig config = BIOME_CONFIGS.get(biome);
            IBiomeWrapper wrapper = BiomeWrapperManager.createBiomeWrapper(config.getString("wrapper", "nether"), config);

            if(wrapper != null)
            {
                BIOME_WRAPPERS.put(biome, wrapper);
            }

            ConfigHelper.saveConfig(config, getBiomeConfigFile(biome));
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

    private static File getBiomeConfigFile(Biome biome)
    {
        return new File(String.format("~/NetherEx/Biomes/%s/%s.json", biome.getRegistryName().getResourceDomain(), biome.getRegistryName().getResourcePath()));
    }
}

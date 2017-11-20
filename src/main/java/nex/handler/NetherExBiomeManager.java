package nex.handler;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lex.biome.WrappedBiomeManager;
import lex.config.Config;
import lex.config.ConfigFactory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static lex.util.JsonUtils.isString;

public class NetherExBiomeManager
{
    private static final List<Biome> BIOME_LIST = new ArrayList<>();
    private static final Map<Biome, Config> BIOME_CONFIG_MAP = new HashMap<>();

    public static void init()
    {
        readBiomeConfig();
        wrapBiomes();
    }

    private static void readBiomeConfig()
    {
        Config config = ConfigFactory.parseFile(new File("~/NetherEx/Biomes/nether_biome_list.json"));
        JsonObject defaultObject = new JsonObject();
        defaultObject.add("nex:hell", new JsonPrimitive("~/NetherEx/Biomes/nex/hell.json"));
        defaultObject.add("nex:ruthless_sands", new JsonPrimitive("~/NetherEx/Biomes/nex/ruthless_sands.json"));
        defaultObject.add("nex:fungi_forest", new JsonPrimitive("~/NetherEx/Biomes/nex/fungi_forest.json"));
        defaultObject.add("nex:torrid_wasteland", new JsonPrimitive("~/NetherEx/Biomes/nex/torrid_wasteland.json"));
        defaultObject.add("nex:arctic_abyss", new JsonPrimitive("~/NetherEx/Biomes/nex/arctic_abyss.json"));

        Config biomeConfig = config.getInnerConfig("biomes");

        if(biomeConfig == null || biomeConfig.getElementMap().size() == 0)
        {
            biomeConfig = config.getInnerConfig("biomes", defaultObject);
        }

        for(Map.Entry<String, JsonElement> entry : biomeConfig.getElementMap().entrySet())
        {
            if(isString(entry.getValue()))
            {
                Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(entry.getKey()));

                if(biome != null)
                {
                    BIOME_LIST.add(biome);
                    BIOME_CONFIG_MAP.put(biome, ConfigFactory.parseFile(new File(entry.getValue().getAsJsonPrimitive().getAsString())));
                }
            }
        }

        ConfigFactory.saveConfig(config);
    }

    private static void wrapBiomes()
    {
        for(Biome biome : BIOME_LIST)
        {
            WrappedBiomeManager.createWrappedBiome(biome, BIOME_CONFIG_MAP.get(biome));
        }
    }

    public static List<Biome> getBiomeList()
    {
        return ImmutableList.copyOf(BIOME_LIST);
    }

    public static Config getBiomeConfig(Biome key)
    {
        if(BIOME_CONFIG_MAP.containsKey(key))
        {
            return BIOME_CONFIG_MAP.get(key);
        }

        return null;
    }
}

package nex.handler;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lex.LibEx;
import lex.biome.WrappedBiomeManager;
import lex.config.JsonConfig;
import lex.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NetherExBiomeManager
{
    private static final List<Biome> BIOMES = new ArrayList<>();

    public static void init()
    {
        readBiomeConfig();
        wrapBiomes();
    }

    private static void readBiomeConfig()
    {
        JsonConfig config = new JsonConfig("Nether Biomes", new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Biomes/biomes.json"));
        JsonArray fallbackArray = new JsonArray();
        fallbackArray.add("nex:hell");
        fallbackArray.add("nex:ruthless_sands");
        fallbackArray.add("nex:fungi_forest");
        fallbackArray.add("nex:torrid_wasteland");
        fallbackArray.add("nex:arctic_abyss");

        JsonArray array = config.getArray("biomes", fallbackArray);

        for(JsonElement entry : array)
        {
            if(JsonUtils.isString(entry))
            {
                Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(entry.getAsJsonPrimitive().getAsString()));

                if(biome != null)
                {
                    BIOMES.add(biome);
                }
                else
                {
                    config.getLogger().warn("The {} config contains the value, {}, which is not a registered biome name", config.getName(), entry.toString());
                }
            }
            else
            {
                config.getLogger().warn("The {} config contains the value, {}, which is not a string", config.getName(), entry.toString());
            }
        }

        if(BIOMES.size() == 0)
        {
            for(JsonElement entry : fallbackArray)
            {
                if(JsonUtils.isString(entry))
                {
                    Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(entry.getAsJsonPrimitive().getAsString()));

                    if(biome != null)
                    {
                        BIOMES.add(biome);
                    }
                }
            }
        }

        config.save();
    }

    private static void wrapBiomes()
    {
        for(Biome biome : BIOMES)
        {
            ResourceLocation registryName = biome.getRegistryName();
            WrappedBiomeManager.createWrappedBiome(biome, new JsonConfig(registryName.toString(), new File(LibEx.CONFIG_DIRECTORY, "/NetherEx/Biomes/" + registryName.getResourceDomain() + "/" + registryName.getResourcePath())));
        }
    }

    public static List<Biome> getBiomes()
    {
        return ImmutableList.copyOf(BIOMES);
    }
}

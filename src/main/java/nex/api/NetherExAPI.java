package nex.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraftforge.common.BiomeManager;
import nex.api.biome.NetherBiome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class NetherExAPI
{
    public static final Logger LOGGER = LogManager.getLogger("NetherEx|API");

    private static List<BiomeManager.BiomeEntry> biomeEntries = Lists.newArrayList();

    public static void addBiome(NetherBiome biome, int weight)
    {
        if(biome == null)
        {
            LOGGER.warn("Unable to add Biome. It was null!");
            return;
        }
        else if(weight <= 0)
        {
            LOGGER.warn(String.format("Unable to add %s to the Nether. Its Weight must be greater than zero!", biome.getBiomeName()));
            return;
        }

        for(BiomeManager.BiomeEntry entry : biomeEntries)
        {
            if(entry.biome.getRegistryName().equals(biome.getRegistryName()))
            {
                entry.itemWeight += weight;
                return;
            }
        }

        biomeEntries.add(new BiomeManager.BiomeEntry(biome, weight));
    }

    public static void removeBiome(NetherBiome biome)
    {
        if(biome == null)
        {
            LOGGER.warn("Unable to remove Biome. It was null!");
            return;
        }

        biomeEntries.stream().filter(entry -> entry.biome.getRegistryName().equals(biome.getRegistryName())).forEach(entry ->
        {
            biomeEntries.remove(new BiomeManager.BiomeEntry(entry.biome, entry.itemWeight));
            LOGGER.info(String.format("Removed %s from the Nether.", entry.biome.getBiomeName()));
        });
    }

    public static ImmutableList<BiomeManager.BiomeEntry> getBiomeEntries()
    {
        return ImmutableList.copyOf(biomeEntries);
    }
}

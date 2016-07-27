package nex.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraftforge.common.BiomeManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class NetherExAPI
{
    public static Logger logger = LogManager.getLogger("NetherEx|API");

    private static List<BiomeManager.BiomeEntry> biomeEntries = Lists.newArrayList();

    public static void addBiome(BiomeManager.BiomeEntry entry)
    {
        if(entry.biome == null)
        {
            logger.info("Unable to add BiomeEntry. Its Biome was null!");
            return;
        }
        else if(biomeEntries.contains(entry))
        {
            logger.info(String.format("Unable to add the %s biome to the Nether. It was already added!", entry.biome.getBiomeName()));
            return;
        }

        biomeEntries.add(entry);
    }

    public static void removeBiome(BiomeManager.BiomeEntry entry)
    {
        if(biomeEntries.contains(entry))
        {
            biomeEntries.remove(entry);
            logger.info(String.format("Removed the %s biome from the Nether.", entry.biome.getBiomeName()));
            return;
        }

        logger.info(String.format("Unable to remove the %s biome from the Nether. It has not yet been added!", entry.biome.getBiomeName()));
    }

    public static ImmutableList<BiomeManager.BiomeEntry> getBiomeEntries()
    {
        return ImmutableList.copyOf(biomeEntries);
    }
}

package nex.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import nex.api.biome.NetherBiomeEntry;
import nex.api.biome.NetherExBiomes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class NetherExAPI
{
    public static Logger logger = LogManager.getLogger("NetherEx|API");

    public static NetherBiomeEntry defaultBiomeEntry = new NetherBiomeEntry(NetherExBiomes.HELL, 10);

    private static List<NetherBiomeEntry> biomeEntries = Lists.newArrayList();

    public static void addBiome(NetherBiomeEntry entry)
    {
        if(biomeEntries.contains(entry))
        {
            logger.info(String.format("Unable to add the %s biome to the Nether. It was already added!", entry.biome.getBiomeName()));
            return;
        }

        biomeEntries.add(entry);
    }

    public static void removeBiome(NetherBiomeEntry entry)
    {

        if(biomeEntries.contains(entry))
        {
            biomeEntries.remove(entry);
            logger.info(String.format("Removed the %s biome from the Nether.", entry.biome.getBiomeName()));
            return;
        }

        logger.info(String.format("Unable to remove the %s biome from the Nether. It has not yet been added!", entry.biome.getBiomeName()));
    }

    public static ImmutableList<NetherBiomeEntry> getBiomeEntries()
    {
        return ImmutableList.copyOf(biomeEntries);
    }
}

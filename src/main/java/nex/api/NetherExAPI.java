package nex.api;

import com.google.common.collect.Lists;
import nex.api.biome.NetherBiome;
import nex.api.biome.NetherBiomeEntry;
import nex.api.biome.NetherExBiomes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.List;

public class NetherExAPI
{
    public static Logger logger = LogManager.getLogger("NetherEx|API");

    public static NetherBiomeEntry defaultBiomeEntry = new NetherBiomeEntry(NetherExBiomes.hell, 10);

    private static List<NetherBiomeEntry> biomeEntries = Lists.newArrayList();

    public static void addBiome(NetherBiome biome, int weight)
    {
        NetherBiomeEntry entry = new NetherBiomeEntry(biome, weight);

        for(NetherBiomeEntry e : biomeEntries)
        {
            if(e.biome == entry.biome)
            {
                logger.info(String.format("Unable to add the %s biome to the Nether. It was already added!", entry.biome.getBiomeName()));
                return;
            }
        }

        biomeEntries.add(entry);
    }

    public static void removeBiome(NetherBiome biome)
    {
        Iterator<NetherBiomeEntry> itr = biomeEntries.iterator();

        while(itr.hasNext())
        {
            NetherBiomeEntry entry = itr.next();

            if(entry.biome == biome)
            {
                logger.info(String.format("Removed the %s biome from the Nether.", biome.getBiomeName()));
                itr.remove();
                return;
            }
        }

        logger.info(String.format("Unable to remove the %s biome from the Nether. It has not yet been added!", biome.getBiomeName()));
    }

    public static List<NetherBiomeEntry> getBiomeEntries()
    {
        return biomeEntries;
    }
}

package nex.api;

import com.google.common.collect.Lists;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.List;

public class NetherExAPI
{
    public static Logger logger = LogManager.getLogger("NetherEx|API");

    private static List<BiomeManager.BiomeEntry> netherBiomes = Lists.newArrayList();

    public static void addNetherBiome(Biome biome, int weight)
    {
        BiomeManager.BiomeEntry entry = new BiomeManager.BiomeEntry(biome, weight);

        for(BiomeManager.BiomeEntry e : netherBiomes)
        {
            if(e.biome == entry.biome)
            {
                logger.info(String.format("Unable to add the %s biome to the Nether. It was already added!", entry.biome.getBiomeName()));
                return;
            }
        }

        netherBiomes.add(entry);
    }

    public static void removeNetherBiome(Biome biome)
    {
        Iterator<BiomeManager.BiomeEntry> itr = netherBiomes.iterator();

        while(itr.hasNext())
        {
            BiomeManager.BiomeEntry entry = itr.next();

            if(entry.biome == biome)
            {
                logger.info(String.format("Removed the %s biome from the Nether.", biome.getBiomeName()));
                itr.remove();
                return;
            }
        }

        logger.info(String.format("Unable to remove the %s biome from the Nether. It has not yet been added!", biome.getBiomeName()));
    }

    public static List<BiomeManager.BiomeEntry> getNetherBiomes()
    {
        return netherBiomes;
    }
}

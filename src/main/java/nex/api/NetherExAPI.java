package nex.api;

import com.google.common.collect.Lists;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.DungeonHooks;
import nex.api.dungeon.DungeonEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class NetherExAPI
{
    public static Logger logger = LogManager.getLogger("NetherEx|API");

    public static BiomeManager.BiomeEntry defaultBiomeEntry = new BiomeManager.BiomeEntry(Biomes.HELL, 10);
    private static DungeonEntry defaultDungeonEntry = new DungeonEntry("Skeleton", 10, Blocks.OBSIDIAN.getDefaultState(), Blocks.OBSIDIAN.getDefaultState(), new DungeonHooks.DungeonMob(10, "Skeleton"));

    private static List<BiomeManager.BiomeEntry> biomeEntries = Lists.newArrayList();
    private static List<DungeonEntry> dungeonEntries = Lists.newArrayList();

    static
    {
        Biomes.HELL.topBlock = Blocks.NETHERRACK.getDefaultState();
        Biomes.HELL.fillerBlock = Blocks.NETHERRACK.getDefaultState();
        NetherExAPI.addBiome(Biomes.HELL, 10);

        NetherExAPI.addDungeonEntry(defaultDungeonEntry);
    }

    private NetherExAPI()
    {
    }

    public static void addBiome(Biome biome, int weight)
    {
        BiomeManager.BiomeEntry entry = new BiomeManager.BiomeEntry(biome, weight);

        for(BiomeManager.BiomeEntry e : biomeEntries)
        {
            if(e.biome == entry.biome)
            {
                logger.info(String.format("Unable to add the %s biome to the Nether. It was already added!", entry.biome.getBiomeName()));
                return;
            }
        }

        biomeEntries.add(entry);
    }

    public static void removeBiome(Biome biome)
    {
        Iterator<BiomeManager.BiomeEntry> itr = biomeEntries.iterator();

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

    public static List<BiomeManager.BiomeEntry> getBiomeEntries()
    {
        return biomeEntries;
    }

    public static void addDungeonEntry(DungeonEntry entry)
    {
        for(DungeonEntry e : dungeonEntries)
        {
            if(e.equals(entry))
            {
                logger.info(String.format("Unable to add the %s Dungeon Entry to the Nether Dungeon Entries. It was already added!", entry.getName()));
                return;
            }
        }

        dungeonEntries.add(entry);
    }

    public static void removeDungeonEntry(String name)
    {
        Iterator<DungeonEntry> itr = dungeonEntries.iterator();

        while(itr.hasNext())
        {
            DungeonEntry e = itr.next();

            if(e.getName().equals(name))
            {
                logger.info(String.format("Removed the %s Dungeon Entry from the Nether Dungeon Entries.", name));
                itr.remove();
                return;
            }
        }

        logger.info(String.format("Unable to remove the %s Dungeon Entry from the Nether Dungeon Entries. It has not yet been added!", name));
    }

    public static List<DungeonEntry> getDungeonEntries()
    {
        return dungeonEntries;
    }

    public static DungeonEntry getRandomDungeonEntry(Random rand)
    {
        if(dungeonEntries.size() == 0)
        {
            dungeonEntries.add(defaultDungeonEntry);
        }

        return WeightedRandom.getRandomItem(rand, dungeonEntries);
    }

    public static String getRandomDungeonEntryMob(DungeonEntry entry, Random rand)
    {
        if(entry.getDungeonMobs().size() == 0)
        {
            entry.getDungeonMobs().add(new DungeonHooks.DungeonMob(10, "Skeleton"));
        }

        return WeightedRandom.getRandomItem(rand, entry.getDungeonMobs()).type;
    }
}

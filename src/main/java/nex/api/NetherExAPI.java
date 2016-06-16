package nex.api;

import com.google.common.collect.Lists;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.DungeonHooks;
import nex.api.dungeon.DungeonSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class NetherExAPI
{
    public static Logger logger = LogManager.getLogger("NetherEx|API");

    private static DungeonSet defaultSet = new DungeonSet("Skeleton", 10, Blocks.OBSIDIAN.getDefaultState(), Blocks.OBSIDIAN.getDefaultState(), new DungeonHooks.DungeonMob(10, "Skeleton"));

    private static List<BiomeManager.BiomeEntry> biomes = Lists.newArrayList();
    private static List<DungeonSet> dungeonSets = Lists.newArrayList();

    private NetherExAPI() {}

    static
    {
        Biomes.HELL.topBlock = Blocks.NETHERRACK.getDefaultState();
        Biomes.HELL.fillerBlock = Blocks.NETHERRACK.getDefaultState();
        NetherExAPI.addBiome(Biomes.HELL, 10);

        NetherExAPI.addDungeonSet(defaultSet);
    }

    public static void addBiome(Biome biome, int weight)
    {
        BiomeManager.BiomeEntry entry = new BiomeManager.BiomeEntry(biome, weight);

        for(BiomeManager.BiomeEntry e : biomes)
        {
            if(e.biome == entry.biome)
            {
                logger.info(String.format("Unable to add the %s biome to the Nether. It was already added!", entry.biome.getBiomeName()));
                return;
            }
        }

        biomes.add(entry);
    }

    public static void removeBiome(Biome biome)
    {
        Iterator<BiomeManager.BiomeEntry> itr = biomes.iterator();

        while(itr.hasNext())
        {
            BiomeManager.BiomeEntry entry = itr.next();

            if(entry.biome == Biomes.HELL)
            {
                logger.info("You cannot remove the default Nether biome!");
                return;
            }
            else if(entry.biome == biome)
            {
                logger.info(String.format("Removed the %s biome from the Nether.", biome.getBiomeName()));
                itr.remove();
                return;
            }
        }

        logger.info(String.format("Unable to remove the %s biome from the Nether. It has not yet been added!", biome.getBiomeName()));
    }

    public static List<BiomeManager.BiomeEntry> getBiomes()
    {
        return biomes;
    }

    public static void addDungeonSet(DungeonSet set)
    {
        for(DungeonSet s : dungeonSets)
        {
            if(s.equals(set))
            {
                logger.info(String.format("Unable to add the %s Dungeon Set to the Nether Dungeon Sets. It was already added!", set.getName()));
                return;
            }
        }

        dungeonSets.add(set);
    }

    public static void removeDungeonSet(String name)
    {
        Iterator<DungeonSet> itr = dungeonSets.iterator();

        while(itr.hasNext())
        {
            DungeonSet s = itr.next();

            if(s.getName().equals(name))
            {
                logger.info(String.format("Removed the %s Dungeon Set from the Nether Dungeon Sets.", name));
                itr.remove();
                return;
            }
        }

        logger.info(String.format("Unable to remove the %s Dungeon Set from the Nether Dungeon Sets. It has not yet been added!", name));
    }

    public static List<DungeonSet> getDungeonSets()
    {
        return dungeonSets;
    }

    public static DungeonSet getRandomDungeonSet(Random rand)
    {
        if(dungeonSets == null)
        {
            return defaultSet;
        }

        return WeightedRandom.getRandomItem(rand, dungeonSets);
    }

    public static String getRandomDungeonSetMob(DungeonSet set, Random rand)
    {
        if(set.getDungeonMobs() == null)
        {
            return "";
        }

        return WeightedRandom.getRandomItem(rand, set.getDungeonMobs()).type;
    }
}

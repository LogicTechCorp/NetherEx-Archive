package nex.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.DimensionManager;
import nex.world.WorldProviderNether;
import nex.world.biome.BiomeHell;

import java.util.Set;

public class ModBiomes
{
    private static Set<BiomeManager.BiomeEntry> biomeEntries = Sets.newHashSet();

    public static final Biome HELL;

    static
    {
        HELL = new BiomeHell();

        DimensionManager.unregisterDimension(-1);
        DimensionType nether = DimensionType.register("Nether", "_nether", -1, WorldProviderNether.class, false);
        DimensionManager.registerDimension(-1, nether);
    }

    public static void init()
    {

    }

    public static boolean addBiome(BiomeManager.BiomeEntry entry)
    {
        for(BiomeManager.BiomeEntry e : biomeEntries)
        {
            if(entry.biome == e.biome)
            {
                e.itemWeight += entry.itemWeight;
                return false;
            }
        }

        return biomeEntries.add(entry);
    }

    public static boolean removeBiome(Biome biome)
    {
        for(BiomeManager.BiomeEntry e : biomeEntries)
        {
            if(biome == e.biome)
            {
                return biomeEntries.remove(e);
            }
        }

        return false;
    }

    public static int getBiomeId(String name)
    {
        for(BiomeManager.BiomeEntry entry : biomeEntries)
        {
            if(entry.biome.getRegistryName().getResourcePath().equals(name))
            {
                return Biome.getIdForBiome(entry.biome);
            }
        }

        return -1;
    }

    public static ImmutableList<BiomeManager.BiomeEntry> getBiomeEntries()
    {
        return ImmutableList.copyOf(biomeEntries);
    }
}
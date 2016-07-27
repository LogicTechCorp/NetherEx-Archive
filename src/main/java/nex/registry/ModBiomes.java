package nex.registry;

import net.minecraft.world.biome.Biome;
import nex.api.biome.NetherBiome;
import nex.world.biome.*;

public class ModBiomes
{
    public static final NetherBiome HELL;
    public static final NetherBiome FORGOTTEN_SANDS;
    public static final NetherBiome FUNGI_FOREST;
    public static final NetherBiome BLAZING_INFERNO;
    public static final NetherBiome FREEZING_BLIZZARD;

    static
    {
        HELL = new BiomeHell(0, "hell", 10, new Biome.BiomeProperties("Hell"));
        FORGOTTEN_SANDS = new BiomeForgottenSands(1, "forgotten_sands", 10, new Biome.BiomeProperties("Forgotten Sands"));
        FUNGI_FOREST = new BiomeFungiForest(2, "fungi_forest", 10, new Biome.BiomeProperties("Fungi Forest"));
        BLAZING_INFERNO = new BiomeBlazingInferno(3, "blazing_inferno", 10, new Biome.BiomeProperties("Blazing Inferno"));
        FREEZING_BLIZZARD = new BiomeFreezingBlizzard(4, "freezing_blizzard", 10, new Biome.BiomeProperties("Freezing Blizzard"));
    }

    public static void register()
    {
    }
}

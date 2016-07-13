package nex.init;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import nex.NetherEx;
import nex.api.NetherExAPI;
import nex.api.biome.NetherBiome;
import nex.api.biome.NetherExBiomes;
import nex.world.biome.BiomeHell;

public class ModRegistry
{
    public static void initBiomes()
    {
        NetherExBiomes.hell = registerBiome(0, "hell", new BiomeHell(new Biome.BiomeProperties("Hell")), 10, Type.NETHER);
    }

    private static NetherBiome registerBiome(int id, String name, NetherBiome biome, int weight, BiomeDictionary.Type... types)
    {
        Biome.registerBiome(id, NetherEx.MOD_ID + ":" + name, biome);
        BiomeDictionary.registerBiomeType(biome, types);
        NetherExAPI.addBiome(biome, weight);
        return biome;
    }
}

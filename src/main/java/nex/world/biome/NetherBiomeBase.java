package nex.world.biome;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import nex.NetherEx;
import nex.api.NetherExAPI;
import nex.api.biome.NetherBiome;

public abstract class NetherBiomeBase extends NetherBiome
{
    public NetherBiomeBase(int id, String name, int weight, BiomeProperties properties)
    {
        super(properties);

        registerBiome(id, name, this, weight);
    }

    private static NetherBiome registerBiome(int id, String name, NetherBiome biome, int weight)
    {
        Biome.registerBiome(id, NetherEx.MOD_ID + ":" + name, biome);
        NetherExAPI.addBiome(new BiomeManager.BiomeEntry(biome, weight));

        return biome;
    }
}

package nex.world.biome;

import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.NetherEx;
import nex.init.ModBiomes;

public abstract class BiomeNetherEx extends Biome
{
    public BiomeNetherEx(BiomeProperties properties)
    {
        super(properties);

        topBlock = Blocks.NETHERRACK.getDefaultState();
        fillerBlock = Blocks.NETHERRACK.getDefaultState();

        spawnableMonsterList.clear();
        spawnableCreatureList.clear();
        spawnableWaterCreatureList.clear();
        spawnableCaveCreatureList.clear();
    }

    public void register(String name, int weight)
    {
        BiomeDictionary.registerBiomeType(this, BiomeDictionary.Type.NETHER);
        GameRegistry.register(setRegistryName(new ResourceLocation(NetherEx.MOD_ID, name)));
        ModBiomes.addBiome(new BiomeManager.BiomeEntry(this, weight));
    }
}
package nex.world.biome;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.NetherEx;
import nex.init.ModBiomes;

public abstract class BiomeNetherEx extends Biome
{
    public final WorldGenFire fire = new WorldGenFire();
    public final WorldGenGlowStone1 glowStone1 = new WorldGenGlowStone1();
    public final WorldGenGlowStone2 glowStone2 = new WorldGenGlowStone2();
    public final WorldGenerator quartz = new WorldGenMinable(Blocks.QUARTZ_ORE.getDefaultState(), 14, BlockMatcher.forBlock(Blocks.NETHERRACK));
    public final WorldGenerator magma = new WorldGenMinable(Blocks.MAGMA.getDefaultState(), 33, BlockMatcher.forBlock(Blocks.NETHERRACK));
    public final WorldGenHellLava lavaTrap = new WorldGenHellLava(Blocks.FLOWING_LAVA, true);
    public final WorldGenHellLava lavaSpring = new WorldGenHellLava(Blocks.FLOWING_LAVA, false);
    public final WorldGenBush brownMushroom = new WorldGenBush(Blocks.BROWN_MUSHROOM);
    public final WorldGenBush redMushroom = new WorldGenBush(Blocks.RED_MUSHROOM);

    public BiomeNetherEx(BiomeProperties properties)
    {
        super(properties);

        topBlock = Blocks.NETHERRACK.getDefaultState();
        fillerBlock = Blocks.NETHERRACK.getDefaultState();

        spawnableMonsterList.clear();
        spawnableCreatureList.clear();
        spawnableWaterCreatureList.clear();
        spawnableCaveCreatureList.clear();

        theBiomeDecorator = new BiomeDecoratorNether();
    }

    public void register(String name, int weight)
    {
        BiomeDictionary.registerBiomeType(this, BiomeDictionary.Type.NETHER);
        GameRegistry.register(setRegistryName(new ResourceLocation(NetherEx.MOD_ID, name)));
        ModBiomes.addBiome(new BiomeManager.BiomeEntry(this, weight));
    }
}
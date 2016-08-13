package nex.world.biome;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import nex.Settings;
import nex.api.world.biome.INetherBiome;
import nex.api.world.biome.NEXBiomes;
import nex.api.world.gen.feature.*;

import java.util.Random;

public abstract class NEXBiome extends Biome implements INetherBiome
{
    public final WorldGenerator glowStone = new WorldGenGlowStone();
    public final WorldGenerator lavaSpring = new WorldGenLava(Blocks.NETHERRACK.getDefaultState(), false);
    public final WorldGenerator lavaTrap = new WorldGenLava(Blocks.NETHERRACK.getDefaultState(), true);
    public final WorldGenerator magma = new WorldGenMinable(Blocks.MAGMA.getDefaultState(), 33, Blocks.NETHERRACK.getDefaultState());
    public final WorldGenerator fire = new WorldGenFire();
    public final WorldGenerator redMushroom = new WorldGenBush(Blocks.RED_MUSHROOM.getDefaultState(), Blocks.NETHERRACK.getDefaultState());
    public final WorldGenerator brownMushroom = new WorldGenBush(Blocks.BROWN_MUSHROOM.getDefaultState(), Blocks.NETHERRACK.getDefaultState());
    public final WorldGenerator quartz = new WorldGenMinable(Blocks.QUARTZ_ORE.getDefaultState(), 14, Blocks.NETHERRACK.getDefaultState());

    static
    {
        assignBiomeIds();
    }

    public NEXBiome(BiomeProperties properties)
    {
        super(properties);

        topBlock = Blocks.NETHERRACK.getDefaultState();
        fillerBlock = Blocks.NETHERRACK.getDefaultState();

        spawnableMonsterList.clear();
        spawnableCreatureList.clear();
        spawnableWaterCreatureList.clear();
        spawnableCaveCreatureList.clear();

        theBiomeDecorator = new BiomeDecoratorHell();
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        super.decorate(world, rand, pos);
    }

    public void register(int id, String name)
    {
        BiomeDictionary.registerBiomeType(this, BiomeDictionary.Type.NETHER);
        Biome.registerBiome(id, name, this);
        NEXBiomes.REGISTRY.addBiome(this);

    }

    private static void assignBiomeIds()
    {
        int[] unusedBiomeIds = new int[256];
        checkBiomeIds(unusedBiomeIds);

        Settings.hellBiomeId = assignBiomeId(unusedBiomeIds, Settings.hellBiomeId);
        Settings.forgottenSandsBiomeId = assignBiomeId(unusedBiomeIds, Settings.forgottenSandsBiomeId);
        Settings.fungiForestBiomeId = assignBiomeId(unusedBiomeIds, Settings.fungiForestBiomeId);
        Settings.blazingInfernoBiomeId = assignBiomeId(unusedBiomeIds, Settings.blazingInfernoBiomeId);
        Settings.freezingBlizzardBiomeId = assignBiomeId(unusedBiomeIds, Settings.freezingBlizzardBiomeId);
    }

    private static int[] checkBiomeIds(int[] unusedBiomeIds)
    {
        for(int i = 0; i < 256; i++)
        {
            unusedBiomeIds[i] = Biome.getBiome(i) == null ? i : -1;
        }

        return unusedBiomeIds;
    }

    private static int assignBiomeId(int[] unusedBiomeIds, int biomeId)
    {
        if(biomeId == -1)
        {
            biomeId = findNextAvailableBiomeId(unusedBiomeIds);
            Settings.assignedBiomeIds = true;
        }

        return biomeId;
    }

    private static int findNextAvailableBiomeId(int[] unusedBiomeIds)
    {
        for(int i = 0; i < 256; i++)
        {
            if(unusedBiomeIds[i] != -1)
            {
                unusedBiomeIds[i] = -1;
                return i;
            }
        }

        return -1;
    }
}

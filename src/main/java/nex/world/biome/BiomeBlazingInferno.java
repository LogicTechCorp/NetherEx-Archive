package nex.world.biome;

import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import nex.NetherEx;
import nex.Settings;
import nex.api.block.NEXBlocks;
import nex.api.world.gen.feature.WorldGenLava;
import nex.api.world.gen.feature.WorldGenMinable;
import nex.api.world.gen.feature.WorldGenPool;
import nex.entity.passive.EntityTortoise;

import java.util.Random;

public class BiomeBlazingInferno extends NEXBiome
{
    private final WorldGenerator lavaSpring = new WorldGenLava(NEXBlocks.netherrack.getStateFromMeta(1), false);
    private final WorldGenerator lavaTrap = new WorldGenLava(NEXBlocks.netherrack.getStateFromMeta(1), true);
    private final WorldGenerator lavaPool = new WorldGenPool(Blocks.LAVA, NEXBlocks.netherrack.getStateFromMeta(1));
    private final WorldGenerator magma = new WorldGenMinable(Blocks.MAGMA.getDefaultState(), 33, NEXBlocks.netherrack.getStateFromMeta(1));
    private final WorldGenerator quartz = new WorldGenMinable(Blocks.QUARTZ_ORE.getDefaultState(), 14, NEXBlocks.netherrack.getStateFromMeta(1));

    public BiomeBlazingInferno()
    {
        super(new BiomeProperties("Blazing Inferno"));

        topBlock = NEXBlocks.netherrack.getStateFromMeta(1);
        fillerBlock = NEXBlocks.netherrack.getStateFromMeta(1);

        spawnableMonsterList.add(new SpawnListEntry(EntityTortoise.class, 100, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityBlaze.class, 20, 4, 4));

        register(Settings.blazingInfernoBiomeId, NetherEx.MOD_ID + ":blazingInferno");
    }

    @Override
    public Biome getBiome()
    {
        return this;
    }

    @Override
    public void genDecorations(World world, Random rand, BlockPos pos)
    {
        for(int i = 0; i < rand.nextInt(rand.nextInt(10) + 1); i++)
        {
            glowStone.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 4, rand.nextInt(16) + 8));
        }

        for(int i = 0; i < 10; i++)
        {
            glowStone.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(128), rand.nextInt(16) + 8));
        }

        for(int i = 0; i < 8; i++)
        {
            lavaSpring.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 4, rand.nextInt(16) + 8));
        }

        for(int i = 0; i < 16; i++)
        {
            lavaTrap.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(108) + 10, rand.nextInt(16)));
        }

        for(int i = 0; i < 4; i++)
        {
            lavaPool.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(128), rand.nextInt(16)));
        }

        for(int i = 0; i < 4; i++)
        {
            magma.generate(world, rand, pos.add(rand.nextInt(16), 28 + rand.nextInt(9), rand.nextInt(16)));
        }

        for(int i = 0; i < rand.nextInt(rand.nextInt(10) + 1) + 1; i++)
        {
            fire.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 4, rand.nextInt(16) + 8));
        }
    }

    @Override
    public void generateOres(World world, Random rand, BlockPos pos)
    {
        for(int i = 0; i < 16; i++)
        {
            quartz.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(108) + 10, rand.nextInt(16)));
        }
    }
}

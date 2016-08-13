package nex.world.biome;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import nex.NetherEx;
import nex.Settings;
import nex.api.block.NEXBlocks;
import nex.api.world.gen.feature.WorldGenBigMushroom;
import nex.api.world.gen.feature.WorldGenBush;
import nex.api.world.gen.feature.WorldGenMinable;
import nex.entity.neutral.EntityMogus;
import nex.world.gen.feature.WorldGenEnoki;

import java.util.Random;

public class BiomeFungiForest extends NEXBiome
{
    private final WorldGenerator enoki = new WorldGenEnoki();
    private final WorldGenerator bigRedMushroom = new WorldGenBigMushroom(Blocks.RED_MUSHROOM_BLOCK, NEXBlocks.mycelium.getDefaultState());
    private final WorldGenerator bigBrownMushroom = new WorldGenBigMushroom(Blocks.BROWN_MUSHROOM_BLOCK, NEXBlocks.mycelium.getDefaultState());
    private final WorldGenerator redMushroom = new WorldGenBush(Blocks.RED_MUSHROOM.getDefaultState(), NEXBlocks.mycelium.getDefaultState());
    private final WorldGenerator brownMushroom = new WorldGenBush(Blocks.BROWN_MUSHROOM.getDefaultState(), NEXBlocks.mycelium.getDefaultState());
    private final WorldGenerator quartz = new WorldGenMinable(NEXBlocks.quartzOre.getDefaultState(), 14, NEXBlocks.netherrack.getDefaultState());

    public BiomeFungiForest()
    {
        super(new BiomeProperties("Fungi Forest"));

        topBlock = NEXBlocks.mycelium.getDefaultState();
        fillerBlock = NEXBlocks.netherrack.getDefaultState();

        spawnableMonsterList.add(new SpawnListEntry(EntityMogus.class, 100, 8, 8));

        register(Settings.fungiForestBiomeId, NetherEx.MOD_ID + ":fungiForest");
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
            enoki.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(128), rand.nextInt(16) + 8));
        }

        for(int i = 0; i < 256; i++)
        {
            bigRedMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(128), rand.nextInt(16) + 8));
        }

        for(int i = 0; i < 256; i++)
        {
            bigBrownMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(128), rand.nextInt(16) + 8));
        }

        if(rand.nextBoolean())
        {
            redMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(128), rand.nextInt(16) + 8));
        }

        if(rand.nextBoolean())
        {
            brownMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(128), rand.nextInt(16) + 8));
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

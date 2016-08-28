package nex.world.gen;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.fml.common.IWorldGenerator;
import nex.Settings;
import nex.init.ModBiomes;
import nex.world.gen.feature.WorldGenThornBush;

import java.util.Random;

public class WorldGenNether implements IWorldGenerator
{
    private WorldGenerator lavaSpring = new WorldGenHellLava(Blocks.FLOWING_LAVA, false);
    private WorldGenerator fire = new WorldGenFire();
    private WorldGenerator glowStone = new WorldGenGlowStone1();
    private WorldGenerator brownMushroom = new WorldGenBush(Blocks.BROWN_MUSHROOM);
    private WorldGenerator redMushroom = new WorldGenBush(Blocks.RED_MUSHROOM);
    private WorldGenerator thornBush = new WorldGenThornBush();
    private WorldGenerator quartz = new WorldGenMinable(Blocks.QUARTZ_ORE.getDefaultState(), 14, BlockMatcher.forBlock(Blocks.NETHERRACK));
    private WorldGenerator magma = new WorldGenMinable(Blocks.MAGMA.getDefaultState(), 33, BlockMatcher.forBlock(Blocks.NETHERRACK));
    private WorldGenerator lavaTrap = new WorldGenHellLava(Blocks.FLOWING_LAVA, true);

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if(world.provider.getDimensionType() != DimensionType.NETHER)
        {
            return;
        }

        BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
        Biome biome = world.getBiomeForCoordsBody(pos);

        if(biome == ModBiomes.HELL)
        {
            generateHellFeatures(world, rand, pos);
        }
        else if(biome == ModBiomes.RUTHLESS_SANDS)
        {
            generateRuthlessSandsFeatures(world, rand, pos);
        }
    }

    private void generateHellFeatures(World world, Random rand, BlockPos pos)
    {
        if(Settings.BiomeHell.GENERATE_LAVA_SPRINGS)
        {
            for(int i = 0; i < Settings.BiomeHell.LAVA_SPRINGS_RARITY; i++)
            {
                lavaSpring.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 4, rand.nextInt(16) + 8));
            }
        }

        if(Settings.BiomeHell.GENERATE_FIRE)
        {
            for(int i = 0; i < rand.nextInt(Settings.BiomeHell.FIRE_RARITY); i++)
            {
                fire.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 4, rand.nextInt(16) + 8));
            }
        }

        if(Settings.BiomeHell.GENERATE_GLOWSTONE)
        {
            for(int i = 0; i < rand.nextInt(Settings.BiomeHell.GLOWSTONE_RARITY); i++)
            {
                glowStone.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 4, rand.nextInt(16) + 8));
            }

            for(int i = 0; i < Settings.BiomeHell.GLOWSTONE_RARITY; i++)
            {
                glowStone.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(128), rand.nextInt(16) + 8));
            }
        }

        if(Settings.BiomeHell.GENERATE_MUSHROOMS)
        {
            if(rand.nextBoolean())
            {
                brownMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(128), rand.nextInt(16) + 8));
            }

            if(rand.nextBoolean())
            {
                redMushroom.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(128), rand.nextInt(16) + 8));
            }
        }

        if(Settings.BiomeHell.GENERATE_QUARTZ_ORE)
        {
            for(int i = 0; i < Settings.BiomeHell.QUARTZ_ORE_RARITY; i++)
            {
                quartz.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(108) + 10, rand.nextInt(16)));
            }
        }

        if(Settings.BiomeHell.GENERATE_MAGMA)
        {
            for(int i = 0; i < Settings.BiomeHell.MAGMA_RARITY; i++)
            {
                magma.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(9) + 28, rand.nextInt(16)));
            }
        }

        if(Settings.BiomeHell.GENERATE_LAVA_TRAPS)
        {
            for(int i = 0; i < Settings.BiomeHell.LAVA_TRAPS_RARITY; i++)
            {
                lavaTrap.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(108) + 10, rand.nextInt(16)));
            }
        }
    }

    private void generateRuthlessSandsFeatures(World world, Random rand, BlockPos pos)
    {
        if(Settings.BiomeRuthlessSands.GENERATE_LAVA_SPRINGS)
        {
            for(int i = 0; i < Settings.BiomeRuthlessSands.LAVA_SPRINGS_RARITY; i++)
            {
                lavaSpring.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 4, rand.nextInt(16) + 8));
            }
        }

        if(Settings.BiomeRuthlessSands.GENERATE_GLOWSTONE)
        {
            for(int i = 0; i < rand.nextInt(Settings.BiomeRuthlessSands.GLOWSTONE_RARITY); i++)
            {
                glowStone.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 4, rand.nextInt(16) + 8));
            }

            for(int i = 0; i < Settings.BiomeRuthlessSands.GLOWSTONE_RARITY; i++)
            {
                glowStone.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(128), rand.nextInt(16) + 8));
            }
        }

        if(Settings.BiomeRuthlessSands.GENERATE_THORN_BUSHES)
        {
            for(int i = 0; i < rand.nextInt(Settings.BiomeRuthlessSands.THORN_BUSHES_RARITY); i++)
            {
                thornBush.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(96) + 32, rand.nextInt(16) + 8));
            }
        }

        if(Settings.BiomeRuthlessSands.GENERATE_QUARTZ_ORE)
        {
            for(int i = 0; i < Settings.BiomeRuthlessSands.QUARTZ_ORE_RARITY; i++)
            {
                quartz.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(108) + 10, rand.nextInt(16)));
            }
        }

        if(Settings.BiomeRuthlessSands.GENERATE_LAVA_TRAPS)
        {
            for(int i = 0; i < Settings.BiomeRuthlessSands.LAVA_TRAPS_RARITY; i++)
            {
                lavaTrap.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(108) + 10, rand.nextInt(16)));
            }
        }
    }
}
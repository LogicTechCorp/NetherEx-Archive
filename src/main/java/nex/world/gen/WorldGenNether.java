/*
 * Copyright (C) 2016.  LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
import nex.init.ModBlocks;
import nex.world.gen.feature.WorldGenBigMushroom;
import nex.world.gen.feature.*;

import java.util.Random;

public class WorldGenNether implements IWorldGenerator
{
    private WorldGenerator lavaSpring = new WorldGenLava(Blocks.NETHERRACK.getDefaultState(), false);
    private WorldGenerator fire = new WorldGenFire();
    private WorldGenerator glowStone = new WorldGenGlowStone1();
    private WorldGenerator bigBrownMushroom = new WorldGenBigMushroom(Blocks.BROWN_MUSHROOM_BLOCK, Blocks.NETHERRACK.getDefaultState());
    private WorldGenerator bigRedMushroom = new WorldGenBigMushroom(Blocks.RED_MUSHROOM_BLOCK, Blocks.NETHERRACK.getDefaultState());
    private WorldGenerator smallBrownMushroom = new WorldGenBush(Blocks.BROWN_MUSHROOM);
    private WorldGenerator smallRedMushroom = new WorldGenBush(Blocks.RED_MUSHROOM);
    private WorldGenerator thornBush = new WorldGenThornBush();
    private WorldGenerator fungalRoots = new WorldGenFungalRoots();
    private WorldGenerator enoki = new WorldGenEnoki();
    private WorldGenerator quartz = new WorldGenMinable(Blocks.QUARTZ_ORE.getDefaultState(), 14, BlockMatcher.forBlock(Blocks.NETHERRACK));
    private WorldGenerator magma = new WorldGenMinable(Blocks.MAGMA.getDefaultState(), 33, BlockMatcher.forBlock(Blocks.NETHERRACK));
    private WorldGenerator lavaTrap = new WorldGenLava(Blocks.NETHERRACK.getDefaultState(), true);

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
        else if(biome == ModBiomes.MUSHROOM_GROVE)
        {
            generateMushroomGroveFeatures(world, rand, pos);
        }
    }

    private void generateHellFeatures(World world, Random rand, BlockPos pos)
    {
        if(Settings.BiomeHell.GENERATE_LAVA_SPRINGS)
        {
            for(int i = 0; i < Settings.BiomeHell.LAVA_SPRINGS_RARITY; i++)
            {
                lavaSpring.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 4, rand.nextInt(16)));
            }
        }

        if(Settings.BiomeHell.GENERATE_FIRE)
        {
            for(int i = 0; i < rand.nextInt(Settings.BiomeHell.FIRE_RARITY); i++)
            {
                fire.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 4, rand.nextInt(16)));
            }
        }

        if(Settings.BiomeHell.GENERATE_GLOWSTONE)
        {
            for(int i = 0; i < rand.nextInt(Settings.BiomeHell.GLOWSTONE_RARITY); i++)
            {
                glowStone.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 4, rand.nextInt(16)));
            }

            for(int i = 0; i < Settings.BiomeHell.GLOWSTONE_RARITY; i++)
            {
                glowStone.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(128), rand.nextInt(16)));
            }
        }

        if(Settings.BiomeHell.GENERATE_MUSHROOMS)
        {
            if(rand.nextBoolean())
            {
                smallBrownMushroom.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(128), rand.nextInt(16)));
            }

            if(rand.nextBoolean())
            {
                smallRedMushroom.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(128), rand.nextInt(16)));
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
                lavaSpring.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 4, rand.nextInt(16)));
            }
        }

        if(Settings.BiomeRuthlessSands.GENERATE_GLOWSTONE)
        {
            for(int i = 0; i < rand.nextInt(Settings.BiomeRuthlessSands.GLOWSTONE_RARITY); i++)
            {
                glowStone.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 4, rand.nextInt(16)));
            }

            for(int i = 0; i < Settings.BiomeRuthlessSands.GLOWSTONE_RARITY; i++)
            {
                glowStone.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(128), rand.nextInt(16)));
            }
        }

        if(Settings.BiomeRuthlessSands.GENERATE_THORN_BUSHES)
        {
            for(int i = 0; i < rand.nextInt(Settings.BiomeRuthlessSands.THORN_BUSHES_RARITY); i++)
            {
                thornBush.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(96) + 32, rand.nextInt(16)));
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

    private void generateMushroomGroveFeatures(World world, Random rand, BlockPos pos)
    {
        if(Settings.BiomeMushroomGrove.GENERATE_GLOWSTONE)
        {
            for(int i = 0; i < Settings.BiomeMushroomGrove.GLOWSTONE_RARITY; i++)
            {
                glowStone.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(120) + 4, rand.nextInt(16)));
            }

            for(int i = 0; i < Settings.BiomeMushroomGrove.GLOWSTONE_RARITY; i++)
            {
                glowStone.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(128), rand.nextInt(16)));
            }
        }

        if(Settings.BiomeMushroomGrove.GENERATE_FUNGAL_ROOTS)
        {
            for(int i = 0; i < Settings.BiomeMushroomGrove.FUNGAL_ROOTS_RARITY_RARITY; i++)
            {
                fungalRoots.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(108) + 10, rand.nextInt(16)));
            }
        }

        if(Settings.BiomeMushroomGrove.GENERATE_ENOKI)
        {
            for(int i = 0; i < Settings.BiomeMushroomGrove.ENOKI_RARITY; i++)
            {
                enoki.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(108) + 10, rand.nextInt(16)));
            }
        }

        if(Settings.BiomeMushroomGrove.GENERATE_BIG_MUSHROOMS)
        {
            bigBrownMushroom = new WorldGenBigMushroom(Blocks.BROWN_MUSHROOM_BLOCK, ModBlocks.MYCELIUM.getDefaultState());
            bigRedMushroom = new WorldGenBigMushroom(Blocks.RED_MUSHROOM_BLOCK, ModBlocks.MYCELIUM.getDefaultState());

            for(int i = 0; i < Settings.BiomeMushroomGrove.BIG_MUSHROOMS_RARITY; i++)
            {
                bigBrownMushroom.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(128), rand.nextInt(16)));
            }

            for(int i = 0; i < Settings.BiomeMushroomGrove.BIG_MUSHROOMS_RARITY; i++)
            {
                bigRedMushroom.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(128), rand.nextInt(16)));
            }
        }

        if(Settings.BiomeMushroomGrove.GENERATE_SMALL_MUSHROOMS)
        {
            if(rand.nextBoolean())
            {
                smallBrownMushroom.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(128), rand.nextInt(16)));
            }

            if(rand.nextBoolean())
            {
                smallRedMushroom.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(128), rand.nextInt(16)));
            }
        }

        if(Settings.BiomeMushroomGrove.GENERATE_QUARTZ_ORE)
        {
            quartz = new WorldGenMinableMeta(ModBlocks.QUARTZ_ORE.getDefaultState(), 14, ModBlocks.NETHERRACK.getDefaultState());

            for(int i = 0; i < Settings.BiomeMushroomGrove.QUARTZ_ORE_RARITY; i++)
            {
                quartz.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(108) + 10, rand.nextInt(16)));
            }
        }
    }
}
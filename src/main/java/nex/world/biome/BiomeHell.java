package nex.world.biome;

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import nex.Settings;

import java.util.Random;

public class BiomeHell extends BiomeNetherEx
{
    public BiomeHell()
    {
        super(new BiomeProperties("Hell"));

        spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 50, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityMagmaCube.class, 2, 4, 4));
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 1, 4, 4));

        register("hell", 10);
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        if(Settings.GENERATE_LAVA_SPRINGS)
        {
            for(int i = 0; i < Settings.LAVA_SPRINGS_RARITY; i++)
            {
                lavaSpring.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 4, rand.nextInt(16) + 8));
            }
        }

        if(Settings.GENERATE_FIRE)
        {
            for(int i = 0; i < rand.nextInt(Settings.FIRE_RARITY); i++)
            {
                fire.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 4, rand.nextInt(16) + 8));
            }
        }

        if(Settings.GENERATE_GLOWSTONE)
        {
            for(int i = 0; i < rand.nextInt(Settings.GLOWSTONE_RARITY); i++)
            {
                glowStone.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 4, rand.nextInt(16) + 8));
            }

            for(int i = 0; i < Settings.GLOWSTONE_RARITY; i++)
            {
                glowStone.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(128), rand.nextInt(16) + 8));
            }
        }

        if(Settings.GENERATE_MUSHROOMS)
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

        if(Settings.GENERATE_QUARTZ_ORE)
        {
            for(int i = 0; i < Settings.QUARTZ_ORE_RARITY; i++)
            {
                quartz.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(108) + 10, rand.nextInt(16)));
            }
        }

        if(Settings.GENERATE_MAGMA)
        {
            for(int i = 0; i < Settings.MAGMA_RARITY; i++)
            {
                magma.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(9) + 28, rand.nextInt(16)));
            }
        }

        if(Settings.GENERATE_LAVA_TRAPS)
        {
            for(int i = 0; i < Settings.LAVA_TRAPS_RARITY; i++)
            {
                lavaTrap.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(108) + 10, rand.nextInt(16)));
            }
        }
    }
}
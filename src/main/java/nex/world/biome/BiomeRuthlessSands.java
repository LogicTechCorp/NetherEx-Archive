package nex.world.biome;

import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import nex.Settings;
import nex.world.gen.feature.WorldGenThornBush;

import java.util.Random;

public class BiomeRuthlessSands extends BiomeNetherEx
{
    private final WorldGenerator thornBush = new WorldGenThornBush();

    public BiomeRuthlessSands()
    {
        super(new BiomeProperties("Ruthless Sands"));

        topBlock = Blocks.SOUL_SAND.getDefaultState();

        spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 10, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 100, 4, 4));

        register("ruthlessSands", 10);
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
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

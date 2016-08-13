package nex.world.biome;

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import nex.NetherEx;
import nex.Settings;

import java.util.Random;

public class BiomeHell extends NEXBiome
{
    public BiomeHell()
    {
        super(new BiomeProperties("Hell"));

        spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 50, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityMagmaCube.class, 2, 4, 4));
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 1, 4, 4));
    }

    @Override
    public int getId()
    {
        return Settings.hellBiomeId;
    }

    @Override
    public String getName()
    {
        return NetherEx.MOD_ID + ":hell";
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
            glowStone1.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 4, rand.nextInt(16) + 8));
        }

        for(int i = 0; i < 10; i++)
        {
            glowStone2.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(128), rand.nextInt(16) + 8));
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
            magma.generate(world, rand, pos.add(rand.nextInt(16), 28 + rand.nextInt(9), rand.nextInt(16)));
        }

        for(int i = 0; i < rand.nextInt(rand.nextInt(10) + 1) + 1; i++)
        {
            fire.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 4, rand.nextInt(16) + 8));
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

package nex.world.biome;

import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import nex.NetherEx;
import nex.Settings;
import nex.entity.hostile.EntityPordenfer;

import java.util.Random;

public class BiomeForgottenSands extends NEXBiome
{
    public BiomeForgottenSands()
    {
        super(new BiomeProperties("Forgotten Sands"));

        topBlock = Blocks.SOUL_SAND.getDefaultState();

        spawnableMonsterList.add(new SpawnListEntry(EntityPordenfer.class, 100, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 20, 4, 4));
    }

    @Override
    public int getId()
    {
        return Settings.forgottenSandsBiomeId;
    }

    @Override
    public String getName()
    {
        return NetherEx.MOD_ID + ":forgottenSands";
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

        for(int i = 0; i < 16; i++)
        {
            lavaTrap.generate(world, rand, pos.add(rand.nextInt(16), rand.nextInt(108) + 10, rand.nextInt(16)));
        }

        for(int i = 0; i < 8; i++)
        {
            lavaSpring.generate(world, rand, pos.add(rand.nextInt(16) + 8, rand.nextInt(120) + 4, rand.nextInt(16) + 8));
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

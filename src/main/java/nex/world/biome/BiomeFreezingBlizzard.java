package nex.world.biome;

import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import nex.NetherEx;
import nex.Settings;
import nex.api.block.NEXBlocks;
import nex.api.world.gen.feature.WorldGenMinable;
import nex.entity.hostile.EntityWight;

import java.util.Random;

public class BiomeFreezingBlizzard extends NEXBiome
{
    private final WorldGenerator quartz = new WorldGenMinable(NEXBlocks.quartzOre.getStateFromMeta(2), 14, NEXBlocks.netherrack.getStateFromMeta(2));

    public BiomeFreezingBlizzard()
    {
        super(new BiomeProperties("Freezing Blizzard"));

        topBlock = Blocks.PACKED_ICE.getDefaultState();
        fillerBlock = NEXBlocks.netherrack.getStateFromMeta(2);

        spawnableMonsterList.add(new SpawnListEntry(EntityWight.class, 100, 4, 4));
        spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 20, 4, 4));

        register(Settings.freezingBlizzardBiomeId, NetherEx.MOD_ID + ":freezingBlizzard");
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

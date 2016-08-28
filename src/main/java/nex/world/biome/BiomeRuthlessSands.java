package nex.world.biome;

import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;
import nex.world.gen.feature.WorldGenThornBush;

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
}

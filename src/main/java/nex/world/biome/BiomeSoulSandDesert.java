package nex.world.biome;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class BiomeSoulSandDesert extends Biome
{
    public BiomeSoulSandDesert(BiomeProperties properties)
    {
        super(properties);
        this.topBlock = Blocks.SOUL_SAND.getDefaultState();
        this.fillerBlock = Blocks.SOUL_SAND.getDefaultState();
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
    }
}

package nex.api.biome;

import com.google.common.collect.Lists;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import nex.api.biome.feature.IBiomeFeature;

import java.util.List;
import java.util.Random;

public class NetherBiome extends Biome
{
    public List<IBiomeFeature> biomeFeatures = Lists.newArrayList();

    public NetherBiome(BiomeProperties properties)
    {
        super(properties);
        topBlock = Blocks.NETHERRACK.getDefaultState();
        fillerBlock = Blocks.NETHERRACK.getDefaultState();
        spawnableMonsterList.clear();
        spawnableCreatureList.clear();
        spawnableWaterCreatureList.clear();
        spawnableCaveCreatureList.clear();

    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        if(biomeFeatures.size() != 0)
        {
            for(IBiomeFeature feature : biomeFeatures)
            {
                feature.doGeneration(world, rand, pos, this);
            }
        }
    }
}

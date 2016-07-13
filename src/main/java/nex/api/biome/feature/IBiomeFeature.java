package nex.api.biome.feature;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nex.api.biome.NetherBiome;

import java.util.Random;

public interface IBiomeFeature
{
    void doGeneration(World world, Random rand, BlockPos pos, NetherBiome biome);
}

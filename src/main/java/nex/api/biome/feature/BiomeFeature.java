package nex.api.biome.feature;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import nex.api.biome.NetherBiome;

import java.util.Random;

public abstract class BiomeFeature extends WorldGenerator implements IBiomeFeature
{
    private int genAttempts;
    private int minY;
    private int maxY;

    public BiomeFeature(int genAttemptsIn, int minYIn, int maxYIn)
    {
        genAttempts = genAttemptsIn;
        minY = minYIn;
        maxY = maxYIn;
    }

    @Override
    public void doGeneration(World world, Random rand, BlockPos pos, NetherBiome biome)
    {
        if(minY < 1)
        {
            minY = 1;
        }
        if(maxY > 127)
        {
            maxY = 127;
        }

        for(int j = 0; j < genAttempts; j++)
        {
            int x = pos.getX() + rand.nextInt(16) + 8;
            int y = rand.ints(minY, (maxY + 1)).findFirst().getAsInt();
            int z = pos.getZ() + rand.nextInt(16) + 8;

            BlockPos newPos = new BlockPos(x, y, z);

            if(world.getBiome(newPos) == biome)
            {
                generate(world, rand, newPos);
            }
        }
    }

    public abstract boolean generate(World world, Random rand, BlockPos pos);
}

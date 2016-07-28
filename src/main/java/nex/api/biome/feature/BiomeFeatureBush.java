package nex.api.biome.feature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BiomeFeatureBush extends BiomeFeature
{
    private final IBlockState bushBlock;
    private final IBlockState target;

    public BiomeFeatureBush(int genAttemptsIn, int minYIn, int maxYIn, IBlockState bushBlockIn, IBlockState targetIn)
    {
        super(genAttemptsIn, minYIn, maxYIn);

        bushBlock = bushBlockIn;
        target = targetIn;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        for(int i = 0; i < 64; ++i)
        {
            BlockPos newPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if(world.isAirBlock(newPos) && (!world.provider.getHasNoSky() || newPos.getY() < world.getHeight() - 1) && world.getBlockState(newPos.down()) == target)
            {
                world.setBlockState(newPos, bushBlock, 2);
            }
        }

        return true;
    }
}

package nex.api.biome.feature;

import net.minecraft.block.BlockBush;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BiomeFeatureBush extends BiomeFeature
{
    private BlockBush bushBlock;

    public BiomeFeatureBush(BlockBush bushBlockIn, int amountPerChunk, int genAttempts, int minY, int maxY)
    {
        super(amountPerChunk, genAttempts, minY, maxY);
        bushBlock = bushBlockIn;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        for(int i = 0; i < 64; ++i)
        {
            BlockPos blockpos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if(world.isAirBlock(blockpos) && (!world.provider.getHasNoSky() || blockpos.getY() < world.getHeight() - 1) && bushBlock.canBlockStay(world, blockpos, bushBlock.getDefaultState()))
            {
                world.setBlockState(blockpos, bushBlock.getDefaultState(), 2);
            }
        }

        return true;
    }
}

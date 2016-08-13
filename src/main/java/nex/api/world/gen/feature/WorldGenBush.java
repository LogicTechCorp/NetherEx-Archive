package nex.api.world.gen.feature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenBush extends WorldGenerator
{
    private final IBlockState bushBlock;
    private final IBlockState blockToPlaceOn;

    public WorldGenBush(IBlockState bushBlockIn, IBlockState blockToPlaceOnIn)
    {
        bushBlock = bushBlockIn;
        blockToPlaceOn = blockToPlaceOnIn;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        for(int i = 0; i < 64; ++i)
        {
            BlockPos newPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if(world.isAirBlock(newPos) && (!world.provider.getHasNoSky() || newPos.getY() < world.getHeight() - 1) && world.getBlockState(newPos.down()) == blockToPlaceOn)
            {
                world.setBlockState(newPos, bushBlock, 2);
            }
        }

        return true;
    }
}

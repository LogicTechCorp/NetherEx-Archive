package nex.api.biome.feature;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BiomeFeatureLava extends BiomeFeature
{
    private final Block block;
    private final boolean insideRock;

    public BiomeFeatureLava(Block blockToPLaceOn, boolean insideRockIn, int amountPerChunk, int genAttempts, int minY, int maxY)
    {
        super(amountPerChunk, genAttempts, minY, maxY);
        block = blockToPLaceOn;
        insideRock = insideRockIn;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        if(world.getBlockState(pos.up()).getBlock() != block)
        {
            return false;
        }
        else if(!world.isAirBlock(pos) && world.getBlockState(pos).getBlock() != block)
        {
            return false;
        }
        else
        {
            int i = 0;

            if(world.getBlockState(pos.west()).getBlock() == block)
            {
                ++i;
            }

            if(world.getBlockState(pos.east()).getBlock() == block)
            {
                ++i;
            }

            if(world.getBlockState(pos.north()).getBlock() == block)
            {
                ++i;
            }

            if(world.getBlockState(pos.south()).getBlock() == block)
            {
                ++i;
            }

            if(world.getBlockState(pos.down()).getBlock() == block)
            {
                ++i;
            }

            int j = 0;

            if(world.isAirBlock(pos.west()))
            {
                ++j;
            }

            if(world.isAirBlock(pos.east()))
            {
                ++j;
            }

            if(world.isAirBlock(pos.north()))
            {
                ++j;
            }

            if(world.isAirBlock(pos.south()))
            {
                ++j;
            }

            if(world.isAirBlock(pos.down()))
            {
                ++j;
            }

            if(!insideRock && i == 4 && j == 1 || i == 5)
            {
                IBlockState iblockstate = Blocks.FLOWING_LAVA.getDefaultState();
                world.setBlockState(pos, iblockstate, 2);
                world.immediateBlockTick(pos, iblockstate, rand);
            }

            return true;
        }
    }
}

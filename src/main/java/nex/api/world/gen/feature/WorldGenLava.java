package nex.api.world.gen.feature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenLava extends WorldGenerator
{
    private final IBlockState blockToPLaceOn;
    private final boolean insideRock;

    public WorldGenLava(IBlockState blockToPLaceOnIn, boolean insideRockIn)
    {
        blockToPLaceOn = blockToPLaceOnIn;
        insideRock = insideRockIn;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        if(world.getBlockState(pos.up()) != blockToPLaceOn)
        {
            return false;
        }
        else if(!world.isAirBlock(pos) && world.getBlockState(pos) != blockToPLaceOn)
        {
            return false;
        }
        else
        {
            int i = 0;

            if(world.getBlockState(pos.west()) == blockToPLaceOn)
            {
                ++i;
            }

            if(world.getBlockState(pos.east()) == blockToPLaceOn)
            {
                ++i;
            }

            if(world.getBlockState(pos.north()) == blockToPLaceOn)
            {
                ++i;
            }

            if(world.getBlockState(pos.south()) == blockToPLaceOn)
            {
                ++i;
            }

            if(world.getBlockState(pos.down()) == blockToPLaceOn)
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

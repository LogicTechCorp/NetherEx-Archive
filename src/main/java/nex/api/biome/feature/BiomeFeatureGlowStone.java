package nex.api.biome.feature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BiomeFeatureGlowStone extends BiomeFeature
{
    public BiomeFeatureGlowStone(int genAttemptsIn, int minYIn, int maxYIn)
    {
        super(genAttemptsIn, minYIn, maxYIn);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos.up());

        if(!world.isAirBlock(pos))
        {
            return false;
        }
        else if(!state.getMaterial().isSolid() || state.getBlock() == Blocks.SOUL_SAND || state.getBlock() == Blocks.GRAVEL)
        {
            return false;
        }
        else
        {
            world.setBlockState(pos, Blocks.GLOWSTONE.getDefaultState(), 2);

            for(int i = 0; i < 1500; ++i)
            {
                BlockPos newPos = pos.add(rand.nextInt(8) - rand.nextInt(8), -rand.nextInt(12), rand.nextInt(8) - rand.nextInt(8));

                if(world.isAirBlock(newPos))
                {
                    int j = 0;

                    for(EnumFacing enumfacing : EnumFacing.values())
                    {
                        if(world.getBlockState(newPos.offset(enumfacing)).getBlock() == Blocks.GLOWSTONE)
                        {
                            ++j;
                        }

                        if(j > 1)
                        {
                            break;
                        }
                    }

                    if(j == 1)
                    {
                        world.setBlockState(newPos, Blocks.GLOWSTONE.getDefaultState(), 2);
                    }
                }
            }

            return true;
        }
    }
}

package nex.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import nex.init.ModBlocks;

import java.util.Random;

public class WorldGenThornBush extends WorldGenerator
{
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        for(int i = 0; i < 64; ++i)
        {
            BlockPos newPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
            Block blockDown = world.getBlockState(newPos.down()).getBlock();

            if((blockDown == Blocks.SAND || blockDown == Blocks.SOUL_SAND) && ModBlocks.THORN.canPlaceBlockAt(world, newPos))
            {
                ModBlocks.THORN.generate(world, rand, newPos);
            }
        }

        return true;
    }
}

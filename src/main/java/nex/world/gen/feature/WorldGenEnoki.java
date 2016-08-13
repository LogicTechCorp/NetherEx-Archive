package nex.world.gen.feature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import nex.api.block.NEXBlocks;
import nex.block.BlockEnokiCap;

import java.util.Random;

public class WorldGenEnoki extends WorldGenerator
{
    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);

        if(world.isAirBlock(pos.down()) && state == NEXBlocks.netherrack.getDefaultState())
        {
            BlockEnokiCap.generatePlant(world, pos, rand, 8);
            return true;
        }

        return false;
    }
}

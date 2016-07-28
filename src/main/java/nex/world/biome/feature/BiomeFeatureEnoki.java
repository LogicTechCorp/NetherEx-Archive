package nex.world.biome.feature;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nex.api.biome.feature.BiomeFeature;
import nex.block.BlockEnokiCap;
import nex.registry.ModBlocks;

import java.util.Random;

public class BiomeFeatureEnoki extends BiomeFeature
{
    public BiomeFeatureEnoki(int genAttemptsIn, int minYIn, int maxYIn)
    {
        super(genAttemptsIn, minYIn, maxYIn);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        Block block = world.getBlockState(pos).getBlock();

        if(world.isAirBlock(pos.down()) && block == Blocks.NETHERRACK || block == ModBlocks.NETHERRACK)
        {
            BlockEnokiCap.generatePlant(world, pos, rand, 8);
            return true;
        }

        return false;
    }
}

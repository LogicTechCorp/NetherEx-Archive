package nex.api.biome.feature;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BiomeFeatureFire extends BiomeFeature
{
    public BiomeFeatureFire(int amountPerChunk, int genAttempts, int minY, int maxY)
    {
        super(amountPerChunk, genAttempts, minY, maxY);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        for(int i = 0; i < 64; ++i)
        {
            BlockPos blockPos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            Material material = world.getBlockState(blockPos.down()).getMaterial();

            if(world.isAirBlock(blockPos) && material.isSolid() && material != Material.ICE && material != Material.PACKED_ICE)
            {
                world.setBlockState(blockPos, Blocks.FIRE.getDefaultState(), 2);
            }
        }

        return true;
    }
}

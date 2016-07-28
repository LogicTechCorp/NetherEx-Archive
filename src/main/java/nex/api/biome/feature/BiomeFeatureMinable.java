package nex.api.biome.feature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BiomeFeatureMinable extends BiomeFeature
{
    private final IBlockState oreBlock;
    private final int veinSize;
    private final IBlockState targetBlock;

    public BiomeFeatureMinable(int genAttemptsIn, int minYIn, int maxYIn, IBlockState oreBlockIn, int veinSizeIn, IBlockState targetBlockIn)
    {
        super(genAttemptsIn, minYIn, maxYIn);

        oreBlock = oreBlockIn;
        veinSize = veinSizeIn;
        targetBlock = targetBlockIn;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        float f = rand.nextFloat() * (float) Math.PI;
        double d0 = (double) ((float) (pos.getX() + 8) + MathHelper.sin(f) * (float) veinSize / 8.0F);
        double d1 = (double) ((float) (pos.getX() + 8) - MathHelper.sin(f) * (float) veinSize / 8.0F);
        double d2 = (double) ((float) (pos.getZ() + 8) + MathHelper.cos(f) * (float) veinSize / 8.0F);
        double d3 = (double) ((float) (pos.getZ() + 8) - MathHelper.cos(f) * (float) veinSize / 8.0F);
        double d4 = (double) (pos.getY() + rand.nextInt(3) - 2);
        double d5 = (double) (pos.getY() + rand.nextInt(3) - 2);

        for(int i = 0; i < veinSize; ++i)
        {
            float f1 = (float) i / (float) veinSize;
            double d6 = d0 + (d1 - d0) * (double) f1;
            double d7 = d4 + (d5 - d4) * (double) f1;
            double d8 = d2 + (d3 - d2) * (double) f1;
            double d9 = rand.nextDouble() * (double) veinSize / 16.0D;
            double d10 = (double) (MathHelper.sin((float) Math.PI * f1) + 1.0F) * d9 + 1.0D;
            double d11 = (double) (MathHelper.sin((float) Math.PI * f1) + 1.0F) * d9 + 1.0D;
            int j = MathHelper.floor_double(d6 - d10 / 2.0D);
            int k = MathHelper.floor_double(d7 - d11 / 2.0D);
            int l = MathHelper.floor_double(d8 - d10 / 2.0D);
            int i1 = MathHelper.floor_double(d6 + d10 / 2.0D);
            int j1 = MathHelper.floor_double(d7 + d11 / 2.0D);
            int k1 = MathHelper.floor_double(d8 + d10 / 2.0D);

            for(int l1 = j; l1 <= i1; ++l1)
            {
                double d12 = ((double) l1 + 0.5D - d6) / (d10 / 2.0D);

                if(d12 * d12 < 1.0D)
                {
                    for(int i2 = k; i2 <= j1; ++i2)
                    {
                        double d13 = ((double) i2 + 0.5D - d7) / (d11 / 2.0D);

                        if(d12 * d12 + d13 * d13 < 1.0D)
                        {
                            for(int j2 = l; j2 <= k1; ++j2)
                            {
                                double d14 = ((double) j2 + 0.5D - d8) / (d10 / 2.0D);

                                if(d12 * d12 + d13 * d13 + d14 * d14 < 1.0D)
                                {
                                    BlockPos newPos = new BlockPos(l1, i2, j2);
                                    IBlockState state = world.getBlockState(newPos);

                                    if(state.getBlock().isReplaceableOreGen(state, world, newPos, BlockMatcher.forBlock(targetBlock.getBlock())) && state == targetBlock)
                                    {
                                        world.setBlockState(newPos, oreBlock, 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}

package logictechcorp.netherex.world.generation.feature;

import com.mojang.datafixers.Dynamic;
import logictechcorp.netherex.block.NetherExBlocks;
import logictechcorp.netherex.data.NetherExTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.BigBrownMushroomFeature;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;

import java.util.function.Function;

public class HugeBrownElderMushroomFeature extends BigBrownMushroomFeature
{
    public HugeBrownElderMushroomFeature(Function<Dynamic<?>, ? extends BigMushroomFeatureConfig> configFactory)
    {
        super(configFactory);
    }

    @Override
    protected boolean func_227209_a_(IWorld world, BlockPos pos, int height, BlockPos.Mutable mutablePos, BigMushroomFeatureConfig config)
    {
        int yPos = pos.getY();

        if(yPos >= 1 && yPos + height + 1 < world.getMaxHeight())
        {
            Block block = world.getBlockState(pos.down()).getBlock();

            if(!isStone(block) && !isDirt(block) && !NetherExTags.Blocks.NETHERRACK.contains(block) && block != NetherExBlocks.HYPHAE.get())
            {
                return false;
            }
            else
            {
                for(int y = 0; y <= height; y++)
                {
                    int radius = this.func_225563_a_(-1, -1, config.field_227274_c_, y);

                    for(int x = -radius; x <= radius; x++)
                    {
                        for(int z = -radius; z <= radius; ++z)
                        {
                            BlockState state = world.getBlockState(mutablePos.setPos(pos).move(x, y, z));

                            if(!state.isAir(world, mutablePos) && !state.isIn(BlockTags.LEAVES))
                            {
                                return false;
                            }
                        }
                    }
                }

                return true;
            }
        }
        else
        {
            return false;
        }
    }
}

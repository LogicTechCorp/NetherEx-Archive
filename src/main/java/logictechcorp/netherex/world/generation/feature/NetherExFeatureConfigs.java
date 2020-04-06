package logictechcorp.netherex.world.generation.feature;

import logictechcorp.netherex.block.NetherExBlocks;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;

public class NetherExFeatureConfigs
{
    // @formatter:off
    public static final BigMushroomFeatureConfig HUGE_BROWN_ELDER_MUSHROOM = new BigMushroomFeatureConfig(new SimpleBlockStateProvider(NetherExBlocks.BROWN_ELDER_MUSHROOM_CAP.get().getDefaultState()), new SimpleBlockStateProvider(NetherExBlocks.ELDER_MUSHROOM_STEM.get().getDefaultState()), 3);
    public static final BigMushroomFeatureConfig HUGE_RED_ELDER_MUSHROOM   = new BigMushroomFeatureConfig(new SimpleBlockStateProvider(NetherExBlocks.RED_ELDER_MUSHROOM_CAP.get().getDefaultState()), new SimpleBlockStateProvider(NetherExBlocks.ELDER_MUSHROOM_STEM.get().getDefaultState()), 2);
    // @formatter:on
}

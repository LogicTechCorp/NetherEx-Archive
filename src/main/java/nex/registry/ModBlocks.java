package nex.registry;

import net.minecraft.block.Block;
import nex.block.*;

public class ModBlocks
{
    public static final Block NETHERRACK;
    public static final Block QUARTZ_ORE;
    public static final Block MYCELIUM;
    public static final Block ENOKI_STEM;
    public static final Block ENOKI_CAP;

    static
    {
        NETHERRACK = new BlockModNetherrack();
        QUARTZ_ORE = new BlockModQuartzOre();
        MYCELIUM = new BlockModMycelium();
        ENOKI_STEM = new BlockEnokiStem();
        ENOKI_CAP = new BlockEnokiCap();
    }

    public static void register()
    {
    }
}

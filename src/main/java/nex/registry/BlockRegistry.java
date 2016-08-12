package nex.registry;

import nex.api.block.NEXBlocks;
import nex.block.*;

public class BlockRegistry
{
    public static void init()
    {
        NEXBlocks.netherrack = new BlockModNetherrack();
        NEXBlocks.quartzOre = new BlockModQuartzOre();
        NEXBlocks.mycelium = new BlockModMycelium();
        NEXBlocks.enokiStem = new BlockEnokiStem();
        NEXBlocks.enokiCap = new BlockEnokiCap();
    }
}

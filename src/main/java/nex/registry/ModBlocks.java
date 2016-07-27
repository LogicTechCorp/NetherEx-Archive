package nex.registry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import nex.block.BlockModMycelium;
import nex.block.BlockVariantContainer;
import nex.enums.EnumNetherrackType;

public class ModBlocks
{
    public static final Block NETHERRACK;
    public static final Block QUARTZ_ORE;
    public static final Block MYCELIUM;

    static
    {
        NETHERRACK = new BlockVariantContainer<>("netherrack", Material.ROCK, EnumNetherrackType.class).setHardness(0.4F);
        QUARTZ_ORE = new BlockVariantContainer<>("quartz_ore", Material.ROCK, EnumNetherrackType.class).setHardness(3.0F).setResistance(5.0F);
        MYCELIUM = new BlockModMycelium("mycelium", Material.ROCK).setHardness(0.4F).setTickRandomly(true);
    }

    public static void register()
    {
    }
}

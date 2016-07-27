package nex.registry;

import net.minecraftforge.oredict.OreDictionary;

public class ModOreDict
{
    public static void register()
    {
        OreDictionary.registerOre("netherrack", ModBlocks.NETHERRACK);
        OreDictionary.registerOre("oreQuartz", ModBlocks.QUARTZ_ORE);
        OreDictionary.registerOre("mycelium", ModBlocks.MYCELIUM);
    }
}

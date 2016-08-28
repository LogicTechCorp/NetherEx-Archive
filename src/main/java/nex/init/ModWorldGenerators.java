package nex.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import nex.world.gen.WorldGenNether;

public class ModWorldGenerators
{
    public static void init()
    {
        GameRegistry.registerWorldGenerator(new WorldGenNether(), 0);
    }
}
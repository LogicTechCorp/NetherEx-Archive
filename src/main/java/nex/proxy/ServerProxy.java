package nex.proxy;

import net.minecraft.world.DimensionType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import nex.NetherEx;
import nex.world.WorldProviderNether;

public class ServerProxy implements IProxy
{
    @Override
    public void preInit()
    {

    }

    @Override
    public void init()
    {
        ObfuscationReflectionHelper.setPrivateValue(DimensionType.class, DimensionType.NETHER, WorldProviderNether.class, "field_186077_g", "clazz");
        NetherEx.logger.info("Replaced Nether World Provider!");
    }

    @Override
    public void postInit()
    {

    }
}

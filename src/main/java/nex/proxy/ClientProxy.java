package nex.proxy;

import net.minecraft.world.DimensionType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import nex.client.model.ModelRegistry;
import nex.world.WorldProviderNether;

public class ClientProxy implements IProxy
{
    @Override
    public void preInit()
    {
        ModelRegistry.INSTANCE.initModels();
    }

    @Override
    public void init()
    {
        ObfuscationReflectionHelper.setPrivateValue(DimensionType.class, DimensionType.NETHER, WorldProviderNether.class, "field_186077_g", "clazz");
    }

    @Override
    public void postInit()
    {

    }
}

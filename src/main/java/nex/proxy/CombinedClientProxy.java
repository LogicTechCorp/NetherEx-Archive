package nex.proxy;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.client.model.ModelRegistry;
import nex.client.render.RenderRegistry;

@SideOnly(Side.CLIENT)
public class CombinedClientProxy implements IProxy
{
    @Override
    public void preInit()
    {
        ModelRegistry.init();
        RenderRegistry.init();
    }

    @Override
    public void init()
    {

    }

    @Override
    public void postInit()
    {

    }
}

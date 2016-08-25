package nex.proxy;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.client.model.ModModels;

@SideOnly(Side.CLIENT)
public class CombinedClientProxy implements IProxy
{
    @Override
    public void preInit()
    {
        ModModels.init();
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
package nex.proxy;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.client.model.ModModels;
import nex.client.render.RenderBrownMogus;
import nex.client.render.RenderRedMogus;
import nex.entity.passive.EntityBrownMogus;
import nex.entity.passive.EntityRedMogus;

@SideOnly(Side.CLIENT)
public class CombinedClientProxy implements IProxy
{
    @Override
    public void preInit()
    {
        ModModels.register();

        RenderingRegistry.registerEntityRenderingHandler(EntityRedMogus.class, RenderRedMogus::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBrownMogus.class, RenderBrownMogus::new);
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

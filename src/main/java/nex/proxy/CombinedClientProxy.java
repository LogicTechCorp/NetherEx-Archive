package nex.proxy;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.client.model.ModModels;
import nex.client.render.entity.RenderBrownMogus;
import nex.client.render.entity.RenderRedMogus;
import nex.client.render.entity.RenderWight;
import nex.entity.hostile.EntityWight;
import nex.entity.neutral.EntityBrownMogus;
import nex.entity.neutral.EntityRedMogus;

@SideOnly(Side.CLIENT)
public class CombinedClientProxy implements IProxy
{
    @Override
    public void preInit()
    {
        ModModels.register();

        RenderingRegistry.registerEntityRenderingHandler(EntityRedMogus.class, RenderRedMogus::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBrownMogus.class, RenderBrownMogus::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWight.class, RenderWight::new);
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

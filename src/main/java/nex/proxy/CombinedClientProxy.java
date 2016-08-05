package nex.proxy;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.client.model.ModModels;
import nex.client.render.entity.RenderMogus;
import nex.client.render.entity.RenderWight;
import nex.entity.hostile.EntityWight;
import nex.entity.neutral.EntityMogus;

@SideOnly(Side.CLIENT)
public class CombinedClientProxy implements IProxy
{
    @Override
    public void preInit()
    {
        ModModels.register();

        RenderingRegistry.registerEntityRenderingHandler(EntityMogus.class, RenderMogus::new);
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

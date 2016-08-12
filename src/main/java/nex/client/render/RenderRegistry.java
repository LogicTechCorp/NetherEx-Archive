package nex.client.render;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import nex.client.render.entity.RenderMogus;
import nex.client.render.entity.RenderPordenfer;
import nex.client.render.entity.RenderTortoise;
import nex.client.render.entity.RenderWight;
import nex.entity.hostile.EntityPordenfer;
import nex.entity.hostile.EntityWight;
import nex.entity.neutral.EntityMogus;
import nex.entity.passive.EntityTortoise;

public class RenderRegistry
{
    public static void init()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityMogus.class, RenderMogus::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWight.class, RenderWight::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPordenfer.class, RenderPordenfer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTortoise.class, RenderTortoise::new);
    }
}

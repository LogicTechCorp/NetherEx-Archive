package nex.client.render.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import nex.NetherEx;
import nex.client.model.entity.ModelMogus;

public class RenderRedMogus extends RenderLiving
{
    private static final ResourceLocation ENTITY_TEXTURE = new ResourceLocation(NetherEx.MOD_ID, "textures/entity/mogus_red.png");

    public RenderRedMogus(RenderManager manager)
    {
        super(manager, new ModelMogus(), 0.3F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return ENTITY_TEXTURE;
    }
}

package nex.client.render.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.NetherEx;
import nex.client.model.entity.ModelMogus;
import nex.entity.neutral.EntityMogus;

@SideOnly(Side.CLIENT)
public class RenderMogus extends RenderLiving<EntityMogus>
{
    private static final ResourceLocation RED_MOGUS_TEXTURE = new ResourceLocation(NetherEx.MOD_ID, "textures/entity/mogus_red.png");
    private static final ResourceLocation BROWN_MOGUS_TEXTURE = new ResourceLocation(NetherEx.MOD_ID, "textures/entity/mogus_brown.png");

    public RenderMogus(RenderManager manager)
    {
        super(manager, new ModelMogus(), 0.3F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMogus mogus)
    {
        return mogus.getType() == 0 ? RED_MOGUS_TEXTURE : BROWN_MOGUS_TEXTURE;
    }
}

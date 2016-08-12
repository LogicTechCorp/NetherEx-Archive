package nex.client.render.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.NetherEx;
import nex.client.model.entity.ModelPordenfer;

@SideOnly(Side.CLIENT)
public class RenderPordenfer extends RenderLiving
{
    private static final ResourceLocation PORDENFER_TEXTURE = new ResourceLocation(NetherEx.MOD_ID, "textures/entity/pordenfer.png");

    public RenderPordenfer(RenderManager manager)
    {
        super(manager, new ModelPordenfer(), 1.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return PORDENFER_TEXTURE;
    }
}

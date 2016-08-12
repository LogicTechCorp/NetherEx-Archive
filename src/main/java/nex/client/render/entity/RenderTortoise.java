package nex.client.render.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.NetherEx;
import nex.client.model.entity.ModelTortoise;

@SideOnly(Side.CLIENT)
public class RenderTortoise extends RenderLiving
{
    private static final ResourceLocation TORTOISE_TEXTURE = new ResourceLocation(NetherEx.MOD_ID, "textures/entity/tortoise.png");

    public RenderTortoise(RenderManager manager)
    {
        super(manager, new ModelTortoise(), 1.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return TORTOISE_TEXTURE;
    }
}

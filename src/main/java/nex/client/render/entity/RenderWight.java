package nex.client.render.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.NetherEx;
import nex.client.model.entity.ModelWight;

@SideOnly(Side.CLIENT)
public class RenderWight extends RenderLiving
{
    private static final ResourceLocation WIGHT_TEXTURE = new ResourceLocation(NetherEx.MOD_ID, "textures/entity/wight.png");

    public RenderWight(RenderManager manager)
    {
        super(manager, new ModelWight(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return WIGHT_TEXTURE;
    }
}

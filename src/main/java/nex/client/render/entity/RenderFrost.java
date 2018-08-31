package nex.client.render.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.client.model.entity.ModelFrost;
import nex.entity.monster.EntityFrost;
import nex.init.NetherExTextures;

@SideOnly(Side.CLIENT)
public class RenderFrost extends RenderLiving<EntityFrost>
{
    public RenderFrost(RenderManager manager)
    {
        super(manager, new ModelFrost(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFrost entity)
    {
        return NetherExTextures.FROST;
    }

}

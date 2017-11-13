/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nex.client.render.entity;

import net.minecraft.client.model.IMultipassModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.entity.item.EntityObsidianBoat;
import nex.init.NetherExTextures;

@SideOnly(Side.CLIENT)
public class RenderObsidianBoat extends Render<EntityObsidianBoat>
{
    private ModelBase modelBoat = new ModelBoat();

    public RenderObsidianBoat(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
        shadowSize = 0.5F;
    }

    @Override
    public void doRender(EntityObsidianBoat entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y + 0.375F, (float) z);
        setupRotation(entity, entityYaw, partialTicks);
        bindEntityTexture(entity);

        if(renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(getTeamColor(entity));
        }

        modelBoat.render(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        if(renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    private void setupRotation(EntityObsidianBoat entity, float p_188311_2_, float p_188311_3_)
    {
        GlStateManager.rotate(180.0F - p_188311_2_, 0.0F, 1.0F, 0.0F);
        float f = (float) entity.getTimeSinceHit() - p_188311_3_;
        float f1 = entity.getDamageTaken() - p_188311_3_;

        if(f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        if(f > 0.0F)
        {
            GlStateManager.rotate(MathHelper.sin(f) * f * f1 / 10.0F * (float) entity.getForwardDirection(), 1.0F, 0.0F, 0.0F);
        }

        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityObsidianBoat entity)
    {
        return NetherExTextures.OBSIDIAN_BOAT;
    }

    @Override
    public boolean isMultipass()
    {
        return true;
    }

    @Override
    public void renderMultipass(EntityObsidianBoat entity, double x, double y, double z, float p_188300_8_, float p_188300_9_)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y + 0.375F, (float) z);
        setupRotation(entity, p_188300_8_, p_188300_9_);
        bindEntityTexture(entity);
        ((IMultipassModel) modelBoat).renderMultipass(entity, p_188300_9_, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GlStateManager.popMatrix();
    }
}

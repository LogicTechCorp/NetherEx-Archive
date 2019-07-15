/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
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

package logictechcorp.netherex.client.model.entity;

import logictechcorp.netherex.entity.monster.EntityLostSoul;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelLostSoul extends ModelBase
{
    private ModelRenderer normalHead;
    private ModelRenderer enlargedHead;
    private ModelRenderer body;

    public ModelLostSoul()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.normalHead = new ModelRenderer(this, 0, 0);
        this.normalHead.setRotationPoint(0.0F, 14.0F, 0.0F);
        this.normalHead.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
        this.body = new ModelRenderer(this, 38, 0);
        this.body.setRotationPoint(0.6F, 16.3F, -0.9F);
        this.body.addBox(-4.0F, 0.0F, -3.0F, 7, 9, 6, 0.0F);
        this.setRotateAngle(body, 0.5235987755982988F, 0.0F, 0.0F);
        this.enlargedHead = new ModelRenderer(this, 0, 17);
        this.enlargedHead.setRotationPoint(0.0F, 14.0F, 0.0F);
        this.enlargedHead.addBox(-5.0F, -7.5F, -5.0F, 10, 12, 10, 0.0F);
        this.setRotateAngle(enlargedHead, -0.1308996938995747F, 0.0F, 0.1308996938995747F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        EntityLostSoul lostSoul = (EntityLostSoul) entity;

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);

        if(!lostSoul.hasEnlargedHead())
        {
            this.normalHead.render(scale);
        }
        else
        {
            this.enlargedHead.render(scale);
        }

        this.body.render(scale);
        GlStateManager.disableBlend();
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scaleFactor, Entity entity)
    {
        this.body.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.15F + 1.0F) + 0.4F;
    }

    private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

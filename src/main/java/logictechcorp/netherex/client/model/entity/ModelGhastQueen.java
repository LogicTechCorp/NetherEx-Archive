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

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelGhastQueen extends ModelBase
{
    private final ModelRenderer backRightTentacle;
    private final ModelRenderer back;
    private final ModelRenderer body;
    private final ModelRenderer frontLeftTentacle;
    private final ModelRenderer middleRightTentacle;
    private final ModelRenderer face;
    private final ModelRenderer middleLeftTentacle;
    private final ModelRenderer frontRightTentacle;
    private final ModelRenderer backLeftTentacle;

    public ModelGhastQueen()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.backRightTentacle = new ModelRenderer(this, 92, 86);
        this.backRightTentacle.setRotationPoint(8.0F, 7.0F, 9.5F);
        this.backRightTentacle.addBox(-1.0F, 0.0F, -2.0F, 2, 17, 2, 0.0F);
        this.setRotationAngle(this.backRightTentacle, 0.5818927726149095F, 0.0F, 0.0F);
        this.frontLeftTentacle = new ModelRenderer(this, 76, 44);
        this.frontLeftTentacle.setRotationPoint(-10.0F, 12.0F, -7.5F);
        this.frontLeftTentacle.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, 0.0F);
        this.setRotationAngle(this.frontLeftTentacle, 0.42830379843940847F, 0.0F, 0.0F);
        this.face = new ModelRenderer(this, 0, 0);
        this.face.setRotationPoint(0.0F, -5.0F, -13.0F);
        this.face.addBox(-8.0F, 0.0F, -2.0F, 16, 16, 4, 0.0F);
        this.frontRightTentacle = new ModelRenderer(this, 92, 44);
        this.frontRightTentacle.setRotationPoint(10.0F, 12.0F, -7.5F);
        this.frontRightTentacle.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, 0.0F);
        this.setRotationAngle(this.frontRightTentacle, 0.40002946455710037F, 0.0F, 0.0F);
        this.back = new ModelRenderer(this, 0, 56);
        this.back.setRotationPoint(0.0F, -3.0F, 9.0F);
        this.back.addBox(-8.0F, 0.0F, -4.0F, 16, 15, 8, 0.0F);
        this.middleLeftTentacle = new ModelRenderer(this, 76, 65);
        this.middleLeftTentacle.setRotationPoint(-9.5F, 12.0F, 0.5F);
        this.middleLeftTentacle.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, 0.0F);
        this.setRotationAngle(this.middleLeftTentacle, 0.24870941840919195F, 0.0F, 0.0F);
        this.backLeftTentacle = new ModelRenderer(this, 76, 86);
        this.backLeftTentacle.setRotationPoint(-8.0F, 7.0F, 8.5F);
        this.backLeftTentacle.addBox(-1.0F, 0.0F, -1.0F, 2, 17, 2, 0.0F);
        this.setRotationAngle(this.backLeftTentacle, 0.20821777976292352F, 0.0F, 0.0F);
        this.middleRightTentacle = new ModelRenderer(this, 92, 65);
        this.middleRightTentacle.setRotationPoint(9.5F, 12.0F, 0.5F);
        this.middleRightTentacle.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, 0.0F);
        this.setRotationAngle(this.middleRightTentacle, 0.5682792044493538F, 0.0F, 0.0F);
        this.body = new ModelRenderer(this, 0, 20);
        this.body.setRotationPoint(0.0F, -7.0F, -3.0F);
        this.body.addBox(-10.0F, 0.0F, -8.0F, 20, 20, 16, 0.0F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.6F, 0.0F);
        this.face.render(scale);
        this.body.render(scale);
        this.back.render(scale);
        this.frontRightTentacle.render(scale);
        this.middleRightTentacle.render(scale);
        this.backRightTentacle.render(scale);
        this.frontLeftTentacle.render(scale);
        this.middleLeftTentacle.render(scale);
        this.backLeftTentacle.render(scale);
        GlStateManager.popMatrix();
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity)
    {
        this.frontRightTentacle.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 0) + 0.4F;
        this.middleRightTentacle.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 1) + 0.4F;
        this.backRightTentacle.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 2) + 0.4F;
        this.frontLeftTentacle.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 3) + 0.4F;
        this.middleLeftTentacle.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 4) + 0.4F;
        this.backLeftTentacle.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 5) + 0.4F;
    }

    private void setRotationAngle(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}

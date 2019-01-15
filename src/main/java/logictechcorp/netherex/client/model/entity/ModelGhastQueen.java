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

public class ModelGhastQueen extends ModelBase
{
    private ModelRenderer face;
    private ModelRenderer body;
    private ModelRenderer back;
    private ModelRenderer eggSack;
    private ModelRenderer frontRightJoint;
    private ModelRenderer middleRightJoint;
    private ModelRenderer backRightJoint;
    private ModelRenderer frontLeftJoint;
    private ModelRenderer middleLeftJoint;
    private ModelRenderer backLeftJoint;
    private ModelRenderer frontRightTentacle;
    private ModelRenderer middleRightTentacle;
    private ModelRenderer backRightTentacle;
    private ModelRenderer frontLeftTentacle;
    private ModelRenderer middleLeftTentacle;
    private ModelRenderer backLeftTentacle;

    public ModelGhastQueen()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;

        this.face = new ModelRenderer(this, 0, 0);
        this.face.setRotationPoint(0.0F, -5.0F, -13.0F);
        this.face.addBox(-8.0F, 0.0F, -2.0F, 16, 16, 4);
        this.body = new ModelRenderer(this, 0, 20);
        this.body.setRotationPoint(0.0F, -7.0F, -3.0F);
        this.body.addBox(-10.0F, 0.0F, -8.0F, 20, 20, 16);
        this.back = new ModelRenderer(this, 0, 56);
        this.back.setRotationPoint(0.0F, -5.0F, 9.0F);
        this.back.addBox(-8.0F, 0.0F, -4.0F, 16, 13, 8);
        this.eggSack = new ModelRenderer(this, 0, 77);
        this.eggSack.setRotationPoint(0.0F, 8.0F, 6.0F);
        this.eggSack.addBox(-6.0F, 0.0F, -5.5F, 12, 7, 11);
        this.frontRightJoint = new ModelRenderer(this, 88, 36);
        this.frontRightJoint.setRotationPoint(9.0F, 10.0F, -7.0F);
        this.frontRightJoint.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4);
        this.middleRightJoint = new ModelRenderer(this, 88, 57);
        this.middleRightJoint.setRotationPoint(9.0F, 10.0F, 1.0F);
        this.middleRightJoint.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4);
        this.backRightJoint = new ModelRenderer(this, 88, 78);
        this.backRightJoint.setRotationPoint(7.0F, 5.0F, 9.0F);
        this.backRightJoint.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4);
        this.frontLeftJoint = new ModelRenderer(this, 72, 36);
        this.frontLeftJoint.setRotationPoint(-9.0F, 10.0F, -7.0F);
        this.frontLeftJoint.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4);
        this.middleLeftJoint = new ModelRenderer(this, 72, 57);
        this.middleLeftJoint.setRotationPoint(-9.0F, 10.0F, 1.0F);
        this.middleLeftJoint.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4);
        this.backLeftJoint = new ModelRenderer(this, 72, 78);
        this.backLeftJoint.setRotationPoint(-7.0F, 5.0F, 9.0F);
        this.backLeftJoint.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4);
        this.frontRightTentacle = new ModelRenderer(this, 92, 44);
        this.frontRightTentacle.setRotationPoint(9.0F, 13.0F, -7.5F);
        this.frontRightTentacle.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2);
        this.setRotationAngles(this.frontRightTentacle, 0.39269908169872414F, 0.0F, 0.0F);
        this.middleRightTentacle = new ModelRenderer(this, 92, 65);
        this.middleRightTentacle.setRotationPoint(9.0F, 13.0F, 0.5F);
        this.middleRightTentacle.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2);
        this.setRotationAngles(this.middleRightTentacle, 0.39269908169872414F, 0.0F, 0.0F);
        this.backRightTentacle = new ModelRenderer(this, 92, 86);
        this.backRightTentacle.setRotationPoint(7.0F, 7.0F, 9.5F);
        this.backRightTentacle.addBox(-1.0F, 0.0F, -2.0F, 2, 17, 2);
        this.setRotationAngles(this.backRightTentacle, 0.19634954084936207F, 0.0F, 0.0F);
        this.frontLeftTentacle = new ModelRenderer(this, 76, 44);
        this.frontLeftTentacle.setRotationPoint(-9.0F, 13.0F, -7.5F);
        this.frontLeftTentacle.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2);
        this.setRotationAngles(this.frontLeftTentacle, 0.39269908169872414F, 0.0F, 0.0F);
        this.middleLeftTentacle = new ModelRenderer(this, 76, 65);
        this.middleLeftTentacle.setRotationPoint(-9.0F, 13.0F, 0.5F);
        this.middleLeftTentacle.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2);
        this.setRotationAngles(this.middleLeftTentacle, 0.39269908169872414F, 0.0F, 0.0F);
        this.backLeftTentacle = new ModelRenderer(this, 76, 86);
        this.backLeftTentacle.setRotationPoint(-7.0F, 7.0F, 8.5F);
        this.backLeftTentacle.addBox(-1.0F, 0.0F, -1.0F, 2, 17, 2);
        this.setRotationAngles(this.backLeftTentacle, 0.19634954084936207F, 0.0F, 0.0F);
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
        this.eggSack.render(scale);
        this.frontRightJoint.render(scale);
        this.middleRightJoint.render(scale);
        this.backRightJoint.render(scale);
        this.frontLeftJoint.render(scale);
        this.middleLeftJoint.render(scale);
        this.backLeftJoint.render(scale);
        this.frontRightTentacle.render(scale);
        this.middleRightTentacle.render(scale);
        this.backRightTentacle.render(scale);
        this.frontLeftTentacle.render(scale);
        this.middleLeftTentacle.render(scale);
        this.backLeftTentacle.render(scale);
        GlStateManager.popMatrix();
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        this.frontRightTentacle.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 0) + 0.4F;
        this.middleRightTentacle.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 1) + 0.4F;
        this.backRightTentacle.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 2) + 0.4F;
        this.frontLeftTentacle.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 3) + 0.4F;
        this.middleLeftTentacle.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 4) + 0.4F;
        this.backLeftTentacle.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 5) + 0.4F;
    }

    private void setRotationAngles(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}

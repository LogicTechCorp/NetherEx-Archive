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

package nex.client.model.entity;

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
        textureWidth = 128;
        textureHeight = 128;

        face = new ModelRenderer(this, 0, 0);
        face.setRotationPoint(0.0F, -5.0F, -13.0F);
        face.addBox(-8.0F, 0.0F, -2.0F, 16, 16, 4);
        body = new ModelRenderer(this, 0, 20);
        body.setRotationPoint(0.0F, -7.0F, -3.0F);
        body.addBox(-10.0F, 0.0F, -8.0F, 20, 20, 16);
        back = new ModelRenderer(this, 0, 56);
        back.setRotationPoint(0.0F, -5.0F, 9.0F);
        back.addBox(-8.0F, 0.0F, -4.0F, 16, 13, 8);
        eggSack = new ModelRenderer(this, 0, 77);
        eggSack.setRotationPoint(0.0F, 8.0F, 6.0F);
        eggSack.addBox(-6.0F, 0.0F, -5.5F, 12, 7, 11);
        frontRightJoint = new ModelRenderer(this, 88, 36);
        frontRightJoint.setRotationPoint(9.0F, 10.0F, -7.0F);
        frontRightJoint.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4);
        middleRightJoint = new ModelRenderer(this, 88, 57);
        middleRightJoint.setRotationPoint(9.0F, 10.0F, 1.0F);
        middleRightJoint.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4);
        backRightJoint = new ModelRenderer(this, 88, 78);
        backRightJoint.setRotationPoint(7.0F, 5.0F, 9.0F);
        backRightJoint.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4);
        frontLeftJoint = new ModelRenderer(this, 72, 36);
        frontLeftJoint.setRotationPoint(-9.0F, 10.0F, -7.0F);
        frontLeftJoint.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4);
        middleLeftJoint = new ModelRenderer(this, 72, 57);
        middleLeftJoint.setRotationPoint(-9.0F, 10.0F, 1.0F);
        middleLeftJoint.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4);
        backLeftJoint = new ModelRenderer(this, 72, 78);
        backLeftJoint.setRotationPoint(-7.0F, 5.0F, 9.0F);
        backLeftJoint.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4);
        frontRightTentacle = new ModelRenderer(this, 92, 44);
        frontRightTentacle.setRotationPoint(9.0F, 13.0F, -7.5F);
        frontRightTentacle.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2);
        setRotationAngles(frontRightTentacle, 0.39269908169872414F, 0.0F, 0.0F);
        middleRightTentacle = new ModelRenderer(this, 92, 65);
        middleRightTentacle.setRotationPoint(9.0F, 13.0F, 0.5F);
        middleRightTentacle.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2);
        setRotationAngles(middleRightTentacle, 0.39269908169872414F, 0.0F, 0.0F);
        backRightTentacle = new ModelRenderer(this, 92, 86);
        backRightTentacle.setRotationPoint(7.0F, 7.0F, 9.5F);
        backRightTentacle.addBox(-1.0F, 0.0F, -2.0F, 2, 17, 2);
        setRotationAngles(backRightTentacle, 0.19634954084936207F, 0.0F, 0.0F);
        frontLeftTentacle = new ModelRenderer(this, 76, 44);
        frontLeftTentacle.setRotationPoint(-9.0F, 13.0F, -7.5F);
        frontLeftTentacle.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2);
        setRotationAngles(frontLeftTentacle, 0.39269908169872414F, 0.0F, 0.0F);
        middleLeftTentacle = new ModelRenderer(this, 76, 65);
        middleLeftTentacle.setRotationPoint(-9.0F, 13.0F, 0.5F);
        middleLeftTentacle.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2);
        setRotationAngles(middleLeftTentacle, 0.39269908169872414F, 0.0F, 0.0F);
        backLeftTentacle = new ModelRenderer(this, 76, 86);
        backLeftTentacle.setRotationPoint(-7.0F, 7.0F, 8.5F);
        backLeftTentacle.addBox(-1.0F, 0.0F, -1.0F, 2, 17, 2);
        setRotationAngles(backLeftTentacle, 0.19634954084936207F, 0.0F, 0.0F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.6F, 0.0F);
        face.render(scale);
        body.render(scale);
        back.render(scale);
        eggSack.render(scale);
        frontRightJoint.render(scale);
        middleRightJoint.render(scale);
        backRightJoint.render(scale);
        frontLeftJoint.render(scale);
        middleLeftJoint.render(scale);
        backLeftJoint.render(scale);
        frontRightTentacle.render(scale);
        middleRightTentacle.render(scale);
        backRightTentacle.render(scale);
        frontLeftTentacle.render(scale);
        middleLeftTentacle.render(scale);
        backLeftTentacle.render(scale);
        GlStateManager.popMatrix();
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        frontRightTentacle.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 0) + 0.4F;
        middleRightTentacle.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 1) + 0.4F;
        backRightTentacle.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 2) + 0.4F;
        frontLeftTentacle.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 3) + 0.4F;
        middleLeftTentacle.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 4) + 0.4F;
        backLeftTentacle.rotateAngleX = 0.2F * MathHelper.sin(ageInTicks * 0.3F + (float) 5) + 0.4F;
    }

    private void setRotationAngles(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}

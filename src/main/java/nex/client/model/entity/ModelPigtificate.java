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
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelPigtificate extends ModelBase
{
    public ModelRenderer head;
    public ModelRenderer nose;
    public ModelRenderer upperBody;
    public ModelRenderer lowerBody;
    public ModelRenderer rightArm;
    public ModelRenderer leftArm;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;

    public ModelPigtificate()
    {
        textureWidth = 128;
        textureHeight = 128;

        head = new ModelRenderer(this, 0, 0);
        head.setRotationPoint(0.0F, -1.0F, 0.0F);
        head.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8);
        nose = new ModelRenderer(this, 32, 0);
        nose.setRotationPoint(0.0F, -4.0F, -4.5F);
        nose.addBox(-2.0F, 0.0F, -0.5F, 4, 3, 1);
        head.addChild(nose);
        upperBody = new ModelRenderer(this, 0, 18);
        upperBody.setRotationPoint(-1.0F, -1.0F, -1.0F);
        upperBody.addBox(-4.0F, 0.0F, -2.0F, 10, 13, 6);
        lowerBody = new ModelRenderer(this, 0, 37);
        lowerBody.setRotationPoint(-1.0F, 12.0F, -1.0F);
        lowerBody.addBox(-4.0F, 0.0F, -2.0F, 10, 7, 6);
        rightArm = new ModelRenderer(this, 0, 51);
        rightArm.setRotationPoint(7.0F, -1.0F, 0.0F);
        rightArm.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
        leftArm = new ModelRenderer(this, 0, 51);
        leftArm.setRotationPoint(-7.0F, -1.0F, 0.0F);
        leftArm.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
        rightLeg = new ModelRenderer(this, 0, 51);
        rightLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
        leftLeg = new ModelRenderer(this, 0, 51);
        leftLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        head.render(scale);
        upperBody.render(scale);
        lowerBody.render(scale);
        rightArm.render(scale);
        leftArm.render(scale);
        leftLeg.render(scale);
        rightLeg.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        head.rotateAngleY = netHeadYaw * 0.017453292F;
        head.rotateAngleX = headPitch * 0.017453292F;

        rightArm.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / 1.0F) + -(float) Math.PI / 8.22F;
        leftArm.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / 1.0F) + -(float) Math.PI / 8.22F;
        rightArm.rotateAngleZ = 0.0F;
        leftArm.rotateAngleZ = 0.0F;

        rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / 1.0F;
        leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / 1.0F;
        rightLeg.rotateAngleY = 0.0F;
        leftLeg.rotateAngleY = 0.0F;
        rightLeg.rotateAngleZ = 0.0F;
        leftLeg.rotateAngleZ = 0.0F;
    }


    public void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

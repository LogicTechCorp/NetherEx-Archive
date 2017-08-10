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

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelPigtificate extends ModelBiped
{
    private ModelRenderer headWear;
    private ModelRenderer head;
    private ModelRenderer nose;
    private ModelRenderer upperBody;
    private ModelRenderer lowerBody;
    private ModelRenderer rightArm;
    private ModelRenderer leftArm;
    private ModelRenderer rightLeg;
    private ModelRenderer leftLeg;

    public ModelPigtificate()
    {
        textureWidth = 128;
        textureHeight = 128;

        headWear = new ModelRenderer(this, 41, 0);
        headWear.setRotationPoint(0.0F, -1.0F, 0.0F);
        headWear.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.5F);
        head = new ModelRenderer(this, 0, 0);
        head.setRotationPoint(0.0F, -1.0F, 0.0F);
        head.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8);
        nose = new ModelRenderer(this, 32, 14);
        nose.setRotationPoint(0.0F, -4.0F, -4.5F);
        nose.addBox(-2.0F, 0.0F, -0.5F, 4, 3, 1);
        head.addChild(nose);
        upperBody = new ModelRenderer(this, 0, 18);
        upperBody.setRotationPoint(-1.0F, -1.0F, -1.0F);
        upperBody.addBox(-4.0F, 0.0F, -2.0F, 10, 13, 6);
        lowerBody = new ModelRenderer(this, 0, 37);
        lowerBody.setRotationPoint(-1.0F, 12.0F, -1.0F);
        lowerBody.addBox(-4.0F, 0.0F, -2.0F, 10, 7, 6);
        rightArm = new ModelRenderer(this, 48, 21);
        rightArm.setRotationPoint(7.0F, -1.0F, 0.0F);
        rightArm.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
        leftArm = new ModelRenderer(this, 32, 21);
        leftArm.setRotationPoint(-7.0F, -1.0F, 0.0F);
        leftArm.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
        rightLeg = new ModelRenderer(this, 16, 50);
        rightLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
        leftLeg = new ModelRenderer(this, 0, 50);
        leftLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);

        headWear.render(scale);
        head.render(scale);
        upperBody.render(scale);
        lowerBody.render(scale);
        rightArm.render(scale);
        leftArm.render(scale);
        leftLeg.render(scale);
        rightLeg.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity)
    {
        boolean flag = entity instanceof EntityLivingBase && ((EntityLivingBase) entity).getTicksElytraFlying() > 4;
        head.rotateAngleY = netHeadYaw * 0.017453292F;

        if(flag)
        {
            head.rotateAngleX = -((float) Math.PI / 4F);
        }
        else
        {
            head.rotateAngleX = headPitch * 0.017453292F;
        }

        upperBody.rotateAngleY = 0.0F;
        rightArm.rotationPointZ = 0.0F;
        rightArm.rotationPointX = -6.25F;
        leftArm.rotationPointZ = 0.0F;
        leftArm.rotationPointX = 6.25F;
        float motion = 1.0F;

        if(flag)
        {
            motion = (float) (entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ);
            motion = motion / 0.2F;
            motion = motion * motion * motion;
        }

        if(motion < 1.0F)
        {
            motion = 1.0F;
        }

        rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / motion;
        leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / motion;
        rightArm.rotateAngleZ = 0.0F;
        leftArm.rotateAngleZ = 0.0F;
        rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / motion;
        leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / motion;
        rightLeg.rotateAngleY = 0.0F;
        leftLeg.rotateAngleY = 0.0F;
        rightLeg.rotateAngleZ = 0.0F;
        leftLeg.rotateAngleZ = 0.0F;

        if(isRiding)
        {
            rightArm.rotateAngleX += -((float) Math.PI / 5F);
            leftArm.rotateAngleX += -((float) Math.PI / 5F);
            rightLeg.rotateAngleX = -1.4137167F;
            rightLeg.rotateAngleY = ((float) Math.PI / 10F);
            rightLeg.rotateAngleZ = 0.07853982F;
            leftLeg.rotateAngleX = -1.4137167F;
            leftLeg.rotateAngleY = -((float) Math.PI / 10F);
            leftLeg.rotateAngleZ = -0.07853982F;
        }

        rightArm.rotateAngleY = 0.0F;
        rightArm.rotateAngleZ = 0.0F;

        switch(leftArmPose)
        {
            case EMPTY:
                leftArm.rotateAngleY = 0.0F;
                break;
            case BLOCK:
                leftArm.rotateAngleX = leftArm.rotateAngleX * 0.5F - 0.9424779F;
                leftArm.rotateAngleY = 0.5235988F;
                break;
            case ITEM:
                leftArm.rotateAngleX = leftArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F);
                leftArm.rotateAngleY = 0.0F;
        }

        switch(rightArmPose)
        {
            case EMPTY:
                rightArm.rotateAngleY = 0.0F;
                break;
            case BLOCK:
                rightArm.rotateAngleX = rightArm.rotateAngleX * 0.5F - 0.9424779F;
                rightArm.rotateAngleY = -0.5235988F;
                break;
            case ITEM:
                rightArm.rotateAngleX = rightArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F);
                rightArm.rotateAngleY = 0.0F;
        }

        if(swingProgress > 0.0F)
        {
            EnumHandSide hand = getMainHand(entity);
            ModelRenderer modelRenderer = getArmForSide(hand);
            float f1 = swingProgress;
            upperBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float) Math.PI * 2F)) * 0.2F;

            if(hand == EnumHandSide.LEFT)
            {
                upperBody.rotateAngleY *= -1.0F;
            }

            rightArm.rotationPointZ = MathHelper.sin(upperBody.rotateAngleY) * 5.0F;
            rightArm.rotationPointX = -MathHelper.cos(upperBody.rotateAngleY) * 5.0F;
            leftArm.rotationPointZ = -MathHelper.sin(upperBody.rotateAngleY) * 5.0F;
            leftArm.rotationPointX = MathHelper.cos(upperBody.rotateAngleY) * 5.0F;
            rightArm.rotateAngleY += upperBody.rotateAngleY;
            leftArm.rotateAngleY += upperBody.rotateAngleY;
            leftArm.rotateAngleX += upperBody.rotateAngleY;
            f1 = 1.0F - swingProgress;
            f1 = f1 * f1;
            f1 = f1 * f1;
            f1 = 1.0F - f1;
            float f2 = MathHelper.sin(f1 * (float) Math.PI);
            float f3 = MathHelper.sin(swingProgress * (float) Math.PI) * -(head.rotateAngleX - 0.7F) * 0.75F;
            modelRenderer.rotateAngleX = (float) ((double) modelRenderer.rotateAngleX - ((double) f2 * 1.2D + (double) f3));
            modelRenderer.rotateAngleY += upperBody.rotateAngleY * 2.0F;
            modelRenderer.rotateAngleZ += MathHelper.sin(swingProgress * (float) Math.PI) * -0.4F;
        }

        if(isSneak)
        {
            upperBody.rotateAngleX = 0.5F;
            rightArm.rotateAngleX += 0.4F;
            leftArm.rotateAngleX += 0.4F;
            rightLeg.rotationPointZ = 4.0F;
            leftLeg.rotationPointZ = 4.0F;
            rightLeg.rotationPointY = 9.0F;
            leftLeg.rotationPointY = 9.0F;
            head.rotationPointY = 1.0F;
        }
        else
        {
            upperBody.rotateAngleX = 0.0F;
            rightLeg.rotationPointZ = 0.1F;
            leftLeg.rotationPointZ = 0.1F;
            rightLeg.rotationPointY = 12.0F;
            leftLeg.rotationPointY = 12.0F;
            head.rotationPointY = 0.0F;
        }

        rightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        leftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        rightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        leftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

        if(rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
        {
            rightArm.rotateAngleY = -0.1F + head.rotateAngleY;
            leftArm.rotateAngleY = 0.1F + head.rotateAngleY + 0.4F;
            rightArm.rotateAngleX = -((float) Math.PI / 2F) + head.rotateAngleX;
            leftArm.rotateAngleX = -((float) Math.PI / 2F) + head.rotateAngleX;
        }
        else if(leftArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
        {
            rightArm.rotateAngleY = -0.1F + head.rotateAngleY - 0.4F;
            leftArm.rotateAngleY = 0.1F + head.rotateAngleY;
            rightArm.rotateAngleX = -((float) Math.PI / 2F) + head.rotateAngleX;
            leftArm.rotateAngleX = -((float) Math.PI / 2F) + head.rotateAngleX;
        }

        copyModelAngles(head, headWear);
    }

    @Override
    protected ModelRenderer getArmForSide(EnumHandSide side)
    {
        return side == EnumHandSide.LEFT ? leftArm : rightArm;
    }

    @Override
    public void setVisible(boolean visible)
    {
        headWear.showModel = visible;
        head.showModel = visible;
        upperBody.showModel = visible;
        lowerBody.showModel = visible;
        rightArm.showModel = visible;
        leftArm.showModel = visible;
        rightLeg.showModel = visible;
        leftLeg.showModel = visible;
    }
}

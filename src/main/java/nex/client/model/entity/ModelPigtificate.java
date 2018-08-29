/*
 * NetherEx
 * Copyright (c) 2016-2018 by MineEx
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
        this.textureWidth = 128;
        this.textureHeight = 128;

        this.headWear = new ModelRenderer(this, 41, 0);
        this.headWear.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.headWear.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.5F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.head.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8);
        this.nose = new ModelRenderer(this, 32, 14);
        this.nose.setRotationPoint(0.0F, -4.0F, -4.5F);
        this.nose.addBox(-2.0F, 0.0F, -0.5F, 4, 3, 1);
        this.head.addChild(this.nose);
        this.upperBody = new ModelRenderer(this, 0, 18);
        this.upperBody.setRotationPoint(-1.0F, -1.0F, -1.0F);
        this.upperBody.addBox(-4.0F, 0.0F, -2.0F, 10, 13, 6);
        this.lowerBody = new ModelRenderer(this, 0, 37);
        this.lowerBody.setRotationPoint(-1.0F, 12.0F, -1.0F);
        this.lowerBody.addBox(-4.0F, 0.0F, -2.0F, 10, 7, 6);
        this.rightArm = new ModelRenderer(this, 48, 21);
        this.rightArm.setRotationPoint(7.0F, -1.0F, 0.0F);
        this.rightArm.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
        this.leftArm = new ModelRenderer(this, 32, 21);
        this.leftArm.setRotationPoint(-7.0F, -1.0F, 0.0F);
        this.leftArm.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
        this.rightLeg = new ModelRenderer(this, 16, 50);
        this.rightLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
        this.leftLeg = new ModelRenderer(this, 0, 50);
        this.leftLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);

        this.headWear.render(scale);
        this.head.render(scale);
        this.upperBody.render(scale);
        this.lowerBody.render(scale);
        this.rightArm.render(scale);
        this.leftArm.render(scale);
        this.leftLeg.render(scale);
        this.rightLeg.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity)
    {
        boolean flag = entity instanceof EntityLivingBase && ((EntityLivingBase) entity).getTicksElytraFlying() > 4;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;

        if(flag)
        {
            this.head.rotateAngleX = -((float) Math.PI / 4F);
        }
        else
        {
            this.head.rotateAngleX = headPitch * 0.017453292F;
        }

        this.upperBody.rotateAngleY = 0.0F;
        this.rightArm.rotationPointZ = 0.0F;
        this.rightArm.rotationPointX = -6.25F;
        this.leftArm.rotationPointZ = 0.0F;
        this.leftArm.rotationPointX = 6.25F;
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

        this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / motion;
        this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / motion;
        this.rightArm.rotateAngleZ = 0.0F;
        this.leftArm.rotateAngleZ = 0.0F;
        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / motion;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / motion;
        this.rightLeg.rotateAngleY = 0.0F;
        this.leftLeg.rotateAngleY = 0.0F;
        this.rightLeg.rotateAngleZ = 0.0F;
        this.leftLeg.rotateAngleZ = 0.0F;

        if(this.isRiding)
        {
            this.rightArm.rotateAngleX += -((float) Math.PI / 5F);
            this.leftArm.rotateAngleX += -((float) Math.PI / 5F);
            this.rightLeg.rotateAngleX = -1.4137167F;
            this.rightLeg.rotateAngleY = ((float) Math.PI / 10F);
            this.rightLeg.rotateAngleZ = 0.07853982F;
            this.leftLeg.rotateAngleX = -1.4137167F;
            this.leftLeg.rotateAngleY = -((float) Math.PI / 10F);
            this.leftLeg.rotateAngleZ = -0.07853982F;
        }

        this.rightArm.rotateAngleY = 0.0F;
        this.rightArm.rotateAngleZ = 0.0F;

        switch(this.leftArmPose)
        {
            case EMPTY:
                this.leftArm.rotateAngleY = 0.0F;
                break;
            case BLOCK:
                this.leftArm.rotateAngleX = this.leftArm.rotateAngleX * 0.5F - 0.9424779F;
                this.leftArm.rotateAngleY = 0.5235988F;
                break;
            case ITEM:
                this.leftArm.rotateAngleX = this.leftArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F);
                this.leftArm.rotateAngleY = 0.0F;
        }

        switch(this.rightArmPose)
        {
            case EMPTY:
                this.rightArm.rotateAngleY = 0.0F;
                break;
            case BLOCK:
                this.rightArm.rotateAngleX = this.rightArm.rotateAngleX * 0.5F - 0.9424779F;
                this.rightArm.rotateAngleY = -0.5235988F;
                break;
            case ITEM:
                this.rightArm.rotateAngleX = this.rightArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F);
                this.rightArm.rotateAngleY = 0.0F;
        }

        if(this.swingProgress > 0.0F)
        {
            EnumHandSide hand = this.getMainHand(entity);
            ModelRenderer modelRenderer = this.getArmForSide(hand);
            float f1 = this.swingProgress;
            this.upperBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float) Math.PI * 2F)) * 0.2F;

            if(hand == EnumHandSide.LEFT)
            {
                this.upperBody.rotateAngleY *= -1.0F;
            }

            this.rightArm.rotationPointZ = MathHelper.sin(this.upperBody.rotateAngleY) * 5.0F;
            this.rightArm.rotationPointX = -MathHelper.cos(this.upperBody.rotateAngleY) * 5.0F;
            this.leftArm.rotationPointZ = -MathHelper.sin(this.upperBody.rotateAngleY) * 5.0F;
            this.leftArm.rotationPointX = MathHelper.cos(this.upperBody.rotateAngleY) * 5.0F;
            this.rightArm.rotateAngleY += this.upperBody.rotateAngleY;
            this.leftArm.rotateAngleY += this.upperBody.rotateAngleY;
            this.leftArm.rotateAngleX += this.upperBody.rotateAngleY;
            f1 = 1.0F - this.swingProgress;
            f1 = f1 * f1;
            f1 = f1 * f1;
            f1 = 1.0F - f1;
            float f2 = MathHelper.sin(f1 * (float) Math.PI);
            float f3 = MathHelper.sin(this.swingProgress * (float) Math.PI) * -(this.head.rotateAngleX - 0.7F) * 0.75F;
            modelRenderer.rotateAngleX = (float) ((double) modelRenderer.rotateAngleX - ((double) f2 * 1.2D + (double) f3));
            modelRenderer.rotateAngleY += this.upperBody.rotateAngleY * 2.0F;
            modelRenderer.rotateAngleZ += MathHelper.sin(this.swingProgress * (float) Math.PI) * -0.4F;
        }

        if(this.isSneak)
        {
            this.upperBody.rotateAngleX = 0.5F;
            this.rightArm.rotateAngleX += 0.4F;
            this.leftArm.rotateAngleX += 0.4F;
            this.rightLeg.rotationPointZ = 4.0F;
            this.leftLeg.rotationPointZ = 4.0F;
            this.rightLeg.rotationPointY = 9.0F;
            this.leftLeg.rotationPointY = 9.0F;
            this.head.rotationPointY = 1.0F;
        }
        else
        {
            this.upperBody.rotateAngleX = 0.0F;
            this.rightLeg.rotationPointZ = 0.1F;
            this.leftLeg.rotationPointZ = 0.1F;
            this.rightLeg.rotationPointY = 12.0F;
            this.leftLeg.rotationPointY = 12.0F;
            this.head.rotationPointY = 0.0F;
        }

        this.rightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.leftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.rightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.leftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

        if(this.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
        {
            this.rightArm.rotateAngleY = -0.1F + this.head.rotateAngleY;
            this.leftArm.rotateAngleY = 0.1F + this.head.rotateAngleY + 0.4F;
            this.rightArm.rotateAngleX = -((float) Math.PI / 2F) + this.head.rotateAngleX;
            this.leftArm.rotateAngleX = -((float) Math.PI / 2F) + this.head.rotateAngleX;
        }
        else if(this.leftArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
        {
            this.rightArm.rotateAngleY = -0.1F + this.head.rotateAngleY - 0.4F;
            this.leftArm.rotateAngleY = 0.1F + this.head.rotateAngleY;
            this.rightArm.rotateAngleX = -((float) Math.PI / 2F) + this.head.rotateAngleX;
            this.leftArm.rotateAngleX = -((float) Math.PI / 2F) + this.head.rotateAngleX;
        }

        copyModelAngles(this.head, this.headWear);
    }

    @Override
    protected ModelRenderer getArmForSide(EnumHandSide side)
    {
        return side == EnumHandSide.LEFT ? this.leftArm : this.rightArm;
    }

    @Override
    public void setVisible(boolean visible)
    {
        this.headWear.showModel = visible;
        this.head.showModel = visible;
        this.upperBody.showModel = visible;
        this.lowerBody.showModel = visible;
        this.rightArm.showModel = visible;
        this.leftArm.showModel = visible;
        this.rightLeg.showModel = visible;
        this.leftLeg.showModel = visible;
    }
}

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

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelPigtificate extends ModelBiped
{
    public ModelRenderer head;
    public ModelRenderer rightArm;
    public ModelRenderer leftArm;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;
    public ModelRenderer body;
    public ModelRenderer robe;
    public ModelRenderer headWear;
    public ModelRenderer middleClosedArm;
    public ModelRenderer snout;
    public ModelRenderer robeFloor;
    public ModelRenderer rightClosedArm;
    public ModelRenderer leftClosedArm;

    public ModelPigtificate()
    {
        this.textureWidth = 64;
        this.textureHeight = 128;
        this.leftArm = new ModelRenderer(this, 42, 34);
        this.leftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.leftArm.addBox(-1.55F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
        this.body = new ModelRenderer(this, 16, 18);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body.addBox(-4.0F, 0.0F, -2.5F, 8, 12, 5, 0.0F);
        this.rightLeg = new ModelRenderer(this, 0, 18);
        this.rightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.rightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.rightClosedArm = new ModelRenderer(this, 32, 61);
        this.rightClosedArm.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rightClosedArm.addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        this.middleClosedArm = new ModelRenderer(this, 32, 53);
        this.middleClosedArm.setRotationPoint(0.0F, 3.0F, -0.8F);
        this.middleClosedArm.addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, 0.0F);
        this.setRotateAngle(this.middleClosedArm, -0.7853981633974483F, 0.0F, 0.0F);
        this.leftClosedArm = new ModelRenderer(this, 48, 61);
        this.leftClosedArm.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.leftClosedArm.addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        this.leftLeg = new ModelRenderer(this, 0, 34);
        this.leftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.leftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.robe = new ModelRenderer(this, 0, 50);
        this.robe.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.robe.addBox(-4.5F, -1.0F, -3.5F, 9, 25, 7, 0.0F);
        this.snout = new ModelRenderer(this, 24, 0);
        this.snout.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.snout.addBox(-2.0F, -4.0F, -5.0F, 4, 3, 1, 0.0F);
        this.robeFloor = new ModelRenderer(this, 6, 38);
        this.robeFloor.setRotationPoint(0.0F, 24.0F, 3.0F);
        this.robeFloor.addBox(-4.5F, 0.0F, 0.0F, 9, 0, 12, 0.0F);
        this.rightArm = new ModelRenderer(this, 42, 18);
        this.rightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.rightArm.addBox(-2.45F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.headWear = new ModelRenderer(this, 32, 0);
        this.headWear.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.headWear.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.6F);
        this.middleClosedArm.addChild(this.rightClosedArm);
        this.middleClosedArm.addChild(this.leftClosedArm);
        this.head.addChild(this.snout);
        this.robe.addChild(this.robeFloor);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);

        this.head.render(scale);
        this.body.render(scale);
        this.rightLeg.render(scale);
        this.leftLeg.render(scale);
        this.robe.render(scale);
        this.headWear.render(scale);
        this.rightArm.render(scale);
        this.leftArm.render(scale);
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

        this.body.rotateAngleY = 0.0F;
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
            this.body.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float) Math.PI * 2F)) * 0.2F;

            if(hand == EnumHandSide.LEFT)
            {
                this.body.rotateAngleY *= -1.0F;
            }

            this.rightArm.rotationPointZ = MathHelper.sin(this.body.rotateAngleY) * 5.0F;
            this.rightArm.rotationPointX = -MathHelper.cos(this.body.rotateAngleY) * 5.0F;
            this.leftArm.rotationPointZ = -MathHelper.sin(this.body.rotateAngleY) * 5.0F;
            this.leftArm.rotationPointX = MathHelper.cos(this.body.rotateAngleY) * 5.0F;
            this.rightArm.rotateAngleY += this.body.rotateAngleY;
            this.leftArm.rotateAngleY += this.body.rotateAngleY;
            this.leftArm.rotateAngleX += this.body.rotateAngleY;
            f1 = 1.0F - this.swingProgress;
            f1 = f1 * f1;
            f1 = f1 * f1;
            f1 = 1.0F - f1;
            float f2 = MathHelper.sin(f1 * (float) Math.PI);
            float f3 = MathHelper.sin(this.swingProgress * (float) Math.PI) * -(this.head.rotateAngleX - 0.7F) * 0.75F;
            modelRenderer.rotateAngleX = (float) ((double) modelRenderer.rotateAngleX - ((double) f2 * 1.2D + (double) f3));
            modelRenderer.rotateAngleY += this.body.rotateAngleY * 2.0F;
            modelRenderer.rotateAngleZ += MathHelper.sin(this.swingProgress * (float) Math.PI) * -0.4F;
        }

        if(this.isSneak)
        {
            this.body.rotateAngleX = 0.5F;
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
            this.body.rotateAngleX = 0.0F;
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

    private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

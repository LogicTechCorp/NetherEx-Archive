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

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import nex.entity.neutral.EntityGoldGolem;

public class ModelGoldGolem extends ModelBase
{
    private ModelRenderer head;
    private ModelRenderer nose;
    private ModelRenderer body;
    private ModelRenderer waist;
    private ModelRenderer loinClothFront;
    private ModelRenderer loinClothBack;
    private ModelRenderer rightLeg;
    private ModelRenderer leftLeg;
    private ModelRenderer rightArm;
    private ModelRenderer leftArm;

    public ModelGoldGolem()
    {
        textureWidth = 128;
        textureHeight = 128;

        head = new ModelRenderer(this, 0, 0);
        head.setRotationPoint(0.0F, -14.0F, -4.0F);
        head.addBox(-4.0F, -9.0F, -4.0F, 8, 9, 8);
        nose = new ModelRenderer(this, 24, 0);
        nose.setRotationPoint(0.0F, -1.0F, -4.0F);
        nose.addBox(-2.0F, -3.0F, -1.0F, 4, 3, 1);
        head.addChild(this.nose);
        body = new ModelRenderer(this, 0, 17);
        body.setRotationPoint(0.0F, -18.0F, 0.0F);
        body.addBox(-9.0F, 0.0F, -5.0F, 18, 20, 10);
        waist = new ModelRenderer(this, 0, 47);
        waist.setRotationPoint(0.0F, 2.0F, 0.0F);
        waist.addBox(-5.0F, 0.0F, -4.0F, 10, 5, 8);
        loinClothFront = new ModelRenderer(this, 36, 47);
        loinClothFront.setRotationPoint(0.0F, 5.0F, -4.0F);
        loinClothFront.addBox(-3.0F, 0.0F, 0.0F, 6, 10, 0);
        waist.addChild(this.loinClothFront);
        loinClothBack = new ModelRenderer(this, 36, 47);
        loinClothBack.setRotationPoint(0.0F, 5.0F, 4.0F);
        loinClothBack.addBox(-3.0F, 0.0F, 0.0F, 6, 10, 0);
        waist.addChild(this.loinClothBack);
        rightLeg = new ModelRenderer(this, 0, 60);
        rightLeg.setRotationPoint(5.0F, 7.0F, 0.0F);
        rightLeg.addBox(-3.0F, 0.0F, -3.0F, 6, 17, 6);
        leftLeg = new ModelRenderer(this, 0, 60);
        leftLeg.setRotationPoint(-5.0F, 7.0F, 0.0F);
        leftLeg.addBox(-3.0F, 0.0F, -3.0F, 6, 17, 6);
        rightArm = new ModelRenderer(this, 0, 83);
        rightArm.setRotationPoint(11.5F, -17.0F, 0.0F);
        rightArm.addBox(-2.5F, 0.0F, -2.5F, 5, 31, 5);
        leftArm = new ModelRenderer(this, 0, 83);
        leftArm.setRotationPoint(-11.5F, -17.0F, 0.0F);
        leftArm.addBox(-2.5F, 0.0F, -2.5F, 5, 31, 5);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        head.render(scale);
        body.render(scale);
        waist.render(scale);
        rightLeg.render(scale);
        leftLeg.render(scale);
        rightArm.render(scale);
        leftArm.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity)
    {
        head.rotateAngleY = netHeadYaw * 0.017453292F;
        head.rotateAngleX = headPitch * 0.017453292F;
        leftLeg.rotateAngleX = -1.5F * triangleWave(limbSwing, 13.0F) * limbSwingAmount;
        rightLeg.rotateAngleX = 1.5F * triangleWave(limbSwing, 13.0F) * limbSwingAmount;
        leftLeg.rotateAngleY = 0.0F;
        rightLeg.rotateAngleY = 0.0F;
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        EntityGoldGolem goldGolem = (EntityGoldGolem) entity;
        int i = goldGolem.getAttackTimer();

        if(i > 0)
        {
            rightArm.rotateAngleX = -2.0F + 1.5F * triangleWave((float) i - partialTickTime, 10.0F);
            leftArm.rotateAngleX = -2.0F + 1.5F * triangleWave((float) i - partialTickTime, 10.0F);
        }
        else
        {
            rightArm.rotateAngleX = (-0.2F + 1.5F * triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
            leftArm.rotateAngleX = (-0.2F - 1.5F * triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
        }
    }

    private float triangleWave(float p_78172_1_, float p_78172_2_)
    {
        return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
    }

    public void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

}

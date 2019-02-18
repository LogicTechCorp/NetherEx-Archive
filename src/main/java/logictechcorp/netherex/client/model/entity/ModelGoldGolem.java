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

import logictechcorp.netherex.entity.neutral.EntityGoldGolem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
        this.textureWidth = 128;
        this.textureHeight = 128;

        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, -14.0F, -4.0F);
        this.head.addBox(-4.0F, -9.0F, -4.0F, 8, 9, 8);
        this.nose = new ModelRenderer(this, 24, 0);
        this.nose.setRotationPoint(0.0F, -1.0F, -4.0F);
        this.nose.addBox(-2.0F, -3.0F, -1.0F, 4, 3, 1);
        this.head.addChild(this.nose);
        this.body = new ModelRenderer(this, 0, 17);
        this.body.setRotationPoint(0.0F, -18.0F, 0.0F);
        this.body.addBox(-9.0F, 0.0F, -5.0F, 18, 20, 10);
        this.waist = new ModelRenderer(this, 0, 47);
        this.waist.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.waist.addBox(-5.0F, 0.0F, -4.0F, 10, 5, 8);
        this.loinClothFront = new ModelRenderer(this, 36, 47);
        this.loinClothFront.setRotationPoint(0.0F, 5.0F, -4.0F);
        this.loinClothFront.addBox(-3.0F, 0.0F, 0.0F, 6, 10, 0);
        this.waist.addChild(this.loinClothFront);
        this.loinClothBack = new ModelRenderer(this, 36, 47);
        this.loinClothBack.setRotationPoint(0.0F, 5.0F, 4.0F);
        this.loinClothBack.addBox(-3.0F, 0.0F, 0.0F, 6, 10, 0);
        this.waist.addChild(this.loinClothBack);
        this.rightLeg = new ModelRenderer(this, 0, 60);
        this.rightLeg.setRotationPoint(5.0F, 7.0F, 0.0F);
        this.rightLeg.addBox(-3.0F, 0.0F, -3.0F, 6, 17, 6);
        this.leftLeg = new ModelRenderer(this, 0, 60);
        this.leftLeg.setRotationPoint(-5.0F, 7.0F, 0.0F);
        this.leftLeg.addBox(-3.0F, 0.0F, -3.0F, 6, 17, 6);
        this.rightArm = new ModelRenderer(this, 0, 83);
        this.rightArm.setRotationPoint(11.5F, -17.0F, 0.0F);
        this.rightArm.addBox(-2.5F, 0.0F, -2.5F, 5, 31, 5);
        this.leftArm = new ModelRenderer(this, 0, 83);
        this.leftArm.setRotationPoint(-11.5F, -17.0F, 0.0F);
        this.leftArm.addBox(-2.5F, 0.0F, -2.5F, 5, 31, 5);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        this.head.render(scale);
        this.body.render(scale);
        this.waist.render(scale);
        this.rightLeg.render(scale);
        this.leftLeg.render(scale);
        this.rightArm.render(scale);
        this.leftArm.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity)
    {
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.leftLeg.rotateAngleX = -1.5F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
        this.rightLeg.rotateAngleX = 1.5F * this.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
        this.leftLeg.rotateAngleY = 0.0F;
        this.rightLeg.rotateAngleY = 0.0F;
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        EntityGoldGolem goldGolem = (EntityGoldGolem) entity;
        int i = goldGolem.getAttackTimer();

        if(i > 0)
        {
            this.rightArm.rotateAngleX = -2.0F + 1.5F * this.triangleWave((float) i - partialTickTime, 10.0F);
            this.leftArm.rotateAngleX = -2.0F + 1.5F * this.triangleWave((float) i - partialTickTime, 10.0F);
        }
        else
        {
            this.rightArm.rotateAngleX = (-0.2F + 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
            this.leftArm.rotateAngleX = (-0.2F - 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
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

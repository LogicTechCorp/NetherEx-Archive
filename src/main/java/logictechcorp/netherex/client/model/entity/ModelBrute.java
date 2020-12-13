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
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBrute extends ModelBase
{
    private final ModelRenderer head;
    private final ModelRenderer nose;
    private final ModelRenderer lip;
    private final ModelRenderer teeth;
    private final ModelRenderer upperBody;
    private final ModelRenderer stomach;
    private final ModelRenderer upperScale;
    private final ModelRenderer middleScale;
    private final ModelRenderer lowerScale;
    private final ModelRenderer rightFrontChain;
    private final ModelRenderer rightBackChain;
    private final ModelRenderer leftFrontChain;
    private final ModelRenderer leftBackChain;
    private final ModelRenderer rightArm;
    private final ModelRenderer leftArm;
    private final ModelRenderer lowerBody;
    private final ModelRenderer rightLeg;
    private final ModelRenderer leftLeg;

    public ModelBrute()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;

        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, -3.0F, -10.0F);
        this.head.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 6);
        this.setRotationAngles(this.head, 0.1308996938995747F, 0.0F, 0.0F);
        this.nose = new ModelRenderer(this, 60, 0);
        this.nose.setRotationPoint(0.0F, 3.0F, -5.5F);
        this.nose.addBox(-1.0F, -2.0F, -2.0F, 2, 4, 2);
        this.head.addChild(this.nose);
        this.lip = new ModelRenderer(this, 28, 10);
        this.lip.setRotationPoint(0.0F, 3.5F, -6.0F);
        this.lip.addBox(-4.0F, -0.5F, -1.0F, 8, 1, 1);
        this.head.addChild(this.lip);
        this.teeth = new ModelRenderer(this, 31, 9);
        this.teeth.setRotationPoint(0.0F, -1.0F, -1.0F);
        this.teeth.addBox(-3.0F, -0.5F, 0.0F, 6, 1, 0);
        this.lip.addChild(this.teeth);
        this.upperBody = new ModelRenderer(this, 0, 14);
        this.upperBody.setRotationPoint(0.0F, 13.0F, -0.5F);
        this.upperBody.addBox(-12.0F, -24.0F, -6.5F, 24, 24, 13);
        this.setRotationAngles(this.upperBody, 0.2617993877991494F, 0.0F, 0.0F);
        this.stomach = new ModelRenderer(this, 74, 42);
        this.stomach.setRotationPoint(0.0F, -7.0F, -6.5F);
        this.stomach.addBox(-6.5F, -4.0F, -1.0F, 13, 8, 1);
        this.upperBody.addChild(this.stomach);
        this.upperScale = new ModelRenderer(this, 50, 0);
        this.upperScale.setRotationPoint(0.0F, -20.0F, 5.5F);
        this.upperScale.addBox(-1.5F, -2.0F, 0.0F, 3, 4, 2);
        this.upperBody.addChild(this.upperScale);
        this.middleScale = new ModelRenderer(this, 50, 0);
        this.middleScale.setRotationPoint(0.0F, -12.0F, 5.5F);
        this.middleScale.addBox(-1.5F, -2.0F, 0.0F, 3, 4, 2);
        this.upperBody.addChild(this.middleScale);
        this.lowerScale = new ModelRenderer(this, 50, 0);
        this.lowerScale.setRotationPoint(0.0F, -4.0F, 5.5F);
        this.lowerScale.addBox(-1.5F, -2.0F, 0.0F, 3, 4, 2);
        this.upperBody.addChild(this.lowerScale);
        this.rightFrontChain = new ModelRenderer(this, 77, 0);
        this.rightFrontChain.setRotationPoint(7.5F, -8.5F, -13.0F);
        this.rightFrontChain.addBox(-1.5F, 0.0F, 0.0F, 3, 28, 0);
        this.rightBackChain = new ModelRenderer(this, 74, 24);
        this.rightBackChain.setRotationPoint(7.5F, 11.3F, 5.8F);
        this.rightBackChain.addBox(-1.5F, 0.0F, 0.0F, 3, 4, 0);
        this.leftFrontChain = new ModelRenderer(this, 74, 0);
        this.leftFrontChain.setRotationPoint(-7.5F, -8.5F, -13.0F);
        this.leftFrontChain.addBox(-1.5F, 0.0F, 0.0F, 3, 16, 0);
        this.leftBackChain = new ModelRenderer(this, 74, 16);
        this.leftBackChain.setRotationPoint(-7.5F, 11.3F, 5.8F);
        this.leftBackChain.addBox(-1.5F, 0.0F, 0.0F, 3, 10, 0);
        this.rightArm = new ModelRenderer(this, 0, 80);
        this.rightArm.setRotationPoint(10.0F, -3.0F, -8.0F);
        this.rightArm.addBox(0.0F, -3.5F, -3.5F, 7, 24, 7);
        this.setRotationAngles(this.rightArm, -0.2617993877991494F, 0.0F, 0.0F);
        this.leftArm = new ModelRenderer(this, 28, 80);
        this.leftArm.setRotationPoint(-10.0F, -3.0F, -8.0F);
        this.leftArm.addBox(-7.0F, -3.5F, -3.5F, 7, 24, 7);
        this.setRotationAngles(this.leftArm, -0.2617993877991494F, 0.0F, 0.0F);
        this.lowerBody = new ModelRenderer(this, 0, 51);
        this.lowerBody.setRotationPoint(0.0F, 14.0F, 0.0F);
        this.lowerBody.addBox(-10.5F, -2.5F, -4.5F, 21, 5, 9);
        this.rightLeg = new ModelRenderer(this, 0, 65);
        this.rightLeg.setRotationPoint(6.0F, 16.0F, 0.0F);
        this.rightLeg.addBox(-3.5F, 0.0F, -3.5F, 7, 8, 7);
        this.leftLeg = new ModelRenderer(this, 28, 65);
        this.leftLeg.setRotationPoint(-6.0F, 16.0F, 0.0F);
        this.leftLeg.addBox(-3.5F, 0.0F, -3.5F, 7, 8, 7);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);

        this.head.render(scale);
        this.upperBody.render(scale);
        this.rightFrontChain.render(scale);
        this.rightBackChain.render(scale);
        this.leftFrontChain.render(scale);
        this.leftBackChain.render(scale);
        this.rightArm.render(scale);
        this.leftArm.render(scale);
        this.lowerBody.render(scale);
        this.rightLeg.render(scale);
        this.leftLeg.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;

        this.rightArm.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / 1.0F) + -(float) Math.PI / 8.22F;
        this.leftArm.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / 1.0F) + -(float) Math.PI / 8.22F;
        this.rightArm.rotateAngleZ = 0.0F;
        this.leftArm.rotateAngleZ = 0.0F;

        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / 1.0F;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / 1.0F;
        this.rightLeg.rotateAngleY = 0.0F;
        this.leftLeg.rotateAngleY = 0.0F;
        this.rightLeg.rotateAngleZ = 0.0F;
        this.leftLeg.rotateAngleZ = 0.0F;

    }

    private void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

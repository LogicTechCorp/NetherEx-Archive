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
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBrute extends ModelBase
{
    private ModelRenderer head;
    private ModelRenderer nose;
    private ModelRenderer lip;
    private ModelRenderer teeth;
    private ModelRenderer upperBody;
    private ModelRenderer stomach;
    private ModelRenderer upperScale;
    private ModelRenderer middleScale;
    private ModelRenderer lowerScale;
    private ModelRenderer rightFrontChain;
    private ModelRenderer rightBackChain;
    private ModelRenderer leftFrontChain;
    private ModelRenderer leftBackChain;
    private ModelRenderer rightArm;
    private ModelRenderer leftArm;
    private ModelRenderer lowerBody;
    private ModelRenderer rightLeg;
    private ModelRenderer leftLeg;

    public ModelBrute()
    {
        textureWidth = 128;
        textureHeight = 128;

        head = new ModelRenderer(this, 0, 0);
        head.setRotationPoint(0.0F, -3.0F, -10.0F);
        head.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 6);
        setRotationAngles(head, 0.1308996938995747F, 0.0F, 0.0F);
        nose = new ModelRenderer(this, 60, 0);
        nose.setRotationPoint(0.0F, 3.0F, -5.5F);
        nose.addBox(-1.0F, -2.0F, -2.0F, 2, 4, 2);
        head.addChild(nose);
        lip = new ModelRenderer(this, 28, 10);
        lip.setRotationPoint(0.0F, 3.5F, -6.0F);
        lip.addBox(-4.0F, -0.5F, -1.0F, 8, 1, 1);
        head.addChild(lip);
        teeth = new ModelRenderer(this, 31, 9);
        teeth.setRotationPoint(0.0F, -1.0F, -1.0F);
        teeth.addBox(-3.0F, -0.5F, 0.0F, 6, 1, 0);
        lip.addChild(teeth);
        upperBody = new ModelRenderer(this, 0, 14);
        upperBody.setRotationPoint(0.0F, 13.0F, -0.5F);
        upperBody.addBox(-12.0F, -24.0F, -6.5F, 24, 24, 13);
        setRotationAngles(upperBody, 0.2617993877991494F, 0.0F, 0.0F);
        stomach = new ModelRenderer(this, 74, 42);
        stomach.setRotationPoint(0.0F, -7.0F, -6.5F);
        stomach.addBox(-6.5F, -4.0F, -1.0F, 13, 8, 1);
        upperBody.addChild(stomach);
        upperScale = new ModelRenderer(this, 50, 0);
        upperScale.setRotationPoint(0.0F, -20.0F, 5.5F);
        upperScale.addBox(-1.5F, -2.0F, 0.0F, 3, 4, 2);
        upperBody.addChild(upperScale);
        middleScale = new ModelRenderer(this, 50, 0);
        middleScale.setRotationPoint(0.0F, -12.0F, 5.5F);
        middleScale.addBox(-1.5F, -2.0F, 0.0F, 3, 4, 2);
        upperBody.addChild(middleScale);
        lowerScale = new ModelRenderer(this, 50, 0);
        lowerScale.setRotationPoint(0.0F, -4.0F, 5.5F);
        lowerScale.addBox(-1.5F, -2.0F, 0.0F, 3, 4, 2);
        upperBody.addChild(lowerScale);
        rightFrontChain = new ModelRenderer(this, 77, 0);
        rightFrontChain.setRotationPoint(7.5F, -8.5F, -13.0F);
        rightFrontChain.addBox(-1.5F, 0.0F, 0.0F, 3, 28, 0);
        rightBackChain = new ModelRenderer(this, 74, 24);
        rightBackChain.setRotationPoint(7.5F, 11.3F, 5.8F);
        rightBackChain.addBox(-1.5F, 0.0F, 0.0F, 3, 4, 0);
        leftFrontChain = new ModelRenderer(this, 74, 0);
        leftFrontChain.setRotationPoint(-7.5F, -8.5F, -13.0F);
        leftFrontChain.addBox(-1.5F, 0.0F, 0.0F, 3, 16, 0);
        leftBackChain = new ModelRenderer(this, 74, 16);
        leftBackChain.setRotationPoint(-7.5F, 11.3F, 5.8F);
        leftBackChain.addBox(-1.5F, 0.0F, 0.0F, 3, 10, 0);
        rightArm = new ModelRenderer(this, 0, 80);
        rightArm.setRotationPoint(10.0F, -3.0F, -8.0F);
        rightArm.addBox(0.0F, -3.5F, -3.5F, 7, 24, 7);
        setRotationAngles(rightArm, -0.2617993877991494F, 0.0F, 0.0F);
        leftArm = new ModelRenderer(this, 28, 80);
        leftArm.setRotationPoint(-10.0F, -3.0F, -8.0F);
        leftArm.addBox(-7.0F, -3.5F, -3.5F, 7, 24, 7);
        setRotationAngles(leftArm, -0.2617993877991494F, 0.0F, 0.0F);
        lowerBody = new ModelRenderer(this, 0, 51);
        lowerBody.setRotationPoint(0.0F, 14.0F, 0.0F);
        lowerBody.addBox(-10.5F, -2.5F, -4.5F, 21, 5, 9);
        rightLeg = new ModelRenderer(this, 0, 65);
        rightLeg.setRotationPoint(6.0F, 16.0F, 0.0F);
        rightLeg.addBox(-3.5F, 0.0F, -3.5F, 7, 8, 7);
        leftLeg = new ModelRenderer(this, 28, 65);
        leftLeg.setRotationPoint(-6.0F, 16.0F, 0.0F);
        leftLeg.addBox(-3.5F, 0.0F, -3.5F, 7, 8, 7);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);

        head.render(scale);
        upperBody.render(scale);
        rightFrontChain.render(scale);
        rightBackChain.render(scale);
        leftFrontChain.render(scale);
        leftBackChain.render(scale);
        rightArm.render(scale);
        leftArm.render(scale);
        lowerBody.render(scale);
        rightLeg.render(scale);
        leftLeg.render(scale);
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

    private void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

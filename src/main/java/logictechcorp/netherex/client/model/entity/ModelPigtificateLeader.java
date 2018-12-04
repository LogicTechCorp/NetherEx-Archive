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

package logictechcorp.netherex.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelPigtificateLeader extends ModelBase
{
    private ModelRenderer rightLeg;
    private ModelRenderer leftLeg;
    private ModelRenderer lowerBody;
    private ModelRenderer bellyButton;
    private ModelRenderer upperBody;
    private ModelRenderer chest;
    private ModelRenderer jaw;
    private ModelRenderer teeth;
    private ModelRenderer head;
    private ModelRenderer nose;
    private ModelRenderer hat;
    private ModelRenderer rightLowerHorn;
    private ModelRenderer rightUpperHorn;
    private ModelRenderer rightEarRing;
    private ModelRenderer leftLowerHorn;
    private ModelRenderer leftUpperHorn;
    private ModelRenderer leftEarRing;
    private ModelRenderer rightArm;
    private ModelRenderer leftArm;

    public ModelPigtificateLeader()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;

        this.rightLeg = new ModelRenderer(this, 20, 105);
        this.rightLeg.setRotationPoint(12.0F, 22.0F, 2.0F);
        this.rightLeg.addBox(-2.5F, -2.5F, -14.0F, 5, 5, 14);
        this.setRotationAngles(this.rightLeg, 0.11257373342468288F, -0.037524580582353764F, 0.0F);
        this.leftLeg = new ModelRenderer(this, 20, 105);
        this.leftLeg.setRotationPoint(-12.0F, 22.0F, 2.0F);
        this.leftLeg.addBox(-2.5F, -2.5F, -14.0F, 5, 5, 14);
        this.setRotationAngles(this.leftLeg, 0.11257373342468288F, 0.037524580582353764F, 0.0F);
        this.lowerBody = new ModelRenderer(this, 0, 72);
        this.lowerBody.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.lowerBody.addBox(-11.0F, 0.0F, -9.0F, 22, 15, 18);
        this.bellyButton = new ModelRenderer(this, 0, 72);
        this.bellyButton.setRotationPoint(0.0F, 9.0F, -10.0F);
        this.bellyButton.addBox(-2.0F, 0.0F, 0.0F, 4, 3, 1);
        this.lowerBody.addChild(this.bellyButton);
        this.upperBody = new ModelRenderer(this, 0, 49);
        this.upperBody.setRotationPoint(0.0F, 0.0F, 0.5F);
        this.upperBody.addBox(-9.5F, 0.0F, -7.0F, 19, 9, 14);
        this.chest = new ModelRenderer(this, 0, 35);
        this.chest.setRotationPoint(0.0F, 0.5F, -4.6F);
        this.chest.addBox(-9.0F, 0.0F, -2.5F, 18, 9, 5);
        this.setRotationAngles(this.chest, -0.1832595714594046F, 0.0F, 0.0F);
        this.upperBody.addChild(this.chest);
        this.jaw = new ModelRenderer(this, 0, 14);
        this.jaw.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.jaw.addBox(-4.5F, 0.0F, -5.0F, 9, 2, 10);
        this.teeth = new ModelRenderer(this, 45, 10);
        this.teeth.setRotationPoint(0.0F, -1.0F, -5.0F);
        this.teeth.addBox(-4.5F, 0.0F, 0.0F, 9, 1, 0);
        this.jaw.addChild(this.teeth);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, -6.0F, 0.0F);
        this.head.addBox(-4.0F, 0.0F, -4.0F, 8, 6, 8);
        this.jaw.addChild(this.head);
        this.nose = new ModelRenderer(this, 24, 0);
        this.nose.setRotationPoint(0.0F, 3.0F, -4.9F);
        this.nose.addBox(-2.0F, 0.0F, 0.0F, 4, 3, 1);
        this.head.addChild(this.nose);
        this.hat = new ModelRenderer(this, 44, 0);
        this.hat.setRotationPoint(0.0F, -4.0F, 0.0F);
        this.hat.addBox(-2.5F, 0.0F, -2.5F, 5, 4, 5);
        this.head.addChild(this.hat);
        this.rightLowerHorn = new ModelRenderer(this, 34, 0);
        this.rightLowerHorn.setRotationPoint(5.0F, 1.0F, 0.0F);
        this.rightLowerHorn.addBox(-1.5F, 0.0F, -1.0F, 3, 2, 2);
        this.head.addChild(this.rightLowerHorn);
        this.rightUpperHorn = new ModelRenderer(this, 34, 4);
        this.rightUpperHorn.setRotationPoint(1.0F, -3.0F, 0.0F);
        this.rightUpperHorn.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1);
        this.rightLowerHorn.addChild(this.rightUpperHorn);
        this.rightEarRing = new ModelRenderer(this, 38, 4);
        this.rightEarRing.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.rightEarRing.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 0);
        this.rightLowerHorn.addChild(this.rightEarRing);
        this.leftLowerHorn = new ModelRenderer(this, 34, 0);
        this.leftLowerHorn.setRotationPoint(-5.0F, 1.0F, 0.0F);
        this.leftLowerHorn.addBox(-1.5F, 0.0F, -1.0F, 3, 2, 2);
        this.head.addChild(this.leftLowerHorn);
        this.leftUpperHorn = new ModelRenderer(this, 34, 4);
        this.leftUpperHorn.setRotationPoint(-1.0F, -3.0F, 0.0F);
        this.leftUpperHorn.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1);
        this.leftLowerHorn.addChild(this.leftUpperHorn);
        this.leftEarRing = new ModelRenderer(this, 38, 4);
        this.leftEarRing.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.leftEarRing.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 0);
        this.leftLowerHorn.addChild(this.leftEarRing);
        this.rightArm = new ModelRenderer(this, 0, 105);
        this.rightArm.setRotationPoint(11.0F, 1.0F, 0.0F);
        this.rightArm.addBox(-2.5F, 0.0F, -2.5F, 5, 14, 5);
        this.setRotationAngles(this.rightArm, 0.0F, 0.0F, -0.10035643198967394F);
        this.leftArm = new ModelRenderer(this, 0, 105);
        this.leftArm.setRotationPoint(-11.0F, 1.0F, 0.0F);
        this.leftArm.addBox(-2.5F, 0.0F, -2.5F, 5, 14, 5);
        this.setRotationAngles(this.leftArm, 0.0F, 0.0F, 0.10035643198967394F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        this.rightLeg.render(scale);
        this.leftLeg.render(scale);
        this.lowerBody.render(scale);
        this.upperBody.render(scale);
        this.jaw.render(scale);
        this.rightArm.render(scale);
        this.leftArm.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        this.jaw.rotateAngleY = netHeadYaw * 0.017453292F;
        this.jaw.rotateAngleX = headPitch * 0.017453292F;

        this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / 1.0F;
        this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / 1.0F;
        this.rightArm.rotateAngleZ = 0.0F;
        this.leftArm.rotateAngleZ = 0.0F;

        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / 1.0F;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / 1.0F;
        this.rightLeg.rotateAngleY = 0.0F;
        this.leftLeg.rotateAngleY = 0.0F;
        this.rightLeg.rotateAngleZ = 0.0F;
        this.leftLeg.rotateAngleZ = 0.0F;
    }

    public void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

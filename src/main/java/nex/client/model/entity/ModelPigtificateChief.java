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

public class ModelPigtificateChief extends ModelBase
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

    public ModelPigtificateChief() 
    {
        textureWidth = 128;
        textureHeight = 128;

        rightLeg = new ModelRenderer(this, 20, 105);
        rightLeg.setRotationPoint(12.0F, 22.0F, 2.0F);
        rightLeg.addBox(-2.5F, -2.5F, -14.0F, 5, 5, 14);
        setRotationAngles(rightLeg, 0.11257373342468288F, -0.037524580582353764F, 0.0F);
        leftLeg = new ModelRenderer(this, 20, 105);
        leftLeg.setRotationPoint(-12.0F, 22.0F, 2.0F);
        leftLeg.addBox(-2.5F, -2.5F, -14.0F, 5, 5, 14);
        setRotationAngles(leftLeg, 0.11257373342468288F, 0.037524580582353764F, 0.0F);
        lowerBody = new ModelRenderer(this, 0, 72);
        lowerBody.setRotationPoint(0.0F, 9.0F, 0.0F);
        lowerBody.addBox(-11.0F, 0.0F, -9.0F, 22, 15, 18);
        bellyButton = new ModelRenderer(this, 0, 72);
        bellyButton.setRotationPoint(0.0F, 9.0F, -10.0F);
        bellyButton.addBox(-2.0F, 0.0F, 0.0F, 4, 3, 1);
        lowerBody.addChild(bellyButton);
        upperBody = new ModelRenderer(this, 0, 49);
        upperBody.setRotationPoint(0.0F, 0.0F, 0.5F);
        upperBody.addBox(-9.5F, 0.0F, -7.0F, 19, 9, 14);
        chest = new ModelRenderer(this, 0, 35);
        chest.setRotationPoint(0.0F, 0.5F, -4.6F);
        chest.addBox(-9.0F, 0.0F, -2.5F, 18, 9, 5);
        setRotationAngles(chest, -0.1832595714594046F, 0.0F, 0.0F);
        upperBody.addChild(chest);
        jaw = new ModelRenderer(this, 0, 14);
        jaw.setRotationPoint(0.0F, -2.0F, 0.0F);
        jaw.addBox(-4.5F, 0.0F, -5.0F, 9, 2, 10);
        teeth = new ModelRenderer(this, 45, 10);
        teeth.setRotationPoint(0.0F, -1.0F, -5.0F);
        teeth.addBox(-4.5F, 0.0F, 0.0F, 9, 1, 0);
        jaw.addChild(teeth);
        head = new ModelRenderer(this, 0, 0);
        head.setRotationPoint(0.0F, -6.0F, 0.0F);
        head.addBox(-4.0F, 0.0F, -4.0F, 8, 6, 8);
        jaw.addChild(head);
        nose = new ModelRenderer(this, 24, 0);
        nose.setRotationPoint(0.0F, 3.0F, -4.9F);
        nose.addBox(-2.0F, 0.0F, 0.0F, 4, 3, 1);
        head.addChild(nose);
        hat = new ModelRenderer(this, 44, 0);
        hat.setRotationPoint(0.0F, -4.0F, 0.0F);
        hat.addBox(-2.5F, 0.0F, -2.5F, 5, 4, 5);
        head.addChild(hat);
        rightLowerHorn = new ModelRenderer(this, 34, 0);
        rightLowerHorn.setRotationPoint(5.0F, 1.0F, 0.0F);
        rightLowerHorn.addBox(-1.5F, 0.0F, -1.0F, 3, 2, 2);
        head.addChild(rightLowerHorn);
        rightUpperHorn = new ModelRenderer(this, 34, 4);
        rightUpperHorn.setRotationPoint(1.0F, -3.0F, 0.0F);
        rightUpperHorn.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1);
        rightLowerHorn.addChild(rightUpperHorn);
        rightEarRing = new ModelRenderer(this, 38, 4);
        rightEarRing.setRotationPoint(0.0F, 2.0F, 0.0F);
        rightEarRing.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 0);
        rightLowerHorn.addChild(rightEarRing);
        leftLowerHorn = new ModelRenderer(this, 34, 0);
        leftLowerHorn.setRotationPoint(-5.0F, 1.0F, 0.0F);
        leftLowerHorn.addBox(-1.5F, 0.0F, -1.0F, 3, 2, 2);
        head.addChild(leftLowerHorn);
        leftUpperHorn = new ModelRenderer(this, 34, 4);
        leftUpperHorn.setRotationPoint(-1.0F, -3.0F, 0.0F);
        leftUpperHorn.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1);
        leftLowerHorn.addChild(leftUpperHorn);
        leftEarRing = new ModelRenderer(this, 38, 4);
        leftEarRing.setRotationPoint(0.0F, 2.0F, 0.0F);
        leftEarRing.addBox(-0.5F, 0.0F, 0.0F, 1, 1, 0);
        leftLowerHorn.addChild(leftEarRing);
        rightArm = new ModelRenderer(this, 0, 105);
        rightArm.setRotationPoint(11.0F, 1.0F, 0.0F);
        rightArm.addBox(-2.5F, 0.0F, -2.5F, 5, 14, 5);
        setRotationAngles(rightArm, 0.0F, 0.0F, -0.10035643198967394F);
        leftArm = new ModelRenderer(this, 0, 105);
        leftArm.setRotationPoint(-11.0F, 1.0F, 0.0F);
        leftArm.addBox(-2.5F, 0.0F, -2.5F, 5, 14, 5);
        setRotationAngles(leftArm, 0.0F, 0.0F, 0.10035643198967394F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale) 
    {
        rightLeg.render(scale);
        leftLeg.render(scale);
        lowerBody.render(scale);
        upperBody.render(scale);
        jaw.render(scale);
        rightArm.render(scale);
        leftArm.render(scale);
    }

    public void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z) 
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

}

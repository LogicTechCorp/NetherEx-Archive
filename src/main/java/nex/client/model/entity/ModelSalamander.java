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

//Fish2's code 
package nex.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSalamander extends ModelBase
{
    private ModelRenderer frontRightLeg;
    private ModelRenderer frontLeftLeg;
    private ModelRenderer toothLeft;
    private ModelRenderer toothRight;
    private ModelRenderer jawLower;
    private ModelRenderer jawUpper;
    private ModelRenderer bodyFront;
    private ModelRenderer bodyBack;
    private ModelRenderer tail;
    private ModelRenderer backRightLeg;
    private ModelRenderer backLeftLeg;

    public ModelSalamander()
    {
        frontRightLeg = new ModelRenderer(this, 0, 18);
        frontRightLeg.addBox(-1.5F, -1.5F, -1.5F, 3, 6, 3, 0.0F);
        frontRightLeg.setRotationPoint(-6F, 19.5F, -4.5F);
        frontLeftLeg = new ModelRenderer(this, 0, 18);
        frontLeftLeg.addBox(-1.5F, -1.5F, -1.5F, 3, 6, 3, 0.0F);
        frontLeftLeg.setRotationPoint(6F, 19.5F, -4.5F);
        toothLeft = new ModelRenderer(this, 0, 27);
        toothLeft.addBox(1.5F, -6.5F, -1F, 1, 1, 1, 0.0F);
        toothLeft.setRotationPoint(0F, 20.5F, -7.5F);
        toothLeft.rotateAngleX = 1.5708F;
        toothRight = new ModelRenderer(this, 0, 27);
        toothRight.addBox(-2.5F, -6.5F, -1F, 1, 1, 1, 0.0F);
        toothRight.setRotationPoint(0F, 20.5F, -7.5F);
        toothRight.rotateAngleX = 1.5708F;
        jawLower = new ModelRenderer(this, 0, 9);
        jawLower.addBox(-3F, -7F, -2.5F, 6, 7, 2, 0.0F);
        jawLower.setRotationPoint(0F, 20.5F, -7.5F);
        jawLower.rotateAngleX = 1.5708F;
        jawUpper = new ModelRenderer(this, 0, 0);
        jawUpper.addBox(-2.5F, -6.5F, 0F, 5, 6, 3, 0.0F);
        jawUpper.setRotationPoint(0F, 20.5F, -7.5F);
        jawUpper.rotateAngleX = 1.5708F;
        bodyFront = new ModelRenderer(this, 16, 0);
        bodyFront.addBox(-4.5F, -5F, -3F, 9, 10, 6, 0.0F);
        bodyFront.setRotationPoint(0F, 20F, -3F);
        bodyFront.rotateAngleX = 1.5708F;
        bodyBack = new ModelRenderer(this, 16, 16);
        bodyBack.addBox(-3.5F, -3.5F, -2.5F, 7, 7, 5, 0.0F);
        bodyBack.setRotationPoint(0F, 20F, 5.5F);
        bodyBack.rotateAngleX = 1.5708F;
        tail = new ModelRenderer(this, 46, 0);
        tail.addBox(-2.5F, -3.5F, -1.5F, 5, 7, 3, 0.0F);
        tail.setRotationPoint(0F, 20F, 12.5F);
        tail.rotateAngleX = 1.5708F;
        backRightLeg = new ModelRenderer(this, 0, 18);
        backRightLeg.addBox(-1.5F, -1.5F, -1.5F, 3, 6, 3, 0.0F);
        backRightLeg.setRotationPoint(-5F, 19.5F, 5.5F);
        backLeftLeg = new ModelRenderer(this, 0, 18);
        backLeftLeg.addBox(-1.5F, -1.5F, -1.5F, 3, 6, 3, 0.0F);
        backLeftLeg.setRotationPoint(5F, 19.5F, 5.5F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        frontRightLeg.render(scale);
        frontLeftLeg.render(scale);
        toothLeft.renderWithRotation(scale);
        toothRight.renderWithRotation(scale);
        jawLower.renderWithRotation(scale);
        jawUpper.renderWithRotation(scale);
        bodyFront.renderWithRotation(scale);
        bodyBack.renderWithRotation(scale);
        tail.renderWithRotation(scale);
        backRightLeg.render(scale);
        backLeftLeg.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scaleFactor, Entity entity)
    {
        frontRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        frontLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        backRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        backLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }
}

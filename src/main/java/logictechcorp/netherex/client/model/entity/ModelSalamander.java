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

//Fish2's code 
package logictechcorp.netherex.client.model.entity;

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
        this.frontRightLeg = new ModelRenderer(this, 0, 18);
        this.frontRightLeg.addBox(-1.5F, -1.5F, -1.5F, 3, 6, 3, 0.0F);
        this.frontRightLeg.setRotationPoint(-6F, 19.5F, -4.5F);
        this.frontLeftLeg = new ModelRenderer(this, 0, 18);
        this.frontLeftLeg.addBox(-1.5F, -1.5F, -1.5F, 3, 6, 3, 0.0F);
        this.frontLeftLeg.setRotationPoint(6F, 19.5F, -4.5F);
        this.toothLeft = new ModelRenderer(this, 0, 27);
        this.toothLeft.addBox(1.5F, -6.5F, -1F, 1, 1, 1, 0.0F);
        this.toothLeft.setRotationPoint(0F, 20.5F, -7.5F);
        this.toothLeft.rotateAngleX = 1.5708F;
        this.toothRight = new ModelRenderer(this, 0, 27);
        this.toothRight.addBox(-2.5F, -6.5F, -1F, 1, 1, 1, 0.0F);
        this.toothRight.setRotationPoint(0F, 20.5F, -7.5F);
        this.toothRight.rotateAngleX = 1.5708F;
        this.jawLower = new ModelRenderer(this, 0, 9);
        this.jawLower.addBox(-3F, -7F, -2.5F, 6, 7, 2, 0.0F);
        this.jawLower.setRotationPoint(0F, 20.5F, -7.5F);
        this.jawLower.rotateAngleX = 1.5708F;
        this.jawUpper = new ModelRenderer(this, 0, 0);
        this.jawUpper.addBox(-2.5F, -6.5F, 0F, 5, 6, 3, 0.0F);
        this.jawUpper.setRotationPoint(0F, 20.5F, -7.5F);
        this.jawUpper.rotateAngleX = 1.5708F;
        this.bodyFront = new ModelRenderer(this, 16, 0);
        this.bodyFront.addBox(-4.5F, -5F, -3F, 9, 10, 6, 0.0F);
        this.bodyFront.setRotationPoint(0F, 20F, -3F);
        this.bodyFront.rotateAngleX = 1.5708F;
        this.bodyBack = new ModelRenderer(this, 16, 16);
        this.bodyBack.addBox(-3.5F, -3.5F, -2.5F, 7, 7, 5, 0.0F);
        this.bodyBack.setRotationPoint(0F, 20F, 5.5F);
        this.bodyBack.rotateAngleX = 1.5708F;
        this.tail = new ModelRenderer(this, 46, 0);
        this.tail.addBox(-2.5F, -3.5F, -1.5F, 5, 7, 3, 0.0F);
        this.tail.setRotationPoint(0F, 20F, 12.5F);
        this.tail.rotateAngleX = 1.5708F;
        this.backRightLeg = new ModelRenderer(this, 0, 18);
        this.backRightLeg.addBox(-1.5F, -1.5F, -1.5F, 3, 6, 3, 0.0F);
        this.backRightLeg.setRotationPoint(-5F, 19.5F, 5.5F);
        this.backLeftLeg = new ModelRenderer(this, 0, 18);
        this.backLeftLeg.addBox(-1.5F, -1.5F, -1.5F, 3, 6, 3, 0.0F);
        this.backLeftLeg.setRotationPoint(5F, 19.5F, 5.5F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        this.frontRightLeg.render(scale);
        this.frontLeftLeg.render(scale);
        this.toothLeft.renderWithRotation(scale);
        this.toothRight.renderWithRotation(scale);
        this.jawLower.renderWithRotation(scale);
        this.jawUpper.renderWithRotation(scale);
        this.bodyFront.renderWithRotation(scale);
        this.bodyBack.renderWithRotation(scale);
        this.tail.renderWithRotation(scale);
        this.backRightLeg.render(scale);
        this.backLeftLeg.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scaleFactor, Entity entity)
    {
        this.frontRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.frontLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        this.backRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        this.backLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }
}

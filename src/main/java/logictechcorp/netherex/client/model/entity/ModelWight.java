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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelWight extends ModelBase
{
    private ModelRenderer head;
    private ModelRenderer hood;
    private ModelRenderer upperBody;
    private ModelRenderer lowerBody;
    private ModelRenderer rightArm;
    private ModelRenderer leftArm;
    private ModelRenderer rightLeg;
    private ModelRenderer leftLeg;

    public ModelWight()
    {
        this.head = new ModelRenderer(this, 0, 10);
        this.head.addBox(-2, -2, -2, 4, 4, 4, 0F);
        this.head.setRotationPoint(0F, 2.5F, -3.5F);
        this.hood = new ModelRenderer(this, 0, 0);
        this.hood.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5, 0F);
        this.hood.setRotationPoint(0F, 2.5F, -3.5F);
        this.upperBody = new ModelRenderer(this, 14, 23);
        this.upperBody.addBox(-4.0F, -2.5F, -2F, 9, 5, 4, 0F);
        this.upperBody.setRotationPoint(-0.5F, 5.5F, -0.5F);
        this.upperBody.rotateAngleX = 0.4363323F;
        this.lowerBody = new ModelRenderer(this, 0, 24);
        this.lowerBody.addBox(-2.5F, -3.0F, -1F, 5, 6, 2, 0F);
        this.lowerBody.setRotationPoint(0F, 10.5F, 0F);
        this.rightArm = new ModelRenderer(this, 24, 0);
        this.rightArm.addBox(-0.5F, -0.5F, -0.5F, 1, 18, 1, 0F);
        this.rightArm.setRotationPoint(-3.5F, 5F, 0F);
        this.leftArm = new ModelRenderer(this, 24, 0);
        this.leftArm.addBox(-0.5F, -0.5F, -0.5F, 1, 18, 1, 0F);
        this.leftArm.setRotationPoint(3.5F, 5F, 0F);
        this.rightLeg = new ModelRenderer(this, 20, 0);
        this.rightLeg.addBox(-0.5F, -0.5F, -0.5F, 1, 12, 1, 0F);
        this.rightLeg.setRotationPoint(1, 13, 0);
        this.leftLeg = new ModelRenderer(this, 20, 0);
        this.leftLeg.addBox(-0.5F, -0.5F, -0.5F, 1, 12, 1, 0F);
        this.leftLeg.setRotationPoint(-1, 13, 0);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scaleFactor)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scaleFactor, entity);
        this.head.render(scaleFactor);
        this.hood.render(scaleFactor);
        this.upperBody.render(scaleFactor);
        this.lowerBody.render(scaleFactor);
        this.rightArm.render(scaleFactor);
        this.leftArm.render(scaleFactor);
        this.rightLeg.render(scaleFactor);
        this.leftLeg.render(scaleFactor);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scaleFactor, Entity entity)
    {
        this.head.rotateAngleY = rotationYaw / 57.29578F;
        this.head.rotateAngleX = rotationPitch / 57.29578F;
        this.hood.rotateAngleY = rotationYaw / 57.29578F;
        this.hood.rotateAngleX = rotationPitch / 57.29578F;
        this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 2.0F * limbSwingAmount * 0.5F;
        this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        this.rightArm.rotateAngleZ = 0F;
        this.leftArm.rotateAngleZ = 0F;
        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        this.rightLeg.rotateAngleY = 0F;
        this.leftLeg.rotateAngleY = 0F;
    }
}

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
        head = new ModelRenderer(this, 0, 10);
        head.addBox(-2, -2, -2, 4, 4, 4, 0F);
        head.setRotationPoint(0F, 2.5F, -3.5F);
        hood = new ModelRenderer(this, 0, 0);
        hood.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5, 0F);
        hood.setRotationPoint(0F, 2.5F, -3.5F);
        upperBody = new ModelRenderer(this, 14, 23);
        upperBody.addBox(-4.0F, -2.5F, -2F, 9, 5, 4, 0F);
        upperBody.setRotationPoint(-0.5F, 5.5F, -0.5F);
        upperBody.rotateAngleX = 0.4363323F;
        lowerBody = new ModelRenderer(this, 0, 24);
        lowerBody.addBox(-2.5F, -3.0F, -1F, 5, 6, 2, 0F);
        lowerBody.setRotationPoint(0F, 10.5F, 0F);
        rightArm = new ModelRenderer(this, 24, 0);
        rightArm.addBox(-0.5F, -0.5F, -0.5F, 1, 18, 1, 0F);
        rightArm.setRotationPoint(-3.5F, 5F, 0F);
        leftArm = new ModelRenderer(this, 24, 0);
        leftArm.addBox(-0.5F, -0.5F, -0.5F, 1, 18, 1, 0F);
        leftArm.setRotationPoint(3.5F, 5F, 0F);
        rightLeg = new ModelRenderer(this, 20, 0);
        rightLeg.addBox(-0.5F, -0.5F, -0.5F, 1, 12, 1, 0F);
        rightLeg.setRotationPoint(1, 13, 0);
        leftLeg = new ModelRenderer(this, 20, 0);
        leftLeg.addBox(-0.5F, -0.5F, -0.5F, 1, 12, 1, 0F);
        leftLeg.setRotationPoint(-1, 13, 0);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scaleFactor)
    {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scaleFactor, entity);
        head.render(scaleFactor);
        hood.render(scaleFactor);
        upperBody.render(scaleFactor);
        lowerBody.render(scaleFactor);
        rightArm.render(scaleFactor);
        leftArm.render(scaleFactor);
        rightLeg.render(scaleFactor);
        leftLeg.render(scaleFactor);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scaleFactor, Entity entity)
    {
        head.rotateAngleY = rotationYaw / 57.29578F;
        head.rotateAngleX = rotationPitch / 57.29578F;
        hood.rotateAngleY = rotationYaw / 57.29578F;
        hood.rotateAngleX = rotationPitch / 57.29578F;
        rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 2.0F * limbSwingAmount * 0.5F;
        leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        rightArm.rotateAngleZ = 0F;
        leftArm.rotateAngleZ = 0F;
        rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        rightLeg.rotateAngleY = 0F;
        leftLeg.rotateAngleY = 0F;
    }
}

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

public class ModelSporeCreeper extends ModelBase
{
    private ModelRenderer head;
    private ModelRenderer body;
    private ModelRenderer frontRightLeg;
    private ModelRenderer frontLeftLeg;
    private ModelRenderer backRightLeg;
    private ModelRenderer backLeftLeg;

    public ModelSporeCreeper()
    {
        textureHeight = 64;

        head = new ModelRenderer(this, 0, 0);
        head.setRotationPoint(0.0F, 4.0F, 0.0F);
        head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8);
        body = new ModelRenderer(this, 0, 16);
        body.setRotationPoint(0.0F, 4.0F, 0.0F);
        body.addBox(-4.0F, 0.0F, -2.0F, 8, 14, 4);
        frontRightLeg = new ModelRenderer(this, 0, 34);
        frontRightLeg.setRotationPoint(2.0F, 18.0F, -4.0F);
        frontRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4);
        frontLeftLeg = new ModelRenderer(this, 16, 34);
        frontLeftLeg.setRotationPoint(-2.0F, 18.0F, -4.0F);
        frontLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4);
        backRightLeg = new ModelRenderer(this, 0, 44);
        backRightLeg.setRotationPoint(2.0F, 18.0F, 4.0F);
        backRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4);
        backLeftLeg = new ModelRenderer(this, 16, 44);
        backLeftLeg.setRotationPoint(-2.0F, 18.0F, 4.0F);
        backLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        head.render(scale);
        body.render(scale);
        frontRightLeg.render(scale);
        frontLeftLeg.render(scale);
        backRightLeg.render(scale);
        backLeftLeg.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity)
    {
        head.rotateAngleY = netHeadYaw * 0.017453292F;
        head.rotateAngleX = headPitch * 0.017453292F;
        frontRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        frontLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        backRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        backLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }
}

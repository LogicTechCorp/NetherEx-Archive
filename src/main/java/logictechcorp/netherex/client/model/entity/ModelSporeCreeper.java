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
        this.textureHeight = 64;

        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8);
        this.body = new ModelRenderer(this, 0, 16);
        this.body.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 14, 4);
        this.frontRightLeg = new ModelRenderer(this, 0, 34);
        this.frontRightLeg.setRotationPoint(2.0F, 18.0F, -4.0F);
        this.frontRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4);
        this.frontLeftLeg = new ModelRenderer(this, 16, 34);
        this.frontLeftLeg.setRotationPoint(-2.0F, 18.0F, -4.0F);
        this.frontLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4);
        this.backRightLeg = new ModelRenderer(this, 0, 44);
        this.backRightLeg.setRotationPoint(2.0F, 18.0F, 4.0F);
        this.backRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4);
        this.backLeftLeg = new ModelRenderer(this, 16, 44);
        this.backLeftLeg.setRotationPoint(-2.0F, 18.0F, 4.0F);
        this.backLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        this.head.render(scale);
        this.body.render(scale);
        this.frontRightLeg.render(scale);
        this.frontLeftLeg.render(scale);
        this.backRightLeg.render(scale);
        this.backLeftLeg.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity)
    {
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.frontRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.frontLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.backRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.backLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }
}

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

package logictechcorp.netherex.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import logictechcorp.netherex.entity.hostile.CoolmarSpiderEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CoolmarSpiderModel extends EntityModel<CoolmarSpiderEntity>
{
    private ModelRenderer head;
    private ModelRenderer thorax;
    private ModelRenderer abdomen;
    private ModelRenderer rightLeg1;
    private ModelRenderer rightLeg2;
    private ModelRenderer rightLeg3;
    private ModelRenderer rightLeg4;
    private ModelRenderer leftLeg1;
    private ModelRenderer leftLeg2;
    private ModelRenderer leftLeg3;
    private ModelRenderer leftLeg4;

    public CoolmarSpiderModel()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.abdomen = new ModelRenderer(this, 20, 0);
        this.abdomen.setRotationPoint(0.0F, 13.0F, 7.0F);
        this.abdomen.addBox(-5.0F, -6.0F, 0.0F, 10, 12, 12, 0.0F);
        this.setRotationAngle(this.abdomen, 0.3490658503988659F, 0.0F, 0.0F);
        this.rightLeg3 = new ModelRenderer(this, 0, 0);
        this.rightLeg3.setRotationPoint(-2.0F, 15.5F, 2.5F);
        this.rightLeg3.addBox(-10.0F, -3.0F, 0.0F, 10, 14, 0, 0.0F);
        this.setRotationAngle(this.rightLeg3, 0.0F, -0.08726646259971647F, 0.2617993877991494F);
        this.leftLeg2 = new ModelRenderer(this, 0, 0);
        this.leftLeg2.mirror = true;
        this.leftLeg2.setRotationPoint(2.0F, 15.5F, 3.0F);
        this.leftLeg2.addBox(0.0F, -3.0F, 0.0F, 10, 14, 0, 0.0F);
        this.setRotationAngle(this.leftLeg2, 0.0F, -0.17453292519943295F, -0.2617993877991494F);
        this.rightLeg1 = new ModelRenderer(this, 0, 0);
        this.rightLeg1.setRotationPoint(-2.0F, 15.5F, 3.5F);
        this.rightLeg1.addBox(-10.0F, -3.0F, 0.0F, 10, 14, 0, 0.0F);
        this.setRotationAngle(this.rightLeg1, 0.0F, 0.3490658503988659F, 0.2617993877991494F);
        this.leftLeg3 = new ModelRenderer(this, 0, 0);
        this.leftLeg3.mirror = true;
        this.leftLeg3.setRotationPoint(2.0F, 15.5F, 2.5F);
        this.leftLeg3.addBox(0.0F, -3.0F, 0.0F, 10, 14, 0, 0.0F);
        this.setRotationAngle(this.leftLeg3, 0.0F, 0.08726646259971647F, -0.2617993877991494F);
        this.leftLeg1 = new ModelRenderer(this, 0, 0);
        this.leftLeg1.mirror = true;
        this.leftLeg1.setRotationPoint(2.0F, 15.5F, 3.5F);
        this.leftLeg1.addBox(0.0F, -3.0F, 0.0F, 10, 14, 0, 0.0F);
        this.setRotationAngle(this.leftLeg1, 0.0F, -0.3490658503988659F, -0.2617993877991494F);
        this.thorax = new ModelRenderer(this, 0, 29);
        this.thorax.setRotationPoint(0.0F, 15.0F, -3.0F);
        this.thorax.addBox(-3.0F, -3.5F, 0.0F, 6, 7, 12, 0.0F);
        this.setRotationAngle(this.thorax, 0.17453292519943295F, 0.0F, 0.0F);
        this.rightLeg4 = new ModelRenderer(this, 0, 0);
        this.rightLeg4.setRotationPoint(-2.0F, 15.5F, 2.0F);
        this.rightLeg4.addBox(-10.0F, -3.0F, 0.0F, 10, 14, 0, 0.0F);
        this.setRotationAngle(this.rightLeg4, 0.0F, -0.3490658503988659F, 0.2617993877991494F);
        this.rightLeg2 = new ModelRenderer(this, 0, 0);
        this.rightLeg2.setRotationPoint(-2.0F, 15.5F, 3.0F);
        this.rightLeg2.addBox(-10.0F, -3.0F, 0.0F, 10, 14, 0, 0.0F);
        this.setRotationAngle(this.rightLeg2, 0.0F, 0.17453292519943295F, 0.2617993877991494F);
        this.head = new ModelRenderer(this, 0, 48);
        this.head.setRotationPoint(0.0F, 15.0F, -2.0F);
        this.head.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
        this.leftLeg4 = new ModelRenderer(this, 0, 0);
        this.leftLeg4.mirror = true;
        this.leftLeg4.setRotationPoint(2.0F, 15.5F, 2.0F);
        this.leftLeg4.addBox(0.0F, -3.0F, 0.0F, 10, 14, 0, 0.0F);
        this.setRotationAngle(this.leftLeg4, 0.0F, 0.3490658503988659F, -0.2617993877991494F);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        this.abdomen.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightLeg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leftLeg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightLeg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leftLeg3.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leftLeg1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.thorax.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightLeg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.rightLeg2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.leftLeg4.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setRotationAngles(CoolmarSpiderEntity coolmarSpider, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        this.head.rotateAngleX = headPitch * 0.017453292F + 0.1308996938995747F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;

        this.leftLeg1.rotateAngleZ = 0.2617993877991494F;
        this.rightLeg1.rotateAngleZ = -0.2617993877991494F;
        this.leftLeg2.rotateAngleZ = 0.2617993877991494F;
        this.rightLeg2.rotateAngleZ = -0.2617993877991494F;
        this.leftLeg3.rotateAngleZ = 0.2617993877991494F;
        this.rightLeg3.rotateAngleZ = -0.2617993877991494F;
        this.leftLeg4.rotateAngleZ = 0.2617993877991494F;
        this.rightLeg4.rotateAngleZ = -0.2617993877991494F;

        this.leftLeg1.rotateAngleY = -0.7853981633974483F;
        this.rightLeg1.rotateAngleY = 0.7853981633974483F;
        this.leftLeg2.rotateAngleY = -0.2617993877991494F;
        this.rightLeg2.rotateAngleY = 0.2617993877991494F;
        this.leftLeg3.rotateAngleY = 0.2617993877991494F;
        this.rightLeg3.rotateAngleY = -0.2617993877991494F;
        this.leftLeg4.rotateAngleY = 0.7853981633974483F;
        this.rightLeg4.rotateAngleY = -0.7853981633974483F;

        float f3 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
        float f4 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * limbSwingAmount;
        float f5 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float) Math.PI / 2F)) * 0.4F) * limbSwingAmount;
        float f6 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float) Math.PI * 3F / 2F)) * 0.4F) * limbSwingAmount;
        float f7 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
        float f8 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float) Math.PI) * 0.4F) * limbSwingAmount;
        float f9 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float) Math.PI / 2F)) * 0.4F) * limbSwingAmount;
        float f10 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float) Math.PI * 3F / 2F)) * 0.4F) * limbSwingAmount;
        this.leftLeg1.rotateAngleY += f3;
        this.rightLeg1.rotateAngleY += -f3;
        this.leftLeg2.rotateAngleY += f4;
        this.rightLeg2.rotateAngleY += -f4;
        this.leftLeg3.rotateAngleY += f5;
        this.rightLeg3.rotateAngleY += -f5;
        this.leftLeg4.rotateAngleY += f6;
        this.rightLeg4.rotateAngleY += -f6;
        this.leftLeg1.rotateAngleZ += f7;
        this.rightLeg1.rotateAngleZ += -f7;
        this.leftLeg2.rotateAngleZ += f8;
        this.rightLeg2.rotateAngleZ += -f8;
        this.leftLeg3.rotateAngleZ += f9;
        this.rightLeg3.rotateAngleZ += -f9;
        this.leftLeg4.rotateAngleZ += f10;
        this.rightLeg4.rotateAngleZ += -f10;
    }

    private void setRotationAngle(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
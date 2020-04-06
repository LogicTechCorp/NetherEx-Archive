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
import logictechcorp.netherex.entity.hostile.SpinoutEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpinoutModel extends EntityModel<SpinoutEntity>
{
    private ModelRenderer head;
    private ModelRenderer upperBody;
    private ModelRenderer middleBody;
    private ModelRenderer lowerBody;
    private ModelRenderer upperSpinner;
    private ModelRenderer middleSpinner;
    private ModelRenderer lowerSpinner;

    public SpinoutModel()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;

        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.head.addBox(-4.0F, 0.0F, -4.0F, 8, 8, 8);
        this.upperBody = new ModelRenderer(this, 0, 16);
        this.upperBody.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.upperBody.addBox(-2.5F, 0.0F, -2.5F, 5, 15, 5);
        this.middleBody = new ModelRenderer(this, 0, 36);
        this.middleBody.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.middleBody.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4);
        this.lowerBody = new ModelRenderer(this, 0, 44);
        this.lowerBody.setRotationPoint(0.0F, 20.0F, 0.0F);
        this.lowerBody.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2);
        this.upperSpinner = new ModelRenderer(this, 32, 0);
        this.upperSpinner.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.upperSpinner.addBox(-12.0F, 0.0F, -12.0F, 24, 0, 24);
        this.middleSpinner = new ModelRenderer(this, 20, 24);
        this.middleSpinner.setRotationPoint(0.0F, 11.0F, 0.0F);
        this.middleSpinner.addBox(-13.5F, 0.0F, -13.5F, 27, 0, 27);
        this.lowerSpinner = new ModelRenderer(this, 60, 51);
        this.lowerSpinner.setRotationPoint(0.0F, 18.0F, 0.0F);
        this.lowerSpinner.addBox(-9.0F, 0.0F, -9.0F, 17, 0, 17);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        this.head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.upperBody.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.middleBody.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.lowerBody.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.upperSpinner.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.middleSpinner.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.lowerSpinner.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setRotationAngles(SpinoutEntity spinout, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        float spin = spinout.isSpinning() ? ageInTicks : 0;

        this.upperBody.rotateAngleY = -(((float) Math.PI / 4F) + spin * (float) Math.PI * 0.03F * 7.0F);
        this.middleBody.rotateAngleY = (((float) Math.PI / 4F) + spin * (float) Math.PI * 0.03F * 7.0F) + 0.7853981633974483F;
        this.lowerBody.rotateAngleY = -(((float) Math.PI / 4F) + spin * (float) Math.PI * 0.03F * 7.0F);
        this.upperSpinner.rotateAngleY = ((float) Math.PI / 4F) + spin * (float) Math.PI * 0.03F * 7.0F;
        this.middleSpinner.rotateAngleY = -((((float) Math.PI / 4F) + spin * (float) Math.PI * 0.03F * 7.0F) + 0.7853981633974483F);
        this.lowerSpinner.rotateAngleY = ((float) Math.PI / 4F) + spin * (float) Math.PI * 0.03F * 7.0F;
    }
}

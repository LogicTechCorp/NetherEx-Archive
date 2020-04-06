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
import logictechcorp.netherex.entity.hostile.SporeEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SporeModel extends EntityModel<SporeEntity>
{
    private ModelRenderer stageOne;
    private ModelRenderer stageTwo;
    private ModelRenderer stageThreeInner;
    private ModelRenderer stageThreeOuter;
    private ModelRenderer stageFour;
    private ModelRenderer stageFive;
    private boolean renderStage0;
    private boolean renderStage1;
    private boolean renderStage2;
    private boolean renderStage3;

    public SporeModel()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.stageOne = new ModelRenderer(this, 0, 0);
        this.stageOne.setRotationPoint(0.0F, 12.0F, 0.0F);
        this.stageOne.addBox(-5.0F, 0.0F, -5.0F, 10, 12, 10);
        this.stageTwo = new ModelRenderer(this, 0, 22);
        this.stageTwo.setRotationPoint(0.0F, 11.0F, 0.0F);
        this.stageTwo.addBox(-5.5F, 0.0F, -5.5F, 11, 13, 11);
        this.stageThreeInner = new ModelRenderer(this, 0, 46);
        this.stageThreeInner.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.stageThreeInner.addBox(-6.0F, 0.0F, -6.0F, 12, 14, 12);
        this.stageThreeOuter = new ModelRenderer(this, 48, 45);
        this.stageThreeOuter.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.stageThreeOuter.addBox(-6.5F, 0.0F, -6.5F, 13, 15, 13);
        this.stageFour = new ModelRenderer(this, 0, 72);
        this.stageFour.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.stageFour.addBox(-5.0F, 0.0F, -5.0F, 10, 8, 10);
        this.stageFive = new ModelRenderer(this, 0, 90);
        this.stageFive.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.stageFive.addBox(-5.5F, 0.0F, -5.5F, 11, 9, 11);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        if(this.renderStage0)
        {
            this.stageOne.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        }
        else if(this.renderStage1)
        {
            this.stageOne.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.stageTwo.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        }
        else if(this.renderStage2)
        {
            this.stageOne.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.stageTwo.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.stageThreeInner.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.stageThreeOuter.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        }
        else if(this.renderStage3)
        {
            this.stageOne.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.stageTwo.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.stageThreeInner.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.stageThreeOuter.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.stageFour.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        }
        else
        {
            this.stageOne.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.stageTwo.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.stageThreeInner.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.stageThreeOuter.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.stageFour.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.stageFive.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        }
    }

    @Override
    public void setLivingAnimations(SporeEntity spore, float limbSwing, float limbSwingAmount, float partialTick)
    {
        if(spore.getStage() == 0)
        {
            this.renderStage0 = true;
        }
        else if(spore.getStage() == 1)
        {
            this.renderStage1 = true;
        }
        else if(spore.getStage() == 2)
        {
            this.renderStage2 = true;
        }
        else if(spore.getStage() == 3)
        {
            this.renderStage3 = true;
        }
    }

    @Override
    public void setRotationAngles(SporeEntity spore, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {

    }
}

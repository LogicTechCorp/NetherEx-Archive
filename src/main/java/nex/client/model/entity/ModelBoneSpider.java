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
public class ModelBoneSpider extends ModelBase
{
    private ModelRenderer head;
    private ModelRenderer thorax;
    private ModelRenderer abdomen;
    private ModelRenderer rightLeg1Upper;
    private ModelRenderer rightLeg1Lower;
    private ModelRenderer rightLeg2Upper;
    private ModelRenderer rightLeg2Lower;
    private ModelRenderer rightLeg3Upper;
    private ModelRenderer rightLeg3Lower;
    private ModelRenderer rightLeg4Upper;
    private ModelRenderer rightLeg4Lower;
    private ModelRenderer leftLeg1Upper;
    private ModelRenderer leftLeg1Lower;
    private ModelRenderer leftLeg2Upper;
    private ModelRenderer leftLeg2Lower;
    private ModelRenderer leftLeg3Upper;
    private ModelRenderer leftLeg3Lower;
    private ModelRenderer leftLeg4Upper;
    private ModelRenderer leftLeg4Lower;

    public ModelBoneSpider()
    {
        this.textureHeight = 64;

        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 14.0F, 0.0F);
        this.head.addBox(-2.5F, -2.5F, -5.0F, 5, 5, 5);
        this.setRotationAngles(this.head, 0.1308996938995747F, 0.0F, 0.0F);
        this.thorax = new ModelRenderer(this, 0, 10);
        this.thorax.setRotationPoint(0.0F, 12.0F, 7.0F);
        this.thorax.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8);
        this.setRotationAngles(this.thorax, 0.19634954084936207F, 0.0F, 0.0F);
        this.abdomen = new ModelRenderer(this, 0, 26);
        this.abdomen.setRotationPoint(0.0F, 12.5F, 11.0F);
        this.abdomen.addBox(-6.0F, -5.0F, -6.5F, 12, 10, 13);
        this.setRotationAngles(this.abdomen, -0.2617993877991494F, 0.0F, 0.0F);
        this.rightLeg1Upper = new ModelRenderer(this, 20, 0);
        this.rightLeg1Upper.setRotationPoint(4.0F, 15.0F, 1.5F);
        this.rightLeg1Upper.addBox(0.0F, -0.5F, -0.5F, 13, 1, 1);
        this.setRotationAngles(this.rightLeg1Upper, 0.0F, 0.7853981633974483F, -0.2617993877991494F);
        this.rightLeg1Lower = new ModelRenderer(this, 50, 0);
        this.rightLeg1Lower.setRotationPoint(13.5F, -0.5F, 0.0F);
        this.rightLeg1Lower.addBox(-0.5F, 0.0F, -0.5F, 1, 13, 1);
        this.rightLeg1Upper.addChild(this.rightLeg1Lower);
        this.rightLeg2Upper = new ModelRenderer(this, 20, 0);
        this.rightLeg2Upper.setRotationPoint(4.0F, 15.0F, 1.5F);
        this.rightLeg2Upper.addBox(0.0F, -0.5F, -0.5F, 13, 1, 1);
        this.setRotationAngles(this.rightLeg2Upper, 0.0F, 0.2617993877991494F, -0.2617993877991494F);
        this.rightLeg2Lower = new ModelRenderer(this, 50, 0);
        this.rightLeg2Lower.setRotationPoint(13.5F, -0.5F, 0.0F);
        this.rightLeg2Lower.addBox(-0.5F, 0.0F, -0.5F, 1, 13, 1);
        this.rightLeg2Upper.addChild(this.rightLeg2Lower);
        this.rightLeg3Upper = new ModelRenderer(this, 20, 0);
        this.rightLeg3Upper.setRotationPoint(4.0F, 15.0F, 1.5F);
        this.rightLeg3Upper.addBox(0.0F, -0.5F, -0.5F, 13, 1, 1);
        this.setRotationAngles(this.rightLeg3Upper, 0.0F, -0.2617993877991494F, -0.2617993877991494F);
        this.rightLeg3Lower = new ModelRenderer(this, 50, 0);
        this.rightLeg3Lower.setRotationPoint(13.5F, -0.5F, 0.0F);
        this.rightLeg3Lower.addBox(-0.5F, 0.0F, -0.5F, 1, 13, 1);
        this.rightLeg3Upper.addChild(this.rightLeg3Lower);
        this.rightLeg4Upper = new ModelRenderer(this, 20, 0);
        this.rightLeg4Upper.setRotationPoint(4.0F, 15.0F, 1.5F);
        this.rightLeg4Upper.addBox(0.0F, -0.5F, -0.5F, 13, 1, 1);
        this.setRotationAngles(this.rightLeg4Upper, 0.0F, -0.7853981633974483F, -0.2617993877991494F);
        this.rightLeg4Lower = new ModelRenderer(this, 50, 0);
        this.rightLeg4Lower.setRotationPoint(13.5F, -0.5F, 0.0F);
        this.rightLeg4Lower.addBox(-0.5F, 0.0F, -0.5F, 1, 13, 1);
        this.rightLeg4Upper.addChild(this.rightLeg4Lower);
        this.leftLeg1Upper = new ModelRenderer(this, 20, 0);
        this.leftLeg1Upper.setRotationPoint(-4.0F, 15.0F, 1.5F);
        this.leftLeg1Upper.addBox(-13.0F, -0.5F, -0.5F, 13, 1, 1);
        this.setRotationAngles(this.leftLeg1Upper, 0.0F, -0.7853981633974483F, 0.2617993877991494F);
        this.leftLeg1Lower = new ModelRenderer(this, 50, 0);
        this.leftLeg1Lower.setRotationPoint(-13.5F, -0.5F, 0.0F);
        this.leftLeg1Lower.addBox(-0.5F, 0.0F, -0.5F, 1, 13, 1);
        this.leftLeg1Upper.addChild(this.leftLeg1Lower);
        this.leftLeg2Upper = new ModelRenderer(this, 20, 0);
        this.leftLeg2Upper.setRotationPoint(-4.0F, 15.0F, 1.5F);
        this.leftLeg2Upper.addBox(-13.0F, -0.5F, -0.5F, 13, 1, 1);
        this.setRotationAngles(this.leftLeg2Upper, 0.0F, -0.2617993877991494F, 0.2617993877991494F);
        this.leftLeg2Lower = new ModelRenderer(this, 50, 0);
        this.leftLeg2Lower.setRotationPoint(-13.5F, -0.5F, 0.0F);
        this.leftLeg2Lower.addBox(-0.5F, 0.0F, -0.5F, 1, 13, 1);
        this.leftLeg2Upper.addChild(this.leftLeg2Lower);
        this.leftLeg3Upper = new ModelRenderer(this, 20, 0);
        this.leftLeg3Upper.setRotationPoint(-4.0F, 15.0F, 1.5F);
        this.leftLeg3Upper.addBox(-13.0F, -0.5F, -0.5F, 13, 1, 1);
        this.setRotationAngles(this.leftLeg3Upper, 0.0F, 0.2617993877991494F, 0.2617993877991494F);
        this.leftLeg3Lower = new ModelRenderer(this, 50, 0);
        this.leftLeg3Lower.setRotationPoint(-13.5F, -0.5F, 0.0F);
        this.leftLeg3Lower.addBox(-0.5F, 0.0F, -0.5F, 1, 13, 1);
        this.leftLeg3Upper.addChild(this.leftLeg3Lower);
        this.leftLeg4Upper = new ModelRenderer(this, 20, 0);
        this.leftLeg4Upper.setRotationPoint(-4.0F, 15.0F, 1.5F);
        this.leftLeg4Upper.addBox(-13.0F, -0.5F, -0.5F, 13, 1, 1);
        this.setRotationAngles(this.leftLeg4Upper, 0.0F, 0.7853981633974483F, 0.2617993877991494F);
        this.leftLeg4Lower = new ModelRenderer(this, 50, 0);
        this.leftLeg4Lower.setRotationPoint(-13.5F, -0.5F, 0.0F);
        this.leftLeg4Lower.addBox(-0.5F, 0.0F, -0.5F, 1, 13, 1);
        this.leftLeg4Upper.addChild(this.leftLeg4Lower);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        this.head.render(scale);
        this.thorax.render(scale);
        this.abdomen.render(scale);
        this.rightLeg1Upper.render(scale);
        this.rightLeg2Upper.render(scale);
        this.rightLeg3Upper.render(scale);
        this.rightLeg4Upper.render(scale);
        this.leftLeg1Upper.render(scale);
        this.leftLeg2Upper.render(scale);
        this.leftLeg3Upper.render(scale);
        this.leftLeg4Upper.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        this.head.rotateAngleX = headPitch * 0.017453292F + 0.1308996938995747F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;

        this.leftLeg1Upper.rotateAngleZ = 0.2617993877991494F;
        this.rightLeg1Upper.rotateAngleZ = -0.2617993877991494F;
        this.leftLeg2Upper.rotateAngleZ = 0.2617993877991494F;
        this.rightLeg2Upper.rotateAngleZ = -0.2617993877991494F;
        this.leftLeg3Upper.rotateAngleZ = 0.2617993877991494F;
        this.rightLeg3Upper.rotateAngleZ = -0.2617993877991494F;
        this.leftLeg4Upper.rotateAngleZ = 0.2617993877991494F;
        this.rightLeg4Upper.rotateAngleZ = -0.2617993877991494F;

        this.leftLeg1Upper.rotateAngleY = -0.7853981633974483F;
        this.rightLeg1Upper.rotateAngleY = 0.7853981633974483F;
        this.leftLeg2Upper.rotateAngleY = -0.2617993877991494F;
        this.rightLeg2Upper.rotateAngleY = 0.2617993877991494F;
        this.leftLeg3Upper.rotateAngleY = 0.2617993877991494F;
        this.rightLeg3Upper.rotateAngleY = -0.2617993877991494F;
        this.leftLeg4Upper.rotateAngleY = 0.7853981633974483F;
        this.rightLeg4Upper.rotateAngleY = -0.7853981633974483F;

        float f3 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
        float f4 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * limbSwingAmount;
        float f5 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float) Math.PI / 2F)) * 0.4F) * limbSwingAmount;
        float f6 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float) Math.PI * 3F / 2F)) * 0.4F) * limbSwingAmount;
        float f7 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
        float f8 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float) Math.PI) * 0.4F) * limbSwingAmount;
        float f9 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float) Math.PI / 2F)) * 0.4F) * limbSwingAmount;
        float f10 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float) Math.PI * 3F / 2F)) * 0.4F) * limbSwingAmount;
        this.leftLeg1Upper.rotateAngleY += f3;
        this.rightLeg1Upper.rotateAngleY += -f3;
        this.leftLeg2Upper.rotateAngleY += f4;
        this.rightLeg2Upper.rotateAngleY += -f4;
        this.leftLeg3Upper.rotateAngleY += f5;
        this.rightLeg3Upper.rotateAngleY += -f5;
        this.leftLeg4Upper.rotateAngleY += f6;
        this.rightLeg4Upper.rotateAngleY += -f6;
        this.leftLeg1Upper.rotateAngleZ += f7;
        this.rightLeg1Upper.rotateAngleZ += -f7;
        this.leftLeg2Upper.rotateAngleZ += f8;
        this.rightLeg2Upper.rotateAngleZ += -f8;
        this.leftLeg3Upper.rotateAngleZ += f9;
        this.rightLeg3Upper.rotateAngleZ += -f9;
        this.leftLeg4Upper.rotateAngleZ += f10;
        this.rightLeg4Upper.rotateAngleZ += -f10;
    }

    private void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

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

package nex.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSkelider extends ModelBase
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

    public ModelSkelider()
    {
        textureHeight = 64;

        head = new ModelRenderer(this, 0, 0);
        head.setRotationPoint(0.0F, 14.0F, 0.0F);
        head.addBox(-2.5F, -2.5F, -5.0F, 5, 5, 5);
        setRotationAngles(head, 0.1308996938995747F, 0.0F, 0.0F);
        thorax = new ModelRenderer(this, 0, 10);
        thorax.setRotationPoint(0.0F, 12.0F, 7.0F);
        thorax.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8);
        setRotationAngles(thorax, 0.19634954084936207F, 0.0F, 0.0F);
        abdomen = new ModelRenderer(this, 0, 26);
        abdomen.setRotationPoint(0.0F, 12.5F, 11.0F);
        abdomen.addBox(-6.0F, -5.0F, -6.5F, 12, 10, 13);
        setRotationAngles(abdomen, -0.2617993877991494F, 0.0F, 0.0F);
        rightLeg1Upper = new ModelRenderer(this, 20, 0);
        rightLeg1Upper.setRotationPoint(4.0F, 15.0F, 1.5F);
        rightLeg1Upper.addBox(0.0F, -0.5F, -0.5F, 13, 1, 1);
        setRotationAngles(rightLeg1Upper, 0.0F, 0.7853981633974483F, -0.2617993877991494F);
        rightLeg1Lower = new ModelRenderer(this, 50, 0);
        rightLeg1Lower.setRotationPoint(13.5F, -0.5F, 0.0F);
        rightLeg1Lower.addBox(-0.5F, 0.0F, -0.5F, 1, 13, 1);
        rightLeg1Upper.addChild(rightLeg1Lower);
        rightLeg2Upper = new ModelRenderer(this, 20, 0);
        rightLeg2Upper.setRotationPoint(4.0F, 15.0F, 1.5F);
        rightLeg2Upper.addBox(0.0F, -0.5F, -0.5F, 13, 1, 1);
        setRotationAngles(rightLeg2Upper, 0.0F, 0.2617993877991494F, -0.2617993877991494F);
        rightLeg2Lower = new ModelRenderer(this, 50, 0);
        rightLeg2Lower.setRotationPoint(13.5F, -0.5F, 0.0F);
        rightLeg2Lower.addBox(-0.5F, 0.0F, -0.5F, 1, 13, 1);
        rightLeg2Upper.addChild(rightLeg2Lower);
        rightLeg3Upper = new ModelRenderer(this, 20, 0);
        rightLeg3Upper.setRotationPoint(4.0F, 15.0F, 1.5F);
        rightLeg3Upper.addBox(0.0F, -0.5F, -0.5F, 13, 1, 1);
        setRotationAngles(rightLeg3Upper, 0.0F, -0.2617993877991494F, -0.2617993877991494F);
        rightLeg3Lower = new ModelRenderer(this, 50, 0);
        rightLeg3Lower.setRotationPoint(13.5F, -0.5F, 0.0F);
        rightLeg3Lower.addBox(-0.5F, 0.0F, -0.5F, 1, 13, 1);
        rightLeg3Upper.addChild(rightLeg3Lower);
        rightLeg4Upper = new ModelRenderer(this, 20, 0);
        rightLeg4Upper.setRotationPoint(4.0F, 15.0F, 1.5F);
        rightLeg4Upper.addBox(0.0F, -0.5F, -0.5F, 13, 1, 1);
        setRotationAngles(rightLeg4Upper, 0.0F, -0.7853981633974483F, -0.2617993877991494F);
        rightLeg4Lower = new ModelRenderer(this, 50, 0);
        rightLeg4Lower.setRotationPoint(13.5F, -0.5F, 0.0F);
        rightLeg4Lower.addBox(-0.5F, 0.0F, -0.5F, 1, 13, 1);
        rightLeg4Upper.addChild(rightLeg4Lower);
        leftLeg1Upper = new ModelRenderer(this, 20, 0);
        leftLeg1Upper.setRotationPoint(-4.0F, 15.0F, 1.5F);
        leftLeg1Upper.addBox(-13.0F, -0.5F, -0.5F, 13, 1, 1);
        setRotationAngles(leftLeg1Upper, 0.0F, -0.7853981633974483F, 0.2617993877991494F);
        leftLeg1Lower = new ModelRenderer(this, 50, 0);
        leftLeg1Lower.setRotationPoint(-13.5F, -0.5F, 0.0F);
        leftLeg1Lower.addBox(-0.5F, 0.0F, -0.5F, 1, 13, 1);
        leftLeg1Upper.addChild(leftLeg1Lower);
        leftLeg2Upper = new ModelRenderer(this, 20, 0);
        leftLeg2Upper.setRotationPoint(-4.0F, 15.0F, 1.5F);
        leftLeg2Upper.addBox(-13.0F, -0.5F, -0.5F, 13, 1, 1);
        setRotationAngles(leftLeg2Upper, 0.0F, -0.2617993877991494F, 0.2617993877991494F);
        leftLeg2Lower = new ModelRenderer(this, 50, 0);
        leftLeg2Lower.setRotationPoint(-13.5F, -0.5F, 0.0F);
        leftLeg2Lower.addBox(-0.5F, 0.0F, -0.5F, 1, 13, 1);
        leftLeg2Upper.addChild(leftLeg2Lower);
        leftLeg3Upper = new ModelRenderer(this, 20, 0);
        leftLeg3Upper.setRotationPoint(-4.0F, 15.0F, 1.5F);
        leftLeg3Upper.addBox(-13.0F, -0.5F, -0.5F, 13, 1, 1);
        setRotationAngles(leftLeg3Upper, 0.0F, 0.2617993877991494F, 0.2617993877991494F);
        leftLeg3Lower = new ModelRenderer(this, 50, 0);
        leftLeg3Lower.setRotationPoint(-13.5F, -0.5F, 0.0F);
        leftLeg3Lower.addBox(-0.5F, 0.0F, -0.5F, 1, 13, 1);
        leftLeg3Upper.addChild(leftLeg3Lower);
        leftLeg4Upper = new ModelRenderer(this, 20, 0);
        leftLeg4Upper.setRotationPoint(-4.0F, 15.0F, 1.5F);
        leftLeg4Upper.addBox(-13.0F, -0.5F, -0.5F, 13, 1, 1);
        setRotationAngles(leftLeg4Upper, 0.0F, 0.7853981633974483F, 0.2617993877991494F);
        leftLeg4Lower = new ModelRenderer(this, 50, 0);
        leftLeg4Lower.setRotationPoint(-13.5F, -0.5F, 0.0F);
        leftLeg4Lower.addBox(-0.5F, 0.0F, -0.5F, 1, 13, 1);
        leftLeg4Upper.addChild(leftLeg4Lower);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        head.render(scale);
        thorax.render(scale);
        abdomen.render(scale);
        rightLeg1Upper.render(scale);
        rightLeg2Upper.render(scale);
        rightLeg3Upper.render(scale);
        rightLeg4Upper.render(scale);
        leftLeg1Upper.render(scale);
        leftLeg2Upper.render(scale);
        leftLeg3Upper.render(scale);
        leftLeg4Upper.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        head.rotateAngleX = headPitch * 0.017453292F + 0.1308996938995747F;
        head.rotateAngleY = netHeadYaw * 0.017453292F;

        leftLeg1Upper.rotateAngleZ = 0.2617993877991494F;
        rightLeg1Upper.rotateAngleZ = -0.2617993877991494F;
        leftLeg2Upper.rotateAngleZ = 0.2617993877991494F;
        rightLeg2Upper.rotateAngleZ = -0.2617993877991494F;
        leftLeg3Upper.rotateAngleZ = 0.2617993877991494F;
        rightLeg3Upper.rotateAngleZ = -0.2617993877991494F;
        leftLeg4Upper.rotateAngleZ = 0.2617993877991494F;
        rightLeg4Upper.rotateAngleZ = -0.2617993877991494F;

        leftLeg1Upper.rotateAngleY = -0.7853981633974483F;
        rightLeg1Upper.rotateAngleY = 0.7853981633974483F;
        leftLeg2Upper.rotateAngleY = -0.2617993877991494F;
        rightLeg2Upper.rotateAngleY = 0.2617993877991494F;
        leftLeg3Upper.rotateAngleY = 0.2617993877991494F;
        rightLeg3Upper.rotateAngleY = -0.2617993877991494F;
        leftLeg4Upper.rotateAngleY = 0.7853981633974483F;
        rightLeg4Upper.rotateAngleY = -0.7853981633974483F;

        float f3 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
        float f4 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * limbSwingAmount;
        float f5 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float) Math.PI / 2F)) * 0.4F) * limbSwingAmount;
        float f6 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + ((float) Math.PI * 3F / 2F)) * 0.4F) * limbSwingAmount;
        float f7 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
        float f8 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float) Math.PI) * 0.4F) * limbSwingAmount;
        float f9 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float) Math.PI / 2F)) * 0.4F) * limbSwingAmount;
        float f10 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + ((float) Math.PI * 3F / 2F)) * 0.4F) * limbSwingAmount;
        leftLeg1Upper.rotateAngleY += f3;
        rightLeg1Upper.rotateAngleY += -f3;
        leftLeg2Upper.rotateAngleY += f4;
        rightLeg2Upper.rotateAngleY += -f4;
        leftLeg3Upper.rotateAngleY += f5;
        rightLeg3Upper.rotateAngleY += -f5;
        leftLeg4Upper.rotateAngleY += f6;
        rightLeg4Upper.rotateAngleY += -f6;
        leftLeg1Upper.rotateAngleZ += f7;
        rightLeg1Upper.rotateAngleZ += -f7;
        leftLeg2Upper.rotateAngleZ += f8;
        rightLeg2Upper.rotateAngleZ += -f8;
        leftLeg3Upper.rotateAngleZ += f9;
        rightLeg3Upper.rotateAngleZ += -f9;
        leftLeg4Upper.rotateAngleZ += f10;
        rightLeg4Upper.rotateAngleZ += -f10;
    }

    private void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

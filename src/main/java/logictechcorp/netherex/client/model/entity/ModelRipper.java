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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelRipper extends ModelBase
{
    private ModelRenderer rightClaw;
    private ModelRenderer leftClaw;
    private ModelRenderer rightArm;
    private ModelRenderer leftArm;
    private ModelRenderer rightShoulder;
    private ModelRenderer leftShoulder;
    private ModelRenderer head;
    private ModelRenderer headWrap;
    private ModelRenderer upperBody;
    private ModelRenderer waist;
    private ModelRenderer lowerBody;

    public ModelRipper()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.head = new ModelRenderer(this, 46, 87);
        this.head.setRotationPoint(0.0F, -2.5F, -7.0F);
        this.head.addBox(-4.5F, -4.0F, -7.0F, 9, 8, 8, 0.0F);
        this.setRotateAngle(this.head, 0.1308996938995747F, 0.0F, 0.0F);
        this.rightArm = new ModelRenderer(this, 0, 36);
        this.rightArm.setRotationPoint(0.0F, 13.0F, 0.0F);
        this.rightArm.addBox(-1.5F, -1.5F, -10.0F, 3, 3, 11, 0.0F);
        this.setRotateAngle(this.rightArm, 0.5235987755982988F, 0.0F, 0.06544984694978735F);
        this.rightClaw = new ModelRenderer(this, 0, 41);
        this.rightClaw.mirror = true;
        this.rightClaw.setRotationPoint(0.0F, -2.0F, -10.0F);
        this.rightClaw.addBox(0.0F, 0.0F, -5.0F, 0, 20, 10, 0.0F);
        this.upperBody = new ModelRenderer(this, 41, 45);
        this.upperBody.setRotationPoint(0.0F, -7.0F, -1.0F);
        this.upperBody.addBox(-5.5F, -1.0F, -6.0F, 11, 8, 12, 0.0F);
        this.leftShoulder = new ModelRenderer(this, 120, 30);
        this.leftShoulder.setRotationPoint(6.0F, -3.0F, -6.0F);
        this.leftShoulder.addBox(-1.0F, -1.0F, -1.0F, 2, 14, 2, 0.0F);
        this.setRotateAngle(this.leftShoulder, -0.2617993877991494F, -0.1308996938995747F, -0.1308996938995747F);
        this.headWrap = new ModelRenderer(this, 45, 66);
        this.headWrap.setRotationPoint(0.0F, -1.5F, -7.0F);
        this.headWrap.addBox(-5.0F, -5.5F, -8.0F, 10, 11, 9, 0.0F);
        this.setRotateAngle(this.headWrap, 0.1308996938995747F, 0.0F, 0.0F);
        this.waist = new ModelRenderer(this, 45, 24);
        this.waist.setRotationPoint(0.0F, -4.5F, 8.0F);
        this.waist.addBox(-4.0F, -1.0F, -6.0F, 8, 8, 12, 0.0F);
        this.setRotateAngle(this.waist, -0.39269908169872414F, 0.0F, 0.0F);
        this.lowerBody = new ModelRenderer(this, 42, 0);
        this.lowerBody.setRotationPoint(0.0F, 0.15F, 10.0F);
        this.lowerBody.addBox(-5.0F, -5.0F, -1.0F, 10, 10, 13, 0.0F);
        this.setRotateAngle(this.lowerBody, -1.0471975511965976F, 0.0F, 0.0F);
        this.leftArm = new ModelRenderer(this, 100, 36);
        this.leftArm.setRotationPoint(0.0F, 13.0F, 0.0F);
        this.leftArm.addBox(-1.5F, -1.5F, -10.0F, 3, 3, 11, 0.0F);
        this.setRotateAngle(this.leftArm, 0.5235987755982988F, 0.0F, -0.06544984694978735F);
        this.leftClaw = new ModelRenderer(this, 108, 41);
        this.leftClaw.setRotationPoint(0.0F, -2.0F, -10.0F);
        this.leftClaw.addBox(0.0F, 0.0F, -5.0F, 0, 20, 10, 0.0F);
        this.rightShoulder = new ModelRenderer(this, 0, 30);
        this.rightShoulder.setRotationPoint(-6.0F, -3.0F, -6.0F);
        this.rightShoulder.addBox(-1.0F, -1.0F, -1.0F, 2, 14, 2, 0.0F);
        this.setRotateAngle(this.rightShoulder, -0.2617993877991494F, 0.1308996938995747F, 0.1308996938995747F);
        this.rightShoulder.addChild(this.rightArm);
        this.rightArm.addChild(this.rightClaw);
        this.leftShoulder.addChild(this.leftArm);
        this.leftArm.addChild(this.leftClaw);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        this.rightShoulder.render(scale);
        this.leftShoulder.render(scale);
        this.head.render(scale);
        this.headWrap.render(scale);
        this.waist.render(scale);
        this.upperBody.render(scale);
        this.lowerBody.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float scale, Entity entity)
    {
        float swingSin = MathHelper.sin(limbSwingAmount * (float) Math.PI);
        float adjustedSwingSin = MathHelper.sin((1.0F - (1.0F - limbSwingAmount) * (1.0F - limbSwingAmount)) * (float) Math.PI);

        this.rightShoulder.rotateAngleX = -0.2617993877991494F;
        this.rightShoulder.rotateAngleX += swingSin * 1.2F - adjustedSwingSin * 0.4F;
        this.rightShoulder.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.rightShoulder.rotateAngleZ = 0.1308996938995747F;
        this.rightShoulder.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;

        this.leftShoulder.rotateAngleX = -0.2617993877991494F;
        this.leftShoulder.rotateAngleX += swingSin * 1.2F - adjustedSwingSin * 0.4F;
        this.leftShoulder.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.leftShoulder.rotateAngleZ = -0.1308996938995747F;
        this.leftShoulder.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;

        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.head.rotateAngleY = headYaw * 0.017453292F;
        this.headWrap.rotateAngleX = headPitch * 0.017453292F;
        this.headWrap.rotateAngleY = headYaw * 0.017453292F;
    }

    private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

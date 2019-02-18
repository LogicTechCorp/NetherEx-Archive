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
public class ModelPigtificateLeader extends ModelBase
{
    private ModelRenderer stomach;
    private ModelRenderer rightArm;
    private ModelRenderer leftArm;
    private ModelRenderer rightLeg;
    private ModelRenderer leftLeg;
    private ModelRenderer torso;
    private ModelRenderer gem;
    private ModelRenderer mouth;
    private ModelRenderer head;
    private ModelRenderer earringsTeeth;
    private ModelRenderer snout;
    private ModelRenderer rightHorn;
    private ModelRenderer leftHorn;
    private ModelRenderer crown;

    public ModelPigtificateLeader()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.torso = new ModelRenderer(this, 0, 72);
        this.torso.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.torso.addBox(-9.5F, -9.0F, 0.0F, 19, 9, 14, 0.0F);
        this.setRotationAngles(this.torso, -0.08726646259971647F, 0.0F, 0.0F);
        this.leftLeg = new ModelRenderer(this, 20, 0);
        this.leftLeg.mirror = true;
        this.leftLeg.setRotationPoint(9.0F, 21.0F, 4.0F);
        this.leftLeg.addBox(-2.5F, 0.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotationAngles(this.leftLeg, -1.3962634015954636F, -0.7853981633974483F, 0.0F);
        this.gem = new ModelRenderer(this, 80, 123);
        this.gem.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.gem.addBox(-2.0F, 8.0F, -11.0F, 4, 3, 2, 0.0F);
        this.mouth = new ModelRenderer(this, 0, 59);
        this.mouth.setRotationPoint(0.0F, -10.5F, 6.5F);
        this.mouth.addBox(-4.5F, 0.0F, -5.5F, 9, 2, 11, 0.0F);
        this.earringsTeeth = new ModelRenderer(this, 0, 25);
        this.earringsTeeth.setRotationPoint(0.0F, 0.0F, 1.0F);
        this.earringsTeeth.addBox(-7.0F, -4.0F, -6.0F, 14, 4, 6, 0.0F);
        this.leftArm = new ModelRenderer(this, 0, 0);
        this.leftArm.mirror = true;
        this.leftArm.setRotationPoint(10.0F, 3.0F, 0.0F);
        this.leftArm.addBox(-2.0F, -2.0F, -2.5F, 5, 14, 5, 0.0F);
        this.setRotationAngles(this.leftArm, 0.0F, 0.0F, -0.2181661564992912F);
        this.crown = new ModelRenderer(this, 0, 35);
        this.crown.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.crown.addBox(-2.5F, -10.0F, -4.0F, 5, 5, 5, 0.0F);
        this.stomach = new ModelRenderer(this, 0, 95);
        this.stomach.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.stomach.addBox(-11.0F, 0.0F, -9.0F, 22, 15, 18, 0.0F);
        this.setRotationAngles(this.stomach, 0.1308996938995747F, 0.0F, 0.0F);
        this.rightLeg = new ModelRenderer(this, 20, 0);
        this.rightLeg.setRotationPoint(-9.0F, 21.0F, 4.0F);
        this.rightLeg.addBox(-2.5F, 0.0F, -2.5F, 5, 12, 5, 0.0F);
        this.setRotationAngles(this.rightLeg, -1.3962634015954636F, 0.7853981633974483F, 0.0F);
        this.head = new ModelRenderer(this, 0, 45);
        this.head.setRotationPoint(0.0F, -1.0F, 1.0F);
        this.head.addBox(-4.0F, -5.0F, -5.0F, 8, 6, 8, 0.0F);
        this.setRotationAngles(this.head, -0.08726646259971647F, 0.0F, 0.0F);
        this.rightArm = new ModelRenderer(this, 0, 0);
        this.rightArm.setRotationPoint(-10.0F, 3.0F, 0.0F);
        this.rightArm.addBox(-3.0F, -2.0F, -2.5F, 5, 14, 5, 0.0F);
        this.setRotationAngles(this.rightArm, 0.0F, 0.0F, 0.2181661564992912F);
        this.snout = new ModelRenderer(this, 24, 49);
        this.snout.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.snout.addBox(-2.0F, -2.0F, -6.0F, 4, 3, 1, 0.0F);
        this.leftHorn = new ModelRenderer(this, 0, 46);
        this.leftHorn.setRotationPoint(3.0F, -2.0F, 0.0F);
        this.leftHorn.addBox(0.0F, -5.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotationAngles(this.leftHorn, 0.0F, 0.0F, 0.4363323129985824F);
        this.rightHorn = new ModelRenderer(this, 0, 46);
        this.rightHorn.setRotationPoint(-3.0F, -2.0F, 0.0F);
        this.rightHorn.addBox(-2.0F, -5.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotationAngles(this.rightHorn, 0.0F, 0.0F, -0.4363323129985824F);
        this.stomach.addChild(this.torso);
        this.stomach.addChild(this.gem);
        this.torso.addChild(this.mouth);
        this.mouth.addChild(this.earringsTeeth);
        this.head.addChild(this.crown);
        this.mouth.addChild(this.head);
        this.head.addChild(this.snout);
        this.head.addChild(this.leftHorn);
        this.head.addChild(this.rightHorn);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        this.stomach.render(scale);
        this.leftLeg.render(scale);
        this.rightLeg.render(scale);
        this.leftArm.render(scale);
        this.rightArm.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netheadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        this.mouth.rotateAngleY = netheadYaw * 0.017453292F;
        this.mouth.rotateAngleX = headPitch * 0.017453292F;

        this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / 1.0F;
        this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / 1.0F;
        this.rightArm.rotateAngleZ = 0.0F;
        this.leftArm.rotateAngleZ = 0.0F;

        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / 1.0F;
        this.leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / 1.0F;
        this.rightLeg.rotateAngleY = 0.0F;
        this.leftLeg.rotateAngleY = 0.0F;
        this.rightLeg.rotateAngleZ = 0.0F;
        this.leftLeg.rotateAngleZ = 0.0F;
    }

    public void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

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

//Fish2's code 
package logictechcorp.netherex.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSalamander extends ModelBase
{
    public ModelRenderer head;
    public ModelRenderer torso;
    public ModelRenderer frontRightLeg;
    public ModelRenderer frontLeftLeg;
    public ModelRenderer backRightLeg;
    public ModelRenderer backLeftLeg;
    public ModelRenderer mouth;
    public ModelRenderer teeth;
    public ModelRenderer waist;
    public ModelRenderer tail;

    public ModelSalamander()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.frontRightLeg = new ModelRenderer(this, 50, 54);
        this.frontRightLeg.setRotationPoint(-5.0F, 19.0F, 4.0F);
        this.frontRightLeg.addBox(-3.0F, -1.0F, -2.0F, 3, 6, 4, 0.0F);
        this.waist = new ModelRenderer(this, 0, 43);
        this.waist.setRotationPoint(0.0F, 0.0F, 10.0F);
        this.waist.addBox(-4.0F, -2.5F, 0.0F, 8, 5, 10, 0.0F);
        this.backLeftLeg = new ModelRenderer(this, 50, 54);
        this.backLeftLeg.mirror = true;
        this.backLeftLeg.setRotationPoint(4.0F, 19.0F, 17.0F);
        this.backLeftLeg.addBox(0.0F, -1.0F, -2.0F, 3, 6, 4, 0.0F);
        this.tail = new ModelRenderer(this, 36, 10);
        this.tail.setRotationPoint(0.0F, 0.0F, 10.0F);
        this.tail.addBox(-2.5F, -1.5F, -1.0F, 5, 3, 9, 0.0F);
        this.setRotateAngle(this.tail, -0.17453292519943295F, 0.0F, 0.0F);
        this.backRightLeg = new ModelRenderer(this, 50, 54);
        this.backRightLeg.setRotationPoint(-4.0F, 19.0F, 17.0F);
        this.backRightLeg.addBox(-3.0F, -1.0F, -2.0F, 3, 6, 4, 0.0F);
        this.teeth = new ModelRenderer(this, 30, 0);
        this.teeth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.teeth.addBox(-3.5F, -1.0F, -9.0F, 7, 1, 9, 0.0F);
        this.mouth = new ModelRenderer(this, 0, 13);
        this.mouth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.mouth.addBox(-3.5F, 0.0F, -9.0F, 7, 3, 9, 0.0F);
        this.setRotateAngle(this.mouth, 0.17453292519943295F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 20.0F, 0.0F);
        this.head.addBox(-3.0F, -4.0F, -8.5F, 6, 4, 9, 0.0F);
        this.setRotateAngle(this.head, -0.061086523819801536F, 0.0F, 0.0F);
        this.torso = new ModelRenderer(this, 0, 25);
        this.torso.setRotationPoint(0.0F, 19.0F, 0.0F);
        this.torso.addBox(-5.0F, -3.0F, 0.0F, 10, 6, 12, 0.0F);
        this.frontLeftLeg = new ModelRenderer(this, 50, 54);
        this.frontLeftLeg.mirror = true;
        this.frontLeftLeg.setRotationPoint(5.0F, 19.0F, 4.0F);
        this.frontLeftLeg.addBox(0.0F, -1.0F, -2.0F, 3, 6, 4, 0.0F);
        this.torso.addChild(this.waist);
        this.waist.addChild(this.tail);
        this.mouth.addChild(this.teeth);
        this.head.addChild(this.mouth);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        this.frontRightLeg.render(scale);
        this.frontLeftLeg.render(scale);
        this.head.render(scale);
        this.torso.render(scale);
        this.backRightLeg.render(scale);
        this.backLeftLeg.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scaleFactor, Entity entity)
    {
        this.frontRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.frontLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        this.backRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        this.backLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

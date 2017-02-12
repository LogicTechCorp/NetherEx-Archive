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
public class ModelNethermite extends ModelBase
{
    private ModelRenderer head;
    private ModelRenderer upperBody;
    private ModelRenderer lowerBody;
    private ModelRenderer tail;
    private ModelRenderer upperBodySpine;
    private ModelRenderer lowerBodySpine;
    private ModelRenderer tailSpineUpper;
    private ModelRenderer tailSpineLower;

    public ModelNethermite()
    {
        head = new ModelRenderer(this, 0, 0);
        head.setRotationPoint(0.0F, 22.0F, -4.0F);
        head.addBox(-1.5F, 0.0F, -0.5F, 3, 2, 1);
        upperBody = new ModelRenderer(this, 0, 3);
        upperBody.setRotationPoint(0.0F, 21.0F, -1.0F);
        upperBody.addBox(-2.0F, 0.0F, -2.5F, 4, 3, 5);
        lowerBody = new ModelRenderer(this, 0, 11);
        lowerBody.setRotationPoint(0.0F, 22.0F, 4.0F);
        lowerBody.addBox(-1.5F, 0.0F, -2.5F, 3, 2, 3);
        tail = new ModelRenderer(this, 0, 16);
        tail.setRotationPoint(0.0F, 23.0F, 5.0F);
        tail.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 2);
        upperBodySpine = new ModelRenderer(this, 18, 5);
        upperBodySpine.setRotationPoint(0.0F, 20.0F, -1.0F);
        upperBodySpine.addBox(0.0F, 0.0F, -2.5F, 0, 1, 5);
        lowerBodySpine = new ModelRenderer(this, 12, 12);
        lowerBodySpine.setRotationPoint(0.0F, 21.0F, 3.0F);
        lowerBodySpine.addBox(0.0F, 0.0F, -1.5F, 0, 1, 3);
        tailSpineUpper = new ModelRenderer(this, 6, 16);
        tailSpineUpper.setRotationPoint(0.0F, 22.0F, 5.0F);
        tailSpineUpper.addBox(0.0F, 0.0F, -1.0F, 0, 1, 2);
        tailSpineLower = new ModelRenderer(this, 6, 19);
        tailSpineLower.setRotationPoint(0.0F, 23.0F, 7.0F);
        tailSpineLower.addBox(0.0F, 0.0F, -1.0F, 0, 1, 2);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        head.render(scale);
        upperBody.render(scale);
        lowerBody.render(scale);
        tail.render(scale);
        upperBodySpine.render(scale);
        lowerBodySpine.render(scale);
        tailSpineUpper.render(scale);
        tailSpineLower.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scaleFactor, Entity entityIn)
    {
        head.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F + (float) 0 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.01F * (float) (1 + Math.abs(0 - 2));
        head.rotationPointX = MathHelper.sin(ageInTicks * 0.9F + (float) 0 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.1F * (float) Math.abs(0 - 2);
        upperBody.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F + (float) 1 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.01F * (float) (1 + Math.abs(1 - 2));
        upperBody.rotationPointX = MathHelper.sin(ageInTicks * 0.9F + (float) 1 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.1F * (float) Math.abs(1 - 2);
        lowerBody.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F + (float) 2 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.01F * (float) (1 + Math.abs(2 - 2));
        lowerBody.rotationPointX = MathHelper.sin(ageInTicks * 0.9F + (float) 2 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.1F * (float) Math.abs(2 - 2);
        tail.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F + (float) 3 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.01F * (float) (1 + Math.abs(3 - 2));
        tail.rotationPointX = MathHelper.sin(ageInTicks * 0.9F + (float) 3 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.1F * (float) Math.abs(3 - 2);
        upperBodySpine.rotateAngleY = upperBody.rotateAngleY;
        upperBodySpine.rotationPointX = upperBody.rotationPointX;
        lowerBodySpine.rotateAngleY = lowerBody.rotateAngleY;
        lowerBodySpine.rotationPointX = lowerBody.rotationPointX;
        tailSpineUpper.rotateAngleY = tail.rotateAngleY;
        tailSpineUpper.rotationPointX = tail.rotationPointX;
        tailSpineLower.rotateAngleY = tail.rotateAngleY;
        tailSpineLower.rotationPointX = tail.rotationPointX;
    }
}

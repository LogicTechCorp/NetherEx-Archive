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
public class ModelMogus extends ModelBase
{
    private ModelRenderer cap;
    private ModelRenderer topCap;
    private ModelRenderer body;
    private ModelRenderer rightLeg;
    private ModelRenderer leftLeg;
    private ModelRenderer rightArm;
    private ModelRenderer leftArm;

    public ModelMogus()
    {
        cap = new ModelRenderer(this, 0, 0);
        cap.addBox(-2F, 0F, -2F, 5, 1, 5);
        cap.setRotationPoint(-1F, 18F, 0F);
        cap.setTextureSize(64, 32);
        cap.mirror = true;
        topCap = new ModelRenderer(this, 20, 0);
        topCap.addBox(-1F, -1F, -1F, 3, 1, 3);
        topCap.setRotationPoint(-1F, 18F, 0F);
        topCap.setTextureSize(64, 32);
        topCap.mirror = true;
        body = new ModelRenderer(this, 0, 6);
        body.addBox(-2F, 1F, -1F, 3, 4, 3);
        body.setRotationPoint(0F, 18F, 0F);
        body.setTextureSize(64, 32);
        body.mirror = true;
        rightLeg = new ModelRenderer(this, 12, 11);
        rightLeg.addBox(-1F, 0F, 0F, 1, 1, 1);
        rightLeg.setRotationPoint(-1F, 23F, 0F);
        rightLeg.setTextureSize(64, 32);
        rightLeg.mirror = true;
        leftLeg = new ModelRenderer(this, 12, 11);
        leftLeg.addBox(0F, 0F, 0F, 1, 1, 1);
        leftLeg.setRotationPoint(0F, 23F, 0F);
        leftLeg.setTextureSize(64, 32);
        leftLeg.mirror = true;
        rightArm = new ModelRenderer(this, 16, 10);
        rightArm.addBox(-1F, 0F, 0F, 1, 2, 1);
        rightArm.setRotationPoint(-2F, 20F, 0F);
        rightArm.setTextureSize(64, 32);
        rightArm.mirror = true;
        leftArm = new ModelRenderer(this, 16, 10);
        leftArm.addBox(0F, 0F, 0F, 1, 2, 1);
        leftArm.setRotationPoint(1F, 20F, 0F);
        leftArm.setTextureSize(64, 32);
        leftArm.mirror = true;
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scaleFactor)
    {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scaleFactor, entity);
        cap.render(scaleFactor);
        topCap.render(scaleFactor);
        body.render(scaleFactor);
        leftLeg.render(scaleFactor);
        rightLeg.render(scaleFactor);
        rightArm.render(scaleFactor);
        leftArm.render(scaleFactor);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scaleFactor, Entity entity)
    {
        rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        leftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }
}

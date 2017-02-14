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
    private ModelRenderer frontBody;
    private ModelRenderer middleBody;
    private ModelRenderer backBody;
    private ModelRenderer tail;
    private ModelRenderer headSpine;
    private ModelRenderer frontBodySpine;
    private ModelRenderer middleBodySpine;
    private ModelRenderer backBodySpine;
    private ModelRenderer tailSpine;

    public ModelNethermite()
    {
        head = new ModelRenderer(this, 0, 0);
        head.setRotationPoint(0.0F, 21.0F, -4.0F);
        head.addBox(-1.5F, 0.0F, -1.0F, 3, 3, 2);
        frontBody = new ModelRenderer(this, 0, 5);
        frontBody.setRotationPoint(0.0F, 20.0F, 0.0F);
        frontBody.addBox(-2.0F, 0.0F, -3.0F, 4, 4, 6);
        middleBody = new ModelRenderer(this, 0, 15);
        middleBody.setRotationPoint(0.0F, 21.0F, 4.0F);
        middleBody.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3);
        backBody = new ModelRenderer(this, 0, 21);
        backBody.setRotationPoint(0.0F, 22.0F, 6.0F);
        backBody.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2);
        tail = new ModelRenderer(this, 0, 25);
        tail.setRotationPoint(0.0F, 23.0F, 8.0F);
        tail.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 2);
        headSpine = new ModelRenderer(this, 10, 2);
        headSpine.setRotationPoint(0.0F, 20.0F, -4.0F);
        headSpine.addBox(0.0F, 0.0F, -1.0F, 0, 1, 2);
        frontBodySpine = new ModelRenderer(this, 20, 5);
        frontBodySpine.setRotationPoint(0.0F, 19.0F, 0.0F);
        frontBodySpine.addBox(0.0F, 0.0F, -3.0F, 0, 1, 6);
        middleBodySpine = new ModelRenderer(this, 12, 17);
        middleBodySpine.setRotationPoint(0.0F, 20.0F, 4.0F);
        middleBodySpine.addBox(0.0F, 0.0F, -1.5F, 0, 1, 3);
        backBodySpine = new ModelRenderer(this, 8, 22);
        backBodySpine.setRotationPoint(0.0F, 21.0F, 6.0F);
        backBodySpine.addBox(0.0F, 0.0F, -1.0F, 0, 1, 2);
        tailSpine = new ModelRenderer(this, 6, 25);
        tailSpine.setRotationPoint(0.0F, 22.0F, 8.0F);
        tailSpine.addBox(0.0F, 0.0F, -1.0F, 0, 1, 2);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        head.render(scale);
        frontBody.render(scale);
        middleBody.render(scale);
        backBody.render(scale);
        tail.render(scale);
        headSpine.render(scale);
        frontBodySpine.render(scale);
        middleBodySpine.render(scale);
        backBodySpine.render(scale);
        tailSpine.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scaleFactor, Entity entityIn)
    {
        head.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F + (float) 0 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.01F * (float) (1 + Math.abs(0 - 2));
        head.rotationPointX = MathHelper.sin(ageInTicks * 0.9F + (float) 0 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.1F * (float) Math.abs(0 - 2);
        frontBody.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F + (float) 1 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.01F * (float) (1 + Math.abs(1 - 2));
        frontBody.rotationPointX = MathHelper.sin(ageInTicks * 0.9F + (float) 1 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.1F * (float) Math.abs(1 - 2);
        middleBody.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F + (float) 2 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.01F * (float) (1 + Math.abs(2 - 2));
        middleBody.rotationPointX = MathHelper.sin(ageInTicks * 0.9F + (float) 2 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.1F * (float) Math.abs(2 - 2);
        backBody.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F + (float) 3 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.01F * (float) (1 + Math.abs(3 - 2));
        backBody.rotationPointX = MathHelper.sin(ageInTicks * 0.9F + (float) 3 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.1F * (float) Math.abs(3 - 2);
        tail.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F + (float) 3 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.01F * (float) (1 + Math.abs(4 - 2));
        tail.rotationPointX = MathHelper.sin(ageInTicks * 0.9F + (float) 3 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.1F * (float) Math.abs(4 - 2);

        headSpine.rotateAngleY = head.rotateAngleY;
        headSpine.rotationPointX = head.rotationPointX;
        frontBodySpine.rotateAngleY = frontBody.rotateAngleY;
        frontBodySpine.rotationPointX = frontBody.rotationPointX;
        middleBodySpine.rotateAngleY = middleBody.rotateAngleY;
        middleBodySpine.rotationPointX = middleBody.rotationPointX;
        backBodySpine.rotateAngleY = backBody.rotateAngleY;
        backBodySpine.rotationPointX = backBody.rotationPointX;
        tailSpine.rotateAngleY = tail.rotateAngleY;
        tailSpine.rotationPointX = tail.rotationPointX;
    }
}

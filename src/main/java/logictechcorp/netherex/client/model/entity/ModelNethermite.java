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
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 21.0F, -4.0F);
        this.head.addBox(-1.5F, 0.0F, -1.0F, 3, 3, 2);
        this.frontBody = new ModelRenderer(this, 0, 5);
        this.frontBody.setRotationPoint(0.0F, 20.0F, 0.0F);
        this.frontBody.addBox(-2.0F, 0.0F, -3.0F, 4, 4, 6);
        this.middleBody = new ModelRenderer(this, 0, 15);
        this.middleBody.setRotationPoint(0.0F, 21.0F, 4.0F);
        this.middleBody.addBox(-1.5F, 0.0F, -1.5F, 3, 3, 3);
        this.backBody = new ModelRenderer(this, 0, 21);
        this.backBody.setRotationPoint(0.0F, 22.0F, 6.0F);
        this.backBody.addBox(-1.0F, 0.0F, -1.0F, 2, 2, 2);
        this.tail = new ModelRenderer(this, 0, 25);
        this.tail.setRotationPoint(0.0F, 23.0F, 8.0F);
        this.tail.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 2);
        this.headSpine = new ModelRenderer(this, 10, 2);
        this.headSpine.setRotationPoint(0.0F, 20.0F, -4.0F);
        this.headSpine.addBox(0.0F, 0.0F, -1.0F, 0, 1, 2);
        this.frontBodySpine = new ModelRenderer(this, 20, 5);
        this.frontBodySpine.setRotationPoint(0.0F, 19.0F, 0.0F);
        this.frontBodySpine.addBox(0.0F, 0.0F, -3.0F, 0, 1, 6);
        this.middleBodySpine = new ModelRenderer(this, 12, 17);
        this.middleBodySpine.setRotationPoint(0.0F, 20.0F, 4.0F);
        this.middleBodySpine.addBox(0.0F, 0.0F, -1.5F, 0, 1, 3);
        this.backBodySpine = new ModelRenderer(this, 8, 22);
        this.backBodySpine.setRotationPoint(0.0F, 21.0F, 6.0F);
        this.backBodySpine.addBox(0.0F, 0.0F, -1.0F, 0, 1, 2);
        this.tailSpine = new ModelRenderer(this, 6, 25);
        this.tailSpine.setRotationPoint(0.0F, 22.0F, 8.0F);
        this.tailSpine.addBox(0.0F, 0.0F, -1.0F, 0, 1, 2);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        this.head.render(scale);
        this.frontBody.render(scale);
        this.middleBody.render(scale);
        this.backBody.render(scale);
        this.tail.render(scale);
        this.headSpine.render(scale);
        this.frontBodySpine.render(scale);
        this.middleBodySpine.render(scale);
        this.backBodySpine.render(scale);
        this.tailSpine.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scaleFactor, Entity entityIn)
    {
        this.head.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F + (float) 0 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.01F * (float) (1 + Math.abs(0 - 2));
        this.head.rotationPointX = MathHelper.sin(ageInTicks * 0.9F + (float) 0 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.1F * (float) Math.abs(0 - 2);
        this.frontBody.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F + (float) 1 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.01F * (float) (1 + Math.abs(1 - 2));
        this.frontBody.rotationPointX = MathHelper.sin(ageInTicks * 0.9F + (float) 1 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.1F * (float) Math.abs(1 - 2);
        this.middleBody.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F + (float) 2 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.01F * (float) (1 + Math.abs(0));
        this.middleBody.rotationPointX = MathHelper.sin(ageInTicks * 0.9F + (float) 2 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.1F * (float) Math.abs(0);
        this.backBody.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F + (float) 3 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.01F * (float) (1 + Math.abs(3 - 2));
        this.backBody.rotationPointX = MathHelper.sin(ageInTicks * 0.9F + (float) 3 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.1F * (float) Math.abs(3 - 2);
        this.tail.rotateAngleY = MathHelper.cos(ageInTicks * 0.9F + (float) 3 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.01F * (float) (1 + Math.abs(4 - 2));
        this.tail.rotationPointX = MathHelper.sin(ageInTicks * 0.9F + (float) 3 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.1F * (float) Math.abs(4 - 2);

        this.headSpine.rotateAngleY = this.head.rotateAngleY;
        this.headSpine.rotationPointX = this.head.rotationPointX;
        this.frontBodySpine.rotateAngleY = this.frontBody.rotateAngleY;
        this.frontBodySpine.rotationPointX = this.frontBody.rotationPointX;
        this.middleBodySpine.rotateAngleY = this.middleBody.rotateAngleY;
        this.middleBodySpine.rotationPointX = this.middleBody.rotationPointX;
        this.backBodySpine.rotateAngleY = this.backBody.rotateAngleY;
        this.backBodySpine.rotationPointX = this.backBody.rotationPointX;
        this.tailSpine.rotateAngleY = this.tail.rotateAngleY;
        this.tailSpine.rotationPointX = this.tail.rotationPointX;
    }
}

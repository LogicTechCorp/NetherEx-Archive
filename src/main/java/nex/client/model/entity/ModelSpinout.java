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
import nex.entity.monster.EntitySpinout;

public class ModelSpinout extends ModelBase
{
    private ModelRenderer head;
    private ModelRenderer upperBody;
    private ModelRenderer middleBody;
    private ModelRenderer lowerBody;
    private ModelRenderer upperSpinner;
    private ModelRenderer middleSpinner;
    private ModelRenderer lowerSpinner;

    public ModelSpinout()
    {
        textureWidth = 128;
        textureHeight = 128;

        head = new ModelRenderer(this, 0, 0);
        head.setRotationPoint(0.0F, -7.0F, 0.0F);
        head.addBox(-4.0F, 0.0F, -4.0F, 8, 8, 8);
        upperBody = new ModelRenderer(this, 0, 16);
        upperBody.setRotationPoint(0.0F, 1.0F, 0.0F);
        upperBody.addBox(-2.5F, 0.0F, -2.5F, 5, 15, 5);
        middleBody = new ModelRenderer(this, 0, 36);
        middleBody.setRotationPoint(0.0F, 16.0F, 0.0F);
        middleBody.addBox(-2.0F, 0.0F, -2.0F, 4, 4, 4);
        lowerBody = new ModelRenderer(this, 0, 44);
        lowerBody.setRotationPoint(0.0F, 20.0F, 0.0F);
        lowerBody.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2);
        upperSpinner = new ModelRenderer(this, 32, 0);
        upperSpinner.setRotationPoint(0.0F, 4.0F, 0.0F);
        upperSpinner.addBox(-12.0F, 0.0F, -12.0F, 24, 0, 24);
        middleSpinner = new ModelRenderer(this, 20, 24);
        middleSpinner.setRotationPoint(0.0F, 11.0F, 0.0F);
        middleSpinner.addBox(-13.5F, 0.0F, -13.5F, 27, 0, 27);
        lowerSpinner = new ModelRenderer(this, 60, 51);
        lowerSpinner.setRotationPoint(0.0F, 18.0F, 0.0F);
        lowerSpinner.addBox(-9.0F, 0.0F, -9.0F, 17, 0, 17);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        head.render(scale);
        upperBody.render(scale);
        middleBody.render(scale);
        lowerBody.render(scale);
        upperSpinner.render(scale);
        middleSpinner.render(scale);
        lowerSpinner.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity)
    {
        EntitySpinout spin = (EntitySpinout) entity;

        if(spin.spinCounter > 0)
        {
            upperSpinner.rotateAngleY = ((float) Math.PI / 4F) + ageInTicks * (float) Math.PI * 0.03F * 7.0F;
            middleSpinner.rotateAngleY = -((((float) Math.PI / 4F) + ageInTicks * (float) Math.PI * 0.03F * 7.0F) + 0.7853981633974483F);
            lowerSpinner.rotateAngleY = ((float) Math.PI / 4F) + ageInTicks * (float) Math.PI * 0.03F * 7.0F;
        }
    }
}

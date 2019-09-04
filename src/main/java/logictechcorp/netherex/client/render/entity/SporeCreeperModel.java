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

package logictechcorp.netherex.client.render.entity;

import logictechcorp.netherex.entity.hostile.SporeCreeperEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SporeCreeperModel extends EntityModel<SporeCreeperEntity>
{
    private RendererModel head;
    private RendererModel body;
    private RendererModel frontRightLeg;
    private RendererModel frontLeftLeg;
    private RendererModel backRightLeg;
    private RendererModel backLeftLeg;

    public SporeCreeperModel()
    {
        this.textureHeight = 64;
        this.head = new RendererModel(this, 0, 0);
        this.head.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8);
        this.body = new RendererModel(this, 0, 16);
        this.body.setRotationPoint(0.0F, 4.0F, 0.0F);
        this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 14, 4);
        this.frontRightLeg = new RendererModel(this, 0, 34);
        this.frontRightLeg.setRotationPoint(2.0F, 18.0F, -4.0F);
        this.frontRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4);
        this.frontLeftLeg = new RendererModel(this, 16, 34);
        this.frontLeftLeg.setRotationPoint(-2.0F, 18.0F, -4.0F);
        this.frontLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4);
        this.backRightLeg = new RendererModel(this, 0, 44);
        this.backRightLeg.setRotationPoint(2.0F, 18.0F, 4.0F);
        this.backRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4);
        this.backLeftLeg = new RendererModel(this, 16, 44);
        this.backLeftLeg.setRotationPoint(-2.0F, 18.0F, 4.0F);
        this.backLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4);
    }

    @Override
    public void render(SporeCreeperEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale);
        this.head.render(scale);
        this.body.render(scale);
        this.frontRightLeg.render(scale);
        this.frontLeftLeg.render(scale);
        this.backRightLeg.render(scale);
        this.backLeftLeg.render(scale);
    }

    @Override
    public void setRotationAngles(SporeCreeperEntity entity,float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
    {
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.frontRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.frontLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.backRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.backLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }
}

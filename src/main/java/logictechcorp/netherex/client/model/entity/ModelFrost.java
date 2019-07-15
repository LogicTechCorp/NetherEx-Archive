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
public class ModelFrost extends ModelBase
{
    private ModelRenderer head;
    private ModelRenderer headSpikes;
    private ModelRenderer lowerRod1;
    private ModelRenderer lowerRod2;
    private ModelRenderer lowerRod3;
    private ModelRenderer lowerRod4;
    private ModelRenderer lowerRod5;
    private ModelRenderer lowerRod6;
    private ModelRenderer lowerRod7;
    private ModelRenderer upperRod1;
    private ModelRenderer upperRod2;
    private ModelRenderer upperRod3;
    private ModelRenderer upperRod4;
    private ModelRenderer upperRod5;
    private ModelRenderer upperRod6;
    private ModelRenderer upperRod7;

    public ModelFrost()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.lowerRod3 = new ModelRenderer(this, 0, 16);
        this.lowerRod3.setRotationPoint(8.7F, 0.3F, 0.0F);
        this.lowerRod3.addBox(0.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(this.lowerRod3, 0.0F, 0.0F, 0.33161255787892263F);
        this.lowerRod2 = new ModelRenderer(this, 0, 16);
        this.lowerRod2.setRotationPoint(-4.77F, 9.59F, 6.76F);
        this.lowerRod2.addBox(0.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(this.lowerRod2, -0.6422811647339132F, -0.40142572795869574F, 0.0F);
        this.lowerRod1 = new ModelRenderer(this, 0, 16);
        this.lowerRod1.setRotationPoint(-6.46F, 9.15F, -8.37F);
        this.lowerRod1.addBox(0.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(this.lowerRod1, 0.7155849933176751F, 0.6632251157578453F, 0.0F);
        this.lowerRod5 = new ModelRenderer(this, 0, 16);
        this.lowerRod5.setRotationPoint(4.77F, 12.31F, -7.1F);
        this.lowerRod5.addBox(0.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(this.lowerRod5, 0.600998114244948F, -0.4886921905584123F, 0.0F);
        this.upperRod4 = new ModelRenderer(this, 0, 16);
        this.upperRod4.setRotationPoint(0.1F, -3.9F, -5.3F);
        this.upperRod4.addBox(0.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(this.upperRod4, 0.8377580409572781F, 0.0F, 0.0F);
        this.lowerRod4 = new ModelRenderer(this, 0, 16);
        this.lowerRod4.setRotationPoint(1.4F, 1.08F, 9.0F);
        this.lowerRod4.addBox(0.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(this.lowerRod4, -0.5061454830783556F, 0.296705972839036F, 0.0F);
        this.upperRod3 = new ModelRenderer(this, 0, 16);
        this.upperRod3.setRotationPoint(-4.9F, -4.5F, 0.1F);
        this.upperRod3.addBox(0.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(this.upperRod3, 0.0F, 0.0F, -0.7504915783575618F);
        this.upperRod7 = new ModelRenderer(this, 0, 16);
        this.upperRod7.setRotationPoint(0.4F, -5.3F, 6.0F);
        this.upperRod7.addBox(0.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(this.upperRod7, -0.8552113334772213F, 0.0F, 0.0F);
        this.lowerRod7 = new ModelRenderer(this, 0, 16);
        this.lowerRod7.setRotationPoint(3.1F, -4.63F, -9.4F);
        this.lowerRod7.addBox(0.3F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(this.lowerRod7, 0.33161255787892263F, -0.3665191429188092F, 0.0F);
        this.lowerRod6 = new ModelRenderer(this, 0, 16);
        this.lowerRod6.setRotationPoint(-9.0F, -1.46F, 0.0F);
        this.lowerRod6.addBox(0.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(this.lowerRod6, 0.0F, 0.0F, -0.33161255787892263F);
        this.upperRod5 = new ModelRenderer(this, 0, 16);
        this.upperRod5.setRotationPoint(-0.1F, -5.3F, 6.0F);
        this.upperRod5.addBox(0.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(this.upperRod5, -0.8552113334772213F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, 0.0F);
        this.upperRod2 = new ModelRenderer(this, 0, 16);
        this.upperRod2.setRotationPoint(0.1F, -3.9F, -5.2F);
        this.upperRod2.addBox(0.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(this.upperRod2, 0.8552113334772213F, 0.0F, 0.0F);
        this.upperRod6 = new ModelRenderer(this, 0, 16);
        this.upperRod6.setRotationPoint(5.8F, -5.2F, 0.1F);
        this.upperRod6.addBox(0.0F, -0.1F, 0.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(this.upperRod6, 0.0F, 0.0F, 0.8552113334772213F);
        this.headSpikes = new ModelRenderer(this, 10, 16);
        this.headSpikes.setRotationPoint(-3.0F, -7.3F, 2.1F);
        this.headSpikes.addBox(0.0F, 1.0F, 0.0F, 6, 5, 6, 0.0F);
        this.setRotateAngle(this.headSpikes, -0.9934414102351724F, 0.0F, 0.0F);
        this.upperRod1 = new ModelRenderer(this, 0, 16);
        this.upperRod1.setRotationPoint(-0.1F, -5.3F, 6.0F);
        this.upperRod1.addBox(0.0F, 0.0F, 0.0F, 2, 8, 2, 0.0F);
        this.setRotateAngle(this.upperRod1, -0.8552113334772213F, 0.0F, 0.0F);
        this.lowerRod4.addChild(this.upperRod4);
        this.lowerRod3.addChild(this.upperRod3);
        this.lowerRod7.addChild(this.upperRod7);
        this.lowerRod5.addChild(this.upperRod5);
        this.lowerRod2.addChild(this.upperRod2);
        this.lowerRod6.addChild(this.upperRod6);
        this.head.addChild(this.headSpikes);
        this.lowerRod1.addChild(this.upperRod1);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        this.lowerRod3.render(scale);
        this.lowerRod2.render(scale);
        this.lowerRod1.render(scale);
        this.lowerRod5.render(scale);
        this.lowerRod4.render(scale);
        this.lowerRod7.render(scale);
        this.lowerRod6.render(scale);
        this.head.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        float angle = ageInTicks * (float) Math.PI * 0.1F;

        this.lowerRod3.rotationPointY = 1.0F + MathHelper.cos((1 * 2F + ageInTicks) * 0.25F);
        this.lowerRod3.rotationPointX = MathHelper.cos(angle) * 9.0F;
        this.lowerRod3.rotationPointZ = MathHelper.sin(angle) * 9.0F;
        angle += 1.5F;

        this.lowerRod4.rotationPointY = 3.0F + MathHelper.cos((1 * 2F + ageInTicks) * 0.25F);
        this.lowerRod4.rotationPointX = MathHelper.cos(angle) * 9.0F;
        this.lowerRod4.rotationPointZ = MathHelper.sin(angle) * 9.0F;
        angle += 1.5F;

        this.lowerRod6.rotationPointY = -2.0F + MathHelper.cos((1 * 2F + ageInTicks) * 0.25F);
        this.lowerRod6.rotationPointX = MathHelper.cos(angle) * 9.0F;
        this.lowerRod6.rotationPointZ = MathHelper.sin(angle) * 9.0F;
        angle += 1.5F;

        this.lowerRod7.rotationPointY = -4.0F + MathHelper.cos((1 * 2F + ageInTicks) * 0.25F);
        this.lowerRod7.rotationPointX = MathHelper.cos(angle) * 9.0F;
        this.lowerRod7.rotationPointZ = MathHelper.sin(angle) * 9.0F;
        angle = 0.47123894F + ageInTicks * (float) Math.PI * -0.05F;

        this.lowerRod1.rotationPointY = 9.0F + MathHelper.cos((1 * 2F + ageInTicks) * 0.25F);
        this.lowerRod1.rotationPointX = MathHelper.cos(angle) * 9.0F;
        this.lowerRod1.rotationPointZ = MathHelper.sin(angle) * 9.0F;
        angle += 2.0F;

        this.lowerRod2.rotationPointY = 11.0F + MathHelper.cos((1 * 2F + ageInTicks) * 0.25F);
        this.lowerRod2.rotationPointX = MathHelper.cos(angle) * 9.0F;
        this.lowerRod2.rotationPointZ = MathHelper.sin(angle) * 9.0F;
        angle += 2.0F;

        this.lowerRod5.rotationPointY = 8.0F + MathHelper.cos((1 * 2F + ageInTicks) * 0.25F);
        this.lowerRod5.rotationPointX = MathHelper.cos(angle) * 9.0F;
        this.lowerRod5.rotationPointZ = MathHelper.sin(angle) * 9.0F;

        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

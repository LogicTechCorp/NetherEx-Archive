package nex.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.entity.passive.EntityBonspider;

@SideOnly(Side.CLIENT)
public class ModelBonspider extends ModelBase
{
    private ModelRenderer head;
    private ModelRenderer neck;
    private ModelRenderer body;
    private ModelRenderer upperLeg1;
    private ModelRenderer upperLeg2;
    private ModelRenderer upperLeg4;
    private ModelRenderer upperLeg3;
    private ModelRenderer saddle;
    private ModelRenderer lowerLeg1;
    private ModelRenderer lowerLeg2;
    private ModelRenderer lowerLeg4;
    private ModelRenderer lowerLeg3;
    private ModelRenderer saddleFront;
    private ModelRenderer saddleBack;

    public ModelBonspider()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.neck = new ModelRenderer(this, 43, 27);
        this.neck.setRotationPoint(0.0F, 17.0F, -1.0F);
        this.neck.addBox(-3.5F, -3.5F, -4.0F, 7, 7, 8, 0.0F);
        this.lowerLeg4 = new ModelRenderer(this, 26, 0);
        this.lowerLeg4.mirror = true;
        this.lowerLeg4.setRotationPoint(-15.0F, -1.0F, 0.0F);
        this.lowerLeg4.addBox(-1.0F, -2.0F, -2.0F, 17, 4, 4, 0.0F);
        this.setRotationAngle(this.lowerLeg4, 0.0F, 0.0F, 1.5707963267948966F);
        this.upperLeg3 = new ModelRenderer(this, 0, 36);
        this.upperLeg3.setRotationPoint(3.0F, 17.0F, -1.0F);
        this.upperLeg3.addBox(0.0F, -1.5F, -1.5F, 16, 3, 3, 0.0F);
        this.setRotationAngle(this.upperLeg3, 0.45814892864851153F, -0.45814892864851153F, -0.5235987755982988F);
        this.upperLeg4 = new ModelRenderer(this, 0, 36);
        this.upperLeg4.setRotationPoint(-3.0F, 17.0F, -1.0F);
        this.upperLeg4.addBox(-16.0F, -1.5F, -1.5F, 16, 3, 3, 0.0F);
        this.setRotationAngle(this.upperLeg4, 0.45814892864851153F, 0.45814892864851153F, 0.5235987755982988F);
        this.saddleBack = new ModelRenderer(this, 10, 53);
        this.saddleBack.setRotationPoint(0.0F, -1.0F, 2.5F);
        this.saddleBack.addBox(-3.5F, 0.0F, -0.5F, 7, 1, 1, 0.0F);
        this.upperLeg1 = new ModelRenderer(this, 0, 36);
        this.upperLeg1.setRotationPoint(3.0F, 17.0F, -3.0F);
        this.upperLeg1.addBox(0.0F, -1.5F, -1.5F, 16, 3, 3, 0.0F);
        this.setRotationAngle(this.upperLeg1, -0.1308996938995747F, 0.2617993877991494F, -0.5235987755982988F);
        this.lowerLeg2 = new ModelRenderer(this, 26, 0);
        this.lowerLeg2.mirror = true;
        this.lowerLeg2.setRotationPoint(-15.0F, -1.0F, 0.0F);
        this.lowerLeg2.addBox(-1.0F, -2.0F, -2.0F, 17, 4, 4, 0.0F);
        this.setRotationAngle(this.lowerLeg2, 0.0F, 0.0F, 1.5707963267948966F);
        this.saddleFront = new ModelRenderer(this, 0, 53);
        this.saddleFront.setRotationPoint(0.0F, -1.0F, -2.5F);
        this.saddleFront.addBox(-1.5F, 0.0F, -0.5F, 3, 1, 1, 0.0F);
        this.body = new ModelRenderer(this, 0, 10);
        this.body.setRotationPoint(0.0F, 17.0F, 2.0F);
        this.body.addBox(-5.0F, -4.5F, 0.0F, 10, 9, 14, 0.0F);
        this.setRotationAngle(this.body, 0.2617993877991494F, 0.0F, 0.0F);
        this.upperLeg2 = new ModelRenderer(this, 0, 36);
        this.upperLeg2.setRotationPoint(-3.0F, 17.0F, -3.0F);
        this.upperLeg2.addBox(-16.0F, -1.5F, -1.5F, 16, 3, 3, 0.0F);
        this.setRotationAngle(this.upperLeg2, -0.1308996938995747F, -0.2617993877991494F, 0.5235987755982988F);
        this.saddle = new ModelRenderer(this, 0, 44);
        this.saddle.setRotationPoint(0.0F, 12.5F, -2.0F);
        this.saddle.addBox(-3.5F, 0.0F, -3.0F, 7, 1, 6, 0.0F);
        this.head = new ModelRenderer(this, 41, 8);
        this.head.setRotationPoint(0.0F, 17.0F, -5.0F);
        this.head.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, 0.0F);
        this.lowerLeg3 = new ModelRenderer(this, 26, 0);
        this.lowerLeg3.mirror = true;
        this.lowerLeg3.setRotationPoint(15.0F, -1.0F, 0.0F);
        this.lowerLeg3.addBox(-1.0F, -2.0F, -2.0F, 17, 4, 4, 0.0F);
        this.setRotationAngle(this.lowerLeg3, 0.0F, 0.0F, 1.5707963267948966F);
        this.lowerLeg1 = new ModelRenderer(this, 26, 0);
        this.lowerLeg1.mirror = true;
        this.lowerLeg1.setRotationPoint(15.0F, -1.0F, 0.0F);
        this.lowerLeg1.addBox(-1.0F, -2.0F, -2.0F, 17, 4, 4, 0.0F);
        this.setRotationAngle(this.lowerLeg1, 0.0F, 0.0F, 1.5707963267948966F);
        this.upperLeg4.addChild(this.lowerLeg4);
        this.saddle.addChild(this.saddleBack);
        this.upperLeg2.addChild(this.lowerLeg2);
        this.saddle.addChild(this.saddleFront);
        this.upperLeg3.addChild(this.lowerLeg3);
        this.upperLeg1.addChild(this.lowerLeg1);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        EntityBonspider bonspider = (EntityBonspider) entity;
        boolean child = bonspider.isChild();

        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        GlStateManager.pushMatrix();
        this.scale(child, 1.2D, 1.2D, 1.2D);
        this.head.render(scale);
        this.upperLeg3.render(scale);
        this.upperLeg2.render(scale);
        this.upperLeg1.render(scale);
        this.neck.render(scale);
        this.body.render(scale);
        this.upperLeg4.render(scale);

        if(!child && bonspider.isHorseSaddled())
        {
            this.saddle.render(scale);
        }

        GlStateManager.popMatrix();
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity)
    {
        this.head.rotateAngleX = headPitch * 0.017453292F + 0.1308996938995747F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;

        this.upperLeg1.rotateAngleZ = -0.6F;
        this.upperLeg2.rotateAngleZ = 0.6F;
        this.upperLeg3.rotateAngleZ = -0.6F;
        this.upperLeg4.rotateAngleZ = 0.6F;

        this.upperLeg1.rotateAngleY = 0.7853981633974483F;
        this.upperLeg2.rotateAngleY = -0.7853981633974483F;
        this.upperLeg3.rotateAngleY = -0.625F;
        this.upperLeg4.rotateAngleY = 0.625F;

        float f3 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbSwingAmount;
        float f4 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + (float) Math.PI) * 0.4F) * limbSwingAmount;
        float f7 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * limbSwingAmount;
        float f8 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + (float) Math.PI) * 0.4F) * limbSwingAmount;
        this.upperLeg2.rotateAngleY += f3;
        this.upperLeg1.rotateAngleY += -f3;
        this.upperLeg3.rotateAngleY += f4;
        this.upperLeg4.rotateAngleY += -f4;
        this.upperLeg2.rotateAngleZ += f7;
        this.upperLeg1.rotateAngleZ += -f7;
        this.upperLeg3.rotateAngleZ += f8;
        this.upperLeg4.rotateAngleZ += -f8;
    }

    private void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    private void scale(boolean child, double x, double y, double z)
    {
        if(child)
        {
            GlStateManager.scale(x / 2, y / 2, z / 2);
            GlStateManager.translate(0.0D, 1.0D, 0.0D);
        }
        else
        {
            GlStateManager.scale(x, y, z);
        }
    }
}

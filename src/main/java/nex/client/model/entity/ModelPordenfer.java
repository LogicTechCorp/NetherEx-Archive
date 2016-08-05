package nex.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelPordenfer extends ModelBase
{
    private ModelRenderer body;
    private ModelRenderer tail;
    private ModelRenderer mouth;
    private ModelRenderer leg1;
    private ModelRenderer leg2;
    private ModelRenderer leg3;
    private ModelRenderer leg4;
    private ModelRenderer leg5;
    private ModelRenderer leg6;

    public ModelPordenfer()
    {
        body = new ModelRenderer(this, 0, 0);
        body.addBox(-4F, -2F, -6F, 8, 4, 12, 1F);
        body.setRotationPoint(0F, 19F, 0F);
        tail = new ModelRenderer(this, 0, 16);
        tail.addBox(-2F, -1F, -0.5F, 4, 2, 6, 1F);
        tail.setRotationPoint(0F, 19F, 5.5F);
        mouth = new ModelRenderer(this, 0, 24);
        mouth.addBox(-2.5F, -1F, -0.5F, 5, 2, 1, 1F);
        mouth.setRotationPoint(0F, 19F, -6F);
        leg1 = new ModelRenderer(this, 10, 27);
        leg1.addBox(-0.5F, -0.5F, -0.5F, 4, 4, 1, 1F);
        leg1.setRotationPoint(4F, 19F, -3F);
        leg2 = new ModelRenderer(this, 10, 27);
        leg2.addBox(-0.5F, -0.5F, -0.5F, 4, 4, 1, 1F);
        leg2.setRotationPoint(4F, 19F, 0F);
        leg3 = new ModelRenderer(this, 10, 27);
        leg3.addBox(-0.5F, -0.5F, -0.5F, 4, 4, 1, 1F);
        leg3.setRotationPoint(4F, 19F, 3F);
        leg4 = new ModelRenderer(this, 0, 27);
        leg4.addBox(-3.5F, -0.5F, -0.5F, 4, 4, 1, 1F);
        leg4.setRotationPoint(-4F, 19F, -3F);
        leg5 = new ModelRenderer(this, 0, 27);
        leg5.addBox(-3.5F, -0.5F, -0.5F, 4, 4, 1, 1F);
        leg5.setRotationPoint(-4F, 19F, 0F);
        leg6 = new ModelRenderer(this, 0, 27);
        leg6.addBox(-3.5F, -0.5F, -0.5F, 4, 4, 1, 1F);
        leg6.setRotationPoint(-4F, 19F, 3F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
        body.render(scale);
        tail.render(scale);
        mouth.render(scale);
        leg1.render(scale);
        leg2.render(scale);
        leg3.render(scale);
        leg4.render(scale);
        leg5.render(scale);
        leg6.render(scale);

    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity)
    {
        tail.rotateAngleX = 0.6108652F;
        leg1.rotateAngleY = 0.2617994F;
        leg2.rotateAngleY = 0.2617994F;
        leg3.rotateAngleY = 0.2617994F;
        leg4.rotateAngleY = -0.2617994F;
        leg5.rotateAngleY = -0.2617994F;
        leg6.rotateAngleY = -0.2617994F;
        leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        leg5.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        leg6.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
    }
}

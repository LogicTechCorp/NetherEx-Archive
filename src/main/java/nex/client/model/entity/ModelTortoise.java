package nex.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelTortoise extends ModelBase
{
    private ModelRenderer neck;
    private ModelRenderer head;
    private ModelRenderer tail;
    private ModelRenderer body;
    private ModelRenderer top;
    private ModelRenderer bottom;
    private ModelRenderer eye1;
    private ModelRenderer eye2;
    private ModelRenderer leg1;
    private ModelRenderer leg2;
    private ModelRenderer leg3;
    private ModelRenderer leg4;

    public ModelTortoise()
    {
        textureWidth = 128;
        textureHeight = 64;

        neck = new ModelRenderer(this, 90, 2);
        neck.addBox(0F, 0F, 0F, 8, 3, 11);
        neck.setRotationPoint(-4F, 15F, -23F);
        neck.setTextureSize(128, 64);
        neck.mirror = true;
        setRotation(neck, 0F, 0F, 0F);
        head = new ModelRenderer(this, 83, 30);
        head.addBox(-5F, -9F, -7F, 11, 11, 11);
        head.setRotationPoint(0F, 17F, -23F);
        head.setTextureSize(128, 64);
        head.mirror = true;
        setRotation(head, 0F, 0F, 0F);
        tail = new ModelRenderer(this, 72, 9);
        tail.addBox(-2F, -2F, 0F, 4, 4, 10);
        tail.setRotationPoint(0F, 13F, 11F);
        tail.setTextureSize(128, 64);
        tail.mirror = true;
        setRotation(tail, 0F, 0F, 0F);
        body = new ModelRenderer(this, 0, 25);
        body.addBox(-1F, 0F, 0F, 28, 12, 27);
        body.setRotationPoint(-13F, 3F, -14F);
        body.setTextureSize(128, 64);
        body.mirror = true;
        setRotation(body, 0F, 0F, 0F);
        top = new ModelRenderer(this, 0, 1);
        top.addBox(0F, 0F, 0F, 24, 3, 24);
        top.setRotationPoint(-12F, 0F, -12F);
        top.setTextureSize(128, 64);
        top.mirror = true;
        setRotation(top, 0F, 0F, 0F);
        bottom = new ModelRenderer(this, 0, 1);
        bottom.addBox(0F, 0F, 0F, 24, 3, 24);
        bottom.setRotationPoint(-12F, 15F, -12F);
        bottom.setTextureSize(128, 64);
        bottom.mirror = true;
        setRotation(bottom, 0F, 0F, 0F);
        eye1 = new ModelRenderer(this, 6, 13);
        eye1.addBox(0F, 0F, 0F, 1, 3, 3);
        eye1.setRotationPoint(6F, 10F, -28F);
        eye1.setTextureSize(128, 64);
        eye1.mirror = true;
        setRotation(eye1, 0F, 0F, 0F);
        eye2 = new ModelRenderer(this, 6, 13);
        eye2.addBox(0F, 0F, 0F, 1, 3, 3);
        eye2.setRotationPoint(-6F, 10F, -28F);
        eye2.setTextureSize(128, 64);
        eye2.mirror = true;
        setRotation(eye2, 0F, 0F, 0F);
        leg1 = new ModelRenderer(this, 1, 35);
        leg1.addBox(-2F, 0F, -2F, 6, 10, 6);
        leg1.setRotationPoint(14F, 14F, 13F);
        leg1.setTextureSize(128, 64);
        leg1.mirror = true;
        setRotation(leg1, 0F, 0F, 0F);
        leg2 = new ModelRenderer(this, 1, 35);
        leg2.addBox(-4F, 0F, -2F, 6, 10, 6);
        leg2.setRotationPoint(-14F, 14F, 13F);
        leg2.setTextureSize(128, 64);
        leg2.mirror = true;
        setRotation(leg2, 0F, 0F, 0F);
        leg3 = new ModelRenderer(this, 1, 35);
        leg3.addBox(-2F, 0F, -4F, 6, 10, 6);
        leg3.setRotationPoint(14F, 14F, -14F);
        leg3.setTextureSize(128, 64);
        leg3.mirror = true;
        setRotation(leg3, 0F, 0F, 0F);
        leg4 = new ModelRenderer(this, 1, 35);
        leg4.addBox(-4F, 0F, -4F, 6, 10, 6);
        leg4.setRotationPoint(-14F, 14F, -14F);
        leg4.setTextureSize(128, 64);
        leg4.mirror = true;
        setRotation(leg4, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
        neck.render(scale);
        head.render(scale);
        tail.render(scale);
        top.render(scale);
        bottom.render(scale);
        body.render(scale);
        eye1.render(scale);
        eye2.render(scale);
        leg1.render(scale);
        leg2.render(scale);
        leg3.render(scale);
        leg4.render(scale);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity)
    {
        leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbSwingAmount;
        leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}

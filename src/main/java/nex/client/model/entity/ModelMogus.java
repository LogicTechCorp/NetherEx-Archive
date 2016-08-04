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
    private ModelRenderer top_cap;
    private ModelRenderer body;
    private ModelRenderer left_leg;
    private ModelRenderer right_leg;
    private ModelRenderer right_arm;
    private ModelRenderer left_arm;

    public ModelMogus()
    {
        textureWidth = 64;
        textureHeight = 32;

        cap = new ModelRenderer(this, 0, 0);
        cap.addBox(-2F, 0F, -2F, 5, 1, 5);
        cap.setRotationPoint(-1F, 18F, 0F);
        cap.setTextureSize(64, 32);
        cap.mirror = true;
        setRotation(cap, 0F, 0F, 0F);
        top_cap = new ModelRenderer(this, 20, 0);
        top_cap.addBox(-1F, -1F, -1F, 3, 1, 3);
        top_cap.setRotationPoint(-1F, 18F, 0F);
        top_cap.setTextureSize(64, 32);
        top_cap.mirror = true;
        setRotation(top_cap, 0F, 0F, 0F);
        body = new ModelRenderer(this, 0, 6);
        body.addBox(-2F, 1F, -1F, 3, 4, 3);
        body.setRotationPoint(0F, 18F, 0F);
        body.setTextureSize(64, 32);
        body.mirror = true;
        setRotation(body, 0F, 0F, 0F);
        left_leg = new ModelRenderer(this, 12, 11);
        left_leg.addBox(0F, 0F, 0F, 1, 1, 1);
        left_leg.setRotationPoint(0F, 23F, 0F);
        left_leg.setTextureSize(64, 32);
        left_leg.mirror = true;
        setRotation(left_leg, 0F, 0F, 0F);
        right_leg = new ModelRenderer(this, 12, 11);
        right_leg.addBox(-1F, 0F, 0F, 1, 1, 1);
        right_leg.setRotationPoint(-1F, 23F, 0F);
        right_leg.setTextureSize(64, 32);
        right_leg.mirror = true;
        setRotation(right_leg, 0F, 0F, 0F);
        right_arm = new ModelRenderer(this, 16, 10);
        right_arm.addBox(-1F, 0F, 0F, 1, 2, 1);
        right_arm.setRotationPoint(-2F, 20F, 0F);
        right_arm.setTextureSize(64, 32);
        right_arm.mirror = true;
        setRotation(right_arm, 0F, 0F, 0F);
        left_arm = new ModelRenderer(this, 16, 10);
        left_arm.addBox(0F, 0F, 0F, 1, 2, 1);
        left_arm.setRotationPoint(1F, 20F, 0F);
        left_arm.setTextureSize(64, 32);
        left_arm.mirror = true;
        setRotation(left_arm, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        cap.render(f5);
        top_cap.render(f5);
        body.render(f5);
        left_leg.render(f5);
        right_leg.render(f5);
        right_arm.render(f5);
        left_arm.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        right_leg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        left_leg.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
        right_arm.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
        left_arm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    }
}

package nex.client.model.entity;


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelWight extends ModelBase
{
    private ModelRenderer head;
    private ModelRenderer hood;
    private ModelRenderer upperBody;
    private ModelRenderer lowerBody;
    private ModelRenderer rightArm;
    private ModelRenderer leftArm;
    private ModelRenderer rightLeg;
    private ModelRenderer leftLeg;

    public ModelWight()
    {
        head = new ModelRenderer(this, 0, 10);
        head.addBox(-2, -2, -2, 4, 4, 4, 0F);
        head.setRotationPoint(0F, 2.5F, -3.5F);
        hood = new ModelRenderer(this, 0, 0);
        hood.addBox(-2.5F, -2.5F, -2.5F, 5, 5, 5, 0F);
        hood.setRotationPoint(0F, 2.5F, -3.5F);
        upperBody = new ModelRenderer(this, 14, 23);
        upperBody.addBox(-4.0F, -2.5F, -2F, 9, 5, 4, 0F);
        upperBody.setRotationPoint(-0.5F, 5.5F, -0.5F);
        upperBody.rotateAngleX = 0.4363323F;
        lowerBody = new ModelRenderer(this, 0, 24);
        lowerBody.addBox(-2.5F, -3.0F, -1F, 5, 6, 2, 0F);
        lowerBody.setRotationPoint(0F, 10.5F, 0F);
        rightArm = new ModelRenderer(this, 24, 0);
        rightArm.addBox(-0.5F, -0.5F, -0.5F, 1, 18, 1, 0F);
        rightArm.setRotationPoint(-3.5F, 5F, 0F);
        leftArm = new ModelRenderer(this, 24, 0);
        leftArm.addBox(-0.5F, -0.5F, -0.5F, 1, 18, 1, 0F);
        leftArm.setRotationPoint(3.5F, 5F, 0F);
        rightLeg = new ModelRenderer(this, 20, 0);
        rightLeg.addBox(-0.5F, -0.5F, -0.5F, 1, 12, 1, 0F);
        rightLeg.setRotationPoint(1, 13, 0);
        leftLeg = new ModelRenderer(this, 20, 0);
        leftLeg.addBox(-0.5F, -0.5F, -0.5F, 1, 12, 1, 0F);
        leftLeg.setRotationPoint(-1, 13, 0);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        rightLeg.render(f5);
        leftLeg.render(f5);
        rightArm.render(f5);
        leftArm.render(f5);
        upperBody.render(f5);
        lowerBody.render(f5);
        head.render(f5);
        hood.render(f5);
    }

    private void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        head.rotateAngleY = f3 / 57.29578F;
        head.rotateAngleX = f4 / 57.29578F;
        hood.rotateAngleY = f3 / 57.29578F;
        hood.rotateAngleX = f4 / 57.29578F;
        rightArm.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 2.0F * f1 * 0.5F;
        leftArm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
        rightArm.rotateAngleZ = 0F;
        leftArm.rotateAngleZ = 0F;
        rightLeg.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        leftLeg.rotateAngleX = MathHelper.cos(f * 0.6662F + 3.141593F) * 1.4F * f1;
        rightLeg.rotateAngleY = 0F;
        leftLeg.rotateAngleY = 0F;
    }
}

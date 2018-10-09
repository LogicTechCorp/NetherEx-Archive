package nex.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.entity.monster.EntityHellhound;

@SideOnly(Side.CLIENT)
public class ModelHellhound extends ModelBase
{
    public ModelRenderer body;
    public ModelRenderer leg1;
    public ModelRenderer leg2;
    public ModelRenderer leg3;
    public ModelRenderer leg4;
    public ModelRenderer head;
    public ModelRenderer tail;
    public ModelRenderer snout;
    public ModelRenderer ear1;
    public ModelRenderer ear2;
    public ModelRenderer headFur;
    public ModelRenderer mane;
    public ModelRenderer maneFur;
    public ModelRenderer tailFur;

    public ModelHellhound()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.leg3 = new ModelRenderer(this, 0, 18);
        this.leg3.setRotationPoint(1.5F, 3.0F, 4.5F);
        this.leg3.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.headFur = new ModelRenderer(this, 0, 0);
        this.headFur.setRotationPoint(0.0F, 5.5F, -2.0F);
        this.headFur.addBox(-2.0F, -2.5F, -2.0F, 4, 5, 4, 0.0F);
        this.maneFur = new ModelRenderer(this, 21, 0);
        this.maneFur.setRotationPoint(0.0F, 0.0F, -2.0F);
        this.maneFur.addBox(-2.5F, -2.0F, -5.0F, 5, 5, 4, 0.0F);
        this.body = new ModelRenderer(this, 18, 14);
        this.body.setRotationPoint(0.0F, 13.0F, 2.0F);
        this.body.addBox(-3.0F, -3.0F, -6.0F, 6, 6, 12, 0.0F);
        this.leg4 = new ModelRenderer(this, 0, 18);
        this.leg4.setRotationPoint(-1.5F, 3.0F, 4.5F);
        this.leg4.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.leg2 = new ModelRenderer(this, 0, 18);
        this.leg2.setRotationPoint(-0.5F, 3.0F, -4.5F);
        this.leg2.addBox(-2.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.snout = new ModelRenderer(this, 0, 11);
        this.snout.setRotationPoint(0.0F, 1.0F, -4.0F);
        this.snout.addBox(-1.5F, -1.5F, -4.0F, 3, 3, 4, 0.0F);
        this.tail = new ModelRenderer(this, 9, 18);
        this.tail.setRotationPoint(0.0F, -0.5F, 5.0F);
        this.tail.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotationAngle(this.tail, 0.5235987755982988F, 0.0F, 0.0F);
        this.tailFur = new ModelRenderer(this, 9, 41);
        this.tailFur.setRotationPoint(0.0F, 4.5F, 0.1F);
        this.tailFur.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotationAngle(this.tailFur, -0.2617993877991494F, 0.0F, 0.0F);
        this.head = new ModelRenderer(this, 0, 30);
        this.head.setRotationPoint(0.0F, 0.0F, -6.0F);
        this.head.addBox(-3.0F, -3.0F, -4.0F, 6, 6, 4, 0.0F);
        this.mane = new ModelRenderer(this, 21, 32);
        this.mane.setRotationPoint(0.0F, -2.0F, -2.0F);
        this.mane.addBox(-3.5F, -3.5F, -4.0F, 7, 7, 8, 0.0F);
        this.setRotationAngle(this.mane, -2.6179938779914944F, 0.0F, 0.0F);
        this.ear2 = new ModelRenderer(this, 16, 14);
        this.ear2.setRotationPoint(-1.5F, -3.0F, -1.5F);
        this.ear2.addBox(-1.0F, -3.0F, -0.5F, 2, 3, 1, 0.0F);
        this.setRotationAngle(this.ear2, 0.0F, 0.0F, -0.21380283336930533F);
        this.leg1 = new ModelRenderer(this, 0, 18);
        this.leg1.setRotationPoint(1.5F, 3.0F, -4.5F);
        this.leg1.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F);
        this.ear1 = new ModelRenderer(this, 16, 14);
        this.ear1.setRotationPoint(1.5F, -3.0F, -1.5F);
        this.ear1.addBox(-1.0F, -3.0F, -0.5F, 2, 3, 1, 0.0F);
        this.setRotationAngle(this.ear1, 0.0F, 0.0F, 0.21380283336930533F);
        this.body.addChild(this.leg3);
        this.head.addChild(this.headFur);
        this.mane.addChild(this.maneFur);
        this.body.addChild(this.leg4);
        this.body.addChild(this.leg2);
        this.head.addChild(this.snout);
        this.body.addChild(this.tail);
        this.tail.addChild(this.tailFur);
        this.body.addChild(this.head);
        this.body.addChild(this.mane);
        this.head.addChild(this.ear2);
        this.body.addChild(this.leg1);
        this.head.addChild(this.ear1);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.3D, 1.3D, 1.3D);
        GlStateManager.translate(0.0D, -0.35D, 0.0D);
        this.body.render(scale);
        GlStateManager.popMatrix();
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        EntityHellhound hellhound = (EntityHellhound) entity;

        if(hellhound.getAttackTarget() != null)
        {
            this.tail.rotateAngleY = 0.0F;
        }
        else
        {
            this.tail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        }

        this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
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
            GlStateManager.translate(0.0D, 1.5D, 0.0D);
        }
        else
        {
            GlStateManager.scale(x, y, z);
        }
    }
}

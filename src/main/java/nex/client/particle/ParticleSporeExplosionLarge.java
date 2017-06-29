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

package nex.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.NetherEx;

@SideOnly(Side.CLIENT)
public class ParticleSporeExplosionLarge extends Particle
{
    private static final ResourceLocation EXPLOSION_TEXTURE = new ResourceLocation(NetherEx.MOD_ID + ":textures/particles/spore_explosion.png");
    private static final VertexFormat VERTEX_FORMAT = (new VertexFormat()).addElement(DefaultVertexFormats.POSITION_3F).addElement(DefaultVertexFormats.TEX_2F).addElement(DefaultVertexFormats.COLOR_4UB).addElement(DefaultVertexFormats.TEX_2S).addElement(DefaultVertexFormats.NORMAL_3B).addElement(DefaultVertexFormats.PADDING_1B);
    private int life;
    private final int lifeTime;
    private final TextureManager theRenderEngine;
    private final float size;

    protected ParticleSporeExplosionLarge(TextureManager renderEngine, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double speed)
    {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
        theRenderEngine = renderEngine;
        lifeTime = 6 + rand.nextInt(4);
        float f = rand.nextFloat() * 0.6F + 0.4F;
        particleRed = f;
        particleGreen = f;
        particleBlue = f;
        size = 1.0F - (float) speed * 0.5F;
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
    {
        int i = (int) (((float) life + partialTicks) * 15.0F / (float) lifeTime);

        if(i <= 15)
        {
            theRenderEngine.bindTexture(EXPLOSION_TEXTURE);
            float f = (float) (i % 4) / 4.0F;
            float f1 = f + 0.24975F;
            float f2 = (float) (i / 4) / 4.0F;
            float f3 = f2 + 0.24975F;
            float f4 = 2.0F * size;
            float f5 = (float) (prevPosX + (posX - prevPosX) * (double) partialTicks - interpPosX);
            float f6 = (float) (prevPosY + (posY - prevPosY) * (double) partialTicks - interpPosY);
            float f7 = (float) (prevPosZ + (posZ - prevPosZ) * (double) partialTicks - interpPosZ);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            RenderHelper.disableStandardItemLighting();
            buffer.begin(7, VERTEX_FORMAT);
            buffer.pos((double) (f5 - rotationX * f4 - rotationXY * f4), (double) (f6 - rotationZ * f4), (double) (f7 - rotationYZ * f4 - rotationXZ * f4)).tex((double) f1, (double) f3).color(particleRed, particleGreen, particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
            buffer.pos((double) (f5 - rotationX * f4 + rotationXY * f4), (double) (f6 + rotationZ * f4), (double) (f7 - rotationYZ * f4 + rotationXZ * f4)).tex((double) f1, (double) f2).color(particleRed, particleGreen, particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
            buffer.pos((double) (f5 + rotationX * f4 + rotationXY * f4), (double) (f6 + rotationZ * f4), (double) (f7 + rotationYZ * f4 + rotationXZ * f4)).tex((double) f, (double) f2).color(particleRed, particleGreen, particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
            buffer.pos((double) (f5 + rotationX * f4 - rotationXY * f4), (double) (f6 - rotationZ * f4), (double) (f7 + rotationYZ * f4 - rotationXZ * f4)).tex((double) f, (double) f3).color(particleRed, particleGreen, particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
            Tessellator.getInstance().draw();
            GlStateManager.enableLighting();
        }
    }

    @Override
    public int getBrightnessForRender(float p_189214_1_)
    {
        return 61680;
    }

    @Override
    public void onUpdate()
    {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        life++;

        if(life == lifeTime)
        {
            setExpired();
        }
    }

    @Override
    public int getFXLayer()
    {
        return 3;
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory
    {
        public Particle createParticle(int particleID, World world, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_)
        {
            return new ParticleSporeExplosionLarge(Minecraft.getMinecraft().getTextureManager(), world, xCoordIn, yCoordIn, zCoordIn, xSpeedIn);
        }
    }
}

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

package logictechcorp.netherex.client.render.entity.layers;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.client.render.entity.CoolmarSpiderModel;
import logictechcorp.netherex.client.render.entity.CoolmarSpiderRenderer;
import logictechcorp.netherex.entity.hostile.CoolmarSpiderEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CoolmarSpiderLayer extends LayerRenderer<CoolmarSpiderEntity, CoolmarSpiderModel>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(NetherEx.MOD_ID, "textures/entity/coolmar_spider/coolmar_spider_layer.png");

    public CoolmarSpiderLayer(CoolmarSpiderRenderer renderer)
    {
        super(renderer);
    }

    @Override
    public void render(CoolmarSpiderEntity coolmarSpider, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.bindTexture(TEXTURE);
        GlStateManager.enableBlend();
        GlStateManager.disableAlphaTest();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

        GlStateManager.depthMask(false);

        int i = 61680;
        int j = i % 65536;
        int k = i / 65536;
        GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, (float) j, (float) k);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GameRenderer gamerenderer = Minecraft.getInstance().gameRenderer;
        gamerenderer.setupFogColor(true);
        this.getEntityModel().render(coolmarSpider, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        gamerenderer.setupFogColor(false);
        i = coolmarSpider.getBrightnessForRender();
        j = i % 65536;
        k = i / 65536;
        GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, (float) j, (float) k);
        this.func_215334_a(coolmarSpider);
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableAlphaTest();
    }

    @Override
    public boolean shouldCombineTextures()
    {
        return false;
    }
}
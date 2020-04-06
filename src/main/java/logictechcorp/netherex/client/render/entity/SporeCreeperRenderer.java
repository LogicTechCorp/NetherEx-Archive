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

import com.mojang.blaze3d.matrix.MatrixStack;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.hostile.SporeCreeperEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SporeCreeperRenderer extends MobRenderer<SporeCreeperEntity, SporeCreeperModel>
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(NetherEx.MOD_ID, "textures/entity/spore_creeper.png");

    public SporeCreeperRenderer(EntityRendererManager manager)
    {
        super(manager, new SporeCreeperModel(), 0.3F);
    }

    @Override
    protected void preRenderCallback(SporeCreeperEntity sporeCreeper, MatrixStack matrixStack, float partialTickTime)
    {
        float flashIntensity = sporeCreeper.getFlashIntensity(partialTickTime);
        float adjustedIntensity = 1.0F + MathHelper.sin(flashIntensity * 100.0F) * flashIntensity * 0.01F;
        flashIntensity = MathHelper.clamp(flashIntensity, 0.0F, 1.0F);
        flashIntensity = flashIntensity * flashIntensity;
        flashIntensity = flashIntensity * flashIntensity;
        float xzScale = (1.0F + flashIntensity * 0.4F) * adjustedIntensity;
        float yScale = (1.0F + flashIntensity * 0.1F) / adjustedIntensity;
        matrixStack.scale(xzScale, yScale, xzScale);
    }

    @Override
    protected float getOverlayProgress(SporeCreeperEntity sporeCreeper, float partialTicks)
    {
        float flashIntensity = sporeCreeper.getFlashIntensity(partialTicks);
        return (int) (flashIntensity * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(flashIntensity, 0.5F, 1.0F);
    }

    @Override
    public ResourceLocation getEntityTexture(SporeCreeperEntity spore)
    {
        return TEXTURE;
    }
}

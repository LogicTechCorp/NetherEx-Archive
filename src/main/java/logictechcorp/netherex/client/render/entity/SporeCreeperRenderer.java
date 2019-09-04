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

import com.mojang.blaze3d.platform.GlStateManager;
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
    protected void preRenderCallback(SporeCreeperEntity creeper, float partialTickTime)
    {
        float flashIntensity = creeper.getFlashIntensity(partialTickTime);
        float f1 = 1.0F + MathHelper.sin(flashIntensity * 100.0F) * flashIntensity * 0.01F;
        flashIntensity = MathHelper.clamp(flashIntensity, 0.0F, 1.0F);
        flashIntensity = flashIntensity * flashIntensity;
        flashIntensity = flashIntensity * flashIntensity;
        float f2 = (1.0F + flashIntensity * 0.4F) * f1;
        float f3 = (1.0F + flashIntensity * 0.1F) / f1;
        GlStateManager.scalef(f2, f3, f2);
    }

    @Override
    protected int getColorMultiplier(SporeCreeperEntity creeper, float lightBrightness, float partialTickTime)
    {
        float flashIntensity = creeper.getFlashIntensity(partialTickTime);

        if((int) (flashIntensity * 10.0F) % 2 == 0)
        {
            return 0;
        }
        else
        {
            int i = (int) (flashIntensity * 0.2F * 255.0F);
            i = MathHelper.clamp(i, 0, 255);
            return i << 24 | 822083583;
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(SporeCreeperEntity spore)
    {
        return TEXTURE;
    }
}

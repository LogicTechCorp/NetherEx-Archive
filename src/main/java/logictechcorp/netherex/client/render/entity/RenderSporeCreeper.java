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

import logictechcorp.netherex.client.model.entity.ModelSporeCreeper;
import logictechcorp.netherex.entity.monster.EntitySporeCreeper;
import logictechcorp.netherex.init.NetherExTextures;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSporeCreeper extends RenderLiving<EntitySporeCreeper>
{
    public RenderSporeCreeper(RenderManager manager)
    {
        super(manager, new ModelSporeCreeper(), 0.3F);
    }

    @Override
    protected void preRenderCallback(EntitySporeCreeper creeper, float partialTickTime)
    {
        float f = creeper.getFlashIntensity(partialTickTime);
        float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        GlStateManager.scale(f2, f3, f2);
    }

    @Override
    protected int getColorMultiplier(EntitySporeCreeper creeper, float lightBrightness, float partialTickTime)
    {
        float f = creeper.getFlashIntensity(partialTickTime);

        if((int) (f * 10.0F) % 2 == 0)
        {
            return 0;
        }
        else
        {
            int i = (int) (f * 0.2F * 255.0F);
            i = MathHelper.clamp(i, 0, 255);
            return i << 24 | 822083583;
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySporeCreeper creeper)
    {
        return NetherExTextures.SPORE_CREEPER;
    }
}

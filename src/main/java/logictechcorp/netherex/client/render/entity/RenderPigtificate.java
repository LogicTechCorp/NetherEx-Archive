/*
 * NetherEx
 * Copyright (c) 2016-2018 by MineEx
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

import logictechcorp.netherex.client.model.entity.ModelPigtificate;
import logictechcorp.netherex.entity.passive.EntityPigtificate;
import logictechcorp.netherex.village.PigtificateProfession;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPigtificate extends RenderBiped<EntityPigtificate>
{
    public RenderPigtificate(RenderManager manager)
    {
        super(manager, new ModelPigtificate(), 0.5F);
    }

    @Override
    protected void preRenderCallback(EntityPigtificate pigtificate, float partialTickTime)
    {
        float f = 0.9375F;

        if(pigtificate.getGrowingAge() < 0)
        {
            f = (float) ((double) f * 0.5D);
            this.shadowSize = 0.25F;
        }
        else
        {
            this.shadowSize = 0.5F;
        }

        GlStateManager.scale(f, f, f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityPigtificate pigtificate)
    {
        PigtificateProfession.Career career = pigtificate.getCareer();
        return pigtificate.useAlternateTexture() ? career.getAlternateTexture() : career.getTexture();
    }
}

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

package nex.client.render.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.client.model.entity.ModelPigtificateLeader;
import nex.entity.passive.EntityPigtificate;
import nex.entity.passive.EntityPigtificateLeader;
import nex.village.trade.TradeCareer;
import nex.village.trade.TradeProfession;

@SideOnly(Side.CLIENT)
public class RenderPigtificateLeader extends RenderLiving<EntityPigtificateLeader>
{
    public RenderPigtificateLeader(RenderManager manager)
    {
        super(manager, new ModelPigtificateLeader(), 0.5F);
    }

    protected void preRenderCallback(EntityPigtificate pigtificate, float partialTickTime)
    {
        float f = 0.9375F;

        if(pigtificate.getGrowingAge() < 0)
        {
            f = (float) ((double) f * 0.5D);
            shadowSize = 0.25F;
        }
        else
        {
            shadowSize = 0.5F;
        }

        GlStateManager.scale(f, f, f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityPigtificateLeader pigtificate)
    {
        return TradeCareer.EnumType.fromIndex(pigtificate.getCareer()).getTexture();
    }
}

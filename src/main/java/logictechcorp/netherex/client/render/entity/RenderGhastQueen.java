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

import logictechcorp.netherex.client.model.entity.ModelGhastQueen;
import logictechcorp.netherex.entity.boss.EntityGhastQueen;
import logictechcorp.netherex.init.NetherExTextures;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGhastQueen extends RenderLiving<EntityGhastQueen>
{
    public RenderGhastQueen(RenderManager manager)
    {
        super(manager, new ModelGhastQueen(), 0.3F);
    }

    @Override
    protected void preRenderCallback(EntityGhastQueen ghastQueen, float partialTickTime)
    {
        GlStateManager.scale(7.25F, 7.25F, 7.25F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityGhastQueen ghastQueen)
    {
        return ghastQueen.isAttacking() ? NetherExTextures.GHAST_QUEEN_SHOOTING : NetherExTextures.GHAST_QUEEN;
    }
}

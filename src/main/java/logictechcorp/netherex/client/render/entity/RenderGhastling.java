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

import logictechcorp.netherex.entity.monster.EntityGhastling;
import logictechcorp.netherex.init.NetherExTextures;
import net.minecraft.client.model.ModelGhast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderGhastling extends RenderLiving<EntityGhastling>
{
    public RenderGhastling(RenderManager manager)
    {
        super(manager, new ModelGhast(), 0.3F);
    }

    @Override
    protected void preRenderCallback(EntityGhastling ghastling, float partialTickTime)
    {
        GlStateManager.scale(2.25F, 2.25F, 2.25F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityGhastling ghastling)
    {
        return ghastling.isAttacking() ? NetherExTextures.GHASTLING_SHOOTING : NetherExTextures.GHASTLING;
    }
}

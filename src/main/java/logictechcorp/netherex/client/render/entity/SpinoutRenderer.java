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

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.entity.hostile.SpinoutEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpinoutRenderer extends MobRenderer<SpinoutEntity, SpinoutModel>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(NetherEx.MOD_ID, "textures/entity/spinout.png");

    public SpinoutRenderer(EntityRendererManager manager)
    {
        super(manager, new SpinoutModel(), 0.3F);
    }

    @Override
    protected ResourceLocation getEntityTexture(SpinoutEntity entity)
    {
        return TEXTURE;
    }
}

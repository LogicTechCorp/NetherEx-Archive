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

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.client.render.entity.CoolmarSpiderModel;
import logictechcorp.netherex.client.render.entity.CoolmarSpiderRenderer;
import logictechcorp.netherex.entity.hostile.CoolmarSpiderEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CoolmarSpiderLayer extends AbstractEyesLayer<CoolmarSpiderEntity, CoolmarSpiderModel>
{
    private static final RenderType RENDER_TYPE = RenderType.getEyes(new ResourceLocation(NetherEx.MOD_ID, "textures/entity/coolmar_spider/coolmar_spider_layer.png"));

    public CoolmarSpiderLayer(CoolmarSpiderRenderer renderer)
    {
        super(renderer);
    }

    @Override
    public RenderType getRenderType()
    {
        return RENDER_TYPE;
    }
}
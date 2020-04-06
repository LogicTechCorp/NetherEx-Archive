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
import logictechcorp.netherex.client.render.entity.layers.CoolmarSpiderLayer;
import logictechcorp.netherex.entity.hostile.CoolmarSpiderEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CoolmarSpiderRenderer extends MobRenderer<CoolmarSpiderEntity, CoolmarSpiderModel>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(NetherEx.MOD_ID, "textures/entity/coolmar_spider/coolmar_spider.png");

    public CoolmarSpiderRenderer(EntityRendererManager manager)
    {
        super(manager, new CoolmarSpiderModel(), 0.5F);
        this.addLayer(new CoolmarSpiderLayer(this));
    }

    @Override
    protected float getDeathMaxRotation(CoolmarSpiderEntity coolmarSpider)
    {
        return 180.0F;
    }

    @Override
    public ResourceLocation getEntityTexture(CoolmarSpiderEntity coolmarSpider)
    {
        return TEXTURE;
    }
}
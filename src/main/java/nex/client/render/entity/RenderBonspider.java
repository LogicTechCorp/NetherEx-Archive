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

package nex.client.render.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.client.model.entity.ModelBonspider;
import nex.client.render.entity.layers.LayerBonspider;
import nex.entity.passive.EntityBonspider;
import nex.init.NetherExTextures;

@SideOnly(Side.CLIENT)
public class RenderBonspider extends RenderLiving<EntityBonspider>
{
    public RenderBonspider(RenderManager manager)
    {
        super(manager, new ModelBonspider(), 0.5F);
        this.addLayer(new LayerBonspider(this));
    }

    @Override
    protected float getDeathMaxRotation(EntityBonspider boneSpider)
    {
        return 180.0F;
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBonspider bonespider)
    {
        return NetherExTextures.BONSPIDER;
    }
}

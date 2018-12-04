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

import logictechcorp.netherex.client.model.entity.ModelMogus;
import logictechcorp.netherex.entity.neutral.EntityMogus;
import logictechcorp.netherex.init.NetherExTextures;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMogus extends RenderLiving<EntityMogus>
{
    public RenderMogus(RenderManager manager)
    {
        super(manager, new ModelMogus(), 0.3F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMogus mogus)
    {
        return mogus.getType() == 0 ? NetherExTextures.MOGUS_BROWN : mogus.getType() == 1 ? NetherExTextures.MOGUS_RED : NetherExTextures.MOGUS_WHITE;
    }
}

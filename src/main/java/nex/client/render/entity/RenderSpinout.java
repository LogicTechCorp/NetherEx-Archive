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

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nex.NetherEx;
import nex.client.model.entity.ModelSpinout;
import nex.entity.monster.EntitySpinout;

@SideOnly(Side.CLIENT)
public class RenderSpinout extends RenderLiving<EntitySpinout>
{
    private static final ResourceLocation SPINOUT_TEXTURE = new ResourceLocation(NetherEx.MOD_ID, "textures/entity/spinout.png");

    public RenderSpinout(RenderManager manager)
    {
        super(manager, new ModelSpinout(), 0.3F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntitySpinout spinout)
    {
        return SPINOUT_TEXTURE;
    }
}

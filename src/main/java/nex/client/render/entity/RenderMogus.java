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
import nex.client.model.entity.ModelMogus;
import nex.entity.neutral.EntityMogus;

@SideOnly(Side.CLIENT)
public class RenderMogus extends RenderLiving<EntityMogus>
{
    private static final ResourceLocation BROWN_MOGUS_TEXTURE = new ResourceLocation(NetherEx.MOD_ID + ":textures/entity/mogus/mogus_brown.png");
    private static final ResourceLocation RED_MOGUS_TEXTURE = new ResourceLocation(NetherEx.MOD_ID + ":textures/entity/mogus/mogus_red.png");
    private static final ResourceLocation WHITE_MOGUS_TEXTURE = new ResourceLocation(NetherEx.MOD_ID + ":textures/entity/mogus/mogus_white.png");

    public RenderMogus(RenderManager manager)
    {
        super(manager, new ModelMogus(), 0.3F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMogus mogus)
    {
        return mogus.getType() == 0 ? BROWN_MOGUS_TEXTURE : mogus.getType() == 1 ? RED_MOGUS_TEXTURE : WHITE_MOGUS_TEXTURE;
    }
}

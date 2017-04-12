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
import nex.NetherEx;
import nex.client.model.entity.ModelPigtificate;
import nex.entity.passive.EntityPigtificate;

@SideOnly(Side.CLIENT)
public class RenderPigtificate extends RenderLiving<EntityPigtificate>
{
    private static final ResourceLocation PIGTIFICATE_FARMER_TEXTURE = new ResourceLocation(NetherEx.MOD_ID + ":textures/entities/pigtificate/pigtificate_farmer.png");
    private static final ResourceLocation PIGTIFICATE_BUTCHER_TEXTURE = new ResourceLocation(NetherEx.MOD_ID + ":textures/entities/pigtificate/pigtificate_butcher.png");
    private static final ResourceLocation PIGTIFICATE_BLACKSMITH_TEXTURE = new ResourceLocation(NetherEx.MOD_ID + ":textures/entities/pigtificate/pigtificate_blacksmith.png");

    public RenderPigtificate(RenderManager manager)
    {
        super(manager, new ModelPigtificate(), 0.5F);
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
    protected ResourceLocation getEntityTexture(EntityPigtificate pigtificate)
    {
        return pigtificate.getProfession() == 0 ? PIGTIFICATE_FARMER_TEXTURE : pigtificate.getProfession() == 1 ? PIGTIFICATE_BUTCHER_TEXTURE : PIGTIFICATE_BLACKSMITH_TEXTURE;
    }
}

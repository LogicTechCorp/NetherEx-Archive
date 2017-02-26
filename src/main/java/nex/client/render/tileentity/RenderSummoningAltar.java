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

package nex.client.render.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nex.tileentity.TileEntityUrnOfSorrow;

public class RenderSummoningAltar extends TileEntitySpecialRenderer<TileEntityUrnOfSorrow>
{
    @Override
    public void renderTileEntityAt(TileEntityUrnOfSorrow altar, double x, double y, double z, float partialTicks, int destroyStage)
    {
        if(altar == null)
        {
            return;
        }

        World world = altar.getWorld();
        ItemStack stack = altar.getInventory().getStackInSlot(0);

        if(!stack.isEmpty())
        {
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5F, y + 1.225F, z + 0.5F);
            GlStateManager.disableLighting();

            GlStateManager.rotate(((world.getTotalWorldTime() + partialTicks) / 15.0F) * (180.0F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.pushAttrib();

            RenderHelper.enableStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
            RenderHelper.disableStandardItemLighting();

            GlStateManager.popAttrib();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }
    }
}

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
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.model.animation.FastTESR;
import nex.tileentity.TileEntitySummoningAltar;

import java.util.List;

public class RenderSummoningAltar extends FastTESR<TileEntitySummoningAltar>
{
    @Override
    public void renderTileEntityFast(TileEntitySummoningAltar altar, double x, double y, double z, float partialTicks, int destroyStage, VertexBuffer vertexRenderer)
    {
        ItemStack stack = altar.getInventory().getStackInSlot(0);

        if(!stack.isEmpty())
        {
            World world = altar.getWorld();
            float time = (world.getTotalWorldTime() + partialTicks) / 20;

            IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, world, null);
            List<BakedQuad> quads;

            for(EnumFacing facing : EnumFacing.values())
            {
                quads = model.getQuads(null, facing, 0L);

                for(BakedQuad quad : quads)
                {
                    vertexRenderer.addVertexData(quad.getVertexData());
                    vertexRenderer.putPosition(x, y + 1.5D, z);
                }
            }

            quads = model.getQuads(null, null, 0L);

            for(BakedQuad quad : quads)
            {
                vertexRenderer.addVertexData(quad.getVertexData());
                vertexRenderer.putPosition(x, y + 1.5D, z);
            }
        }
    }
}

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

package logictechcorp.netherex.handler;

import logictechcorp.libraryex.client.render.block.model.BakedModelDynamic;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExBlocks;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, value = Side.CLIENT)
public class ModelHandler
{
    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event)
    {
        IBakedModel model = event.getModelRegistry().getObject(NetherExBlocks.QUARTZ_ORE.getModelLocation());

        if(model != null)
        {
            event.getModelRegistry().putObject(NetherExBlocks.QUARTZ_ORE.getModelLocation(), new BakedModelDynamic(model, NetherExBlocks.QUARTZ_ORE.getTexturePlacement()));
        }
    }
}

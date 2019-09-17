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

import logictechcorp.libraryex.block.MimicBlock;
import logictechcorp.libraryex.client.render.block.model.MimicBakedModel;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.block.NetherExBlocks;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MimicModelHandler
{
    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event)
    {
        MimicBlock mimicBlock = (MimicBlock) NetherExBlocks.QUARTZ_ORE;
        ModelResourceLocation modelLocation = mimicBlock.getModelLocation();
        IBakedModel model = event.getModelRegistry().get(modelLocation);

        if(model != null)
        {
            event.getModelRegistry().put(modelLocation, new MimicBakedModel(model, mimicBlock.getMimicType()));
        }
    }
}
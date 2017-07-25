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

package nex.handler;

import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nex.NetherEx;
import nex.util.WorldGenUtil;
import nex.world.gen.GenerationStage;


@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class WorldGenHandler
{
    @SubscribeEvent
    public static void onPrePopulateChunk(PopulateChunkEvent.Pre event)
    {
        WorldGenUtil.generateFeature(event.getWorld(), event.getChunkX(), event.getChunkZ(), event.getRand(), GenerationStage.PRE_POPULATE);
    }

    @SubscribeEvent
    public static void onPostPopulateChunk(PopulateChunkEvent.Post event)
    {
        WorldGenUtil.generateFeature(event.getWorld(), event.getChunkX(), event.getChunkZ(), event.getRand(), GenerationStage.POST_POPULATE);
    }

    @SubscribeEvent
    public static void onPreBiomeDecorate(DecorateBiomeEvent.Pre event)
    {
        WorldGenUtil.generateFeature(event.getWorld(), event.getPos(), event.getRand(), GenerationStage.PRE_DECORATE);
    }

    @SubscribeEvent
    public static void onPreGenerateOres(OreGenEvent.Pre event)
    {
        WorldGenUtil.generateFeature(event.getWorld(), event.getPos(), event.getRand(), GenerationStage.PRE_ORE);
    }

    @SubscribeEvent
    public static void onPostGenerateOres(OreGenEvent.Post event)
    {
        WorldGenUtil.generateFeature(event.getWorld(), event.getPos(), event.getRand(), GenerationStage.POST_ORE);
    }

    @SubscribeEvent
    public static void onPostBiomeDecorate(DecorateBiomeEvent.Post event)
    {
        WorldGenUtil.generateFeature(event.getWorld(), event.getPos(), event.getRand(), GenerationStage.POST_DECORATE);
    }
}

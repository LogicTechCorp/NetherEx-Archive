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

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.api.NetherExAPI;
import logictechcorp.netherex.village.PigtificateVillageData;
import logictechcorp.netherex.village.PigtificateVillageManager;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class WorldHandler
{
    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event)
    {
        World world = event.getWorld();

        if(!world.isRemote)
        {
            if(world.getWorldType() == NetherEx.WORLD_TYPE)
            {
                NetherExAPI.getInstance().getBiomeDataManager().readBiomeDataConfigs(event);
                NetherExAPI.getInstance().getTradeManager().readTradeConfigs(event);
                PigtificateVillageManager.loadVillageData(world);
            }
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        World world = event.world;

        if(event.phase == TickEvent.Phase.START)
        {
            if(!world.isRemote)
            {
                if(world.getWorldType() == NetherEx.WORLD_TYPE)
                {
                    PigtificateVillageData data = PigtificateVillageManager.getVillageData(world, false);

                    if(data != null)
                    {
                        data.tick();
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onWorldUnload(WorldEvent.Unload event)
    {
        World world = event.getWorld();

        if(!world.isRemote)
        {
            if(world.getWorldType() == NetherEx.WORLD_TYPE)
            {
                NetherExAPI.getInstance().getBiomeDataManager().writeBiomeDataConfigs(event);
                NetherExAPI.getInstance().getTradeManager().writeTradeConfigs(event);
                PigtificateVillageManager.unloadVillageData(world);
            }
        }
    }
}

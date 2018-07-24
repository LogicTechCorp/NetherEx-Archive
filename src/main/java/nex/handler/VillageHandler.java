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

package nex.handler;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import nex.NetherEx;
import nex.village.PigtificateVillageCollection;
import nex.village.PigtificateVillageManager;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class VillageHandler
{
    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if(event.phase == TickEvent.Phase.START)
        {
            PigtificateVillageCollection villages = PigtificateVillageManager.getNetherVillages(event.world, false);

            if(villages != null)
            {
                villages.tick();
            }
        }
    }
}

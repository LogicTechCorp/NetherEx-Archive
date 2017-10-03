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

import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import nex.NetherEx;
import nex.world.TeleporterNetherEx;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class NetherPortalHandler
{
    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event)
    {
        World world = event.getWorld();

        if(!world.isRemote)
        {
            if(world.provider.getDimension() == DimensionType.OVERWORLD.getId() || world.provider.getDimension() == DimensionType.NETHER.getId())
            {
                ReflectionHelper.setPrivateValue(WorldServer.class, (WorldServer) world, new TeleporterNetherEx((WorldServer) world), "field_85177_Q", "worldTeleporter");
            }
        }
    }
}

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

package logictechcorp.netherex.proxy;

import logictechcorp.libraryex.proxy.IProxy;
import logictechcorp.netherex.NetherEx;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

public class CommonProxy implements IProxy
{
    @Override
    public void registerHandlers()
    {
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        forgeEventBus.addListener(this::onServerAboutToStart);
        forgeEventBus.addListener(this::onServerStopping);
    }

    private void onServerAboutToStart(FMLServerAboutToStartEvent event)
    {
        event.getServer().getResourceManager().addReloadListener(NetherEx.BIOME_DATA_MANAGER);
    }

    private void onServerStopping(FMLServerStoppingEvent event)
    {
        NetherEx.BIOME_DATA_MANAGER.cleanup();
    }
}

/*
 * LibraryEx
 * Copyright (c) 2017-2019 by LogicTechCorp
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

package logictechcorp.netherex.api.internal;

import logictechcorp.libraryex.world.biome.IBiomeDataManager;
import net.minecraftforge.event.world.WorldEvent;

public final class NetherBiomeDataManagerStub implements IBiomeDataManager
{
    public static final IBiomeDataManager INSTANCE = new NetherBiomeDataManagerStub();

    private NetherBiomeDataManagerStub()
    {
    }

    @Override
    public void readBiomeDataConfigs(WorldEvent.Load event)
    {

    }

    @Override
    public void writeBiomeDataConfigs(WorldEvent.Unload event)
    {

    }
}

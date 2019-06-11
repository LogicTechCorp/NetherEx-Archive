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

package logictechcorp.netherex.api.internal.impl;

import logictechcorp.libraryex.api.entity.trade.ITradeManager;
import logictechcorp.libraryex.api.world.biome.data.IBiomeDataManager;
import logictechcorp.libraryex.api.world.biome.data.IBiomeDataRegistry;
import logictechcorp.netherex.api.internal.iface.INetherExAPI;

public final class NetherExAPIStub implements INetherExAPI
{
    public static final INetherExAPI INSTANCE = new NetherExAPIStub();

    private NetherExAPIStub()
    {
    }

    @Override
    public boolean isStub()
    {
        return true;
    }

    @Override
    public IBiomeDataRegistry getBiomeDataRegistry()
    {
        return NetherBiomeDataRegistryStub.INSTANCE;
    }

    @Override
    public IBiomeDataManager getBiomeDataManager()
    {
        return NetherBiomeDataManagerStub.INSTANCE;
    }

    @Override
    public ITradeManager getTradeManager()
    {
        return TradeManagerStub.INSTANCE;
    }
}

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

import net.minecraftforge.event.world.WorldEvent;

public interface ITradeManager
{
    /**
     * Reads trade configs from disk and then configures them.
     *
     * @param event To ensure that this is only called after the server has started.
     */

    void readTradeConfigs(WorldEvent.Load event);

    /**
     * Writes trade configs to disk.
     *
     * @param event To ensure that this is only called before the server has stopped.
     */
    void writeTradeConfigs(WorldEvent.Unload event);
}

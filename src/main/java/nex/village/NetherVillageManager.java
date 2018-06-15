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

package nex.village;

import com.google.common.collect.Maps;
import net.minecraft.world.World;

import java.util.Map;

public class NetherVillageManager
{
    private static final Map<World, NetherVillageCollection> netherVillages = Maps.newHashMap();

    public static void init(World world)
    {
        String id = NetherVillageCollection.fileNameForProvider(world.provider);
        NetherVillageCollection netherVillageCollection = (NetherVillageCollection) world.getPerWorldStorage().getOrLoadData(NetherVillageCollection.class, id);

        if(netherVillageCollection == null)
        {
            netherVillages.put(world, new NetherVillageCollection(world));
            world.getPerWorldStorage().setData(id, netherVillages.get(world));
        }
        else
        {
            netherVillages.put(world, netherVillageCollection);
            netherVillages.get(world).setWorldsForAll(world);
        }
    }

    public static NetherVillageCollection getNetherVillages(World world)
    {
        return netherVillages.get(world);
    }
}

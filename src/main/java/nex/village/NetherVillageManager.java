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
    private static NetherVillageCollection getOrCreate(World world)
    {
        String dimension = world.provider.getDimensionType().name().toLowerCase();
        String id = NetherVillageCollection.fileNameForProvider(world.provider);
        NetherVillageCollection NetherVillageCollection = (NetherVillageCollection) world.getPerWorldStorage().getOrLoadData(NetherVillageCollection.class, id);

        if(NetherVillageCollection == null)
        {

            NetherVillageCollection = new NetherVillageCollection(world);
            world.getPerWorldStorage().setData(id, NetherVillageCollection);
        }
        else
        {
            NetherVillageCollection.setWorldsForAll(world);
        }
        return NetherVillageCollection;
    }

    public static NetherVillageCollection getNetherVillages(World world)
    {
        return getOrCreate(world);
    }

    public static NetherVillageCollection getNetherVillagesNoCreate(World world){
        String id = NetherVillageCollection.fileNameForProvider(world.provider);
        return (NetherVillageCollection) world.getPerWorldStorage().getOrLoadData(NetherVillageCollection.class, id);
    }
}

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

import net.minecraft.world.World;

public class NetherExVillageManager
{
    public static NetherExVillageCollection getNetherVillages(World world, boolean create)
    {
        String worldFile = NetherExVillageCollection.fileNameForProvider(world.provider);
        NetherExVillageCollection netherExVillageCollection = (NetherExVillageCollection) world.getPerWorldStorage().getOrLoadData(NetherExVillageCollection.class, worldFile);

        if(create)
        {
            if(netherExVillageCollection == null)
            {
                netherExVillageCollection = new NetherExVillageCollection(world);
                world.getPerWorldStorage().setData(worldFile, netherExVillageCollection);
            }
            else
            {
                netherExVillageCollection.setWorldsForAll(world);
            }
        }

        return netherExVillageCollection;
    }
}

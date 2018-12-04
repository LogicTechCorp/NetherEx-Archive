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

package logictechcorp.netherex.village;

import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class PigtificateVillageManager
{
    private static final Map<Integer, PigtificateVillageData> PIGTIFICATE_VILLAGE_DATA = new HashMap<>();

    public static void loadVillageData(World world)
    {
        if(!hasData(world))
        {
            getVillageData(world, false);
        }
    }

    public static void unloadVillageData(World world)
    {
        PIGTIFICATE_VILLAGE_DATA.remove(world.provider.getDimension());
    }

    public static boolean hasData(World world)
    {
        return PIGTIFICATE_VILLAGE_DATA.containsKey(world.provider.getDimension());
    }

    public static PigtificateVillageData getVillageData(World world, boolean createData)
    {
        PigtificateVillageData data;
        int dimensionId = world.provider.getDimension();

        if(hasData(world))
        {
            data = PIGTIFICATE_VILLAGE_DATA.get(dimensionId);
        }
        else
        {
            String worldFile = PigtificateVillageData.getDataId(world);
            data = (PigtificateVillageData) world.loadData(PigtificateVillageData.class, worldFile);

            if(data == null && createData)
            {
                data = new PigtificateVillageData(world);
                world.setData(worldFile, data);
            }
        }

        if(data != null)
        {
            data.setWorld(world);
            PIGTIFICATE_VILLAGE_DATA.put(dimensionId, data);
        }

        return data;
    }
}

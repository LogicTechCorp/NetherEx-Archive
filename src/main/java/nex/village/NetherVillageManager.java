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

package nex.village;

import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetherVillageManager
{
    private static NetherVillageCollection netherVillages;

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherVillageManager");

    public static void init(World world)
    {
        LOGGER.info("Attempting to read the Nether Village data.");

        String s = NetherVillageCollection.fileNameForProvider(world.provider);
        NetherVillageCollection netherVillageCollection = (NetherVillageCollection) world.getPerWorldStorage().getOrLoadData(NetherVillageCollection.class, s);

        if(netherVillageCollection == null)
        {
            LOGGER.info("The Nether Village data was unable to be read.");

            netherVillages = new NetherVillageCollection(world);
            world.getPerWorldStorage().setData(s, netherVillages);
        }
        else
        {
            LOGGER.info("The Nether Village data was read successfully.");

            netherVillages = netherVillageCollection;
            netherVillages.setWorldsForAll(world);
        }
    }

    public static NetherVillageCollection getNetherVillages()
    {
        return netherVillages;
    }
}

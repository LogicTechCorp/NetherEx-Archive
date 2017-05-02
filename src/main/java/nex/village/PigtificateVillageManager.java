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

public class PigtificateVillageManager
{
    private static PigtificateVillageCollection pigtificateVillages;

    private static final Logger LOGGER = LogManager.getLogger("NetherEx|PigtificateVillageManager");

    public static void init(World world)
    {
        LOGGER.info("Attempting to read the Nether Village data.");

        String s = PigtificateVillageCollection.fileNameForProvider(world.provider);
        PigtificateVillageCollection pigtificateVillageCollection = (PigtificateVillageCollection) world.getPerWorldStorage().getOrLoadData(PigtificateVillageCollection.class, s);

        if(pigtificateVillageCollection == null)
        {
            LOGGER.info("The Pigtificate Village data was created successfully.");

            pigtificateVillages = new PigtificateVillageCollection(world);
            world.getPerWorldStorage().setData(s, pigtificateVillages);
        }
        else
        {
            LOGGER.info("The Pigtificate Village data was read successfully.");

            pigtificateVillages = pigtificateVillageCollection;
            pigtificateVillages.setWorldsForAll(world);
        }
    }

    public static PigtificateVillageCollection getPigtificateVillages()
    {
        return pigtificateVillages;
    }
}

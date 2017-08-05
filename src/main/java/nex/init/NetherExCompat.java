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

package nex.init;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import nex.world.biome.NetherBiomeManager;

import java.io.File;

public class NetherExCompat
{
    private static boolean biomesOPlentyCompatEnabled = false;

    public static void resetCompat()
    {
        biomesOPlentyCompatEnabled = false;
    }

    public static void enableBiomesOPlentyCompat(World world, File directory)
    {
        if(!biomesOPlentyCompatEnabled)
        {
            WorldType worldType = world.getWorldType();

            if(worldType.getName().equalsIgnoreCase("BIOMESOP") || worldType.getName().equalsIgnoreCase("lostcities_bop"))
            {
                NetherBiomeManager.parseBiomeConfigs(new File(directory, "/NetherEx/Biome Configs/biomesoplenty"));
                biomesOPlentyCompatEnabled = true;
            }
        }
    }
}

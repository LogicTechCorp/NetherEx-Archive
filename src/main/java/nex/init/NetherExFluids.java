/*
 * Copyright (C) 2016.  LogicTechCorp
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetherExFluids
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherExFluids");

    static
    {
        LOGGER.info("Fluid registration started.");
        LOGGER.info("Fluid registration completed.");
    }

    public static void runStaticInit()
    {

    }
}

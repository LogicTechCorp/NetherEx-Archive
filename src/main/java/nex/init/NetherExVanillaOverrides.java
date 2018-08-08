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

package nex.init;

import net.minecraft.init.Blocks;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import nex.NetherEx;
import nex.handler.ConfigHandler;
import nex.world.WorldProviderNetherEx;

public class NetherExVanillaOverrides
{
    public static void preInit()
    {
        if(ConfigHandler.blockConfig.netherWart.growthTickSpeed > 0)
        {
            Blocks.NETHER_WART.setTickRandomly(false);
        }
    }

    public static void postInit()
    {
        DimensionManager.unregisterDimension(-1);
        DimensionType nether = DimensionType.register("Nether", "_nether", -1, WorldProviderNetherEx.class, false);
        DimensionManager.registerDimension(-1, nether);
        NetherEx.LOGGER.info("The Nether has been overridden.");
    }
}

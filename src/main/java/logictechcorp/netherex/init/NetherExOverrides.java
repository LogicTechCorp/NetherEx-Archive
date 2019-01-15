/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
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

package logictechcorp.netherex.init;

import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.handler.ConfigHandler;
import logictechcorp.netherex.world.WorldProviderNetherEx;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class NetherExOverrides
{
    public static void overrideObjects()
    {
        if(ConfigHandler.blockConfig.netherWart.growthTickSpeed > 0)
        {
            Blocks.NETHER_WART.setTickRandomly(false);
        }

        Blocks.OBSIDIAN.setTickRandomly(true);
        Biomes.HELL.topBlock = Blocks.NETHERRACK.getDefaultState();
        Biomes.HELL.fillerBlock = Blocks.NETHERRACK.getDefaultState();
    }

    public static void overrideNether()
    {
        if(ConfigHandler.dimensionConfig.nether.overrideNether)
        {
            DimensionManager.unregisterDimension(-1);
            DimensionType nether = DimensionType.register("Nether", "_nether", -1, WorldProviderNetherEx.class, false);
            DimensionManager.registerDimension(-1, nether);
            NetherEx.LOGGER.info("The Nether has been overridden.");
        }
    }
}

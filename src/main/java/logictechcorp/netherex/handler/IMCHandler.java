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

package logictechcorp.netherex.handler;

import logictechcorp.libraryex.utility.IMCHelper;
import logictechcorp.netherex.init.NetherExBlocks;

public class IMCHandler
{
    public static void sendCompatibilityMessages()
    {
        IMCHelper.registerChiseledBlock("netherrack", NetherExBlocks.GLOOMY_NETHERRACK.getDefaultState());
        IMCHelper.registerChiseledBlock("netherrack", NetherExBlocks.LIVELY_NETHERRACK.getDefaultState());
        IMCHelper.registerChiseledBlock("netherrack", NetherExBlocks.FIERY_NETHERRACK.getDefaultState());
        IMCHelper.registerChiseledBlock("netherrack", NetherExBlocks.ICY_NETHERRACK.getDefaultState());
        IMCHelper.registerChiseledBlock("netherbrick", NetherExBlocks.GLOOMY_NETHER_BRICK.getDefaultState());
        IMCHelper.registerChiseledBlock("netherbrick", NetherExBlocks.LIVELY_NETHER_BRICK.getDefaultState());
        IMCHelper.registerChiseledBlock("netherbrick", NetherExBlocks.FIERY_NETHER_BRICK.getDefaultState());
        IMCHelper.registerChiseledBlock("netherbrick", NetherExBlocks.ICY_NETHER_BRICK.getDefaultState());
        IMCHelper.registerChiseledBlock("basalt", NetherExBlocks.BASALT.getDefaultState());
        IMCHelper.registerChiseledBlock("basalt", NetherExBlocks.SMOOTH_BASALT.getDefaultState());
        IMCHelper.registerChiseledBlock("basalt", NetherExBlocks.BASALT_BRICK.getDefaultState());
        IMCHelper.registerChiseledBlock("basalt", NetherExBlocks.BASALT_PILLAR.getDefaultState());
        IMCHelper.registerChiseledBlock("plankWood-oak", NetherExBlocks.GENESIS_PLANK.getDefaultState());
        IMCHelper.registerChiseledBlock("plankWood-spruce", NetherExBlocks.GENESIS_PLANK.getDefaultState());
        IMCHelper.registerChiseledBlock("plankWood-birch", NetherExBlocks.GENESIS_PLANK.getDefaultState());
        IMCHelper.registerChiseledBlock("plankWood-jungle", NetherExBlocks.GENESIS_PLANK.getDefaultState());
        IMCHelper.registerChiseledBlock("plankWood-acacia", NetherExBlocks.GENESIS_PLANK.getDefaultState());
        IMCHelper.registerChiseledBlock("plankWood-dark-oak", NetherExBlocks.GENESIS_PLANK.getDefaultState());
        IMCHelper.registerChiseledBlock("ice", NetherExBlocks.FROSTBURN_ICE.getDefaultState());
        IMCHelper.registerChiseledBlock("glass", NetherExBlocks.SOUL_GLASS.getDefaultState());
        IMCHelper.registerChiseledBlock("obsidian", NetherExBlocks.CRYING_OBSIDIAN.getDefaultState());
        IMCHelper.registerChiseledBlock("obsidian", NetherExBlocks.GLOWING_OBSIDIAN.getDefaultState());
    }
}

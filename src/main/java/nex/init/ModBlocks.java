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

import net.minecraft.block.Block;
import nex.block.*;

public class ModBlocks
{
    public static final BlockThornBush THORN_BUSH;
    public static final Block NETHERRACK;
    public static final Block QUARTZ_ORE;
    public static final Block MYCELIUM;
    public static final BlockFungalRoots FUNGAL_ROOTS;
    public static final BlockEnokiCap ENOKI_CAP;
    public static final Block ENOKI_STEM;
    public static final Block RIME_ORE;
    public static final Block RIME_ICE;
    public static final Block FROST;

    static
    {
        THORN_BUSH = new BlockThornBush();
        NETHERRACK = new BlockNetherrack();
        QUARTZ_ORE = new BlockQuartzOre();
        MYCELIUM = new BlockMycelium();
        FUNGAL_ROOTS = new BlockFungalRoots();
        ENOKI_CAP = new BlockEnokiCap();
        ENOKI_STEM = new BlockEnokiStem();
        RIME_ORE = new BlockRimeOre();
        RIME_ICE = new BlockRimeIce();
        FROST = new BlockFrost();
    }

    public static void init()
    {

    }
}

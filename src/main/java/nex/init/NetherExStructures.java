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

import net.minecraft.world.gen.structure.MapGenStructureIO;
import nex.world.gen.structure.MapGenNetherStructures;
import nex.world.gen.structure.NetherStructures;

public class NetherExStructures
{
    public static void init()
    {
        MapGenStructureIO.registerStructure(MapGenNetherStructures.Start.class, "Nether Structures");
        MapGenStructureIO.registerStructureComponent(NetherStructures.AncientAltar.class, "AA");
    }
}

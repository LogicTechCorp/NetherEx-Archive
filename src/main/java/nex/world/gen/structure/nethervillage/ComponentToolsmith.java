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

package nex.world.gen.structure.nethervillage;

import net.minecraft.util.EnumFacing;

import java.util.Random;

public class ComponentToolsmith extends ComponentPigtificateStructure
{

    public ComponentToolsmith()
    {

    }

    public ComponentToolsmith(ComponentWell.Controller controller, int componentType, String[] templates, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, Random rand)
    {
        super(controller, componentType, templates, structureMinX, structureMinY, structureMinZ, facing, rand);
    }
}

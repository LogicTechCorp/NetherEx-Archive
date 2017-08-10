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
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import nex.village.Pigtificate;

import java.util.List;
import java.util.Random;

public class StructureNetherVillageBlacksmithHut extends StructureNetherVillageHut
{
    public StructureNetherVillageBlacksmithHut()
    {

    }

    private StructureNetherVillageBlacksmithHut(StructureNetherVillageWell.Controller controller, int componentType, Random rand, StructureBoundingBox boundingBoxIn, EnumFacing facing)
    {
        super(controller, componentType, rand, boundingBoxIn, facing);
    }

    public static StructureNetherVillageBlacksmithHut createPiece(StructureNetherVillageWell.Controller controller, List<StructureComponent> components, Random rand, int minX, int minY, int minZ, EnumFacing facing, int componentType)
    {
        StructureBoundingBox boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, 9, 5, 9, facing);
        return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(components, boundingBox) == null ? new StructureNetherVillageBlacksmithHut(controller, componentType, rand, boundingBox, facing) : null;
    }

    @Override
    public boolean addComponentParts(World world, Random rand, StructureBoundingBox boundingBoxIn)
    {
        boolean generate = super.addComponentParts(world, rand, boundingBoxIn);

        if(generate)
        {
            Pigtificate.Career career = Pigtificate.Career.getRandomCareer(Pigtificate.Profession.BLACKSMITH, rand);

            if(career == Pigtificate.Career.ARMORSMITH)
            {

            }
            else if(career == Pigtificate.Career.TOOLSMITH)
            {

            }
        }

        return generate;
    }
}

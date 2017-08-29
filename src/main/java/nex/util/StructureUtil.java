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

package nex.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

public class StructureUtil
{
    public static StructureBoundingBox getBoundingBoxFromTemplate(Template template, PlacementSettings placementSettings, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing)
    {
        StructureBoundingBox boundingBox = getBoundingBoxFromTemplate(template, placementSettings);
        return StructureBoundingBox.getComponentToAddBoundingBox(structureMinX, structureMinY, structureMinZ, 0, 0, 0, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ, facing);
    }

    public static StructureBoundingBox getBoundingBoxFromTemplate(Template template, PlacementSettings placementSettings)
    {
        Rotation rotation = placementSettings.getRotation();
        Mirror mirror = placementSettings.getMirror();
        BlockPos templateSize = template.transformedSize(rotation);
        StructureBoundingBox boundingBox = new StructureBoundingBox(0, 0, 0, templateSize.getX(), templateSize.getY(), templateSize.getZ());

        switch (rotation)
        {
            case NONE:
            default:
                break;
            case CLOCKWISE_90:
                boundingBox.offset(-templateSize.getX(), 0, 0);
                break;
            case COUNTERCLOCKWISE_90:
                boundingBox.offset(0, 0, -templateSize.getZ());
                break;
            case CLOCKWISE_180:
                boundingBox.offset(-templateSize.getX(), 0, -templateSize.getZ());
        }

        switch (mirror)
        {
            case NONE:
            default:
                break;
            case FRONT_BACK:
                BlockPos mirroredFrontBackPos = BlockPos.ORIGIN;

                if (rotation != Rotation.CLOCKWISE_90 && rotation != Rotation.COUNTERCLOCKWISE_90)
                {
                    if (rotation == Rotation.CLOCKWISE_180)
                    {
                        mirroredFrontBackPos = mirroredFrontBackPos.offset(EnumFacing.EAST, templateSize.getX());
                    }
                    else
                    {
                        mirroredFrontBackPos = mirroredFrontBackPos.offset(EnumFacing.WEST, templateSize.getX());
                    }
                }
                else
                {
                    mirroredFrontBackPos = mirroredFrontBackPos.offset(rotation.rotate(EnumFacing.WEST), templateSize.getZ());
                }

                boundingBox.offset(mirroredFrontBackPos.getX(), 0, mirroredFrontBackPos.getZ());
                break;
            case LEFT_RIGHT:
                BlockPos mirroredLeftRightPos = BlockPos.ORIGIN;

                if (rotation != Rotation.CLOCKWISE_90 && rotation != Rotation.COUNTERCLOCKWISE_90)
                {
                    if (rotation == Rotation.CLOCKWISE_180)
                    {
                        mirroredLeftRightPos = mirroredLeftRightPos.offset(EnumFacing.SOUTH, templateSize.getZ());
                    }
                    else
                    {
                        mirroredLeftRightPos = mirroredLeftRightPos.offset(EnumFacing.NORTH, templateSize.getZ());
                    }
                }
                else
                {
                    mirroredLeftRightPos = mirroredLeftRightPos.offset(rotation.rotate(EnumFacing.NORTH), templateSize.getX());
                }

                boundingBox.offset(mirroredLeftRightPos.getX(), 0, mirroredLeftRightPos.getZ());
        }

        return boundingBox;
    }
}

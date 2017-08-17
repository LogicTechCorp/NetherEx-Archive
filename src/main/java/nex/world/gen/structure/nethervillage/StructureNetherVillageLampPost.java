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

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import nex.block.BlockVanilla;
import nex.block.BlockVanillaFence;
import nex.init.NetherExBlocks;

import java.util.List;
import java.util.Random;

public class StructureNetherVillageLampPost extends StructureNetherVillage
{
    public StructureNetherVillageLampPost()
    {
    }

    public StructureNetherVillageLampPost(StructureNetherVillageWell.Controller controller, int componentType, Random rand, StructureBoundingBox boundingBoxIn, EnumFacing facing)
    {
        super(controller, componentType);
        setCoordBaseMode(facing);
        boundingBox = boundingBoxIn;
    }

    public static StructureBoundingBox findPieceBox(StructureNetherVillageWell.Controller controller, List<StructureComponent> components, Random rand, int minX, int minY, int minZ, EnumFacing facing)
    {
        StructureBoundingBox boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, 3, 4, 2, facing);
        return StructureComponent.findIntersecting(components, boundingBox) != null ? null : boundingBox;
    }

    @Override
    public boolean addComponentParts(World world, Random rand, StructureBoundingBox boundingBoxIn)
    {
        if(averageGroundLvl < 0)
        {
            averageGroundLvl = findAverageGroundLevel(world, boundingBoxIn);

            if(averageGroundLvl < 0)
            {
                return true;
            }

            for(int x = 0; x < 3; x++)
            {
                for(int z = 0; z < 3; z++)
                {
                    StructureBoundingBox fakeBoundingBox = new StructureBoundingBox(boundingBox);
                    fakeBoundingBox.offset(0, averageGroundLvl - boundingBox.maxY + 4 - 1, 0);

                    BlockPos pos = new BlockPos(getXWithOffset(x, z), fakeBoundingBox.minY - 1, getZWithOffset(x, z));

                    if(world.isAirBlock(pos))
                    {
                        return false;
                    }
                }
            }

            boundingBox.offset(0, averageGroundLvl - boundingBox.maxY + 4 - 1, 0);
        }

        IBlockState redNetherBrickFence = NetherExBlocks.FENCE_VANILLA.getDefaultState().withProperty(BlockVanillaFence.TYPE, BlockVanilla.EnumTypeFence.BRICK_NETHER_RED);

        fillWithAir(world, boundingBoxIn, 0, 0, 0, 2, 5, 2);
        fillWithBlocks(world, boundingBoxIn, 1, 0, 1, 1, 2, 1, redNetherBrickFence, redNetherBrickFence, false);
        setBlockState(world, Blocks.GLOWSTONE.getDefaultState(), 1, 3, 1, boundingBoxIn);

        for(int x = 0; x < 3; x++)
        {
            for(int z = 0; z < 3; z++)
            {
                replaceAirAndLiquidDownwards(world, Blocks.NETHERRACK.getDefaultState(), x, -1, z, boundingBoxIn);
            }
        }

        return true;
    }
}

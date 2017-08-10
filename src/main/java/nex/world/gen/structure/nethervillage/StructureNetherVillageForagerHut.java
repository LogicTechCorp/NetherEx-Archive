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

import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import nex.village.Pigtificate;

import java.util.List;
import java.util.Random;

public class StructureNetherVillageForagerHut extends StructureNetherVillageHut
{
    public StructureNetherVillageForagerHut()
    {

    }

    private StructureNetherVillageForagerHut(StructureNetherVillageWell.Controller controller, int componentType, Random rand, StructureBoundingBox boundingBoxIn, EnumFacing facing)
    {
        super(controller, componentType, rand, boundingBoxIn, facing);
    }

    public static StructureNetherVillageForagerHut createPiece(StructureNetherVillageWell.Controller controller, List<StructureComponent> components, Random rand, int minX, int minY, int minZ, EnumFacing facing, int componentType)
    {
        StructureBoundingBox boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, 9, 5, 9, facing);
        return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(components, boundingBox) == null ? new StructureNetherVillageForagerHut(controller, componentType, rand, boundingBox, facing) : null;
    }

    @Override
    public boolean addComponentParts(World world, Random rand, StructureBoundingBox boundingBoxIn)
    {
        boolean generate = super.addComponentParts(world, rand, boundingBoxIn);

        if(generate)
        {
            IBlockState netherBrick = getBiomeSpecificBlock(Blocks.NETHER_BRICK.getDefaultState());
            IBlockState netherBrickStairs = getBiomeSpecificBlock(Blocks.NETHER_BRICK_STAIRS.getDefaultState());

            Pigtificate.Career career = Pigtificate.Career.getRandomCareer(Pigtificate.Profession.FORAGER, rand);

            if(career == Pigtificate.Career.HUNTER)
            {
                int variant = rand.nextInt(2);

                if(variant == 0)
                {
                    setBlockState(world, netherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.FACING, EnumFacing.WEST), 2, 0, 2, boundingBoxIn);
                    setBlockState(world, netherBrick, 2, 0, 3, boundingBoxIn);
                    setBlockState(world, netherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.FACING, EnumFacing.WEST), 2, 0, 4, boundingBoxIn);
                    setBlockState(world, netherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.FACING, EnumFacing.EAST), 6, 0, 2, boundingBoxIn);

                    spawnPigtificates(world, boundingBox, 4, 0, 3, career, 1, new ItemStack(Items.GOLDEN_SWORD, 1, 0));
                }
                else if(variant == 1)
                {
                    setBlockState(world, netherBrick, 6, 0, 2, boundingBoxIn);
                    setBlockState(world, netherBrick, 6, 0, 3, boundingBoxIn);
                    setBlockState(world, netherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP).withProperty(BlockStairs.FACING, EnumFacing.EAST), 5, 0, 5, boundingBoxIn);
                    setBlockState(world, netherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP).withProperty(BlockStairs.FACING, EnumFacing.WEST), 2, 0, 4, boundingBoxIn);
                    setBlockState(world, Blocks.SKULL.getDefaultState().withProperty(BlockSkull.FACING, EnumFacing.UP), 2, 1, 4, boundingBoxIn);

                    spawnPigtificates(world, boundingBox, 4, 0, 3, career, 1, new ItemStack(Items.GOLDEN_AXE, 1, 0));
                }
            }
            else if(career == Pigtificate.Career.GATHERER)
            {
            }
            else if(career == Pigtificate.Career.SCAVENGER)
            {
            }
        }

        return generate;
    }
}

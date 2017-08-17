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

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import nex.init.NetherExLootTables;
import nex.village.Pigtificate;

import java.util.List;
import java.util.Random;

public class StructureNetherVillageScavengerHut extends StructureNetherVillageHut
{
    public StructureNetherVillageScavengerHut()
    {

    }

    private StructureNetherVillageScavengerHut(StructureNetherVillageWell.Controller controller, int componentType, Random rand, StructureBoundingBox boundingBoxIn, EnumFacing facing)
    {
        super(controller, componentType, rand, boundingBoxIn, facing);
    }

    public static StructureNetherVillageScavengerHut createPiece(StructureNetherVillageWell.Controller controller, List<StructureComponent> components, Random rand, int minX, int minY, int minZ, EnumFacing facing, int componentType)
    {
        StructureBoundingBox boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, 9, 5, 9, facing);
        return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(components, boundingBox) == null ? new StructureNetherVillageScavengerHut(controller, componentType, rand, boundingBox, facing) : null;
    }

    @Override
    public boolean addComponentParts(World world, Random rand, StructureBoundingBox boundingBoxIn)
    {
        boolean generate = super.addComponentParts(world, rand, boundingBoxIn);

        if(generate)
        {
            IBlockState netherBrick = Blocks.NETHER_BRICK.getDefaultState();
            IBlockState netherBrickStairs = Blocks.NETHER_BRICK_STAIRS.getDefaultState();

            if(rand.nextBoolean())
            {
                setBlockState(world, netherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.FACING, EnumFacing.NORTH), 1, 0, 4, boundingBoxIn);
                setBlockState(world, netherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.FACING, EnumFacing.WEST), 1, 0, 3, boundingBoxIn);
                setBlockState(world, netherBrick, 2, 0, 5, boundingBoxIn);
                generateChest(world, boundingBoxIn, rand, 5, 0, 4, NetherExLootTables.CHEST_VILLAGE_BASE);
                setBlockState(world, netherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.FACING, EnumFacing.SOUTH), 5, 0, 2, boundingBoxIn);

                spawnPigtificates(world, boundingBox, 3, 0, 3, Pigtificate.Career.SCAVENGER, 1, new ItemStack(Items.GOLDEN_HOE, 1, 0));
            }
            else
            {
                setBlockState(world, netherBrick, 1, 0, 4, boundingBoxIn);
                setBlockState(world, netherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.FACING, EnumFacing.WEST), 1, 0, 3, boundingBoxIn);
                setBlockState(world, netherBrick, 4, 0, 5, boundingBoxIn);
                generateChest(world, boundingBoxIn, rand, 2, 0, 5, NetherExLootTables.CHEST_VILLAGE_BASE);
                setBlockState(world, netherBrickStairs.withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.FACING, EnumFacing.SOUTH), 5, 0, 2, boundingBoxIn);

                spawnPigtificates(world, boundingBox, 3, 0, 3, Pigtificate.Career.SCAVENGER, 1, new ItemStack(Items.AIR, 1, 0));
            }
        }

        return generate;
    }
}

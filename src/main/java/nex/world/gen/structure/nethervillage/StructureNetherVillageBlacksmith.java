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
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

import java.util.List;
import java.util.Random;

public class StructureNetherVillageBlacksmith extends StructureNetherVillage
{
    private boolean hasMadeChest;

    public StructureNetherVillageBlacksmith()
    {
    }

    private StructureNetherVillageBlacksmith(StructureNetherVillageWell.Controller controller, int componentType, Random rand, StructureBoundingBox boundingBoxIn, EnumFacing facing)
    {
        super(controller, componentType);
        setCoordBaseMode(facing);
        boundingBox = boundingBoxIn;
    }

    public static StructureNetherVillageBlacksmith createPiece(StructureNetherVillageWell.Controller controller, List<StructureComponent> components, Random rand, int minX, int minY, int minZ, EnumFacing facing, int componentType)
    {
        StructureBoundingBox boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, 10, 6, 7, facing);
        return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(components, boundingBox) == null ? new StructureNetherVillageBlacksmith(controller, componentType, rand, boundingBox, facing) : null;
    }

    @Override
    protected void writeStructureToNBT(NBTTagCompound tagCompound)
    {
        super.writeStructureToNBT(tagCompound);
        tagCompound.setBoolean("Chest", hasMadeChest);
    }

    @Override
    protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
    {
        super.readStructureFromNBT(tagCompound, p_143011_2_);
        hasMadeChest = tagCompound.getBoolean("Chest");
    }

    @Override
    public boolean addComponentParts(World world, Random random, StructureBoundingBox boundingBoxIn)
    {
        if(averageGroundLvl < 0)
        {
            averageGroundLvl = getAverageGroundLevel(world, boundingBoxIn);

            if(averageGroundLvl < 0)
            {
                return true;
            }

            boundingBox.offset(0, averageGroundLvl - boundingBox.maxY + 6 - 1, 0);
        }

        IBlockState iblockstate = Blocks.COBBLESTONE.getDefaultState();
        IBlockState iblockstate1 = getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
        IBlockState iblockstate2 = getBiomeSpecificBlockState(Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST));
        IBlockState iblockstate3 = getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState());
        IBlockState iblockstate4 = getBiomeSpecificBlockState(Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH));
        IBlockState iblockstate5 = getBiomeSpecificBlockState(Blocks.LOG.getDefaultState());
        IBlockState iblockstate6 = getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
        fillWithBlocks(world, boundingBoxIn, 0, 1, 0, 9, 4, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
        fillWithBlocks(world, boundingBoxIn, 0, 0, 0, 9, 0, 6, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 0, 4, 0, 9, 4, 6, iblockstate, iblockstate, false);
        fillWithBlocks(world, boundingBoxIn, 0, 5, 0, 9, 5, 6, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
        fillWithBlocks(world, boundingBoxIn, 1, 5, 1, 8, 5, 5, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
        fillWithBlocks(world, boundingBoxIn, 1, 1, 0, 2, 3, 0, iblockstate3, iblockstate3, false);
        fillWithBlocks(world, boundingBoxIn, 0, 1, 0, 0, 4, 0, iblockstate5, iblockstate5, false);
        fillWithBlocks(world, boundingBoxIn, 3, 1, 0, 3, 4, 0, iblockstate5, iblockstate5, false);
        fillWithBlocks(world, boundingBoxIn, 0, 1, 6, 0, 4, 6, iblockstate5, iblockstate5, false);
        setBlockState(world, iblockstate3, 3, 3, 1, boundingBoxIn);
        fillWithBlocks(world, boundingBoxIn, 3, 1, 2, 3, 3, 2, iblockstate3, iblockstate3, false);
        fillWithBlocks(world, boundingBoxIn, 4, 1, 3, 5, 3, 3, iblockstate3, iblockstate3, false);
        fillWithBlocks(world, boundingBoxIn, 0, 1, 1, 0, 3, 5, iblockstate3, iblockstate3, false);
        fillWithBlocks(world, boundingBoxIn, 1, 1, 6, 5, 3, 6, iblockstate3, iblockstate3, false);
        fillWithBlocks(world, boundingBoxIn, 5, 1, 0, 5, 3, 0, iblockstate6, iblockstate6, false);
        fillWithBlocks(world, boundingBoxIn, 9, 1, 0, 9, 3, 0, iblockstate6, iblockstate6, false);
        fillWithBlocks(world, boundingBoxIn, 6, 1, 4, 9, 4, 6, iblockstate, iblockstate, false);
        setBlockState(world, Blocks.FLOWING_LAVA.getDefaultState(), 7, 1, 5, boundingBoxIn);
        setBlockState(world, Blocks.FLOWING_LAVA.getDefaultState(), 8, 1, 5, boundingBoxIn);
        setBlockState(world, Blocks.IRON_BARS.getDefaultState(), 9, 2, 5, boundingBoxIn);
        setBlockState(world, Blocks.IRON_BARS.getDefaultState(), 9, 2, 4, boundingBoxIn);
        fillWithBlocks(world, boundingBoxIn, 7, 2, 4, 8, 2, 5, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
        setBlockState(world, iblockstate, 6, 1, 3, boundingBoxIn);
        setBlockState(world, Blocks.FURNACE.getDefaultState(), 6, 2, 3, boundingBoxIn);
        setBlockState(world, Blocks.FURNACE.getDefaultState(), 6, 3, 3, boundingBoxIn);
        setBlockState(world, Blocks.DOUBLE_STONE_SLAB.getDefaultState(), 8, 1, 1, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 4, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 6, boundingBoxIn);
        setBlockState(world, Blocks.GLASS_PANE.getDefaultState(), 4, 2, 6, boundingBoxIn);
        setBlockState(world, iblockstate6, 2, 1, 4, boundingBoxIn);
        setBlockState(world, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), 2, 2, 4, boundingBoxIn);
        setBlockState(world, iblockstate3, 1, 1, 5, boundingBoxIn);
        setBlockState(world, iblockstate1, 2, 1, 5, boundingBoxIn);
        setBlockState(world, iblockstate2, 1, 1, 4, boundingBoxIn);

        if(!hasMadeChest && boundingBoxIn.isVecInside(new BlockPos(getXWithOffset(5, 5), getYWithOffset(1), getZWithOffset(5, 5))))
        {
            hasMadeChest = true;
            generateChest(world, boundingBoxIn, random, 5, 1, 5, LootTableList.CHESTS_VILLAGE_BLACKSMITH);
        }

        for(int i = 6; i <= 8; ++i)
        {
            if(getBlockStateFromPos(world, i, 0, -1, boundingBoxIn).getMaterial() == Material.AIR && getBlockStateFromPos(world, i, -1, -1, boundingBoxIn).getMaterial() != Material.AIR)
            {
                setBlockState(world, iblockstate4, i, 0, -1, boundingBoxIn);

                if(getBlockStateFromPos(world, i, -1, -1, boundingBoxIn).getBlock() == Blocks.GRASS_PATH)
                {
                    setBlockState(world, Blocks.GRASS.getDefaultState(), i, -1, -1, boundingBoxIn);
                }
            }
        }

        for(int k = 0; k < 7; ++k)
        {
            for(int j = 0; j < 10; ++j)
            {
                clearCurrentPositionBlocksUpwards(world, j, 6, k, boundingBoxIn);
                replaceAirAndLiquidDownwards(world, iblockstate, j, -1, k, boundingBoxIn);
            }
        }

        spawnPigtificates(world, boundingBoxIn, 7, 1, 1, 1);
        return true;
    }

    protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession)
    {
        return 3;
    }
}

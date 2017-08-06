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

import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

import java.util.List;
import java.util.Random;

public class StructureNetherVillageWell extends StructureNetherVillage
{
    public StructureNetherVillageWell()
    {
    }

    public StructureNetherVillageWell(StructureNetherVillageWell.Start start, int type, Random rand, int x, int z)
    {
        super(start, type);
        setCoordBaseMode(EnumFacing.Plane.HORIZONTAL.random(rand));

        if(getCoordBaseMode().getAxis() == EnumFacing.Axis.Z)
        {
            boundingBox = new StructureBoundingBox(x, 64, z, x + 6 - 1, 78, z + 6 - 1);
        }
        else
        {
            boundingBox = new StructureBoundingBox(x, 64, z, x + 6 - 1, 78, z + 6 - 1);
        }
    }

    @Override
    public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand)
    {
        generateAndAddRoadPiece((StructureNetherVillageWell.Start) componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, EnumFacing.WEST, this.getComponentType());
        generateAndAddRoadPiece((StructureNetherVillageWell.Start) componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ + 1, EnumFacing.EAST, this.getComponentType());
        generateAndAddRoadPiece((StructureNetherVillageWell.Start) componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
        generateAndAddRoadPiece((StructureNetherVillageWell.Start) componentIn, listIn, rand, this.boundingBox.minX + 1, this.boundingBox.maxY - 4, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
    }

    @Override
    public boolean addComponentParts(World world, Random rand, StructureBoundingBox boundingBox)
    {
        if(this.averageGroundLvl < 0)
        {
            this.averageGroundLvl = this.getAverageGroundLevel(world, boundingBox);

            if(this.averageGroundLvl < 0)
            {
                return true;
            }

            this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 3, 0);
        }

        IBlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());
        IBlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.OAK_FENCE.getDefaultState());
        this.fillWithBlocks(world, boundingBox, 1, 0, 1, 4, 12, 4, iblockstate, Blocks.FLOWING_WATER.getDefaultState(), false);
        this.setBlockState(world, Blocks.AIR.getDefaultState(), 2, 12, 2, boundingBox);
        this.setBlockState(world, Blocks.AIR.getDefaultState(), 3, 12, 2, boundingBox);
        this.setBlockState(world, Blocks.AIR.getDefaultState(), 2, 12, 3, boundingBox);
        this.setBlockState(world, Blocks.AIR.getDefaultState(), 3, 12, 3, boundingBox);
        this.setBlockState(world, iblockstate1, 1, 13, 1, boundingBox);
        this.setBlockState(world, iblockstate1, 1, 14, 1, boundingBox);
        this.setBlockState(world, iblockstate1, 4, 13, 1, boundingBox);
        this.setBlockState(world, iblockstate1, 4, 14, 1, boundingBox);
        this.setBlockState(world, iblockstate1, 1, 13, 4, boundingBox);
        this.setBlockState(world, iblockstate1, 1, 14, 4, boundingBox);
        this.setBlockState(world, iblockstate1, 4, 13, 4, boundingBox);
        this.setBlockState(world, iblockstate1, 4, 14, 4, boundingBox);
        this.fillWithBlocks(world, boundingBox, 1, 15, 1, 4, 15, 4, iblockstate, iblockstate, false);

        for(int i = 0; i <= 5; ++i)
        {
            for(int j = 0; j <= 5; ++j)
            {
                if(j == 0 || j == 5 || i == 0 || i == 5)
                {
                    this.setBlockState(world, iblockstate, j, 11, i, boundingBox);
                    this.clearCurrentPositionBlocksUpwards(world, j, 12, i, boundingBox);
                }
            }
        }

        return true;
    }

    public static class Start extends StructureNetherVillageWell
    {
        public BiomeProvider biomeProvider;
        public int terrainType;
        public StructureNetherVillage.PieceWeight structVillagePieceWeight;
        public List<StructureNetherVillage.PieceWeight> pieceWeights;
        public List<StructureComponent> pendingHouses = Lists.newArrayList();
        public List<StructureComponent> pendingRoads = Lists.newArrayList();
        public Biome biome;

        public Start()
        {
        }

        public Start(BiomeProvider biomeProviderIn, Random rand, int x, int z, List<StructureNetherVillage.PieceWeight> pieceWeightsIn, int terrainTypeIn)
        {
            super(null, 0, rand, x, z);
            biomeProvider = biomeProviderIn;
            pieceWeights = pieceWeightsIn;
            terrainType = terrainTypeIn;
            biome = biomeProviderIn.getBiome(new BlockPos(x, 0, z), Biomes.DEFAULT);
            startPiece = this;

            if(biome instanceof BiomeDesert)
            {
                structureType = 1;
            }
            else if(biome instanceof BiomeSavanna)
            {
                structureType = 2;
            }
            else if(biome instanceof BiomeTaiga)
            {
                structureType = 3;
            }

            setStructureType(structureType);
            isZombieInfested = rand.nextInt(50) == 0;
        }
    }
}

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
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import nex.entity.passive.EntityPigtificate;
import nex.util.WorldGenUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public abstract class StructureNetherVillage extends StructureComponent
{
    protected int averageGroundLvl = -1;
    private int villagersSpawned;
    protected int structureType;
    protected boolean isZombieInfested;
    protected StructureNetherVillageWell.Start startPiece;

    public StructureNetherVillage()
    {
    }

    protected StructureNetherVillage(StructureNetherVillageWell.Start start, int type)
    {
        super(type);

        if(start != null)
        {
            this.structureType = start.structureType;
            this.isZombieInfested = start.isZombieInfested;
            startPiece = start;
        }
    }

    public static void registerVillagePieces()
    {
        MapGenStructureIO.registerStructureComponent(StructureNetherVillageWell.Start.class, "nether_village_well_start");
        MapGenStructureIO.registerStructureComponent(StructureNetherVillageWell.class, "nether_village_well");
        MapGenStructureIO.registerStructureComponent(StructureNetherVillagePath.class, "nether_village_path");
        MapGenStructureIO.registerStructureComponent(StructureNetherVillageHouse1.class, "nether_village_house_1");
        MapGenStructureIO.registerStructureComponent(StructureNetherVillageHouse2.class, "nether_village_house_2");
        MapGenStructureIO.registerStructureComponent(StructureNetherVillageHouse3.class, "nether_village_house_3");
        MapGenStructureIO.registerStructureComponent(StructureNetherVillageHouse4.class, "nether_village_house_4");
        MapGenStructureIO.registerStructureComponent(StructureNetherVillageWoodHut.class, "nether_village_wood_hut");
        MapGenStructureIO.registerStructureComponent(StructureNetherVillageHall.class, "nether_village_hall");
        MapGenStructureIO.registerStructureComponent(StructureNetherVillageChurch.class, "nether_village_church");
        MapGenStructureIO.registerStructureComponent(StructureNetherVillageField1.class, "nether_village_field1");
        MapGenStructureIO.registerStructureComponent(StructureNetherVillageField2.class, "nether_village_field2");
        MapGenStructureIO.registerStructureComponent(StructureNetherVillageTorch.class, "nether_village_torch");
    }

    public static List<StructureNetherVillage.PieceWeight> getStructureVillageWeightedPieceList(Random random, int size)
    {
        List<StructureNetherVillage.PieceWeight> list = Lists.newArrayList();
        list.add(new StructureNetherVillage.PieceWeight(StructureNetherVillageHouse1.class, 20, MathHelper.getInt(random, size, 2 + size)));
        list.add(new StructureNetherVillage.PieceWeight(StructureNetherVillageHouse2.class, 15, MathHelper.getInt(random, 0, 1 + size)));
        list.add(new StructureNetherVillage.PieceWeight(StructureNetherVillageHouse3.class, 8, MathHelper.getInt(random, size, 3 + size * 2)));
        list.add(new StructureNetherVillage.PieceWeight(StructureNetherVillageHouse4.class, 4, MathHelper.getInt(random, 2 + size, 4 + size * 2)));
        list.add(new StructureNetherVillage.PieceWeight(StructureNetherVillageWoodHut.class, 3, MathHelper.getInt(random, 2 + size, 5 + size * 3)));
        list.add(new StructureNetherVillage.PieceWeight(StructureNetherVillageHall.class, 15, MathHelper.getInt(random, size, 2 + size)));
        list.add(new StructureNetherVillage.PieceWeight(StructureNetherVillageChurch.class, 20, MathHelper.getInt(random, size, 1 + size)));
        list.add(new StructureNetherVillage.PieceWeight(StructureNetherVillageField1.class, 3, MathHelper.getInt(random, 1 + size, 4 + size)));
        list.add(new StructureNetherVillage.PieceWeight(StructureNetherVillageField2.class, 3, MathHelper.getInt(random, 2 + size, 4 + size * 2)));
        list.removeIf(pieceWeight -> (pieceWeight).villagePiecesLimit == 0);
        return list;
    }

    private static int updatePieceWeight(List<StructureNetherVillage.PieceWeight> pieceWeights)
    {
        boolean flag = false;
        int i = 0;

        for(StructureNetherVillage.PieceWeight pieceWeight : pieceWeights)
        {
            if(pieceWeight.villagePiecesLimit > 0 && pieceWeight.villagePiecesSpawned < pieceWeight.villagePiecesLimit)
            {
                flag = true;
            }

            i += pieceWeight.villagePieceWeight;
        }

        return flag ? i : -1;
    }

    private static StructureNetherVillage findAndCreateComponentFactory(StructureNetherVillageWell.Start start, StructureNetherVillage.PieceWeight pieceWeight, List<StructureComponent> structureComponents, Random rand, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, int componentType)
    {
        Class<? extends StructureNetherVillage> structureNetherVillageCls = pieceWeight.villagePieceClass;
        StructureNetherVillage structureNetherVillage = null;

        if(structureNetherVillageCls == StructureNetherVillageHouse4.class)
        {
            structureNetherVillage = StructureNetherVillageHouse4.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
        }
        else if(structureNetherVillageCls == StructureNetherVillageChurch.class)
        {
            structureNetherVillage = StructureNetherVillageChurch.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
        }
        else if(structureNetherVillageCls == StructureNetherVillageHouse1.class)
        {
            structureNetherVillage = StructureNetherVillageHouse1.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
        }
        else if(structureNetherVillageCls == StructureNetherVillageWoodHut.class)
        {
            structureNetherVillage = StructureNetherVillageWoodHut.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
        }
        else if(structureNetherVillageCls == StructureNetherVillageHall.class)
        {
            structureNetherVillage = StructureNetherVillageHall.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
        }
        else if(structureNetherVillageCls == StructureNetherVillageField1.class)
        {
            structureNetherVillage = StructureNetherVillageField1.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
        }
        else if(structureNetherVillageCls == StructureNetherVillageField2.class)
        {
            structureNetherVillage = StructureNetherVillageField2.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
        }
        else if(structureNetherVillageCls == StructureNetherVillageHouse2.class)
        {
            structureNetherVillage = StructureNetherVillageHouse2.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
        }
        else if(structureNetherVillageCls == StructureNetherVillageHouse3.class)
        {
            structureNetherVillage = StructureNetherVillageHouse3.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
        }

        return structureNetherVillage;
    }

    private static StructureNetherVillage generateComponent(StructureNetherVillageWell.Start start, List<StructureComponent> structureComponents, Random rand, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, int componentType)
    {
        int i = updatePieceWeight(start.pieceWeights);

        if(i <= 0)
        {
            return null;
        }
        else
        {
            int j = 0;

            while(j < 5)
            {
                ++j;
                int k = rand.nextInt(i);

                for(StructureNetherVillage.PieceWeight pieceWeight : start.pieceWeights)
                {
                    k -= pieceWeight.villagePieceWeight;

                    if(k < 0)
                    {
                        if(!pieceWeight.canSpawnMoreVillagePiecesOfType(componentType) || pieceWeight == start.structVillagePieceWeight && start.pieceWeights.size() > 1)
                        {
                            break;
                        }

                        StructureNetherVillage structurevillagepieces$village = findAndCreateComponentFactory(start, pieceWeight, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);

                        if(structurevillagepieces$village != null)
                        {
                            ++pieceWeight.villagePiecesSpawned;
                            start.structVillagePieceWeight = pieceWeight;

                            if(!pieceWeight.canSpawnMoreVillagePieces())
                            {
                                start.pieceWeights.remove(pieceWeight);
                            }

                            return structurevillagepieces$village;
                        }
                    }
                }
            }

            StructureBoundingBox boundingBox = StructureNetherVillageTorch.findPieceBox(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing);

            if(boundingBox != null)
            {
                return new StructureNetherVillageTorch(start, componentType, rand, boundingBox, facing);
            }
            else
            {
                return null;
            }
        }
    }

    private static StructureComponent generateAndAddComponent(StructureNetherVillageWell.Start start, List<StructureComponent> structureComponents, Random rand, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, int componentType)
    {
        if(componentType > 50)
        {
            return null;
        }
        else if(Math.abs(structureMinX - start.getBoundingBox().minX) <= 112 && Math.abs(structureMinZ - start.getBoundingBox().minZ) <= 112)
        {
            StructureComponent component = generateComponent(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType + 1);

            if(component != null)
            {
                structureComponents.add(component);
                start.pendingHouses.add(component);
                return component;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    public static StructureComponent generateAndAddRoadPiece(StructureNetherVillageWell.Start start, List<StructureComponent> structureComponents, Random rand, int minX, int minY, int minZ, EnumFacing facing, int componentType)
    {
        if(componentType > 3 + start.terrainType)
        {
            return null;
        }
        else if(Math.abs(minX - start.getBoundingBox().minX) <= 112 && Math.abs(minZ - start.getBoundingBox().minZ) <= 112)
        {
            StructureBoundingBox structureboundingbox = StructureNetherVillagePath.findPieceBox(start, structureComponents, rand, minX, minY, minZ, facing);

            if(structureboundingbox != null && structureboundingbox.minY > 10)
            {
                StructureComponent structurecomponent = new StructureNetherVillagePath(start, componentType, rand, structureboundingbox, facing);
                structureComponents.add(structurecomponent);
                start.pendingRoads.add(structurecomponent);
                return structurecomponent;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    protected void writeStructureToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setInteger("HorizontalPosition", this.averageGroundLvl);
        tagCompound.setInteger("PigtificateCount", this.villagersSpawned);
        tagCompound.setByte("Type", (byte) this.structureType);
        tagCompound.setBoolean("Zombie", this.isZombieInfested);
    }

    @Override
    protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
    {
        this.averageGroundLvl = tagCompound.getInteger("HorizontalPosition");
        this.villagersSpawned = tagCompound.getInteger("PigtificateCount");
        this.structureType = tagCompound.getByte("Type");

        if(tagCompound.getBoolean("Desert"))
        {
            this.structureType = 1;
        }

        this.isZombieInfested = tagCompound.getBoolean("Zombie");
    }

    @Override
    public abstract boolean addComponentParts(World world, Random rand, StructureBoundingBox boundingBox);

    protected StructureComponent getNextComponentNN(StructureNetherVillageWell.Start start, List<StructureComponent> structureComponents, Random rand, int minY, int minXZ)
    {
        EnumFacing facing = this.getCoordBaseMode();

        if(facing != null)
        {
            switch(facing)
            {
                case NORTH:
                default:
                    return generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX - 1, this.boundingBox.minY + minY, this.boundingBox.minZ + minXZ, EnumFacing.WEST, this.getComponentType());
                case SOUTH:
                    return generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX - 1, this.boundingBox.minY + minY, this.boundingBox.minZ + minXZ, EnumFacing.WEST, this.getComponentType());
                case WEST:
                    return generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX + minXZ, this.boundingBox.minY + minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                case EAST:
                    return generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX + minXZ, this.boundingBox.minY + minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
            }
        }
        else
        {
            return null;
        }
    }

    @Nullable
    protected StructureComponent getNextComponentPP(StructureNetherVillageWell.Start start, List<StructureComponent> structureComponents, Random rand, int minY, int minXZ)
    {
        EnumFacing enumfacing = this.getCoordBaseMode();

        if(enumfacing != null)
        {
            switch(enumfacing)
            {
                case NORTH:
                default:
                    return generateAndAddComponent(start, structureComponents, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + minY, this.boundingBox.minZ + minXZ, EnumFacing.EAST, this.getComponentType());
                case SOUTH:
                    return generateAndAddComponent(start, structureComponents, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + minY, this.boundingBox.minZ + minXZ, EnumFacing.EAST, this.getComponentType());
                case WEST:
                    return generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX + minXZ, this.boundingBox.minY + minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                case EAST:
                    return generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX + minXZ, this.boundingBox.minY + minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
            }
        }
        else
        {
            return null;
        }
    }

    protected int getAverageGroundLevel(World world, StructureBoundingBox boundingBox)
    {
        int i = 0;
        int j = 0;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for(int k = this.boundingBox.minZ; k <= this.boundingBox.maxZ; ++k)
        {
            for(int l = this.boundingBox.minX; l <= this.boundingBox.maxX; ++l)
            {
                mutableBlockPos.setPos(l, 32, k);

                if(boundingBox.isVecInside(mutableBlockPos))
                {
                    i += Math.max(WorldGenUtil.getSolidBlockBelow(world, mutableBlockPos, 80).getY(), world.getSeaLevel() + 1);
                    ++j;
                }
            }
        }

        if(j == 0)
        {
            return -1;
        }
        else
        {
            return i / j;
        }
    }

    protected static boolean canVillageGoDeeper(StructureBoundingBox boundingBox)
    {
        return boundingBox != null && boundingBox.minY > 10;
    }

    protected void spawnVillagers(World world, StructureBoundingBox boundingBox, int x, int y, int z, int count)
    {
        if(this.villagersSpawned < count)
        {
            for(int i = this.villagersSpawned; i < count; ++i)
            {
                int j = this.getXWithOffset(x + i, z);
                int k = this.getYWithOffset(y);
                int l = this.getZWithOffset(x + i, z);

                if(!boundingBox.isVecInside(new BlockPos(j, k, l)))
                {
                    break;
                }

                ++this.villagersSpawned;

                if(this.isZombieInfested)
                {
                    EntityPigZombie pigZombie = new EntityPigZombie(world);
                    pigZombie.setLocationAndAngles((double) j + 0.5D, (double) k, (double) l + 0.5D, 0.0F, 0.0F);
                    pigZombie.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(pigZombie)), null);
                    pigZombie.enablePersistence();
                    world.spawnEntity(pigZombie);
                }
                else
                {
                    EntityPigtificate pigtificate = new EntityPigtificate(world);
                    pigtificate.setLocationAndAngles((double) j + 0.5D, (double) k, (double) l + 0.5D, 0.0F, 0.0F);
                    world.spawnEntity(pigtificate);
                }
            }
        }
    }

    protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession)
    {
        return currentVillagerProfession;
    }

    protected IBlockState getBiomeSpecificBlockState(IBlockState state)
    {
        BiomeEvent.GetVillageBlockID event = new BiomeEvent.GetVillageBlockID(startPiece == null ? null : startPiece.biome, state);
        MinecraftForge.TERRAIN_GEN_BUS.post(event);

        if(event.getResult() == Event.Result.DENY)
        {
            return event.getReplacement();
        }
        if(this.structureType == 1)
        {
            if(state.getBlock() == Blocks.LOG || state.getBlock() == Blocks.LOG2)
            {
                return Blocks.SANDSTONE.getDefaultState();
            }

            if(state.getBlock() == Blocks.COBBLESTONE)
            {
                return Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.DEFAULT.getMetadata());
            }

            if(state.getBlock() == Blocks.PLANKS)
            {
                return Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata());
            }

            if(state.getBlock() == Blocks.OAK_STAIRS)
            {
                return Blocks.SANDSTONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING));
            }

            if(state.getBlock() == Blocks.STONE_STAIRS)
            {
                return Blocks.SANDSTONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING));
            }

            if(state.getBlock() == Blocks.GRAVEL)
            {
                return Blocks.SANDSTONE.getDefaultState();
            }
        }

        return state;
    }

    private BlockDoor biomeDoor()
    {
        switch(this.structureType)
        {
            case 2:
                return Blocks.ACACIA_DOOR;
            case 3:
                return Blocks.SPRUCE_DOOR;
            default:
                return Blocks.OAK_DOOR;
        }
    }

    protected void createVillageDoor(World world, StructureBoundingBox boundingBox, Random rand, int x, int y, int z, EnumFacing facing)
    {
        if(!this.isZombieInfested)
        {
            this.generateDoor(world, boundingBox, rand, x, y, z, EnumFacing.NORTH, this.biomeDoor());
        }
    }

    protected void placeTorch(World world, EnumFacing facing, int x, int y, int z, StructureBoundingBox boundingBox)
    {
        if(!this.isZombieInfested)
        {
            this.setBlockState(world, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, facing), x, y, z, boundingBox);
        }
    }

    protected void replaceAirAndLiquidDownwards(World world, IBlockState state, int x, int y, int z, StructureBoundingBox boundingBox)
    {
        IBlockState iblockstate = this.getBiomeSpecificBlockState(state);
        super.replaceAirAndLiquidDownwards(world, iblockstate, x, y, z, boundingBox);
    }

    protected void setStructureType(int structureTypeIn)
    {
        this.structureType = structureTypeIn;
    }

    public static class PieceWeight
    {
        public Class<? extends StructureNetherVillage> villagePieceClass;
        public final int villagePieceWeight;
        public int villagePiecesSpawned;
        public int villagePiecesLimit;

        public PieceWeight(Class<? extends StructureNetherVillage> p_i2098_1_, int p_i2098_2_, int p_i2098_3_)
        {
            this.villagePieceClass = p_i2098_1_;
            this.villagePieceWeight = p_i2098_2_;
            this.villagePiecesLimit = p_i2098_3_;
        }

        public boolean canSpawnMoreVillagePiecesOfType(int componentType)
        {
            return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
        }

        public boolean canSpawnMoreVillagePieces()
        {
            return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
        }
    }

    public abstract static class Road extends StructureNetherVillage
    {
        public Road()
        {
        }

        protected Road(StructureNetherVillageWell.Start start, int type)
        {
            super(start, type);
        }
    }
}

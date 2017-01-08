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

package nex.world.gen.structure;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;
import nex.NetherEx;

import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class NetherStructures
{
    public static class ArtifactTower extends Structure
    {
        private static final ResourceLocation TOWER = new ResourceLocation(NetherEx.MOD_ID + ":tower_artifact");

        public ArtifactTower()
        {

        }

        public ArtifactTower(Random rand, int x, int z, int xSize, int ySize, int zSize)
        {
            super(rand, x, 96, z, xSize, ySize, zSize);
        }

        @Override
        public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB)
        {
            BlockPos pos = new BlockPos(boundingBox.minX + 8, boundingBox.minY, boundingBox.minZ + 8);
            return generateStructure(world, rand, pos, TOWER, Blocks.STRUCTURE_VOID, 0.75F, false, true, LootTableList.CHESTS_NETHER_BRIDGE);
        }
    }

    public static class BlacksmithHut extends Structure
    {
        private static final ResourceLocation HUT = new ResourceLocation(NetherEx.MOD_ID + ":hut_blacksmith");

        public BlacksmithHut()
        {

        }

        public BlacksmithHut(Random rand, int x, int z, int xSize, int ySize, int zSize)
        {
            super(rand, x, 96, z, xSize, ySize, zSize);
        }

        @Override
        public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB)
        {
            BlockPos pos = new BlockPos(boundingBox.minX + 8, boundingBox.minY, boundingBox.minZ + 8);
            return generateStructure(world, rand, pos, HUT, Blocks.STRUCTURE_VOID, 0.75F, false, true, LootTableList.CHESTS_NETHER_BRIDGE);
        }
    }

    public static class ChiefHut extends Structure
    {
        private static final ResourceLocation HUT = new ResourceLocation(NetherEx.MOD_ID + ":hut_chief");

        public ChiefHut()
        {

        }

        public ChiefHut(Random rand, int x, int z, int xSize, int ySize, int zSize)
        {
            super(rand, x, 96, z, xSize, ySize, zSize);
        }

        @Override
        public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB)
        {
            BlockPos pos = new BlockPos(boundingBox.minX + 8, boundingBox.minY, boundingBox.minZ + 8);
            return generateStructure(world, rand, pos, HUT, Blocks.STRUCTURE_VOID, 0.75F, false, true, LootTableList.CHESTS_NETHER_BRIDGE);
        }
    }

    public static class PigmanHut extends Structure
    {
        private static final ResourceLocation HUT = new ResourceLocation(NetherEx.MOD_ID + ":hut_pigman_basic");

        public PigmanHut()
        {

        }

        public PigmanHut(Random rand, int x, int z, int xSize, int ySize, int zSize)
        {
            super(rand, x, 96, z, xSize, ySize, zSize);
        }

        @Override
        public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB)
        {
            BlockPos pos = new BlockPos(boundingBox.minX + 8, boundingBox.minY, boundingBox.minZ + 8);
            return generateStructure(world, rand, pos, HUT, Blocks.STRUCTURE_VOID, 0.75F, false, true, LootTableList.CHESTS_NETHER_BRIDGE);
        }
    }

    public static class AncientAltar extends Structure
    {
        private static final ResourceLocation INTACT = new ResourceLocation(NetherEx.MOD_ID + ":altar_ancient_intact");
        private static final ResourceLocation DESTROYED = new ResourceLocation(NetherEx.MOD_ID + ":altar_ancient_destroyed");
        private static final ResourceLocation RUINED = new ResourceLocation(NetherEx.MOD_ID + ":altar_ancient_ruined");

        private int altar = 0;

        public AncientAltar()
        {

        }

        public AncientAltar(int altarIn, Random rand, int x, int z, int xSize, int ySize, int zSize)
        {
            super(rand, x, 96, z, xSize, ySize, zSize);

            altar = altarIn;
        }

        @Override
        public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB)
        {
            BlockPos pos = new BlockPos(boundingBox.minX + 8, boundingBox.minY, boundingBox.minZ + 8);
            ResourceLocation ALTAR = altar == 0 ? INTACT : altar == 1 ? DESTROYED : altar == 3 ? RUINED : INTACT;
            return generateStructure(world, rand, pos, ALTAR, Blocks.AIR, 0.25F, false, true, null);
        }
    }

    public static class AncientThrone extends Structure
    {
        private static final ResourceLocation THRONE = new ResourceLocation(NetherEx.MOD_ID + ":throne_ancient");

        public AncientThrone()
        {

        }

        public AncientThrone(Random rand, int x, int z, int xSize, int ySize, int zSize)
        {
            super(rand, x, 96, z, xSize, ySize, zSize);
        }

        @Override
        public boolean addComponentParts(World world, Random rand, StructureBoundingBox structureBB)
        {
            BlockPos pos = new BlockPos(boundingBox.minX + 8, boundingBox.minY, boundingBox.minZ + 8);
            return generateStructure(world, rand, pos, THRONE, Blocks.AIR, 0.25F, false, true, null);
        }
    }

    abstract static class Structure extends StructureComponent
    {
        Mirror[] mirrors = Mirror.values();
        Rotation[] rotations = Rotation.values();

        public int xSize;
        public int ySize;
        public int zSize;

        public Structure(Random rand, int x, int y, int z, int xSizeIn, int ySizeIn, int zSizeIn)
        {
            super(0);

            xSize = xSizeIn;
            ySize = ySizeIn;
            zSize = zSizeIn;
            setCoordBaseMode(EnumFacing.Plane.HORIZONTAL.random(rand));

            if(getCoordBaseMode().getAxis() == EnumFacing.Axis.Z)
            {
                boundingBox = new StructureBoundingBox(x, y, z, x + xSizeIn - 1, y + ySizeIn - 1, z + zSizeIn - 1);
            }
            else
            {
                boundingBox = new StructureBoundingBox(x, y, z, x + zSizeIn - 1, y + ySizeIn - 1, z + xSizeIn - 1);
            }
        }

        protected Structure()
        {
        }

        @Override
        protected void writeStructureToNBT(NBTTagCompound compound)
        {
            compound.setInteger("XSize", xSize);
            compound.setInteger("YSize", ySize);
            compound.setInteger("ZSize", zSize);
        }

        @Override
        protected void readStructureFromNBT(NBTTagCompound compound, TemplateManager manager)
        {
            xSize = compound.getInteger("XSize");
            ySize = compound.getInteger("YSize");
            zSize = compound.getInteger("ZSize");
        }

        public boolean generateStructure(World world, Random rand, BlockPos pos, ResourceLocation templateLocation, Block replacedBlock, float percentage, boolean isBuried, boolean hasLoot, ResourceLocation lootTable)
        {
            while(world.isAirBlock(pos) && pos.getY() > 32)
            {
                int solidBlocks = 0;

                for(int posX = -1; posX < 2; posX++)
                {
                    for(int posZ = -1; posZ < 2; posZ++)
                    {
                        BlockPos newPos = pos.add(posX, 0, posZ);

                        if(world.getBlockState(newPos).getBlock().isBlockSolid(world, newPos, EnumFacing.DOWN))
                        {
                            solidBlocks++;
                        }
                    }
                }

                if(solidBlocks == 9)
                {
                    break;
                }

                pos = pos.down();
            }

            Mirror mirror = mirrors[rand.nextInt(mirrors.length)];
            Rotation rotation = rotations[rand.nextInt(rotations.length)];
            PlacementSettings placementSettings = new PlacementSettings().setMirror(mirror).setRotation(rotation).setReplacedBlock(replacedBlock);
            MinecraftServer minecraftServer = world.getMinecraftServer();
            TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
            Template template = templateManager.getTemplate(minecraftServer, templateLocation);
            BlockPos structureSize = Template.transformedBlockPos(placementSettings.copy(), template.getSize());
            float airAmount = 0;
            float blockAmount = MathHelper.abs((structureSize.getX() + 2) * (structureSize.getY() + 1) * (structureSize.getZ() + 2));

            for(int posZ = -1; posZ < structureSize.getZ() + 1; posZ++)
            {
                for(int posX = -1; posX < structureSize.getX() + 1; posX++)
                {
                    for(int posY = 0; posY < structureSize.getY() + 1; posY++)
                    {
                        BlockPos newPos = pos.add(-(posX / 2), posY + 1, -(posZ / 2));

                        if(world.isAirBlock(newPos))
                        {
                            airAmount += 1.0F;
                        }
                    }
                }
            }

            BlockPos newPos = pos.add(-(structureSize.getX() / 2), 1, -(structureSize.getZ() / 2));
            boolean canSpawn = !world.isAirBlock(newPos) || !world.isAirBlock(newPos.down());
            float airBlockRatio = isBuried ? MathHelper.abs(blockAmount) / MathHelper.abs(airAmount) : MathHelper.abs(airAmount) / MathHelper.abs(blockAmount);

            if(canSpawn && airBlockRatio >= percentage)
            {
                template.addBlocksToWorldChunk(world, newPos, placementSettings.copy());

                if(hasLoot)
                {
                    setChestContents(world, rand, newPos, structureSize, lootTable);
                }
                return true;
            }

            return false;
        }

        protected static void setChestContents(World world, Random rand, BlockPos pos, BlockPos structureSize, ResourceLocation lootTableList)
        {
            Iterable<BlockPos> blockPositions = BlockPos.getAllInBox(pos, new BlockPos(pos.getX() + structureSize.getX(), pos.getY() + structureSize.getY(), pos.getZ() + structureSize.getZ()));

            for(BlockPos newPos : blockPositions)
            {
                Block block = world.getBlockState(newPos).getBlock();

                if(block instanceof BlockChest)
                {
                    TileEntityChest chest = (TileEntityChest) world.getTileEntity(newPos);
                    chest.setLootTable(lootTableList, rand.nextLong());
                }
            }
        }
    }
}

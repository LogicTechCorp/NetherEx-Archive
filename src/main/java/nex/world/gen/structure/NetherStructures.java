/*
 * Copyright (C) 2016.  LogicTechCorp
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

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;
import nex.NetherEx;

import java.util.Map;
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
            if(!offsetToEvenGround(world, structureBB, 0))
            {
                return false;
            }
            else
            {
                BlockPos pos = new BlockPos(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
                MinecraftServer minecraftServer = world.getMinecraftServer();
                TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
                Template template = templateManager.getTemplate(minecraftServer, TOWER);
                PlacementSettings placementSettings = new PlacementSettings().setMirror(mirrors[rand.nextInt(mirrors.length)]).setRotation(rotations[rand.nextInt(rotations.length)]).setBoundingBox(boundingBox);
                template.addBlocksToWorldChunk(world, pos, placementSettings.copy());
                setChestContents(world, rand, pos, template, placementSettings.copy(), LootTableList.CHESTS_NETHER_BRIDGE);
                return true;
            }
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
            if(!offsetToEvenGround(world, structureBB, 0))
            {
                return false;
            }
            else
            {
                BlockPos pos = new BlockPos(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
                MinecraftServer minecraftServer = world.getMinecraftServer();
                TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
                Template template = templateManager.getTemplate(minecraftServer, HUT);
                PlacementSettings placementSettings = new PlacementSettings().setMirror(mirrors[rand.nextInt(mirrors.length)]).setRotation(rotations[rand.nextInt(rotations.length)]).setBoundingBox(boundingBox);
                template.addBlocksToWorldChunk(world, pos, placementSettings.copy());
                setChestContents(world, rand, pos, template, placementSettings.copy(), LootTableList.CHESTS_NETHER_BRIDGE);
                return true;
            }
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
            if(!offsetToEvenGround(world, structureBB, 0))
            {
                return false;
            }
            else
            {
                BlockPos pos = new BlockPos(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
                MinecraftServer minecraftServer = world.getMinecraftServer();
                TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
                Template template = templateManager.getTemplate(minecraftServer, HUT);
                PlacementSettings placementSettings = new PlacementSettings().setMirror(mirrors[rand.nextInt(mirrors.length)]).setRotation(rotations[rand.nextInt(rotations.length)]).setBoundingBox(boundingBox);
                template.addBlocksToWorldChunk(world, pos, placementSettings.copy());
                setChestContents(world, rand, pos, template, placementSettings.copy(), LootTableList.CHESTS_NETHER_BRIDGE);
                return true;
            }
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
            if(!offsetToEvenGround(world, structureBB, 0))
            {
                return false;
            }
            else
            {
                BlockPos pos = new BlockPos(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
                MinecraftServer minecraftServer = world.getMinecraftServer();
                TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
                Template template = templateManager.getTemplate(minecraftServer, HUT);
                PlacementSettings placementSettings = new PlacementSettings().setMirror(mirrors[rand.nextInt(mirrors.length)]).setRotation(rotations[rand.nextInt(rotations.length)]).setBoundingBox(boundingBox);
                template.addBlocksToWorldChunk(world, pos, placementSettings.copy());
                setChestContents(world, rand, pos, template, placementSettings.copy(), LootTableList.CHESTS_NETHER_BRIDGE);
                return true;
            }
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
            if(!offsetToEvenGround(world, structureBB, 0))
            {
                return false;
            }
            else
            {
                BlockPos pos = new BlockPos(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
                MinecraftServer minecraftServer = world.getMinecraftServer();
                TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
                Template template = templateManager.getTemplate(minecraftServer, altar == 0 ? INTACT : altar == 1 ? DESTROYED : altar == 3 ? RUINED : INTACT);
                PlacementSettings placementSettings = new PlacementSettings().setMirror(mirrors[rand.nextInt(mirrors.length)]).setRotation(rotations[rand.nextInt(rotations.length)]).setBoundingBox(boundingBox).setReplacedBlock(Blocks.AIR);
                template.addBlocksToWorldChunk(world, pos, placementSettings.copy());
                return true;
            }
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
            if(!offsetToEvenGround(world, structureBB, 0))
            {
                return false;
            }
            else
            {
                BlockPos pos = new BlockPos(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
                MinecraftServer minecraftServer = world.getMinecraftServer();
                TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
                Template template = templateManager.getTemplate(minecraftServer, THRONE);
                PlacementSettings placementSettings = new PlacementSettings().setMirror(mirrors[rand.nextInt(mirrors.length)]).setRotation(rotations[rand.nextInt(rotations.length)]).setBoundingBox(boundingBox).setReplacedBlock(Blocks.AIR);
                template.addBlocksToWorldChunk(world, pos, placementSettings.copy());
                return true;
            }
        }
    }

    abstract static class Structure extends StructureComponent
    {
        Mirror[] mirrors = Mirror.values();
        Rotation[] rotations = Rotation.values();

        public int xSize;
        public int ySize;
        public int zSize;
        public int hPos = -1;

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
            compound.setInteger("HPos", hPos);
        }

        @Override
        protected void readStructureFromNBT(NBTTagCompound compound, TemplateManager manager)
        {
            xSize = compound.getInteger("XSize");
            ySize = compound.getInteger("YSize");
            zSize = compound.getInteger("ZSize");
            hPos = compound.getInteger("HPos");
        }

        protected boolean offsetToEvenGround(World world, StructureBoundingBox structureBB, int yOffset)
        {
            if(hPos >= 0)
            {
                return true;
            }
            else
            {
                int i = 0;
                int j = 0;
                BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

                for(int k = boundingBox.minZ; k <= boundingBox.maxZ; ++k)
                {
                    for(int l = boundingBox.minX; l <= boundingBox.maxX; ++l)
                    {
                        pos.setPos(l, boundingBox.maxY, k);

                        if(structureBB.isVecInside(pos))
                        {
                            Tuple<BlockPos, Boolean> tuple = getSuitableGroundPos(world, pos);

                            if(!tuple.getSecond())
                            {
                                return false;
                            }

                            i += tuple.getFirst().getY();
                            j++;
                        }
                    }
                }

                if(j == 0)
                {
                    return false;
                }
                else
                {
                    hPos = i / j;
                    boundingBox.offset(0, hPos - boundingBox.minY + yOffset, 0);
                    return true;
                }
            }
        }

        public Tuple<BlockPos, Boolean> getSuitableGroundPos(World world, BlockPos pos)
        {
            boolean isSuitable = false;

            while(!isSuitable && pos.getY() > 32)
            {
                if(world.getBlockState(pos).getBlock().isBlockSolid(world, pos, EnumFacing.DOWN) && world.isAirBlock(pos.up()))
                {
                    isSuitable = true;
                }

                pos = pos.down();
            }

            return new Tuple<>(pos.up(), isSuitable);
        }
    }

    protected static void setChestContents(World world, Random rand, BlockPos pos, Template template, PlacementSettings placementSettings, ResourceLocation lootTableList)
    {
        Map<BlockPos, String> map = template.getDataBlocks(pos, placementSettings);

        for(Map.Entry<BlockPos, String> entry : map.entrySet())
        {
            if("chest".equals(entry.getValue()))
            {
                BlockPos blockPos = entry.getKey();
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
                TileEntity tileEntity = world.getTileEntity(blockPos.down());

                if(tileEntity instanceof TileEntityChest)
                {
                    ((TileEntityChest) tileEntity).setLootTable(lootTableList, rand.nextLong());
                }
            }
        }
    }
}

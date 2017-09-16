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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;
import nex.NetherEx;
import nex.handler.ConfigHandler;
import nex.util.WorldGenUtil;
import nex.world.gen.structure.StructureComponentNetherEx;

import java.util.List;
import java.util.Random;

public abstract class ComponentNetherVillage extends StructureComponentNetherEx
{
    protected int averageGroundLvl = -1;
    private int villagersSpawned;
    protected int componentType;
    protected boolean isZombieInfested;
    protected ComponentWell.Controller controller;

    public ComponentNetherVillage()
    {
    }

    protected ComponentNetherVillage(ComponentWell.Controller controllerIn, int componentTypeIn, EnumFacing facing)
    {
        super(componentTypeIn);

        if(controllerIn != null)
        {
            componentType = controllerIn.componentType;
            isZombieInfested = controllerIn.isZombieInfested;
            controller = controllerIn;
        }

        setCoordBaseMode(facing);
    }

    public static void registerPieces()
    {
        MapGenStructureIO.registerStructureComponent(ComponentWell.Controller.class, NetherEx.MOD_ID + ":village_nether_controller");
        MapGenStructureIO.registerStructureComponent(ComponentWell.class, NetherEx.MOD_ID + ":village_nether_well");
        MapGenStructureIO.registerStructureComponent(ComponentPath.class, NetherEx.MOD_ID + ":village_nether_path");
        MapGenStructureIO.registerStructureComponent(ComponentLampPost.class, NetherEx.MOD_ID + ":village_nether_lamp_post");
        MapGenStructureIO.registerStructureComponent(ComponentChief.class, NetherEx.MOD_ID + ":village_nether_structure_chief");
        MapGenStructureIO.registerStructureComponent(ComponentHunter.class, NetherEx.MOD_ID + ":village_nether_structure_hunter");
        MapGenStructureIO.registerStructureComponent(ComponentGatherer.class, NetherEx.MOD_ID + ":village_nether_structure_gatherer");
        MapGenStructureIO.registerStructureComponent(ComponentScavenger.class, NetherEx.MOD_ID + ":village_nether_structure_scavenger");
        MapGenStructureIO.registerStructureComponent(ComponentArmorsmith.class, NetherEx.MOD_ID + ":village_nether_structure_armorsmith");
        MapGenStructureIO.registerStructureComponent(ComponentToolsmith.class, NetherEx.MOD_ID + ":village_nether_structure_toolsmith");
        MapGenStructureIO.registerStructureComponent(ComponentEnchanter.class, NetherEx.MOD_ID + ":village_nether_structure_enchanter");
        MapGenStructureIO.registerStructureComponent(ComponentBrewer.class, NetherEx.MOD_ID + ":village_nether_structure_brewer");
    }

    public static List<Piece> getStructureVillagePieceList(Random rand, int size)
    {
        List<Piece> list = Lists.newArrayList();
        list.add(new Piece(ComponentChief.class, 5, 1));
        list.add(new Piece(ComponentHunter.class, 20, MathHelper.getInt(rand, size + 1, (size * 2) + 4)));
        list.add(new Piece(ComponentGatherer.class, 20, MathHelper.getInt(rand, size + 1, (size * 2) + 4)));
        list.add(new Piece(ComponentScavenger.class, 20, MathHelper.getInt(rand, size + 1, (size * 2) + 4)));
        list.add(new Piece(ComponentArmorsmith.class, 15, MathHelper.getInt(rand, size + 1, (size * 2) + 4)));
        list.add(new Piece(ComponentToolsmith.class, 15, MathHelper.getInt(rand, size + 1, (size * 2) + 4)));
        list.add(new Piece(ComponentEnchanter.class, 10, MathHelper.getInt(rand, size + 1, (size * 2) + 4)));
        list.add(new Piece(ComponentBrewer.class, 20, MathHelper.getInt(rand, size + 1, (size * 2) + 4)));
        list.removeIf(piece -> (piece).getSpawnLimit() == 0);
        return list;
    }

    private static int updatePieceWeight(List<Piece> pieces)
    {
        boolean flag = false;
        int i = 0;

        for(Piece piece : pieces)
        {
            if(piece.getSpawnLimit() > 0 && piece.getAmountSpawned() < piece.getSpawnLimit())
            {
                flag = true;
            }

            i += piece.getWeight();
        }

        return flag ? i : -1;
    }

    private static ComponentNetherVillage findAndCreateComponentFactory(ComponentWell.Controller controller, Piece piece, List<StructureComponent> components, Random rand, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, int componentType)
    {
        Class<? extends ComponentNetherVillage> componentCls = piece.getCls();

        if(componentCls == ComponentChief.class)
        {
            if(ConfigHandler.biome.hell.structure.netherVillage.chiefStructureTemplates != null && ConfigHandler.biome.hell.structure.netherVillage.chiefStructureTemplates.length > 0)
            {
                ComponentChief componentChief = new ComponentChief(controller, componentType, ConfigHandler.biome.hell.structure.netherVillage.chiefStructureTemplates, structureMinX, structureMinY, structureMinZ, facing, rand);
                StructureBoundingBox boundingBox = componentChief.getBoundingBox();
                return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(components, boundingBox) == null ? componentChief : null;
            }
        }
        else if(componentCls == ComponentHunter.class)
        {
            if(ConfigHandler.biome.hell.structure.netherVillage.hunterStructureTemplates != null && ConfigHandler.biome.hell.structure.netherVillage.hunterStructureTemplates.length > 0)
            {
                ComponentHunter componentHunter = new ComponentHunter(controller, componentType, ConfigHandler.biome.hell.structure.netherVillage.hunterStructureTemplates, structureMinX, structureMinY, structureMinZ, facing, rand);
                StructureBoundingBox boundingBox = componentHunter.getBoundingBox();
                return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(components, boundingBox) == null ? componentHunter : null;
            }
        }
        else if(componentCls == ComponentGatherer.class)
        {
            if(ConfigHandler.biome.hell.structure.netherVillage.gathererStructureTemplates != null && ConfigHandler.biome.hell.structure.netherVillage.gathererStructureTemplates.length > 0)
            {
                ComponentGatherer componentGatherer = new ComponentGatherer(controller, componentType, ConfigHandler.biome.hell.structure.netherVillage.gathererStructureTemplates, structureMinX, structureMinY, structureMinZ, facing, rand);
                StructureBoundingBox boundingBox = componentGatherer.getBoundingBox();
                return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(components, boundingBox) == null ? componentGatherer : null;
            }
        }
        else if(componentCls == ComponentScavenger.class)
        {
            if(ConfigHandler.biome.hell.structure.netherVillage.scavengerStructureTemplates != null && ConfigHandler.biome.hell.structure.netherVillage.scavengerStructureTemplates.length > 0)
            {
                ComponentScavenger componentScavenger = new ComponentScavenger(controller, componentType, ConfigHandler.biome.hell.structure.netherVillage.scavengerStructureTemplates, structureMinX, structureMinY, structureMinZ, facing, rand);
                StructureBoundingBox boundingBox = componentScavenger.getBoundingBox();
                return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(components, boundingBox) == null ? componentScavenger : null;
            }
        }
        else if(componentCls == ComponentArmorsmith.class)
        {
            if(ConfigHandler.biome.hell.structure.netherVillage.armorsmithStructureTemplates != null && ConfigHandler.biome.hell.structure.netherVillage.armorsmithStructureTemplates.length > 0)
            {
                ComponentArmorsmith componentArmorsmith = new ComponentArmorsmith(controller, componentType, ConfigHandler.biome.hell.structure.netherVillage.armorsmithStructureTemplates, structureMinX, structureMinY, structureMinZ, facing, rand);
                StructureBoundingBox boundingBox = componentArmorsmith.getBoundingBox();
                return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(components, boundingBox) == null ? componentArmorsmith : null;
            }

        }
        else if(componentCls == ComponentToolsmith.class)
        {
            if(ConfigHandler.biome.hell.structure.netherVillage.toolsmithStructureTemplates != null && ConfigHandler.biome.hell.structure.netherVillage.toolsmithStructureTemplates.length > 0)
            {
                ComponentToolsmith componentToolsmith = new ComponentToolsmith(controller, componentType, ConfigHandler.biome.hell.structure.netherVillage.toolsmithStructureTemplates, structureMinX, structureMinY, structureMinZ, facing, rand);
                StructureBoundingBox boundingBox = componentToolsmith.getBoundingBox();
                return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(components, boundingBox) == null ? componentToolsmith : null;
            }
        }
        else if(componentCls == ComponentEnchanter.class)
        {
            if(ConfigHandler.biome.hell.structure.netherVillage.enchanterStructureTemplates != null && ConfigHandler.biome.hell.structure.netherVillage.enchanterStructureTemplates.length > 0)
            {
                ComponentEnchanter componentEnchanter = new ComponentEnchanter(controller, componentType, ConfigHandler.biome.hell.structure.netherVillage.enchanterStructureTemplates, structureMinX, structureMinY, structureMinZ, facing, rand);
                StructureBoundingBox boundingBox = componentEnchanter.getBoundingBox();
                return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(components, boundingBox) == null ? componentEnchanter : null;
            }
        }
        else if(componentCls == ComponentBrewer.class)
        {
            if(ConfigHandler.biome.hell.structure.netherVillage.brewerStructureTemplates != null && ConfigHandler.biome.hell.structure.netherVillage.brewerStructureTemplates.length > 0)
            {
                ComponentBrewer componentBrewer = new ComponentBrewer(controller, componentType, ConfigHandler.biome.hell.structure.netherVillage.brewerStructureTemplates, structureMinX, structureMinY, structureMinZ, facing, rand);
                StructureBoundingBox boundingBox = componentBrewer.getBoundingBox();
                return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(components, boundingBox) == null ? componentBrewer : null;
            }
        }

        return null;
    }

    private static ComponentNetherVillage generateComponent(ComponentWell.Controller controller, List<StructureComponent> components, Random rand, int minX, int minY, int minZ, EnumFacing facing, int componentType)
    {
        int i = updatePieceWeight(controller.getPieces());

        if(i <= 0)
        {
            return null;
        }
        else
        {
            int j = 0;

            while(j < 5)
            {
                j++;
                int k = rand.nextInt(i);

                for(Piece piece : controller.getPieces())
                {
                    k -= piece.getWeight();

                    if(k < 0)
                    {
                        if(!piece.canSpawnMoreVillagePieces() || piece == controller.getPiece() && controller.getPieces().size() > 1)
                        {
                            break;
                        }

                        ComponentNetherVillage componentNetherVillage = findAndCreateComponentFactory(controller, piece, components, rand, minX, minY, minZ, facing, componentType);

                        if(componentNetherVillage != null)
                        {
                            piece.incrementAmountSpawned();
                            controller.setPiece(piece);

                            if(!piece.canSpawnMoreVillagePieces())
                            {
                                controller.getPieces().remove(piece);
                            }

                            return componentNetherVillage;
                        }
                    }
                }
            }

            StructureBoundingBox boundingBox = ComponentLampPost.findPieceBox(controller, components, rand, minX, minY, minZ, facing);

            if(boundingBox != null)
            {
                return new ComponentLampPost(controller, componentType, rand, boundingBox, facing);
            }
            else
            {
                return null;
            }
        }
    }

    private static StructureComponent generateAndAddComponent(ComponentWell.Controller controller, List<StructureComponent> components, Random rand, int minX, int minY, int minZ, EnumFacing facing, int componentType)
    {
        if(componentType > 50)
        {
            return null;
        }
        else if(Math.abs(minX - controller.getBoundingBox().minX) <= 112 && Math.abs(minZ - controller.getBoundingBox().minZ) <= 112)
        {
            StructureComponent component = generateComponent(controller, components, rand, minX, minY, minZ, facing, componentType + 1);

            if(component != null)
            {
                components.add(component);
                controller.getPendingHouses().add(component);
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

    public static StructureComponent generateAndAddRoadPiece(ComponentWell.Controller controller, List<StructureComponent> components, Random rand, int minX, int minY, int minZ, EnumFacing facing, int componentType)
    {
        if(componentType > 3 + controller.getVillageSize())
        {
            return null;
        }
        else if(Math.abs(minX - controller.getBoundingBox().minX) <= 112 && Math.abs(minZ - controller.getBoundingBox().minZ) <= 112)
        {
            StructureBoundingBox boundingBox = ComponentPath.findPieceBox(controller, components, rand, minX, minY, minZ, facing);

            if(boundingBox != null && boundingBox.minY > 10)
            {
                StructureComponent structurecomponent = new ComponentPath(controller, componentType, rand, boundingBox, facing);
                components.add(structurecomponent);
                controller.getPendingRoads().add(structurecomponent);
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
        tagCompound.setInteger("HorizontalPosition", averageGroundLvl);
        tagCompound.setInteger("PigtificateCount", villagersSpawned);
        tagCompound.setInteger("Type", componentType);
        tagCompound.setBoolean("Zombie", isZombieInfested);
    }

    @Override
    protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
    {
        averageGroundLvl = tagCompound.getInteger("HorizontalPosition");
        villagersSpawned = tagCompound.getInteger("PigtificateCount");
        componentType = tagCompound.getInteger("Type");
        isZombieInfested = tagCompound.getBoolean("Zombie");
    }

    @Override
    public abstract boolean addComponentParts(World world, Random rand, StructureBoundingBox boundingBoxIn);

    protected StructureComponent getNextComponentNN(ComponentWell.Controller controller, List<StructureComponent> components, Random rand, int minY, int minXZ)
    {
        EnumFacing facing = getCoordBaseMode();

        if(facing != null)
        {
            switch(facing)
            {
                case NORTH:
                default:
                    return generateAndAddComponent(controller, components, rand, boundingBox.minX - 1, boundingBox.minY + minY, boundingBox.minZ + minXZ, EnumFacing.WEST, getComponentType());
                case SOUTH:
                    return generateAndAddComponent(controller, components, rand, boundingBox.minX - 1, boundingBox.minY + minY, boundingBox.minZ + minXZ, EnumFacing.WEST, getComponentType());
                case WEST:
                    return generateAndAddComponent(controller, components, rand, boundingBox.minX + minXZ, boundingBox.minY + minY, boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
                case EAST:
                    return generateAndAddComponent(controller, components, rand, boundingBox.minX + minXZ, boundingBox.minY + minY, boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
            }
        }
        else
        {
            return null;
        }
    }

    protected StructureComponent getNextComponentPP(ComponentWell.Controller controller, List<StructureComponent> components, Random rand, int minY, int minXZ)
    {
        EnumFacing facing = getCoordBaseMode();

        if(facing != null)
        {
            switch(facing)
            {
                case NORTH:
                default:
                    return generateAndAddComponent(controller, components, rand, boundingBox.maxX + 1, boundingBox.minY + minY, boundingBox.minZ + minXZ, EnumFacing.EAST, getComponentType());
                case SOUTH:
                    return generateAndAddComponent(controller, components, rand, boundingBox.maxX + 1, boundingBox.minY + minY, boundingBox.minZ + minXZ, EnumFacing.EAST, getComponentType());
                case WEST:
                    return generateAndAddComponent(controller, components, rand, boundingBox.minX + minXZ, boundingBox.minY + minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
                case EAST:
                    return generateAndAddComponent(controller, components, rand, boundingBox.minX + minXZ, boundingBox.minY + minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
            }
        }
        else
        {
            return null;
        }
    }

    protected int findAverageGroundLevel(World world, StructureBoundingBox boundingBoxIn)
    {
        int i = 0;
        int j = 0;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for(int k = boundingBox.minZ; k <= boundingBox.maxZ; ++k)
        {
            for(int l = boundingBox.minX; l <= boundingBox.maxX; ++l)
            {
                mutableBlockPos.setPos(l, 32, k);

                if(boundingBoxIn.isVecInside(mutableBlockPos))
                {
                    i += Math.max(WorldGenUtil.getSolidBlockBelow(world, mutableBlockPos, 125).getY(), world.getSeaLevel() + 1);
                    j++;
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

    private static boolean canVillageGoDeeper(StructureBoundingBox boundingBoxIn)
    {
        return boundingBoxIn != null && boundingBoxIn.minY > 10;
    }

    protected void replaceAirAndLiquidDownwards(World world, IBlockState state, int x, int y, int z, StructureBoundingBox boundingBox)
    {
        super.replaceAirAndLiquidDownwards(world, state, x, y, z, boundingBox);
    }

    protected void setStructureType(int componentTypeIn)
    {
        componentType = componentTypeIn;
    }

    public static class Piece
    {
        private Class<? extends ComponentNetherVillage> cls;
        private final int weight;
        private int amountSpawned;
        private int spawnLimit;

        public Piece(Class<? extends ComponentNetherVillage> clsIn, int weightIn, int spawnLimitIn)
        {
            cls = clsIn;
            weight = weightIn;
            spawnLimit = spawnLimitIn;
        }

        public boolean canSpawnMoreVillagePieces()
        {
            return spawnLimit == 0 || amountSpawned < spawnLimit;
        }

        public Class<? extends ComponentNetherVillage> getCls()
        {
            return cls;
        }

        public int getWeight()
        {
            return weight;
        }

        public int getAmountSpawned()
        {
            return amountSpawned;
        }

        public int getSpawnLimit()
        {
            return spawnLimit;
        }

        public void incrementAmountSpawned()
        {
            amountSpawned++;
        }
    }

    public abstract static class Road extends ComponentNetherVillage
    {
        public Road()
        {
        }

        protected Road(ComponentWell.Controller controller, int componentType, EnumFacing facing)
        {
            super(controller, componentType, facing);
        }
    }
}

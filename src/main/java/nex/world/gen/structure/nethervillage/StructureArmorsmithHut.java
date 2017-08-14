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

import com.google.common.collect.Maps;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import nex.NetherEx;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class StructureArmorsmithHut extends StructureNetherVillage
{
    private ResourceLocation structure;
    private BlockPos structureSize;
    private static final Map<ResourceLocation, BlockPos> STRUCTURES = Maps.newHashMap();

    static
    {
        STRUCTURES.put(new ResourceLocation(NetherEx.MOD_ID + ":village_nether_hell_hut_armorsmith"), new BlockPos(9, 5, 9));
        STRUCTURES.put(new ResourceLocation(NetherEx.MOD_ID + ":village_nether_hell_hut_armorsmith_variant"), new BlockPos(11, 5, 8));
    }

    public StructureArmorsmithHut()
    {
    }

    private StructureArmorsmithHut(StructureNetherVillageWell.Controller controller, int componentType, ResourceLocation structureIn, BlockPos structureSizeIn, Random rand, StructureBoundingBox boundingBoxIn, EnumFacing facing)
    {
        super(controller, componentType);

        setCoordBaseMode(facing);
        boundingBox = boundingBoxIn;
        structure = structureIn;
        structureSize = structureSizeIn;
    }

    public static StructureArmorsmithHut createComponent(StructureNetherVillageWell.Controller controller, List<StructureComponent> components, Random rand, int minX, int minY, int minZ, EnumFacing facing, int componentType)
    {
        ResourceLocation structure = (ResourceLocation) STRUCTURES.keySet().toArray()[rand.nextInt(STRUCTURES.size())];
        BlockPos structureSize = STRUCTURES.get(structure);
        StructureBoundingBox boundingBox = StructureBoundingBox.getComponentToAddBoundingBox(minX, minY, minZ, 0, 0, 0, structureSize.getX(), structureSize.getY(), structureSize.getZ(), facing);

        return canVillageGoDeeper(boundingBox) && StructureComponent.findIntersecting(components, boundingBox) == null ? new StructureArmorsmithHut(controller, componentType, structure, structureSize, rand, boundingBox, facing) : null;
    }

    @Override
    public boolean addComponentParts(World world, Random rand, StructureBoundingBox boundingBoxIn)
    {
        structureSize = Template.transformedBlockPos(new PlacementSettings().setRotation(getRotation()).setMirror(getMirror()), structureSize);

        if(averageGroundLvl < 0)
        {
            averageGroundLvl = findAverageGroundLevel(world, boundingBoxIn);

            if(averageGroundLvl < 0)
            {
                return true;
            }

            StructureBoundingBox fakeBoundingBox = new StructureBoundingBox(boundingBox);
            fakeBoundingBox.offset(0, averageGroundLvl - boundingBox.maxY + structureSize.getY() - 1, 0);

            for(int x = 0; x < structureSize.getX(); x++)
            {
                for(int z = 0; z < structureSize.getZ(); z++)
                {
                    BlockPos pos = new BlockPos(getXWithOffset(x, z), fakeBoundingBox.minY - 1, getZWithOffset(x, z));

                    if(world.isAirBlock(pos))
                    {
                        return false;
                    }
                }
            }

            int solidBlocks = 0;

            for(int x = 0; x < structureSize.getX(); x++)
            {
                for(int z = 0; z < structureSize.getZ(); z++)
                {
                    for(int y = 0; y < structureSize.getY(); y++)
                    {
                        BlockPos pos = new BlockPos(getXWithOffset(x, z), fakeBoundingBox.minY + y, getZWithOffset(x, z));
                        IBlockState state = world.getBlockState(pos);

                        if(state.getMaterial().isSolid())
                        {
                            if(solidBlocks++ > 162)
                            {
                                return false;
                            }
                        }
                    }
                }
            }

            boundingBox.offset(0, averageGroundLvl - boundingBox.maxY + structureSize.getY() - 1, 0);
        }

        MinecraftServer minecraftServer = world.getMinecraftServer();
        TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
        PlacementSettings placementSettings = (new PlacementSettings()).setMirror(getMirror()).setRotation(getRotation()).setReplacedBlock(Blocks.STRUCTURE_VOID).setBoundingBox(boundingBox);
        Template template = templateManager.getTemplate(minecraftServer, structure);
        BlockPos pos = template.getZeroPositionWithTransform(new BlockPos(getXWithOffset(0, -1), boundingBox.minY, getZWithOffset(0, -1)), getMirror(), getRotation());
        template.addBlocksToWorldChunk(world, pos, placementSettings);

        for(int x = 0; x < structureSize.getX(); x++)
        {
            for(int z = 0; z < structureSize.getZ(); z++)
            {
                replaceAirAndLiquidDownwards(world, Blocks.NETHERRACK.getDefaultState(), x, -1, z, boundingBoxIn);
            }
        }

        return true;
    }
}

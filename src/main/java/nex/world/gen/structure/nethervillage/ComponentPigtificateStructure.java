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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import nex.entity.passive.EntityPigtificate;
import nex.entity.passive.EntityPigtificateLeader;
import nex.init.NetherExLootTables;
import nex.util.StructureUtil;
import nex.village.Pigtificate;

import java.util.Map;
import java.util.Random;

public class ComponentPigtificateStructure extends ComponentNetherVillage
{
    private Template template;
    private PlacementSettings placementSettings;

    public ComponentPigtificateStructure()
    {

    }

    public ComponentPigtificateStructure(ComponentWell.Controller controller, int componentType, String[] templates, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, Random rand)
    {
        super(controller, componentType, facing);

        template = controller.getTemplateManager().getTemplate(controller.getWorld().getMinecraftServer(), new ResourceLocation(templates[rand.nextInt(templates.length)]));
        placementSettings = new PlacementSettings().setMirror(getMirror()).setRotation(getRotation()).setReplacedBlock(Blocks.STRUCTURE_VOID);
        boundingBox = StructureUtil.getBoundingBoxFromTemplate(template, placementSettings, structureMinX, structureMinY, structureMinZ, facing);
    }

    @Override
    public boolean addComponentParts(World world, Random rand, StructureBoundingBox boundingBoxIn)
    {
        if(template != null)
        {
            BlockPos templateSize = template.transformedSize(placementSettings.getRotation());

            if(averageGroundLvl < 0)
            {
                averageGroundLvl = findAverageGroundLevel(world, boundingBoxIn);

                if(averageGroundLvl < 0)
                {
                    return true;
                }

                StructureBoundingBox fakeBoundingBox = new StructureBoundingBox(boundingBox);
                fakeBoundingBox.offset(0, averageGroundLvl - boundingBox.maxY + templateSize.getY() - 1, 0);

                for(int x = 0; x < templateSize.getX(); x++)
                {
                    for(int z = 0; z < templateSize.getZ(); z++)
                    {
                        BlockPos checkPos = new BlockPos(getXWithOffset(x, z), fakeBoundingBox.minY - 1, getZWithOffset(x, z));

                        if(world.isAirBlock(checkPos) && world.isAirBlock(checkPos.down()))
                        {
                            return false;
                        }
                    }
                }

                int solidBlocks = 0;

                for(int x = 0; x < templateSize.getX(); x++)
                {
                    for(int z = 0; z < templateSize.getZ(); z++)
                    {
                        for(int y = 0; y < templateSize.getY(); y++)
                        {
                            BlockPos checkPos = new BlockPos(getXWithOffset(x, z), fakeBoundingBox.minY + y, getZWithOffset(x, z));
                            IBlockState state = world.getBlockState(checkPos);

                            if(state.getMaterial().isSolid())
                            {
                                if(solidBlocks++ > (templateSize.getX() * templateSize.getZ() * 2))
                                {
                                    return false;
                                }
                            }
                        }
                    }
                }

                boundingBox.offset(0, averageGroundLvl - boundingBox.maxY + templateSize.getY() - 1, 0);
            }

            placementSettings.setBoundingBox(boundingBoxIn);
            BlockPos templatePos = template.getZeroPositionWithTransform(new BlockPos(getXWithOffset(0, templateSize.getZ() - 2), boundingBox.minY, getZWithOffset(0, templateSize.getZ() - 2)), placementSettings.getMirror(), placementSettings.getRotation());
            template.addBlocksToWorld(world, templatePos, placementSettings);

            for(Map.Entry<BlockPos, String> data : template.getDataBlocks(templatePos, placementSettings).entrySet())
            {
                BlockPos dataPos = data.getKey();
                String dataText = data.getValue();

                if(dataText.startsWith("PigtificateLeader"))
                {
                    world.setBlockToAir(dataPos);
                    Pigtificate.Career career = Pigtificate.Career.getFromString(dataText.split(":")[1]);
                    EntityPigtificateLeader pigtificateLeader = new EntityPigtificateLeader(world, career);
                    pigtificateLeader.setPosition(dataPos.getX() + 0.5F, dataPos.getY(), dataPos.getZ() + 0.5F);
                    world.spawnEntity(pigtificateLeader);
                }
                else if(dataText.startsWith("Pigtificate"))
                {
                    world.setBlockToAir(dataPos);
                    Pigtificate.Career career = Pigtificate.Career.getFromString(dataText.split(":")[1]);
                    EntityPigtificate pigtificate = new EntityPigtificate(world, career);
                    pigtificate.setPosition(dataPos.getX() + 0.5F, dataPos.getY(), dataPos.getZ() + 0.5F);
                    world.spawnEntity(pigtificate);
                }
                else if(dataText.startsWith("Chest"))
                {
                    if(boundingBoxIn.isVecInside(dataPos))
                    {
                        world.setBlockState(dataPos, Blocks.CHEST.correctFacing(world, dataPos, Blocks.CHEST.getDefaultState()));
                        TileEntity tileentity = world.getTileEntity(dataPos);

                        if(tileentity instanceof TileEntityChest)
                        {
                            ((TileEntityChest) tileentity).setLootTable(NetherExLootTables.CHEST_VILLAGE_BASE, rand.nextLong());
                        }
                    }
                }
            }

            for(int x = 0; x < templateSize.getX(); x++)
            {
                for(int z = 0; z < (templateSize.getZ() - 1); z++)
                {
                    replaceAirAndLiquidDownwards(world, Blocks.NETHERRACK.getDefaultState(), x, -1, z, boundingBoxIn);
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}

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

package nex.world.gen.feature;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;
import nex.util.BlockUtil;
import nex.util.WeightedUtil;
import nex.util.WorldGenUtil;
import nex.world.biome.NetherBiome;

import java.util.List;
import java.util.Random;

public class FeatureStructure extends Feature
{
    private final List<StructureType> types;
    private final List<WeightedUtil.NamedItem> structures;
    private final List<Block> replacedBlocks;
    private final List<List<ResourceLocation>> lootTables;
    private final List<List<ResourceLocation>> spawnerMobs;
    private final List<Boolean> rotationList;
    private final List<Boolean> mirrorList;
    private final int minHeight;
    private final int maxHeight;

    public FeatureStructure(Biome biome, NetherBiome.BiomeFeature feature)
    {
        super(biome, feature);

        types = Lists.newArrayList();
        structures = Lists.newArrayList();
        replacedBlocks = Lists.newArrayList();
        lootTables = Lists.newArrayList();
        spawnerMobs = Lists.newArrayList();
        rotationList = Lists.newArrayList();
        mirrorList = Lists.newArrayList();
        minHeight = feature.getMinHeight();
        maxHeight = feature.getMaxHeight();

        for(NetherBiome.BiomeStructure structure : feature.getStructureList())
        {
            int index = feature.getStructureList().indexOf(structure);
            replacedBlocks.add(index, BlockUtil.getBlock(structure.getReplacedBlock(), ""));
            structures.add(index, new WeightedUtil.NamedItem(structure.getStructureId(), structure.getWeight()));
            types.add(index, StructureType.getFromString(structure.getStructureType()));
            lootTables.add(index, Lists.newArrayList());
            spawnerMobs.add(index, Lists.newArrayList());
            rotationList.add(index, structure.mirror());
            mirrorList.add(index, structure.rotate());

            if(structure.getLootTables() != null)
            {
                for(String lootTable : structure.getLootTables())
                {
                    lootTables.get(index).add(new ResourceLocation(lootTable));
                }
            }
            if(structure.getSpawnerMobs() != null)
            {
                for(String spawnerMob : structure.getSpawnerMobs())
                {
                    spawnerMobs.get(index).add(new ResourceLocation(spawnerMob));
                }
            }

            if(lootTables.get(index).size() == 0)
            {
                lootTables.get(index).add(LootTableList.EMPTY);
            }
            if(spawnerMobs.get(index).size() == 0)
            {
                spawnerMobs.get(index).add(new ResourceLocation("minecraft:zombie_pigman"));
            }
        }
    }

    @Override
    public boolean generate(World world, BlockPos pos, Random rand)
    {
        rand = world.getChunkFromBlockCoords(pos).getRandomWithSeed(world.getSeed());

        WeightedUtil.NamedItem structure = WeightedUtil.getRandomNamedItem(rand, structures);
        int index = structures.indexOf(structure);

        StructureType type = types.get(index);

        Mirror[] mirrors = Mirror.values();
        Rotation[] rotations = Rotation.values();
        Mirror mirror = mirrorList.get(index) ? mirrors[rand.nextInt(mirrors.length)] : Mirror.NONE;
        Rotation rotation = rotationList.get(index) ? rotations[rand.nextInt(rotations.length)] : Rotation.NONE;

        MinecraftServer minecraftServer = world.getMinecraftServer();
        TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
        Template template = templateManager.getTemplate(minecraftServer, new ResourceLocation(structure.name));

        PlacementSettings placementSettings = new PlacementSettings().setMirror(mirror).setRotation(rotation).setReplacedBlock(replacedBlocks.get(index)).setRandom(rand);
        BlockPos structureSize = Template.transformedBlockPos(placementSettings.copy(), template.getSize());
        BlockPos newPos = new BlockPos(pos.getX() - structureSize.getX() / 2, maxHeight, pos.getZ() - structureSize.getZ() / 2);
        BlockPos spawnPos;

        if(type == StructureType.GROUND)
        {
            spawnPos = WorldGenUtil.getSuitableGroundPos(world, newPos, structureSize, 0.875F);
        }
        else if(type == StructureType.AIR)
        {
            spawnPos = WorldGenUtil.getSuitableAirPos(world, newPos, structureSize);
        }
        else if(type == StructureType.CEILING)
        {
            spawnPos = WorldGenUtil.getSuitableCeilingPos(world, newPos, structureSize);
        }
        else if(type == StructureType.WALL)
        {
            spawnPos = WorldGenUtil.getSuitableWallPos(world, newPos, structureSize, 0.8F);
        }
        else
        {
            return false;
        }

        if(!spawnPos.equals(BlockPos.ORIGIN) && spawnPos.getY() >= minHeight && spawnPos.getY() <= maxHeight)
        {
            WorldGenUtil.generateStructure(world, spawnPos, rand, template, placementSettings.copy(), lootTables.get(index), spawnerMobs.get(index));
            return true;
        }

        return false;
    }

    @Override
    public boolean canGenerate()
    {
        return structures.size() > 0;
    }

    @Override
    public FeatureType getType()
    {
        return FeatureType.STRUCTURE;
    }

    public enum StructureType
    {
        GROUND,
        AIR,
        CEILING,
        WALL,
        UNKNOWN;

        public static StructureType getFromString(String string)
        {
            if(!Strings.isNullOrEmpty(string))
            {
                for(StructureType type : values())
                {
                    if(type.name().equalsIgnoreCase(string))
                    {
                        return type;
                    }
                }
            }

            return UNKNOWN;
        }
    }
}

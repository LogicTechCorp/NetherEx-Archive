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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import nex.util.WeightedUtil;
import nex.util.WorldGenUtil;
import nex.world.gen.structure.NetherStructure;

import java.util.List;
import java.util.Random;

public class NetherGeneratorStructure extends NetherGenerator
{
    private final StructureType structureType;
    private final List<WeightedUtil.NamedItem> netherStructures;

    public static final NetherGeneratorStructure INSTANCE = new NetherGeneratorStructure(0, 0.0F, 0, 0, StructureType.UNKNOWN, Lists.newArrayList());

    private NetherGeneratorStructure(int generationAttempts, float generationProbability, int minHeight, int maxHeight, StructureType structureTypeIn, List<WeightedUtil.NamedItem> netherStructuresIn)
    {
        super(generationAttempts, generationProbability, minHeight, maxHeight);

        structureType = structureTypeIn;
        netherStructures = netherStructuresIn;
    }

    @Override
    public NetherGenerator deserializeConfig(JsonObject config)
    {
        JsonArray structureConfigs = JsonUtils.getJsonArray(config, "structures", new JsonArray());

        if(structureConfigs.size() > 0)
        {
            Random rand = new Random();

            int generationAttempts = JsonUtils.getInt(config, "generationAttempts", 10);
            float generationProbability = JsonUtils.getFloat(config, "generationProbability", 1.0F);
            int minHeight = JsonUtils.getInt(config, "minHeight", 32);
            int maxHeight = JsonUtils.getInt(config, "maxHeight", 128);

            List<WeightedUtil.NamedItem> netherStructureList = Lists.newArrayList();
            StructureType structureType = StructureType.getFromString(JsonUtils.getString(config, "structureType", ""));

            for(JsonElement structureConfig : structureConfigs)
            {
                String structureIdentifier = JsonUtils.getString(structureConfig.getAsJsonObject(), "structure", "");

                if(!structureIdentifier.equals(""))
                {
                    int weight = JsonUtils.getInt(structureConfig.getAsJsonObject(), "weight", 1);

                    Rotation rotation;
                    Mirror mirror;

                    switch(JsonUtils.getString(structureConfig.getAsJsonObject(), "rotation", ""))
                    {
                        case "rotate0":
                            rotation = Rotation.NONE;
                            break;
                        case "rotate90":
                            rotation = Rotation.CLOCKWISE_90;
                            break;
                        case "rotate180":
                            rotation = Rotation.CLOCKWISE_180;
                            break;
                        case "rotate270":
                            rotation = Rotation.COUNTERCLOCKWISE_90;
                            break;
                        default:
                            rotation = Rotation.values()[rand.nextInt(Rotation.values().length)];
                            break;
                    }
                    switch(JsonUtils.getString(structureConfig.getAsJsonObject(), "mirror", ""))
                    {
                        case "noMirror":
                            mirror = Mirror.NONE;
                            break;
                        case "mirrorLeftRight":
                            mirror = Mirror.LEFT_RIGHT;
                            break;
                        case "mirrorFrontBack":
                            mirror = Mirror.FRONT_BACK;
                            break;
                        default:
                            mirror = Mirror.values()[rand.nextInt(Mirror.values().length)];
                            break;
                    }

                    Block replacedBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JsonUtils.getString(structureConfig.getAsJsonObject(), "replacedBlock", "")));

                    List<ResourceLocation> lootTables = Lists.newArrayList();
                    List<ResourceLocation> spawnerMobs = Lists.newArrayList();

                    JsonArray lootTableConfigs = JsonUtils.getJsonArray(structureConfig.getAsJsonObject(), "lootTables", new JsonArray());
                    JsonArray spawnerMobConfigs = JsonUtils.getJsonArray(structureConfig.getAsJsonObject(), "spawnerMobs", new JsonArray());

                    if(lootTableConfigs.size() > 0)
                    {
                        for(JsonElement lootTable : lootTableConfigs)
                        {
                            if(lootTable.isJsonPrimitive())
                            {
                                lootTables.add(new ResourceLocation(lootTable.getAsJsonPrimitive().getAsString()));
                            }
                        }
                    }
                    if(spawnerMobConfigs.size() > 0)
                    {
                        for(JsonElement spawnerMob : spawnerMobConfigs)
                        {
                            if(spawnerMob.isJsonPrimitive())
                            {
                                spawnerMobs.add(new ResourceLocation(spawnerMob.getAsJsonPrimitive().getAsString()));
                            }
                        }
                    }

                    netherStructureList.add(new NetherStructure(structureIdentifier, weight, rotation, mirror, replacedBlock, lootTables, spawnerMobs));
                }
            }

            return new NetherGeneratorStructure(generationAttempts, generationProbability, minHeight, maxHeight, structureType, netherStructureList);
        }

        return null;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        rand = world.getChunkFromBlockCoords(pos).getRandomWithSeed(world.getSeed());

        WeightedUtil.NamedItem structure = WeightedUtil.getRandomNamedItem(rand, netherStructures);
        NetherStructure netherStructure = (NetherStructure) netherStructures.get(netherStructures.indexOf(structure));

        MinecraftServer minecraftServer = world.getMinecraftServer();
        TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
        Template template = templateManager.getTemplate(minecraftServer, new ResourceLocation(structure.name));

        PlacementSettings placementSettings = new PlacementSettings().setMirror(netherStructure.getMirror()).setRotation(netherStructure.getRotation()).setReplacedBlock(netherStructure.getReplacedBlock()).setRandom(rand);
        BlockPos structureSize = Template.transformedBlockPos(placementSettings.copy(), template.getSize());
        BlockPos newPos = new BlockPos(pos.getX() - structureSize.getX() / 2, getMaxHeight(), pos.getZ() - structureSize.getZ() / 2);
        BlockPos spawnPos;

        switch(structureType)
        {
            case GROUND:
                spawnPos = WorldGenUtil.getSuitableGroundPos(world, newPos, structureSize, 0.875F);
                break;
            case AIR:
                spawnPos = WorldGenUtil.getSuitableAirPos(world, newPos, structureSize);
                break;
            case CEILING:
                spawnPos = WorldGenUtil.getSuitableCeilingPos(world, newPos, structureSize);
                break;
            case WALL:
                spawnPos = WorldGenUtil.getSuitableWallPos(world, newPos, structureSize, 0.8F);
                break;
            case UNKNOWN:
            default:
                return false;
        }

        if(!spawnPos.equals(BlockPos.ORIGIN) && spawnPos.getY() >= getMinHeight() && spawnPos.getY() <= getMaxHeight())
        {
            WorldGenUtil.addBlocksToWorld(world, spawnPos, rand, template, placementSettings.copy(), netherStructure.getLootTables(), netherStructure.getSpawnerMobs());
            return true;
        }

        return false;
    }

    private enum StructureType
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

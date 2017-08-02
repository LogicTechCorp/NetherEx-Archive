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

public class EnhancedGeneratorStructure extends EnhancedGenerator
{
    private final StructureType structureType;
    private final List<WeightedUtil.NamedItem> structures;

    public static final EnhancedGeneratorStructure INSTANCE = new EnhancedGeneratorStructure(0, 0.0F, 0, 0, StructureType.UNKNOWN, Lists.newArrayList());

    private EnhancedGeneratorStructure(int generationAttempts, float generationProbability, int minHeight, int maxHeight, StructureType structureTypeIn, List<WeightedUtil.NamedItem> structuresIn)
    {
        super(generationAttempts, generationProbability, minHeight, maxHeight);

        structureType = structureTypeIn;
        structures = structuresIn;
    }

    @Override
    public EnhancedGenerator deserializeConfig(JsonObject config)
    {
        JsonArray structureConfigs = JsonUtils.getJsonArray(config, "structures", new JsonArray());

        if(structureConfigs.size() > 0)
        {
            Random rand = new Random();

            int generationAttempts = JsonUtils.getInt(config, "generationAttempts", 10);
            float generationProbability = JsonUtils.getFloat(config, "generationProbability", 1.0F);
            int minHeight = JsonUtils.getInt(config, "minHeight", 32);
            int maxHeight = JsonUtils.getInt(config, "maxHeight", 128);

            List<WeightedUtil.NamedItem> structureList = Lists.newArrayList();
            StructureType structureType = StructureType.getFromString(JsonUtils.getString(config, "structureType", ""));

            for(JsonElement structureConfig : structureConfigs)
            {
                String structureIdentifier = JsonUtils.getString(structureConfig.getAsJsonObject(), "structure", "");

                if(!structureIdentifier.equals(""))
                {
                    int weight = JsonUtils.getInt(structureConfig.getAsJsonObject(), "weight", 1);
                    String rotationIdentifier = JsonUtils.getString(structureConfig.getAsJsonObject(), "rotation", "");

                    Rotation rotation = null;

                    for(Enum value : Rotation.class.getEnumConstants())
                    {
                        if(value.name().equalsIgnoreCase(rotationIdentifier))
                        {
                            rotation = (Rotation) value;
                        }
                    }
                    if(rotation == null)
                    {
                        rotation = Rotation.values()[rand.nextInt(Rotation.values().length)];
                    }

                    String mirrorIdentifier = JsonUtils.getString(structureConfig.getAsJsonObject(), "rotation", "");

                    Mirror mirror = null;

                    for(Enum value : Mirror.class.getEnumConstants())
                    {
                        if(value.name().equalsIgnoreCase(mirrorIdentifier))
                        {
                            mirror = (Mirror) value;
                        }
                    }
                    if(mirror == null)
                    {
                        mirror = Mirror.values()[rand.nextInt(Mirror.values().length)];
                    }

                    Block replacedBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(JsonUtils.getString(structureConfig.getAsJsonObject(), "replacedBlock", "")));

                    List<ResourceLocation> lootTables = Lists.newArrayList();
                    List<ResourceLocation> spawnerMobs = Lists.newArrayList();

                    for(JsonElement lootTable : JsonUtils.getJsonArray(structureConfig.getAsJsonObject(), "lootTables", new JsonArray()))
                    {
                        if(lootTable.isJsonPrimitive() && lootTable.getAsJsonPrimitive().isString())
                        {
                            lootTables.add(new ResourceLocation(lootTable.getAsJsonPrimitive().getAsString()));
                        }
                    }
                    for(JsonElement spawnerMob : JsonUtils.getJsonArray(structureConfig.getAsJsonObject(), "spawnerMobs", new JsonArray()))
                    {
                        if(spawnerMob.isJsonPrimitive() && spawnerMob.getAsJsonPrimitive().isString())
                        {
                            spawnerMobs.add(new ResourceLocation(spawnerMob.getAsJsonPrimitive().getAsString()));
                        }
                    }

                    structureList.add(new NetherStructure(structureIdentifier, weight, rotation, mirror, replacedBlock, lootTables, spawnerMobs));
                }
            }

            return new EnhancedGeneratorStructure(generationAttempts, generationProbability, minHeight, maxHeight, structureType, structureList);
        }

        return null;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        rand = world.getChunkFromBlockCoords(pos).getRandomWithSeed(world.getSeed());

        WeightedUtil.NamedItem structure = WeightedUtil.getRandomNamedItem(rand, structures);
        NetherStructure netherStructure = (NetherStructure) structures.get(structures.indexOf(structure));

        MinecraftServer minecraftServer = world.getMinecraftServer();
        TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
        Template template = templateManager.getTemplate(minecraftServer, new ResourceLocation(structure.getName()));

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

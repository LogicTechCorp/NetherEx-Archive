/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
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

package logictechcorp.netherex.handler;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.JsonOps;
import logictechcorp.libraryex.utilities.DynamicHelper;
import logictechcorp.libraryex.world.biome.BiomeData;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.world.NetherExWorldType;
import logictechcorp.netherex.world.biome.NetherBiomeData;
import net.minecraft.block.BlockState;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = NetherEx.MOD_ID)
public class NetherDimensionManager
{
    private static final Gson GSON = new Gson();
    private static final Map<ResourceLocation, BiomeData> BIOME_DATA = new HashMap<>();
    private static final Map<ResourceLocation, List<String>> SUB_BIOME_DATA = new HashMap<>();
    private static final Map<ResourceLocation, BiomeManager.BiomeEntry> BIOME_ENTRIES = new HashMap<>();

    private static WorldType worldType;

    public static void setup()
    {
        worldType = new NetherExWorldType();
    }

    @SubscribeEvent
    public static void onServerStarting(FMLServerStartingEvent event)
    {
        MinecraftServer server = event.getServer();
        World world = server.getWorld(DimensionType.THE_NETHER);

        if(world.getWorldType() == worldType)
        {
            try
            {
                IResourceManager resourceManager = server.getResourceManager();

                for(Biome biome : ForgeRegistries.BIOMES)
                {
                    ResourceLocation biomeName = biome.getRegistryName();
                    ResourceLocation configLocation = new ResourceLocation(NetherEx.MOD_ID, "biomes/" + biome.getRegistryName().toString().replace(":", "/") + ".json");

                    if(resourceManager.hasResource(configLocation))
                    {
                        List<IResource> resources = resourceManager.getAllResources(configLocation);

                        for(IResource resource : resources)
                        {
                            InputStream inputStream = resource.getInputStream();
                            Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                            Dynamic<JsonElement> dynamic = new Dynamic<>(JsonOps.INSTANCE, JSONUtils.fromJson(GSON, reader, JsonObject.class));

                            if(dynamic.getValue() == null)
                            {
                                NetherEx.LOGGER.error("Couldn't load {} biome config from {} data pack.", resource.getLocation(), resource.getPackName());
                            }
                            else
                            {
                                int generationWeight = dynamic.get("generation_weight").asInt(10);
                                boolean useDefaultFeatures = dynamic.get("use_default_features").asBoolean(true);
                                boolean isSubBiome = dynamic.get("is_sub_biome").asBoolean(false);
                                Map<BiomeData.BlockType, BlockState> blocks = dynamic.get("blocks").asMap(BiomeData.BlockType::deserialize, BlockState::deserialize);
                                List<Biome.SpawnListEntry> entities = dynamic.get("entities").asList(DynamicHelper::deserializeSpawnListEntry);
                                Map<GenerationStage.Decoration, ConfiguredFeature> features = dynamic.get("features").asStream().collect(Collectors.toMap(DynamicHelper::deserializeGenerationStage, DynamicHelper::deserializeConfiguredFeature));
                                List<String> subBiomes = dynamic.get("sub_biomes").asList(subBiomeDynamic -> subBiomeDynamic.asString(""));

                                BiomeData biomeData = new NetherBiomeData(biome, generationWeight, useDefaultFeatures, isSubBiome);
                                blocks.forEach(biomeData::addBiomeBlock);
                                entities.forEach(biomeData::addEntitySpawn);
                                features.forEach(biomeData::addFeature);
                                SUB_BIOME_DATA.put(biomeName, subBiomes);
                                BIOME_DATA.put(biomeName, biomeData);

                                if(!biomeData.isSubBiome())
                                {
                                    BIOME_ENTRIES.put(biomeName, new BiomeManager.BiomeEntry(biome, generationWeight));
                                }
                            }

                            IOUtils.closeQuietly(inputStream);
                            IOUtils.closeQuietly(reader);
                            IOUtils.closeQuietly(resource);
                        }
                    }
                }

                for(Map.Entry<ResourceLocation, List<String>> entry : SUB_BIOME_DATA.entrySet())
                {
                    BiomeData biomeData = BIOME_DATA.get(entry.getKey());

                    if(biomeData != null)
                    {
                        for(String subBiomeName : entry.getValue())
                        {
                            BiomeData subBiomeData = BIOME_DATA.get(new ResourceLocation(subBiomeName));

                            if(subBiomeData != null)
                            {
                                biomeData.addSubBiome(subBiomeData);
                            }
                        }
                    }
                }

                BIOME_DATA.forEach((resourceLocation, biomeData) -> biomeData.configureBiome());
            }
            catch(IOException ignored)
            {
            }
        }
    }

    @SubscribeEvent
    public static void onServerStopping(FMLServerStoppingEvent event)
    {
        BIOME_DATA.forEach((resourceLocation, biomeData) -> biomeData.resetBiome());
        BIOME_DATA.clear();
        SUB_BIOME_DATA.clear();
        BIOME_ENTRIES.clear();
    }

    public static Map<ResourceLocation, BiomeData> getBiomeData()
    {
        return Collections.unmodifiableMap(BIOME_DATA);
    }

    public static Map<ResourceLocation, BiomeManager.BiomeEntry> getBiomeEntries()
    {
        return Collections.unmodifiableMap(BIOME_ENTRIES);
    }
}

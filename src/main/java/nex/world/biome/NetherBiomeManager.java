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

package nex.world.biome;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import nex.NetherEx;
import nex.util.BlockUtil;
import nex.util.FileUtil;
import nex.world.gen.feature.Feature;
import nex.world.gen.feature.FeatureFire;
import nex.world.gen.feature.FeatureGlowStone;
import nex.world.gen.feature.FeatureOre;
import nex.world.gen.layer.GenLayerNetherEx;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class NetherBiomeManager
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|NetherBiomeManager");

    public static void init(File directory)
    {
        try
        {
            if(!directory.exists())
            {
                directory.mkdir();
            }

            LOGGER.info("Copying the Biome List Directory to the config folder.");

            if(NetherEx.IS_DEV_ENV)
            {
                FileUtils.copyDirectory(new File(NetherEx.class.getResource("/assets/nex/biome_lists").getFile()), directory);
            }
            else
            {
                FileUtil.extractFromJar("/assets/nex/biome_lists", directory.getPath());
            }
        }
        catch(IOException e)
        {
            LOGGER.fatal("The attempt to copy the Biome List Directory to the config folder was unsuccessful.");
            LOGGER.fatal(e);
        }

        Gson gson = new Gson();
        List<File> netherBiomeFiles = Lists.newArrayList(directory.listFiles());

        try
        {
            for(File netherBiomeFile : netherBiomeFiles)
            {
                String jsonText = Files.toString(netherBiomeFile, Charsets.UTF_8);
                NetherBiome.BiomeList biomeList = gson.fromJson(jsonText, NetherBiome.BiomeList.class);

                LOGGER.info("Adding biomes from the " + biomeList.getName() + ".");

                for(NetherBiome.Mod biomeMod : biomeList.getMods())
                {
                    for(NetherBiome netherBiome : biomeMod.getBiomes())
                    {
                        ResourceLocation biomeRegistryName = new ResourceLocation(biomeMod.getId() + ":" + netherBiome.getId());
                        Biome biome = ForgeRegistries.BIOMES.getValue(biomeRegistryName);

                        if(biome == null)
                        {
                            continue;
                        }

                        NetherBiome.BiomeBlock topBlock = netherBiome.getTopBlock();
                        NetherBiome.BiomeBlock fillerBlock = netherBiome.getFillerBlock();
                        NetherBiome.BiomeBlock oceanBlock = netherBiome.getOceanBlock();
                        IBlockState topState = biome.topBlock;
                        IBlockState fillerState = biome.fillerBlock;
                        IBlockState oceanState = Blocks.LAVA.getDefaultState();

                        if(topBlock != null)
                        {
                            IBlockState state = BlockUtil.getBlock(topBlock);
                            topState = state.getBlock() == Blocks.AIR ? topState : state;

                            LOGGER.info("Set the " + biome.getBiomeName() + " biome's top Block to " + ForgeRegistries.BLOCKS.getKey(topState.getBlock()).toString() + " with a meta of " + topState.getBlock().getMetaFromState(topState) + ".");
                        }
                        if(fillerBlock != null)
                        {
                            IBlockState state = BlockUtil.getBlock(fillerBlock);
                            fillerState = state.getBlock() == Blocks.AIR ? fillerState : state;

                            LOGGER.info("Set the " + biome.getBiomeName() + " biome's filler Block to " + ForgeRegistries.BLOCKS.getKey(fillerState.getBlock()).toString() + " with a meta of " + fillerState.getBlock().getMetaFromState(fillerState) + ".");
                        }
                        if(oceanBlock != null)
                        {
                            IBlockState state = BlockUtil.getBlock(oceanBlock);
                            oceanState = state.getBlock() == Blocks.AIR ? oceanState : state;

                            LOGGER.info("Set the " + biome.getBiomeName() + " biome's ocean Block to " + ForgeRegistries.BLOCKS.getKey(oceanState.getBlock()).toString() + " with a meta of " + oceanState.getBlock().getMetaFromState(oceanState) + ".");
                        }

                        Map<EnumCreatureType, List<Biome.SpawnListEntry>> entitySpawnList = Maps.newHashMap();

                        for(EnumCreatureType creatureType : EnumCreatureType.values())
                        {
                            entitySpawnList.put(creatureType, Lists.newArrayList());
                        }

                        if(netherBiome.getEntitySpawnList() != null)
                        {
                            for(NetherBiome.BiomeEntity entity : netherBiome.getEntitySpawnList())
                            {
                                for(EnumCreatureType creatureType : EnumCreatureType.values())
                                {
                                    if(creatureType.toString().equalsIgnoreCase(entity.getCreatureType()))
                                    {
                                        Class<? extends Entity> cls = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(entity.getId())).getEntityClass();

                                        if(cls != null && EntityLiving.class.isAssignableFrom(cls))
                                        {
                                            entitySpawnList.get(creatureType).add(new Biome.SpawnListEntry((Class<? extends EntityLiving>) cls, entity.getWeight(), entity.getMinGroupCount(), entity.getMaxGroupCount()));
                                            LOGGER.info("Added the " + entity.getId() + " Entity to the " + biome.getBiomeName() + " biome.");
                                        }
                                    }
                                }
                            }
                        }

                        List<Feature> features = Lists.newArrayList();

                        if(netherBiome.getFeatures() != null)
                        {
                            for(NetherBiome.BiomeFeature biomeFeature : netherBiome.getFeatures())
                            {
                                Feature.FeatureType type = Feature.FeatureType.getFromString(biomeFeature.getFeatureType());
                                Feature feature;

                                if(type == Feature.FeatureType.FIRE)
                                {
                                    feature = new FeatureFire(biomeFeature);
                                }
                                else if(type == Feature.FeatureType.GLOWSTONE)
                                {
                                    feature = new FeatureGlowStone(biomeFeature);
                                }
                                else if(type == Feature.FeatureType.ORE)
                                {
                                    feature = new FeatureOre(biomeFeature);
                                }
                                else
                                {
                                    LOGGER.info("A biome feature with the type of " + biomeFeature.getFeatureType() + " is unknown.");
                                    continue;
                                }

                                if(feature.canGenerate())
                                {
                                    features.add(feature);
                                }
                            }
                        }

                        NetherBiomeManager.NetherBiomeType.getFromString(netherBiome.getClimateType()).addBiome(biome, netherBiome.getWeight(), topState, fillerState, oceanState, entitySpawnList, features);
                        LOGGER.info("Added the " + biome.getBiomeName() + " biome from the " + biomeList.getName() + " to the Nether.");
                    }
                }
            }
        }
        catch(IOException e)
        {
            LOGGER.fatal("NetherEx was unable to read the Biome lists.");
            LOGGER.fatal(e);
        }
    }

    public enum NetherBiomeType
    {
        HOT,
        WARM,
        TEMPERATE,
        COOL,
        COLD;

        private static final Map<Biome, NetherBiomeEntry> biomes = Maps.newHashMap();

        public void addBiome(Biome biome, int weight, IBlockState topBlock, IBlockState fillerBlock, IBlockState oceanBlock, Map<EnumCreatureType, List<Biome.SpawnListEntry>> entitySpawnList, List<Feature> features)
        {
            biomes.put(biome, new NetherBiomeManager.NetherBiomeEntry(biome, weight, topBlock, fillerBlock, oceanBlock, entitySpawnList, features));
        }

        public static NetherBiomeManager.NetherBiomeType getTypeFromBiome(Biome biome)
        {
            for(NetherBiomeManager.NetherBiomeType type : values())
            {
                for(NetherBiomeManager.NetherBiomeEntry entry : type.getBiomes())
                {
                    if(entry.biome == biome)
                    {
                        return type;
                    }
                }
            }

            return TEMPERATE;
        }

        public static NetherBiomeManager.NetherBiomeType getFromString(String string)
        {
            if(!Strings.isNullOrEmpty(string))
            {
                for(NetherBiomeManager.NetherBiomeType type : values())
                {
                    if(type.name().equalsIgnoreCase(string))
                    {
                        return type;
                    }
                }
            }

            return TEMPERATE;
        }

        public static List<NetherBiomeManager.NetherBiomeEntry> getAllBiomes()
        {
            List<NetherBiomeManager.NetherBiomeEntry> biomes = Lists.newArrayList();

            for(NetherBiomeManager.NetherBiomeType type : values())
            {
                biomes.addAll(type.getBiomes());
            }

            return biomes;
        }

        public static Biome getRandomBiome(List<NetherBiomeManager.NetherBiomeEntry> biomes, Random rand)
        {
            return WeightedRandom.getRandomItem(biomes, rand.nextInt(WeightedRandom.getTotalWeight(biomes))).getBiome();
        }

        public static Biome getRandomBiome(List<NetherBiomeManager.NetherBiomeEntry> biomes, GenLayerNetherEx layer)
        {
            return WeightedRandom.getRandomItem(biomes, layer.nextInt(WeightedRandom.getTotalWeight(biomes))).getBiome();
        }

        public List<NetherBiomeManager.NetherBiomeEntry> getBiomes()
        {
            return ImmutableList.copyOf(biomes.values());
        }

        public IBlockState getBiomeTopBlock(Biome biome)
        {
            if(biomes.containsKey(biome))
            {
                return biomes.get(biome).getTopBlock();
            }

            return biome.topBlock;
        }

        public IBlockState getBiomeFillerBlock(Biome biome)
        {
            if(biomes.containsKey(biome))
            {
                return biomes.get(biome).getFillerBlock();
            }

            return biome.fillerBlock;
        }

        public IBlockState getBiomeOceanBlock(Biome biome)
        {
            if(biomes.containsKey(biome))
            {
                return biomes.get(biome).getOceanBlock();
            }

            return Blocks.LAVA.getDefaultState();
        }

        public Map<EnumCreatureType, List<Biome.SpawnListEntry>> getBiomeEntitySpawnList(Biome biome)
        {
            if(biomes.containsKey(biome))
            {
                return biomes.get(biome).getEntitySpawnList();
            }

            Map<EnumCreatureType, List<Biome.SpawnListEntry>> entitySpawnList = Maps.newHashMap();

            for(EnumCreatureType creatureType : EnumCreatureType.values())
            {
                entitySpawnList.put(creatureType, Lists.newArrayList());
            }

            return entitySpawnList;
        }

        public List<Feature> getBiomeFeatures(Biome biome)
        {
            if(biomes.containsKey(biome))
            {
                return biomes.get(biome).getFeatures();
            }

            return Lists.newArrayList();
        }
    }

    public static class NetherBiomeEntry extends BiomeManager.BiomeEntry
    {
        private final IBlockState topBlock;
        private final IBlockState fillerBlock;
        private final IBlockState oceanBlock;
        private final Map<EnumCreatureType, List<Biome.SpawnListEntry>> entitySpawnList;
        private final List<Feature> features;

        public NetherBiomeEntry(Biome biome, int weight, IBlockState topBlockIn, IBlockState fillerBlockIn, IBlockState oceanBlockIn, Map<EnumCreatureType, List<Biome.SpawnListEntry>> entitySpawnListIn, List<Feature> featuresIn)
        {
            super(biome, weight <= 0 ? 10 : weight);

            topBlock = topBlockIn;
            fillerBlock = fillerBlockIn;
            oceanBlock = oceanBlockIn;
            entitySpawnList = entitySpawnListIn;
            features = featuresIn;
        }

        public Biome getBiome()
        {
            return biome;
        }

        public int getWeight()
        {
            return itemWeight;
        }

        public IBlockState getTopBlock()
        {
            return topBlock;
        }

        public IBlockState getFillerBlock()
        {
            return fillerBlock;
        }

        public IBlockState getOceanBlock()
        {
            return oceanBlock;
        }

        public Map<EnumCreatureType, List<Biome.SpawnListEntry>> getEntitySpawnList()
        {
            return entitySpawnList;
        }

        public List<Feature> getFeatures()
        {
            return features;
        }
    }
}

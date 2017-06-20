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
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.gson.Gson;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import nex.NetherEx;
import nex.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
                NetherBiomeList biomeList = gson.fromJson(jsonText, NetherBiomeList.class);

                LOGGER.info("Adding biomes from the " + biomeList.getName() + ".");

                for(NetherBiomeMod biomeMod : biomeList.getMods())
                {
                    for(NetherBiome netherBiome : biomeMod.getBiomes())
                    {
                        ResourceLocation biomeRegistryName = new ResourceLocation(biomeMod.getId() + ":" + netherBiome.getId());
                        Biome biome = ForgeRegistries.BIOMES.getValue(biomeRegistryName);

                        if(biome == null)
                        {
                            continue;
                        }

                        NetherBiome.Block topBlock = netherBiome.getTopBlock();
                        NetherBiome.Block fillerBlock = netherBiome.getFillerBlock();
                        NetherBiome.Block oceanBlock = netherBiome.getOceanBlock();
                        IBlockState oceanState = Blocks.LAVA.getDefaultState();

                        if(topBlock != null)
                        {
                            IBlockState state = Block.getBlockFromName(topBlock.getId().isEmpty() ? "minecraft:air" : topBlock.getId()).getStateFromMeta(topBlock.getMeta());
                            biome.topBlock = state.getBlock() == Blocks.AIR ? Blocks.NETHERRACK.getDefaultState() : state;

                            LOGGER.info("Set the " + biome.getBiomeName() + " biome's top Block to " + ForgeRegistries.BLOCKS.getKey(biome.topBlock.getBlock()).toString() + " with a meta of " + biome.topBlock.getBlock().getMetaFromState(biome.topBlock) + ".");

                        }
                        if(fillerBlock != null)
                        {
                            IBlockState state = Block.getBlockFromName(fillerBlock.getId().isEmpty() ? "minecraft:air" : fillerBlock.getId()).getStateFromMeta(fillerBlock.getMeta());
                            biome.fillerBlock = state.getBlock() == Blocks.AIR ? Blocks.NETHERRACK.getDefaultState() : state;

                            LOGGER.info("Set the " + biome.getBiomeName() + " biome's filler Block to " + ForgeRegistries.BLOCKS.getKey(biome.fillerBlock.getBlock()).toString() + " with a meta of " + biome.fillerBlock.getBlock().getMetaFromState(biome.fillerBlock) + ".");
                        }
                        if(oceanBlock != null)
                        {
                            IBlockState state = Block.getBlockFromName(oceanBlock.getId().isEmpty() ? "minecraft:air" : oceanBlock.getId()).getStateFromMeta(oceanBlock.getMeta());
                            oceanState = state.getBlock() == Blocks.AIR ? oceanState : state;

                            LOGGER.info("Set the " + biome.getBiomeName() + " biome's ocean Block to " + ForgeRegistries.BLOCKS.getKey(oceanState.getBlock()).toString() + " with a meta of " + oceanState.getBlock().getMetaFromState(oceanState) + ".");
                        }

                        for(NetherBiomeEntity entity : netherBiome.getEntitySpawnList())
                        {
                            for(EnumCreatureType creatureType : EnumCreatureType.values())
                            {
                                if(creatureType.toString().equalsIgnoreCase(entity.getCreatureType()))
                                {
                                    Class<? extends Entity> cls = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(entity.getId())).getEntityClass();

                                    if(cls != null && EntityLiving.class.isAssignableFrom(cls))
                                    {
                                        biome.getSpawnableList(creatureType).add(new Biome.SpawnListEntry((Class<? extends EntityLiving>) cls, entity.getWeight(), entity.getMinGroupCount(), entity.getMaxGroupCount()));
                                        LOGGER.info("Added the " + entity.getId() + " Entity to the " + biome.getBiomeName() + " biome.");
                                    }
                                }
                            }
                        }

                        NetherBiomeType.getFromString(netherBiome.getClimateType()).addBiome(biome, netherBiome.getWeight(), oceanState);
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
}

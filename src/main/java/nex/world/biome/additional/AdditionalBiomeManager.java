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

package nex.world.biome.additional;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.gson.Gson;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import nex.NetherEx;
import nex.init.NetherExBiomes;
import nex.util.FileUtil;
import nex.world.biome.BiomeTypeNetherEx;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class AdditionalBiomeManager
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|AdditionalBiomeManager");

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
        List<File> additionalBiomeFiles = Lists.newArrayList(directory.listFiles());

        try
        {
            for(File additionalBiomeFile : additionalBiomeFiles)
            {
                String jsonText = Files.toString(additionalBiomeFile, Charsets.UTF_8);
                AdditionalBiomeList biomeList = gson.fromJson(jsonText, AdditionalBiomeList.class);

                if(biomeList.getName().equalsIgnoreCase("Example"))
                {
                    continue;
                }

                LOGGER.info("Adding biomes from the " + biomeList.getName() + ".");

                for(AdditionalBiomeMod biomeMod : biomeList.getMods())
                {
                    for(AdditionalBiome additionalBiome : biomeMod.getBiomes())
                    {
                        ResourceLocation biomeRegistryName = new ResourceLocation(biomeMod.getName() + ":" + additionalBiome.getName());
                        Biome biome = Biome.REGISTRY.getObject(biomeRegistryName);
                        int weight = additionalBiome.getWeight();
                        Block block = Block.getBlockFromName(additionalBiome.getOceanBlock().getName());
                        int meta = additionalBiome.getOceanBlock().getMeta();
                        IBlockState state = block.getStateFromMeta(meta);
                        BiomeTypeNetherEx type = BiomeTypeNetherEx.valueOf(additionalBiome.getType().toUpperCase());

                        if(biome == null)
                        {
                            continue;
                        }

                        NetherExBiomes.addBiome(biome, weight, state, type == null ? BiomeTypeNetherEx.WARM : type);
                        LOGGER.info("The " + biome.getBiomeName() + " biome from the " + biomeList.getName() + " biome list was added to the Nether.");
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

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

package nex.village;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.util.JsonUtils;
import nex.NetherEx;
import nex.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

@SuppressWarnings("ConstantConditions")
public class TradeManager
{
    private static final Logger LOGGER = LogManager.getLogger("NetherEx|TradeManager");

    public static void init(File directory)
    {
        copyTradeConfigsToConfigDirectory(directory);
        parseTradeConfigs(directory);
    }

    private static void copyTradeConfigsToConfigDirectory(File directory)
    {
        try
        {
            if(!directory.exists())
            {
                directory.mkdir();
            }

            LOGGER.info("Copying the Trade config directory to the config folder.");

            if(NetherEx.IS_DEV_ENV)
            {
                FileUtils.copyDirectory(new File(NetherEx.class.getResource("/assets/nex/trade_lists").getFile()), directory);
            }
            else
            {
                FileUtil.extractFromJar("/assets/nex/trade_lists", directory.getPath());
            }
        }
        catch(IOException e)
        {
            LOGGER.fatal("The attempt to copy the Trade config directory to the config folder was unsuccessful.");
            LOGGER.fatal(e);
        }

    }

    private static void parseTradeConfigs(File directory)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        Path directoryPath = directory.toPath();

        try
        {
            Iterator<Path> pathIter = Files.walk(directoryPath).iterator();

            while(pathIter.hasNext())
            {
                Path configPath = pathIter.next();

                if(FilenameUtils.getExtension(configPath.toString()).equals("json"))
                {
                    BufferedReader reader = java.nio.file.Files.newBufferedReader(configPath);
                    parseTradeConfig(JsonUtils.fromJson(gson, reader, JsonObject.class));
                    IOUtils.closeQuietly(reader);
                }
                else if(!configPath.toFile().isDirectory())
                {
                    LOGGER.warn("Skipping file located at, " + configPath.toString() + ", as it is not a json file.");
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void parseTradeConfig(JsonObject config)
    {
        String careerIdentifier = JsonUtils.getString(config, "career", "");
        Pigtificate.Career career = null;

        for(Enum value : Pigtificate.Career.class.getEnumConstants())
        {
            if(value.name().equalsIgnoreCase(careerIdentifier))
            {
                career = (Pigtificate.Career) value;
            }
        }
        if(career != null)
        {
            career.addTrade(EnhancedTrade.deserialize(config));
        }
    }
}

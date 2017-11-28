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

import com.google.gson.*;
import net.minecraft.util.JsonUtils;
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

    public static void parseTradeConfigs(File directory)
    {
        if(!directory.exists())
        {
            directory.mkdir();
        }

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
                    BufferedReader reader = Files.newBufferedReader(configPath);
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
        JsonArray tradeConfigs = JsonUtils.getJsonArray(config, "trades", new JsonArray());

        if(tradeConfigs.size() > 0)
        {
            Pigtificate.Career career = Pigtificate.Career.getFromString(JsonUtils.getString(config, "career", ""));

            for(JsonElement tradeConfig : tradeConfigs)
            {
                EnhancedTrade trade = EnhancedTrade.deserialize(tradeConfig.getAsJsonObject());

                if(trade != null)
                {
                    career.addTrade(trade);
                }
            }
        }
    }

    public static void clearTrades()
    {
        for(Pigtificate.Career career : Pigtificate.Career.values())
        {
            for(int i = 0; i < career.getTradeMap().keySet().size(); i++)
            {
                career.clearTradeList(i);
            }
        }
    }
}

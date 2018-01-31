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

import lex.LibEx;
import lex.config.FileConfig;
import lex.config.IConfig;
import lex.util.FileHelper;
import lex.village.ITrade;
import lex.village.Trade;
import nex.NetherEx;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ConstantConditions")
public class NetherExTradeManager
{
    public static void preInit()
    {
        FileHelper.copyDirectoryToDirectory(new File(NetherEx.class.getResource("/assets/nex/trade_configs").getFile()), new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Trades"));
    }

    public static void setupDefaultTrades()
    {
        NetherEx.LOGGER.info("Setting up default trades.");
        parseTradeConfigs(new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Trades/nex"));
    }

    public static void setupCustomTrades()
    {
        NetherEx.LOGGER.info("Setting up custom trades.");
        parseTradeConfigs(new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Trades/custom"));
    }

    private static void parseTradeConfigs(File directory)
    {
        if(!directory.exists())
        {
            directory.mkdir();
        }

        Path directoryPath = directory.toPath();

        try
        {
            Iterator<Path> pathIter = Files.walk(directoryPath).iterator();

            while(pathIter.hasNext())
            {
                Path configPath = pathIter.next();
                File configFile = configPath.toFile();

                if(FileHelper.getFileExtension(configFile).equals("json"))
                {
                    createTrade(new FileConfig(configFile));
                }
                else if(!configFile.isDirectory())
                {
                    NetherEx.LOGGER.warn("Skipping file located at, " + configPath.toString() + ", as it is not a json file.");
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void createTrade(IConfig config)
    {
        Pigtificate.Career career = config.getEnum("career", Pigtificate.Career.class);

        if(career != null)
        {
            List<IConfig> tradeConfigs = config.getInnerConfigs("trades", new ArrayList<>());

            for(IConfig tradeConfig : tradeConfigs)
            {
                career.addTrade(new Trade(tradeConfig));
            }
        }
    }

    public static void clearTrades()
    {
        for(Pigtificate.Career career : Pigtificate.Career.values())
        {
            career.getTrades().clear();
        }
    }
}

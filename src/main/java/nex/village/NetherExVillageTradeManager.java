/*
 * NetherEx
 * Copyright (c) 2016-2018 by MineEx
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
import lex.config.Config;
import lex.util.FileHelper;
import lex.village.Trade;
import nex.NetherEx;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class NetherExVillageTradeManager
{
    public static void preInit()
    {
        FileHelper.copyDirectoryToDirectory(NetherEx.class.getResource("/assets/nex/trade_configs"), new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Trades"));
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
            directory.mkdirs();
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
                    createTrade(new Config(configFile));
                }
                else if(!configFile.isDirectory())
                {
                    NetherEx.LOGGER.warn("Skipping file located at {}, as it is not a json file.", configPath.toString());
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void createTrade(Config config)
    {
        Pigtificate.Career career = config.getEnum("career", Pigtificate.Career.class);

        if(career != null)
        {
            List<Config> tradeConfigs = config.getDataBranches("trades", new ArrayList<>());

            for(Config tradeConfig : tradeConfigs)
            {
                career.addTrade(new Trade(tradeConfig));
            }
        }
    }

    public static void clearTrades()
    {
        for(Pigtificate.Career career : Pigtificate.Career.values())
        {
            career.removeAllTrades();
        }
    }
}

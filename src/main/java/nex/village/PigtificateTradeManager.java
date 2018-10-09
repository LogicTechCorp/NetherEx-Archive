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

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.FileConfig;
import lex.LibEx;
import lex.util.ConfigHelper;
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

public class PigtificateTradeManager
{
    public static void copyTradeConfigs()
    {
        FileHelper.copyDirectoryToDirectory(NetherEx.class.getResource("/assets/nex/trade_configs"), new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Trades"));
    }

    public static void readTradeConfigs()
    {
        NetherEx.LOGGER.info("Setting up default trades.");
        parseTradeConfigs(new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Trades/nex"));

        NetherEx.LOGGER.info("Setting up custom trades.");
        parseTradeConfigs(new File(LibEx.CONFIG_DIRECTORY, "NetherEx/Trades/custom"));
    }

    private static void parseTradeConfigs(File directory)
    {
        try
        {
            Path directoryPath = directory.toPath();
            Files.createDirectories(directoryPath);
            Iterator<Path> pathIter = Files.walk(directoryPath).iterator();

            while(pathIter.hasNext())
            {
                Path configPath = pathIter.next();
                File configFile = configPath.toFile();

                String extension = FileHelper.getFileExtension(configFile);

                if(extension.equals("json") || extension.equals("toml"))
                {
                    FileConfig config = ConfigHelper.newConfig(configFile, false, true, true, true);
                    config.load();
                    createTrade(config);
                }
                else if(!configFile.isDirectory())
                {
                    NetherEx.LOGGER.warn("Skipping file located at {}, as it is not a json or toml file.", configPath.toString());
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
        Pigtificate.Career career = ConfigHelper.getOrSetEnum(config, "career", Pigtificate.Career.class, null);

        if(career != null)
        {
            List<Config> tradeConfigs = ConfigHelper.getOrSet(config, "trades", new ArrayList<>());

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

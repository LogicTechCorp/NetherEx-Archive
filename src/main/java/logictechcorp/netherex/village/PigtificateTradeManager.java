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

package logictechcorp.netherex.village;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.FileConfig;
import logictechcorp.libraryex.LibraryEx;
import logictechcorp.libraryex.util.ConfigHelper;
import logictechcorp.libraryex.util.FileHelper;
import logictechcorp.libraryex.village.Trade;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExRegistries;
import net.minecraft.util.ResourceLocation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

public class PigtificateTradeManager
{
    public static void copyTradeConfigs()
    {
        FileHelper.copyDirectoryToDirectory(NetherEx.class.getResource("/assets/netherex/trade_configs"), new File(LibraryEx.CONFIG_DIRECTORY, "NetherEx/Trades"));
    }

    public static void readTradeConfigs()
    {
        NetherEx.LOGGER.info("Setting up default trades.");
        parseTradeConfigs(new File(LibraryEx.CONFIG_DIRECTORY, "NetherEx/Trades/logictechcorp.netherex"));

        NetherEx.LOGGER.info("Setting up custom trades.");
        parseTradeConfigs(new File(LibraryEx.CONFIG_DIRECTORY, "NetherEx/Trades/custom"));
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

                if(extension.equals("json"))
                {
                    FileConfig config = ConfigHelper.newConfig(configFile, true, true, true);
                    config.load();
                    createTrade(config);
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
        PigtificateProfession profession = NetherExRegistries.PIGTIFICATE_PROFESSIONS.getValue(new ResourceLocation(config.get("profession")));

        if(profession != null)
        {
            PigtificateProfession.Career career = profession.getCareer(new ResourceLocation(config.get("career")));

            if(career != null && config.get("trades") instanceof List)
            {
                List<Config> tradeConfigs = config.get("trades");

                for(Config tradeConfig : tradeConfigs)
                {
                    career.addTrade(new Trade(tradeConfig));
                }
            }
        }
    }
}

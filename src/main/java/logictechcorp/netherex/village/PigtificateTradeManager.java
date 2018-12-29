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
import com.electronwill.nightconfig.toml.TomlFormat;
import logictechcorp.libraryex.util.ConfigHelper;
import logictechcorp.libraryex.util.FileHelper;
import logictechcorp.libraryex.util.WorldHelper;
import logictechcorp.libraryex.village.Trade;
import logictechcorp.libraryex.village.TradeStack;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.handler.ConfigHandler;
import logictechcorp.netherex.init.NetherExRegistries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PigtificateTradeManager
{
    public static void readTradeConfigs(MinecraftServer server)
    {
        Path path = new File(WorldHelper.getSaveFile(server.getEntityWorld()), "/config/NetherEx/Trades").toPath();
        NetherEx.LOGGER.info("Reading Pigtificate trade configs.");

        try
        {
            Files.createDirectories(path);
            Iterator<Path> pathIter = Files.walk(path).iterator();

            while(pathIter.hasNext())
            {
                Path configPath = pathIter.next();
                File configFile = configPath.toFile();

                if(FileHelper.getFileExtension(configFile).equals("toml"))
                {
                    FileConfig config = ConfigHelper.newConfig(configFile, true, true, true);
                    config.load();

                    PigtificateProfession profession = NetherExRegistries.PIGTIFICATE_PROFESSIONS.getValue(new ResourceLocation(config.get("profession")));

                    if(profession != null)
                    {
                        PigtificateProfession.Career career = profession.getCareer(new ResourceLocation(config.get("career")));

                        if(career != null)
                        {
                            List<Config> tradeConfigs = config.getOrElse("trades", new ArrayList<>());

                            if(tradeConfigs.size() > 0)
                            {
                                for(Config tradeConfig : tradeConfigs)
                                {
                                    career.addTrade(new Trade(tradeConfig));
                                }
                            }
                        }
                    }

                }
                else if(!configFile.isDirectory())
                {
                    NetherEx.LOGGER.warn("Skipping file located at {}, as it is not a toml file.", configPath.toString());
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void writeTradeConfigs(MinecraftServer server)
    {
        try
        {
            if(ConfigHandler.internalConfig.tradeConfigs.writeTradeConfigsToWorldConfigFolder)
            {
                NetherEx.LOGGER.info("Writing Pigtificate trade configs.");

                for(PigtificateProfession profession : NetherExRegistries.PIGTIFICATE_PROFESSIONS.getValuesCollection())
                {
                    for(PigtificateProfession.Career career : profession.getCareers())
                    {
                        File configFile = new File(WorldHelper.getSaveFile(server.getEntityWorld()), "/config/NetherEx/Trades/" + career.getName().getNamespace() + "/" + career.getName().getPath() + ".toml");
                        Files.createDirectories(configFile.getParentFile().toPath());
                        FileConfig config = ConfigHelper.newConfig(new File(WorldHelper.getSaveFile(server.getEntityWorld()), "/config/NetherEx/Trades/" + career.getName().getNamespace() + "/" + career.getName().getPath() + ".toml"), true, true, true);
                        config.set("profession", profession.getName().toString());
                        config.set("career", career.getName().toString());

                        List<Config> tradeConfigs = new ArrayList<>();

                        for(List<Trade> trades : career.getTrades().values())
                        {
                            for(Trade trade : trades)
                            {
                                Config tradeConfig = TomlFormat.newConcurrentConfig();
                                TradeStack output = trade.getOutput();
                                TradeStack inputOne = trade.getInputOne();
                                TradeStack inputTwo = trade.getInputTwo();

                                ConfigHelper.setItemStackSimple(tradeConfig, "output", output.getItemStack());

                                if(!tradeConfig.contains("output.minCount"))
                                {
                                    tradeConfig.add("output.minCount", output.getMinCount());
                                }
                                if(!tradeConfig.contains("output.maxCount"))
                                {
                                    tradeConfig.add("output.maxCount", output.getMaxCount());
                                }

                                ConfigHelper.setItemStackSimple(tradeConfig, "inputOne", inputOne.getItemStack());

                                if(!tradeConfig.contains("inputOne.minCount"))
                                {
                                    tradeConfig.add("inputOne.minCount", output.getMinCount());
                                }
                                if(!tradeConfig.contains("inputOne.maxCount"))
                                {
                                    tradeConfig.add("inputOne.maxCount", output.getMaxCount());
                                }

                                if(!inputTwo.getItemStack().isEmpty())
                                {
                                    ConfigHelper.setItemStackSimple(tradeConfig, "inputTwo", inputTwo.getItemStack());

                                    if(!tradeConfig.contains("inputTwo.minCount"))
                                    {
                                        tradeConfig.add("inputTwo.minCount", output.getMinCount());
                                    }
                                    if(!tradeConfig.contains("inputTwo.maxCount"))
                                    {
                                        tradeConfig.add("inputTwo.maxCount", output.getMaxCount());
                                    }
                                }

                                tradeConfig.add("minTradeCount", trade.getMinTradeCount());
                                tradeConfig.add("maxTradeCount", trade.getMaxTradeCount());
                                tradeConfig.add("tradeLevel", trade.getTradeLevel());
                                tradeConfigs.add(tradeConfig);
                            }
                        }

                        config.add("trades", tradeConfigs);
                        config.save();
                        config.close();
                    }
                }

                ConfigHandler.internalConfig.tradeConfigs.writeTradeConfigsToWorldConfigFolder = false;
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}

/*
 * NetherEx
 * Copyright (c) 2016-2019 by LogicTechCorp
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
import com.electronwill.nightconfig.json.JsonFormat;
import logictechcorp.libraryex.LibraryEx;
import logictechcorp.libraryex.trade.Trade;
import logictechcorp.libraryex.utility.FileHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExRegistries;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public final class NetherExTradeManager
{
    //NOT for use by modders. Please access using the api.
    public static final NetherExTradeManager INSTANCE = new NetherExTradeManager();

    public void readTradeConfigs(WorldEvent.Load event)
    {
        World world = event.getWorld();

        if(world.provider.getDimension() == DimensionType.OVERWORLD.getId())
        {
            Path path = new File(LibraryEx.CONFIG_DIRECTORY, NetherEx.MOD_ID + "/trades").toPath();
            NetherEx.LOGGER.info("Reading Pigtificate trade configs from disk.");

            try
            {
                Files.createDirectories(path);
                Iterator<Path> pathIter = Files.walk(path).iterator();

                while(pathIter.hasNext())
                {
                    Path configPath = pathIter.next();
                    File configFile = configPath.toFile();

                    if(FileHelper.getFileExtension(configFile).equals("json"))
                    {
                        FileConfig config = FileConfig.builder(configFile, JsonFormat.fancyInstance()).preserveInsertionOrder().build();
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

                        config.save();
                        config.close();

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
    }

    public void writeTradeConfigs(WorldEvent.Unload event)
    {
        World world = event.getWorld();

        if(world.provider.getDimension() == DimensionType.OVERWORLD.getId())
        {
            try
            {
                NetherEx.LOGGER.info("Writing Pigtificate trade configs to disk.");

                for(PigtificateProfession profession : NetherExRegistries.PIGTIFICATE_PROFESSIONS.getValuesCollection())
                {
                    for(PigtificateProfession.Career career : profession.getCareers())
                    {
                        List<Trade> trades = career.getTrades();
                        File configFile = new File(LibraryEx.CONFIG_DIRECTORY, NetherEx.MOD_ID + "/trades/" + career.getName().getNamespace() + "/" + career.getName().getPath() + ".json");
                        Files.createDirectories(configFile.getParentFile().toPath());
                        FileConfig tradeConfig = FileConfig.of(configFile);
                        tradeConfig.set("profession", profession.getName().toString());
                        tradeConfig.set("career", career.getName().toString());
                        tradeConfig.add("trades", trades.stream().map(Trade::getAsConfig).collect(Collectors.toList()));
                        tradeConfig.save();
                        tradeConfig.close();
                        trades.forEach(career::removeTrade);
                    }
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}

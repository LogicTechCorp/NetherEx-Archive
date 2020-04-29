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
import logictechcorp.libraryex.trade.Trade;
import logictechcorp.libraryex.utility.FileHelper;
import logictechcorp.libraryex.utility.WorldHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExRegistries;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class PigtificateVillageManager
{
    private final Map<PigtificateProfession.Career, List<Trade>> defaultTrades;
    private final Map<Integer, PigtificateVillageData> pigtificateVillageData;

    public PigtificateVillageManager()
    {
        this.defaultTrades = new HashMap<>();
        this.pigtificateVillageData = new HashMap<>();
    }

    public void setup()
    {
        NetherExRegistries.PIGTIFICATE_PROFESSIONS.getValuesCollection()
                .forEach(profession -> profession.getCareers()
                        .forEach(career -> this.defaultTrades.put(career, new ArrayList<>(career.getTrades()))));
    }

    public void loadVillageData(WorldEvent.Load event)
    {
        World world = event.getWorld();

        if(!this.hasData(world))
        {
            this.getVillageData(world, false);
        }
    }

    public void unloadVillageData(WorldEvent.Unload event)
    {
        this.pigtificateVillageData.remove(event.getWorld().provider.getDimension());
    }

    public void cleanup(WorldEvent.Unload event)
    {
        NetherExRegistries.PIGTIFICATE_PROFESSIONS.getValuesCollection()
                .forEach(profession -> profession.getCareers()
                        .forEach(career -> career.getTrades().forEach(career::removeTrade)));

        this.pigtificateVillageData.clear();
    }

    public void readPigtificateTradeConfigs(WorldEvent.Load event)
    {
        Path path = Paths.get(WorldHelper.getSaveDirectory(event.getWorld()), "config", NetherEx.MOD_ID, "pigtificate_trades");

        if(Files.isReadable(path))
        {
            NetherEx.LOGGER.info("Reading Pigtificate trade configs.");

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
                        String fileText = FileUtils.readFileToString(configFile, Charset.defaultCharset()).trim();

                        if(fileText.isEmpty() || !fileText.startsWith("{") || !fileText.endsWith("}"))
                        {
                            String filePath = configFile.getPath();
                            String fileBackupPath = filePath + "_backup";
                            Files.move(configFile.toPath(), Paths.get(fileBackupPath));
                            NetherEx.LOGGER.warn("The trade config at {} was invalid and was backed up as {}.", filePath, fileBackupPath);
                            continue;
                        }

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
                    else if(!configFile.isDirectory() && !FileHelper.getFileExtension(configFile).equals("json_backup"))
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
        else
        {
            NetherEx.LOGGER.warn("Unable to read Pigtificate trade configs. The default configs will be used.");
        }
    }

    public void createPigtificateTradeConfigs(WorldEvent.Load event)
    {
        try
        {
            NetherEx.LOGGER.info("Creating Pigtificate trade configs.");

            for(Map.Entry<PigtificateProfession.Career, List<Trade>> entry : this.defaultTrades.entrySet())
            {
                PigtificateProfession.Career career = entry.getKey();
                List<Trade> trades = entry.getValue();

                File configFile = new File(WorldHelper.getSaveDirectory(event.getWorld()), "/config/" + NetherEx.MOD_ID + "/pigtificate_trades/" + career.getName().toString().replace(":", "/") + ".json");

                if(!configFile.exists())
                {
                    Files.createDirectories(configFile.getParentFile().toPath());
                    FileConfig tradeConfig = FileConfig.of(configFile);
                    tradeConfig.set("profession", career.getProfession().getName().toString());
                    tradeConfig.set("career", career.getName().toString());
                    tradeConfig.add("trades", trades.stream().map(Trade::getAsConfig).collect(Collectors.toList()));
                    tradeConfig.save();
                    tradeConfig.close();
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean hasData(World world)
    {
        return pigtificateVillageData.containsKey(world.provider.getDimension());
    }

    public PigtificateVillageData getVillageData(World world, boolean createData)
    {
        PigtificateVillageData data;
        int dimensionId = world.provider.getDimension();

        if(this.hasData(world))
        {
            data = pigtificateVillageData.get(dimensionId);
        }
        else
        {
            String worldFile = PigtificateVillageData.getDataId(world);
            data = (PigtificateVillageData) world.getPerWorldStorage().getOrLoadData(PigtificateVillageData.class, worldFile);

            if(data == null && createData)
            {
                data = new PigtificateVillageData(world);
                world.getPerWorldStorage().setData(worldFile, data);
            }
        }

        if(data != null)
        {
            pigtificateVillageData.put(dimensionId, data);
        }

        return data;
    }
}

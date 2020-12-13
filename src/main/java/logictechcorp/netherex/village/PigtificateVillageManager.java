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
import logictechcorp.libraryex.utility.WorldHelper;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.NetherExConfig;
import logictechcorp.netherex.init.NetherExRegistries;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
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
    private final Map<PigtificateProfession.Career, List<Trade>> currentTrades;
    private final Map<Integer, PigtificateVillageData> pigtificateVillageData;

    public PigtificateVillageManager()
    {
        this.defaultTrades = new HashMap<>();
        this.currentTrades = new HashMap<>();
        this.pigtificateVillageData = new HashMap<>();
    }

    public void setup()
    {
        NetherExRegistries.PIGTIFICATE_PROFESSIONS.getValuesCollection()
                .forEach(profession -> profession.getCareers()
                        .forEach(career ->
                        {
                            List<Trade> trades = career.getTrades();
                            this.defaultTrades.put(career, trades);
                            trades.forEach(career::removeTrade);
                        }));

        if(NetherExConfig.entity.pigtificate.useGlobalTradeConfigs)
        {
            Path globalTradeConfigDirectoryPath = LibraryEx.CONFIG_DIRECTORY.toPath().resolve(NetherEx.MOD_ID).resolve("pigtificate_trades");
            this.createPigtificateTradeConfigs(globalTradeConfigDirectoryPath);
        }
    }

    public void onWorldLoad(WorldEvent.Load event)
    {
        World world = event.getWorld();

        if(!world.isRemote)
        {
            if(world.provider.getDimension() == DimensionType.OVERWORLD.getId())
            {
                if(NetherExConfig.entity.pigtificate.useGlobalTradeConfigs)
                {
                    Path globalTradeConfigDirectoryPath = LibraryEx.CONFIG_DIRECTORY.toPath().resolve(NetherEx.MOD_ID).resolve("pigtificate_trades");
                    this.readPigtificateTradeConfigs(globalTradeConfigDirectoryPath);
                }

                if(NetherExConfig.entity.pigtificate.usePerWorldTradeConfigs)
                {
                    Path perWorldTradeConfigDirectoryPath = Paths.get(WorldHelper.getSaveDirectory(event.getWorld()), "config", NetherEx.MOD_ID, "pigtificate_trades");
                    this.createPigtificateTradeConfigs(perWorldTradeConfigDirectoryPath);
                    this.readPigtificateTradeConfigs(perWorldTradeConfigDirectoryPath);
                }

                for(Map.Entry<PigtificateProfession.Career, List<Trade>> entry : this.currentTrades.entrySet())
                {
                    PigtificateProfession.Career career = entry.getKey();
                    List<Trade> trades = entry.getValue();
                    trades.forEach(career::addTrade);
                }
            }

            this.loadVillageData(world);
        }
    }

    public void onWorldTick(TickEvent.WorldTickEvent event)
    {
        World world = event.world;

        if(event.phase == TickEvent.Phase.END)
        {
            if(!world.isRemote)
            {
                PigtificateVillageData data = this.getVillageData(world, false);

                if(data != null)
                {
                    data.tick(world);
                }
            }
        }
    }

    public void onWorldUnload(WorldEvent.Unload event)
    {
        World world = event.getWorld();

        if(!world.isRemote)
        {
            int dimension = world.provider.getDimension();
            this.unloadVillageData(dimension);

            if(dimension == DimensionType.OVERWORLD.getId())
            {
                NetherExRegistries.PIGTIFICATE_PROFESSIONS.getValuesCollection()
                        .forEach(profession -> profession.getCareers()
                                .forEach(career ->
                                {
                                    List<Trade> trades = career.getTrades();
                                    trades.forEach(career::removeTrade);
                                }));

                this.pigtificateVillageData.clear();
            }
        }
    }

    public void loadVillageData(World world)
    {
        if(!this.hasData(world))
        {
            this.getVillageData(world, false);
        }
    }

    public void unloadVillageData(int dimensionId)
    {
        this.pigtificateVillageData.remove(dimensionId);
    }

    public void readPigtificateTradeConfigs(Path tradeConfigDirectoryPath)
    {
        if(Files.exists(tradeConfigDirectoryPath))
        {
            NetherEx.LOGGER.info("Reading Pigtificate trade configs.");

            try
            {
                Iterator<Path> pathIter = Files.walk(tradeConfigDirectoryPath).iterator();

                while(pathIter.hasNext())
                {
                    Path configPath = pathIter.next();
                    File configFile = configPath.toFile();

                    if(FileHelper.getFileExtension(configFile).equals("json"))
                    {
                        if(Files.isReadable(configPath))
                        {
                            String fileText = FileUtils.readFileToString(configFile, Charset.defaultCharset()).trim();

                            if(fileText.startsWith("{") && fileText.endsWith("}"))
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
                                                if(!this.currentTrades.containsKey(career))
                                                {
                                                    this.currentTrades.put(career, new ArrayList<>());
                                                }

                                                this.currentTrades.get(career).add(new Trade(tradeConfig));
                                            }
                                        }
                                    }
                                }

                                config.save();
                                config.close();
                            }
                            else
                            {
                                NetherEx.LOGGER.warn("Skipping Pigtificate trade config at {}. Its contents are invalid.", configPath);
                            }
                        }
                        else
                        {
                            NetherEx.LOGGER.warn("Skipping Pigtificate trade config at {}. It is unreadable.", configPath);
                        }
                    }
                    else if(!configFile.isDirectory())
                    {
                        NetherEx.LOGGER.warn("Skipping Pigtificate trade config at {}. It is not a json file.", configPath);
                    }
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void createPigtificateTradeConfigs(Path tradeConfigDirectoryPath)
    {
        if(Files.notExists(tradeConfigDirectoryPath))
        {
            NetherEx.LOGGER.info("Creating Pigtificate trade configs.");

            try
            {
                for(Map.Entry<PigtificateProfession.Career, List<Trade>> entry : this.defaultTrades.entrySet())
                {
                    PigtificateProfession.Career career = entry.getKey();
                    List<Trade> trades = entry.getValue();

                    String careerName = career.getName().toString();
                    File configFile = new File(tradeConfigDirectoryPath.toFile(), careerName.replace(":", "/") + ".json");
                    Files.createDirectories(configFile.getParentFile().toPath());
                    FileConfig tradeConfig = FileConfig.builder(configFile, JsonFormat.fancyInstance()).preserveInsertionOrder().build();
                    tradeConfig.set("profession", career.getProfession().getName().toString());
                    tradeConfig.set("career", careerName);
                    tradeConfig.add("trades", trades.stream().map(Trade::getAsConfig).collect(Collectors.toList()));
                    tradeConfig.save();
                    tradeConfig.close();
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public boolean hasData(World world)
    {
        return this.pigtificateVillageData.containsKey(world.provider.getDimension());
    }

    public Map<PigtificateProfession.Career, List<Trade>> getDefaultTrades()
    {
        return Collections.unmodifiableMap(this.defaultTrades);
    }

    public Map<PigtificateProfession.Career, List<Trade>> getCurrentTrades()
    {
        return Collections.unmodifiableMap(this.currentTrades);
    }

    public PigtificateVillageData getVillageData(World world, boolean createData)
    {
        PigtificateVillageData data;
        int dimensionId = world.provider.getDimension();

        if(this.hasData(world))
        {
            data = this.pigtificateVillageData.get(dimensionId);
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
            this.pigtificateVillageData.put(dimensionId, data);
        }

        return data;
    }
}

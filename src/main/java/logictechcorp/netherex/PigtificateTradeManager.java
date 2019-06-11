/*
 * LibraryEx
 * Copyright (c) 2017-2019 by LogicTechCorp
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

package logictechcorp.netherex;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.FileConfig;
import logictechcorp.libraryex.api.entity.trade.ITradeManager;
import logictechcorp.libraryex.config.ModJsonConfigFormat;
import logictechcorp.libraryex.trade.Trade;
import logictechcorp.libraryex.utility.FileHelper;
import logictechcorp.libraryex.utility.WorldHelper;
import logictechcorp.netherex.init.NetherExRegistries;
import logictechcorp.netherex.village.PigtificateProfession;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.WorldEvent;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

final class PigtificateTradeManager implements ITradeManager
{
    static final ITradeManager INSTANCE = new PigtificateTradeManager();
    private static boolean readConfigs = false;
    private final Marker marker = MarkerManager.getMarker("PigtificateTradeManager");

    private PigtificateTradeManager()
    {
    }

    @Override
    public void readTradeConfigs(WorldEvent.Load event)
    {
        World world = event.getWorld();
        int dimensionId = world.provider.getDimension();

        if(dimensionId != DimensionType.OVERWORLD.getId() && dimensionId != DimensionType.NETHER.getId())
        {
            return;
        }

        if(world.provider.getDimension() == DimensionType.OVERWORLD.getId())
        {
            PigtificateTradeManager.readConfigs = true;
            return;
        }

        if(PigtificateTradeManager.readConfigs)
        {
            if(DimensionManager.getWorld(DimensionType.NETHER.getId()).getWorldInfo().getTerrainType() == NetherEx.WORLD_TYPE)
            {
                Path path = new File(WorldHelper.getSaveDirectory(world), "/config/netherex/trades").toPath();
                NetherEx.LOGGER.info(this.marker, "Reading Pigtificate trade configs from disk.");

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
                            FileConfig config = FileConfig.of(configFile, ModJsonConfigFormat.instance());
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
                            NetherEx.LOGGER.warn("Skipping file located at {}, as it is not a json file.", configPath.toString());
                        }
                    }
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }

            PigtificateTradeManager.readConfigs = false;
        }
    }

    @Override
    public void writeTradeConfigs(WorldEvent.Unload event)
    {
        World world = event.getWorld();

        if(world.provider.getDimension() == DimensionType.OVERWORLD.getId())
        {
            try
            {
                NetherEx.LOGGER.info(this.marker, "Writing Pigtificate trade configs to disk.");

                for(PigtificateProfession profession : NetherExRegistries.PIGTIFICATE_PROFESSIONS.getValuesCollection())
                {
                    for(PigtificateProfession.Career career : profession.getCareers())
                    {
                        List<Trade> trades = career.getTrades();
                        File configFile = new File(WorldHelper.getSaveDirectory(world), "/config/netherex/trades/" + career.getName().getNamespace() + "/" + career.getName().getPath() + ".json");
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

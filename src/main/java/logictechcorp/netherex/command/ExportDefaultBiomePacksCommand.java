/*
 * NetherEx
 * Copyright (c) 2016-2020 by LogicTechCorp
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

package logictechcorp.netherex.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import logictechcorp.netherex.NetherEx;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class ExportDefaultBiomePacksCommand
{
    public static ArgumentBuilder<CommandSource, ?> register()
    {
        return Commands.literal("exportDefaultBiomePacks")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(ExportDefaultBiomePacksCommand::exportBiomePacks);
    }

    private static int exportBiomePacks(CommandContext<CommandSource> context)
    {
        CommandSource source = context.getSource();
        MinecraftServer server = source.getServer();
        File datapackDirectory = server.getActiveAnvilConverter().getFile(server.getFolderName(), "datapacks");

        try
        {
            ModFile modFile = ModList.get().getModFileById(NetherEx.MOD_ID).getFile();
            Iterator<Path> pathIter = Files.walk(modFile.getLocator().findPath(modFile, "datapacks"), 1).iterator();

            while(pathIter.hasNext())
            {
                File datapack = pathIter.next().toFile();

                if(datapack.isDirectory() && new File(datapack, "pack.mcmeta").exists())
                {
                    File destinationDirectory = new File(datapackDirectory, datapack.getName());
                    FileUtils.copyDirectory(datapack, destinationDirectory);
                }
            }
        }
        catch(Throwable ignored)
        {
            NetherEx.LOGGER.warn("Unable to export default biome packs.");
        }

        return 0;
    }
}

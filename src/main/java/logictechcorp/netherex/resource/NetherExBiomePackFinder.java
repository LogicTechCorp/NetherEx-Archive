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

package logictechcorp.netherex.resource;

import net.minecraft.resources.FolderPack;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.ResourcePackInfo;

import java.io.File;
import java.util.Map;

public class NetherExBiomePackFinder implements IPackFinder
{
    private final File folder;

    public NetherExBiomePackFinder(File folder)
    {
        this.folder = folder;
    }

    @Override
    public <T extends ResourcePackInfo> void addPackInfosToMap(Map<String, T> packs, ResourcePackInfo.IFactory<T> packFactory)
    {
        if(this.folder.isDirectory())
        {
            String fileName = "file/" + this.folder.getName();
            T t = ResourcePackInfo.createResourcePack(fileName, false, () -> new FolderPack(this.folder), packFactory, ResourcePackInfo.Priority.BOTTOM);

            if(t != null)
            {
                packs.put(fileName, t);
            }
        }
    }
}

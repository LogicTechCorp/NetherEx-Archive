/*
 * LibraryEx
 * Copyright (c) 2017-2018 by MineEx
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
 *
 */

package logictechcorp.netherex.handler;

import logictechcorp.libraryex.util.FlatteningHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

import java.util.HashMap;
import java.util.Map;

public class FlatteningHandler implements IFixableData
{
    public static final FlatteningHandler INSTANCE = new FlatteningHandler();

    private static final Map<String, String> OLD_TO_NEW_ID_MAP = FlatteningHelper.make(new HashMap<>(), (map ->
    {
        map.put("nex:basalt.0", "netherex:basalt");
        map.put("nex:basalt.1", "netherex:smooth_basalt");
        map.put("nex:basalt.2", "netherex:basalt_brick");
        map.put("nex:basalt.3", "netherex:basalt_pillar");

    }));


    private FlatteningHandler()
    {

    }

    @Override
    public int getFixVersion()
    {
        return 209;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound)
    {
        String newRegistryName = OLD_TO_NEW_ID_MAP.get(compound.getString("id") + '.' + compound.getShort("Damage"));

        if(newRegistryName != null)
        {
            compound.setString("id", newRegistryName);
            compound.setShort("Damage", (short) 0);
        }

        return compound;
    }
}

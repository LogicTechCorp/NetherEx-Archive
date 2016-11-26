/*
 * Copyright (C) 2016.  LogicTechCorp
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

package nex.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTUtil
{
    public static void setTag(ItemStack stack)
    {
        if(!stack.hasTagCompound())
        {
            stack.setTagCompound(new NBTTagCompound());
        }
    }

    public static NBTTagCompound setTag(ItemStack stack, NBTTagCompound compound)
    {
        if(stack.hasTagCompound())
        {
            compound = stack.getTagCompound();
        }
        else
        {
            compound = new NBTTagCompound();
        }

        return compound;
    }
}

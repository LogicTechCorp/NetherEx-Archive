/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
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

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ArmorHelper
{
    public static boolean isWearingFullArmorSet(EntityPlayer player, ItemArmor.ArmorMaterial material)
    {
        Iterable<ItemStack> armor = player.getArmorInventoryList();
        List<ItemArmor.ArmorMaterial> armorMaterials = Lists.newArrayList();

        for(ItemStack testStack : armor)
        {
            if(testStack == ItemStack.EMPTY || !(testStack.getItem() instanceof ItemArmor))
            {
                return false;
            }

            armorMaterials.add(((ItemArmor) testStack.getItem()).getArmorMaterial());
        }

        for(ItemArmor.ArmorMaterial testMaterial : armorMaterials)
        {
            if(testMaterial != material)
            {
                return false;
            }
        }

        return true;
    }

}

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

package logictechcorp.netherex.item;

import logictechcorp.libraryex.item.ItemArmorLibEx;
import logictechcorp.netherex.NetherEx;
import logictechcorp.netherex.init.NetherExMaterials;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemWitherBoneArmor extends ItemArmorLibEx
{
    public ItemWitherBoneArmor(String name, int renderIndex, EntityEquipmentSlot equipmentSlot)
    {
        super(NetherEx.instance, "wither_bone_" + name, NetherExMaterials.WITHER_BONE, renderIndex, equipmentSlot);
    }
}

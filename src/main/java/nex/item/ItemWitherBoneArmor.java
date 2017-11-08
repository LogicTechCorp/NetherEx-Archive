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

package nex.item;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import nex.init.NetherExItems;
import nex.init.NetherExMaterials;

@SuppressWarnings("ConstantConditions")
public class ItemWitherBoneArmor extends ItemNetherExArmor
{
    public ItemWitherBoneArmor(String name, int renderIndex, EntityEquipmentSlot equipmentSlot)
    {
        super("wither_bone_" + name, NetherExMaterials.WITHER_BONE, renderIndex, equipmentSlot);
    }

    @Override
    public boolean getIsRepairable(ItemStack armor, ItemStack repair)
    {
        return repair.getItem() == NetherExItems.WITHER_BONE;
    }
}

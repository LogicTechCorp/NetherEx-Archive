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

package logictechcorp.netherex.init;

import logictechcorp.netherex.NetherEx;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

public class NetherExMaterials
{
    public static final Item.ToolMaterial AMEDIAN = EnumHelper.addToolMaterial(NetherEx.MOD_ID + ":amedian", 4, 2250, 10.0F, 1.0F, 12).setRepairItem(new ItemStack(NetherExItems.AMETHYST_CRYSTAL));

    public static final ItemArmor.ArmorMaterial WITHER_BONE = EnumHelper.addArmorMaterial(NetherEx.MOD_ID + ":wither_bone", NetherEx.MOD_ID + ":wither_bone_armor", 8, new int[]{2, 3, 4, 2}, 16, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.5F).setRepairItem(new ItemStack(NetherExItems.WITHER_BONE));
    public static final ItemArmor.ArmorMaterial ORANGE_SALAMANDER_HIDE = EnumHelper.addArmorMaterial(NetherEx.MOD_ID + ":orange_salamander_hide", NetherEx.MOD_ID + ":orange_salamander_hide_armor", 10, new int[]{2, 4, 5, 2}, 21, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0F);
    public static final ItemArmor.ArmorMaterial BLACK_SALAMANDER_HIDE = EnumHelper.addArmorMaterial(NetherEx.MOD_ID + ":black_salamander_hide", NetherEx.MOD_ID + ":black_salamander_hide_armor", 10, new int[]{2, 4, 5, 2}, 21, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0F);
}

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

package nex.init;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import nex.NetherEx;

public class NetherExMaterials
{
    public static final Material FLUID_ICHOR = new MaterialLiquid(MapColor.PURPLE);

    public static Item.ToolMaterial TOOL_BONE_WITHERED = EnumHelper.addToolMaterial(NetherEx.MOD_ID + ":bone_withered", 2, 250, 12.0F, 1.2F, 22);

    public static ItemArmor.ArmorMaterial ARMOR_BONE_WITHERED = EnumHelper.addArmorMaterial(NetherEx.MOD_ID + ":bone_withered", NetherEx.MOD_ID + ":bone_withered", 8, new int[]{2, 3, 3, 2}, 16, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.5F);
    public static ItemArmor.ArmorMaterial ARMOR_HIDE_SALAMANDER = EnumHelper.addArmorMaterial(NetherEx.MOD_ID + ":hide_salamander", NetherEx.MOD_ID + ":hide_salamander", 10, new int[]{2, 5, 4, 2}, 21, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0F);

    public static void init()
    {

    }
}

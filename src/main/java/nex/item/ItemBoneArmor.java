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

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import nex.NetherEx;
import nex.init.NetherExMaterials;

public class ItemBoneArmor extends ItemNetherExArmor
{
    public ItemBoneArmor(String name, int renderIndex, EntityEquipmentSlot equipmentSlot)
    {
        super("armor_" + name + "_bone", NetherExMaterials.ARMOR_BONE, renderIndex, equipmentSlot);

        addPropertyOverride(new ResourceLocation("variant"), (stack, worldIn, entityIn) -> isVariant(stack) ? 1.0F : 0.0F);
    }

    private static boolean isVariant(ItemStack stack)
    {
        return stack.getTagCompound() != null && stack.getTagCompound().hasKey("Variant");
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return stack.getTagCompound() != null && stack.getTagCompound().hasKey("Variant") ? String.format(NetherEx.MOD_ID + ":textures/models/armor/bone_variant_layer_%d.png", getEquipmentSlot() == EntityEquipmentSlot.LEGS ? 2 : 1) : super.getArmorTexture(stack, entity, slot, type);
    }
}

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

import lex.util.NBTHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import nex.NetherEx;
import nex.init.NetherExItems;
import nex.init.NetherExMaterials;

@SuppressWarnings("ConstantConditions")
public class ItemSalamanderHideArmor extends ItemNetherExArmor
{
    public ItemSalamanderHideArmor(String name, int renderIndex, EntityEquipmentSlot equipmentSlot)
    {
        super("salamander_hide_" + name, NetherExMaterials.SALAMANDER_HIDE, renderIndex, equipmentSlot);
        addPropertyOverride(new ResourceLocation("variant"), (stack, worldIn, entity) -> isVariant(stack) ? 1.0F : 0.0F);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        if(isInCreativeTab(tab))
        {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setBoolean("Variant", true);
            list.add(new ItemStack(this, 1, 0));
            list.add(NBTHelper.setTag(new ItemStack(this, 1, 0), compound));
        }
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return stack.getTagCompound() != null && stack.getTagCompound().hasKey("Variant") ? String.format(NetherEx.MOD_ID + ":textures/models/armor/hide_salamander_variant_layer_%d.png", this == NetherExItems.SALAMANDER_HIDE_LEGGINGS ? 2 : 1) : super.getArmorTexture(stack, entity, slot, type);
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return !repair.isEmpty() && repair.getItem() == NetherExItems.SALAMANDER_HIDE || super.getIsRepairable(toRepair, repair);

    }

    private static boolean isVariant(ItemStack stack)
    {
        return stack.getTagCompound() != null && stack.getTagCompound().hasKey("Variant");
    }
}

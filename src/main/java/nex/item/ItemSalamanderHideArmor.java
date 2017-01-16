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

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import nex.NetherEx;
import nex.init.NetherExItems;
import nex.init.NetherExMaterials;
import nex.util.ArmorUtil;
import nex.util.NBTUtil;

@SuppressWarnings("ConstantConditions")
public class ItemSalamanderHideArmor extends ItemNetherExArmor
{
    public ItemSalamanderHideArmor(String name, int renderIndex, EntityEquipmentSlot equipmentSlot)
    {
        super("armor_" + name + "_hide_salamander", NetherExMaterials.ARMOR_HIDE_SALAMANDER, renderIndex, equipmentSlot);

        addPropertyOverride(new ResourceLocation("variant"), (stack, worldIn, entity) -> isVariant(stack) ? 1.0F : 0.0F);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean("Variant", true);
        list.add(new ItemStack(item, 1, 0));
        list.add(NBTUtil.setTag(new ItemStack(item, 1, 0), compound));
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return stack.getTagCompound() != null && stack.getTagCompound().hasKey("Variant") ? String.format(NetherEx.MOD_ID + ":textures/models/armor/hide_salamander_variant_layer_%d.png", getEquipmentSlot() == EntityEquipmentSlot.LEGS ? 2 : 1) : super.getArmorTexture(stack, entity, slot, type);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
        if(ArmorUtil.isWearingFullArmorSet(player, getArmorMaterial()))
        {
            player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE));
        }
    }

    @Override
    public boolean getIsRepairable(ItemStack armor, ItemStack repair)
    {
        return NBTUtil.setTag(armor).getTagCompound().hasKey("Variant") && repair.getItem() == NetherExItems.ITEM_HIDE_SALAMANDER && repair.getItemDamage() == 1 || !NBTUtil.setTag(armor).getTagCompound().hasKey("Variant") && repair.getItem() == NetherExItems.ITEM_HIDE_SALAMANDER && repair.getItemDamage() == 0;
    }

    private static boolean isVariant(ItemStack stack)
    {
        return stack.getTagCompound() != null && stack.getTagCompound().hasKey("Variant");
    }
}

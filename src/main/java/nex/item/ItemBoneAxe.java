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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import nex.init.NetherExMaterials;
import nex.util.NBTUtil;

public class ItemBoneAxe extends ItemNetherExAxe
{
    public ItemBoneAxe()
    {
        super("tool_axe_bone", NetherExMaterials.TOOL_BONE, 6.0F, -3.0F);

        addPropertyOverride(new ResourceLocation("variant"), (stack, worldIn, entityIn) -> isVariant(stack) ? 1.0F : 0.0F);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setBoolean("Variant", true);
        list.add(new ItemStack(item, 1, 0));
        list.add(NBTUtil.setTag(new ItemStack(item, 1, 0), compound));
    }

    private static boolean isVariant(ItemStack stack)
    {
        return stack.getTagCompound() != null && stack.getTagCompound().hasKey("Variant");
    }
}


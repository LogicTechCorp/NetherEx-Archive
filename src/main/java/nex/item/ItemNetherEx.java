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

package nex.item;

import com.google.common.base.CaseFormat;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import nex.NetherEx;
import nex.block.IVariantContainer;
import nex.init.ModItems;

import java.util.List;

/**
 * This is a base implementation for items
 * <p>
 * Inspired by Vazkii:
 * https://github.com/Vazkii/Psi/blob/master/src/main/java/vazkii/psi/common/item/base/ItemMod.java
 */
public abstract class ItemNetherEx extends Item implements IVariantContainer
{
    private final String[] VARIANTS;

    public ItemNetherEx(String name, String... variants)
    {
        if(variants.length > 1)
        {
            setHasSubtypes(true);
        }

        if(variants.length == 0)
        {
            variants = new String[]{name};
        }

        VARIANTS = variants;

        setCreativeTab(NetherEx.CREATIVE_TAB);

        setRegistryName(NetherEx.MOD_ID + ":" + name);
        setUnlocalizedName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, getRegistryName().toString()));

        ModItems.variantContainers.add(this);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
        for(int i = 0; i < getVariants().length; i++)
        {
            subItems.add(new ItemStack(itemIn, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        if(getVariants().length == 1)
        {
            return "item." + NetherEx.MOD_ID + ":" + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, getRegistryName().getResourcePath());
        }

        return "item." + NetherEx.MOD_ID + ":" + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, getRegistryName().getResourcePath()) + "." + getVariants()[stack.getItemDamage()];
    }

    @Override
    public String[] getVariants()
    {
        return VARIANTS;
    }
}
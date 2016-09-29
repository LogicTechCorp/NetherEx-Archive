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
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import nex.NetherEx;
import nex.block.IModBlock;
import nex.block.IVariantContainer;
import nex.init.ModItems;

import java.util.List;

/**
 * This is a base implementation for itemblocks that use an enum to add variants
 * <p>
 * Inspired by Vazkii:
 * https://github.com/Vazkii/Psi/blob/master/src/main/java/vazkii/psi/common/item/base/ItemModBlock.java
 */
public class ItemBlockVariantContainer extends ItemBlock implements IVariantContainer
{
    private IModBlock block;
    private String propertyName;

    public ItemBlockVariantContainer(Block blockIn, String propertyNameIn)
    {
        super(blockIn);

        block = (IModBlock) blockIn;
        propertyName = propertyNameIn;

        if(getVariants().length > 1)
        {
            setHasSubtypes(true);
        }

        ModItems.variantContainers.add(this);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> variants)
    {
        for(int i = 0; i < getVariants().length; i++)
        {
            if(block.shouldDisplayVariant(i))
            {
                variants.add(new ItemStack(item, 1, i));
            }
        }
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        if(stack.getItemDamage() > getVariants().length)
        {
            return "tile." + NetherEx.MOD_ID + ":" + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, getRegistryName().getResourcePath()) + ".null";
        }
        else if(getVariants().length  == 1)
        {
            return "tile." + NetherEx.MOD_ID + ":" + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, getRegistryName().getResourcePath());
        }

        return "tile." + NetherEx.MOD_ID + ":" + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, getRegistryName().getResourcePath()) + "." + getVariants()[stack.getItemDamage()];
    }

    @Override
    public String[] getVariants()
    {
        return block.getVariants();
    }

    @Override
    public String getPropertyName()
    {
        return propertyName;
    }
}
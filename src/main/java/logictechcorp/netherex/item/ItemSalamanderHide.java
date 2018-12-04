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

import com.google.common.base.CaseFormat;
import logictechcorp.libraryex.client.model.item.ItemModelHandler;
import logictechcorp.libraryex.item.ItemLibEx;
import logictechcorp.netherex.NetherEx;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;

public class ItemSalamanderHide extends ItemLibEx
{
    public ItemSalamanderHide()
    {
        super(NetherEx.instance, "salamander_hide");
        this.setHasSubtypes(true);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        if(this.isInCreativeTab(tab))
        {
            for(EnumType type : EnumType.values())
            {
                list.add(new ItemStack(this, 1, type.ordinal()));
            }
        }
    }

    @Override
    public String getTranslationKey(ItemStack stack)
    {
        return super.getTranslationKey() + "." + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, EnumType.fromMeta(stack.getItemDamage()).getName());
    }

    @Override
    public void registerModel()
    {
        for(ItemSalamanderHide.EnumType type : ItemSalamanderHide.EnumType.values())
        {
            ItemModelHandler.registerItemModel(this, type.ordinal(), this.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }
    }

    public enum EnumType implements IStringSerializable
    {
        ORANGE,
        BLACK;

        @Override
        public String getName()
        {
            return this.toString().toLowerCase();
        }

        public static EnumType fromMeta(int meta)
        {
            if(meta < 0 || meta >= values().length)
            {
                meta = 0;
            }

            return values()[meta];
        }
    }
}

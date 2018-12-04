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
import logictechcorp.netherex.block.BlockNetherrack;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNetherbrick extends ItemLibEx
{
    public ItemNetherbrick()
    {
        super(NetherEx.instance, "netherbrick");
        this.setHasSubtypes(true);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        if(this.isInCreativeTab(tab))
        {
            for(BlockNetherrack.EnumType type : BlockNetherrack.EnumType.values())
            {
                list.add(new ItemStack(this, 1, type.ordinal()));
            }
        }
    }

    @Override
    public String getTranslationKey(ItemStack stack)
    {
        return super.getTranslationKey() + "." + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, BlockNetherrack.EnumType.fromMeta(stack.getItemDamage()).getName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerModel()
    {
        for(BlockNetherrack.EnumType type : BlockNetherrack.EnumType.values())
        {
            ItemModelHandler.registerItemModel(this, type.ordinal(), this.getRegistryName().toString(), String.format("type=%s", type.getName()));
        }
    }
}

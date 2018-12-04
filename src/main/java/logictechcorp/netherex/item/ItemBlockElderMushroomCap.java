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
import logictechcorp.libraryex.item.ItemBlockLibEx;
import logictechcorp.netherex.block.BlockElderMushroom;
import logictechcorp.netherex.init.NetherExBlocks;
import net.minecraft.item.ItemStack;

public class ItemBlockElderMushroomCap extends ItemBlockLibEx
{
    public ItemBlockElderMushroomCap()
    {
        super(NetherExBlocks.ELDER_MUSHROOM_CAP);
        this.setHasSubtypes(true);
    }

    @Override
    public String getTranslationKey(ItemStack stack)
    {
        return super.getTranslationKey() + "." + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, BlockElderMushroom.EnumType.fromMeta(stack.getItemDamage()).getName());
    }
}

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

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import nex.NetherEx;
import nex.block.BlockNetherrack;
import nex.init.NetherExBlocks;

import java.util.List;

public class ItemBlockNetherrack extends ItemBlockNetherEx
{
    public ItemBlockNetherrack()
    {
        super(NetherExBlocks.NETHERRACK);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for(BlockNetherrack.EnumType type : BlockNetherrack.EnumType.values())
        {
            list.add(new ItemStack(this, 1, type.ordinal()));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        String registryName = getRegistryName().getResourcePath();

        switch(stack.getItemDamage())
        {
            case 0:
                return "tile." + NetherEx.MOD_ID + ":" + registryName + ".corrupted";
            default:
                return "tile." + NetherEx.MOD_ID + ":" + registryName;
        }
    }

    @Override
    public int getMetadata(int metadata)
    {
        return metadata;
    }
}

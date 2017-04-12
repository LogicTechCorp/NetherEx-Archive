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

package nex.recipe;

import net.minecraft.item.ItemStack;

public class TradeRecipe
{
    private ItemStack output;
    private ItemStack inputA;
    private ItemStack inputB;
    private int stock;

    public TradeRecipe(ItemStack outputIn, ItemStack inputAIn, ItemStack inputBIn, int stockIn)
    {
        output = outputIn;
        inputA = inputAIn;
        inputB = inputBIn;
        stock = stockIn;
    }

    public ItemStack getOutput()
    {
        return output;
    }

    public ItemStack getInputA()
    {
        return inputA;
    }

    public ItemStack getInputB()
    {
        return inputB;
    }

    public int getStock()
    {
        return stock;
    }
}

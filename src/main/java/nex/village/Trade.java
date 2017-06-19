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

package nex.village;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;

import java.util.List;
import java.util.Random;

public class Trade extends MerchantRecipe
{
    private final TradeOffer.Amount outputAmount;
    private final TradeOffer.Amount inputAAmount;
    private final TradeOffer.Amount inputBAmount;
    private final TradeOffer.Amount offerAmount;
    private final List<TradeOffer.Enchantment> outputEnchantments;

    public Trade(ItemStack output, TradeOffer.Amount outputAmountIn, ItemStack inputA, TradeOffer.Amount inputAAmountIn, ItemStack inputB, TradeOffer.Amount inputBAmountIn, TradeOffer.Amount offerAmountIn, List<TradeOffer.Enchantment> outputEnchantmentsIn)
    {
        super(inputA, inputB, output, 0, 1);

        outputAmount = outputAmountIn;
        inputAAmount = inputAAmountIn;
        inputBAmount = inputBAmountIn;
        offerAmount = offerAmountIn;
        outputEnchantments = outputEnchantmentsIn;
    }

    public MerchantRecipe getRandomTrade(Random rand)
    {
        ItemStack outputStack = getItemToSell().copy();
        ItemStack inputA = getItemToBuy().copy();
        ItemStack inputB = getSecondItemToBuy().copy();
        outputStack.setCount(rand.nextInt((outputAmount.getMax() - outputAmount.getMin()) + 1) + outputAmount.getMin());
        inputA.setCount(rand.nextInt((inputAAmount.getMax() - inputAAmount.getMin()) + 1) + inputAAmount.getMin());
        inputB.setCount(rand.nextInt((inputBAmount.getMax() - inputBAmount.getMin()) + 1) + inputBAmount.getMin());

        if(outputEnchantments.size() > 0)
        {
            for(TradeOffer.Enchantment outputEnchantment : outputEnchantments)
            {
                Enchantment enchantment = Enchantment.getEnchantmentByLocation(outputEnchantment.getName());
                TradeOffer.Amount enchantmentAmount = outputEnchantment.getAmount();

                if(enchantment != null)
                {
                    if(outputStack.getItem() instanceof ItemEnchantedBook)
                    {
                        ((ItemEnchantedBook) outputStack.getItem()).addEnchantment(outputStack, new EnchantmentData(enchantment, rand.nextInt((enchantmentAmount.getMax() - enchantmentAmount.getMin()) + 1) + enchantmentAmount.getMin()));
                    }
                    else
                    {
                        outputStack.addEnchantment(enchantment, rand.nextInt((enchantmentAmount.getMax() - enchantmentAmount.getMin()) + 1) + enchantmentAmount.getMin());
                    }
                }
            }
        }

        return new MerchantRecipe(inputA, inputB, outputStack, 0, rand.nextInt((offerAmount.getMax() - offerAmount.getMin()) + 1) + offerAmount.getMin());
    }
}

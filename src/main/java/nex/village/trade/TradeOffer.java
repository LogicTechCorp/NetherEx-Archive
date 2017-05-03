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

package nex.village.trade;

import java.util.List;

public class TradeOffer
{
    private Output output;
    private Input inputA;
    private Input inputB;
    private int level;
    private Amount amount;

    public Output getOutput()
    {
        return output;
    }

    public Input getInputA()
    {
        return inputA;
    }

    public Input getInputB()
    {
        return inputB;
    }

    public int getLevel()
    {
        return level;
    }

    public Amount getAmount()
    {
        return amount;
    }

    public class Output
    {
        private String name;
        private int meta;
        private Amount amount;
        private List<Enchantment> enchantments;
        private Display display;

        public String getName()
        {
            return name;
        }

        public int getMeta()
        {
            return meta;
        }

        public Amount getAmount()
        {
            return amount;
        }

        public List<Enchantment> getEnchantments()
        {
            return enchantments;
        }

        public Display getDisplay()
        {
            return display;
        }
    }

    public static class Input
    {
        private String name;
        private int meta;
        private Amount amount;

        public Input(String nameIn, int metaIn, Amount amountIn)
        {
            name = nameIn;
            meta = metaIn;
            amount = amountIn;
        }

        public String getName()
        {
            return name;
        }

        public int getMeta()
        {
            return meta;
        }

        public Amount getAmount()
        {
            return amount;
        }
    }

    public static class Amount
    {
        private int min;
        private int max;

        public Amount(int minIn, int maxIn)
        {
            min = minIn;
            max = maxIn;
        }

        public int getMin()
        {
            return min;
        }

        public int getMax()
        {
            return max;
        }
    }

    public class Enchantment
    {
        private String name;
        private Amount amount;

        public String getName()
        {
            return name;
        }

        public Amount getAmount()
        {
            return amount;
        }
    }

    public class Display
    {
        private String name;
        private List<String> lore;

        public String getName()
        {
            return name;
        }

        public List<String> getLore()
        {
            return lore;
        }
    }
}

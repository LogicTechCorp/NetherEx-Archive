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

package nex.trade;

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
        private String id;
        private int meta;
        private Amount amount;
        private List<Enchantment> ench;
        private Display display;

        public String getId()
        {
            return id;
        }

        public int getMeta()
        {
            return meta;
        }

        public Amount getAmount()
        {
            return amount;
        }

        public List<Enchantment> getEnch()
        {
            return ench;
        }

        public Display getDisplay()
        {
            return display;
        }
    }

    public class Input
    {
        private String id;
        private int meta;
        private Amount amount;

        public String getId()
        {
            return id;
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

    public class Amount
    {
        private int min;
        private int max;

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
        private String id;
        private int level;

        public String getId()
        {
            return id;
        }

        public int getLevel()
        {
            return level;
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

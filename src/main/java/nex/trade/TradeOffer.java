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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;

public class TradeOffer
{
    private Output output;
    private Input inputA;
    private Input inputB;
    private int level;
    private Amount amount;

    public class Output
    {
        private String id;
        private int meta;
        private Amount amount;
        private List<Ench> ench;
        private Display display;
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

    public class Ench
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
        private String color;
        private List<String> lore;

        public String getName()
        {
            return name;
        }

        public String getColor()
        {
            return color;
        }

        public List<String> getLore()
        {
            return lore;
        }
    }
}

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
import net.minecraft.item.ItemStack;

import java.lang.reflect.Type;

public class TradeOffer
{
    private final ItemStack output;
    private final ItemStack inputA;
    private final ItemStack inputB;
    private final int level;
    private final int stock;

    public TradeOffer(ItemStack outputIn, ItemStack inputAIn, ItemStack inputBIn, int levelIn, int stockIn)
    {
        output = outputIn;
        inputA = inputAIn;
        inputB = inputBIn;
        level = levelIn;
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

    public int getLevel()
    {
        return level;
    }

    public int getStock()
    {
        return stock;
    }

    public static class Deserializer implements JsonDeserializer<TradeOffer>
    {
        @Override
        public TradeOffer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            return null;
        }
    }
}

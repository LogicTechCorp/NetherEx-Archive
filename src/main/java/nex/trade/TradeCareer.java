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

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;

public class TradeCareer
{
    private final String name;
    private final List<TradeOffer> offers;

    public TradeCareer(String nameIn, TradeOffer[] offersIn)
    {
        name = nameIn;
        offers = Lists.newArrayList(offersIn);
    }

    public String getName()
    {
        return name;
    }

    public List<TradeOffer> getOffers()
    {
        return offers;
    }

    public static class Deserializer implements JsonDeserializer<TradeCareer>
    {
        @Override
        public TradeCareer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            return null;
        }
    }
}

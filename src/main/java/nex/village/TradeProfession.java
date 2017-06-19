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

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TradeProfession
{
    private String name;
    private List<TradeCareer> careers;

    public String getName()
    {
        return name;
    }

    public List<TradeCareer> getCareers()
    {
        return careers;
    }

    public enum EnumType
    {
        LEADER,
        FORAGER,
        BLACKSMITH,
        SORCERER;

        public static EnumType getRandom(Random rand, boolean isLeader)
        {
            if(isLeader)
            {
                return LEADER;
            }
            else
            {
                List<EnumType> types = Lists.newArrayList();
                Collections.addAll(types, values());
                types.remove(LEADER);
                return types.get(rand.nextInt(types.size()));
            }
        }

        public static EnumType fromIndex(int index)
        {
            EnumType profession = values()[index];
            return profession != null ? profession : FORAGER;
        }

        public static EnumType fromProfession(TradeProfession profession)
        {
            return valueOf(profession.getName().toUpperCase());
        }
    }

}

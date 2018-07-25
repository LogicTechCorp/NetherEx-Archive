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

package nex.village;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lex.util.WeightedHelper;
import lex.village.Trade;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import nex.init.NetherExLootTables;
import nex.init.NetherExTextures;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Pigtificate
{
    public enum Profession
    {
        LEADER,
        FORAGER,
        BLACKSMITH,
        SORCERER;

        public static Profession getRandom(Random rand, boolean isLeader)
        {
            if(isLeader)
            {
                return LEADER;
            }
            else
            {
                List<Profession> types = Lists.newArrayList();
                Collections.addAll(types, values());
                types.remove(LEADER);
                return types.get(rand.nextInt(types.size()));
            }
        }

        public static Profession getFromIndex(int index)
        {
            Profession profession = values()[index];
            return profession != null ? profession : FORAGER;
        }

        public static Profession getFromString(String string)
        {
            if(!Strings.isNullOrEmpty(string))
            {
                for(Profession profession : values())
                {
                    if(profession.name().equalsIgnoreCase(string))
                    {
                        return profession;
                    }
                }
            }

            return FORAGER;
        }

    }

    public enum Career
    {
        CHIEF(Profession.LEADER, 16, NetherExTextures.PIGTIFICATE_CHIEF, NetherExLootTables.PIGTIFICATE_CHIEF),
        HUNTER(Profession.FORAGER, 5, NetherExTextures.PIGTIFICATE_HUNTER, NetherExLootTables.PIGTIFICATE_HUNTER),
        GATHERER(Profession.FORAGER, 5, NetherExTextures.PIGTIFICATE_GATHERER, NetherExLootTables.PIGTIFICATE_GATHERER),
        SCAVENGER(Profession.FORAGER, 5, NetherExTextures.PIGTIFICATE_SCAVENGER, NetherExLootTables.PIGTIFICATE_SCAVENGER),
        ARMORSMITH(Profession.BLACKSMITH, 8, NetherExTextures.PIGTIFICATE_ARMORSMITH, NetherExLootTables.PIGTIFICATE_ARMORSMITH),
        TOOLSMITH(Profession.BLACKSMITH, 8, NetherExTextures.PIGTIFICATE_TOOLSMITH, NetherExLootTables.PIGTIFICATE_TOOLSMITH),
        ENCHANTER(Profession.SORCERER, 8, NetherExTextures.PIGTIFICATE_ENCHANTER, NetherExLootTables.PIGTIFICATE_ENCHANTER),
        BREWER(Profession.SORCERER, 8, NetherExTextures.PIGTIFICATE_BREWER, NetherExLootTables.PIGTIFICATE_BREWER);

        private Profession profession;
        private int weight;
        private ResourceLocation texture;
        private ResourceLocation lootTable;
        private final Map<Integer, List<Trade>> trades = Maps.newHashMap();

        Career(Profession professionIn, int weightIn, ResourceLocation textureIn, ResourceLocation lootTableIn)
        {
            profession = professionIn;
            weight = weightIn;
            texture = textureIn;
            lootTable = lootTableIn;
        }

        public void addTrade(Trade trade)
        {
            trades.computeIfAbsent(trade.getTradeLevel(), k -> Lists.newArrayList()).add(trade);
        }

        public void removeAllTrades()
        {
            trades.clear();
        }

        public static Career getFromIndex(int index)
        {
            Career career = values()[index];
            return career != null ? career : HUNTER;
        }

        public static Career getFromString(String string)
        {
            if(!Strings.isNullOrEmpty(string))
            {
                for(Career career : values())
                {
                    if(career.name().equalsIgnoreCase(string))
                    {
                        return career;
                    }
                }
            }

            return HUNTER;
        }

        public static Career getRandomCareer(Profession profession, Random rand)
        {
            List<WeightedHelper.NamedItem> careers = Lists.newArrayList();

            for(Pigtificate.Career career : Pigtificate.Career.values())
            {
                if(profession == career.getProfession())
                {
                    careers.add(new WeightedHelper.NamedItem(career.name(), career.getWeight()));
                }
            }

            return Pigtificate.Career.getFromString(WeightedRandom.getRandomItem(rand, careers).getName());
        }

        public int getWeight()
        {
            return weight;
        }

        public ResourceLocation getTexture()
        {
            return texture;
        }

        public ResourceLocation getLootTable()
        {
            return lootTable;
        }

        public Profession getProfession()
        {
            return profession;
        }

        public Map<Integer, List<Trade>> getTrades()
        {
            return ImmutableMap.copyOf(trades);
        }
    }
}

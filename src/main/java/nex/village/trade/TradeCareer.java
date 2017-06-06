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

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import nex.init.NetherExLootTables;
import nex.init.NetherExTextures;

import java.util.List;

public class TradeCareer
{
    private String name;
    private List<TradeOffer> trades;

    public String getName()
    {
        return name;
    }

    public List<TradeOffer> getTrades()
    {
        return trades;
    }

    public static class Weighted extends WeightedRandom.Item
    {
        private EnumType type;

        public Weighted(EnumType typeIn)
        {
            super(typeIn.getWeight());
            type = typeIn;
        }

        public EnumType getType()
        {
            return type;
        }
    }

    public enum EnumType
    {
        CHIEF(TradeProfession.EnumType.LEADER, 16, NetherExTextures.ENTITY_PIGTIFICATE_CHIEF, NetherExLootTables.ENTITY_PIGTIFICATE_CHIEF),
        HUNTER(TradeProfession.EnumType.FORAGER, 5, NetherExTextures.ENTITY_PIGTIFICATE_HUNTER, NetherExLootTables.ENTITY_PIGTIFICATE_HUNTER),
        GATHERER(TradeProfession.EnumType.FORAGER, 5, NetherExTextures.ENTITY_PIGTIFICATE_GATHERER, NetherExLootTables.ENTITY_PIGTIFICATE_GATHERER),
        SCAVENGER(TradeProfession.EnumType.FORAGER, 5, NetherExTextures.ENTITY_PIGTIFICATE_SCAVENGER, NetherExLootTables.ENTITY_PIGTIFICATE_SCAVENGER),
        ARMORSMITH(TradeProfession.EnumType.BLACKSMITH, 8, NetherExTextures.ENTITY_PIGTIFICATE_ARMORSMITH, NetherExLootTables.ENTITY_PIGTIFICATE_ARMORSMITH),
        TOOLSMITH(TradeProfession.EnumType.BLACKSMITH, 8, NetherExTextures.ENTITY_PIGTIFICATE_TOOLSMITH, NetherExLootTables.ENTITY_PIGTIFICATE_TOOLSMITH),
        ENCHANTER(TradeProfession.EnumType.SORCERER, 8, NetherExTextures.ENTITY_PIGTIFICATE_ENCHANTER, NetherExLootTables.ENTITY_PIGTIFICATE_ENCHANTER),
        BREWER(TradeProfession.EnumType.SORCERER, 8, NetherExTextures.ENTITY_PIGTIFICATE_BREWER, NetherExLootTables.ENTITY_PIGTIFICATE_BREWER);

        private TradeProfession.EnumType profession;
        private int weight;
        private ResourceLocation texture;
        private ResourceLocation lootTable;

        EnumType(TradeProfession.EnumType professionIn, int weightIn, ResourceLocation textureIn, ResourceLocation lootTableIn)
        {
            profession = professionIn;
            weight = weightIn;
            texture = textureIn;
            lootTable = lootTableIn;
        }

        public static EnumType fromIndex(int index)
        {
            EnumType career = values()[index];
            return career != null ? career : HUNTER;
        }

        public static EnumType fromCareer(TradeCareer career)
        {
            return valueOf(career.getName().toUpperCase());
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

        public TradeProfession.EnumType getProfession()
        {
            return profession;
        }
    }
}

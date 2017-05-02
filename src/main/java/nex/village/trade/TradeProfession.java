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
import nex.NetherEx;

import java.util.List;

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
        LEADER(new ResourceLocation(NetherEx.MOD_ID + ":textures/entities/pigtificate/pigtificate_chief.png")),
        FORAGER(new ResourceLocation(NetherEx.MOD_ID + ":textures/entities/pigtificate/pigtificate_forager.png")),
        BLACKSMITH(new ResourceLocation(NetherEx.MOD_ID + ":textures/entities/pigtificate/pigtificate_blacksmith.png")),
        SORCERER(new ResourceLocation(NetherEx.MOD_ID + ":textures/entities/pigtificate/pigtificate_sorcerer.png"));

        private ResourceLocation textureLocation;

        EnumType(ResourceLocation textureLocationIn)
        {
            textureLocation = textureLocationIn;
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

        public ResourceLocation getTextureLocation()
        {
            return textureLocation;
        }
    }

}
